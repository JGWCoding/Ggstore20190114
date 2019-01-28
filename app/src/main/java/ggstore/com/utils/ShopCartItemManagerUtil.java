package ggstore.com.utils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import ggstore.com.BaseApplication;
import ggstore.com.bean.ShopCartBean;
import ggstore.com.bean.ShopCartBeanDao;

public class ShopCartItemManagerUtil {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertShopCart(ShopCartBean shop) {
        ToastUtil.showToast("add success");
        BaseApplication.getDaoInstant().getShopCartBeanDao().insertOrReplace(shop);
    }


    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteShopCart(long id) {
//        ToastUtil.showToast("delete success");
        BaseApplication.getDaoInstant().getShopCartBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateShopCart(ShopCartBean shop) {
//        ToastUtil.showToast("add success");
        BaseApplication.getDaoInstant().getShopCartBeanDao().update(shop);
    }


//    public static List<ShopCartBean> queryShopCart() {
//        return BaseApplication.getDaoInstant().getShopCartBeanDao().queryBuilder().where(ShopCartBeanDao.Properties.Buy_number.eq(1)).list();
//    }

    /**
     * 查询全部数据
     */
    public static List<ShopCartBean> queryAll() {
        return BaseApplication.getDaoInstant().getShopCartBeanDao().loadAll();
    }

    public static int getSize() {
        return BaseApplication.getDaoInstant().getShopCartBeanDao().loadAll().size();
    }

    private static ShopCartBean queryBuyNumber(Long id) {
        QueryBuilder<ShopCartBean> queryBuilder = BaseApplication.getDaoInstant().getShopCartBeanDao().queryBuilder().where(ShopCartBeanDao.Properties.Id.eq(id));
        if (queryBuilder.list() != null && queryBuilder.list().size() > 0) {
            return queryBuilder.list().get(0);
        } else {
            return null;
        }
    }

    public static void updateShopCart(Long id, String productName, float price, int number, int limitNumber, String iconUrl, String productCode, String detail1, String detail2) {
        if (queryBuyNumber(id) != null) {
            ShopCartBean shopCartBean = queryBuyNumber(id);
            if (shopCartBean.getBuy_number() >= shopCartBean.getLimit_number()) {
                //todo 超出限制数量,是否作出提示
                shopCartBean.setBuy_number(shopCartBean.getLimit_number());
                return;
            } else {
                shopCartBean.setBuy_number(shopCartBean.getBuy_number() + number);
            }
                updateShopCart(shopCartBean);
        } else {
            insertShopCart(new ShopCartBean(id, productName, price, number, limitNumber, iconUrl, productCode, detail1, detail2));
        }
    }
}
