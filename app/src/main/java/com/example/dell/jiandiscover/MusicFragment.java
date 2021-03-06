package com.example.dell.jiandiscover;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MusicFragment extends Fragment implements View.OnClickListener {
    private Button playBtn;
    private Button nextBtn;
    private TextView songNameTxt;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ListView listView;
    private List<SongItem> songItemList = new ArrayList<SongItem>();
    private boolean isResume;
    public int songIndex;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    seekBar.setProgress((Integer) msg.obj);
                    break;
                default:
                    break;

            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getAllMusic();
        isResume = true;
        songIndex = 0;
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        initMediaPlayer();
        initview(view);
        return view;
    }


    public void initview(View view) {
        listView = (ListView) view.findViewById(R.id.listview_song);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        playBtn = (Button) view.findViewById(R.id.btn_play);
        nextBtn = (Button) view.findViewById(R.id.btn_next);
        songNameTxt = (TextView) view.findViewById(R.id.txt_now_song_name);
        playBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        songNameTxt.setText(songItemList.get(songIndex).getTitle());
        seekBar.setProgress(0);
        listView.setAdapter(new MusicListAdapter(getActivity(), R.layout.item_listview_music, songItemList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();//修改了路径后要记得reset
                    mediaPlayer.setDataSource(songItemList.get(position).getUrl());
                    songIndex = position;
                    songNameTxt.setText(songItemList.get(songIndex).getTitle());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //手动调整进度条，音乐变化
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                   int dest = seekBar.getProgress();
                                                   int time = mediaPlayer.getDuration();//单位毫秒
                                                   int max = seekBar.getMax();
                                                   mediaPlayer.seekTo(time * dest / max);
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {

                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {

                                               }
                                           }
        );
    }

    private void Progress() {
        final int seconds = 500;
        new Thread() {
            @Override
            public void run() {
                while (isResume) {
                    try {
                        sleep(seconds);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isResume) {
                        int position = mediaPlayer.getCurrentPosition();//歌曲的总时间
                        int time = mediaPlayer.getDuration();//歌曲总时间
                        int max = seekBar.getMax();
                        int offset=position * max / time;
                        Message message=mHandler.obtainMessage();
                        message.what=0;
                        message.obj=offset;
                        mHandler.sendMessage(message);
                    }
                }
            }
        }.start();
    }
    private void getAllMusic() {
        ContentResolver cr = getActivity().getApplication().getContentResolver();
        if (cr == null) {
            return;
        }
        //获取所有歌曲
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return;
        }
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//歌曲名
                String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//歌手名
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));//专辑名
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));//歌曲大小
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//总播放长度
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//歌曲地址
                songItemList.add(new SongItem(title, singer, url));
            } while (cursor.moveToNext());
        }

    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(songItemList.get(songIndex).getUrl());
            mediaPlayer.prepare();//prepare是同步,prepareAsync异步
        } catch (Exception e) {
            e.printStackTrace();
        }
        Progress();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                }
                break;
            case R.id.btn_next:
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();//修改了路径后要记得reset
                    songIndex++;
                    if (songIndex >= songItemList.size()) {
                        songIndex = 0;
                    }
                    mediaPlayer.setDataSource(songItemList.get(songIndex).getUrl());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                songNameTxt.setText(songItemList.get(songIndex).getTitle());
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        Log.e("MusicFragment", "onPause");
        isResume = false;
        mediaPlayer.release();
        songItemList.clear();
        mediaPlayer = null;
        super.onPause();
    }

}
