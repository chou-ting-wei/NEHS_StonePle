package com.userwei;

public class ValueCalculate extends Thread{
    Thread thread;
    static final int INF = 1000007;

    static int characterLevel = 1, characterExp = 0, characterLife = 0, characterPercent = 0, characterExpPercent = 0;
    static boolean characterLifeChange = false;
    static boolean characterExpChange = false;

    // atk, def, lif, exp
    static int characterValue[][] = {
        {0, 0, 1, 1, 2, 2, 3, 4, 6, 7, 9},
        {0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8},
        {0, 10, 12, 14, 16, 18, 20, 25, 30, 35, 40},
        {0, 2, 6, 16, 30, 50, 70, 95, 125, 175, INF}
    };
  
    public void init(){
        characterLife = characterValue[2][characterLevel];
        characterPercent = (100 * characterLife) / characterValue[2][characterLevel];
    }

    public void run(){
        
        init();

        while(true){

            update();

            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if(characterLevel >= 5){
            FieldPanel.caveUnlocked = true;
        }
        while(characterExp >= characterValue[3][characterLevel]){
            characterExp -= characterValue[3][characterLevel];
            characterLevel ++;
            characterLife += characterValue[2][characterLevel] - characterValue[2][characterLevel - 1];
            characterExpChange = true;
        }
        if(characterLifeChange){
            characterLifeChange = false;
            characterPercent = (100 * characterLife) / characterValue[2][characterLevel];
        }
        if(characterExpChange){
            characterExpChange = false;
            characterExpPercent = (100 * characterExp) / characterValue[3][characterLevel];
        }
    }

    static public int characterAttackDamage(){
        // 角色攻擊度 + 武器攻擊力
        Weapon nowWeapon = UpgradePanel.weapon[UpgradePanel.weaponSelecting];
        int CharacterAtk = characterValue[0][characterLevel], weaponAtk = nowWeapon.atk;
        return CharacterAtk + weaponAtk;
    }

    static public int additionCharacterAttackDamage(){
        // 角色攻擊度 + 武器攻擊力
        Weapon nowWeapon = UpgradePanel.weapon[UpgradePanel.weaponSelecting];
        int CharacterAtk = characterValue[0][characterLevel], weaponAtk = nowWeapon.atk, additionWeaponAtk = nowWeapon.atkAddition;
        return CharacterAtk + weaponAtk + additionWeaponAtk;
    }
}
