package com.padassist.Util;

import android.util.Log;

import com.padassist.Data.Element;
import com.padassist.Data.Enemy;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Thomas on 7/11/2015.
 */
public class DamageCalculationUtil {

//    private static Realm realm = Realm.getDefaultInstance();

    public static double monsterElement1Damage(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos) {
        double totalOrbDamage = 0;
        int orbAwakenings = team.getOrbPlusAwakenings(monster.getElement1());
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getElement().equals(monster.getElement1())) {
                totalOrbDamage += orbMatch(monster.getTotalAtk(), orbMatchList.get(i), orbAwakenings, monster.getTPA());
            }
        }
        return Math.ceil(leadOtherMultiplier1(comboMultiplier(totalOrbDamage, monster, combos, true), monster, team, orbMatchList, position));
    }

    public static double monsterElement2Damage(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos) {
        double totalOrbDamage = 0;
        int orbAwakenings = team.getOrbPlusAwakenings(monster.getElement2());
        if (monster.getElement1().equals(monster.getElement2())) {
            for (int i = 0; i < orbMatchList.size(); i++) {
                if (orbMatchList.get(i).getElement().equals(monster.getElement2())) {
                    totalOrbDamage += orbMatch((int) Math.ceil(monster.getTotalAtk() * .1), orbMatchList.get(i), orbAwakenings, monster.getTPA());
                }
            }
            return Math.ceil(leadOtherMultiplier2(comboMultiplier(totalOrbDamage, monster, combos, true), monster, team, orbMatchList, position));
        }
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getElement().equals(monster.getElement2())) {
                totalOrbDamage += orbMatch((int) Math.ceil(monster.getTotalAtk() / 3), orbMatchList.get(i), orbAwakenings, monster.getTPA());
            }
        }
        return Math.ceil(leadOtherMultiplier2(comboMultiplier(totalOrbDamage, monster, combos, true), monster, team, orbMatchList, position));
    }

    public static double comboMultiplier(double damage, Monster monster, int combos, boolean isDamage) {
        double orbDamage = damage;
        if (isDamage) {
            int counter = 0;
            ArrayList<Integer> monsterAwakenings = monster.getAwakenings();
            if (monsterAwakenings.contains(43)) {
                if (combos > 6) {
                    for (int i = 0; i < monsterAwakenings.size(); i++) {
                        if (monsterAwakenings.get(i) == 43) {
                            counter++;
                        }
                    }
                    orbDamage *= Math.pow(2, counter);
                }
            }
        }

        return Math.ceil(orbDamage * ((combos - 1) * .25 + 1.0));
    }

    public static double orbMatch(int Attack, OrbMatch orbMatches, int OrbAwakenings, int TPAwakenings) {
        // Write in combos
        // Attack, Orb Awakenings, TPA from API, draw from Monster Database
        //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
        double damage = 0;
        if (orbMatches.getOrbsLinked() < 3) {
            throw new IllegalArgumentException();
        }
        if (orbMatches.getNumOrbPlus() == 0) {
            damage = Attack * (((orbMatches.getOrbsLinked() - 3) * .25) + 1);
        } else {
            damage = Attack * (((orbMatches.getOrbsLinked() - 3) * .25) + 1)
                    * ((orbMatches.getNumOrbPlus() * .06) + 1) * ((OrbAwakenings * .05) + 1);
        }
        if (orbMatches.getOrbsLinked() == 4) {
            damage = damage * Math.pow(1.5, TPAwakenings);
        }

        return Math.ceil(damage);
    }

    public static double leadOtherMultiplier1(double damage, Monster monster, Team team, RealmResults<OrbMatch> orbMatchList, int position) {
        //No active skill multiplier
        double returnDamage = damage;
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getElement().equals(monster.getElement1())) {
                if (orbMatchList.get(i).isRow()) {
                    counter++;
                }
            }
        }
        //Need to choose the correct multiplier for the monster
        returnDamage = returnDamage * team.getAtk1Multiplier().get(position) * (team.getRowAwakenings(monster.getElement1()) * 0.1 * counter + 1);

        if (Singleton.getInstance().isEnableMultiplier()) {
            Boolean affected = false;
            if (Singleton.getInstance().getExtraElementMultiplier().contains(monster.getElement1())) {
                returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                affected = true;
            }
            if (Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType1()) || Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType2()) || Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType3()) && !affected) {
                if (!affected) {
                    returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                }
            }
        }
        if (team.getTeamBadge() == 6 && !Singleton.getInstance().isCoopEnable()) {
            return (int) (returnDamage * 1.05);
        } else if (team.getTeamBadge() == 14 && !Singleton.getInstance().isCoopEnable()) {
            return (int) (returnDamage * 1.15);
        } else {
            return returnDamage;
        }
    }

    public static double leadOtherMultiplier2(double damage, Monster monster, Team team, RealmResults<OrbMatch> orbMatchList, int position) {
        //No active skill multiplier
        double returnDamage = damage;
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getElement().equals(monster.getElement2())) {
                if (orbMatchList.get(i).isRow()) {
                    counter++;
                }
            }
        }
        // Need to choose the correct multiplier for the monster
        returnDamage = returnDamage * team.getAtk2Multiplier().get(position) * (team.getRowAwakenings(monster.getElement2()) * 0.1 * counter + 1);

        if (Singleton.getInstance().isEnableMultiplier()) {
            Boolean affected = false;
            if (Singleton.getInstance().getExtraElementMultiplier().contains(monster.getElement2())) {
                returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                affected = true;
            }
            if (Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType1()) || Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType2()) || Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType3())) {
                if (!affected) {
                    returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                }
            }
        }
        if (team.getTeamBadge() == 6 && !Singleton.getInstance().isCoopEnable()) {
            return (int) (returnDamage * 1.05);
        } else {
            return returnDamage;
        }
    }

    public static double monsterElement1DamageEnemy(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement1DamageEnemyElement(team, orbMatchList, monster, position, combos, enemy);
        if (damage == 0) {
            return 0;
        }
        damage = monsterDamageEnemyDefense(damage, team, monster, enemy);
        return damage;
    }

    public static double monsterElement2DamageEnemy(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement2DamageEnemyElement(team, orbMatchList, monster, position, combos, enemy);
        if (damage == 0) {
            return 0;
        }

        damage = monsterDamageEnemyDefense(damage, team, monster, enemy);
        return damage;
    }

    public static double monsterElement1DamageEnemyElement(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement1Damage(team, orbMatchList, monster, position, combos);
        if (damage == 0) {
            return 0;
        }
        if (monster.getElement1().equals(Element.RED)) {
            if (enemy.getCurrentElement().getElement().equals(Element.BLUE)) {
                damage = damage / 2;
            } else if (enemy.getCurrentElement().getElement().equals(Element.GREEN)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.BLUE)) {
            if (enemy.getCurrentElement().getElement().equals(Element.GREEN)) {
                damage = damage / 2;
            } else if (enemy.getCurrentElement().getElement().equals(Element.RED)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.GREEN)) {
            if (enemy.getCurrentElement().getElement().equals(Element.RED)) {
                damage = damage / 2;
            } else if (enemy.getCurrentElement().getElement().equals(Element.BLUE)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.LIGHT)) {
            if (enemy.getCurrentElement().getElement().equals(Element.DARK)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.DARK)) {
            if (enemy.getCurrentElement().getElement().equals(Element.LIGHT)) {
                damage = damage * 2;
            }
        }
        damage = killerCalculation(damage, monster, enemy);

        return damage;
    }

    public static double monsterElement2DamageEnemyElement(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement2Damage(team, orbMatchList, monster, position, combos);
        if (damage == 0) {
            return 0;
        }
        if (monster.getElement2().equals(Element.RED)) {
            if (enemy.getCurrentElement().getElement().equals(Element.BLUE)) {
                damage = damage / 2;
            } else if (enemy.getCurrentElement().getElement().equals(Element.GREEN)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.BLUE)) {
            if (enemy.getCurrentElement().getElement().equals(Element.GREEN)) {
                damage = damage / 2;
            } else if (enemy.getCurrentElement().getElement().equals(Element.RED)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.GREEN)) {
            if (enemy.getCurrentElement().getElement().equals(Element.RED)) {
                damage = damage / 2;
            } else if (enemy.getCurrentElement().getElement().equals(Element.BLUE)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.LIGHT)) {
            if (enemy.getCurrentElement().getElement().equals(Element.DARK)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.DARK)) {
            if (enemy.getCurrentElement().getElement().equals(Element.LIGHT)) {
                damage = damage * 2;
            }
        }
        damage = killerCalculation(damage, monster, enemy);

        return damage;
    }

    public static double monsterDamageEnemyDefense(double damage, Team team, Monster monster, Enemy enemy) {
        int counter = 0;
        ArrayList<Integer> monsterAwakenings = monster.getAwakenings();
        if (monsterAwakenings.contains(44)) {
            ArrayList<Element> matchElements = new ArrayList<>();
            matchElements.add(Element.RED);
            matchElements.add(Element.BLUE);
            matchElements.add(Element.GREEN);
            matchElements.add(Element.LIGHT);
            matchElements.add(Element.DARK);
            for(int i = 0; i < matchElements.size(); i++){
                if(team.getOrbMatchElements().contains(matchElements.get(i))){
                    counter++;
                }
            }
            Log.d("DamageCalcUtil", "Match elements counter is: " + counter);
            if(counter == matchElements.size()){
                counter = 0;
                for (int i = 0; i < monsterAwakenings.size(); i++) {
                    if (monsterAwakenings.get(i) == 44) {
                        counter++;
                    }
                }
                Log.d("DamageCalcUtil", "Counter is: " + counter);
                return damage - enemy.getTargetDef()*(1-.5*counter);
            } else if (damage - enemy.getTargetDef() < 0) {
                return 1;
            }

        } else if (damage - enemy.getTargetDef() < 0) {
            return 1;
        }
        return damage - enemy.getTargetDef();
    }

    public static double monsterElement1DamageReduction(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement1DamageEnemyElement(team, orbMatchList, monster, position, combos, enemy);
        if (damage == 0) {
            return 0;
        }
        if (enemy.getReduction().isEmpty()) {
            return monsterDamageEnemyDefense(damage, team, monster, enemy);
        } else if (enemy.reductionContains(monster.getElement1()) && enemy.isHasReduction()) {
            return monsterDamageEnemyDefense(damage * (100 - enemy.getReductionValue()) / 100, team, monster, enemy);
        } else return monsterDamageEnemyDefense(damage, team, monster, enemy);
    }

    public static double monsterElement2DamageReduction(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement2DamageEnemyElement(team, orbMatchList, monster, position, combos, enemy);
        if (damage == 0) {
            return 0;
        }
        if (enemy.getReduction().isEmpty() || enemy.getReduction() == null) {
            return monsterDamageEnemyDefense(damage, team, monster, enemy);
        } else if (enemy.reductionContains(monster.getElement2()) && enemy.isHasReduction()) {
            return monsterDamageEnemyDefense(damage * (100 - enemy.getReductionValue()) / 100, team, monster, enemy);
        } else return monsterDamageEnemyDefense(damage, team, monster, enemy);
    }

    public static double monsterElement1DamageAbsorb(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement1DamageReduction(team, orbMatchList, monster, position, combos, enemy);
        if (enemy.absorbContains(monster.getElement1()) && enemy.isHasAbsorb()) {
            return damage * -1;
        }
        return damage;
    }

    public static double monsterElement2DamageAbsorb(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement2DamageReduction(team, orbMatchList, monster, position, combos, enemy);
        if (enemy.absorbContains(monster.getElement2()) && enemy.isHasAbsorb()) {
            return damage * -1;
        } else return damage;
    }

    public static double monsterElement1DamageThreshold(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement1DamageAbsorb(team, orbMatchList, monster, position, combos, enemy);
        if (damage >= enemy.getDamageThreshold()) {
            return (damage * -1);
        }
        return damage;
    }

    public static double monsterElement2DamageThreshold(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement2DamageAbsorb(team, orbMatchList, monster, position, combos, enemy);
        if (damage >= enemy.getDamageThreshold()) {
            return (damage * -1);
        }
        return damage;
    }

    public static double monsterElement1DamageImmunity(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement1DamageAbsorb(team, orbMatchList, monster, position, combos, enemy);
        if (damage >= enemy.getDamageImmunity()) {
            return 0;
        }
        return damage;
    }

    public static double monsterElement2DamageImmunity(Team team, RealmResults<OrbMatch> orbMatchList, Monster monster, int position, int combos, Enemy enemy) {
        double damage = monsterElement2DamageAbsorb(team, orbMatchList, monster, position, combos, enemy);
        if (damage >= enemy.getDamageImmunity()) {
            return 0;
        }
        return damage;
    }

    public static double hpRecovered(Team team, RealmResults<OrbMatch> orbMatchList, int combos) {
        double totalRcv = 0;
        ArrayList<OrbMatch> heartOrbMatches = new ArrayList<>();
        ArrayList<OrbMatch> poisonOrbMatches = new ArrayList<>();
//        RealmResults<OrbMatch> heartResults = realm.where(OrbMatch.class).equalTo("elementInt", 5).findAll();
//        RealmResults<OrbMatch> poisonResults = realm.where(OrbMatch.class).equalTo("elementInt", 7).or().equalTo("elementInt", 8).findAll();
//        for (int i = 0; i < heartResults.size(); i++) {
//            heartOrbMatches.add(realm.copyFromRealm(heartResults.get(i)));
//        }
//        for (int i = 0; i < poisonResults.size(); i++) {
//            poisonOrbMatches.add(realm.copyFromRealm(poisonResults.get(i)));
//        }

        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getElement().equals(Element.HEART)) {
                heartOrbMatches.add(orbMatchList.get(i));
            } else if (orbMatchList.get(i).getElement().equals(Element.POISON) ||
                    orbMatchList.get(i).getElement().equals(Element.MORTAL_POISON)) {
                poisonOrbMatches.add(orbMatchList.get(i));
            }
        }

