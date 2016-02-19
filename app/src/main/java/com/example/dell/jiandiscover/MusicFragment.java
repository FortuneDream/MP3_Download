package com.example.dell.jiandiscover;

import android.content.Context;
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


public class MusicFragment extends Fragment implements View.OnClickListener {
    private File dirFile;
    private Button playBtn;
    private Button nextBtn;
    private File songFile;
    private TextView songNameTxt;
    private TextView ListSongTxt;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private File list[];
    private StringBuffer songList=new StringBuffer();
    public static int singIndex=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String viewMsg=(String) msg.obj;
            songNameTxt.setText(viewMsg);
        }
    };
    private   FileInputStream fileInputStream;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initMediaPath();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        initview(view);
        return view;
    }
    public void initview(View view)
    {
        playBtn = (Button) view.findViewById(R.id.btn_play);
        nextBtn = (Button) view.findViewById(R.id.btn_next);
        songNameTxt =(TextView)view.findViewById(R.id.txt_now_song_name);
        ListSongTxt =(TextView)view.findViewById(R.id.txt_list_song);
        playBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        ListSongTxt.setText(songList.toString());
        initMediaPlayer();
    }

    private void initMediaPath() {
        dirFile = new File(Environment.getExternalStorageDirectory() + "/Music");
        if(dirFile.exists()&& dirFile.isDirectory()) {
            list = dirFile.listFiles();
        }
        for(int i=0;i<list.length;i++){
            songList.append(list[i].getName().toString());
            songList.append("\n");
        }
    }

    private void initMediaPlayer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(list[singIndex]);
                    mediaPlayer.setDataSource(fileInputStream.getFD());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.obj=list[singIndex].getName().toString();
                System.out.print(message.toString());
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
                break;
            case R.id.btn_next:
               fileInputStream = null;
                try {
                    mediaPlayer.reset();//修改了路径后要记得reset
                    fileInputStream = new FileInputStream(list[(++singIndex)%list.length]);
                    mediaPlayer.setDataSource(fileInputStream.getFD());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.obj=list[singIndex%list.length].toString();
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
        songList.delete(0,songList.length()-1);
        singIndex=0;
    }
}
