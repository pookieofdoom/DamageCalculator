package com.padassist.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Fragments.MonsterPageFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class MonsterListRecycler extends RecyclerView.Adapter<MonsterListRecycler.ViewHolder> {
    private RealmList<Monster> monsterList;
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> latentList;
    private Team team;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            Parcelable teamParcel = Parcels.wrap(team);
            Singleton.getInstance().setMonsterOverwrite(holder.getAdapterPosition());
            if (monsterList.get(holder.getAdapterPosition()).getMonsterId() == 0) {
                ((MainActivity) mContext).switchFragment(MonsterTabLayoutFragment.newInstance(false, 0, holder.getAdapterPosition(), teamParcel), MonsterTabLayoutFragment.TAG, "good");
            } else {
                Parcelable monsterParcel = Parcels.wrap(monsterList.get(holder.getAdapterPosition()));
                ((MainActivity) mContext).switchFragment(MonsterPageFragment.newInstance(monsterList.get(holder.getAdapterPosition()).getMonsterId(), holder.getAdapterPosition(), monsterParcel, teamParcel), MonsterPageFragment.TAG, "good");

//                ((MainActivity) mContext).switchFragment(MonsterPageFragment.newInstance(realm.copyFromRealm(monsterList.get(holder.getAdapterPosition())), holder.getAdapterPosition()), MonsterPageFragment.TAG, "good");
            }
        }
    };

    private View.OnLongClickListener onItemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            Singleton.getInstance().setMonsterOverwrite(holder.getAdapterPosition());
            Parcelable teamParcel = Parcels.wrap(team);
            ((MainActivity) mContext).switchFragment(MonsterTabLayoutFragment.newInstance(false, monsterList.get(holder.getAdapterPosition()).getMonsterId(), holder.getAdapterPosition(), teamParcel), MonsterTabLayoutFragment.TAG, "good");
            return true;
        }
    };

    public MonsterListRecycler(Context context, RealmList<Monster> monsterList, Team team) {
        mContext = context;
        this.monsterList = monsterList;
        this.team = team;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());

        if(monsterList.get(position).getMonsterInherit() != null && monsterList.get(position).getMonsterInherit().getMonsterId() != 0){
            viewHolder.monsterPicture.setBackgroundResource(R.drawable.portrait_stroke_small);
        } else {
            viewHolder.monsterPicture.setBackgroundResource(0);
        }

        viewHolder.monsterPicture.setImageBitmap(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getTotalAtk()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getTotalRcv()));
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getTotalHp()) + " / ");
        viewHolder.monsterAwakenings.setText(" " + Integer.toString(monsterList.get(position).getCurrentAwakenings()));
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterLevelValue.setText(Integer.toString(monsterList.get(position).getCurrentLevel()));
        viewHolder.monsterPlus.setText(" +" + Integer.toString(monsterList.get(position).getTotalPlus()) + " ");
        viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);
        if (monsterList.get(position).getCurrentAwakenings() >= monsterList.get(position).getMaxAwakenings()) {
            viewHolder.monsterAwakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monsterAwakenings.setText("");
        }

        if (latentList == null) {
            latentList = new ArrayList<>();
        } else {
            latentList.clear();
        }

        if(monsterList.get(position).getTotalPlus() == 297){
            for (int i = 0; i < monsterList.get(position).getLatents().size(); i++) {
                if (monsterList.get(position).getLatents().get(i).getValue() != 0) {
                    latentList.add(1);
                }
            }
        } else {
            for (int i = 0; i < monsterList.get(position).getLatents().size() - 1; i++) {
                if (monsterList.get(position).getLatents().get(i).getValue() != 0) {
                    latentList.add(1);
                }
            }
        }
        if(latentList.size() == 0){
            viewHolder.monsterLatents.setVisibility(View.INVISIBLE);
        } else if(monsterList.get(position).getTotalPlus() == 297){
            if(latentList.size() == 6){
                viewHolder.monsterLatents.setBackgroundResource(R.drawable.latent_max);
                viewHolder.monsterLatents.setText("");
                viewHolder.monsterLatents.setVisibility(View.VISIBLE);
            } else {
                viewHolder.monsterLatents.setText(" " + latentList.size());
                viewHolder.monsterLatents.setVisibility(View.VISIBLE);
            }
        } else {
            if(latentList.size() == 5){
                viewHolder.monsterLatents.setBackgroundResource(R.drawable.latent_max);
                viewHolder.monsterLatents.setText("");
                viewHolder.monsterLatents.setVisibility(View.VISIBLE);
            } else {
                viewHolder.monsterLatents.setText(" " + latentList.size());
                viewHolder.monsterLatents.setVisibility(View.VISIBLE);
            }
        }

        if (monsterList.get(position).getTotalPlus() == 0) {
            viewHolder.monsterPlus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monsterPlus.setVisibility(View.VISIBLE);
        }
        if (monsterList.get(position).getCurrentAwakenings() == 0) {
            viewHolder.monsterAwakenings.setVisibility(View.INVISIBLE);
            if (latentList.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monsterAwakenings.getLayoutParams();
                viewHolder.monsterLatents.setLayoutParams(z);
            }
        } else {
            viewHolder.monsterAwakenings.setVisibility(View.VISIBLE);
        }

        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.monsterLevelValue.setVisibility(View.INVISIBLE);
            viewHolder.monsterHP.setVisibility(View.INVISIBLE);
            viewHolder.monsterATK.setVisibility(View.INVISIBLE);
            viewHolder.monsterRCV.setVisibility(View.INVISIBLE);
            viewHolder.monsterLevel.setVisibility(View.INVISIBLE);
            viewHolder.type1.setVisibility(View.INVISIBLE);
            viewHolder.type2.setVisibility(View.INVISIBLE);
            viewHolder.rarity.setVisibility(View.INVISIBLE);
            viewHolder.rarityStar.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monsterLevelValue.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
            viewHolder.monsterLevel.setVisibility(View.VISIBLE);
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
        }

        if (monsterList.get(position).getType2() == -1 && monsterList.get(position).getMonsterId() != 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.type1.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            viewHolder.type1.setLayoutParams(params);
            viewHolder.type1.setVisibility(View.VISIBLE);
        }

        if (monsterList.get(position).getType3() == -1) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.type2.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            viewHolder.type2.setLayoutParams(params);
            viewHolder.type2.setVisibility(View.VISIBLE);
        }
        if(monsterList.get(position).getType1() >= 0){
            viewHolder.type1.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getType1()));
            viewHolder.type1.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type1.setVisibility(View.GONE);
        }
        if(monsterList.get(position).getType2() >= 0){
            viewHolder.type2.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getType2()));
            viewHolder.type2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type2.setVisibility(View.GONE);
        }
        if(monsterList.get(position).getType3() >= 0){
            viewHolder.type3.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getType3()));
            viewHolder.type3.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type3.setVisibility(View.GONE);
        }
        viewHolder.monsterName.setHorizontallyScrolling(true);
        viewHolder.monsterName.setSelected(true);

        viewHolder.itemView.setOnClickListener(onItemClickListener);
        viewHolder.itemView.setOnLongClickListener(onItemLongClickListener);
        viewHolder.itemView.setTag(viewHolder);

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.monster_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monsterName, monsterPlus, monsterAwakenings, monsterLevelValue, monsterHP, monsterATK, monsterRCV, monsterLevel, rarity, monsterLatents;
        ImageView monsterPicture, type1, type2, type3, rarityStar;
        RelativeLayout relativeLayout;

        public ViewHolder(View convertView) {
            super(convertView);
            monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            monsterPlus = (TextView) convertView.findViewById(R.id.monsterPlus);
            monsterAwakenings = (TextView) convertView.findViewById(R.id.monsterAwakenings);
            monsterLatents = (TextView) convertView.findViewById(R.id.monsterLatents);
            monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            monsterLevelValue = (TextView) convertView.findViewById(R.id.monsterLevelValue);
            monsterLevel = (TextView) convertView.findViewById(R.id.monsterLevel);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            type1 = (ImageView) convertView.findViewById(R.id.type1);
            type2 = (ImageView) convertView.findViewById(R.id.type2);
            type3 = (ImageView) convertView.findViewById(R.id.type3);
            rarity = (TextView) convertView.findViewById(R.id.rarity);
            rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
            relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
        }
    }

    public void updateList(RealmList<Monster> monsterList) {
        this.monsterList.clear();
        this.monsterList.addAll(monsterList);
        notifyDataSetChanged();
    }
}