//        for (int i = 0; i < realm.where(OrbMatch.class).findAll().size(); i++) {
//            if (realm.where(OrbMatch.class).findAll().get(i).getElement().equals(Element.HEART)) {
//               // totalOrbDamage += orbMatch(rcv, realm.where(OrbMatch.class).findAll().get(i));
//                heartOrbMatches.add(realm.where(OrbMatch.class).findAll().get(i));
//            } else if (realm.where(OrbMatch.class).findAll().get(i).getElement().equals(Element.POISON) || realm.where(OrbMatch.class).findAll().get(i).getElement().equals(Element.MORTAL_POISON)){
//                poisonOrbMatches.add(realm.where(OrbMatch.class).findAll().get(i));
//            }
//        }
        for (int i = 0; i < team.getMonsters().size(); i++) {
            double totalOrbDamage = 0;
            double rcv = team.getMonsters().get(i).getTotalRcv() * team.getRcvMultiplier().get(i);
            for (int j = 0; j < heartOrbMatches.size(); j++) {
                totalOrbDamage += orbMatch((int) Math.floor(rcv + 0.5d), heartOrbMatches.get(j));
            }
            totalRcv += comboMultiplier(totalOrbDamage, null, combos, false);
        }
        return totalRcv - poisonDamage(team, poisonOrbMatches);
    }

    public static double orbMatch(int rcv, OrbMatch orbMatch) {
        // Write in combos
        // Attack, Orb Awakenings, TPA from API, draw from Monster Database
        //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
        if (orbMatch.getOrbsLinked() < 3) {
            throw new IllegalArgumentException();
        }
        double heal = 0;
        if (orbMatch.getNumOrbPlus() == 0) {
            heal = rcv * (((orbMatch.getOrbsLinked() - 3) * .25) + 1);
        } else {
            heal = rcv * (((orbMatch.getOrbsLinked() - 3) * .25) + 1)
                    * ((orbMatch.getNumOrbPlus() * .06) + 1);
        }
        return Math.ceil(heal);
    }

    public static double poisonDamage(Team team, ArrayList<OrbMatch> poisonOrbMatches) {
        if (poisonOrbMatches.size() == 0) {
            return 0;
        }
        double damage = 0;
        double teamHealth = team.getTeamHealth();
        for (int i = 0; i < poisonOrbMatches.size(); i++) {
            if (poisonOrbMatches.get(i).getElement().equals(Element.POISON)) {
                damage += ((poisonOrbMatches.get(i).getOrbsLinked() - 3) * .05 + .20) * teamHealth;
            } else if (poisonOrbMatches.get(i).getElement().equals(Element.MORTAL_POISON)) {
                damage += ((poisonOrbMatches.get(i).getOrbsLinked() - 3) * .125 + .50) * teamHealth;
            }
        }
        return damage;
    }

