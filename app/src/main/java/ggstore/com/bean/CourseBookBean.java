package ggstore.com.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class CourseBookBean implements Serializable {
    /**
     * total_page : 9
     * prev_page : 1
     * next_page : 2
     */
    /**
     * id : 55
     * name : 八字（一）试读
     * name2 :
     * detail :     我个人认爲『八字』是各门术数的基础，如果不懂『八字』，是很难或可说无法去研究其他更高级的术数如『六壬』及『奇门遁甲』等。就算学『紫微斗数』和『风水』，也应对『八字』有一定的认识。
     * 所以，在这个网站开始带领大家去研究各门术数的时候，就先开始介绍『八字』，使一些完全不懂术数而又想学术数的人有一个好的开始和巩固的基础。
     * 这个网站，最主要目的是让广大的网民有机会去接触各门的术数，并明白这并不是迷信。我个人认爲每个人如能很好的去掌握各门术数，有时候真的有很大机会把运气掌握在自己的手中。
     * date : 2015-05-28
     * is_new : 1
     * allow_preview : 0
     * coin : 0.00
     * bought : false
     * read : false
     * photo_list : ["upload/images/coursebook_category/20150504112616_16.jpg"]
     * thumb_list : ["upload/images/coursebook_category/m/20150504112616_16.jpg"]
     */

    private String id;
    private String name;
    private String name2;
    private String detail;
    private String date;
    private String is_new;
    private String allow_preview;
    private String coin;
    private boolean bought;
    private boolean read;
    private List<String> photo_list;
    private List<String> thumb_list;


    private int total_page;
    private int prev_page;
    private int next_page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getAllow_preview() {
        return allow_preview;
    }

    public void setAllow_preview(String allow_preview) {
        this.allow_preview = allow_preview;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public List<String> getPhoto_list() {
        return photo_list;
    }

    public void setPhoto_list(List<String> photo_list) {
        this.photo_list = photo_list;
    }

    public List<String> getThumb_list() {
        return thumb_list;
    }

    public void setThumb_list(List<String> thumb_list) {
        this.thumb_list = thumb_list;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getPrev_page() {
        return prev_page;
    }

    public void setPrev_page(int prev_page) {
        this.prev_page = prev_page;
    }

    public int getNext_page() {
        return next_page;
    }

    public void setNext_page(int next_page) {
        this.next_page = next_page;
    }
}
