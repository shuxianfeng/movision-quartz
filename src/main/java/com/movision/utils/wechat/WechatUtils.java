package com.movision.utils.wechat;

import com.movision.utils.MD5Util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2018/3/8 14:21
 * 微信小程序后台开发过程中的所有静态工具类
 */
public class WechatUtils {

    public static String map2XmlString(Map<String, Object> map) throws UnsupportedEncodingException {
        String xmlResult;

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : map.keySet()) {
            System.out.println(key + "========" + map.get(key));

            String value = "<![CDATA[" + map.get(key) + "]]>";
            sb.append("<" + key + ">" + value + "</" + key + ">");
            System.out.println();
        }
        sb.append("</xml>");
        xmlResult = sb.toString();
        return xmlResult;
    }

    public static String getSign(String str){

        String sign = MD5Util.MD5(str).toUpperCase();

        return sign;
    }
}
