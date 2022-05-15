package com.userwei;

public class ValueCalculate {
    Thread thread;

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

    static int characterDamage(int monsterDef){
        // (角色攻擊度 + 武器攻擊力 + 招式攻擊力) * 屬性加成 - 怪物防禦度
        Weapon nowWeapon = UpgradePanel.weapon[UpgradePanel.weaponSelecting];
        int CharacterAtk = , weaponAtk = nowWeapon.atk;
        return ;
    }
}
