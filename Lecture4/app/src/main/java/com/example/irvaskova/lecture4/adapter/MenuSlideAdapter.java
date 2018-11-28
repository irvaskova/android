package com.example.irvaskova.lecture4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.irvaskova.lecture4.R;
import com.example.irvaskova.lecture4.model.MenuSlideItem;

import java.util.List;

public class MenuSlideAdapter extends BaseAdapter{

    private Context context;
    private List<MenuSlideItem> lstItm;

    public MenuSlideAdapter(Context context, List<MenuSlideItem> lstItm) {
        this.context = context;
        this.lstItm = lstItm;
    }

    @Override

    public int getCount() {
        return lstItm.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View view1 = View.inflate(context, R.layout.item_sliding_menu, null);
        ImageView img1 = (ImageView)view1.findViewById(R.id.item_img);
        TextView txt1 = (TextView)view1.findViewById(R.id.item_title);

        MenuSlideItem item = lstItm.get(position);
        img1.setImageResource(item.getImgId());
        txt1.setText(item.getTitle());
        return view1;
    }
}
