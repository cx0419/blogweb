package com.util;


//import org.apache.commons.mail.HtmlEmail;


import org.apache.commons.mail.HtmlEmail;

public class SendEmail {
    public static boolean sendRegisterEmail(String to, String test) {

        if(to!=null){
            System.out.println("验证码为："+test);
            return false;
        }
        //发件人发给发件人时直接return
        if(to.equals("3207242935@qq.com")){
            return false;
        }
        try {
            //创建网页邮箱对象
            HtmlEmail email = new HtmlEmail();

            //基本设置
            email.setDebug(true);

            //设置为QQ邮箱作为发送主邮箱
            email.setHostName("SMTP.qq.com");
            email.setSmtpPort(587);

            //qq邮箱的验证信息
            email.setAuthentication("3207242935@qq.com", "iwrvxtsjxhdydggc");

            //设置邮件发送人
            email.setFrom("3207242935@qq.com");

            //设置中文
            email.setCharset("gbk");

            //设置邮件接收人
            email.addTo(to);

            //设置发送的内容
            email.setMsg("尊敬的用户:"+to+",您本次申请注册的的验证码为:"+test);

            //设置邮箱标题
            email.setSubject("注册申请");

            //执行邮件发送
            email.send();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean sendAlterPasswordEmail(String to, String test) {
        if(to!=null){
            System.out.println("验证码为："+test);
            return false;
        }
        //发件人发给发件人时直接return
        if(to.equals("3207242935@qq.com")){
            return false;
        }
        try {
            //创建网页邮箱对象
            HtmlEmail email = new HtmlEmail();

            //基本设置
            email.setDebug(true);

            //设置为QQ邮箱作为发送主邮箱
            email.setHostName("SMTP.qq.com");
            email.setSmtpPort(587);

            //qq邮箱的验证信息
            email.setAuthentication("3207242935@qq.com", "iwrvxtsjxhdydggc");

            //设置中文
            email.setCharset("gbk");
            //设置邮件发送人
            email.setFrom("3207242935@qq.com");

            //设置邮件接收人
            email.addTo(to);

            //设置发送的内容
            email.setTextMsg("华氏度");
            email.setMsg("尊敬的用户:"+to+",您本次修改密码的的验证码为:"+test);

            //设置邮箱标题
            email.setSubject("修改密码");

            //执行邮件发送
            email.send();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 测试qq邮箱发送
     * @param
     */
    public static void main(String[] args) {
        boolean b = sendRegisterEmail("1299459723@qq.com","123");
        System.out.println("发送"+(b?"成功":"失败"));
    }
}