package util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUtil {


    private static final Logger logger = Logger.getLogger(SignUtil.class);

    /**
     * 获取签名sign
     *
     * @param params 请求头参数map
     * @return sign
     */
    public static String sign(Map<String, String> params) {

        Map<String, List<String>> signParams = new HashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String jsonStr;
            Object obj = entry.getValue();
            if (obj instanceof String) {
                jsonStr = entry.getValue();
            } else {
                jsonStr = String.valueOf(obj);
            }
            signParams.put(entry.getKey(), Collections.singletonList(jsonStr));
        }
        String paramStr = ListUtils.n(signParams.keySet()).order(key -> key).list(key -> {
            String values = ListUtils.n(signParams.get(key)).order(val -> val).join();
            return key + values;
        }).join();

        paramStr += "secret12345678";
        //logger.info(paramStr);
        String sign = DigestUtils.md5Hex(paramStr);
        //logger.info(sign);
        return sign;
    }

    public static String sign(String json) {

        Map map = JSON.parseObject(json);
        return sign(map);
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("afasf*^d#&^h213sa152{\"provider_sn\":\"OEM0047\",\"sku\":\"206015610\"}"));
        System.out.println(MyMD5("afasf*^d#&^h213sa152{\"provider_sn\":\"OEM0047\",\"sku\":\"206015610\"}"));
    }

    /**
     * MD5加密
     *
     * @param str 原数据
     * @return 加密数据
     */
    public static String MyMD5(String str) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

}
