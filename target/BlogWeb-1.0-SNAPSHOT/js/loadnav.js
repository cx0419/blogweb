//引入bootstrap的csss
var css = document.createElement('link');
css.rel = 'stylesheet';
css.href = 'css/Bootstrap.css';
document.head.appendChild(css);
var css = document.createElement('link');
css.rel = 'stylesheet';
css.href = 'iconfont/iconfont.css';
document.head.appendChild(css);
document.write('<script src="js/axios-0.18.0.min.js" type="text/javascript" charset="utf-8"></script>');
document.write('<script src="js/axiosUtil.js" type="text/javascript" charset="utf-8"></script>');
// 获取指定名称的cookie
function getCookie(name){
    var strcookie = document.cookie;//获取cookie字符串
    var arrcookie = strcookie.split("; ");//分割
    //遍历匹配
    for ( var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == name){
            return arr[1];
        }
    }
    return "";
}
/**
 * 获取url当中的key->value
 * @param paraName
 * @returns {string}
 * @constructor
 */
function GetUrlParam(paraName) {
    var url = window.location.toString();
    var arrObj = url.split("?");

    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");

            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}
//警告
function popSucceedWindows(msg){
    let bodyEle = document.body;
    let str = "\t<button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
        "\t\t\taria-hidden=\"true\">\n" +
        "\t\t&times;\n" +
        "\t</button>"+msg;
    let div = document.createElement('div');
    div.classList.add("alert");
    div.classList.add("alert-success");
    div.classList.add("alert-dismissable");
    div.classList.add("alertbyBootSt");
    div.innerHTML = str;
    // $("body").append(div);
    document.body.insertBefore(div, document.body.firstElementChild);
    setTimeout(e =>{
        div.remove();
    },3*1000);
}
//警告
function popErrorWindows(msg){
    let bodyEle = document.body;
    let str = "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
        "\t\t\taria-hidden=\"true\">\n" +
        "\t\t&times;\n" +
        "\t</button>"+msg;
    let div = document.createElement('div');
    div.classList.add("alert");
    div.classList.add("alert-danger");
    div.classList.add("alert-dismissable");
    div.classList.add("alertbyBootSt");
    div.innerHTML = str;
    $("body").append(div);
    setTimeout(e =>{
        div.remove();
    },3*1000);
}

Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function getnavinfo(){
    if(getCookie("id")===""){

        document.getElementById("nav-nickname").innerHTML = "请登录";
        return;
    }
    AxiosPostRequst("http://localhost:8080/BlogWeb/UserServlet?method=getUser",{
        'account': getCookie("account"),
    }).then(res => {
        let user = res.user;
        let userExtend = res.userExtend;
        document.getElementById("avatar").src = user.avatar;
        document.getElementById("nav-nickname").innerHTML = user.name;
        document.getElementById("nav-fansnum").innerHTML = userExtend.fans;
        document.getElementById("nav-view").innerHTML = userExtend.view;
        document.getElementById("nav-likenum").innerHTML = userExtend.like;
        document.getElementById("nav-introduction").innerHTML = user.introduction;
        //修改跳转页面
        //个人中心,去个人中心的个人资料需要设置url 的参数 id
        document.getElementById("personalCenter").setAttribute("href","http://localhost:8080/BlogWeb/personalCenter.html?page=person&id="+user.id);
        //去个人中心的修改密码
        document.getElementById("AlterPassword").setAttribute("href","http://localhost:8080/BlogWeb/personalCenter.html?page=alterpassword&id="+user.id);
        //粉丝
        document.getElementById("AlterPassword").setAttribute("href","http://localhost:8080/BlogWeb/personalCenter.html?page=alterpassword&id="+user.id);
        //关注
        document.getElementById("AlterPassword").setAttribute("href","http://localhost:8080/BlogWeb/personalCenter.html?page=alterpassword&id="+user.id);
        //收藏
        document.getElementById("AlterPassword").setAttribute("href","http://localhost:8080/BlogWeb/personalCenter.html?page=collection&id="+user.id);
        //去内容管理
        document.getElementById("createBlog").setAttribute("href","http://localhost:8080/BlogWeb/createBlog.html?id="+user.id);
        //去文档编辑发布区, 需要携带参数博客id,当参数为o时代表了文档为修改状态
        document.getElementById("edit").setAttribute("href","http://localhost:8080/BlogWeb/edit.html");
        //退出登录
        $('#exitlogin').on('click',e=>{
            AxiosPostRequst("http://localhost:8080/BlogWeb/LoginServlet?method=exitLogin",{

            }).then(res=>{
                popSucceedWindows("退出登录成功");
            });
        })
        //给搜索栏添加搜索事件
        $('#nav-searchbtn').on('click', e => {
            window.location.replace("http://localhost:8080/BlogWeb/search.html?key="+document.getElementById("searchsomething").value);
        });


    });
}
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

    function replaceParamVal(url,paramName,replaceVal) {
    var oUrl = url.toString();
    var re=eval('/('+ paramName+'=)([^&]*)/gi');
    var nUrl = oUrl.replace(re,paramName+'='+replaceVal);
    return nUrl;
}
//将某个类中所有元素隐藏 显示某个指定元素
function hiddenClassElementShowOneOfThem(cls,elid,st){
    let list = document.getElementsByClassName(cls);
    let rel  = [];
    let i;
    for (i = 0; i < list.length; ++i)
    {
        let jqueryobj = $(list[i]);
        jqueryobj.css("display",'none');

    }
    let jqueryobj = $('#'+elid);
    jqueryobj.css("display",st);
}
//还将执行指定操作
function hiddenClassElementShowOneOfThem_f(cls,elid,st,otherfunction){
    let list = document.getElementsByClassName(cls);
    let rel  = [];
    let i;
    for (i = 0; i < list.length; ++i)
    {
        let jqueryobj = $(list[i]);
        jqueryobj.css("display",'none');

    }
    let jqueryobj = $('#1111');
    jqueryobj.css("display",st);
    otherfunction();
}
//将指定元素加入到一个div的第一个元素
function adddivtofirst(el1,el2){
    el1.insertBefore(el2, el1.children[0]);
}
function checknum(str){
    let patt=/^\d{1,8}$/;	// 表示字符串长度在6-12之间，且必须是字母、数字、下划线组成
    return patt.test(str);
}