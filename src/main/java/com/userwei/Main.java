package com.userwei;
import javax.swing.JFrame;
import java.awt.*;

public class Main{
    public static void main(String[] args){
        // 0 Start Panel; 1 Main Panel; 2 Pause Panel; 3 Field Panel; 4 CavePanel; 5 Instruction Panel
        JFrame mainFrame = new JFrame("StonePle (beta 0.1)");
        JFrame startScreen = new JFrame("StonePle (beta 0.1)");
        JFrame pauseScreen = new JFrame("StonePle (beta 0.1)");
        JFrame fieldScreen = new JFrame("StonePle (beta 0.1)");
        JFrame caveScreen = new JFrame("StonePle (beta 0.1)");
        JFrame instructionScreen = new JFrame("StonePle (beta 0.1)");


        GamePanel gamePanel = new GamePanel(startScreen, mainFrame, pauseScreen, fieldScreen, caveScreen, instructionScreen);
        StartPanel startPanel = new StartPanel(mainFrame, startScreen);
        PausePanel pausePanel = new PausePanel(pauseScreen);
        FieldPanel fieldPanel = new FieldPanel(mainFrame, pauseScreen, fieldScreen, caveScreen);
        CavePanel cavePanel = new CavePanel(mainFrame, pauseScreen, fieldScreen, caveScreen);
        InstructionPanel instructionPanel = new InstructionPanel(mainFrame, instructionScreen);

        int size_x = 1280, size_y = 748;
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int loc_x = (int) screensize.getWidth() / 2 - size_x / 2;
        int loc_y = (int) screensize.getHeight() / 2 - size_y / 2;	

        startScreen.getContentPane().add(startPanel);
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen.setVisible(true);
        startScreen.setSize(size_x, size_y);
        startScreen.setResizable(false);
        startScreen.setLocation(loc_x, loc_y);
        
        mainFrame.getContentPane().add(gamePanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(false);
        mainFrame.setSize(size_x, size_y);
        mainFrame.setResizable(false);
        mainFrame.setLocation(loc_x, loc_y);

        pauseScreen.getContentPane().add(pausePanel);
        pauseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pauseScreen.setVisible(false);
        pauseScreen.setSize(size_x, size_y);
        pauseScreen.setResizable(false);
        pauseScreen.setLocation(loc_x, loc_y); 

        fieldScreen.getContentPane().add(fieldPanel);
        fieldScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fieldScreen.setVisible(false);
        fieldScreen.setResizable(false);
        fieldScreen.setLocation(loc_x, loc_y);
        fieldScreen.setSize(size_x, size_y);

        caveScreen.getContentPane().add(cavePanel);
        caveScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        caveScreen.setVisible(false);
        caveScreen.setResizable(false);
        caveScreen.setLocation(loc_x, loc_y);
        caveScreen.setSize(size_x, size_y);

        instructionScreen.getContentPane().add(instructionPanel);
        instructionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionScreen.setVisible(false);
        instructionScreen.setResizable(false);
        instructionScreen.setLocation(loc_x, loc_y);
        instructionScreen.setSize(size_x, size_y);
    }
}
