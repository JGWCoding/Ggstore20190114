package ggstore.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class OrderNumberBean {
    //不能用int
    @Unique//唯一性,不能有相同的名称
    @Id (autoincrement = true)    //ProductID
    private Long id;
    private long order_id;  //订单号
    @Unique //唯一性,不能有相同的名称
    private String name;//商品名称

    @Property(nameInDb = "price")   //自定义列表名 nameInDb = "price"
    private float price;   //商品价格

    private int buy_number; //买入数量

    private String image_url;   //图标url

    private String product_number; //商品编号

    private String pay_day;   //付款日期    26/11/2018
    private String order_state;   //订单状态
    @Generated(hash = 523349299)
    public OrderNumberBean(Long id, long order_id, String name, float price,
            int buy_number, String image_url, String product_number, String pay_day,
            String order_state) {
        this.id = id;
        this.order_id = order_id;
        this.name = name;
        this.price = price;
        this.buy_number = buy_number;
        this.image_url = image_url;
        this.product_number = product_number;
        this.pay_day = pay_day;
        this.order_state = order_state;
    }
    @Generated(hash = 866707557)
    public OrderNumberBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getOrder_id() {
        return this.order_id;
    }
    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
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
    public String getProduct_number() {
        return this.product_number;
    }
    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }
    public String getPay_day() {
        return this.pay_day;
    }
    public void setPay_day(String pay_day) {
        this.pay_day = pay_day;
    }
    public String getOrder_state() {
        return this.order_state;
    }
    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

}
