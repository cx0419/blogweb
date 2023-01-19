package com.service;

import com.mapper.BlogMapper;
import com.mapper.ColumnMapper;
import com.mapper.TagMapper;
import com.pojo.Blog;
import com.pojo.BlogColumn;
import com.pojo.Column;
import com.pojo.Tag;
import com.service.BaseService.BaseService;
import com.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ColumnService implements BaseService {
    /**
     * 根据名字查询专栏
     * @param name
     * @return
     */
    public static Column selectColumnByNameAndUid(String name, Long userid){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        Column column = columnMapper.selectColumnByNameAndUId(name,userid);
        sqlSession.close();
        return column;
    }
    public static BlogColumn selectBlogColumnByBC(Long blogId, Long columnId){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        BlogColumn blogColumn = columnMapper.selectBlogColumnByBC(blogId, columnId);
        sqlSession.close();
        return blogColumn;
    }

    /**
     *将一个中间关系插入
     * @param blogId
     */
    public static void addBlogColumn(Long blogId, Long columnId){
        Tag tag = new Tag();
        if(selectBlogColumnByBC(blogId,columnId)==null){
            SqlSession sqlSession = factory.openSession();
            ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
            BlogColumn blogColumn = new BlogColumn();
            blogColumn.setBlogId(blogId);
            blogColumn.setColumnId(columnId);
            columnMapper.insertBlogColumn(blogColumn);
            sqlSession.commit();
            sqlSession.close();
        }
    }
    public static Long addColumn(String name,Long userId){
        Column column = new Column();
        Column column1 = selectColumnByNameAndUid(name,userId);
        if(column1==null){
            SqlSession sqlSession = factory.openSession();
            ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
            column.setName(name);
            column.setUserId(userId);
            columnMapper.insertColumn(column);
            sqlSession.commit();
            sqlSession.close();
        }
        return column.getId()!=null?column.getId():column1.getId();
    }
    public static Long addColumn(String name,String introduction,String picture,Boolean show,Long userId){
        Column column = new Column();
        Column column1 = selectColumnByNameAndUid(name,userId);
        if(column1==null){
            SqlSession sqlSession = factory.openSession();
            ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
            column.setName(name);
            column.setUserId(userId);
            column.setIntroduction(introduction);
            column.setPicture(picture);
            column.setShow(show);
            columnMapper.insertColumn(column);
            sqlSession.commit();
            sqlSession.close();
        }
        return column.getId()!=null?column.getId():column1.getId();
    }


    public static void addCloumns(Long blogid, Long userId,List<String> columns){
        for (String name:columns) {
            //添加专栏，包括了专栏的名字和归属者id
            Long columnId = addColumn(name,userId);
            addBlogColumn(blogid,columnId);
        }
    }

    /**
     * 通过关键字来查询 所有专栏
     * @param key
     * @return
     */
    public static List<Column> selectColumnsByKey(int pageSize,int pagenum,String key){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        List<Column> columns = columnMapper.selectColumnsByKey((pagenum-1)*pageSize,pageSize,key);
        sqlSession.close();
        return columns;
    }

    /**
     * 通过关键字来查询 所有专栏
     * @return
     */
    public static List<Column> selectColumnsByUId(int pageSize,int pagenum,Long  userid){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        List<Column> columns = columnMapper.selectColumnsByUId((pagenum-1)*pageSize,pageSize,userid);
        sqlSession.close();
        return columns;
    }

    /**
     * 通过关键字来查询 所有专栏数量
     * @return
     */
    public static int selectColumnsNumByUId(Long  userid){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        int num = columnMapper.selectColumnsNumByUId(userid);
        sqlSession.close();
        return num;
    }

    /**
     * 通过专栏id获取专栏
     * @param id
     * @return
     */
    public static Column selectColumnsByCId(Long id){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        Column column = columnMapper.selectColumnsByCId(id);
        sqlSession.close();
        return column;
    }


    /**
     * 通过关键字获取相关专栏
     * @param key
     * @return
     */
    public static int selectColumnsByKey(String key){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        int num = columnMapper.selectColumnsNumByKey(key);
        sqlSession.close();
        return num;
    }

    /**
     * 获取一个专栏所有的博客id
     * @param id
     * @return
     */
    public static List<BlogColumn> selectBlogColumns(Long id){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        List<BlogColumn> blogColumns = columnMapper.selectBlogColumnsByCId(id);
        sqlSession.close();
        return blogColumns;
    }


    /**
     * 查询一个博客的所有专栏
     * @param id
     * @return
     */
    public static List<Column> selectColumnsByBlogId(Long id){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        List<Column> columns = columnMapper.selectColumnsByBlogId(id);
        sqlSession.close();
        return columns;
    }

    /**
     * 查询一个专栏的博客数量
     * @param id
     * @return
     */
    public static int selectBlogNumColumns(Long id){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        int num = columnMapper.selectBlogNumByColumnId(id);
        sqlSession.close();
        return num;
    }

    /**
     * 查询一个人的专栏数量
     * @param id
     * @return
     */
    public static int selectColumnNumByUserId(Long id){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        int num = columnMapper.selectColumnNumByUId(id);
        sqlSession.close();
        return num;
    }

    /**
     * 删除一个专栏
     * @param id 专栏id
     * @return
     */
    public static void deleteAboutAColumn(Long id){
        SqlSession sqlSession = factory.openSession();
        //先判断是否为默认专栏
        Column column = selectColumnById(id);
        if("默认专栏".equals(column.getName())){
            //默认专栏不给删
        }else{
            //其他专栏删除以后将自动移动到默认专栏当中
            ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
            //拿到默认专栏的id 这个id只需要找到 user的id就可以找到默认专栏了
            Column moRenColumn = columnMapper.selectColumnByNameAndUId("默认专栏",column.getUserId());
            List<BlogColumn> blogColumns = columnMapper.selectBlogColumnsByCId(id);
            //拿到这个专栏里面的每一篇文章id
            for (BlogColumn blogColumn:blogColumns) {
                //通过专栏id 和 博客id 去数据库中查找 这个关系(这篇文章 是否在 默认收藏夹当中)
                //没有找到 说明这个关系需要添加
                //找到了 那就不用添加了 说明它已经存在于默认收藏夹 , 在下面这个业务函数当中以及帮我们处理了以上情况
                addBlogColumn(blogColumn.getBlogId(),moRenColumn.getId());

            }
            columnMapper.deleteColumnByCId(id);
            sqlSession.commit();
            sqlSession.close();
        }

    }



    /**
     * 更新一个专栏
     * @param id
     * @return
     */
    public static void updateColumn(Long id,String name,String introduction,Long userId,String picture){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        columnMapper.deleteBlogColumnByByCId(id);
        Column column = new Column();
        column.setId(id);
        column.setName(name);
        column.setIntroduction(introduction);
        column.setUserId(userId);
        column.setPicture(picture);
        columnMapper.updateColumnById(column);
        sqlSession.commit();
        sqlSession.close();
    }

    public static List<Blog> selectBlogsByCId(int pageSize, int pagenum, Long columnid){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = blogMapper.selectBlogsByCId((pagenum-1)*pageSize,pageSize,columnid);
        sqlSession.close();
        return blogs;
    }

    public static void deleteColumnById(Long id){
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        columnMapper.deleteColumnById(id);
        sqlSession.close();
    }


    /**
     * 通过id 修改Column的关系 和 Columns
     * @param blogid
     * @param ColumnNames
     * @throws Exception
     */
    public static void alterColumn(Long blogid,List<String> ColumnNames) throws Exception{
        //先找到数据库中没有的进行添加
        for (String temp:ColumnNames) {
            if(TagService.selectTagByName(temp)==null){
                TagService.addTag(temp);
            }
        }
        //找到数据库中多余的进行删除
        List<Column> columns =  ColumnService.selectColumnsByBlogId(blogid);
        for (Column column: columns) {
            if(!StringUtil.findString(ColumnNames,column.getName())){
                //如果在数据库拿到的column在本次的tags里面没有找到 那么就可以直接删除
                deleteColumnById(column.getId());
            }
        }
    }

    /**
     * 通过id去找到这个专栏 , 这个主要作用在更新时拉取信息
     * @param columnid
     * @return
     */
    public static Column selectColumnById(Long columnid) {
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        Column column = columnMapper.selectColumnById(columnid);
        sqlSession.close();
        return column;
    }


    /**
     *改变专栏前台是否展示
     * @param columnid
     * @return
     */
    public static void alterColumnShow(Long columnid,Boolean show) {
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        columnMapper.alterColumnShow(columnid,show);
        sqlSession.close();
    }

    /**
     * 根据用户id查询到所有专栏
     */
    public static List<Column>  selectColumnByUserId(Long userid) {
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        List<Column> columns = columnMapper.selectColumnByUserId(userid);
        sqlSession.close();
        return columns;
    }

    /**
     * 根据用户id找到所有专栏 不分页 放在个人区展示
     */
    public static List<Column>  getAllColumnByUserId(Long userid) {
        SqlSession sqlSession = factory.openSession();
        ColumnMapper columnMapper = sqlSession.getMapper(ColumnMapper.class);
        List<Column> columns = columnMapper.getAllColumnByUserId(userid);
        sqlSession.close();
        return columns;
    }


}
