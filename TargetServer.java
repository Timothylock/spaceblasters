/*-----------------------------------------------------------------------------------------------------------------|
 * -------------------------------------------- Space Blasters v1 -------------------------------------------------|
 * ------------------------------------- Created by Conrad Lin and Timothy Lock -----------------------------------|
 * ----------------------------------------------- For ICS4U1 -----------------------------------------------------|
 * ---------------------------------------------- June 16 2014 ----------------------------------------------------|
 * ---------------------------------------------------------------------------------------------------------------*/

//SPACE BLASTERS (c) by CONRAD LIN & TIMOTHY LOCK

//SPACE BLASTERS is licensed under a
//Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.

//You should have received a copy of the license along with this
//work.  If not, see <http://creativecommons.org/licenses/by-nc-sa/3.0/>.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.JTextArea;
import static java.lang.Math.pow;


public class TargetServer implements ActionListener{
  //-------------------------------------
  // Start Frame Variables
  //-------------------------------------
  SuperSocketMaster ssm;
  Thread ssmthread;
  public JFrame startFrame;
  public JPanel startPanel;
  public JLabel title;
  public JLabel startDesc;
  public JLabel startPortLabel;
  public JTextField portTextfield;
  public JButton startButton;
  public JFileChooser chooser = new JFileChooser();
  public int intPort;
  
