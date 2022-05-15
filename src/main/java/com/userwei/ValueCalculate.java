package com.userwei;

public class ValueCalculate {
    Thread thread;

    static int characterLevel = 0, characterExp = 0;
    // atk, def, lif, exp
    static int characterValue[][] = {
        {0, 1, 1, 2, 2, 3, 4, 6, 7, 9},
        {0, 0, 1, 2, 3, 4, 5, 6, 7, 8},
        {10, 12, 14, 16, 18, 20, 25, 30, 35, 40},
        {0, 2, 6, 16, 30, 50, 70, 95, 125, 175}
    };

    ValueCalculate(){
        thread = new Thread(() -> {
            while(true){

                update();

                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void update(){
        
    }

    static public int characterAttackDamage(){
        // 角色攻擊度 + 武器攻擊力
        Weapon nowWeapon = UpgradePanel.weapon[UpgradePanel.weaponSelecting];
        int CharacterAtk = characterValue[0][characterLevel], weaponAtk = nowWeapon.atk;
        return CharacterAtk + weaponAtk;
    }

    // "slime", "brownie", "drackmage", "mimic", "overkilling_machine"
    static public int monsterAttackDamage(Monster nowMonster){
        if(!nowMonster.destroyed){

        }
        return 0;
    }
}
