package com.service;

import com.mapper.CommentMapper;
import com.mapper.LikeMapper;
import com.pojo.Like;
import com.service.BaseService.BaseService;
import com.util.JedisUtil;
import org.apache.ibatis.session.SqlSession;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

public class LikeService implements BaseService {
    //redis
    public static JedisPool jedisPool = JedisUtil.redisPoolFactory();

    static {
        System.out.println("进行一次redis初始化...");
        FaBuJob();
        init();
        System.out.println("redis初始化成功");
    }
    public static void init() {
        Map<String,String> map = new HashMap<>();
        Map<String,String> nummap = new HashMap<>();
        Jedis jedis = null;
        map.put("-1#-1", "-1");
        nummap.put("-1", "0");
        jedis = jedisPool.getResource();
        jedis.flushDB();
        jedis.hmset("hash",map);
        jedis.hmset("numhash",nummap);
        JedisUtil.closeJedis(jedis);
    }

    /**
     * 在hash,numhash当中共存有2种类型的key->value
     * 1.userid$blogid => 0/1 //代表了一条用户给博客点赞了或者没有点赞
     * 2.blogid => num //博客获得的点赞数量
     * @param userid
     * @param blogid
     */
    public static Boolean CheckUserHaveLikeForBlog(Long userid,Long blogid){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.hget("hash", userid+"#"+blogid);
        boolean flag = true;

        if(selectLikeByUIdAndBId(userid,blogid)==null){
            flag = false;
        }

        //缓存中被取消点赞,或者缓存中为空
        //System.out.println("缓存中情况为:"+value+" "+"判断:"+"1".equals(value));
        if(value==null){

        }else if("1".equals(value)){
            flag = false;
        }else if("0".equals(value)){
            flag = true;
        }
        if(jedis!=null){
            jedis.close();
        }
        return flag;
    }

    //点赞博客
    public static void AddLike(Long userid,Long blogid){
        Jedis jedis = jedisPool.getResource();
        try {
                jedis.hset("hash", userid +"#"+blogid, "0");
                jedis.hincrBy("numhash", String.valueOf(blogid), 1);
            System.out.println("点赞后:"+jedis.hget("numhash", blogid.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    //取消点赞
    public static void CancelAddLike(Long userid,Long blogid){
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset("hash", userid+"#"+blogid, "1");
            jedis.hincrBy("numhash", String.valueOf(blogid), -1);
            System.out.println("取消点赞后:"+jedis.hget("numhash", blogid.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            JedisUtil.closeJedis(jedis);
        }

    }



    /**
     * 通过博客id查询点赞数量
     */
    public static int CheckLikeNumByBlogId(Long blogid){
        Jedis jedis = jedisPool.getResource();
        try {
            //先查出数据库中博客点赞数
            SqlSession sqlSession = factory.openSession();
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            int num1 = likeMapper.selectLikeNumByblogId(blogid);
            sqlSession.close();
            //再查出redis当中点赞数
            int num2 = 0;
            String num2str = jedis.hget("numhash",blogid.toString());
            if(num2str!=null){
                num2 = Integer.parseInt(num2str);
            }
            return num1 +num2;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            JedisUtil.closeJedis(jedis);
        }
        return 0;
    }

    /**
     * 插入一条
     * @param userid
     * @param blogid
     * @param isdelete
     */
    public static void InsertLike(Long userid,Long blogid,Boolean isdelete){
        SqlSession sqlSession = factory.openSession();
        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        likeMapper.InsertMoreLike(userid,blogid,isdelete);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 通过博客id 和 用户id 查询like是否存在 (如果isdelete为true则查不到)
     */
    public static Like selectLikeByUIdAndBId(Long userid,Long blogid){
        SqlSession sqlSession = factory.openSession();
        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        Like like = likeMapper.selectLikeByUIdAndBId(userid, blogid);
        sqlSession.close();
        return like;
    }



    //发布持久化任务
    public static void FaBuJob(){
        try {
            //创建调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //定义一个触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1") //定义触发器名称和所属触发器的分组
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever()
                            .withIntervalInSeconds(60*60) //每隔x秒执行一次
                            .withRepeatCount(99999))//执行x次
                    .build();
            JobBuilder jobBuilder = JobBuilder.newJob(PersistenceJob.class);//定义一个JobDetail  (任务)
            jobBuilder.withIdentity("PersistenceJob01", "PersistenceJob");//定义任务名称和所属任务的分组
            // jobBuilder.usingJobData("email", "admin@10086.com");//定义传入任务里的属性(key,value)
            JobDetail job = jobBuilder
                    .build();

            //调度加入这个job
            scheduler.scheduleJob(job, trigger);

            //启动任务
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } finally {

        }
    }
    /**
     * 定义任务类
     */
    public static class PersistenceJob  implements Job{
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                LikeService.PersistenceLikeData();
            } catch (Exception e) {
                System.out.println("发生了异常，取消这个Job 对应的所有调度");
                JobExecutionException je =new JobExecutionException(e);
                je.setUnscheduleAllTriggers(true);
                throw je;
            }
        }
    }

    //持久化数据
    public static void PersistenceLikeData(){
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> set = jedis.hkeys(("hash"));
            List<String> list_1 = new ArrayList<>(set);
            List<Like> likes = new ArrayList<>(0);
            for (String s:list_1) {
                if(s.charAt(0) == '-') continue;
                String value = jedis.hget("hash", s);
                String[] temp =s.split ("#");
                Long userid = new Long(temp[0]);
                Long blogid = new Long(temp[1]);
                Boolean isdelete = "1".equals(value);
                System.out.println("de:"+isdelete);
                InsertLike(userid,blogid,isdelete);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            JedisUtil.closeJedis(jedis);
        }
        //这里已经跟数据库保持同步了 , 最后清理一下缓存
        init();
    }

    public static List<Long> selectAllBlogIdByUserLike(Long userid){
        //先进行持久化
        PersistenceLikeData();
        //去数据库查一次
        SqlSession sqlSession = factory.openSession();
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        List<Long>  blogids= commentMapper.selectAllBlogIdByUserLike(userid);

        sqlSession.close();
        return blogids;
    }
    public static void main(String[] args) {

    }


}
