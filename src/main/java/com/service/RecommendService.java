package com.service;

import com.pojo.BeLikeScore;
import com.pojo.Blog;
import com.pojo.LikeScore;

import java.util.*;

public class RecommendService {
    //物品向量 blogid => {userid:score}
    public  static Map<Long,List<BeLikeScore>> itemMap = new HashMap<>();
    //用户向量 userid => {blogid:score}
    public  static Map<Long,List<LikeScore>> userMap = new HashMap<>();
     //本次想要加入测试的user实例
    public static List<Long> users = UserListService.selectAll();
     // 本次想要加入测试的blog实例
    public static List<Long> blogs = BlogListService.selectAllBlogToReCommend();
     // 用户对物品的评分矩阵乛[user][item]
    public static Double[][] userScoreMatrix = new Double[1050][1050];
     //物品相似度矩阵 [item][item]
    public static Double[][] similarValueMatrix = new Double[1050][1050];
    //结果矩阵
    public static double[][] resultMatrix = new double[1050][1050];
    //以下这个map将会用来寻找推荐文章的下标
    public static Map<Long,Integer> UserId_Index = new HashMap<>();
    public static Map<Integer,Long> Blog_Index = new HashMap<>();
    //管理员推荐的几个博客.
    public static List<Long> blogids = new ArrayList<>(0);
     //得到所有用户向量组
    public static void initUserMap(){
        for (int i = 0; i < 1050; i++) {
            for(int j = 0 ; j < 1050 ; j++){
                userScoreMatrix[i][j] = 0.0;
                similarValueMatrix[i][j] = 0.0;
                resultMatrix[i][j] = 0.0;
            }
        }
        for (int i = 0; i < blogs.size(); i++) Blog_Index.put(i, blogs.get(i));
        //遍历用户数组 , 计算出每一个 user对每一个博客的喜好程度
        for (Long userid:users) {
            List<LikeScore> likeScores = new ArrayList<>(0);
            for(Long blogid:blogs){
                LikeScore likeScore = new LikeScore();
                likeScore.setBlogId(blogid);
                double sumScore = 0;
                //是否被点赞?
                if(LikeService.CheckUserHaveLikeForBlog(userid, blogid)){
                    sumScore += 0.3;
                }
                if(CollectionService.selectUserIsCollectionByBIdAndUId(blogid,userid)!=null){
                    sumScore += 0.5;
                }
                likeScore.setLikeDegree(sumScore);
                likeScores.add(likeScore);
            }
            System.out.print("用户"+userid+"{");
            for (int i = 0; i < likeScores.size(); i++) {
                System.out.print(likeScores.get(i).getBlogId()+":"+likeScores.get(i).getLikeDegree()+"  ");
            }
            System.out.println("}");
            userMap.put(userid, likeScores);
        }
    }
    /**
     * 获取博客向量数组 基于用户向量组 相当于给 矩用户评分矩阵进行转置
     */
    public static void initItemMap(){
        for(int k = 0 ; k < blogs.size(); k++){
            Long blogid = blogs.get(k);
            List<BeLikeScore> beLikeScores = new ArrayList<>(0);
            for (Long userid:users) {
                List<LikeScore> likeScores = userMap.get(userid);
                //在用户  userid => {blogid:score,blogid:score,blogid:score...} 中查找 blogid =  此处遍历到的blogid
                LikeScore likeScore = likeScores.get(k);
                BeLikeScore beLikeScore = new BeLikeScore();
                beLikeScore.setLikeDegree(likeScore.getLikeDegree());
                beLikeScore.setUserid(userid);
                beLikeScores.add(beLikeScore);
            }
            itemMap.put(blogid,beLikeScores);
            System.out.print("物品:"+blogid+"{");
            for (int i = 0; i < beLikeScores.size(); i++) {
                System.out.print(beLikeScores.get(i).getUserid()+":"+beLikeScores.get(i).getLikeDegree()+"  ");
            }
            System.out.println("}");
        }
    }

    /**
     * 用户对物品的评分矩阵乛
     */
    public static void updateUserScoreMatrix(){
        //遍历map
        int i = 0,j=0;
        Set<Map.Entry<Long,List<LikeScore>>> entrySet = userMap.entrySet();
        for(Map.Entry<Long,List<LikeScore>> entry:entrySet){
            Long userid = entry.getKey();
            List<LikeScore> likeScores = entry.getValue();
            j=0;
           for(LikeScore likeScore:likeScores){
               userScoreMatrix[i][j] = likeScore.getLikeDegree();
               j++;
           }
           i++;
        }
        System.out.println("用户评分矩阵");
        for(int q = 0 ;  q < users.size() ; q++){
            for(int w = 0 ; w < blogs.size() ; w++){
                System.out.print(userScoreMatrix[q][w] + "    ");
            }
            System.out.println("");
        }
    }

