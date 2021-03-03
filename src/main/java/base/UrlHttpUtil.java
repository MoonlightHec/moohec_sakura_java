package base;

import java.util.Map;

/**
 * @Author lijun
 * @Date 2020年7月2日 17:05:01
 * @Description //TODO
 */

public class UrlHttpUtil extends RealRequest {

    /**
     * get请求，可以传递参数，带请求头
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     */
    public static RealResponse urlHttpGet(String url, Map<String, String> paramsMap, Map<String, String> headerMap) {
        return new RealRequest().getData(getUrl(url, paramsMap), headerMap);

    }

    /**
     * get请求
     *
     * @param url：url
     */
    public static RealResponse urlHttpGet(String url) {
        return urlHttpGet(url, null, null);
    }

    /**
     * get请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     */
    public static RealResponse urlHttpGet(String url, Map<String, String> paramsMap) {
        return urlHttpGet(url, paramsMap, null);
    }


    /**
     * post请求
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param jsonStr：json格式字符串参数
     * @param headerMap：map集合，封装请求头键值对
     * @return RealResponse
     */
    public static RealResponse urlHttpPost(String url, Map<String, String> paramsMap, String jsonStr, Map<String, String> headerMap) {
        return new RealRequest().postData(url, getPostBody(paramsMap, jsonStr), getPostBodyType(paramsMap, jsonStr), headerMap);
    }

    /**
     * post请求
     *
     * @param url：url
     */
    public static RealResponse urlHttpPost(String url) {
        return urlHttpPost(url, null, null, null);
    }

    /**
     * post请求，键值对参数，不带请求头
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     */
    public static RealResponse urlHttpPost(String url, Map<String, String> paramsMap) {
        return urlHttpPost(url, paramsMap, null, null);
    }

    /**
     * post请求，键值对参数，带请求头
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     */
    public static RealResponse urlHttpPost(String url, Map<String, String> paramsMap, Map<String, String> headerMap) {
        return urlHttpPost(url, paramsMap, null, headerMap);
    }

    /**
     * post请求，json参数,不带请求头
     *
     * @param url：url
     * @param jsonStr：json格式字符串参数
     */
    public static RealResponse urlHttpPost(String url, String jsonStr) {
        return urlHttpPost(url, null, jsonStr, null);
    }

    /**
     * post请求，json参数，带请求头
     *
     * @param url：url
     * @param jsonStr：json格式字符串参数
     * @param headerMap：map集合，封装请求头键值对
     */
    public static RealResponse urlHttpPost(String url, String jsonStr, Map<String, String> headerMap) {
        return urlHttpPost(url, null, jsonStr, headerMap);
    }

}
