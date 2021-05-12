package com.hqyg.pay;

import base.RealResponse;
import base.UrlHttpUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 lijun:
 * @Date 2021年05月12日 17:33:21
 * @ClassName GetRate
 * @Description 支付获取XE汇率
 * @Version 1.0
 */
public class GetRate {

    @Test
    public void getXERate() {
        String interfaceStr = "https://www.xe.com/api/protected/midmarket-converter/";

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Basic bG9kZXN0YXI6REx1OHhOQ1ZrWkpSeHk3ck91R1hDQ2l2d0U2cXd4c1Q=");

        RealResponse response = UrlHttpUtil.urlHttpGet(interfaceStr, null, headerMap);
        System.out.println(response.is2String(response.getInputStream()));
    }
}
