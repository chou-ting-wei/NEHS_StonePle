package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.Graphics;

public class CavePanel extends JPanel implements KeyListener{
    String mapName[][] = {
        {"null", "null", "null", "maze(DR).png", "maze(UD).png", "maze(UR).png"},
        {"null", "maze(DR).png", "maze(UD).png", "maze(ULR).png", "null", "maze(LR).png"},
        {"maze(D).png", "maze(ULR).png", "null", "maze(LDR).png", "maze(UD).png", "maze(ULDR).png"},
        {"null", "maze(L).png", "null", "maze(LR).png", "null", "maze(L).png"},
        {"null", "null", "maze(D).png", "maze(UL).png", "null", "null"}
    };
    static Map allMap[][];

    Background allMapBackground[][][];
    int allMapBackgroundCount[][];

    static Monster allMonster[][][];
    static int allMonsterCount[][];

    boolean allMapMoveJudge[][][][];

    static int mapState_i = 2, mapState_j = 5;
    // static int mapState_i = 2, mapState_j = 0; // test
    int mapSizeX = 5, mapSizeY = 6;

    // character Front : 0 up, 1 left, 2 down, 3 right
    static Character character1;
    int characterFront = 0;
    static int characterInitX = 640, characterInitY = 640;
    static Map map;

    Background inventory;
    Material herb;
    Font hpbar, expbar1, expbar2, herbcount;

    static Background CharacterAttackBackground[][];
    static Background MonsterAttackBackground[][];

    static boolean ChkCharacterAttackBackground[][];
    static boolean ChkMonsterAttackBackground[][];

    static boolean CharacterAttackChange;
    boolean MonsterAttackChange;

    JFrame mainFrame, pauseScreen, fieldScreen, caveScreen, backpackScreen;
    static boolean Start;
    Thread thread, monsterThread, CharacterAttackThread, MonsterAttackThread;

    public void addMoveJudge(int mapx, int mapy, int x1, int x2, int y1, int y2){
        for(int i = x1; i <= x2; i ++){
            for(int j = y1; j <= y2; j ++){
                allMapMoveJudge[mapx][mapy][i][j] = true;
            }
        }
    }

    public void addBackground(int mapx, int mapy, int x, int y, int w, int h, String s){
        int nowMapBackgroundCount = allMapBackgroundCount[mapx][mapy];
        Background nowBackground = new Background(x, y, w, h, s);
        allMapBackground[mapx][mapy][nowMapBackgroundCount] = nowBackground;

        allMapBackgroundCount[mapx][mapy] ++;
    }

    public void addMonster(int mapx, int mapy, int x, int y, int w, int h, String s){
        int nowMonsterCount = allMonsterCount[mapx][mapy];
        Monster nowMonster = new Monster(x, y, 80, 80, w, h, s);
        allMonster[mapx][mapy][nowMonsterCount] = nowMonster;

        allMonsterCount[mapx][mapy] ++;
    }

    // {"null", "null", "null", "maze(DR).png", "maze(UD).png", "maze(UR).png"},
    // {"null", "maze(DR).png", "maze(UD).png", "maze(ULR).png", "null", "maze(LR).png"},
    // {"maze(D).png", "maze(ULR).png", "null", "maze(LDR).png", "maze(UD).png", "maze(ULDR).png"},
    // {"null", "maze(L).png", "null", "maze(LR).png", "null", "maze(L).png"},
    // {"null", "null", "maze(D).png", "maze(UL).png", "null", "null"}

