package com.example.dell.jiandiscover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2016/2/27.
 */
public class MusicListAdapter extends ArrayAdapter<SongItem> {
    private int resourced;
    public MusicListAdapter(Context context, int textViewResourceId, List<SongItem> objects) {
        super(context, textViewResourceId, objects);
        resourced=textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder{
            private TextView title;
            private TextView singer;
        }
        SongItem songItem=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
           view= LayoutInflater.from(getContext()).inflate(resourced,null);
            viewHolder=new ViewHolder();
            viewHolder.title=(TextView)view.findViewById(R.id.txt_song_name);
            viewHolder.singer=(TextView)view.findViewById(R.id.txt_singer_name);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.title.setText(songItem.getTitle());
        viewHolder.singer.setText(songItem.getSinger());
        return view;
    }
}
