package ggstore.com.utils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.bean.OrderNumberBean;
import ggstore.com.bean.OrderNumberBeanDao;
import ggstore.com.bean.ShopCartBean;

public class OrderNumberManagerUtil {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertOrderNumber(OrderNumberBean shop) {
        App.getDaoInstant().getOrderNumberBeanDao().insertOrReplace(shop);
    }


    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteOrderNumber(long id) {
        App.getDaoInstant().getOrderNumberBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateOrderNumber(OrderNumberBean shop) {
        App.getDaoInstant().getOrderNumberBeanDao().update(shop);
    }


    /**
     * 查询全部数据
     */
    public static ArrayList<OrderNumberBean> queryAll() {
        ArrayList<OrderNumberBean> orderNumberBeans = (ArrayList<OrderNumberBean>) App.getDaoInstant().getOrderNumberBeanDao().loadAll();
        if (orderNumberBeans == null || orderNumberBeans.size() == 0) {
            return new ArrayList<>();
        }
        Collections.sort(orderNumberBeans, new Comparator<OrderNumberBean>() {
            @Override
            public int compare(OrderNumberBean o1, OrderNumberBean o2) {
                return (int) (o2.getOrder_id() - o1.getOrder_id());
            }
        });
        return orderNumberBeans;
    }

    public static ArrayList<OrderNumberBean> queryOrderIdOnlyOne() {
        ArrayList<OrderNumberBean> orderNumberBeans = queryAll();
        ArrayList<OrderNumberBean> list = new ArrayList<>();
        for (int i = 0; i < orderNumberBeans.size(); i++) {
            if (i==0) {
                list.add(orderNumberBeans.get(i));
            }else{
                if (list.get(list.size()-1).getOrder_id()!=orderNumberBeans.get(i).getOrder_id()){
                    list.add(orderNumberBeans.get(i));
                }
            }
        }
        return list;
    }

    public static int getSize() {
        return App.getDaoInstant().getOrderNumberBeanDao().loadAll().size();
    }


    public static void addOrderNumber(List<ShopCartBean> shopCartList, String yyyyMMddHHmmss) {
        if (shopCartList == null) {
            ToastUtil.showToast(R.string.add_order_number_error);
            return;
        }
        String day = com.blankj.utilcode.util.TimeUtils.getNowString(new SimpleDateFormat("dd/MM/yyyy"));
        for (ShopCartBean bean : shopCartList) {
            OrderNumberBean omBean = new OrderNumberBean();
            omBean.setBuy_number(bean.getBuy_number());
            omBean.setImage_url(bean.getImage_url());
            omBean.setName(bean.getName());
            omBean.setOrder_id(Long.valueOf(yyyyMMddHHmmss));
            omBean.setOrder_state("準備送貨");
            omBean.setPay_day(day);
            omBean.setPrice(bean.getPrice());
            omBean.setProduct_number(bean.getProductNumber());
            omBean.setImages(bean.getImages());
            App.getDaoInstant().getOrderNumberBeanDao().insertOrReplace(omBean);
        }
    }

    public static ArrayList<OrderNumberBean> queryOrderId(Long orderId) {  //查询这个订单号的所有产品
        QueryBuilder<OrderNumberBean> queryBuilder = App.getDaoInstant().getOrderNumberBeanDao().queryBuilder().where(OrderNumberBeanDao.Properties.Order_id.eq(orderId));
        if (queryBuilder != null && queryBuilder.list() != null && queryBuilder.list().size() == 0) {
            return new ArrayList<OrderNumberBean>();
        }
        return (ArrayList<OrderNumberBean>) queryBuilder.list();
    }

    public static boolean queryOrderIdIsExist(Long orderId) {  //查询是否有这个订单号
        QueryBuilder<OrderNumberBean> queryBuilder = App.getDaoInstant().getOrderNumberBeanDao().queryBuilder().where(OrderNumberBeanDao.Properties.Order_id.eq(orderId));
        if (queryBuilder != null && queryBuilder.list() != null && queryBuilder.list().size() > 0) {
            return true;
        }
        return false;
    }
}
