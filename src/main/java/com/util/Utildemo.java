package com.util;

public class Utildemo {
    public static void main(String[] args) {
        String qrCodeUrl = "http://m.xinjuenet.com/images/banner/i-wx.jpg"; //二维码
        String goodsUrl = "http://m.xinjuenet.com/images/banner/b-xldh.jpg"; //顶部图片
        String avatarUrl = "http://images.xinjuenet.com//20190529140101.jpg"; //头像
        String name = "和自己对话";
        String desc = "沉默王二1，《Web 全栈开发进阶之路》作者；一个不止写代码的程序员，还写有趣有益的文字，给不喜欢严肃的你。沉默王二，《Web 全栈开发进阶之路》作者；一个不止写代码的程序员，还写有趣有益的文字，给不喜欢严肃的你。";
        String price = "重度抑郁1";

        Poster poster_ = new Poster();
        poster_.setWidth(375);
        poster_.setHeight(670);
        poster_.setQrCodeUrl(qrCodeUrl); //二维码
        poster_.setGoodsUrl(goodsUrl); //顶部banner
        poster_.setDesc(desc); //测评结果说明
        poster_.setPrice(price); //测评结果标题
        Poster poster = PosterUtil.initPoster(poster_);
        try {
            PosterUtil.drawPoster(poster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
