package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;

public class GamePanel extends JPanel implements KeyListener{
    // Music
    static ArrayList<String> musicName;
    static ArrayList<Music> music;
    static boolean[] musicSwitched;
    static int nowState = 0;
    static int lastState = 0;
    static boolean sudo;

    // Frame
    static JFrame[] frame;
    static int frameSize = 8;
    JFrame mainFrame, pauseScreen, fieldScreen, startScreen, caveScreen, instructionScreen, upgradeScreen, backpackScreen;

    // Element
    Background background1, background2, background3, background4, background5;
    Background background6, background7, background8, background9, background10;
    Background background11, background12, background13, background14, background15;
    Background inventory;
    Character character1;
    Icon icon1, icon2, icon3;
    Map map;

    Material herb;
    Font hpbar, herbcount;
    
    // Thread
    Thread thread;

    static boolean Start;

    public void init(){
        // Music
        musicName = new ArrayList<String>();
        // 0 Start Panel
        musicName.add("an_Emotional_Day.wav");
        // 1 Main Panel
        musicName.add("Escaping.wav");
        // 2 Pause Panel
        musicName.add("null");
        // 3 Field Panel
        musicName.add("a_Brief_Start.wav");
        // 4 CavePanel
        musicName.add("a_Miserable_Melody.wav");
        // 5 Instruction Panel
        musicName.add("null");
        // 6 Update Panel
        musicName.add("null");
        // 7 Bckpack Panel
        musicName.add("null");

        music = new ArrayList<Music>();
        musicSwitched = new boolean[frameSize];
        for(int i = 0; i < frameSize; i ++){
            musicSwitched[i] = false;
            String nowMusicName = musicName.get(i);
            try{
                if(nowMusicName != "null"){
                    Music nowMusic = new Music(nowMusicName);
                    music.add(nowMusic);
                }
                else{
                    music.add(null);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // Element
        map = new Map(0, 0, 1280, 720, "woodfloor.png");

        background1 = new Background(40, 0, 200, 80, "kitchen_table.png");
        background2 = new Background(-18, 0, 80, 80, "frige.png");
        background3 = new Background(80, 160, 400, 240, "diner_table.png");

        background4 = new Background(0, 480, 160, 80, "brown_shelf1.png");
        background5 = new Background(80, 640, 80, 80, "plant1.png");
        icon1 = new Icon(10, 570, 60, 60, "exclamation.png");

        background6 = new Background(1040, 240, 240, 160, "sofa1.png");
        background7 = new Background(880, 240, 160, 160, "sofa2.png");
        background8 = new Background(1140, 163, 160, 80, "black_shelf1.png");
        background9 = new Background(880, 160, 280, 80, "black_shelf2.png");
        
        icon2 = new Icon(1210, 10, 60, 60, "sword.png");
        background10 = new Background(1120, 0, 80, 80, "plant1.png");

        icon3 = new Icon(1210, 650, 60, 60, "arrow_up.png");
        background11 = new Background(1120, 640, 80, 80, "plant1.png");

        character1 = new Character(0, 640, 80, 80, 80, 80, "walk.gif");

        hpbar = new Font(0, 0, 120, 20, "hp" + Integer.toString(ValueCalculate.characterPercent) + ".png" );

        inventory = new Background(1210, 650, 60, 60, "inventory.png");
        herb = new Material(1220, 660, 40, 40, 0, "herb.png" );
        herbcount = new Font(1177, 682, 80, 20, Integer.toString(BackpackPanel.getMaterialAmount("herb")) + ".png" );
    }

    GamePanel(JFrame startScreen, JFrame mainFrame, JFrame pauseScreen, JFrame fieldScreen, JFrame caveScreen, JFrame instructionScreen, JFrame upgradeScreen, JFrame backpackScreen){
        this.startScreen = startScreen;
        this.mainFrame = mainFrame;
        this.pauseScreen = pauseScreen;
        this.fieldScreen = fieldScreen;
        this.caveScreen = caveScreen;
        this.instructionScreen = instructionScreen;
        this.upgradeScreen = upgradeScreen;
        this.backpackScreen = backpackScreen;

        frame = new JFrame[frameSize];
        frame[0] = startScreen;
        frame[1] = mainFrame;
        frame[2] = pauseScreen;
        frame[3] = fieldScreen;
        frame[4] = caveScreen;
        frame[5] = instructionScreen;
        frame[6] = upgradeScreen;
        frame[7] = backpackScreen;

        addKeyListener(this);
        setFocusable(true);

        init();
      
        thread = new Thread(() -> {
            // ><
            try{
                Thread.sleep(4000);
            }catch(Exception e){
                e.printStackTrace();
            }
            
            while(true){
                playMusic();

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

    public void paintComponent(Graphics g){
        map.draw(g, this);
        background1.draw(g, this);
        background2.draw(g, this);
        background3.draw(g, this);
        background4.draw(g, this);
        background5.draw(g, this);
        background6.draw(g, this);
        background7.draw(g, this);
        background8.draw(g, this);
        background9.draw(g, this);
        background10.draw(g, this);
        background11.draw(g, this);
        icon1.draw(g, this);
        icon2.draw(g, this);
        icon3.draw(g, this);
        character1.draw(g, this);
        hpbar.draw(g, this);
        inventory.draw(g, this);
        herb.draw(g, this);
        herbcount.draw(g, this);
    }

    // 0 Start Panel; 1 Main Panel; 2 Pause Panel; 3 Field Panel; 4 Cave Panel; 5 Instruction Panel; 6 Upgrade Panel; 7 Backpack Panel
    public void update() {
        // exclamation
        if(character1.x == 0 && character1.y == 560 && !InstructionPanel.Start){
            Start = false;
            InstructionPanel.Start = true;
            switchState(5);
            character1.x = 80;
            instructionScreen.setVisible(true);
            mainFrame.setVisible(false);
        }
        // sword
        if(character1.x == 1200 && character1.y == 0 && !FieldPanel.Start){
            Start = false;
            FieldPanel.Start = true;
            switchState(3);
            character1.y = 80;
            fieldScreen.setVisible(true);
            mainFrame.setVisible(false);
        }
        // arrow_up
        if(character1.x == 1200 && character1.y == 640 && !UpgradePanel.Start){
            Start = false;
            UpgradePanel.Start = true;
            switchState(5);
            character1.y = 560;
            upgradeScreen.setVisible(true);
            mainFrame.setVisible(false);
        }
        if(ValueCalculate.characterPercent >= 0){
            hpbar = new Font(0, 0, 180, 30, "hp" + ValueCalculate.characterPercent + ".png");
            repaint();
        }
        else{
            hpbar = new Font(0, 0, 180, 30, "hpRevive.png");
            repaint();
        }
        if(BackpackPanel.getMaterialAmount("herb") <= 99){
            herbcount = new Font(1177, 682, 80, 20, Integer.toString(BackpackPanel.getMaterialAmount("herb")) + ".png" );
            repaint();
        }
        else if(BackpackPanel.getMaterialAmount("herb") > 99){
            herbcount = new Font(1177, 682, 80, 20, "99+.png" );
            repaint();
        }
        else{
            herbcount = new Font(1177, 682, 80, 20, "EOF.png" );
            repaint();
        }
	}

    // Music
    static public void playMusic(){
        Music nowMusic = music.get(nowState);
        String nowMusicName = musicName.get(nowState);

        if(nowMusicName != "null"){
            if(musicSwitched[nowState]){
                nowMusic.resetAudioStream();
                musicSwitched[nowState] = false;
            }
            nowMusic.play();
        }
    }

    static public void switchState(int state){
        if(sudo){
            sudo = false;
            Music nowMusic = music.get(lastState);
            String nowMusicName = musicName.get(lastState);
            if(nowMusicName != "null"){
                nowMusic.stop();
                musicSwitched[lastState] = true;
            }

            nowState = state;
            lastState = 2;
            playMusic();
            return;
        }
        if(state == 2 || state == 5 || state == 6 || state == 7){
            lastState = nowState;
            nowState = state;
            return;
        }
        if(nowState == 2 || nowState == 5 || nowState == 6 || nowState == 7){
            int tmpState = nowState;
            nowState = lastState;
            lastState = tmpState;
            return;
        }

        Music nowMusic = music.get(nowState);
        String nowMusicName = musicName.get(nowState);
        if(nowMusicName != "null"){
            nowMusic.stop();
            musicSwitched[nowState] = true;
        }
        
        lastState = nowState;
        nowState = state;
        playMusic();
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
        if(edgeJudge(x, 1, 1) && edgeJudge(y, 8, 8)){
            return false;
        }
        if(edgeJudge(x, 0, 1) && edgeJudge(y, 6, 6)){
            return false;
        }
        if(edgeJudge(x, 0, 2) && edgeJudge(y, 0, 0)){
            return false;
        }
        if(edgeJudge(x, 14, 14) && edgeJudge(y, 8, 8)){
            return false;
        }
        if(edgeJudge(x, 14, 14) && edgeJudge(y, 0, 0)){
            return false;
        }
        if(edgeJudge(x, 1, 5) && edgeJudge(y, 2, 4)){
            return false;
        }
        if(edgeJudge(x, 11, 15) && edgeJudge(y, 2, 4)){
            return false;
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 0 Start Panel; 1 Main Panel; 2 Pause Panel; 3 Field Panel; 4 Cave Panel; 5 Instruction Panel; 6 Upgrade Panel; 7 Backpack Panel
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            switchState(2);
            Start = false;
            PausePanel.Start = true;
            pauseScreen.setVisible(true);
            mainFrame.setVisible(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_Q){
            switchState(7);
            Start = false;
            BackpackPanel.Start = true;
            backpackScreen.setVisible(true);
            mainFrame.setVisible(false);
        }

        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(lastState + " " + nowState);
            
            System.out.println("Music State:");
            for(int i = 0; i < frameSize - 1; i ++){
                System.out.print(musicSwitched[i] + " ");
            }
            System.out.println(musicSwitched[frameSize - 1]);

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
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            if(character1.y <= mainFrame.getHeight() - character1.width - character1.movY){
                if(moveJudge(character1.x, character1.y + character1.movY)){
                    character1.y += character1.movY;
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            if(character1.x >= character1.movX){
                if(moveJudge(character1.x - character1.movX, character1.y)){
                    character1.x -= character1.movX;
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            if(character1.x <= mainFrame.getWidth() - character1.width - character1.movX){
                if(moveJudge(character1.x + character1.movX, character1.y)){
                    character1.x += character1.movX;
                }
            }
        }
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
        if(e.getKeyCode() == KeyEvent.VK_M){
            ValueCalculate.characterLife -= 5;
            ValueCalculate.characterLifeChange = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_L){
            BackpackPanel.addMaterialAmount("herb", 5);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
