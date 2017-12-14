package com.topie.zhongkexie;

import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.database.core.model.*;
import com.topie.zhongkexie.security.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenguojun on 2017/2/16.
 */
public class AppTest {

    @Test
    public void reg() throws Exception {
        String u = URLDecoder.decode("/?a=b", "UTF-8");
        Pattern p = Pattern.compile("(https?://[^/]+)?(/[^?]*)\\??(.*)");
        Matcher m = p.matcher(u);
        if (m.find()) {
            String host = m.group(1);
            if (StringUtils.isNotEmpty(host)) {
                host = host.replaceAll("https?://", "");
                System.out.println(host);
            }
            String uri = m.group(2);
            System.out.println(uri);

            String[] strings = m.group(3).split("[?&]+");
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }

    @Test
    public void test() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTA2ODQyNjgyNzgwLCJleHAiOjE1MDY5MjkwODIsInVzZXIiOiIxMzEyNjY1ODEwMCJ9.QvT-n5XyKjmlHfJC8gVkaR4bW42Dkt9wNpXMAfZpV2JPZ6WO6BvEHeAGZ_pxkji0iQUwjApiDcFz8wNVoEAVmA";
        TokenUtils tokenUtils = new TokenUtils();
        tokenUtils.setSecret("huaifangplatform!@#$");
        System.out.println(tokenUtils.getUsernameFromToken(token));
    }

    @Test
    public void idNum() throws Exception {
        String idNum = "350822199008151317";

        System.out.println(idNum.substring(idNum.length()-6));
    }


}