//    public static double finalMultiplier(double leadMul, double extraMul, int rowAwakenings, int numberOfRows) {
//        //Extra multiplier can be skills
//        //Lead multiplier needs conditions
//        double multiplier;
//        if (numberOfRows == 0) {
//            multiplier = leadMul * extraMul;
//        } else {
//            multiplier = leadMul * extraMul * ((rowAwakenings * 0.1) * numberOfRows + 1);
//        }
//        return multiplier;
//    }

    public static double monsterStatCalc(int minimumStat, int maximumStat, int currentLevel, int maxLevel, double statScale) {
        if (currentLevel <= 1) {
            return minimumStat;
        }
        //return (int) Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double) ((currentLevel - 1) / (maxLevel - 1)), statScale)));
        return Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double) (currentLevel - 1) / (maxLevel - 1), statScale)));
    }

    public static int monsterHpCalc(Monster monster, Team team, RealmResults<OrbMatch> orbMatchList, int position) {
        double monsterHp = 0;
        monsterHp = monster.getTotalHp();
        monsterHp = monsterHp * team.getHpMultiplier().get(position);
        return (int) Math.ceil(monsterHp);
    }

    public static double monsterRcvCalc(Monster monster, Team team, RealmResults<OrbMatch> orbMatchList, int position) {
        double monsterRcv;
        monsterRcv = monster.getTotalRcv() * team.getRcvMultiplier().get(position);
//        Log.d("DamageCalcUtil", "LeadSkill is: " + team.getLeadSkill().getName() + " rcvSkillType is: " + team.getLeadSkill().getRcvSkillType());
//        if(team.getLeadSkill().getRcvSkillType() != null){
//            if(team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.FLAT) || team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.MONSTER_CONDITIONAL) || team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.HP_FLAT) || team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.CO_OP)){
//                monsterRcv *= team.getLeadSkill().rcvMultiplier(monster, team);
//            }
//        }
//        if(team.getHelperSkill().getRcvSkillType() != null){
//            if(team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.FLAT) || team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.MONSTER_CONDITIONAL) || team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.HP_FLAT) || team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.CO_OP)){
//                monsterRcv *= team.getHelperSkill().rcvMultiplier(monster, team);
//            }
//        }
//        Log.d("Damage Calc Util", "Leadskill is: " + team.getLeadSkill() + " RcvData is: " + team.getLeadSkill().getRcvData() + " multiplier is: " + team.getLeadSkill().rcvMultiplier(monster, team));
        return monsterRcv;
    }

    private static double killerCalculation(double damage, Monster monster, Enemy enemy) {
        double killerDamage = damage;
        int counter;
        ArrayList<Integer> trimmedKillerAwakenings = new ArrayList<>();
        if (monster.getKillerAwakenings().size() != 0) {
            for (int i = 0; i < monster.getKillerAwakenings().size(); i++) {
                if (!trimmedKillerAwakenings.contains(monster.getKillerAwakenings().get(i).getValue())) {
                    trimmedKillerAwakenings.add(monster.getKillerAwakenings().get(i).getValue());
                }
            }
            for (int i = 0; i < trimmedKillerAwakenings.size(); i++) {
                counter = 0;
                switch (trimmedKillerAwakenings.get(i)) {
                    case 31:
                        if (enemy.typeContains(4)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 32:
                        if (enemy.typeContains(5)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 33:
                        if (enemy.typeContains(7)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 34:
                        if (enemy.typeContains(8)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 35:
                        if (enemy.typeContains(6)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 36:
                        if (enemy.typeContains(2)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 37:
                        if (enemy.typeContains(3)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 38:
                        if (enemy.typeContains(1)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 39:
                        if (enemy.typeContains(12)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 40:
                        if (enemy.typeContains(14)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 41:
                        if (enemy.typeContains(15)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                    case 42:
                        if (enemy.typeContains(0)) {
                            for (int j = 0; j < monster.getKillerAwakenings().size(); j++) {
                                if (trimmedKillerAwakenings.get(i) == monster.getKillerAwakenings().get(j).getValue()) {
                                    counter++;
                                }
                            }
                            killerDamage *= Math.pow(3, counter);
                        }
                        break;
                }
            }
        }

        ArrayList<Integer> latentKillerAwakenings = new ArrayList<>();
        if (monster.getTotalPlus() == 297) {
            for (int i = 0; i < monster.getLatents().size(); i++) {
                if (monster.getLatents().get(i).getValue() > 12 && !latentKillerAwakenings.contains(monster.getLatents().get(i).getValue())) {
                    latentKillerAwakenings.add(monster.getLatents().get(i).getValue());
                }
            }
        } else {
            for (int i = 0; i < monster.getLatents().size() - 1; i++) {
                if (monster.getLatents().get(i).getValue() > 12 && !latentKillerAwakenings.contains(monster.getLatents().get(i).getValue())) {
                    latentKillerAwakenings.add(monster.getLatents().get(i).getValue());
                }
            }
        }
        if (latentKillerAwakenings.size() != 0) {
            for (int i = 0; i < latentKillerAwakenings.size(); i++) {
                counter = 0;
                switch (latentKillerAwakenings.get(i)) {
                    case 13:
                        if (enemy.typeContains(5)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 14:
                        if (enemy.typeContains(4)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 15:
                        if (enemy.typeContains(7)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 16:
                        if (enemy.typeContains(8)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 17:
                        if (enemy.typeContains(1)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 18:
                        if (enemy.typeContains(6)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 19:
                        if (enemy.typeContains(2)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                    case 20:
                        if (enemy.typeContains(3)) {
                            for (int j = 0; j < monster.getLatents().size(); j++) {
                                if (monster.getLatents().get(j).getValue() == latentKillerAwakenings.get(i)) {
                                    counter++;
                                }
                            }
                            counter = counter / 2;
                            killerDamage *= Math.pow(1.5, counter);
                        }
                        break;
                }
            }
        }
        return killerDamage;
    }
}
