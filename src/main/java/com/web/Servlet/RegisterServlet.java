package com.web.Servlet;

import com.pojo.Collection;
import com.service.CollectionService;
import com.service.ColumnService;
import com.service.UserListService;
import com.util.JwtUitls;
import com.util.SendEmail;
import com.util.StringUtil;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.util.WebUtil.getParameter;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";
    public static HashMap<String,String> VC = new HashMap<>();
    public static HashMap<String,String> VCtoken = new HashMap<>();

    /**
     * 注册方法
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String Register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> mp = new HashMap<>();
        String email = getParameter(request, "email");
        String verificationcode =getParameter(request, "verificationcode");
        String name = getParameter(request, "name");
        String password = getParameter(request, "password");
        System.out.println("email:"+email);
        if(checkVC(email,verificationcode)){
            if(UserListService.selectByEmail(email)==null) {
                mp.put("msg", "验证码正确");
                String account = UserListService.addUser(name, password, email);
                mp.put("account", account);
                Long userid = UserListService.selectByAP(account, password).getId();
                //为该用户创建默认专栏 和 默认 收藏
                ColumnService.addColumn(name,"",null,true,userid);
                Collection collection = new Collection();
                collection.setName("默认收藏夹");
                collection.setUserId(userid);
                collection.setIsdelete(false);
                collection.setCreateTime(StringUtil.getTime());
                CollectionService.addCollection(collection);
                //返回json
            }else{
                mp.put("msg", "该邮箱已经被注册！");
            }
        }else{
            mp.put("msg", "验证码错误或为空");
        }
        return axios+new JSONObject(mp).toString();
    }


    /**
     * 私有方法 判断此验证码是否有效
     * @param email
     * @param vc
     * @return
     */
    private boolean checkVC(String email,String vc){
        String token = VCtoken.get(email);
        String verificationcode = VC.get(email);
        if(token!=null && JwtUitls.verifyVCToken(token)){
            //验证吗有效
            if(verificationcode!=null && verificationcode.equals(vc)){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 发送验证码
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String SendVC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入发送验证码");
        HashMap<String,Object> mp = new HashMap<>();
        String email = getParameter(request, "email");
        String token = VCtoken.get(email);
        if(token!=null && JwtUitls.verifyVCToken(token)){
            //此验证码还没有过时，不让继续发
            mp.put("msg","验证码仍有效");
        }else{
            String vc = StringUtil.getCharAndNum(4);
            //生成验证码
            VC.put(email, vc);
            //将验证码发送给该邮箱
            SendEmail.sendRegisterEmail(email, vc);
            //生成验证码的有效期
            VCtoken.put(email, JwtUitls.createToken(email));
            mp.put("msg","验证码发送成功！");
        }
        return axios+new JSONObject(mp).toString();
    }





    public String Method03(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        return null;
    }

}
