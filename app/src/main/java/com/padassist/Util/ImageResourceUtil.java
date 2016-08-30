package com.padassist.Util;

import android.util.Log;

import com.padassist.Data.Element;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.R;

import java.util.ArrayList;

import io.realm.Realm;

public class ImageResourceUtil {

    private static Realm realm = Realm.getDefaultInstance();
    
    public static int monsterAwakeningImageResource(int awakening){
        switch (awakening) {
            case 1:
                return R.drawable.awakening_1;
            case 2:
                return R.drawable.awakening_2;
            case 3:
               return R.drawable.awakening_3;
            case 4:
               return R.drawable.awakening_4;
            case 5:
               return R.drawable.awakening_5;
            case 6:
               return R.drawable.awakening_6;
            case 7:
               return R.drawable.awakening_7;
            case 8:
               return R.drawable.awakening_8;
            case 9:
               return R.drawable.awakening_9;
            case 10:
               return R.drawable.awakening_10;
            case 11:
               return R.drawable.awakening_11;
            case 12:
               return R.drawable.awakening_12;
            case 13:
               return R.drawable.awakening_13;
            case 14:
               return R.drawable.awakening_14;
            case 15:
               return R.drawable.awakening_15;
            case 16:
               return R.drawable.awakening_16;
            case 17:
               return R.drawable.awakening_17;
            case 18:
               return R.drawable.awakening_18;
            case 19:
               return R.drawable.awakening_19;
            case 20:
               return R.drawable.awakening_20;
            case 21:
               return R.drawable.awakening_21;
            case 22:
               return R.drawable.awakening_22;
            case 23:
               return R.drawable.awakening_23;
            case 24:
               return R.drawable.awakening_24;
            case 25:
               return R.drawable.awakening_25;
            case 26:
               return R.drawable.awakening_26;
            case 27:
               return R.drawable.awakening_27;
            case 28:
               return R.drawable.awakening_28;
            case 29:
               return R.drawable.awakening_29;
            case 30:
               return R.drawable.awakening_30;
            case 31:
               return R.drawable.awakening_31;
            case 32:
               return R.drawable.awakening_32;
            case 33:
               return R.drawable.awakening_33;
            case 34:
               return R.drawable.awakening_34;
            case 35:
               return R.drawable.awakening_35;
            case 36:
               return R.drawable.awakening_36;
            case 37:
               return R.drawable.awakening_37;
            case 38:
               return R.drawable.awakening_38;
            case 39:
               return R.drawable.awakening_39;
            case 40:
               return R.drawable.awakening_40;
            case 41:
               return R.drawable.awakening_41;
            case 42:
               return R.drawable.awakening_42;
            default:
               return R.drawable.awakening;
        }
    }
    
    public static int monsterAwakeningDisabledImageResource(int awakening){
        switch (awakening) {
            case 1:
                return R.drawable.awakening_1_disabled;
            case 2:
                return R.drawable.awakening_2_disabled;
            case 3:
                return R.drawable.awakening_3_disabled;
            case 4:
                return R.drawable.awakening_4_disabled;
            case 5:
                return R.drawable.awakening_5_disabled;
            case 6:
                return R.drawable.awakening_6_disabled;
            case 7:
                return R.drawable.awakening_7_disabled;
            case 8:
                return R.drawable.awakening_8_disabled;
            case 9:
                return R.drawable.awakening_9_disabled;
            case 10:
                return R.drawable.awakening_10_disabled;
            case 11:
                return R.drawable.awakening_11_disabled;
            case 12:
                return R.drawable.awakening_12_disabled;
            case 13:
                return R.drawable.awakening_13_disabled;
            case 14:
                return R.drawable.awakening_14_disabled;
            case 15:
                return R.drawable.awakening_15_disabled;
            case 16:
                return R.drawable.awakening_16_disabled;
            case 17:
                return R.drawable.awakening_17_disabled;
            case 18:
                return R.drawable.awakening_18_disabled;
            case 19:
                return R.drawable.awakening_19_disabled;
            case 20:
                return R.drawable.awakening_20_disabled;
            case 21:
                return R.drawable.awakening_21_disabled;
            case 22:
                return R.drawable.awakening_22_disabled;
            case 23:
                return R.drawable.awakening_23_disabled;
            case 24:
                return R.drawable.awakening_24_disabled;
            case 25:
                return R.drawable.awakening_25_disabled;
            case 26:
                return R.drawable.awakening_26_disabled;
            case 27:
                return R.drawable.awakening_27_disabled;
            case 28:
                return R.drawable.awakening_28_disabled;
            case 29:
                return R.drawable.awakening_29_disabled;
            case 30:
                return R.drawable.awakening_30_disabled;
            case 31:
                return R.drawable.awakening_31_disabled;
            case 32:
                return R.drawable.awakening_32_disabled;
            case 33:
                return R.drawable.awakening_33_disabled;
            case 34:
                return R.drawable.awakening_34_disabled;
            case 35:
                return R.drawable.awakening_35_disabled;
            case 36:
                return R.drawable.awakening_36_disabled;
            case 37:
                return R.drawable.awakening_37_disabled;
            case 38:
                return R.drawable.awakening_38_disabled;
            case 39:
                return R.drawable.awakening_39_disabled;
            case 40:
                return R.drawable.awakening_40_disabled;
            case 41:
                return R.drawable.awakening_41_disabled;
            case 42:
                return R.drawable.awakening_42_disabled;
            default:
                return R.drawable.awakening_disabled;
        }
    }
    
    public static int monsterLatentImageResource(int latent){
        switch (latent) {
            case 1:
                return R.drawable.latent_awakening_1;
            case 2:
                return R.drawable.latent_awakening_2;
            case 3:
                return R.drawable.latent_awakening_3;
            case 4:
                return R.drawable.latent_awakening_4;
            case 5:
                return R.drawable.latent_awakening_5;
            case 6:
                return R.drawable.latent_awakening_6;
            case 7:
                return R.drawable.latent_awakening_7;
            case 8:
                return R.drawable.latent_awakening_8;
            case 9:
                return R.drawable.latent_awakening_9;
            case 10:
                return R.drawable.latent_awakening_10;
            case 11:
                return R.drawable.latent_awakening_11;
            default:
                return R.drawable.latent_awakening_blank;

        }
    }
    
    public static int monsterTypeImageResource(int type){
        switch (type) {
            case 0:
                return R.drawable.type_evo_material;
            case 1:
                return R.drawable.type_balanced;
            case 2:
                return R.drawable.type_physical;
            case 3:
                return R.drawable.type_healer;
            case 4:
                return R.drawable.type_dragon;
            case 5:
                return R.drawable.type_god;
            case 6:
                return R.drawable.type_attacker;
            case 7:
                return R.drawable.type_devil;
            case 8:
                return R.drawable.type_machine;
            case 12:
                return R.drawable.type_awoken;
            case 14:
                return R.drawable.type_enhance_material;
            case 15:
                return R.drawable.type_vendor;
            default:
                return R.drawable.type_what_is_this;

        }
    }
}
