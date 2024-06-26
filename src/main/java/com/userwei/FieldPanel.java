package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.Graphics;

public class FieldPanel extends JPanel implements KeyListener{
    static Map allMap[][];
    /*
    Map Situation
    0-0 1-0 2-0 3-0 4-0 5-0 6-0
    0-1 1-1 2-1 3-1 4-1 5-1 6-1
    0-2 1-2 2-2 3-2 4-2 5-2 6-2
    */
    Background allMapBackground[][][];
    int allMapBackgroundCount[][];

    static Monster allMonster[][][];
    static int allMonsterCount[][];

    boolean allMapMoveJudge[][][][];

    static int mapState_i = 1, mapState_j = 5;
    int mapSizeX = 3, mapSizeY = 7;

    // character Front : 0 up, 1 left, 2 down, 3 right
    static Character character1;
    int characterFront = 2;
    static int characterInitX = 560, characterInitY = 400;
    static Map map;

    Background inventory;
    Material herb;
    Font hpbar, expbar1, expbar2, herbcount;

    static boolean caveUnlocked;

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
                Map nowMap = new Map(0, 0, 1280, 720, "field.png");
                allMap[i][j] = nowMap;
            }
        }

        inventory = new Background(1210, 650, 60, 60, "inventory.png");
        herb = new Material(1220, 655, 40, 40, 0, "herb.png" );
        herbcount = new Font(1173, 690, 60, 15, BackpackPanel.getMaterialAmount("herb") + ".png" );
        expbar1 = new Font(1135, 0, 40, 20, "level" + ValueCalculate.characterLevel+ ".png" );
        expbar2 = new Font(1175, 0, 105, 20, "lv" + ValueCalculate.characterExpPercent + ".png" );

        // Map Situation
        // 0-0 1-0 2-0 3-0 4-0 5-0 6-0
        // 0-1 1-1 2-1 3-1 4-1 5-1 6-1
        // 0-2 1-2 2-2 3-2 4-2 5-2 6-2

        // 0-0(done)
            // fence
            for(int i = 0; i <= 15; i ++){
                addBackground(0, 0, 80 * i, 0, 80, 80, "fence.png");
            }
            addMoveJudge(0, 0, 0, 15, 0, 0);
            // tree
            for(int i = 1; i<= 7; i += 2){
                addBackground(0, 0, 0, 80 * i, 160, 160, "tree.png");
            }
            addMoveJudge(0, 0, 0, 1, 1, 8);

            // slime
            addMonster(0, 0, 240, 160, 80, 80, "slime.gif");
            addMonster(0, 0, 320, 240, 80, 80, "slime.gif");

        // 0-1(done)
            // fence
            for(int i = 0; i <= 15; i ++){
                addBackground(1, 0, 80 * i, 0, 80, 80, "fence.png");
            }
            addMoveJudge(1, 0, 0, 15, 0, 0);

            // brownie
            addMonster(1, 0, 560, 320, 80, 80, "brownie.gif");
            addMonster(1, 0, 640, 320, 80, 80, "brownie.gif");

        // 0-2(done)
            // fence
            for(int i = 0; i <= 13; i ++){
                addBackground(2, 0, 80 * i, 0, 80, 80, "fence.png");
            }
            addMoveJudge(2, 0, 0, 13, 0, 0);
            // trunk
            for(int i = 2; i <= 8; i ++){
                addBackground(2, 0, 1200, 80 * i, 80, 80, "trunk.png");
            }
            addMoveJudge(2, 0, 15, 15, 2, 8);
            // tree
            addBackground(2, 0, 1120, 0, 160, 160, "tree.png");
            addMoveJudge(2, 0, 14, 15, 0, 1);

            // slime
            addMonster(2, 0, 960, 240, 80, 80, "slime.gif");
            addMonster(2, 0, 880, 320, 80, 80, "slime.gif");

        // 1-0(done)
            // tree
            addBackground(0, 1, 0, 0, 160, 160, "tree.png");
            addBackground(0, 1, 0, 480, 160, 160, "tree.png");
            addBackground(0, 1, 160, 320, 160, 160, "tree.png");
            addMoveJudge(0, 1, 0, 1, 0, 1);
            addMoveJudge(0, 1, 0, 1, 6, 7);
            addMoveJudge(0, 1, 2, 3, 4, 5);
            // trunk
            for(int i = 2; i <= 5; i ++){
                addBackground(0, 1, 0, 80 * i, 80, 80, "trunk.png");
            }
            addBackground(0, 1, 0, 640, 80, 80, "trunk.png");
            addMoveJudge(0, 1, 0, 0, 2, 5);
            addMoveJudge(0, 1, 0, 0, 8, 8);
            // chest 1-5
            addBackground(0, 1, 80, 400, 80, 80, "chest_close.png");
            addMoveJudge(0, 1, 1, 1, 5, 5);

            // brownie
            addMonster(0, 1, 80, 320, 80, 80, "brownie.gif");

        // 1-1 洞穴進入位置(done)
            // cave
            addBackground(1, 1, 480, 80, 320,240, "cave_locked.png");
            addMoveJudge(1, 1, 6, 9, 1, 3);

            // brownie
            addMonster(1, 1, 560, 320, 80, 80, "brownie.gif");
            addMonster(1, 1, 640, 320, 80, 80, "brownie.gif");

        // 1-2(done)
            // trunk
            for(int i = 0; i <= 8; i ++){
                addBackground(2, 1, 1200, 80 * i, 80,80, "trunk.png");
            }
            addMoveJudge(2, 1, 15, 15, 0, 8);

            // brownie
            addMonster(2, 1, 400, 240, 80, 80, "brownie.gif");
            addMonster(2, 1, 800, 480, 80, 80, "brownie.gif");

        // 2-0(done)
            // trunk
            for(int i = 0; i <= 8; i ++){
                addBackground(0, 2, 0, 80 * i, 80,80, "trunk.png");
            }
            addBackground(0, 2, 160, 640, 80,80, "trunk.png");
            addMoveJudge(0, 2, 0, 0, 0, 8);
            addMoveJudge(0, 2, 2, 2, 8, 8);

            // slime
            addMonster(0, 2, 80, 640, 80, 80, "slime.gif");

        // 2-1(done)
            // trunk
            for(int i = 5, j = 3; i <= 7 && j >= 1; i ++, j --){
                addBackground(1, 2, 80 * i, 80 * j, 80,80, "trunk.png");
                addMoveJudge(1, 2, i, i, j, j);
            }
            for(int i = 8, j = 1; i <= 10 && j <= 3; i ++, j ++){
                addBackground(1, 2, 80 * i, 80 * j, 80,80, "trunk.png");
                addMoveJudge(1, 2, i, i, j, j);
            }
            for(int i = 5, j = 5; i <= 7 && j <= 7; i ++, j ++){
                addBackground(1, 2, 80 * i, 80 * j, 80,80, "trunk.png");
                addMoveJudge(1, 2, i, i, j, j);
            }
            for(int i = 8, j = 7; i <= 10 && j >= 5; i ++, j --){
                addBackground(1, 2, 80 * i, 80 * j, 80,80, "trunk.png");
                addMoveJudge(1, 2, i, i, j, j);
            }
            // chest 7-4, 8-4
            addBackground(1, 2, 560, 320, 80, 80, "chest_close.png");
            addBackground(1, 2, 640, 320, 80, 80, "chest_close.png");
            addMoveJudge(1, 2, 7, 8, 4, 4);

            // slime
            for(int i = 6; i <= 9; i ++){
                addMonster(1, 2, 80 * i, 240, 80, 80, "slime.gif");
                addMonster(1, 2, 80 * i, 400, 80, 80, "slime.gif");
            }
            addMonster(1, 2, 480, 320, 80, 80, "slime.gif");
            addMonster(1, 2, 720, 320, 80, 80, "slime.gif");

        // 2-2(done)
            // tree
            addBackground(2, 2, 960, 560, 160,160, "tree.png");
            addBackground(2, 2, 1120, 400, 160,160, "tree.png");
            addBackground(2, 2, 1120, 560, 160,160, "tree.png");
            addMoveJudge(2, 2, 12, 13, 7, 8);
            addMoveJudge(2, 2, 14, 15, 5, 6);
            addMoveJudge(2, 2, 14, 15, 7, 8);
            // trunk
            for(int i = 0; i <= 4; i ++){
                addBackground(2, 2, 1200, 80 * i, 80,80, "trunk.png");
            }
            addMoveJudge(2, 2, 15, 15, 0, 4);

            // brownie
            addMonster(2, 2, 1040, 480, 80, 80, "brownie.gif");

        // 3-0(done)
            // trunk
            for(int i = 0; i <= 8; i ++){
                addBackground(0, 3, 0, 80 * i, 80,80, "trunk.png");
                addBackground(0, 3, 160, 80 * i, 80,80, "trunk.png");
            }
            addMoveJudge(0, 3, 0, 0, 0, 8);
            addMoveJudge(0, 3, 2, 2, 0, 8);

            // slime
            for(int i = 3; i <= 6; i ++){
                addMonster(0, 3, 80, 80 * i, 80, 80, "slime.gif");
            }

        // 3-1(done)
            // fence
                for(int i = 2; i <= 6; i ++){
                    addBackground(1, 3, 80 * i, 320, 80, 80, "fence.png");
                }
                for(int i = 9; i <= 13; i ++){
                    addBackground(1, 3, 80 * i, 320, 80, 80, "fence.png");
                }
                addMoveJudge(1, 3, 2, 6, 4, 4);
                addMoveJudge(1, 3, 9, 13, 4, 4);
            // stone
                for(int i = 6; i <= 9; i ++){
                    addBackground(1, 3, 80 * i, 240, 80, 80, "stone.png");
                    addBackground(1, 3, 80 * i, 400, 80, 80, "stone.png");
                }
                addMoveJudge(1, 3, 6, 9, 3, 3);
                addMoveJudge(1, 3, 6, 9, 5, 5);

            // brownie
            addMonster(1, 3, 560, 320, 80, 80, "brownie.gif");
            addMonster(1, 3, 640, 320, 80, 80, "brownie.gif");

        // 3-2(done)
            // tree
            addBackground(2, 3, 960, 0, 160,160, "tree.png");
            addBackground(2, 3, 1120, 0, 160,160, "tree.png");
            addBackground(2, 3, 1120, 160, 160,160, "tree.png");
            addMoveJudge(2, 3, 12, 13, 0, 1);
            addMoveJudge(2, 3, 14, 15, 0, 1);
            addMoveJudge(2, 3, 14, 15, 2, 3);
            // stone
            for(int i = 12; i <= 13; i ++){
                addBackground(2, 3, 80 * i, 160, 80,80, "stone.png");
            }
            addBackground(2, 3, 1040, 240, 80,80, "stone.png");
            addBackground(2, 3, 1120, 320, 80,80, "stone.png");
            for(int i = 4; i <= 8; i ++){
                addBackground(2, 3, 1200, 80 * i, 80,80, "stone.png");
            }
            addBackground(2, 3, 960, 400, 80,80, "stone.png");
            addBackground(2, 3, 1040, 480, 80,80, "stone.png");
            addBackground(2, 3, 1120, 560, 80,80, "stone.png");
            addMoveJudge(2, 3, 12, 13, 2, 2);
            addMoveJudge(2, 3, 13, 13, 3, 3);
            addMoveJudge(2, 3, 14, 14, 4, 4);
            addMoveJudge(2, 3, 15, 15, 4, 8);
            addMoveJudge(2, 3, 12, 12, 5, 5);
            addMoveJudge(2, 3, 13, 13, 6, 6);
            addMoveJudge(2, 3, 14, 14, 7, 7);
            // chest 14-6
            addBackground(2, 3, 1120, 480, 80, 80, "chest_close.png");
            addMoveJudge(2, 3, 14, 14, 6, 6);

            // slime
            addMonster(2, 3, 960, 240, 80, 80, "slime.gif");
            addMonster(2, 3, 960, 320, 80, 80, "slime.gif");
            addMonster(2, 3, 1040, 320, 80, 80, "slime.gif");
            addMonster(2, 3, 1040, 400, 80, 80, "slime.gif");
            addMonster(2, 3, 1120, 400, 80, 80, "slime.gif");

        // 4-0(done)
            // trunk
            for(int i = 0; i <= 8; i ++){
                addBackground(0, 4, 0, 80 * i, 80,80, "trunk.png");
                addBackground(0, 4, 160, 80 * i, 80,80, "trunk.png");
            }
            addMoveJudge(0, 4, 0, 0, 0, 8);
            addMoveJudge(0, 4, 2, 2, 0, 8);

            // brownie
            addMonster(0, 4, 80, 320, 80, 80, "brownie.gif");
            addMonster(0, 4, 720, 320, 80, 80, "brownie.gif");

        // 4-1(done)
            // tree
            addBackground(1, 4, 320, 160, 160,160, "tree.png");
            addBackground(1, 4, 800, 400, 160,160, "tree.png");
            addMoveJudge(1, 4, 4, 5, 2, 3);
            addMoveJudge(1, 4, 10, 11, 5, 6);

            // slime
            addMonster(1, 4, 560, 320, 80, 80, "slime.gif");
            addMonster(1, 4, 640, 320, 80, 80, "slime.gif");

        // 4-2(done)
            // tree
            addBackground(2, 4, 1120, 560, 160,160, "tree.png");
            addMoveJudge(2, 4, 14, 15, 7, 8);
            // stone
            for(int i = 0; i <= 6; i ++){
                addBackground(2, 4, 1200, 80 * i, 80,80, "stone.png");
            }
            addMoveJudge(2, 4, 15, 15, 0, 6);

            // slime
            addMonster(2, 4, 1120, 480, 80, 80, "slime.gif");

        // 5-0(done)
            // trunk
            for(int i = 0; i <= 8; i ++){
                addBackground(0, 5, 0, 80 * i, 80,80, "trunk.png");
                addBackground(0, 5, 160, 80 * i, 80,80, "trunk.png");
            }
            addMoveJudge(0, 5, 0, 0, 0, 8);
            addMoveJudge(0, 5, 2, 2, 0, 8);

            // slime
            addMonster(0, 5, 640, 320, 80, 80, "slime.gif");
            addMonster(0, 5, 720, 320, 80, 80, "slime.gif");

        // 5-1 出生位置（家）(done)
            // house
            addBackground(1, 5, 400, 160, 240, 240, "house.png");
            addMoveJudge(1, 5, 5, 7, 2, 4);
            // stone
            addBackground(1, 5, 720, 320, 80, 80, "stone.png");
            addBackground(1, 5, 800, 240, 80, 80, "stone.png");
            addBackground(1, 5, 960, 240, 80, 80, "stone.png");
            addBackground(1, 5, 1040, 320, 80, 80, "stone.png");
            for(int i = 2; i <= 6; i ++){
                addBackground(1, 5, 880, 80 * i, 80, 80, "stone.png");
            }
            addMoveJudge(1, 5, 9, 9, 4, 4);
            addMoveJudge(1, 5, 10, 10, 3, 3);
            addMoveJudge(1, 5, 12, 12, 3, 3);
            addMoveJudge(1, 5, 13, 13, 4, 4);
            addMoveJudge(1, 5, 11, 11, 2, 6);

        // 5-2(done)
            // tree
            addBackground(2, 5, 1120, 0, 160,160, "tree.png");
            addBackground(2, 5, 1120, 560, 160, 160, "tree.png");
            addBackground(2, 5, 960, 160, 160, 160, "tree.png");
            addBackground(2, 5, 960, 400, 160, 160, "tree.png");
            addMoveJudge(2, 5, 14, 15, 0, 1);
            addMoveJudge(2, 5, 14, 15, 7, 8);
            addMoveJudge(2, 5, 12, 13, 2, 3);
            addMoveJudge(2, 5, 12, 13, 5, 6);
            // trunk
            addBackground(2, 5, 1120, 320, 80,80, "trunk.png");
            addMoveJudge(2, 5, 14, 14, 4, 4);
            // chest 13-4
            addBackground(2, 5, 1040, 320, 80, 80, "chest_close.png");
            addMoveJudge(2, 5, 13, 13, 4, 4);

            // brownie
            addMonster(2, 5, 800, 320, 80, 80, "brownie.gif");
            addMonster(2, 5, 960, 320, 80, 80, "brownie.gif");

        // 6-0(done)
            // fence
            for(int i = 0; i <= 15; i ++){
                addBackground(0, 6, 80 * i, 640, 80, 80, "fence.png");
            }
            addMoveJudge(0, 6, 0, 15, 8, 8);
            // tree
            addBackground(0, 6, 0, 320, 160,160, "tree.png");
            addBackground(0, 6, 160, 400, 160, 160, "tree.png");
            addBackground(0, 6, 400, 480, 160, 160, "tree.png");
            addMoveJudge(0, 6, 0, 1, 4, 5);
            addMoveJudge(0, 6, 2, 3, 5, 6);
            addMoveJudge(0, 6, 5, 6, 6, 7);
            // trunk
            for(int i = 0; i <= 3; i ++){
                addBackground(0, 6, 0, 80 * i, 80, 80, "trunk.png");
            }
            for(int i = 6; i <= 7; i ++){
                addBackground(0, 6, 0, 80 * i, 80, 80, "trunk.png");
            }
            for(int i = 1; i <= 3; i ++){
                addBackground(0, 6, 80 * i, 240, 80, 80, "trunk.png");
            }
            for(int i = 2; i <= 3; i ++){
                addBackground(0, 6, 80 * i, 320, 80, 80, "trunk.png");
            }
            addMoveJudge(0, 6, 0, 0, 0, 3);
            addMoveJudge(0, 6, 0, 0, 6, 7);
            addMoveJudge(0, 6, 1, 3, 3, 3);
            addMoveJudge(0, 6, 2, 3, 4, 4);
            for(int i = 0; i <= 1; i ++){
                addBackground(0, 6, 160, 80 * i, 80, 80, "trunk.png");
            }
            for(int i = 3; i <= 4; i ++){
                addBackground(0, 6, 80 * i, 80, 80, 80, "trunk.png");
            }
            for(int i = 1; i <= 5; i ++){
                addBackground(0, 6, 400, 80 * i, 80, 80, "trunk.png");
            }
            addMoveJudge(0, 6, 2, 2, 0, 1);
            addMoveJudge(0, 6, 3, 4, 1, 1);
            addMoveJudge(0, 6, 5, 5, 1, 5);
            // chest 1-6
            addBackground(0, 6, 80, 480, 80, 80, "chest_close.png");
            addMoveJudge(0, 6, 1, 1, 6, 6);

            // slime
            addMonster(0, 6, 80, 160, 80, 80, "slime.gif");
            addMonster(0, 6, 80, 560, 80, 80, "slime.gif");
            addMonster(0, 6, 320, 160, 80, 80, "slime.gif");
            addMonster(0, 6, 320, 560, 80, 80, "slime.gif");

        // 6-1(done)
            // fence
            for(int i = 0; i <= 15; i ++){
                addBackground(1, 6, 80 * i, 640, 80, 80, "fence.png");
            }
            addMoveJudge(1, 6, 0, 15, 8, 8);

            // slime
            for(int i = 6; i <= 9; i ++){
                for(int j = 3; j <= 5; j ++){
                    addMonster(1, 6, 80 * i, 80 * j, 80, 80, "slime.gif");
                }
            }

        // 6-2(done)
            // fence
            for(int i = 0; i <= 15; i ++){
                addBackground(2, 6, 80 * i, 640, 80, 80, "fence.png");
            }
            addMoveJudge(2, 6, 0, 15, 8, 8);
            // tree
            addBackground(2, 6, 1120, 0, 160,160, "tree.png");
            addBackground(2, 6, 1120, 480, 160, 160, "tree.png");
            addMoveJudge(2, 6, 14, 15, 0, 1);
            addMoveJudge(2, 6, 14, 15, 6, 7);
            // stone
            addBackground(2, 6, 1120, 240, 80,80, "stone.png");
            addBackground(2, 6, 1120, 320, 80,80, "stone.png");
            addBackground(2, 6, 1200, 160, 80,80, "stone.png");
            addBackground(2, 6, 1200, 400, 80,80, "stone.png");
            for(int i = 7; i <= 8; i ++){
                addBackground(2, 6, 80 * i, 320, 80, 80, "stone.png");
            }
            addMoveJudge(2, 6, 15, 15, 2, 2);
            addMoveJudge(2, 6, 15, 15, 5, 5);
            addMoveJudge(2, 6, 14, 14, 3, 4);
            addMoveJudge(2, 6, 7, 8, 4, 4);
            // trunk
            addBackground(2, 6, 400, 160, 80,80, "trunk.png");
            addBackground(2, 6, 800, 160, 80,80, "trunk.png");
            addBackground(2, 6, 400, 480, 80,80, "trunk.png");
            addBackground(2, 6, 800, 480, 80,80, "trunk.png");
            addMoveJudge(2, 6, 5, 5, 2, 2);
            addMoveJudge(2, 6, 10, 10, 2, 2);
            addMoveJudge(2, 6, 5, 5, 6, 6);
            addMoveJudge(2, 6, 10, 10, 6, 6);

            // slime
            for(int i = 7; i <= 8; i ++){
                addMonster(2, 6, 80 * i, 160, 80, 80, "slime.gif");
                addMonster(2, 6, 80 * i, 480, 80, 80, "slime.gif");
            }
    }

    static public void reset(){
        map = allMap[mapState_i][mapState_j];
        character1 = new Character(characterInitX, characterInitY, 80, 80, 80, 80, "walk.gif");
    }

    FieldPanel(JFrame mainFrame, JFrame pauseScreen, JFrame fieldScreen, JFrame caveScreen, JFrame backpackScreen){
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
        if(caveUnlocked){
            if(allMapBackground[1][1][0].name != "cave_unlocked.png"){
                Background newBackground = new Background(480, 80,320, 240, "cave_unlocked.png");
                allMapBackground[1][1][0] = newBackground;
                repaint();
            }
        }
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
            Background nowBackground = allMapBackground[mapState_i][mapState_j][i];
            nowBackground.draw(g, this);
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
                if(i < 0 || j < 0 || i > 1200 || j > 640){
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
            if(nowMonster.monsterIdx == 0){
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
        }
        MonsterAttackChange = true;
    }

    public boolean checkMonsterIdx(Monster nowMonster){
        if(nowMonster.monsterIdx == 3 || nowMonster.monsterIdx == 4){
            return true;
        }
        return false;
    }

    public void monsterMove(){
        int nowMonsterCount = allMonsterCount[mapState_i][mapState_j];
        for(int i = 0; i < nowMonsterCount; i ++){
            Monster nowMonster = allMonster[mapState_i][mapState_j][i];
            if(!nowMonster.destroyed && Start){
                int cx = character1.x, cy = character1.y, mx = nowMonster.x, my = nowMonster.y;
                int distx = Math.abs(mx - cx) / 80, disty = Math.abs(my - cy) / 80;
                if(((distx == 1 && disty == 0) || (distx == 0 && disty == 1)) || (Math.max(distx, disty) == 1 && checkMonsterIdx(nowMonster))){
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
        }
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

    // chest pos : [0][6] 1-6, [2][5] 13-4, [2][3] 14-6, [0][1] 1-5, [1][2] 7-4, [1][2] 8-4
    public boolean moveJudge(int x, int y, String s){
        if(mapState_i == 1 && mapState_j == 5 && edgeJudge(x, 5, 7) && edgeJudge(y, 2, 4) && s == "character"){
            monsterReset();
            Start = false;
            JFrame nowFrame = GamePanel.frame[1];
            GamePanel.Start = true;
            GamePanel.switchState(1);
            nowFrame.setVisible(true);
            fieldScreen.setVisible(false);
        }
        else if(mapState_i == 1 && mapState_j == 1 && edgeJudge(x, 6, 9) && edgeJudge(y, 1, 3) && caveUnlocked && s == "character"){
            Start = false;
            JFrame nowFrame = GamePanel.frame[4];
            CavePanel.Start = true;
            GamePanel.switchState(4);
            nowFrame.setVisible(true);
            fieldScreen.setVisible(false);
        }
        else if(mapState_i == 0 && mapState_j == 6 && edgeJudge(x, 1, 1) && edgeJudge(y, 6, 6) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[0][6];
            if(allMapBackground[0][6][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(80, 480, 80, 80, "chest_open.png");
                allMapBackground[0][6][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("herb", 20);
            }
        }
        else if(mapState_i == 2 && mapState_j == 5 && edgeJudge(x, 13, 13) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[2][5];
            if(allMapBackground[2][5][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(1040, 320, 80, 80, "chest_open.png");
                allMapBackground[2][5][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("coin", 5);
            }
        }
        else if(mapState_i == 2 && mapState_j == 3 && edgeJudge(x, 14, 14) && edgeJudge(y, 6, 6) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[2][3];
            if(allMapBackground[2][3][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(1120, 480, 80, 80, "chest_open.png");
                allMapBackground[2][3][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("wood", 10);
            }
        }
        else if(mapState_i == 0 && mapState_j == 1 && edgeJudge(x, 1, 1) && edgeJudge(y, 5, 5) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[0][1];
            if(allMapBackground[0][1][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(80, 400, 80, 80, "chest_open.png");
                allMapBackground[0][1][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("herb", 10);
            }
        }
        else if(mapState_i == 1 && mapState_j == 2 && edgeJudge(x, 7, 7) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[1][2];
            if(allMapBackground[1][2][nowBackgroundCount - 2].name != "chest_open.png"){
                Background newBackground = new Background(560, 320, 80, 80, "chest_open.png");
                allMapBackground[1][2][nowBackgroundCount - 2] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("wood", 20);
            }
        }
        else if(mapState_i == 1 && mapState_j == 2 && edgeJudge(x, 8, 8) && edgeJudge(y, 4, 4) && s == "character"){
            int nowBackgroundCount = allMapBackgroundCount[1][2];
            if(allMapBackground[1][2][nowBackgroundCount - 1].name != "chest_open.png"){
                Background newBackground = new Background(640, 320, 80, 80, "chest_open.png");
                allMapBackground[1][2][nowBackgroundCount - 1] = newBackground;
                repaint();
                BackpackPanel.addMaterialAmount("coin", 10);
            }
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
            fieldScreen.setVisible(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_Q){
            GamePanel.switchState(7);
            Start = false;
            BackpackPanel.Start = true;
            backpackScreen.setVisible(true);
            fieldScreen.setVisible(false);
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
            try{
                Music nowMusic = new Music("attack.wav");
                nowMusic.playOnce(500);
            }catch(Exception e1){
                e1.printStackTrace();
            }
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
