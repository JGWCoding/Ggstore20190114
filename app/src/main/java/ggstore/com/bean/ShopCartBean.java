package ggstore.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class ShopCartBean {

    //不能用int
    @Id(autoincrement = true)
    private Long id;    //商品唯一识别号

//    @Unique //唯一性,不能有相同的名称
    private String name;//商品名称

    @Property(nameInDb = "price")   //自定义列表名 nameInDb = "price"
    private String price;   //商品价格

    private int buy_number; //买入数量

    private String image_url;   //图标url

    private String shop_id; //商品编号

    private String shop_detail1;   //商品介绍详情
    private String shop_detail2;   //商品介绍详情
    @Generated(hash = 1780214760)
    public ShopCartBean(Long id, String name, String price, int buy_number,
            String image_url, String shop_id, String shop_detail1,
            String shop_detail2) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.buy_number = buy_number;
        this.image_url = image_url;
        this.shop_id = shop_id;
        this.shop_detail1 = shop_detail1;
        this.shop_detail2 = shop_detail2;
    }
    @Generated(hash = 656791369)
    public ShopCartBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public int getBuy_number() {
        return this.buy_number;
    }
    public void setBuy_number(int buy_number) {
        this.buy_number = buy_number;
    }
    public String getImage_url() {
        return this.image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public String getShop_id() {
        return this.shop_id;
    }
    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getShop_detail1() {
        return this.shop_detail1;
    }
    public void setShop_detail1(String shop_detail1) {
        this.shop_detail1 = shop_detail1;
    }
    public String getShop_detail2() {
        return this.shop_detail2;
    }
    public void setShop_detail2(String shop_detail2) {
        this.shop_detail2 = shop_detail2;
    }
}