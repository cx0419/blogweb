package com.web.Servlet;

import com.pojo.User;
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

import static com.util.WebUtil.getObjectParameter;
import static com.util.WebUtil.getParameter;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";
    Map<String,String> VC = new HashMap<>(); //email->verificationcode
    Map<String,String> VCtoken = new HashMap<>(); //email->token
    /**
     * 更新个人资料
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String UpdateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        User user = getObjectParameter(request, "json", User.class);
        //数据库保存user对象
        UserListService.UpdateInfo(user);
        mp.put("msg", "修改资料成功");
        return axios + new JSONObject(mp).toString();
    }

    //修改密码
    public String AlterPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入修改密码...");
        HashMap<String, Object> mp = new HashMap<>();
        String account = getParameter(request, "account");
        String password = getParameter(request, "password");
        String email = getParameter(request, "email");
        String verificationcode = getParameter(request, "verificationcode");

        if(checkVC(email,verificationcode)){
            User user = UserListService.selectByAccount(account);
            if(user==null || !user.getEmail().equals(email)){
                mp.put("msg","该用户不存在");
            }else{
                UserListService.AlterPassword(account, password);
                mp.put("msg","修改成功！");
                //将验证信息全部消除
                VC.remove(email);
                VCtoken.remove(email);
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
        //先来验证验证码的有效期
        if(token!=null && JwtUitls.verifyVCToken(token)){
            //验证码是否正确
            if(verificationcode!=null && verificationcode.equals(vc)){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }
    //发送修改密码的验证码
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

            SendEmail.sendAlterPasswordEmail(email, vc);
            //生成验证码的有效期
            VCtoken.put(email, JwtUitls.createToken(email));
            mp.put("msg","验证码发送成功！");
        }
        return axios+new JSONObject(mp).toString();
    }



}