    public void init(){
        allMap = new Map[mapSizeX][mapSizeY];
        allMapBackground = new Background[mapSizeX][mapSizeY][70];
        allMapBackgroundCount = new int[mapSizeX][mapSizeY];
        allMapMoveJudge = new boolean[mapSizeX][mapSizeY][16][9];

        allMonster = new Monster[mapSizeX][mapSizeY][70];
        allMonsterCount = new int[mapSizeX][mapSizeY];

        CharacterAttackBackground = new Background[16][9];
        MonsterAttackBackground = new Background[16][9];

        ChkCharacterAttackBackground = new boolean[16][9];
        ChkMonsterAttackBackground = new boolean[16][9];

        for(int i = mapSizeX - 1; i >= 0; i --){
            for(int j = 0; j < mapSizeY; j ++){
                if(mapName[i][j] != "null"){
                    Map nowMap = new Map(0, 0, 1280, 720, mapName[i][j]);
                    allMap[i][j] = nowMap;
                }
            }
        }

        inventory = new Background(1210, 650, 60, 60, "inventory.png");
        herb = new Material(1220, 655, 40, 40, 0, "herb.png" );
        herbcount = new Font(1173, 690, 60, 15, BackpackPanel.getMaterialAmount("herb") + ".png" );
        expbar1 = new Font(1135, 0, 40, 20, "level" + ValueCalculate.characterLevel+ ".png" );
        expbar2 = new Font(1175, 0, 105, 20, "lv" + ValueCalculate.characterExpPercent + ".png" );

        // 0-3
            // mimic
            addMonster(0, 3, 80, 80, 80, 80, "mimic.gif");
            addMonster(0, 3, 1120, 80, 80, 80, "mimic.gif");
            addMonster(0, 3, 80, 560, 80, 80, "mimic.gif");
            addMonster(0, 3, 1120, 560, 80, 80, "mimic.gif");

        // 0-4
            // chest 7-4
            addBackground(0, 4, 560, 320, 80, 80, "chest_close.png");
            addMoveJudge(0, 4, 7, 7, 4, 4);

        // 0-5
            // drackmage
            for(int i = 3; i <= 5; i ++){
                addMonster(0, 5, 80, 80 * i, 80, 80, "drackmage.gif");
            }

        // 1-1
            for(int i = 6; i <= 9; i ++){
                for(int j = 3; j <= 5; j ++){
                    addMonster(1, 1, 80 * i, 80 * j, 80, 80, "drackmage.gif");
                }
            }

        // 1-2
            // mimic
            addMonster(1, 2, 80, 80, 80, 80, "mimic.gif");
            addMonster(1, 2, 1120, 80, 80, 80, "mimic.gif");

        // 1-3
            // chest 1-1
            addBackground(1, 3, 80, 80, 80, 80, "chest_close.png");
            addMoveJudge(1, 3, 1, 1, 1, 1);

            // drackmage
            addMonster(1, 3, 160, 80, 80, 80, "drackmage.gif");
            for(int i = 1; i <= 2; i ++){
                addMonster(1, 3, 80 * i, 160, 80, 80, "drackmage.gif");
            }

            // slime
            for(int i = 1; i <= 2; i ++){
                addMonster(1, 3, 240, 80 * i, 80, 80, "slime.gif");
            }
            for(int i = 1; i <= 3; i ++){
                addMonster(1, 3, 80 * i, 240, 80, 80, "slime.gif");
            }

        // 1-5
            // NULL

        // 2-0
            // overkilling_machine
            addMonster(2, 0, 560, 240, 160, 160, "overkilling_machine.gif");

        // 2-1
            // NULL

        // 2-3
            // drackmage
            for(int i = 7; i <= 8; i ++){
                addMonster(2, 3, 80 * i, 240, 80, 80, "drackmage.gif");
            }

        // 2-4
            // chest 7-4
            addBackground(2, 4, 560, 320, 80, 80, "chest_close.png");
            addMoveJudge(2, 4, 7, 7, 4, 4);

        // 2-5
            // transport door
            addBackground(2, 5, 1120, 560, 80, 80, "transport_door.png");
            addMoveJudge(2, 5, 14, 14, 7, 7);

        // 3-1
            // chest 11-4
            addBackground(3, 1, 880, 320, 80, 80, "chest_close.png");
            addMoveJudge(3, 1, 11, 11, 4, 4);

        // 3-3
            // mimic
            for(int i = 3; i <= 5; i ++){
                addMonster(3, 3, 160, 80 * i, 80, 80, "mimic.gif");
            }

        // 3-5
            // NULL

        // 4-2
            // chest 7-4, 8-4
            addBackground(4, 2, 560, 320, 80, 80, "chest_close.png");
            addBackground(4, 2, 640, 320, 80, 80, "chest_close.png");
            addMoveJudge(4, 2, 7, 8, 4, 4);

        // 4-3
            // drackmage
            for(int i = 7; i <= 8; i ++){
                addMonster(4, 3, 80 * i, 320, 80, 80, "drackmage.gif");
            }

    }

    static public void reset(){
        map = allMap[mapState_i][mapState_j];
        character1 = new Character(characterInitX, characterInitY, 80, 80, 80, 80, "walk.gif");
    }

