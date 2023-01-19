package com.web.Servlet;

import com.service.UserListService;
import com.util.ImageWatermarkUtils;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/FileServlet")
public class FileServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";
    private static final String md = "md:";
    public String Method01(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

    public String Method02(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

    public String Method03(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

    /**
     * 该方法用于解决博客中加入本地图片的问题
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String BlogImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        DiskFileItemFactory sf= new DiskFileItemFactory();//实例化磁盘被文件列表工厂
        String path = "D:\\MyBlogWeb\\BlogImg";
        System.out.println("path: "+path);
        sf.setRepository(new File(path));//设置文件存放目录
        sf.setSizeThreshold(1024*1024);//设置文件上传小于1M放在内存中
        String rename = "";//文件新生成的文件名
        String fileName = "";//文件原名称
        String name = "";//普通field字段
        //从工厂得到servletupload文件上传类
        ServletFileUpload sfu = new ServletFileUpload(sf);

        try {
            List<FileItem> lst = sfu.parseRequest(request);//得到request中所有的元素
            for (FileItem fileItem : lst) {
                if(fileItem.isFormField()){
                    if("name".equals(fileItem.getFieldName())){
                        name = fileItem.getString("UTF-8");
                    }
                }else{
                    //获得文件名称
                    fileName = fileItem.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
                    String houzhui = fileName.substring(fileName.lastIndexOf("."));
                    rename = UUID.randomUUID()+houzhui;
                    fileItem.write(new File(path, rename));
                    //为图片添加url水印
                    System.out.println("水印地址:"+"D:\\MyBlogWeb\\BlogImg\\" + rename);
                    ImageWatermarkUtils.markImageByText(path+rename,"D:\\MyBlogWeb\\BlogImg\\"+rename,"D:\\MyBlogWeb\\BlogImg\\"+rename,0);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = "http://localhost:8080/MyBlogWebFile/BlogImg/" +rename;
        String s = "{\"success\": 1," +
                " \"message\":\"上传成功\"," +
                " \"url\":"+"\""+url+"\""+
                "}";
        return md + s;
    }
    //上传用户头像
    public String UserAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("收到了头像上传请求...");
        Map<String,Object> mp = new HashMap<>();
        System.out.println("method:"+request.getMethod());
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // 保存到服务器的路径
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建一个文件上传的处理器
            ServletFileUpload upload = new ServletFileUpload(factory);
            String baseDir = "D:\\MyBlogWeb\\BlogImg";
            try {
                List<FileItem> items = upload.parseRequest(request);
                if(items==null){
                    System.out.println("items is null");

                }
                assert items != null;
                items.forEach((item) -> {
                    //如果是普通表单项,拿到id
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        //控制台打印浏览器提交的参数
                        System.out.println(name + ":" + value);
                        if(name.equals("id")){
                            mp.put("id",value);
                        }
                    }
                });
                items.forEach((item) -> {
                    if(mp.get("id")==null){
                        return;
                    }
                    //如果不是普通表单项
                    if (!item.isFormField()) {
                        //如果是文件项,写入到本地服务器
                        try {
                            if(mp.get("id")==null || mp.get("id")==""){
                                mp.put("msg","上传失败！");
                                return;
                            }
                            //获取文件名
                            String name = item.getName();
                            String suffix = name.substring(name.lastIndexOf("."));
                            //生成一个随机文件名
                            UUID uuid = UUID.randomUUID();
                            String newFileName = uuid.toString().replaceAll("-", "").toUpperCase() + suffix;
                            //item.write(new File(getServletContext().getRealPath("/repository"), newFileName));
                            item.write(new File(baseDir, newFileName));
                            mp.put("url","http://localhost:8080/MyBlogWebFile/BlogImg/"+newFileName);
                            //更新数据库
                            UserListService.AlterAvatar(new Long((String)mp.get("id")),"http://localhost:8080/MyBlogWebFile/BlogImg/"+newFileName);
                            mp.put("msg","上传成功！");
                        } catch (Exception e) {
                            mp.put("msg","上传失败！");
                            e.printStackTrace();
                        }
                    }
                });
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("no");
        }
        return axios + new JSONObject(mp).toString();
    }

    //上传博客封面图片
    public String uploadCover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("收到了博客封面上传请求...");
        Map<String,Object> mp = new HashMap<>();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // 保存到服务器的路径
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建一个文件上传的处理器
            ServletFileUpload upload = new ServletFileUpload(factory);
            String baseDir = "D:\\MyBlogWeb\\BlogImg";
            try {
                List<FileItem> items = upload.parseRequest(request);
                if(items==null){
                    System.out.println("items is null");
                }
                assert items != null;
                items.forEach((item) -> {
                    //如果不是普通表单项
                    if (!item.isFormField()) {
                        //如果是文件项,写入到本地服务器
                        try {
                            //获取文件名
                            String name = item.getName();
                            String suffix = name.substring(name.lastIndexOf("."));
                            //生成一个随机文件名
                            UUID uuid = UUID.randomUUID();
                            String newFileName = uuid.toString().replaceAll("-", "").toUpperCase() + suffix;
                            //item.write(new File(getServletContext().getRealPath("/repository"), newFileName));
                            item.write(new File(baseDir, newFileName));
                            mp.put("url","http://localhost:8080/MyBlogWebFile/BlogImg/"+newFileName);
                            mp.put("msg","上传成功！");
                        } catch (Exception e) {
                            mp.put("msg","上传失败！");
                            e.printStackTrace();
                        }
                    }
                });
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("no");
        }
        return axios + new JSONObject(mp).toString();
    }



}
