package com.util;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtil {
    //判断字符串是否为空白
    public static boolean isBlank(String s){
        return s==null || "".equals(s);
    }


    //生成一个长度为lenth的字符串(有数字和英文)
    public static String getCharAndNum(int length) {

        Random random = new Random();

        StringBuffer valSb = new StringBuffer();

        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";

        int charLength = charStr.length();



        for (int i = 0; i < length; i++) {

            int index = random.nextInt(charLength);

            valSb.append(charStr.charAt(index));

        }

        return valSb.toString();

    }
    //生成一个长度为lenth的数字串
    public static String getNum(int length) {

        Random random = new Random();

        StringBuffer valSb = new StringBuffer();

        String charStr = "0123456789";

        int charLength = charStr.length();



        for (int i = 0; i < length; i++) {

            int index = random.nextInt(charLength);

            valSb.append(charStr.charAt(index));

        }

        return valSb.toString();
    }

    public static String getUTF_8(String s_ISO_8859_1){
        if(s_ISO_8859_1==null) return null;
        return new String(s_ISO_8859_1.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public static String getTime(){
        Date date = new Date();
//        得到时间戳
        date.getTime();
//        生成指定格式的时间字符串
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = dateFormat.format(date);
        return date2;
    }

    public static Boolean findString(List<String> stringList,String t){
        for (String s:stringList) {
            if (s.equals(t)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isnum(String s,int len)  //判断字符串是否是 纯数字串并且长度不超过 限定的最大值数的长度--过渡用
    {
        Pattern p=Pattern.compile("^[1-9]{1,"+len+"}$");
        return p.matcher(s).matches();
    }

}
