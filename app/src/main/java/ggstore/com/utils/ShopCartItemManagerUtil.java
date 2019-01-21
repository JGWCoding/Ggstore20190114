package ggstore.com.utils;

import java.util.List;

import ggstore.com.BaseApplication;
import ggstore.com.bean.ShopCartBean;

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
    public static void deleteShopCart() {
        List<ShopCartBean> shopCartBeans = BaseApplication.getDaoInstant().getShopCartBeanDao().loadAll();
        if (shopCartBeans.size()>0) {
            BaseApplication.getDaoInstant().getShopCartBeanDao().delete(shopCartBeans.get(0));
        ToastUtil.showToast("delete success");
        } else {
            ToastUtil.showToast("delete fail");
            LogUtil.e("数据库删除数据失败");
        }
    }
    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteShopCart(long id) {
        ToastUtil.showToast("delete success");
        BaseApplication.getDaoInstant().getShopCartBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateShopCart(ShopCartBean shop) {
        ToastUtil.showToast("add success");
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
}
