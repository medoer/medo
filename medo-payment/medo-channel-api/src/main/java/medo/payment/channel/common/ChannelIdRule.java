package medo.payment.channel.common;

import java.util.regex.Pattern;

/**
 * Charge Channel ID
 */
public class ChannelIdRule {

    //支付宝: 用户刷卡条形码规则：16-24位纯数字，以25-30开头
    private static final int[] ALIPAY_QRCODE_LENGTH_ARR = {16,17,18,19,20,21,22,23,24};
    private static final String[] ALIPAY_QRCODE_START_ARR = new String[]{"25","26","27","28","29","30"};
    //微信: 用户刷卡条形码规则：18位纯数字，以10-15开头
    private static final int WECHAT_QRCODE_LENGTH = 18;
    private static final String[] WECHAT_QRCODE_START_ARR = new String[]{"10","11","12","13","14","15"};

    /**
     * 判断是否是微信的二维码
     * @param code 二维码
     * @return true if is wechat qrcode ; false if not
     */
    public static boolean isWechatQrcode(String code){
        return isNumberOfLengthNStartWith(code,WECHAT_QRCODE_LENGTH,WECHAT_QRCODE_START_ARR);
    }

    /**
     * 判断是否是支付宝的二维码
     * @param code 二维码
     * @return true if is alipay qrcode ; false if not
     */
    public static boolean isAlipayQrcode(String code){
        boolean result = false;
        for(int len : ALIPAY_QRCODE_LENGTH_ARR){
            if(isNumberOfLengthNStartWith(code,len,ALIPAY_QRCODE_START_ARR)){
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 判断是否是N位的数字,且开头字母符合规则
     * @param str 需要判断的字符串
     * @param size 字符串的长度
     * @param arr 开头字母包含的字符串数组
     * @return true if ok; false if not
     */
    public static boolean isNumberOfLengthNStartWith(String str,int size,String[] arr){
        boolean result;
        if(size > 0){
            result = Pattern.matches("^\\d{"+size+"}$", str);
        }else{
            result = Pattern.matches("^[0-9]*$", str);
        }
        if(result && arr != null){
            result = false;
            for(String s : arr){
                if(str.startsWith(s)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 判断是否是N位的数字
     * @param str 需要判断的字符串
     * @param size 字符串的长度
     * @return true if ok; false if not
     */
    public static boolean isNumberOfLength(String str,int size){
        if(size > 0){
            String pattern = "^\\d{"+size+"}$";
            return Pattern.matches(pattern, str);
        }else{
            String pattern = "^[0-9]*$";
            return Pattern.matches(pattern, str);
        }
    }

    /**
     * 判断是否是纯数字
     * @param str 需要判断的字符串
     * @return  true if ok; false if not
     */
    public static boolean isNumberOfLength(String str){
        return isNumberOfLength(str,-1);
    }

}
