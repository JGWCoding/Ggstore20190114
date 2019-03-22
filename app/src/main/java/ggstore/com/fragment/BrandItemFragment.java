//package ggstore.com.fragment;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.ImageView;
//
//import ggstore.com.R;
//import ggstore.com.base.BaseFragment;
//import ggstore.com.utils.LogUtil;
//
//public class BrandItemFragment extends BaseFragment {
//    public static boolean isSingle = true; //是否为单列
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_brand_item;
//    }
//    @Override
//    protected void initWidget(View root) {
//        isSingle = true;
////        addFragment(R.id.fragment_brand_item_fragment,new BrandItemRecyclerFragment());
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        BrandItemRecyclerFragment brandItemRecyclerFragment = new BrandItemRecyclerFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("url",getArguments().getString("url"));
//        brandItemRecyclerFragment.setArguments(bundle);
//        transaction.add(R.id.fragment_brand_item_fragment,brandItemRecyclerFragment);
//        transaction.commitNowAllowingStateLoss();
//        final ImageView singleImg = findView(R.id.fragment_brand_item_img);
//        final ImageView doubleImg = findView(R.id.fragment_brand_item_img2);
//        singleImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isSingle=true;
//                BrandItemRecyclerFragment fragment = (BrandItemRecyclerFragment) getChildFragmentManager().findFragmentById(R.id.fragment_brand_item_fragment);
////                BrandItemRecyclerFragment fragment = (BrandItemRecyclerFragment) getChildFragmentManager().findFragmentByTag(ToyEducationRecyclerFragment.class.getName());
//                fragment.setLayoutManager(fragment.getLayoutManager());
//                singleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg));
//                doubleImg.setBackgroundDrawable(null);
//            }
//        });
//        doubleImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isSingle=false;
//                singleImg.setBackgroundDrawable(null);
//                doubleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg));
//                BrandItemRecyclerFragment fragment = (BrandItemRecyclerFragment) getChildFragmentManager().findFragmentById(R.id.fragment_brand_item_fragment);
//                fragment.setLayoutManager(fragment.getLayoutManager());
//            }
//        });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        BrandItemRecyclerFragment fragment = (BrandItemRecyclerFragment) getChildFragmentManager().findFragmentById(R.id.fragment_brand_item_fragment);
//        if (fragment!=null) {
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//            fragmentTransaction.remove(fragment).commitNowAllowingStateLoss();
//            LogUtil.i("回收Fragment:"+this.getClass().getName());
//        }else{
//            LogUtil.i("已经回收Fragment:"+this.getClass().getName());
//        }
//    }
//}