  //-------------------------------------
  // Server Frame Variables
  //-------------------------------------
  public JFrame runFrame;
  public JPanel runPanel;
  public Timer guiupdatetimer;
  public Timer scoretimer;
  public Timer targettimer;
  public Timer lobbytimer;
  public Timer roundtimer;
  public Timer lobbysectimer;
  public String strIP = "ERROR";
  public JTextField textfield;
  public JTextField ssmfield;
  public JLabel runtitle;
  public JLabel numConnectedPlayersLabel;
  public JTextField numConnectedPlayersTextfield;
  public JTextField gameStarted;
  public JTextField roundNum;
  public JTextField serverIP;
  public JLabel playerStatisticsLabel;
  public JLabel player1;
  public JLabel player2;
  public JLabel player3;
  public JLabel player4;
  public JTextField p1Name;
  public JTextField p2Name;
  public JTextField p3Name;
  public JTextField p4Name;
  public JTextField p1Score;
  public JTextField p2Score;
  public JTextField p3Score;
  public JTextField p4Score;
  public JTextField p1X;
  public JTextField p2X;
  public JTextField p3X;
  public JTextField p4X;
  public JTextField p1Y;
  public JTextField p2Y;
  public JTextField p3Y;
  public JTextField p4Y;
  public JButton scoreOveride;
  public JLabel target1;
  public JLabel target2;
  public JLabel target3;
  public JLabel target4;
  public JLabel target5;
  public JLabel target6;
  public JLabel target7;
  public JLabel target8;
  public JLabel target9;
  public JLabel target10;
  public JTextField t1X;
  public JTextField t2X;
  public JTextField t3X;
  public JTextField t4X;
  public JTextField t5X;
  public JTextField t6X;
  public JTextField t7X;
  public JTextField t8X;
  public JTextField t9X;
  public JTextField t10X;
  public JTextField t1Y;
  public JTextField t2Y;
  public JTextField t3Y;
  public JTextField t4Y;
  public JTextField t5Y;
  public JTextField t6Y;
  public JTextField t7Y;
  public JTextField t8Y;
  public JTextField t9Y;
  public JTextField t10Y;
  public JTextField t1Hit;
  public JTextField t2Hit;
  public JTextField t3Hit;
  public JTextField t4Hit;
  public JTextField t5Hit;
  public JTextField t6Hit;
  public JTextField t7Hit;
  public JTextField t8Hit;
  public JTextField t9Hit;
  public JTextField t10Hit;
  public JButton forceReset;
  public JTextArea textarea;
  public JScrollPane thescrollpane;
  
  
  public int[] scoreOverideData = new int[2];
  public String[] q1 = new String[] {"Player 1", "Player 2", "Player 3", "Player 4"};
  public String strPlayersNames[][] = new String [4][2];
  public int intPlayersData[][] = new int [4][4];
  public int intTargetPos[][] = new int [10][2];
  public int intTargetDir[] = new int [10];
  public int intTargetValue[] = new int [10];
  public int intTargetRadius = 20;
  public int intScreenMaxRight = 1350;
  public int intScreenMaxLeft = -110;
  public boolean intTargetStat[] = new boolean [10];
  public int intNumConnectedPlayers = 0;
  public int intRound = 1;
  public int intRoundMax = 3;
  public int intTimeLeft = 60;
  public int intTimeLeftConfig;
  public boolean gameStart = false;
  public String strLine;
  public String[] strTemp;
  public int intTemp;
  public int intLobbyTimer = 31;
  public int intCD = 10;
  public String strNothing;
  
  
  //Methods
  public void actionPerformed(ActionEvent evt){
    if(evt.getSource() == ssmfield){
      strLine = ssmfield.getText();
      
      if (strLine != null){
        //COnnecting
        if(strLine.startsWith("CONN")){
          intNumConnectedPlayers ++;
          append("A player has connected. Waiting for info...");
          
          //Disconnecting
        }else if(strLine.startsWith("DISC")){
          intNumConnectedPlayers --;
          append("A player has disconnected. ");
        }
        
        if (strLine != null){ //Split message
          strTemp = strLine.split(",");
        }
        
        try{
          //System.out.println(strTemp[0]);
          if (strTemp[0].equals("CHAT")){  //Player Chat
            ssm.sendText("BROADCAST,"+ strPlayersNames[Integer.parseInt(strTemp[1])][0] +": " + strTemp[2]);
            append(strPlayersNames[0][Integer.parseInt(strTemp[1])] + ": " + strTemp[2]);
          }
          if (strTemp[0].equals("JOIN")){  //Joining/check
            if (strPlayersNames[0][0] == null){
              strPlayersNames[0][0] = (strTemp[1]);  //Name
              strPlayersNames[0][1] = (strTemp[2]);  //IP
              append(strPlayersNames[0][0] + " assigned to Player 1");
              ssm.sendText("" + strPlayersNames[0][1] + ",ACCEPT," + 0); //confirm with client
              ssm.sendText("BROADCAST, " + strPlayersNames[0][0] + " has joined");
            }else if (strPlayersNames[1][0] == null){
              strPlayersNames[1][0] = (strTemp[1]);  //Name
              strPlayersNames[1][1] = (strTemp[2]);  //IP
              append(strPlayersNames[1][0] + " assigned to Player 2");
              ssm.sendText("" + strPlayersNames[1][1] + ",ACCEPT," + 1); //confirm with client
              ssm.sendText("BROADCAST, " + strPlayersNames[1][0] + " has joined");
            }else if (strPlayersNames[2][0] == null){
              strPlayersNames[2][0] = (strTemp[1]);  //Name
              strPlayersNames[2][1] = (strTemp[2]);  //IP
              append(strPlayersNames[2][0] + " assigned to Player 3");
              ssm.sendText("" + strPlayersNames[2][1] + ",ACCEPT," + 2); //confirm with client
              ssm.sendText("BROADCAST, " + strPlayersNames[2][0] + " has joined");
            }else if (strPlayersNames[3][0] == null){
              strPlayersNames[3][0] = (strTemp[1]);  //Name
              strPlayersNames[3][1] = (strTemp[2]);  //IP
              append(strPlayersNames[3][0] + " assigned to Player 4");
              ssm.sendText("" + strPlayersNames[3][1] + ",ACCEPT," + 3); //confirm with client
              ssm.sendText("BROADCAST, " + strPlayersNames[3][0] + " has joined");
            }else{
              ssm.sendText("" + strTemp[2] + ",DENY," + 0); //confirm with client
              append(strTemp[1] + " was rejected. Server Full");
            }
          }else if (strTemp[0].equals("CLIENTLOCATION")){  //Players sending back locations
            //Deal with that data
            intPlayersData[Integer.parseInt(strTemp[1])][1] = Integer.parseInt(strTemp[2]); //x
            intPlayersData[Integer.parseInt(strTemp[1])][2] = Integer.parseInt(strTemp[3]); //y
            intPlayersData[Integer.parseInt(strTemp[1])][3] = Integer.parseInt(strTemp[4]); //clicked
            
            //Hit Score Detection
            for (intTemp = 0; intTemp <10; intTemp ++){ //Check all 10 targets
              if (intTemp > 9){
              }else{
                hitDetect(Integer.parseInt(strTemp[1]), Integer.parseInt(strTemp[2]), Integer.parseInt(strTemp[3]), intTemp);
              }
            }
          }
        }catch (Exception e){
        }
      }
        
    }
    if (evt.getSource() == lobbytimer){ //if in lobby
      try{
        intLobbyTimer --;
        ssm.sendText("WAITING,1," + intLobbyTimer);
        ssm.sendText("LOCATION" + "," + intTimeLeft + "," + intTargetPos[0][0] + "," + intTargetPos[0][1] + "," + intTargetPos[1][0] + "," + intTargetPos[1][1] + "," + intTargetPos[2][0] + "," + intTargetPos[2][1] + "," + intTargetPos[3][0] + "," + intTargetPos[3][1] + "," + intTargetPos[4][0] + "," + intTargetPos[4][1] + "," + intTargetPos[5][0] + "," + intTargetPos[5][1] + "," + intTargetPos[6][0] + "," + intTargetPos[6][1] + "," + intTargetPos[7][0] + "," + intTargetPos[7][1] + "," + intTargetPos[8][0] + "," + intTargetPos[8][1] + "," + intTargetPos[9][0] + "," + intTargetPos[9][1]);
        ssm.sendText("PLAYERLOC," + intPlayersData[0][1] + "," + intPlayersData[0][2] + "," + intPlayersData[1][1] + "," + intPlayersData[1][2] + "," + intPlayersData[2][1] + "," + intPlayersData[2][2] + "," + intPlayersData[3][1] + "," + intPlayersData[3][2]);
        ssm.sendText("SCORES" + "," + intRound + ",Player 1: " + strPlayersNames[0][0] + ",Player 2: " + strPlayersNames[1][0] + ",Player 3: " + strPlayersNames[2][0] + ",Player 4: " + strPlayersNames[3][0] + "," + intPlayersData[0][0] + "," + intPlayersData[1][0] + "," + intPlayersData[2][0] + "," + intPlayersData[3][0]);
        ssm.sendText("PLAYERLOC," + intPlayersData[0][1] + "," + intPlayersData[0][2] + "," + intPlayersData[1][1] + "," + intPlayersData[1][2] + "," + intPlayersData[2][1] + "," + intPlayersData[2][2] + "," + intPlayersData[3][1] + "," + intPlayersData[3][2]);
      }catch(NullPointerException  e){
      }
      if (intLobbyTimer % 5 == 0){
        append("At Lobby. Time left until start: " + intLobbyTimer);
      }
      if (intLobbyTimer == 6){
        ssm.sendText("BROADCAST,SERVER: Preparing to start game");
      }
      if (intLobbyTimer < 0){
        ssm.sendText("WAITING,0," + intLobbyTimer);
        append("Starting round");
        intLobbyTimer = 31;
        lobbytimer.stop();
        targettimer.start();
        scoretimer.start();
        roundtimer.start();
      }
    }
    if(evt.getSource() == startButton){
      try{ //Make sure port is a real number
        intPort = Integer.parseInt(portTextfield.getText());
        System.out.println(intPort);
        ssm = new SuperSocketMaster(ssmfield ,intPort);
        ssmthread = new Thread(ssm);
        ssmthread.start();
        startFrame.setVisible(false);
        runFrame.setVisible(true);
        guiupdatetimer.start();
        scoretimer.start();
        targettimer.start();
        roundtimer.start();
        resetTargets();
      }catch(NumberFormatException e){
        JOptionPane.showMessageDialog(startFrame, "Port number MUST be a number and contain NO spaces or letters");
      }
    }else if (evt.getSource() == forceReset){
      append("Server admin reset server");
      ssm.sendText("BROADCAST, Admin resetted the server");
      gameLobby(true);
    }else if (evt.getSource() == scoreOveride){
      scoreOverideData[0] = JOptionPane.showOptionDialog(null, "Which player score are you changing?", "Server Admin Score Overide",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, q1, q1[0]);
      try{
        scoreOverideData[1] = Integer.parseInt(JOptionPane.showInputDialog ( "Enter the desired score" ));
      }catch(NumberFormatException e){
        JOptionPane.showMessageDialog(startFrame, "Score too high or contains invalid characters. Score override aborted");
      }
      intPlayersData[scoreOverideData[0]][0] = scoreOverideData[1];
    }
    if(evt.getSource() == lobbysectimer){
      intCD --;
      ssm.sendText("WAITING,1," + intCD);
      if (intCD == 0){
        intRound ++;
        if (intRound > intRoundMax){
          gameLobby(true);
          intRound = 0;
        }else{
          append("Starting round " + intRound);
          gameLobby(false);
        }
        intTimeLeft = intTimeLeftConfig;
        intCD = 15;
        lobbysectimer.stop();
        ssm.sendText("WAITING,0," + intCD);
      }
    }
    if(evt.getSource() == roundtimer){  
      intTimeLeft --;
      //System.out.println(intTimeLeft);
      if(intTimeLeft == 1 && intRound == intRoundMax){
        ssm.sendText("GAMEEND," + intPlayersData[0][0] + "," + intPlayersData[1][0] + "," + intPlayersData[2][0] + "," + intPlayersData[3][0]);
        append("---------------------------------------");
        append("GAME finished. Pausing 10 sec. Admin");
        append("console will be unresponsive throughout");
        append("this duration. DO NOT click anything.");
        append("---------------------------------------");
      }else if (intTimeLeft == 1){ //warn console admin
        append("---------------------------------------");
        append("Round finished. Pausing 10 sec. Admin");
        append("console will be unresponsive throughout");
        append("this duration. DO NOT click anything.");
        append("---------------------------------------");
      }
      if (intTimeLeft < 0){ //Next Round
        ssm.sendText("FINISHED," + intRound + "," + intPlayersData[0][0] + "," + intPlayersData[1][0] + "," + intPlayersData[2][0] + "," + intPlayersData[3][0]);
        ssm.sendText("LOCATION" + "," + intTimeLeft + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000+ "," + -1000 + "," + -1000 + "," + -1000 + "," + -1000);
        targettimer.stop();
        scoretimer.stop();
        roundtimer.stop();
        resetTargets();
        lobbysectimer.start();
      }
    }
      
    if(evt.getSource() == scoretimer){
        ssm.sendText("SCORES" + "," + intRound + ",Player 1: " + strPlayersNames[0][0] + ",Player 2: " + strPlayersNames[1][0] + ",Player 3: " + strPlayersNames[2][0] + ",Player 4: " + strPlayersNames[3][0] + "," + intPlayersData[0][0] + "," + intPlayersData[1][0] + "," + intPlayersData[2][0] + "," + intPlayersData[3][0]);
    }else if (evt.getSource() == targettimer){  //As players increase, send more targets flying across screen
      if (intNumConnectedPlayers == 0){  //if no player, reset game and go back to lobby
        append("No players connected. Heading to lobby.");
        gameLobby(true);
      }else if (intNumConnectedPlayers == 1){
        targetPhysics(0);
        targetPhysics(7);
        targetPhysics(4);
      }else if (intNumConnectedPlayers == 2){
        targetPhysics(0);
        targetPhysics(1);
        targetPhysics(3);
        targetPhysics(4);
        targetPhysics(7);
      }else if (intNumConnectedPlayers == 3){
        targetPhysics(0);
        targetPhysics(1);
        targetPhysics(2);
        targetPhysics(3);
        targetPhysics(4);
        targetPhysics(5);
        targetPhysics(7);
      }else if (intNumConnectedPlayers >=4){
        targetPhysics(0);
        targetPhysics(1);
        targetPhysics(2);
        targetPhysics(3);
        targetPhysics(4);
        targetPhysics(5);
        targetPhysics(6);
        targetPhysics(7);
        targetPhysics(8);
        targetPhysics(9);  //easter egg
      }
      //ssm.sendText("GAMERUNNING");
      try{
        ssm.sendText("PLAYERLOC," + intPlayersData[0][1] + "," + intPlayersData[0][2] + "," + intPlayersData[1][1] + "," + intPlayersData[1][2] + "," + intPlayersData[2][1] + "," + intPlayersData[2][2] + "," + intPlayersData[3][1] + "," + intPlayersData[3][2]);
        ssm.sendText("LOCATION" + "," + intTimeLeft + "," + intTargetPos[0][0] + "," + intTargetPos[0][1] + "," + intTargetPos[1][0] + "," + intTargetPos[1][1] + "," + intTargetPos[2][0] + "," + intTargetPos[2][1] + "," + intTargetPos[3][0] + "," + intTargetPos[3][1] + "," + intTargetPos[4][0] + "," + intTargetPos[4][1] + "," + intTargetPos[5][0] + "," + intTargetPos[5][1] + "," + intTargetPos[6][0] + "," + intTargetPos[6][1] + "," + intTargetPos[7][0] + "," + intTargetPos[7][1] + "," + intTargetPos[8][0] + "," + intTargetPos[8][1] + "," + intTargetPos[9][0] + "," + intTargetPos[9][1]);
      }catch(Exception e){
      }
    }else if (evt.getSource() == guiupdatetimer){  //Server Screen refresh
      ServerScreenRefresh();
    }
  }
  
