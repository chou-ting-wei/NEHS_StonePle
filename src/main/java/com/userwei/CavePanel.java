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
    Map allMap[][];

    Background allMapBackground[][][];
    int allMapBackgroundCount[][];

    boolean allMapMoveJudge[][][][];

    int mapState_i = 2, mapState_j = 5;
    int mapSizeX = 5, mapSizeY = 6;

    Character character1;
    int characterInitX = 640, characterInitY = 640;
    Map map;

    JFrame mainFrame, pauseScreen, fieldScreen, caveScreen, backpackScreen;
    static boolean Start;

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

        // 0-3


        // 0-4


        // 0-5


        // 1-1


        // 1-2


        // 1-3


        // 1-5


        // 2-0


        // 2-1


        // 2-3


        // 2-4


        // 2-5
            // transport door
            addBackground(2, 5, 1120, 560, 80, 80, "transport_door.png");
            addMoveJudge(2, 5, 14, 14, 7, 7);

        // 3-1


        // 3-3


        // 3-5


        // 4-2


        // 4-3


        for(int i = mapSizeX - 1; i >= 0; i --){
            for(int j = 0; j < mapSizeY; j ++){
                if(mapName[i][j] != "null"){
                    Map nowMap = new Map(0, 0, 1280, 720, mapName[i][j]);
                    allMap[i][j] = nowMap;
                }
            }
        }
    }

    public void reset(){
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
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        character1.draw(g, this);
        for(int i = 0; i < allMapBackgroundCount[mapState_i][mapState_j]; i ++){
            Background nowbBackground = allMapBackground[mapState_i][mapState_j][i];
            nowbBackground.draw(g, this);
        }
    }

    public int randomNumber(int srt, int end){
        return (int)(Math.random() * (end - srt + 1)) + srt;
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

    public boolean moveJudge(int x, int y){
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
        if(x == 0 || y == 0 || x == 1200 || y == 640){
            return false;
        }
        if(mapState_i == 2 && mapState_j == 5 && edgeJudge(x, 14, 14) && edgeJudge(y, 7, 7)){
            Start = false;
            JFrame nowFrame = GamePanel.frame[3];
            FieldPanel.Start = true;
            GamePanel.switchState(3);
            nowFrame.setVisible(true);
            caveScreen.setVisible(false);
            return false;
        }
        return !allMapMoveJudge[mapState_i][mapState_j][x / 80][y / 80];
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
            mainFrame.setVisible(false);
        }
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
        if(e.getKeyCode() == KeyEvent.VK_W){
            if(character1.y >= character1.movY){
                if(moveJudge(character1.x, character1.y - character1.movY)){
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
            if(character1.y <= mainFrame.getHeight() - character1.width - character1.movY){
                if(moveJudge(character1.x, character1.y + character1.movY)){
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
            if(character1.x >= character1.movX){
                if(moveJudge(character1.x - character1.movX, character1.y)){
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
            if(character1.x <= mainFrame.getWidth() - character1.width - character1.movX){
                if(moveJudge(character1.x + character1.movX, character1.y)){
                    character1.x += character1.movX;
                }
            }
            else if(mapJudge(1, 0)){
                characterInitX = 0;
                characterInitY = character1.y;
                reset();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
