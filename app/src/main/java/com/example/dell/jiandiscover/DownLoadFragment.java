package com.example.dell.jiandiscover;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import utils.HttpDownloader;

/**
 * Created by dell on 2016/1/25.
 */
public class DownLoadFragment extends Fragment implements View.OnClickListener {
    private EditText urlAddressEdt;
    private EditText songNameEdt;
    private TextView infoTxt;
    private Button downloadBtn;
    private String urladdress;
    private String singname;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 2:
                    infoTxt.setText((String) msg.obj);
                    break;
                case 1:
                    infoTxt.setText("文件已存在");
                    break;
                case -1:
                    infoTxt.setText("下载失败");
                    break;
                case 0:
                    infoTxt.setText("下载成功!");
                    break;
                default:
                    break;
            }
        }
    };
    private  HttpDownloader httpDownloader=new HttpDownloader();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_download, container, false);
        urlAddressEdt =(EditText)view.findViewById(R.id.edt_url_address);
        songNameEdt =(EditText) view.findViewById(R.id.edt_song_name);
        downloadBtn =(Button)view.findViewById(R.id.btn_download);
        infoTxt =(TextView)view.findViewById(R.id.txt_info);
        downloadBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public synchronized void onClick(View v) {
        urladdress= urlAddressEdt.getText().toString();
        singname= songNameEdt.getText().toString();
        //不能在oncreateview里面设置getText，因为此时的view已经返回了固定下来了，只能通过监听回调。
        new Thread(new Runnable() {
        @Override
            public void run() {
                if (urladdress.endsWith(".txt")) {
                    String response=httpDownloader.download(urladdress);
                    Message message =handler.obtainMessage();
                    message.obj=response;
                    message.what=2;
                    handler.sendMessage(message);
               }else if(urladdress.endsWith(".mp3")) {
                    int result=httpDownloader.downFile(urladdress, "Music/" ,  singname+".mp3");
                    Message message =handler.obtainMessage();
                    message.what=result;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