  //-------------------------------------
  // Config File Reader
  //-------------------------------------
  public void configLoad() { 
    BufferedReader thefile = null;
    String strLine = "";
    
    try{
      thefile = new BufferedReader(new FileReader("config.txt"));
    }catch(FileNotFoundException e){
      JOptionPane.showMessageDialog(startFrame, "There was a problem reading the config file. Either damaged or not found. Please reselect the config file!");
      String sb = "TEST CONTENT";
      chooser.setCurrentDirectory(new File("/home/me/Documents"));
      int retrival = chooser.showSaveDialog(null);
      if (retrival == JFileChooser.APPROVE_OPTION) {
        try {
          thefile = new BufferedReader(new FileReader(chooser.getSelectedFile() + ".txt"));
        } catch (Exception ex) {
          
        }
      }
    }
    
    try{  //Try Reading
      strNothing = thefile.readLine();
      intLobbyTimer = Integer.parseInt(thefile.readLine());
      strNothing = thefile.readLine();
      strNothing = thefile.readLine();
      intTargetRadius = Integer.parseInt(thefile.readLine());
      strNothing = thefile.readLine();
      strNothing = thefile.readLine();
      intRoundMax = Integer.parseInt(thefile.readLine());
      strNothing = thefile.readLine();
      strNothing = thefile.readLine();
      intTimeLeftConfig = Integer.parseInt(thefile.readLine());
      strNothing = thefile.readLine();
      strNothing = thefile.readLine();
      for (intTemp = 0; intTemp <10; intTemp ++){ //Check all 10 targets
        intTargetValue[intTemp] = Integer.parseInt(thefile.readLine());
      }
      
    }catch(Exception r){  //Defaul Values
      JOptionPane.showMessageDialog(startFrame, "Config file damaged. Defaulting to default values.");
      intLobbyTimer = 31;
      intTargetRadius = 20;
      intRoundMax = 3;
      intTimeLeftConfig = 60;
    }
    intTimeLeft = intTimeLeftConfig;
  }
  
