package com.litingzhe.justandroid.global;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * 常量
 */
public class Constants {

    //---------------------------微信支付参数---------------------//
    //appid
    public static final String APP_ID = "wx0c9beaaff560cabe";

    //商户号
    public static final String MCH_ID = "1316622601";

    //  API密钥，在商户平台设置
    public static final String API_KEY = "61AC8889E44C036C66F1FEF8D16EBA86";


//---------------------------支付宝支付参数---------------------//

    public static final String AliAppId = "2016031501214491";
    // 商户PID

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCc3lF1Sois6MaIj936c6iWjfWnkytEZ7lnvkibx/XQq5UTo4MrGf4HKmNtUAh6AO1HO5qwB8o+ZlGMm1Jb473+qMtKAJhItgFrMendODVMB7ND0mRZ0JIwg86Zme5fQqNCdX1MGDMFJsUXhGvRwhodQF14K5+m+Z39iZHQ6YdL0bCnWcniI5w34nGsEqZaxqLcDIBiIpOwhc0IyjRP6NI04RXADG5Rq3rZZkKle021ec+xIgwjI4mz5C6Cz431M5wrJyUCy9CxfACQ6TW1WXWuZcOumj2R96QJ7FrWrHz70ZBSn+PjrR9+x026vxTmjyWDNwgd4XPCl7TCKwLsPmv3AgMBAAECggEAdEcrNHkqNJlbSLDzxwlihy71T+D8KnmEak0XPzoKkUIOf/8tZI6gySj3iHjRv7XeLHYXJLKxjG2Ft2cOtoRrOlBeWB12xDb9eDsIcm2OvCvdCsHylszPAF1DkIIUutIHeGTkmro5X3EsmVEUJujtOI+UeVZEwp23Kc+Gocfo/R5iYD1fyGHSZRiJ0yB1TABTxaOhlujCbdWCsGbOJilmSmWYdi9lFzX0jmkCEu4wAkhKlqpbRuMwzyuu0rcBxz8GXPXVE9elguKryADBKH9kOQ+XczMLCAyav3LUPvODuE2knzDLYuEN++V88eeMqZMi4hEcXnQqN4UdFJoqYhryUQKBgQD6/tv1cSWfFTmHet+GEXLpHqd4deCPGh2feewGEahYH6ZVjgBBadpJNpjLoqH2ZVTcfFKkoGP9x1xCVjhfVyzUre8Lgj0ljIu4TjJu5m8OIdZrPQfFS6YKsmV8UYEKG6TnJTeq4PfSR0yKiKykyE798wrJIW+qnSLkrEG20H+9SQKBgQCf/wMJ9Qc6VsHQm/a0vRWw6Go3FveuSWUXpvFexyRGpqz3606VZCroWkbvi/zu8rhnb74FCeDecPqKPndsg79H91RC1FO0CBXuZaDtYwu/bjpnmcBjrLeuQzmCui9Rh5ESRfDiwxQnIzxilThXJOLkoCKO00uaTLxDLx7erMsfPwKBgEsGv61AYySIbafnvMUQli7E8P57DeL4xX4ULc8qnA2reEb+qWjkQVG9NjM4q/1BEPrPVszK/NixLQzPRQ6KumXgkCH6jliZCHLRPRYVX4Dz8Goh1h7NnBZ6eQeOPWpOFR7a6ybzFGEQ2vpWHHUtQx959PFmQ0DWs8VzEDmbXXOxAoGAak6NnVsSX1/vzYUjifms5Ou4TzxSe5pGsk15SQk2gD8U5HGTuE1i+Rmq0lWITNg3UHr9nelvYT1eKubQgOiU+uNKOFFOAGvy7oDWSYSq9Jnv8pBFNbU4InCGUOcWs6xI5wRTxsfppwuRfss5MSXQICqKa63XKQHdgSJY94mGOQ8CgYEAuPSQrp99CMnaR7rdUt4PQXxB5jtxsUGNij/xU3MU/WtPlQIGdjQQFPtH4xA4Opf0mZ48EVVRWcNcHMREx1vb+LXljYPlWPNHhu5xblCwe94pIephb0LSgvMdbEzsTf3rz9RrrJAbBQ91TPXmeMdZze6aJgA4uISB5u3kiVIsXFk=";

    //---------------------------支付宝支付参数---------------------//

    public static final String BASEURL = "https://op.dh-etc.com/";

    //钱包充值微信回调
    public static final String CHARGEWALLET_WEICHAT = Constants.BASEURL + "charge/chargeNotifyForApp";
    //钱包充值支付宝回调
    public static final String CHARGEWALLET_AliPAY = Constants.BASEURL + "charge/chargeZFBNotify";


    public static final String MYTAG = "MYTAG";


}
