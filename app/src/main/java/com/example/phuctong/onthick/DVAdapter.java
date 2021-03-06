package com.example.phuctong.onthick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class DVAdapter extends BaseAdapter {
    private int Layout;
    private FrnHome context;
    private List<com.example.phuctong.onthick.DV> listdv;
    private ImageButton btnxoa,btnsua;
    public DVAdapter(int layout, FrnHome context, List<com.example.phuctong.onthick.DV> listdv) {
        Layout = layout;
        this.context = context;
        this.listdv = listdv;
    }
    @Override
    public int getCount() {
        if(listdv.size() !=0 && !listdv.isEmpty())
        {
            return listdv.size();
        }
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(listdv.size() !=0 && !listdv.isEmpty())
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(Layout,parent,false);
        }
        final com.example.phuctong.onthick.DV dv=listdv.get(position);
        TextView txtTien=(TextView)convertView.findViewById(R.id.Txt_Tien);
        TextView txtGD=(TextView)convertView.findViewById(R.id.Txt_Tengd);
        String Tien =Double.toString(dv.getTien());

        txtTien.setText(Tien);
        txtGD.setText(dv.getDichvu());
        btnxoa=(ImageButton) convertView.findViewById(R.id.Img_Xoa);
        btnsua=(ImageButton) convertView.findViewById(R.id.Img_Sua);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.opendialog(dv);
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            context.Xoa(dv);
            }
        });
        return convertView;
    }
}
