package ggstore.com.utils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggstore.com.BaseApplication;
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
        BaseApplication.getDaoInstant().getOrderNumberBeanDao().insertOrReplace(shop);
    }


    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteOrderNumber(long id) {
        BaseApplication.getDaoInstant().getOrderNumberBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateOrderNumber(OrderNumberBean shop) {
        BaseApplication.getDaoInstant().getOrderNumberBeanDao().update(shop);
    }


    /**
     * 查询全部数据
     */
    public static ArrayList<OrderNumberBean> queryAll() {
        ArrayList<OrderNumberBean> orderNumberBeans = (ArrayList<OrderNumberBean>) BaseApplication.getDaoInstant().getOrderNumberBeanDao().loadAll();
        Collections.sort(orderNumberBeans, new Comparator<OrderNumberBean>() {
            @Override
            public int compare(OrderNumberBean o1, OrderNumberBean o2) {
                return (int) (o2.getOrder_id()-o1.getOrder_id());
            }
        });
        return orderNumberBeans;
    }

    public static int getSize() {
        return BaseApplication.getDaoInstant().getOrderNumberBeanDao().loadAll().size();
    }

    private static OrderNumberBean queryBuyNumber(Long id) {
        QueryBuilder<OrderNumberBean> queryBuilder = BaseApplication.getDaoInstant().getOrderNumberBeanDao().queryBuilder().where(OrderNumberBeanDao.Properties.Id.eq(id));
        if (queryBuilder.list() != null && queryBuilder.list().size() > 0) {
            return queryBuilder.list().get(0);
        } else {
            return null;
        }
    }

    public static void addOrderNumber(List<ShopCartBean> shopCartList, String yyyyMMddHHmmss) {
        if (shopCartList==null){
            ToastUtil.showToast("shopCartList is null,please screenshot me");
            return;
        }
        String day = com.blankj.utilcode.util.TimeUtils.getNowString(new SimpleDateFormat("dd/MM/yyyy"));
        for (ShopCartBean bean:shopCartList){
            OrderNumberBean omBean = new OrderNumberBean();
            omBean.setBuy_number(bean.getBuy_number());
            omBean.setImage_url(bean.getImage_url());
            omBean.setName(bean.getName());
            omBean.setOrder_id(Long.valueOf(yyyyMMddHHmmss));
            omBean.setOrder_state("準備送貨");
            omBean.setPay_day(day);
            omBean.setPrice(bean.getPrice());
            omBean.setProduct_number(bean.getProductNumber());
            BaseApplication.getDaoInstant().getOrderNumberBeanDao().insertOrReplace(omBean);
        }
    }


    public static boolean queryOrderId(Long orderId) {  //查询是否有这个订单号,有
        QueryBuilder<OrderNumberBean> queryBuilder = BaseApplication.getDaoInstant().getOrderNumberBeanDao().queryBuilder().where(OrderNumberBeanDao.Properties.Order_id.eq(orderId));
        if (queryBuilder!=null&&queryBuilder.list()!=null&&queryBuilder.list().size()>0){
            return true;
        }
        return false;
    }
}
