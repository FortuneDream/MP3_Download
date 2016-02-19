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
    private EditText urlAddress;
    private EditText singName;
    private TextView info;
    private Button downloadButton;
    private String urladdress;
    private String singname;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 2:
                    info.setText((String)msg.obj);
                    break;
                case 1:
                    info.setText("文件已存在");
                    break;
                case -1:
                    info.setText("下载失败");
                    break;
                case 0:
                    info.setText("下载成功!");
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
        View view=inflater.inflate(R.layout.download, container, false);
        urlAddress=(EditText)view.findViewById(R.id.url_address);
        singName=(EditText) view.findViewById(R.id.sing_name);
        downloadButton=(Button)view.findViewById(R.id.download_button);
        info=(TextView)view.findViewById(R.id.info);
        downloadButton.setOnClickListener(this);
        return view;
    }

    @Override
    public synchronized void onClick(View v) {
        urladdress=urlAddress.getText().toString();
        singname=singName.getText().toString();
        //不能在oncreateview里面设置getText，因为此时的view已经返回了固定下来了，只能通过监听回调。
        new Thread(new Runnable() {
        @Override
            public void run() {
                if (urladdress.endsWith(".txt")) {
                    String response=httpDownloader.download(urladdress);
                    Message message =new Message();
                    message.obj=response;
                    message.what=2;
                    handler.sendMessage(message);
               }else if(urladdress.endsWith(".mp3")) {
                    int result=httpDownloader.downFile(urladdress, "Music/" ,  singname+".mp3");
                    Message message =new Message();
                    message.what=result;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
