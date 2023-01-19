// 获得浏览器地址所有参数
function getQueryString() {
    var qs = location.search.substr(1), // 获取url中"?"符后的字串
        args = {}, // 保存参数数据的对象
        items = qs.length ? qs.split("&") : [], // 取得每一个参数项
        item = null,
        len = items.length;
    for(var i = 0; i < len; i++) {
        item = items[i].split("=");
        var name = decodeURIComponent(item[0]),
            value = decodeURIComponent(item[1]);
        if(name) {
            args[name] = value;
        }
    }
    return args;
}


// 将json转为get参数
function getParamByJson(json) {
    return Object.keys(json).map(function (key) {
        return encodeURIComponent(key) + "=" + encodeURIComponent(json[key]);
    }).join("&");
}


/**
 * @author tellsea
 * @version v0.0.1
 * @description javascript # 格式校验工具类
 * @date: 2019/7/5 15:30
 */
var validator = {
    /**
     * 非空验证
     *
     * @param val
     * @returns {boolean}
     */
    isEmpty: function isEmpty(val) {
        if (val == undefined || val == null || val == '' || val.length == 0) {
            return true;
        } else {
            return false;
        }
    },

    /**
     * 邮箱验证
     *
     * @param val
     * @returns {boolean}
     */
    isEmail: function isEmail(val) {
        var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        return reg.test(val);
    },

    /**
     * 身份证号码验证
     *
     * @param val
     * @returns {boolean}
     * 身份证正则表达式(15位):isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
     * 身份证正则表达式(18位):isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
     */
    isCard: function isCard(val) {
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        return reg.test(val);
    },

    /**
     * 手机号码
     *
     * @param val
     * @returns {boolean}
     */
    isPhone: function isPhone(val) {
        var reg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        return reg.test(val);
    },

    /**
     * 固定电话
     *
     * @param val
     * @returns {boolean}
     */
    isLandline: function isLandline(val) {
        var reg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
        return reg.test(val);
    },

    /**
     * 手机号码或者固定电话
     *
     * @param val
     * @returns {boolean}
     */
    isPhoneOrLandline: function isPhoneOrLandline(val) {
        var reg = /^((0\d{2,3}-\d{7,8})|(1[3456789]\d{9}))$/;
        return reg.test(val);
    },

    /**
     * 邮编
     *
     * @param val
     * @returns {boolean}
     */
    isPostalcode: function isPostalcode(val) {
        var reg = /^[1-9][0-9]{5}$/;
        return reg.test(val);
    },

    /**
     * 银行卡号，15,16,19位
     *
     * @param val
     * @returns {boolean}
     */
    isBank: function isBank(val) {
        var reg = /^([1-9]{1})(\d{14}|\d{18}|\d{15})$/;
        return reg.test(val);
    }
};

//数组操作工具类
var arrayUtils = {
    /**
     * 删除数组中的元素
     *
     * @param array 数组
     * @param del 需要删除的元素
     * 1、删除数组中的某个元素，首先需要确定需要获得删除元素的索引值
     * 2、找到相对应的索引值后，根据索引值删除数组中该元素对应的值
     */
    removeElement: function removeElement(arr, del) {
        Array.prototype.indexOf = function (val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == val) {
                    return i;
                }
            }

            return -1;
        };

        Array.prototype.remove = function (val) {
            var index = this.indexOf(val);

            if (index > -1) {
                this.splice(index, 1);
            }
        };

        arr.remove(del);
    },

    /**
     * 根据一个父数组删除子数组相对应的元素
     *
     * @param arr1 父数组
     * @param arr2 子数组
     * @returns {*}
     */
    removePoint: function removePoint(arr1, arr2) {
        for (var i = 0; i < arr2.length; i++) {
            for (var j = 0; j < arr1.length; j++) {
                if (arr2[i] == arr1[j]) {
                    var index = arr1.indexOf(arr1[j]);
                    arr1.splice(index, 1);
                }
            }
        }

        return arr1;
    }
};


/**
 * 序列化表单数据
 */
$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
};


/**
 * 把Date转化为指定格式的String（Format）
 * @param fmt
 * @returns {void | string}
 * @constructor
 * 使用案例：new Date().Format('yyyy-MM-dd HH:mm:ss');
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * 得到当前时间 yyyyMMddHHmm
 */
function fmtDateMin() {
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth() + 1;
    var date = myDate.getDate();
    var h = myDate.getHours();
    var m = myDate.getMinutes();
    var s = myDate.getSeconds();
    var now = year + '' + conver(month) + '' + conver(date) + '' + conver(h) + '' + conver(m);
    return now;
}

//日期时间处理
function conver(s) {
    return s < 10 ? '0' + s : s;
}


function array(n) {
    for (i = 0; i < n; i++) this[i] = 0;
    this.length = n;
}


/**
 * 上传文件
 * @param inputId   输入框id
 * @param folder    保存文件夹名称
 * @param url       上传路径
 */
function uploadFile(inputId, folder, url) {
    var res = {
        code: 0,
        message: '',
        data: {}
    };
    var formData = new FormData();
    var length = $('#' + inputId)[0].files.length;
    if (0 == length) {
        res.code = 404;
        res.message = '文件为空，请选择文件';
    }
    for (var i = 0; i < $('#' + inputId)[0].files.length; i++) {
        formData.append('file', $('#' + inputId)[0].files[i]);
    }
    formData.append('folder', folder);
    var defer = $.Deferred();
    $.ajax({
        method: 'post',
        url: url,
        type: 'POST',
        data: formData,
        dataType: 'json',
        processData: false,
        contentType: false,
        async: true,
        success: function (result) {
            res.code = result.code;
            res.message = result.message;
            res.data = result.data;
            defer.resolve(res)
        },
        error: function (result) {
            res.code = result.code;
            res.message = result.message;
            res.data = result.data;
            defer.resolve(res)
        }
    });
    return defer;
}
