package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class BaseMonsterListRecycler extends RecyclerView.Adapter<BaseMonsterListRecycler.ViewHolder> {

    private ArrayList<BaseMonster> monsterList;
    private View.OnClickListener monsterListOnClickListener;
    private Context mContext;
    private LayoutInflater inflater;
    private Toast toast;

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
        }
    };

    public BaseMonsterListRecycler(Context context, ArrayList<BaseMonster> monsterList, View.OnClickListener monsterListOnClickListener){
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterId.setText("" + monsterList.get(position).getMonsterId());
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getHpMax()) + " / ");
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getAtkMax()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getRcvMax()));

        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterId.setVisibility(View.INVISIBLE);
            viewHolder.rarity.setVisibility(View.GONE);
            viewHolder.rarityStar.setVisibility(View.GONE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
        }else {
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.monsterId.setVisibility(View.VISIBLE);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
        }

        switch(monsterList.get(position).getType1()){
            case 0:
                viewHolder.type1.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type1.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type1.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type1.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type1.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type1.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type1.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type1.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type1.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type1.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type1.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type1.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type1.setVisibility(View.INVISIBLE);
                break;
        }
        switch(monsterList.get(position).getType2()){
            case 0:
                viewHolder.type2.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type2.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type2.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type2.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type2.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type2.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type2.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type2.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type2.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type2.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type2.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type2.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type2.setVisibility(View.INVISIBLE);
                break;
        }
        switch(monsterList.get(position).getType3()){
            case 0:
                viewHolder.type3.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type3.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type3.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type3.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type3.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type3.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type3.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type3.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type3.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type3.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type3.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type3.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type3.setVisibility(View.INVISIBLE);
                break;
        }

        viewHolder.monsterName.setSelected(true);
        viewHolder.itemView.setTag(R.string.index, position);
        viewHolder.itemView.setOnClickListener(monsterListOnClickListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.base_monster_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monsterName, monsterId, rarity, monsterHP, monsterATK, monsterRCV;
        ImageView monsterPicture, type1, type2, type3, rarityStar;

        public ViewHolder(View convertView){
            super(convertView);
            monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            monsterId = (TextView) convertView.findViewById(R.id.monsterId);
            rarity = (TextView) convertView.findViewById(R.id.rarity);
            monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            type1 = (ImageView) convertView.findViewById(R.id.type1);
            type2 = (ImageView) convertView.findViewById(R.id.type2);
            type3 = (ImageView) convertView.findViewById(R.id.type3);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
        }
    }

    public void notifyDataSetChanged(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }

    public ArrayList<BaseMonster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
    }

    public BaseMonster getItem(int position){
        return monsterList.get(position);
    }
}
