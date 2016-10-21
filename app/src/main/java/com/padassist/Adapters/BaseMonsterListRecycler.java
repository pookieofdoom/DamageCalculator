package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.padassist.Data.BaseMonster;
import com.padassist.Util.BaseMonsterListRecyclerBase;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class BaseMonsterListRecycler extends BaseMonsterListRecyclerBase {

    public BaseMonsterListRecycler(Context context, ArrayList<BaseMonster> monsterList, RecyclerView monsterListView, View.OnClickListener monsterListOnClickListener, View.OnLongClickListener monsterListOnLongClickListener, boolean isGrid, ClearTextFocus clearTextFocus) {
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        this.monsterListOnLongClickListener = monsterListOnLongClickListener;
        this.monsterListView = monsterListView;
        this.isGrid = isGrid;
        this.clearTextFocus = clearTextFocus;

        fortyEightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, mContext.getResources().getDisplayMetrics());
        eightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics());
        fiftyFourDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 54, mContext.getResources().getDisplayMetrics());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


}
