package com.example.dell.jiandiscover;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView homepageImg, musicImg, downloadImg, otherImg;

    private HomepageFragment homepageFragment;
    private MusicFragment musicFragment;
    private DownLoadFragment downLoadFragment;
    private OtherFragment otherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homepageImg = (ImageView) findViewById(R.id.ic_homepage_fragment);
        musicImg = (ImageView) findViewById(R.id.ic_music_fragment);
        downloadImg = (ImageView) findViewById(R.id.ic_download_fragment);
        otherImg = (ImageView) findViewById(R.id.ic_other_fragment);

        homepageImg.setOnClickListener(this);
        musicImg.setOnClickListener(this);
        downloadImg.setOnClickListener(this);
        otherImg.setOnClickListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomepageFragment homepageFragment = new HomepageFragment();
        transaction.replace(R.id.content, homepageFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//返回一个BackStackRecord（extend FragmentTransaction），记录一个操作。replace，add等操作
        switch (v.getId()) {
            case R.id.ic_homepage_fragment:
                if (homepageFragment == null) {
                    homepageFragment = new HomepageFragment();
                }
                transaction.replace(R.id.content, homepageFragment);
                break;
            case R.id.ic_music_fragment:
                if (musicFragment == null) {
                    musicFragment = new MusicFragment();
                }
                transaction.replace(R.id.content, musicFragment);
                break;
            case R.id.ic_download_fragment:
                if (downLoadFragment == null) {
                    downLoadFragment = new DownLoadFragment();
                }
                transaction.replace(R.id.content, downLoadFragment);
                break;
            case R.id.ic_other_fragment:
                if (otherFragment == null) {
                    otherFragment = new OtherFragment();
                }
                transaction.replace(R.id.content, otherFragment);
                break;
        }
        transaction.commit();
    }
}


