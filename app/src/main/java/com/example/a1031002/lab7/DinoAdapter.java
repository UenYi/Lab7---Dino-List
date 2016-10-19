package com.example.a1031002.lab7;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 1031002 on 10/19/2016.
 */
public class DinoAdapter extends BaseAdapter {
    private Context context;
    private String[] names, infos;
    private int[] pics, icons;
    private static LayoutInflater inflater = null;

    public DinoAdapter(MainActivity mainActivity, String[] names, int[] pics, String[] infos, int[] icons) {
        this.names = names;
        this.pics = pics;
        this.icons = icons;
        this.infos = infos;
        this.context = mainActivity;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.dino_list, null);
        TextView tv = (TextView) rowView.findViewById(R.id.textView1);
        ImageView img = (ImageView) rowView.findViewById(R.id.imageView1);

        tv.setText(names[position]);
        img.setImageResource(icons[position]);

        final Intent intent = new Intent(context, DetailActivity.class);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name", names[position]);
                intent.putExtra("info", infos[position]);
                intent.putExtra("pic", pics[position]);

                context.startActivity(intent);
            }
        });

        return rowView;
    }

}




