package com.padassist.Util;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by DragonLotus on 8/25/2016.
 */

public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.d("Migration", "oldVersion is: " + oldVersion + " newVersion is: " + newVersion);
        RealmSchema schema = realm.getSchema();
        if(oldVersion == 0){
            schema.get("Team").addField("teamBadge", int.class);
            oldVersion++;
        }
    }
}