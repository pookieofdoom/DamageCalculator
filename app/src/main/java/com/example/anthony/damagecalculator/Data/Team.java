package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 8/10/2015.
 */
public class Team implements Parcelable {
    private int teamHealth, teamRcv;
    private ArrayList<Integer> rowAwakenings, orbPlusAwakenings;

    public Team() {
        teamHealth = 0;
        teamRcv = 0;
    }

    public int getTeamHealth() {
        return teamHealth;
    }

    public void setTeamHealth(int teamHealth) {
        this.teamHealth = teamHealth;
    }

    public int getTeamRcv() {
        return teamRcv;
    }

    public void setTeamRcv(int teamRcv) {
        this.teamRcv = teamRcv;
    }

    public ArrayList<Integer> getRowAwakenings() {
        return rowAwakenings;
    }

    public void setRowAwakenings(ArrayList<Integer> rowAwakenings) {
        this.rowAwakenings = rowAwakenings;
    }

    public ArrayList<Integer> getOrbPlusAwakenings() {
        return orbPlusAwakenings;
    }

    public void setOrbPlusAwakenings(ArrayList<Integer> orbPlusAwakenings) {
        this.orbPlusAwakenings = orbPlusAwakenings;
    }

    public int getOrbPlusAwakenings(Color color){
        //Case Switch thing for each color. 5 elements for 5 colors. 1 red, 2 blue, 3 green, 4 light, 5 dark
        return 6;
    }

    public Team(Parcel source) {
        teamHealth = source.readInt();
        teamRcv = source.readInt();
        rowAwakenings = source.readArrayList(Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamHealth);
        dest.writeInt(teamRcv);
        dest.writeList(rowAwakenings);
    }

    public static final Parcelable.Creator<Team> CREATOR = new Creator<Team>() {
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