    /**
     * 给相似矩阵赋值
     */
    public static void updateSimilarValueMatrix(){
        for (int i = 0; i < blogs.size(); i++) {
            for (int j = 0; j < blogs.size(); j++) {
                if(i==j) {
                    similarValueMatrix[i][j]=1.0;
                    continue;
                }
                //欧几里德算法文章i 和 文章 j 的 相似度为:
                double d = 0;
                List<BeLikeScore> i_beLikeScores = itemMap.get(blogs.get(i));
                List<BeLikeScore> J_beLikeScores = itemMap.get(blogs.get(j));
                for(int k = 0 ; k < i_beLikeScores.size() ; k++){
                    double i_num = i_beLikeScores.get(k).getLikeDegree();
                    double j_num = J_beLikeScores.get(k).getLikeDegree();
                    d += Math.pow(i_num*i_num - j_num*j_num, 2);
                }
                similarValueMatrix[i][j]=1/(Math.pow(d, 0.5)+1);
            }
        }
        System.out.println("相似矩阵");
        for(int i = 0 ;  i < blogs.size() ; i++){
            for(int j = 0 ; j < blogs.size() ; j++){
                System.out.print(String.format("%.1f",similarValueMatrix[i][j]) + "   ");
            }
            System.out.println("");
        }
    }

    /**
     * 进行矩阵乘法计算
     * @param a
     * @param b
     * @return
     */
    public static double[][] matrix(Double[][] a, Double[][] b) {
        //当a的列数与矩阵b的行数不相等时，不能进行点乘，返回null
        if (a[0].length != b.length)
            return null;
        //c矩阵的行数y，与列数x
        int y = users.size();
        int x = blogs.size();
        double[][] c = new double[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                //c矩阵的第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++){
                    c[i][j] += a[i][k] * b[k][j];
                }

                return c;
    }


    /**
     * 通过userid 去获取 它的 推荐列表 不足 则以热门文章补上
     * @param UID
     * @param algorithm 是否使用算法
     * @return
     */
    public static List<Blog> getRecommendList(Long UID, Boolean algorithm){
        Set<Long> set = new TreeSet<>();
        if(algorithm && UID!=null){
            int userindex = UserId_Index.get(UID);
            List<Double> score = new LinkedList<>();
            for (int i = 0; i < resultMatrix[userindex].length; i++) score.add(resultMatrix[userindex][i]);
            Collections.sort(score);
            for (int i = 0; i < 10 && i < score.size(); i++) {
                double maxnum = -99999;
                int index = 0;
                for(int j = 0 ; j < score.size() ; j++){
                    if(score.get(i)>maxnum){
                        maxnum = score.get(i);
                        index = j;
                    }
                }
                if(score.size()>0){
                    set.add(Blog_Index.get(index));
                    score.remove(index);
                }
            }
            List<Long> userlike1 = LikeService.selectAllBlogIdByUserLike(UID);
            set.addAll(userlike1);
        }
        //不足10个则用管理员推荐的几个补齐.
        if(set.size()<=10){
            set.addAll(blogids);
        }
        return getBlogsByIDList(new ArrayList<>(set));
    }


    /**
     * 得到结果矩阵
     */
    public static void initMyRecommend(){
        initUserMap();
        initItemMap();
        updateUserScoreMatrix();
        updateSimilarValueMatrix();
        resultMatrix = matrix(userScoreMatrix,similarValueMatrix);
        System.out.println("预测结果矩阵");
        for(int i = 0 ;  i < users.size() ; i++){
            System.out.println("用户:" + users.get(i)+"对这些博客的评分:");
            UserId_Index.put(users.get(i), i);
            for(int j = 0 ; j < blogs.size() ; j++){
                System.out.print(String.format("%.1f",resultMatrix[i][j]) + "   ");
            }
            System.out.println("");
        }
    }
    static {
        manageAddBlogId(23L);
        initMyRecommend();
    }

    /**
     * 管理员添加推荐的博客
     * @param blogid
     */
    public static void manageAddBlogId(Long blogid){
        blogids.add(blogid);
    }


    /**
     * 管理员删除一个推荐的博客
     * @param blogid
     */
    public static void manageDeleteBlogId(Long blogid){
        blogids.remove(blogid);
    }

    /**
     * 检查一个博客是否在推荐列表当中
     * @param blogid
     * @return
     */
    public static Boolean checkBlogIsInCommentList(Long blogid){
        for(Long blogid1:blogids){
            if(blogid.equals(blogid1)){
                return true;
            }
        }
        return false;
    }
    /**
     * 通过博客id的list将所有博客拉取到
     * @param ids
     * @return
     */
    public static List<Blog> getBlogsByIDList(List<Long> ids){
        List<Blog> blogs = new ArrayList<>(0);
        for (Long id:ids) {
            //先获取博客id
            Blog blog = BlogListService.selectBlogById(id);
            if(blogs!=null)
            blogs.add(blog);
        }
        return blogs;
    }


}
