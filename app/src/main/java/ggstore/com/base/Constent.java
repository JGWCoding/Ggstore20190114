package ggstore.com.base;

import ggstore.com.bean.NewProductBean;

public class Constent {
    //sp里面的字段
    public static final String is_remeber = "is_remeber";
    public static final String username_passwrod = "username_passwrod";

    public static  boolean is_chinese = true; //表明是否为中文语言


    public static final String file_path = "file"; //CrashHandler 里面的字段
    public static  boolean is_login = false; //表明是否登录
    public static  boolean is_member = false; //表明是否为会员
    public static  String account_number = ""; //登录时的邮箱 bingmeo2013@gmail.com
    public static  String user_name = "";   //登录后的具体账号取得名字
    public static  String token = "";
    public static final String base_url = "http://shop.googoogaga.com.hk/";
    public static final String base_images_url = "http://shop.googoogaga.com.hk/images/product/";

    public static final String login_url = base_url+"api_get_login.php";
    public static final String getInfo_url = base_url+"api_get_member_info.php";
    public static final String url_home = base_url +"api_get_news.php";
    public static final String url_new_product = base_url +"api/getLatest.php?sort=new";

    public static final String testUrl = "https://www.ziweiyang.com/api_get_news.php?recordperpage=8&page=1&sortby=data";

    public static NewProductBean newProductBean ; //用来保存bean
}
