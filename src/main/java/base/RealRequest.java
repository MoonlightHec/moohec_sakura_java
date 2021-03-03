package base;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author lijun
 * @Date 2020年7月2日 17:05:01
 * @Description //TODO
 */

public class RealRequest {

    private static final Logger logger = Logger.getLogger(RealRequest.class);

    /**
     * get请求
     */
    public RealResponse getData(String requestURL, Map<String, String> headerMap) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpURLConnection(requestURL, "GET");
            conn.setDoInput(true);
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    conn.setRequestProperty(key, headerMap.get(key));
                }
            }
            conn.connect();
            return getRealResponse(conn);
        } catch (Exception e) {
            return getExceptionResponse(conn, e);
        }
    }

    /**
     * post请求
     */
    public RealResponse postData(String requestURL, String body, String bodyType, Map<String, String> headerMap) {
        HttpURLConnection conn = null;
        try {
            logger.info("请求地址：+" + requestURL);
            conn = getHttpURLConnection(requestURL, "POST");
            conn.setDoOutput(true);//可读出
            conn.setDoInput(true);//可写入
            conn.setUseCaches(false);//不是有缓存
            conn.setRequestProperty("Content-Type", bodyType);

            if (headerMap != null) {
                //设置请求头，请求头必须放在conn.connect()之前
                for (String key : headerMap.keySet()) {
                    logger.info("请求头信息：" + headerMap.get(key));
                    conn.setRequestProperty(key, headerMap.get(key));
                }
            }
            conn.connect();// 连接，以上所有的请求配置必须在这个API调用之前

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8.name()));
            writer.write(body);
            writer.close();

            return getRealResponse(conn);
        } catch (Exception e) {
            return getExceptionResponse(conn, e);
        }
    }


    /**
     * 得到Connection对象，并进行一些设置
     */
    public HttpURLConnection getHttpURLConnection(String requestURL, String requestMethod) throws IOException {

        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(15 * 1000);
        conn.setRequestMethod(requestMethod);
        return conn;
    }


    /**
     * 当正常返回时，得到Response对象
     */
    public RealResponse getRealResponse(HttpURLConnection conn) {
        RealResponse response = new RealResponse();
        try {
            response.setCode(conn.getResponseCode());
            response.setContentLength(conn.getContentLength());
            response.setInputStream(conn.getInputStream());
            response.setErrorStream(conn.getErrorStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 当发生异常时，得到Response对象
     */
    public RealResponse getExceptionResponse(HttpURLConnection conn, Exception e) {
        if (conn != null) {
            conn.disconnect();
        }
        e.printStackTrace();
        RealResponse response = new RealResponse();
        response.setException(e);
        return response;
    }

    /**
     * get请求，将键值对凭接到url上
     */
    public static String getUrl(String path, Map<String, String> paramsMap) {
        if (paramsMap != null) {
            path = path + "?";
            for (String key : paramsMap.keySet()) {
                path = path + key + "=" + paramsMap.get(key) + "&";
            }
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 得到post请求的body
     */
    public static String getPostBody(Map<String, String> params, String jsonStr) {
        if (params != null) {
            return getPostBodyFormParamsMap(params);
        } else {
            logger.info(jsonStr);
            return jsonStr;
        }
    }

    /**
     * 根据键值对参数得到body
     */
    public static String getPostBodyFormParamsMap(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            logger.info("请求body：" + result.toString());
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 得到bodyType
     */
    public static String getPostBodyType(Map<String, String> paramsMap, String jsonStr) {
        if (paramsMap != null) {
            return "application/x-www-form-urlencoded";
            //return "text/plain";
        } else if (jsonStr != null) {
            return "application/json;charset=utf-8";
        }
        return null;
    }
}
