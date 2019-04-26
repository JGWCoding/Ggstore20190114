package ggstore.com.utils;

import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import ggstore.com.App;
import ggstore.com.R;
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
        App.getDaoInstant().getShopCartBeanDao().insertOrReplace(shop);
    }


    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteShopCart(long id) {
        ToastUtil.showToast(R.string.delete_product);
        App.getDaoInstant().getShopCartBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateShopCart(ShopCartBean shop) {
        limitNumberCheckAndReset(shop.getId());
        App.getDaoInstant().getShopCartBeanDao().update(shop);
    }


//    public static List<ShopCartBean> queryShopCart() {
//        return App.getDaoInstant().getShopCartBeanDao().queryBuilder().where(ShopCartBeanDao.Properties.Buy_number.eq(1)).list();
//    }

    /**
     * 查询全部数据
     */
    public static List<ShopCartBean> queryAll() {
        return App.getDaoInstant().getShopCartBeanDao().loadAll();
    }

    public static int getSize() {
        return App.getDaoInstant().getShopCartBeanDao().loadAll().size();
    }

    private static ShopCartBean queryBuyNumber(Long id) {
        QueryBuilder<ShopCartBean> queryBuilder = App.getDaoInstant().getShopCartBeanDao().queryBuilder().where(ShopCartBeanDao.Properties.Id.eq(id));
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
        if (item == null) {
            ToastUtil.showToast(R.string.add_shopcart_error);
            return;
        }
        ToastUtil.showToast(R.string.add_product_to_shopcart);
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
            String imgs = getImages(item);
            insertShopCart(new ShopCartBean(Long.valueOf(item.getProductID()), item.getProductName_cn(), Float.valueOf(item.getUnitPrice()), 1, TextUtils.isEmpty(item.getUnitsInStock()) ? Integer.MAX_VALUE : Integer.valueOf(item.getUnitsInStock()), item.getPictureL(), item.getProductCode(), item.getRemark_cn(), item.getRemark_cn(), imgs));
        }
    }

    private static String getImages(NewProductBean newProductBean) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(newProductBean.getPictureL())) {
            sb.append(newProductBean.getPictureL() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS1())) {
            sb.append(newProductBean.getPictureS1() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS2())) {
            sb.append(newProductBean.getPictureS2() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS3())) {
            sb.append(newProductBean.getPictureS3() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS4())) {
            sb.append(newProductBean.getPictureS4() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS5())) {
            sb.append(newProductBean.getPictureS5() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS6())) {
            sb.append(newProductBean.getPictureS6() + ";");
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS7())) {
            sb.append(newProductBean.getPictureS7() + ";");

        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS8())) {
            sb.append(newProductBean.getPictureS8() + ";");
        }
        return sb.toString();
    }

    public static void deleteShopCartAll() {
        App.getDaoInstant().getShopCartBeanDao().deleteAll();
    }

    public static void deleteShopCart(List<ShopCartBean> shopCartList) {
        App.getDaoInstant().getShopCartBeanDao().deleteInTx(shopCartList);
    }
}