  //-------------------------------------
  // Pre-Game Start/Waiting Room/Reset
  //-------------------------------------
  public void gameLobby(boolean blnAllReset){
    targettimer.stop();
    scoretimer.stop();
    roundtimer.stop();
    resetTargets();
    try{
      ssm.sendText("LOCATION" + "," + intTimeLeft + "," + intTargetPos[0][0] + "," + intTargetPos[0][1] + "," + intTargetPos[1][0] + "," + intTargetPos[1][1] + "," + intTargetPos[2][0] + "," + intTargetPos[2][1] + "," + intTargetPos[3][0] + "," + intTargetPos[3][1] + "," + intTargetPos[4][0] + "," + intTargetPos[4][1] + "," + intTargetPos[5][0] + "," + intTargetPos[5][1] + "," + intTargetPos[6][0] + "," + intTargetPos[6][1] + "," + intTargetPos[7][0] + "," + intTargetPos[7][1] + "," + intTargetPos[8][0] + "," + intTargetPos[8][1] + "," + intTargetPos[9][0] + "," + intTargetPos[9][1]);
    }catch(NullPointerException e){
    }
    if (blnAllReset == true){ //Full Lobby Reset
      intPlayersData[0][0] = 0;
      intPlayersData[1][0] = 0;
      intPlayersData[2][0] = 0;
      intPlayersData[3][0] = 0;
      intPlayersData[0][1] = 0;
      intPlayersData[1][1] = 0;
      intPlayersData[2][1] = 0;
      intPlayersData[3][1] = 0;
      intPlayersData[0][2] = 0;
      intPlayersData[1][2] = 0;
      intPlayersData[2][2] = 0;
      intPlayersData[3][2] = 0;
      strPlayersNames[0][0] = null;
      strPlayersNames[1][0] = null;
      strPlayersNames[2][0] = null;
      strPlayersNames[3][0] = null;
      ssm.sendText("ALLRESET");
      configLoad();
      lobbytimer.start();
    }else if (blnAllReset == false){ // If not going through lobby and just resetting values
      lobbytimer.stop();
      targettimer.start();
      intTimeLeft = intTimeLeftConfig;
      scoretimer.start();
      roundtimer.start();
    }
  }
  
  //-------------------------------------
  // Hit Detection Method
  //-------------------------------------
  public void hitDetect(int intPlayerNum, int intPlayerX, int intPlayerY, int intTargetNum){
    try{
      if((pow((intPlayerX - intTargetPos[intTargetNum][0])-20,2) + pow((intPlayerY - intTargetPos[intTargetNum][1])-20,2))<(pow(intTargetRadius,2))){
        if(intPlayersData[intPlayerNum][3] == 1){  //Only count is hit if the mouse button is clicked
          intTargetStat[intTargetNum] = true;
          intPlayersData[intPlayerNum][0] = intPlayersData[intPlayerNum][0] + intTargetValue[intTargetNum];
          append("Player " + intPlayerNum + 1 + " hit target " + intTargetNum);
          //Reset specific target
          //Randomize its start location (L/R)
          intTargetPos[intTargetNum][1] = (int)((Math.random()*480)+50);
          if ((int)(Math.random()*100) <= 50){
            intTargetPos[intTargetNum][0] = intScreenMaxLeft;
          }else{
            intTargetPos[intTargetNum][0] = intScreenMaxRight;
          }          
        }
      }
    }catch(ArrayIndexOutOfBoundsException e){
      //Darnit
    }
  }
  
