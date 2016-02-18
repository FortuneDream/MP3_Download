package com.example.dell.jiandiscover;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView homepage, music, download, phone, other;

    private HomepageFragment homepageFragment;
    private MusicFragment musicFragment;
    private DownLoadFragment downLoadFragment;
    private PhoneFragment phoneFragment;
    private OtherFragment otherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        homepage= (ImageView) findViewById(R.id.homepage);
        music = (ImageView) findViewById(R.id.music);
        download = (ImageView) findViewById(R.id.download);
        phone = (ImageView) findViewById(R.id.phone);
        other = (ImageView) findViewById(R.id.other);

        homepage.setOnClickListener(this);
        music.setOnClickListener(this);
        download.setOnClickListener(this);
        phone.setOnClickListener(this);
        other.setOnClickListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomepageFragment homepageFragment = new HomepageFragment();
        transaction.replace(R.id.content, homepageFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
        Log.d("haha", "APP打开了");
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.homepage:
                if (homepageFragment == null) {
                    homepageFragment = new HomepageFragment();
                }
                transaction.replace(R.id.content, homepageFragment);
                Log.d("haha", "主页点击");
                break;
            case R.id.music:
                if (musicFragment == null) {
                    musicFragment = new MusicFragment();
                }
                transaction.replace(R.id.content, musicFragment);
                Log.d("haha", "音乐点击");
                break;
            case R.id.download:
                if (downLoadFragment == null) {
                    downLoadFragment = new DownLoadFragment();
                }
                transaction.replace(R.id.content, downLoadFragment);
                Log.d("haha", "下载点击");
                break;
            case R.id.phone:
                if (phoneFragment == null) {
                    phoneFragment = new PhoneFragment();
                }
                transaction.replace(R.id.content, phoneFragment);
                Log.d("haha", "phone点击");
                break;
            case R.id.other:
                if (otherFragment == null) {
                    otherFragment = new OtherFragment();
                }
                transaction.replace(R.id.content, otherFragment);
                Log.d("haha", "other点击");
                break;
        }
        transaction.commit();
    }
}


//-------------------------------------------------------------------------------------

