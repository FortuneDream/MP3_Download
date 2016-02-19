package com.example.dell.jiandiscover;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dell on 2016/1/22.
 */
public class HomepageFragment extends Fragment {
    private Resources resources;
    private TextView studyWhatTxt, studyNoteTxt, functionTxt;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private ImageView image;
    public final static int num=3;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        resources=getResources();
        InitImage(view);
        InitTextView(view);
        InitViewPager(view);
        return view;
    }

    public void InitTextView(View parentView){
        studyWhatTxt = (TextView)parentView.findViewById(R.id.txt_study_what);
        studyNoteTxt = (TextView)parentView.findViewById(R.id.txt_study_note);
        functionTxt = (TextView)parentView.findViewById(R.id.txt_function);

        studyWhatTxt.setOnClickListener(new txListener(0));
        studyNoteTxt.setOnClickListener(new txListener(1));
        functionTxt.setOnClickListener(new txListener(2));
    }


    public class txListener implements View.OnClickListener{
        private int index=0;

        public txListener(int i) {
            index =i;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPager.setCurrentItem(index);
        }
    }


    /*
     * 初始化图片的位移像素
     */
    public void InitImage(View parentView){
        image = (ImageView)parentView.findViewById(R.id.img_cursor);
        bmpW = image.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW/num - bmpW)/2;

        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        image.setImageMatrix(matrix);
    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager(View parentView){
        mPager = (ViewPager)parentView.findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment tabFunctionFragment=new TabFunctionFragment();
        Fragment tabStudyNoteFragment=new TabStudyNoteFragment();
        Fragment tabStudyWhatFragmen=new TabStudyWhatFragment();
        fragmentList.add(tabStudyWhatFragmen);
        fragmentList.add(tabStudyNoteFragment);
        fragmentList.add(tabFunctionFragment);

        //给ViewPager设置适配器
        mPager.setAdapter(new HomepageViewpagerAdapter(getChildFragmentManager() , fragmentList));
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页

        mPager.addOnPageChangeListener(new MyOnPageChangeListener());//页面滑动监听器
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int one = offset *2 +bmpW;//两个相邻页面的偏移量

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画
            currIndex = arg0;
            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
            animation.setDuration(200);//动画持续时间0.2秒
            image.startAnimation(animation);//是用ImageView来显示动画的
        }
    }
}
