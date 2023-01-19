
/**使用方法：
 AxiosPostRequst("http://localhost:8080/BlogWeb/registerServlet",{
           "json":JSON.stringify(json),
            "ps": "中文"
 }).then(res => {
    //拿到data...
 });
 * axios封装post请求
 * @param url
 * @param data
 * @returns {Promise<*>}
 * @constructor
 */
async function AxiosPostRequst(url, data) {
    let ans;
    await axios({
        url: url,
        method: 'post',
        data: data,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + "&";
            }
            return ret;
        }],
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(resp => {
        ans = resp.data;
        //alert("util-in:" + resp.data.msg);
        return resp.data;
    }).catch(error => {
        ans = "exception=" + error;
        return "exception=" + error;
    });
    return ans;
}
async function handleUploadFile(file,url) {
    let ans;
    let formData = new FormData();
    formData.append('files', file)
    const res = await axios({
        url: url,
        method: 'POST',
        transformRequest: [function(data, headers) {
            // 去除post请求默认的Content-Type
            delete headers.post['Content-Type']
            return data
        }],
        data: formData
    }).then(res=>{
        ans = res.data;
    })
    console.log(res.data);
    return ans;
}

//get请求
async function axiosGetRequst(url) {
    let ans;
    await axios({
        method: 'get',
        url: url,
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(function (resp) {
        ana = resp.data;
        return resp.data;
    }).catch(function (error) {
        ans = "exception=" + error
        return "exception=" + error;
    });
    return ans;
}

