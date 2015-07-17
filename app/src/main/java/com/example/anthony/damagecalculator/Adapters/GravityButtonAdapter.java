package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.anthony.damagecalculator.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by DragonLotus on 7/16/2015.
 */
public class GravityButtonAdapter extends ArrayAdapter<Integer> {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> gravityButtonList;
    private int resourceId;

    public GravityButtonAdapter(Context context, int textViewResourceId, ArrayList<Integer> gravityButtonList)
    {
        super(context, textViewResourceId, gravityButtonList);
        mContext = context;
        this.gravityButtonList = gravityButtonList;
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.gravityButton = (Button) convertView.findViewById(R.id.gravityButton);
            viewHolder.gravityButton.setClickable(false);
            viewHolder.gravityButton.setFocusable(false);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.gravityButton.setText(gravityButtonList.get(position).toString() + "%");

        return convertView;
    }

    static class ViewHolder
    {
        Button gravityButton;
    }


}
