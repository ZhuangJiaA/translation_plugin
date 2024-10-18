package com.southpaw.cloud.translation_plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private static final String APP_ID = "20240630002088482";
    private static final String SECURITY_KEY = "gZL1kGy57yHpUyZa3pHC";

    private static TransApi api = new TransApi(APP_ID, SECURITY_KEY);

    //中译英
    public static String zhToen(String q){
        String result =  api.getTransResult(q, "auto", "en");
        int begin = result.indexOf("dst");
        String en = result.substring(begin + 6, result.length() - 4);
        System.out.println(en);
        return en;

    }
    //英译中
    public static String enTozh(String q){
        String result =  api.getTransResult(q,"en","zh");
        int begin = result.indexOf("dst");
        String zh = result.substring(begin + 6, result.length() - 4);
//        System.out.println(zh);
        return convertUnicodeToCh(zh);
    }

    private static String convertUnicodeToCh(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\w{4}))");
        Matcher matcher = pattern.matcher(str);

        // 迭代，将str中的所有unicode转换为正常字符
        while (matcher.find()) {
            String unicodeFull = matcher.group(1); // 匹配出的每个字的unicode，比如\u67e5
            String unicodeNum = matcher.group(2); // 匹配出每个字的数字，比如\u67e5，会匹配出67e5

            // 将匹配出的数字按照16进制转换为10进制，转换为char类型，就是对应的正常字符了
            char singleChar = (char) Integer.parseInt(unicodeNum, 16);

            // 替换原始字符串中的unicode码
            str = str.replace(unicodeFull, singleChar + "");
        }
        return str;
    }


    public static void main(String[] args) {
        System.out.println(enTozh("What's good for dinner tonight?"));
    }
}