package com.web.Servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
/**
 * BaseServlet[抽象类] 不需要在web.xml中添加路径 该类实现一个servlet对应多个请求， 实现原理：
 * 在get和post的url当中增加method参数，如果是get方法method需要放入到隐藏域当中，
 * 在传递给后端时 后端利用反射获取本类的字节码文件 再调用它的getMethod方法 利用Method实例的
 * invoke执行该方法 在必要时将数据进行异步交互 转发和重定向
 * @功能 1.
 * @author
 *
 */
public abstract class BaseServlet extends HttpServlet {
    //基于反射封装的的baseServlet

    private static final long serialVersionUID = 1L;


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");//处理响应编码
        /*
         * 设置POST和GET请求编码
         */
        /*
         * 1. 获取参数，用来识别用户想请求的方法
         *      method是用户在浏览器地址栏中输入的想要调用的方法，是个参数
         */
        String methodName = request.getParameter("method");
        //2. 判断用户有没有传入参数。
        if(methodName == null || methodName.trim().isEmpty()){
            throw new RuntimeException("您没有传递method参数! 无法确定您要调用的方法！");
        }
        /**
         * 3. 判断是哪一个方法，是哪一个就调用哪一个
         *    所用的技术是： 反射
         *        需要得到本类Class,然后调用它的getMethod，通过传递过来的方法名字进行得到Method对象
         *    这样做的目的是： 以后像其他Servlet中的方法要修改或者添加新方法时，就不用在去修改这一块代码了
         */
        Method method = null ;
        try {
            /**
             * method的结果:public java.lang.String cn.kgc.servlet.client.loOrreServlet.login(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse) throws javax.servlet.ServletException,java.io.IOException
             * 如下这句代码请注意两点：
             * 1.因为getClass()在Object类中定义成了final，子类不能重写该方法，
             * 所以这里的this调用的是Object类中的getClass()方法，等价于super，
             * ( getClass()方法返回值为当前运行时类的Class对象，
             * 所以 getClass()不受this和super影响，而是有当前的运行类决定的。)
             * 2.getMethod()方法只能获取public修饰的方法，所以继承此类的servlet中的方法的访问修饰符必须是 public的
             * 若想访问所有的方法，则把getMethod改成getDeclaredMethod
             *
             */
            //3.1获取method方法
            method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("您要调用的方法"+methodName+",它不存在");
        }
        try {
            /*
             * 3.2. 调用method表示的方法,即： 调用继承该类的类中的方法
             *     反射调用： 用this来调用method表示的方法，并且传递参数req和resp
             *     result的返回值，是由继承此类的返回值决定，例如：return "f:/pages/user/login_success.jsp";
             */
            String result =(String)method.invoke(this, request,response);
            System.out.println("本次请求返回给前端的结果:"+result);
            /**
             * 4. 处理从继承这个类的类中返回的字符串（重定向和转发）
             *     return "r:/index.jsp"; 和 return "f:/index.jsp";
             *      返回的是字符串，需要解读字符串
             */
            /*
             * 4.1. 如果用户返回的字符串为null，或者"",那么什么都不做
             */
            if(result == null || result.trim().isEmpty()){
                return  ;
            }
            /*
             * 4.2. 解读字符串1:判断字符串中有没有冒号
             *        没有冒号默认表示转发，反之再进行判断
             */
            if(result.contains(":")){
                /*
                 * 4.3. 解读字符串2 ： 先获取冒号位置，然后截取前缀（操作，是重定向还是转发）和后缀（路径）
                 */
                int index = result.indexOf(":");
                String operate = result.substring(0,index);
                String pathOrData = result.substring(index+1);
                /*
                 * 4.4. 进行处理，如果是r重定向，如果是f则转发 a则是axios异步请求写入数据
                 */
                if(operate.equalsIgnoreCase("r")){
                    //重定向： 浏览器再一次发送请求
                    response.sendRedirect(request.getContextPath()+pathOrData);
                }else if(operate.equalsIgnoreCase("f")){
                    //转发： 资源切换由服务器来指向 切换页面时
                    request.getRequestDispatcher(pathOrData).forward(request, response);
                }else if(operate.equalsIgnoreCase("a")){
                    //异步请求：局部响应
                    response.setContentType("application/json");
                    //设置响应的编码
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(pathOrData);
                }else if(operate.equalsIgnoreCase("md")){
                    //md图片
                    response.setContentType("text/html");
                    response.getWriter().write(pathOrData);
                }else{
                    throw new RuntimeException("您指定的操作"+operate+
                            "不支持，请正确填写：r和f,a");
                }
            }else{
                /*
                 * 没有冒号默认转发处理
                 */
                request.getRequestDispatcher(result).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("您要调用的方法"+methodName+",它内部抛出了异常");
            throw new RuntimeException(e);
        }
    }
}
/*
get
<form action="${pageContext.request.contextPath}/loOrreServlet?method=login" method="get">
<!-- 添加表单隐藏域-->
<input  type="hidden"  name="method"  value="login"/>
</form>


post
<form action="${pageContext.request.contextPath}/loOrreServlet?method=login" method="post">
<!-- INPUT框信息 -->
</form>
*/