    CavePanel(JFrame mainFrame, JFrame pauseScreen, JFrame fieldScreen, JFrame caveScreen, JFrame backpackScreen){
        this.fieldScreen = fieldScreen;
        this.pauseScreen = pauseScreen;
        this.mainFrame = mainFrame;
        this.caveScreen = caveScreen;
        this.backpackScreen = backpackScreen;

        addKeyListener(this);
        setFocusable(true);
        
        init();
        reset();

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

        monsterThread = new Thread(() -> {
            while(true){
                monsterMove();
                repaint();

                try{
                    Thread.sleep(800);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        monsterThread.start();

        CharacterAttackThread = new Thread(() -> {
            while(true){
                if(CharacterAttackChange){
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    CharacterAttackChange = false;
                    for(int i = 0; i < 16; i ++){
                        for(int j = 0; j < 9; j ++){
                            ChkCharacterAttackBackground[i][j] = false;
                        }
                    }
                }
                repaint();
                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        CharacterAttackThread.start();

        MonsterAttackThread = new Thread(() -> {
            while(true){
                if(MonsterAttackChange){
                    try{
                        Thread.sleep(50);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    MonsterAttackChange = false;
                    for(int i = 0; i < 16; i ++){
                        for(int j = 0; j < 9; j ++){
                            ChkMonsterAttackBackground[i][j] = false;
                        }
                    }
                }
                repaint();
                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        MonsterAttackThread.start();
    }

    public void update(){
        if(ValueCalculate.characterPercent >= 0){
            hpbar = new Font(0, 0, 180, 30, "hp" + ValueCalculate.characterPercent + ".png");
            repaint();
        }
        else{
            hpbar = new Font(0, 0, 180, 30, "hpRevive.png");
            repaint();
        }
        if(BackpackPanel.getMaterialAmount("herb") < 0){
            herbcount = new Font(1180, 690, 60, 15, "EOF.png");
            repaint();
        }
        else if(BackpackPanel.getMaterialAmount("herb") <= 99){
            herbcount = new Font(1173, 690, 60, 15, BackpackPanel.getMaterialAmount("herb") + ".png" );
            repaint();
        }
        else{
            herbcount = new Font(1180, 690, 60, 15, "99+.png");
            repaint();
        }
        expbar1 = new Font(1135, 0, 40, 20, "level" + ValueCalculate.characterLevel+ ".png" );
        expbar2 = new Font(1175, 0, 105, 20, "lv" + ValueCalculate.characterExpPercent + ".png" );
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        for(int i = 0; i < 16; i ++){
            for(int j = 0; j < 9; j ++){
                Background nowBackground = CharacterAttackBackground[i][j];
                if(ChkCharacterAttackBackground[i][j]){
                    nowBackground.draw(g, this);
                }
            }
        }
        for(int i = 0; i < 16; i ++){
            for(int j = 0; j < 9; j ++){
                Background nowBackground = MonsterAttackBackground[i][j];
                if(ChkMonsterAttackBackground[i][j]){
                    nowBackground.draw(g, this);
                }
            }
        }
        character1.draw(g, this);
        for(int i = 0; i < allMapBackgroundCount[mapState_i][mapState_j]; i ++){
            Background nowbBackground = allMapBackground[mapState_i][mapState_j][i];
            nowbBackground.draw(g, this);
        }
        for(int i = 0; i < allMonsterCount[mapState_i][mapState_j]; i ++){
            Monster nowMonster = allMonster[mapState_i][mapState_j][i];
            nowMonster.draw(g, this);
        }
        hpbar.draw(g, this);
        inventory.draw(g, this);
        herb.draw(g, this);
        herbcount.draw(g, this);
        expbar1.draw(g, this);
        expbar2.draw(g, this);
    }

    public int randomNumber(int srt, int end){
        return (int)(Math.random() * (end - srt + 1)) + srt;
    }

    public boolean AttackJudge(int x, int y){
        String nowMap = mapName[mapState_i][mapState_j].substring(5, 9);
        // ULDR
        boolean mapOpen[] = {false, false, false, false};
        for(int i = 0; i < 4; i ++){
            char nowChar = nowMap.charAt(i);
            if(nowChar == ')'){
                break;
            }
            else{
                if(nowChar == 'U'){
                    mapOpen[0] = true;
                }
                else if(nowChar == 'L'){
                    mapOpen[1] = true;
                }
                else if(nowChar == 'D'){
                    mapOpen[2] = true;
                }
                else if(nowChar == 'R'){
                    mapOpen[3] = true;
                }
            }
        }
        if(mapOpen[0] && (edgeJudge(x, 6, 9) && edgeJudge(y, 0, 0))){
            return true;
        }
        if(mapOpen[1] && (edgeJudge(x, 0, 0) && edgeJudge(y, 3, 5))){
            return true;
        }
        if(mapOpen[2] && (edgeJudge(x, 6, 9) && edgeJudge(y, 8, 8))){
            return true;
        }
        if(mapOpen[3] && (edgeJudge(x, 15, 15) && edgeJudge(y, 3, 5))){
            return true;
        }
        if(x <= 0 || y <= 0 || x >= 1200 || y >= 640){
            return false;
        }
        return !allMapMoveJudge[mapState_i][mapState_j][x / 80][y / 80];
    }

    // character Front : 0 up, 1 left, 2 down, 3 right
    public void characterAttack(){
        int cx = character1.x, cy = character1.y;
        int nowMonsterCount = allMonsterCount[mapState_i][mapState_j];

        int addx, addy;
        if(characterFront == 0){
            addx = cx;
            addy = cy - 80;
        }
        else if(characterFront == 1){
            addx = cx - 80;
            addy = cy;
        }
        else if(characterFront == 2){
            addx = cx;
            addy = cy + 80;
        }
        else{
            addx = cx + 80;
            addy = cy;
        }

        for(int i = cx - 80; i <= cx + 80; i += 80){
            for(int j = cy - 80; j <= cy + 80; j += 80){
                if(!AttackJudge(i, j)){
                    continue;
                }
                if(i == cx && j == cy){
                    continue;
                }
                if(i == addx && j == addy){
                    Background nowBackground = new Background(i, j, 80, 80, "character_addition_attack.png");
                    CharacterAttackBackground[i / 80][j / 80] = nowBackground;
                    ChkCharacterAttackBackground[i / 80][j / 80] = true;
                }
                else{
                    // Background nowBackground = new Background(i, j, 80, 80, "character_attack.png");
                    // CharacterAttackBackground[i / 80][j / 80] = nowBackground;
                    // ChkCharacterAttackBackground[i / 80][j / 80] = true;
                }
                repaint();
                for(int k = 0; k < nowMonsterCount; k ++){
                    Monster nowMonster = allMonster[mapState_i][mapState_j][k];
                    int mx = nowMonster.x, my = nowMonster.y;
                    if(!nowMonster.destroyed && Start){
                        if(i == mx && j == my){
                            if(i == addx && j == addy){
                                nowMonster.nowlif -= ValueCalculate.additionCharacterAttackDamage();
                            }
                            else{
                                // nowMonster.nowlif -= ValueCalculate.characterAttackDamage();
                            }
                        }
                        else if(nowMonster.monsterIdx == 4){
                            if(i == addx + 80 && j == addy){
                                nowMonster.nowlif -= ValueCalculate.additionCharacterAttackDamage();
                            }
                            if(i == addx && j == addy + 80){
                                nowMonster.nowlif -= ValueCalculate.additionCharacterAttackDamage();
                            }
                            if(i == addx + 80 && j == addy + 80){
                                nowMonster.nowlif -= ValueCalculate.additionCharacterAttackDamage();
                            }
                        }
                    }
                }
            }
        }
        CharacterAttackChange = true;      
    }

    public void monsterAttack(Monster nowMonster){
        int cx = character1.x, cy = character1.y, mx = nowMonster.x, my = nowMonster.y;
        int relatx = (cx - (mx - 80)), relaty = (cy - (my - 80));

        if(!nowMonster.destroyed && Start){
            if(nowMonster.monsterIdx == 0 || nowMonster.monsterIdx == 2){
                if(relatx / 80 >= 0 && relatx / 80 < 3 && relaty / 80 >= 0 && relaty / 80 < 3){
                    int dmg = nowMonster.atk[relatx / 80][relaty / 80];
                    if(dmg > 0){
                        if(cx == character1.x && cy == character1.y && mx == nowMonster.x && my == nowMonster.y){
                            Background nowBackground = new Background(cx, cy, 80, 80, "monster_attack.png");
                            MonsterAttackBackground[cx / 80][cy / 80] = nowBackground;
                            ChkMonsterAttackBackground[cx / 80][cy / 80] = true;
                            ValueCalculate.characterLife -= dmg;
                            ValueCalculate.characterLifeChange = true;
                            repaint();
                        }
                    }
                }
            }
            if(nowMonster.monsterIdx == 1){
                if(relatx / 80 >= 0 && relatx / 80 < 3 && relaty / 80 >= 0 && relaty / 80 < 3){
                    int dmg = nowMonster.atk[relatx / 80][relaty / 80];
                    if(dmg > 0){
                        if(cx == character1.x && cy == character1.y && mx == nowMonster.x && my == nowMonster.y){
                            if((mx - 80) >= 0 && (mx - 80) <= 1200){
                                Background nowBackground = new Background(mx - 80, my, 80, 80, "monster_attack.png");
                                MonsterAttackBackground[(mx - 80) / 80][my / 80] = nowBackground;
                                ChkMonsterAttackBackground[(mx - 80) / 80][my / 80] = true;
                            }
                            if((my - 80) >= 0 && (my - 80) <= 640){
                                Background nowBackground = new Background(mx, my - 80, 80, 80, "monster_attack.png");
                                MonsterAttackBackground[mx / 80][(my - 80) / 80] = nowBackground;
                                ChkMonsterAttackBackground[mx / 80][(my - 80) / 80] = true;
                            }
                            if((mx + 80) >= 0 && (mx + 80) <= 1200){
                                Background nowBackground = new Background(mx + 80, my, 80, 80, "monster_attack.png");
                                MonsterAttackBackground[(mx + 80) / 80][my / 80] = nowBackground;
                                ChkMonsterAttackBackground[(mx + 80) / 80][my / 80] = true;
                            }
                            if((my + 80) >= 0 && (my + 80) <= 640){
                                Background nowBackground = new Background(mx, my + 80, 80, 80, "monster_attack.png");
                                MonsterAttackBackground[mx / 80][(my + 80) / 80] = nowBackground;
                                ChkMonsterAttackBackground[mx / 80][(my + 80) / 80] = true;
                            }
                            ValueCalculate.characterLife -= dmg;
                            ValueCalculate.characterLifeChange = true;
                            repaint();
                        }
                    }
                }
            }
            if(nowMonster.monsterIdx == 3){
                if(relatx / 80 >= 0 && relatx / 80 < 3 && relaty / 80 >= 0 && relaty / 80 < 3){
                    int dmg = nowMonster.atk[relatx / 80][relaty / 80];
                    if(dmg > 0){
                        if(cx == character1.x && cy == character1.y && mx == nowMonster.x && my == nowMonster.y){
                            for(int i = mx - 80; i <= mx + 80; i += 80){
                                for(int j = my - 80; j <= my + 80; j += 80){
                                    if(i == mx && j == my){
                                        continue;
                                    }
                                    if(AttackJudge(i, j) && monsterJudge(i / 80, j / 80)){
                                        Background nowBackground = new Background(i, j, 80, 80, "monster_attack.png");
                                        MonsterAttackBackground[i / 80][j / 80] = nowBackground;
                                        ChkMonsterAttackBackground[i / 80][j / 80] = true;
                                    }
                                }
                            }
                            ValueCalculate.characterLife -= dmg;
                            ValueCalculate.characterLifeChange = true;
                            repaint();
                        }
                    }
                }
            }
        }
        MonsterAttackChange = true;
    }

    public void monsterMove(){
        int nowMonsterCount = allMonsterCount[mapState_i][mapState_j];
        for(int i = 0; i < nowMonsterCount; i ++){
            Monster nowMonster = allMonster[mapState_i][mapState_j][i];
            if(!nowMonster.destroyed && Start){
                if(nowMonster.monsterIdx == 4){
                    BOSS(nowMonster);
                }
                else{
                    int cx = character1.x, cy = character1.y, mx = nowMonster.x, my = nowMonster.y;
                    int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
                    if(((distx == 1 && disty == 0) || (distx == 0 && disty == 1)) || (distx == 1 && disty == 1 && nowMonster.monsterIdx == 3)){
                        monsterAttack(nowMonster);
                    }
                    else if(Math.max(distx, disty) <= 4){
                        int rand = randomNumber(1, 100);
                        boolean test = (distx == disty ? true : false);

                        if(distx > disty || (test && rand % 2 == 0)){
                            boolean moved = false;
                            if(cx > mx){
                                if(moveJudge(mx + nowMonster.movX, my, "monster")){
                                    nowMonster.x += nowMonster.movX;
                                    moved = true;
                                }
                            }
                            else if(cx < mx){
                                if(moveJudge(mx - nowMonster.movX, my, "monster")){
                                    nowMonster.x -= nowMonster.movX;
                                    moved = true;
                                }
                            }
                            else{
                                if(!moved){
                                    if(cy > my){
                                        if(moveJudge(mx, my + nowMonster.movY, "monster")){
                                            nowMonster.y += nowMonster.movY;
                                            moved = true;
                                        }
                                    }
                                    else if(cy < my){
                                        if(moveJudge(mx, my - nowMonster.movY, "monster")){
                                            nowMonster.y -= nowMonster.movY;
                                            moved = true;
                                        }
                                    }
                                }
                            }
                        }
                        else if(distx < disty || (test && rand % 2 == 1)){
                            boolean moved = false;
                            if(cy > my){
                                if(moveJudge(mx, my + nowMonster.movY, "monster")){
                                    nowMonster.y += nowMonster.movY;
                                    moved = true;
                                }
                            }
                            else if(cy < my){
                                if(moveJudge(mx, my - nowMonster.movY, "monster")){
                                    nowMonster.y -= nowMonster.movY;
                                    moved = true;
                                }
                            }
                            else{
                                if(!moved){
                                    if(cx > mx){
                                        if(moveJudge(mx + nowMonster.movX, my, "monster")){
                                            nowMonster.x += nowMonster.movX;
                                            moved = true;
                                        }
                                    }
                                    else if(cx < mx){
                                        if(moveJudge(mx - nowMonster.movX, my, "monster")){
                                            nowMonster.x -= nowMonster.movX;
                                            moved = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        nowMonster.nowlif = nowMonster.maxlif;
                        nowMonster.x = nowMonster.initX;
                        nowMonster.y = nowMonster.initY;
                    }
                }
                repaint();
            }
        }
    }

    public void BOSSAttack(Monster nowMonster, int lastcx, int lastcy, int lastmx, int lastmy){
        int relatx = (lastcx - (lastmx - 80)), relaty = (lastcy - (lastmy - 80));
        if(!nowMonster.destroyed && Start){
            int dmg = nowMonster.atk[relatx / 80][relaty / 80];
            if(dmg > 0){
                if(lastcx == character1.x && lastcy == character1.y && lastmx == nowMonster.x && lastmy == nowMonster.y){
                    for(int i = lastmx - 80; i <= lastmx + 160; i += 80){
                        for(int j = lastmy - 80; j <= lastmy + 160; j += 80){
                            if(i == lastmx && j == lastmy){
                                continue;
                            }
                            if(i == lastmx + 80 && j == lastmy){
                                continue;
                            }
                            if(i == lastmx && j == lastmy + 80){
                                continue;
                            }
                            if(i == lastmx + 80 && j == lastmy + 80){
                                continue;
                            }
                            if(AttackJudge(i, j) && monsterJudge(i / 80, j / 80)){
                                if(i == lastmx - 80 && j == lastmy - 80){
                                    Background nowBackground = new Background(i, j, 80, 80, "monster_addition_attack.png");
                                    MonsterAttackBackground[i / 80][j / 80] = nowBackground;
                                    ChkMonsterAttackBackground[i / 80][j / 80] = true;
                                }
                                else if(i == lastmx - 80 && j == lastmy + 160){
                                    Background nowBackground = new Background(i, j, 80, 80, "monster_addition_attack.png");
                                    MonsterAttackBackground[i / 80][j / 80] = nowBackground;
                                    ChkMonsterAttackBackground[i / 80][j / 80] = true;
                                }
                                else if(i == lastmx + 160 && j == lastmy - 80){
                                    Background nowBackground = new Background(i, j, 80, 80, "monster_addition_attack.png");
                                    MonsterAttackBackground[i / 80][j / 80] = nowBackground;
                                    ChkMonsterAttackBackground[i / 80][j / 80] = true;
                                }
                                else if(i == lastmx + 160 && j == lastmy + 160){
                                    Background nowBackground = new Background(i, j, 80, 80, "monster_addition_attack.png");
                                    MonsterAttackBackground[i / 80][j / 80] = nowBackground;
                                    ChkMonsterAttackBackground[i / 80][j / 80] = true;
                                }
                                else{
                                    Background nowBackground = new Background(i, j, 80, 80, "monster_attack.png");
                                    MonsterAttackBackground[i / 80][j / 80] = nowBackground;
                                    ChkMonsterAttackBackground[i / 80][j / 80] = true;
                                }
                            }
                        }
                    }
                    ValueCalculate.characterLife -= dmg;
                    ValueCalculate.characterLifeChange = true;
                    repaint();
                }
            }
        }
        MonsterAttackChange = true;
    }

    public void BOSS(Monster nowMonster){
        int cx = character1.x, cy = character1.y, mx = nowMonster.x, my = nowMonster.y;
        if(cx < mx && cy < my){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 1 && disty == 1){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if(Math.max(distx, disty) <= 4){
                int rand = randomNumber(1, 100);
                if(rand % 2 == 0){
                    if(moveJudge(mx - nowMonster.movX, my, "boss")){
                        nowMonster.x -= nowMonster.movX;
                    }
                }
                else{
                    if(moveJudge(my - nowMonster.movY, my, "boss")){
                        nowMonster.y -= nowMonster.movY;
                    }
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx < mx && cy <= my + 80){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 1 && disty == 0 || distx == 1 && disty == 1){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if(distx <= 4){
                if(moveJudge(mx - nowMonster.movX, my, "boss")){
                    nowMonster.x -= nowMonster.movX;
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx <= mx + 80 && cy < my){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 0 && disty == 1 || distx == 1 && disty == 1){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if(disty <= 4){
                if(moveJudge(my - nowMonster.movY, my, "boss")){
                    nowMonster.y -= nowMonster.movY;
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx < mx && cy > my + 80){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 1 && disty == 2){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if(Math.max(distx, disty - 1) <= 4){
                int rand = randomNumber(1, 100);
                if(rand % 2 == 0){
                    if(moveJudge(mx - nowMonster.movX, my, "boss")){
                        nowMonster.x -= nowMonster.movX;
                    }
                }
                else{
                    if(moveJudge(my + nowMonster.movY, my, "boss")){
                        nowMonster.y += nowMonster.movY;
                    }
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx > mx + 80 && cy < my){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            
            if(distx == 2 && disty == 1){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if(Math.max(distx - 1, disty) <= 4){
                int rand = randomNumber(1, 100);
                if(rand % 2 == 0){
                    if(moveJudge(mx + nowMonster.movX, my, "boss")){
                        nowMonster.x += nowMonster.movX;
                    }
                }
                else{
                    if(moveJudge(my - nowMonster.movY, my, "boss")){
                        nowMonster.y -= nowMonster.movY;
                    }
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx <= mx + 80 && cy > my + 80){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 0 && disty == 2 || distx == 1 && disty == 2){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if((disty - 2) <= 4){
                if(moveJudge(my + nowMonster.movY, my, "boss")){
                    nowMonster.y += nowMonster.movY;
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx > mx + 80 && cy <= my + 80){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 2 && disty == 0 || distx == 2 && disty == 1){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if((distx - 2) <= 4){
                if(moveJudge(mx + nowMonster.movX, my, "boss")){
                    nowMonster.x += nowMonster.movX;
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        else if(cx > mx + 80 && cy > my + 80){
            int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
            if(distx == 2 && disty == 2){
                BOSSAttack(nowMonster, cx, cy, mx, my);
            }
            else if(Math.max(distx - 1, disty - 1) <= 4){
                int rand = randomNumber(1, 100);
                if(rand % 2 == 0){
                    if(moveJudge(mx + nowMonster.movX, my, "boss")){
                        nowMonster.x += nowMonster.movX;
                    }
                }
                else{
                    if(moveJudge(my + nowMonster.movY, my, "boss")){
                        nowMonster.y += nowMonster.movY;
                    }
                }
            }
            else{
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
        repaint();
    }

    static public void monsterReset(){
        int nowMonsterCount = allMonsterCount[mapState_i][mapState_j];
        for(int i = 0; i < nowMonsterCount; i ++){
            Monster nowMonster = allMonster[mapState_i][mapState_j][i];
            if(!nowMonster.destroyed){
                nowMonster.nowlif = nowMonster.maxlif;
                nowMonster.x = nowMonster.initX;
                nowMonster.y = nowMonster.initY;
            }
        }
    }

    public boolean monsterJudge(int x, int y){
        int nowMonsterCount = allMonsterCount[mapState_i][mapState_j];
        for(int i = 0; i < nowMonsterCount; i ++){
            Monster nowMonster = allMonster[mapState_i][mapState_j][i];
            if(x == (nowMonster.x / 80) && y == (nowMonster.y / 80) && !nowMonster.destroyed){
                return false;
            }
            if(nowMonster.monsterIdx == 4){
                if(x == (nowMonster.x / 80) + 1 && y == (nowMonster.y / 80) && !nowMonster.destroyed){
                    return false;
                }
                if(x == (nowMonster.x / 80) && y == (nowMonster.y / 80) + 1 && !nowMonster.destroyed){
                    return false;
                }
                if(x == (nowMonster.x / 80) + 1 && y == (nowMonster.y / 80) + 1 && !nowMonster.destroyed){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean mapJudge(int dx, int dy){
        if(mapState_i + dx < 0 || mapState_i + dx >= mapSizeX){
            return false;
        }
        if(mapState_j + dy < 0 || mapState_j + dy >= mapSizeY){
            return false;
        }
        if(mapName[mapState_i + dx][mapState_j + dy] == "null"){
            return false;
        }
        monsterReset();
        mapState_i += dx;
        mapState_j += dy;
        return true;
    }

    public boolean edgeJudge(int k, int srt, int end){
        for(int i = srt; i <= end; i ++){
            if(k / 80 == i){
                return true;
            }
        }
        return false;
    }

    // chest pos : [0][4] 7-4, [1][3] 1-1, [2][4] 7-4, [3][1] 11-4, [4][2] 7-4, [4][2] 8-4
    public boolean moveJudge(int x, int y, String s){
        String nowMap = mapName[mapState_i][mapState_j].substring(5, 9);
        // ULDR
        boolean mapOpen[] = {false, false, false, false};
        for(int i = 0; i < 4; i ++){
            char nowChar = nowMap.charAt(i);
            if(nowChar == ')'){
                break;
            }
            else{
                if(nowChar == 'U'){
                    mapOpen[0] = true;
                }
                else if(nowChar == 'L'){
                    mapOpen[1] = true;
                }
                else if(nowChar == 'D'){
                    mapOpen[2] = true;
                }
                else if(nowChar == 'R'){
                    mapOpen[3] = true;
                }
            }
        }
        if(mapOpen[0] && (edgeJudge(x, 6, 9) && edgeJudge(y, 0, 0)) && s == "character"){
            return true;
        }
        if(mapOpen[1] && (edgeJudge(x, 0, 0) && edgeJudge(y, 3, 5)) && s == "character"){
            return true;
        }
        if(mapOpen[2] && (edgeJudge(x, 6, 9) && edgeJudge(y, 8, 8)) && s == "character"){
            return true;
        }
        if(mapOpen[3] && (edgeJudge(x, 15, 15) && edgeJudge(y, 3, 5)) && s == "character"){
            return true;
        }
        if(x == 0 || y == 0 || x == 1200 || y == 640){
            return false;
        }
        if(mapState_i == 2 && mapState_j == 5 && edgeJudge(x, 14, 14) && edgeJudge(y, 7, 7) && s == "character"){
            monsterReset();
            Start = false;
            JFrame nowFrame = GamePanel.frame[3];
            FieldPanel.Start = true;
            GamePanel.switchState(3);
            nowFrame.setVisible(true);
            caveScreen.setVisible(false);
            return false;
        }
        else if(mapState_i == 0 && mapState_j == 4 && edgeJudge(x, 7, 7) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[0][4];
            if(allMapBackground[0][4][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(560, 320, 80, 80, "chest_open.png");
                allMapBackground[0][4][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("iron", 10);
            }
        }
        else if(mapState_i == 1 && mapState_j == 3 && edgeJudge(x, 1, 1) && edgeJudge(y, 1, 1) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[1][3];
            if(allMapBackground[1][3][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(80, 80, 80, 80, "chest_open.png");
                allMapBackground[1][3][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("wood", 10);
            }
        }
        else if(mapState_i == 2 && mapState_j == 4 && edgeJudge(x, 7, 7) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[2][4];
            if(allMapBackground[2][4][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(560, 320, 80, 80, "chest_open.png");
                allMapBackground[2][4][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("iron", 10);
            }
        }
        else if(mapState_i == 3 && mapState_j == 1 && edgeJudge(x, 11, 11) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[3][1];
            if(allMapBackground[3][1][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(880, 320, 80, 80, "chest_open.png");
                allMapBackground[3][1][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("herb", 20);
            }
        }
        else if(mapState_i == 4 && mapState_j == 2 && edgeJudge(x, 7, 7) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[4][2];
            if(allMapBackground[4][2][nowBackgroundCount - 2].name != "chest_open.png"){
                Background newBackground = new Background(560, 320, 80, 80, "chest_open.png");
                allMapBackground[4][2][nowBackgroundCount - 2] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("coin", 40);
            }
        }
        else if(mapState_i == 4 && mapState_j == 2 && edgeJudge(x, 8, 8) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[4][2];
            if(allMapBackground[4][2][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(640, 320, 80, 80, "chest_open.png");
                allMapBackground[4][2][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("iron", 20);
            }
        }
        if(s == "boss"){
            return !allMapMoveJudge[mapState_i][mapState_j][x / 80][y / 80];
        }
        return !allMapMoveJudge[mapState_i][mapState_j][x / 80][y / 80] && monsterJudge(x / 80, y / 80);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            GamePanel.switchState(2);
            Start = false;
            PausePanel.Start = true;
            pauseScreen.setVisible(true);
            caveScreen.setVisible(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_Q){
            GamePanel.switchState(7);
            Start = false;
            BackpackPanel.Start = true;
            backpackScreen.setVisible(true);
            caveScreen.setVisible(false);
        }
        /*
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(GamePanel.lastState + " " + GamePanel.nowState);

            System.out.println("Music State:");
            for(int i = 0; i < GamePanel.frameSize - 1; i ++){
                System.out.print(GamePanel.musicSwitched[i] + " ");
            }
            System.out.println(GamePanel.musicSwitched[GamePanel.frameSize - 1]);

            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start + " " + UpgradePanel.Start);

            System.out.println("Character Coordinate:");
            System.out.println(character1.x + " " + character1.y);
        }
        */
        
        // move
        // character Front : 0 up, 1 left, 2 down, 3 right
        if(e.getKeyCode() == KeyEvent.VK_W){
            characterFront = 0;
            if(character1.y >= character1.movY){
                if(moveJudge(character1.x, character1.y - character1.movY, "character")){
                    character1.y -= character1.movY;
                }
            }
            else if(mapJudge(0, -1)){
                characterInitX = character1.x;
                characterInitY = 640;
                reset();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            characterFront = 2;
            if(character1.y <= mainFrame.getHeight() - character1.width - character1.movY){
                if(moveJudge(character1.x, character1.y + character1.movY, "character")){
                    character1.y += character1.movY;
                }
            }
            else if(mapJudge(0, 1)){
                characterInitX = character1.x;
                characterInitY = 0;
                reset();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            characterFront = 1;
            if(character1.x >= character1.movX){
                if(moveJudge(character1.x - character1.movX, character1.y, "character")){
                    character1.x -= character1.movX;
                }
            }
            else if(mapJudge(-1, 0)){
                characterInitX = 1200;
                characterInitY = character1.y;
                reset();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            characterFront = 3;
            if(character1.x <= mainFrame.getWidth() - character1.width - character1.movX){
                if(moveJudge(character1.x + character1.movX, character1.y, "character")){
                    character1.x += character1.movX;
                }
            }
            else if(mapJudge(1, 0)){
                characterInitX = 0;
                characterInitY = character1.y;
                reset();
            }
        }
        // attack
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            characterAttack();
        }
        // props
        if(e.getKeyCode() == KeyEvent.VK_C){
            if(BackpackPanel.getMaterialAmount("herb") > 0){
                BackpackPanel.addMaterialAmount("herb", -1);
                if(ValueCalculate.characterLife + 4 <= ValueCalculate.characterValue[2][ValueCalculate.characterLevel]){
                    ValueCalculate.characterLife += 4;
                    ValueCalculate.characterLifeChange = true;

                }
                else{
                    ValueCalculate.characterLife = ValueCalculate.characterValue[2][ValueCalculate.characterLevel];
                    ValueCalculate.characterLifeChange = true;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
