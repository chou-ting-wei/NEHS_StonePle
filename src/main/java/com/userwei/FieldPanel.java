package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class FieldPanel extends JPanel implements KeyListener{
    Map allMap[][];
    int mapState_i = 1, mapState_j = 5;
    int mapSizeX = 3, mapSizeY = 7;

    Character character1;
    int characterInitX = 640, characterInitY = 320;
    Map map;

    JFrame mainFrame, pauseScreen, fieldScreen, caveScreen;
    static boolean Start;

    void init(){
        allMap = new Map[mapSizeX][mapSizeY];
        for(int i = mapSizeX - 1; i >= 0; i --){
            for(int j = 0; j < mapSizeY; j ++){
                Map nowMap = new Map(0, 0, 1280, 720, "field.png");
                allMap[i][j] = nowMap;
            }
        }
    }

    void reset(){
        map = allMap[mapState_i][mapState_j];
        character1 = new Character(characterInitX, characterInitY, 80, 80, 80, 80, "walk.gif");
    }

    FieldPanel(JFrame mainFrame, JFrame pauseScreen, JFrame fieldScreen, JFrame caveScreen){
        this.fieldScreen = fieldScreen;
        this.pauseScreen = pauseScreen;
        this.mainFrame = mainFrame;
        this.caveScreen = caveScreen;

        addKeyListener(this);
        setFocusable(true);
        
        init();
        reset();
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        character1.draw(g, this);
    }

    public void update() {
        
	}

    boolean mapJudge(int dx, int dy){
        if(mapState_i + dx < 0 || mapState_i + dx >= mapSizeX){
            return false;
        }
        if(mapState_j + dy < 0 || mapState_j + dy >= mapSizeY){
            return false;
        }
        mapState_i += dx;
        mapState_j += dy;
        return true;
    }

    boolean edgeJudge(int k, int srt, int end){
        for(int i = srt; i <= end; i ++){
            if(k / 80 == i){
                return true;
            }
        }
        return false;
    }

    boolean moveJudge(int x, int y){

        return true;
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
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(GamePanel.lastState + " " + GamePanel.nowState);

            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start + " " + UpgradePanel.Start);
            
            System.out.println("Music State:");
            for(int i = 0; i < GamePanel.frameSize - 1; i ++){
                System.out.print(GamePanel.musicSwitched[i] + " ");
            }
            System.out.println(GamePanel.musicSwitched[GamePanel.frameSize - 1]);
            
            System.out.println("Character Coordinate:");
            System.out.println(character1.x + " " + character1.y);
        }
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
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
        if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
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
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
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
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
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