  //-------------------------------------
  // Target Movement Method
  //-------------------------------------
  public void targetPhysics(int intTargetNum) {
    if (intTargetDir[intTargetNum] == 0){
      if (intTargetNum <3){
      intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] +1;
      }else if (intTargetNum > 3 && intTargetNum <6){
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] +3;
      }else if(intTargetNum == 9){
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] +30;  //easter egg
      }else{
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] +5;
      }
      if (intTargetPos[intTargetNum][0] > intScreenMaxRight){ //Direction Switcher
        intTargetDir[intTargetNum] = 1;
      }
    }else if (intTargetDir[intTargetNum] == 1){
      if (intTargetNum <3){
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] -1;
      }else if (intTargetNum > 3 && intTargetNum <6){
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] -3;
      }else if(intTargetNum == 9){
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] -30; //easter egg
      }else{
        intTargetPos[intTargetNum][0] = intTargetPos[intTargetNum][0] -5;
      }
      if (intTargetPos[intTargetNum][0] < intScreenMaxLeft){//Direction Switcher
        intTargetDir[intTargetNum] = 0;
      }
    } 
  }
  
  //-------------------------------------
  // Reset Targets
  //-------------------------------------
  public void resetTargets() {
    intTargetPos[0][0] = intScreenMaxLeft;
    intTargetPos[0][1] = (int)((Math.random()*480)+50);
    intTargetPos[1][0] = intScreenMaxRight;
    intTargetPos[1][1] = (int)((Math.random()*480)+50);
    intTargetPos[2][0] = intScreenMaxLeft;
    intTargetPos[2][1] = (int)((Math.random()*480)+50);
    intTargetPos[3][0] = intScreenMaxRight;
    intTargetPos[3][1] = (int)((Math.random()*480)+50);
    intTargetPos[4][0] = intScreenMaxLeft;
    intTargetPos[4][1] = (int)((Math.random()*480)+50);
    intTargetPos[5][0] = intScreenMaxRight;
    intTargetPos[5][1] = (int)((Math.random()*480)+50);
    intTargetPos[6][0] = intScreenMaxLeft;
    intTargetPos[6][1] = (int)((Math.random()*480)+50);
    intTargetPos[7][0] = intScreenMaxRight;
    intTargetPos[7][1] = (int)((Math.random()*480)+50);
    intTargetPos[8][0] = intScreenMaxLeft;
    intTargetPos[8][1] = (int)((Math.random()*480)+50);
    intTargetPos[9][0] = intScreenMaxRight;
    intTargetPos[9][1] = (int)((Math.random()*480)+50);
  }
  
  //-------------------------------------
  // Log Append
  //-------------------------------------
  public void append(String text) {
    textarea.append(text  + "\n");
    
    textarea.scrollRectToVisible(new Rectangle(0,textarea.getHeight()-2,1,1));
  }
  
  //-------------------------------------
  // Screen Update
  //-------------------------------------
  public void ServerScreenRefresh(){  //refresh the server screen. Can be slow to preserve CPU. 
    p1Name.setText("" + strPlayersNames[0][0]);
    p2Name.setText("" + strPlayersNames[1][0]);
    p3Name.setText("" + strPlayersNames[2][0]);
    p4Name.setText("" + strPlayersNames[3][0]);
    p1Score.setText("" + intPlayersData[0][0]);
    p2Score.setText("" + intPlayersData[1][0]);
    p3Score.setText("" + intPlayersData[2][0]);
    p4Score.setText("" + intPlayersData[3][0]);
    p1X.setText("" + intPlayersData[0][1]);
    p2X.setText("" + intPlayersData[1][1]);
    p3X.setText("" + intPlayersData[2][1]);
    p4X.setText("" + intPlayersData[3][1]);
    p1Y.setText("" + intPlayersData[0][2]);
    p2Y.setText("" + intPlayersData[1][2]);
    p3Y.setText("" + intPlayersData[2][2]);
    p4Y.setText("" + intPlayersData[3][2]);
    t1X.setText("" + intTargetPos[0][0]);
    t2X.setText("" + intTargetPos[1][0]);
    t3X.setText("" + intTargetPos[2][0]);
    t4X.setText("" + intTargetPos[3][0]);
    t5X.setText("" + intTargetPos[4][0]);
    t6X.setText("" + intTargetPos[5][0]);
    t7X.setText("" + intTargetPos[6][0]);
    t8X.setText("" + intTargetPos[7][0]);
    t9X.setText("" + intTargetPos[8][0]);
    t10X.setText("" + intTargetPos[9][0]);
    t1Y.setText("" + intTargetPos[0][1]);
    t2Y.setText("" + intTargetPos[1][1]);
    t3Y.setText("" + intTargetPos[2][1]);
    t4Y.setText("" + intTargetPos[3][1]);
    t5Y.setText("" + intTargetPos[4][1]);
    t6Y.setText("" + intTargetPos[5][1]);
    t7Y.setText("" + intTargetPos[6][1]);
    t8Y.setText("" + intTargetPos[7][1]);
    t9Y.setText("" + intTargetPos[8][1]);
    t10Y.setText("" + intTargetPos[9][1]);
    t1Hit.setText("" + intTargetStat[0]);
    t2Hit.setText("" + intTargetStat[1]);
    t3Hit.setText("" + intTargetStat[2]);
    t4Hit.setText("" + intTargetStat[3]);
    t5Hit.setText("" + intTargetStat[4]);
    t6Hit.setText("" + intTargetStat[5]);
    t7Hit.setText("" + intTargetStat[6]);
    t8Hit.setText("" + intTargetStat[7]);
    t9Hit.setText("" + intTargetStat[8]);
    t10Hit.setText("" + intTargetStat[9]);
    numConnectedPlayersTextfield.setText("" + intNumConnectedPlayers);
    gameStarted.setText("" + gameStart);
    roundNum.setText("" + intRound);
    
  }
  
  
  //-------------------------------------
  // Pause
  //-------------------------------------
  public static void pause(int intMS){
    try{
      Thread.sleep(intMS);
    }catch(InterruptedException e){
    } 
    
  }
  
  //Constructor
  public TargetServer (){
    //-------------------------------------
    // Starting Frame
    //-------------------------------------
    startFrame = new JFrame ("Space Blaster Server - Start Page");
    startFrame.setSize(500,200);
    //startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    startFrame.setResizable(false);
    
    startPanel = new JPanel();
    startPanel.setLayout(null);
    startFrame.setContentPane(startPanel);
    
    title = new JLabel("Space Blaster Server");
    title.setSize (600,50);
    title.setLocation(40,15);
    title.setFont(new Font("Arial", Font.ITALIC, 40)); 
    startPanel.add(title);
    
    startDesc = new JLabel("This is the server for Space Blaster. A seperate server is needed to handle 4 players.");
    startDesc.setSize (600,50);
    startDesc.setLocation(40,45);
    startDesc.setFont(new Font("Arial", Font.PLAIN, 10));
    startPanel.add(startDesc);
    
    startPortLabel = new JLabel("Port Number:");
    startPortLabel.setSize (600,50);
    startPortLabel.setLocation (40, 100);
    startPortLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    startPanel.add(startPortLabel);
    
    portTextfield = new JTextField("9634");
    portTextfield.setSize (100, 25);
    portTextfield.setLocation(165, 113);
    portTextfield.setEditable(true);
    startPanel.add(portTextfield);
    
    startButton = new JButton("Start Server");
    startButton.addActionListener(this);
    startButton.setSize (140,25);
    startButton.setLocation(295, 113);
    startPanel.add(startButton);
    
    //-------------------------------------
    // Running Frame
    //-------------------------------------
    runFrame = new JFrame ("Space Blaster Server - Running Statistics");
    runFrame.setSize(600,530);
//    runFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    runFrame.setResizable(false);
    
    runPanel = new JPanel();
    runPanel.setLayout(null);
    runFrame.setContentPane(runPanel);
    
    try{
      strIP = InetAddress.getLocalHost().getHostAddress();
    }catch(Exception e){
    }
    
    ssmfield = new JTextField("Server started. IP: " + strIP);
    ssmfield.addActionListener(this);
    ssmfield.setSize(600,25);
    ssmfield.setVisible (false);
    ssmfield.setEditable (false);
    ssmfield.setFont(new Font("Arial", Font.BOLD, 12));
    
    runtitle = new JLabel(" Space Blaster Server Statistics");
    runtitle.setSize (630,50);
    runtitle.setLocation(44,15);
    runtitle.setFont(new Font("Arial", Font.ITALIC, 33)); 
    runPanel.add(runtitle);
    
    numConnectedPlayersLabel = new JLabel("Server IP:                                   Connected Players:            Error Detected:               Round: ");
    numConnectedPlayersLabel.setSize(600,50);
    numConnectedPlayersLabel.setLocation (30,80);
    numConnectedPlayersLabel.setFont(new Font("Arial", Font.BOLD, 12));
    runPanel.add(numConnectedPlayersLabel);
    
    serverIP = new JTextField(strIP);
    serverIP.setSize (90, 25);
    serverIP.setLocation(90, 93);
    serverIP.setEditable(false);
    serverIP.setFont(new Font("Arial", Font.PLAIN, 12));
    runPanel.add(serverIP);
    
    numConnectedPlayersTextfield = new JTextField("0");
    numConnectedPlayersTextfield.setSize (20, 25);
    numConnectedPlayersTextfield.setLocation(310, 93);
    numConnectedPlayersTextfield.setEditable(false);
    numConnectedPlayersTextfield.setFont(new Font("Arial", Font.PLAIN, 12));
    runPanel.add(numConnectedPlayersTextfield);
    
    gameStarted = new JTextField("false");
    gameStarted.setSize (35, 25);
    gameStarted.setLocation(425, 93);
    gameStarted.setEditable(false);
    gameStarted.setFont(new Font("Arial", Font.PLAIN, 12));
    runPanel.add(gameStarted);
    
    roundNum = new JTextField("0");
    roundNum.setSize (20, 25);
    roundNum.setLocation(510, 93);
    roundNum.setEditable(false);
    roundNum.setFont(new Font("Arial", Font.PLAIN, 12));
    runPanel.add(roundNum);
    
    playerStatisticsLabel = new JLabel("Name             Score          X       Y                                               X         Y           Hit");
    playerStatisticsLabel.setSize(600,50);
    playerStatisticsLabel.setLocation (115, 120);
    playerStatisticsLabel.setFont(new Font("Arial", Font.BOLD, 12));
    runPanel.add(playerStatisticsLabel);
    
    player1 = new JLabel("Player 1:");
    player2 = new JLabel("Player 2:");
    player3 = new JLabel("Player 3:");
    player4 = new JLabel("Player 4:");
    player1.setSize(600,50);
    player2.setSize(600,50);
    player3.setSize(600,50);
    player4.setSize(600,50);
    player1.setLocation(30, 150);
    player2.setLocation(30, 180);
    player3.setLocation(30, 210);
    player4.setLocation(30, 240);
    player1.setFont(new Font("Arial", Font.BOLD, 12));
    player2.setFont(new Font("Arial", Font.BOLD, 12));
    player3.setFont(new Font("Arial", Font.BOLD, 12));
    player4.setFont(new Font("Arial", Font.BOLD, 12));
    runPanel.add(player1);
    runPanel.add(player2);
    runPanel.add(player3);
    runPanel.add(player4);
    
    p1Name = new JTextField ("NotConnected");
    p2Name = new JTextField ("NotConnected");
    p3Name = new JTextField ("NotConnected");
    p4Name = new JTextField ("NotConnected");
    p1Name.setSize(90,25);
    p2Name.setSize(90,25);
    p3Name.setSize(90,25);
    p4Name.setSize(90,25);
    p1Name.setLocation(90, 160);
    p2Name.setLocation(90, 190);
    p3Name.setLocation(90, 220);
    p4Name.setLocation(90, 250);
    p1Name.setEditable(false);
    p2Name.setEditable(false);
    p3Name.setEditable(false);
    p4Name.setEditable(false);
    p1Name.setFont(new Font("Arial", Font.PLAIN, 12));
    p2Name.setFont(new Font("Arial", Font.PLAIN, 12));
    p3Name.setFont(new Font("Arial", Font.PLAIN, 12));
    p4Name.setFont(new Font("Arial", Font.PLAIN, 12));
    runPanel.add(p1Name);
    runPanel.add(p2Name);
    runPanel.add(p3Name);
    runPanel.add(p4Name);
    
    p1Score = new JTextField ("0");
    p2Score = new JTextField ("0");
    p3Score = new JTextField ("0");
    p4Score = new JTextField ("0");
    p1Score.setSize(50,25);
    p2Score.setSize(50,25);
    p3Score.setSize(50,25);
    p4Score.setSize(50,25);
    p1Score.setLocation(180, 160);
    p2Score.setLocation(180, 190);
    p3Score.setLocation(180, 220);
    p4Score.setLocation(180, 250);
    p1Score.setEditable(false);
    p2Score.setEditable(false);
    p3Score.setEditable(false);
    p4Score.setEditable(false);
    p1Score.setFont(new Font("Arial", Font.PLAIN, 12));
    p2Score.setFont(new Font("Arial", Font.PLAIN, 12));
    p3Score.setFont(new Font("Arial", Font.PLAIN, 12));
    p4Score.setFont(new Font("Arial", Font.PLAIN, 12));
    runPanel.add(p1Score);
    runPanel.add(p2Score);
    runPanel.add(p3Score);
    runPanel.add(p4Score);
    
    p1X = new JTextField ("0");
    p2X = new JTextField ("0");
    p3X = new JTextField ("0");
    p4X = new JTextField ("0");
    p1X.setSize(30,25);
    p2X.setSize(30,25);
    p3X.setSize(30,25);
    p4X.setSize(30,25);
    p1X.setLocation(240, 160);
    p2X.setLocation(240, 190);
    p3X.setLocation(240, 220);
    p4X.setLocation(240, 250);
    p1X.setEditable(false);
    p2X.setEditable(false);
    p3X.setEditable(false);
    p4X.setEditable(false);
    p1X.setFont(new Font("Arial", Font.PLAIN, 10));
    p2X.setFont(new Font("Arial", Font.PLAIN, 10));
    p3X.setFont(new Font("Arial", Font.PLAIN, 10));
    p4X.setFont(new Font("Arial", Font.PLAIN, 10));
    runPanel.add(p1X);
    runPanel.add(p2X);
    runPanel.add(p3X);
    runPanel.add(p4X);
    
    p1Y = new JTextField ("0");
    p2Y = new JTextField ("0");
    p3Y = new JTextField ("0");
    p4Y = new JTextField ("0");
    p1Y.setSize(30,25);
    p2Y.setSize(30,25);
    p3Y.setSize(30,25);
    p4Y.setSize(30,25);
    p1Y.setLocation(270, 160);
    p2Y.setLocation(270, 190);
    p3Y.setLocation(270, 220);
    p4Y.setLocation(270, 250);
    p1Y.setEditable(false);
    p2Y.setEditable(false);
    p3Y.setEditable(false);
    p4Y.setEditable(false);
    p1Y.setFont(new Font("Arial", Font.PLAIN, 10));
    p2Y.setFont(new Font("Arial", Font.PLAIN, 10));
    p3Y.setFont(new Font("Arial", Font.PLAIN, 10));
    p4Y.setFont(new Font("Arial", Font.PLAIN, 10));
    runPanel.add(p1Y);
    runPanel.add(p2Y);
    runPanel.add(p3Y);
    runPanel.add(p4Y);
    
    scoreOveride = new JButton ("Override Scores");
    scoreOveride.addActionListener(this);
    scoreOveride.setSize (270, 25);
    scoreOveride.setLocation(30, 290);
    runPanel.add(scoreOveride);
    
    forceReset = new JButton ("Force Reset of game");
    forceReset.addActionListener(this);
    forceReset.setSize (270, 25);
    forceReset.setLocation(30, 320);
    runPanel.add(forceReset);
    
    target1 = new JLabel("Target 1:");
    target2 = new JLabel("Target 2:");
    target3 = new JLabel("Target 3:");
    target4 = new JLabel("Target 4:");
    target5 = new JLabel("Target 5:");
    target6 = new JLabel("Target 6:");
    target7 = new JLabel("Target 7:");
    target8 = new JLabel("Target 8:");
    target9 = new JLabel("Target 9:");
    target10 = new JLabel("Target 10:");
    target1.setSize(60,50);
    target2.setSize(60,50);
    target3.setSize(60,50);
    target4.setSize(60,50);
    target5.setSize(60,50);
    target6.setSize(60,50);
    target7.setSize(60,50);
    target8.setSize(60,50);
    target9.setSize(60,50);
    target10.setSize(60,50);
    target1.setLocation(350, 150);
    target2.setLocation(350, 180);
    target3.setLocation(350, 210);
    target4.setLocation(350, 240);
    target5.setLocation(350, 270);
    target6.setLocation(350, 300);
    target7.setLocation(350, 330);
    target8.setLocation(350, 360);
    target9.setLocation(350, 390);
    target10.setLocation(350, 420);
    target1.setFont(new Font("Arial", Font.BOLD, 12));
    target2.setFont(new Font("Arial", Font.BOLD, 12));
    target3.setFont(new Font("Arial", Font.BOLD, 12));
    target4.setFont(new Font("Arial", Font.BOLD, 12));
    target5.setFont(new Font("Arial", Font.BOLD, 12));
    target6.setFont(new Font("Arial", Font.BOLD, 12));
    target7.setFont(new Font("Arial", Font.BOLD, 12));
    target8.setFont(new Font("Arial", Font.BOLD, 12));
    target9.setFont(new Font("Arial", Font.BOLD, 12));
    target10.setFont(new Font("Arial", Font.BOLD, 11));
    runPanel.add(target1);
    runPanel.add(target2);
    runPanel.add(target3);
    runPanel.add(target4);
    runPanel.add(target5);
    runPanel.add(target6);
    runPanel.add(target7);
    runPanel.add(target8);
    runPanel.add(target9);
    runPanel.add(target10);
    
    t1X = new JTextField ("0");
    t2X = new JTextField ("0");
    t3X = new JTextField ("0");
    t4X = new JTextField ("0");
    t5X = new JTextField ("0");
    t6X = new JTextField ("0");
    t7X = new JTextField ("0");
    t8X = new JTextField ("0");
    t9X = new JTextField ("0");
    t10X = new JTextField ("0");
    t1X.setSize(30,25);
    t2X.setSize(30,25);
    t3X.setSize(30,25);
    t4X.setSize(30,25);
    t5X.setSize(30,25);
    t6X.setSize(30,25);
    t7X.setSize(30,25);
    t8X.setSize(30,25);
    t9X.setSize(30,25);
    t10X.setSize(30,25);
    t1X.setLocation(420, 160);
    t2X.setLocation(420, 190);
    t3X.setLocation(420, 220);
    t4X.setLocation(420, 250);
    t5X.setLocation(420, 280);
    t6X.setLocation(420, 310);
    t7X.setLocation(420, 340);
    t8X.setLocation(420, 370);
    t9X.setLocation(420, 400);
    t10X.setLocation(420, 430);
    t1X.setEditable (false);
    t2X.setEditable (false);
    t3X.setEditable (false);
    t4X.setEditable (false);
    t5X.setEditable (false);
    t6X.setEditable (false);
    t7X.setEditable (false);
    t8X.setEditable (false);
    t9X.setEditable (false);
    t10X.setEditable (false);
    t1X.setFont(new Font("Arial", Font.PLAIN, 10));
    t2X.setFont(new Font("Arial", Font.PLAIN, 10));
    t3X.setFont(new Font("Arial", Font.PLAIN, 10));
    t4X.setFont(new Font("Arial", Font.PLAIN, 10));
    t5X.setFont(new Font("Arial", Font.PLAIN, 10));
    t6X.setFont(new Font("Arial", Font.PLAIN, 10));
    t7X.setFont(new Font("Arial", Font.PLAIN, 10));
    t8X.setFont(new Font("Arial", Font.PLAIN, 10));
    t9X.setFont(new Font("Arial", Font.PLAIN, 10));
    t10X.setFont(new Font("Arial", Font.PLAIN, 10));
    runPanel.add(t1X);
    runPanel.add(t2X);
    runPanel.add(t3X);
    runPanel.add(t4X);
    runPanel.add(t5X);
    runPanel.add(t6X);
    runPanel.add(t7X);
    runPanel.add(t8X);
    runPanel.add(t9X);
    runPanel.add(t10X);
    
    t1Y = new JTextField ("0");
    t2Y = new JTextField ("0");
    t3Y = new JTextField ("0");
    t4Y = new JTextField ("0");
    t5Y = new JTextField ("0");
    t6Y = new JTextField ("0");
    t7Y = new JTextField ("0");
    t8Y = new JTextField ("0");
    t9Y = new JTextField ("0");
    t10Y = new JTextField ("0");
    t1Y.setSize(30,25);
    t2Y.setSize(30,25);
    t3Y.setSize(30,25);
    t4Y.setSize(30,25);
    t5Y.setSize(30,25);
    t6Y.setSize(30,25);
    t7Y.setSize(30,25);
    t8Y.setSize(30,25);
    t9Y.setSize(30,25);
    t10Y.setSize(30,25);
    t1Y.setLocation(450, 160);
    t2Y.setLocation(450, 190);
    t3Y.setLocation(450, 220);
    t4Y.setLocation(450, 250);
    t5Y.setLocation(450, 280);
    t6Y.setLocation(450, 310);
    t7Y.setLocation(450, 340);
    t8Y.setLocation(450, 370);
    t9Y.setLocation(450, 400);
    t10Y.setLocation(450, 430);
    t1Y.setEditable (false);
    t2Y.setEditable (false);
    t3Y.setEditable (false);
    t4Y.setEditable (false);
    t5Y.setEditable (false);
    t6Y.setEditable (false);
    t7Y.setEditable (false);
    t8Y.setEditable (false);
    t9Y.setEditable (false);
    t10Y.setEditable (false);
    t1Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t2Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t3Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t4Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t5Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t6Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t7Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t8Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t9Y.setFont(new Font("Arial", Font.PLAIN, 10));
    t10Y.setFont(new Font("Arial", Font.PLAIN, 10));
    runPanel.add(t1Y);
    runPanel.add(t2Y);
    runPanel.add(t3Y);
    runPanel.add(t4Y);
    runPanel.add(t5Y);
    runPanel.add(t6Y);
    runPanel.add(t7Y);
    runPanel.add(t8Y);
    runPanel.add(t9Y);
    runPanel.add(t10Y);
    
    t1Hit = new JTextField ("false");
    t2Hit = new JTextField ("false");
    t3Hit = new JTextField ("false");
    t4Hit = new JTextField ("false");
    t5Hit = new JTextField ("false");
    t6Hit = new JTextField ("false");
    t7Hit = new JTextField ("false");
    t8Hit = new JTextField ("false");
    t9Hit = new JTextField ("false");
    t10Hit = new JTextField ("false");
    t1Hit.setSize(50,25);
    t2Hit.setSize(50,25);
    t3Hit.setSize(50,25);
    t4Hit.setSize(50,25);
    t5Hit.setSize(50,25);
    t6Hit.setSize(50,25);
    t7Hit.setSize(50,25);
    t8Hit.setSize(50,25);
    t9Hit.setSize(50,25);
    t10Hit.setSize(50,25);
    t1Hit.setLocation(490, 160);
    t2Hit.setLocation(490, 190);
    t3Hit.setLocation(490, 220);
    t4Hit.setLocation(490, 250);
    t5Hit.setLocation(490, 280);
    t6Hit.setLocation(490, 310);
    t7Hit.setLocation(490, 340);
    t8Hit.setLocation(490, 370);
    t9Hit.setLocation(490, 400);
    t10Hit.setLocation(490, 430);
    t1Hit.setEditable (false);
    t2Hit.setEditable (false);
    t3Hit.setEditable (false);
    t4Hit.setEditable (false);
    t5Hit.setEditable (false);
    t6Hit.setEditable (false);
    t7Hit.setEditable (false);
    t8Hit.setEditable (false);
    t9Hit.setEditable (false);
    t10Hit.setEditable (false);
    runPanel.add(t1Hit);
    runPanel.add(t2Hit);
    runPanel.add(t3Hit);
    runPanel.add(t4Hit);
    runPanel.add(t5Hit);
    runPanel.add(t6Hit);
    runPanel.add(t7Hit);
    runPanel.add(t8Hit);
    runPanel.add(t9Hit);
    runPanel.add(t10Hit);
    
    textarea = new JTextArea ("Server started. IP: " + strIP  + "\n");
    DefaultCaret caret = (DefaultCaret)textarea.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    thescrollpane = new JScrollPane(textarea);
    thescrollpane.setSize (270, 100);
    thescrollpane.setLocation(30, 360);
    runPanel.add(thescrollpane);
    
    //Timers
    guiupdatetimer = new Timer(1000/8, this);  //This slow time will not affect the speed in which it handles/recieves data. This timer is merely to adjust the refresh rate of the server GUI
    scoretimer = new Timer(1000/5, this); //This timer is slow since scores do not need to be sent as frequently over to clients
    targettimer = new Timer(1000/60, this); //This timer is fast to constantly send updated target locations
    lobbytimer = new Timer(1000, this); //This timer is for the lobby/waiting room
    roundtimer = new Timer((1000), this); //This timer is for each round
    lobbysectimer = new Timer((1000), this); //exactly as it states
    
    //Set Visible
    startFrame.setVisible(true);
    
  }
  
  public static void main (String[] args){
    TargetServer screen = new TargetServer();
    
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        public void run() {
        //this.sendText("SERVERSHUTDOWN");
        }
    }, "Shutdown-thread"));
  }
}