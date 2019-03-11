package com.dreamf;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/11 0011 15:19
 * @Description:
 */
public class StringUtils {
    /**
     * 判断指定字符串是否为空
     *
     * @param _string 指定的字符串
     * @return 若字符串为空对象（_string==null）或空串（长度为0），则返回true；否则，返回false.
     */
    public static boolean isEmpty(Object _string) {
        return (_string == null || (_string.toString().trim().length() == 0));
    }

    /**
     * 判定非空
     *
     * @param _string
     * @return
     */
    public static boolean isNotEmpty(Object _string) {
        return (!isEmpty(_string));
    }

    /**
     * 获取double
     *
     * @param _strSrc
     * @return
     */
    public static double getDouble(Object _strSrc) {
        if (isEmpty(_strSrc)) {
            return 0;
        }
        try {
            return Double.parseDouble(_strSrc.toString());
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    /**
     * 获取int
     *
     * @param _strSrc
     * @return
     */
    public static int getInt(Object _strSrc) {
        if (isEmpty(_strSrc)) {
            return 0;
        }
        try {
            return Integer.parseInt(_strSrc.toString().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 功能描述:获取随机字符串
     *
     * @param: [length:获取的随机字符串长度;randStr:随机字符串因子->ex:abcdefg]
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/2/25 0025 17:34
     */
    public static String getRandomString(int length, String randStr) {
        if (length <= 0 || isEmpty(randStr)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int len = randStr.length();
        for (int i = 0; i < length; i++) {
            int round = (int) Math.round(Math.random() * (len - 1));
            sb.append(randStr.charAt(round));
        }
        return sb.toString();
    }
}
