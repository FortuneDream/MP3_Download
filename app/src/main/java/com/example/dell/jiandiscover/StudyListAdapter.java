package com.example.dell.jiandiscover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2016/2/11.
 */
public class StudyListAdapter extends ArrayAdapter<String>{
    private int resourceId;
    public StudyListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
        //resource=item layout
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder {
            TextView textView;
        }

        String string= getItem(position);//某一个item
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {//未缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);//动态加载视图
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.textview);
            view.setTag(viewHolder);//缓存实例
        } else {//已经缓存了
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(string);//取出viewholder中的实例，并给某一个item赋值
        return view;
    }
}
