package com.example.dell.jiandiscover;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/1/25.
 */
public class TabStudyWhatFragment extends Fragment {
    private List<String> list=new ArrayList<String>();
    private ListView listView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.viewpager_fragment_studywhat,container,false);
        listView=(ListView) view.findViewById(R.id.listview);
        StudyListAdapter adapter=new StudyListAdapter(getActivity(),R.layout.item_listview_study,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        }
        );//可设置事件监听
        return view;
    }
    private void initList(){
        String string_1="UI：\n" +
                "Inflate--->三个参数的使用\n" +
                "Menu--->onCreateOptionsMenu，menu资源文件\n" +
                "Toolbar--->style,theme\n" +
                "fragment中嵌套viewpager-->getChildFragmentManager()\n" +
                "animation动画--->Matirx 3*3图形二维变换\n";
        list.add(string_1);
        String string_2="SAX和pull解析XML,解析JSON\n" +
                "23种设计模式\n" +
                "线程的异步操作（Handler和AsyncTask）\n" +
                "服务Servicce和通知Notification基本操作\n" +
                "httpd服务器搭建\n" +
                "收发短信\n";
        list.add(string_2);
        String string_3="JAVA：\n" +
                "I/O流\n";
        list.add(string_3);
        String string_4="其他：\n" +
                "python基础\n"+
                "夭折的抢红包app（微信更新太快了）\n"+
                "口琴(大雾)\n";
        list.add(string_4);
    }
}
