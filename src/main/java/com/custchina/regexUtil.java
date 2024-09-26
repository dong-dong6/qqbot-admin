package com.custchina;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2024/9/24
 * 栋dong
 */

public class regexUtil {
    public static String kickRegex = "踢\\[CQ:at,qq=\\d+,name=.*\\]";//踢人命令

    public static String getQQRegex = "qq=(\\d+)";//获取QQ号

    public static boolean checkKick(String kick) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(kickRegex);

        // 创建匹配器
        Matcher matcher = pattern.matcher(kick);
        // 判断是否匹配
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

//    public static String getQQid(String rawMassage) {
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile(getQQRegex);
//
//        // 创建匹配器
//        Matcher matcher = pattern.matcher(rawMassage);
//        // 查找并提取 qq 号
//        if (matcher.find()) {
//            System.out.println(matcher.group(1));
//            return matcher.group(1); // 返回匹配到的第一个捕获组 (qq 号)
//        } else {
//            return null; // 未找到符合条件的 qq 号
//        }
//    }
}
