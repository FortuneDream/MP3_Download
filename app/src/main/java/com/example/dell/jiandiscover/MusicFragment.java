package com.example.dell.jiandiscover;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by dell on 2016/1/24.
 */
public class MusicFragment extends Fragment implements View.OnClickListener {
    private Button play;
    private Button pause;
    private Button next;
    private TextView singname;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private File list[];
    public static int singIndex=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            singname.setText((String)msg.obj);
        }
    };
    private   FileInputStream fileInputStream;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music, container, false);
        play = (Button) view.findViewById(R.id.play);
        next = (Button) view.findViewById(R.id.next);
        singname=(TextView)view.findViewById(R.id.txt_now_singname);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        initMediaPlayer();
        return view;
    }

    private void initMediaPlayer() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Music");
        if(file.exists()&&file.isDirectory()) {
            list = file.listFiles();
        }
            fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(list[singIndex]);
                mediaPlayer.setDataSource(fileInputStream.getFD());
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.obj=list[0].toString();
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
                break;
            case R.id.next:
               fileInputStream = null;
                try {
                    mediaPlayer.reset();//修改了路径后要记得reset
                    fileInputStream = new FileInputStream(list[(++singIndex)%list.length]);
                    mediaPlayer.setDataSource(fileInputStream.getFD());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.obj=(String)list[singIndex];
                            handler.sendMessage(message);
                        }
                    }).start();
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        singIndex=0;
    }
}
