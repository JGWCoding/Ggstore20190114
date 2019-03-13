package ggstore.com.utils;

import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import ggstore.com.BaseApplication;
import ggstore.com.bean.NewProductBean;
import ggstore.com.bean.ShopCartBean;
import ggstore.com.bean.ShopCartBeanDao;

public class ShopCartItemManagerUtil {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertShopCart(ShopCartBean shop) {
        BaseApplication.getDaoInstant().getShopCartBeanDao().insertOrReplace(shop);
    }


    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteShopCart(long id) {
        BaseApplication.getDaoInstant().getShopCartBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateShopCart(ShopCartBean shop) {
        limitNumberCheckAndReset(shop.getId());
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


    private static void limitNumberCheckAndReset(Long id) {
        if (queryBuyNumber(id) != null) {
            ShopCartBean shopCartBean = queryBuyNumber(id);
            if (shopCartBean.getBuy_number() > shopCartBean.getLimit_number()) {
                shopCartBean.setBuy_number(shopCartBean.getLimit_number());
            }
        }
    }

    public static void addShopCart(NewProductBean item) {
        long id = Long.valueOf(item.getProductID());
        if (queryBuyNumber(id) != null) {
            ShopCartBean shopCartBean = queryBuyNumber(id);
            if (shopCartBean.getBuy_number() >= shopCartBean.getLimit_number()) {
                limitNumberCheckAndReset(id);
            } else {
                shopCartBean.setBuy_number(shopCartBean.getBuy_number() + 1);
            }
            updateShopCart(shopCartBean);
        } else {
            insertShopCart(new ShopCartBean(Long.valueOf(item.getProductID()), item.getProductName_cn(), Float.valueOf(item.getUnitPrice()), 1, TextUtils.isEmpty(item.getUnitsInStock()) ? Integer.MAX_VALUE : Integer.valueOf(item.getUnitsInStock()), item.getPictureL(), item.getProductCode(), item.getRemark_cn(), item.getRemark_cn()));
        }
    }

    public static void deleteShopCartAll() {
        BaseApplication.getDaoInstant().getShopCartBeanDao().deleteAll();
    }

    public static void deleteShopCart(List<ShopCartBean> shopCartList) {
        BaseApplication.getDaoInstant().getShopCartBeanDao().deleteInTx(shopCartList);
    }
}
