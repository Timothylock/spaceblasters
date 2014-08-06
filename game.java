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
import java.awt.Font.*;
import javax.swing.text.*;
import java.net.*;
import java.awt.Cursor.*;
import java.awt.image.*; 

public class game implements ActionListener, MouseListener, MouseMotionListener{
  //properties
  //main window
  JFrame mainWindow;
  AnimationPanel thePanel;
  Timer frameTimer;
  LobbyPanel theLobby;
  int currentscreen = 2;
  int switchscreen = 1;
  //0 = lobby, 1 = game window
  
  //mainmenu
  Timer clientTimer;
  MainPanel menuPanel;
  JButton clientModeBut;
  JButton menuBack;
  JTextField serverIP;
  JTextField serverPort;
  JTextField namePort;
  JButton startClientBut;
  JButton serverModeBut;
  JButton helpBut;
  JButton quitBut;
  
  //help menu
  HelpPanel theHelp;
  JButton nextPage;
  JButton menuBack2;
  JButton nextPage2;
  
  //Tim Networking Components CONRAD NO CHANGE THESE PLZ
  Timer sendTimer;
  String strIP;
  JTextField ssmfield;
  SuperSocketMaster ssm;
  Thread ssmthread;
  String strLine;
  String strTemp[];
  
  int intShotCounter = 0;
  
  //Tim added Variables
  String strName = "Timmy";
  String strBroadcast;
  
  //chat
  JTextArea chat;
  JScrollPane chatScroll;
  JTextField sendChat;
  JButton sendButton;
  int moveChat = 0;
  
  //methods
  // Transparent 16 x 16 pixel cursor image.
  BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
  
  // Create a new blank cursor.
  Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
  
  //printing to leaderboard
  
  
  
  public void actionPerformed(ActionEvent evt){
    //Main Menu Action Listeners
    if(evt.getSource() == clientModeBut){  // Connect to Client
      clientModeBut.setVisible(false);
      serverIP.setVisible(true);
      serverPort.setVisible(true);
      namePort.setVisible(true);
      startClientBut.setVisible(true);
      serverModeBut.setVisible(false);
      helpBut.setVisible(false);
      quitBut.setVisible(false);
      menuBack.setVisible(true);
    }else if(evt.getSource() == startClientBut){
      clientTimer.start();
      strName = namePort.getText();
      try{
        ssm = new SuperSocketMaster(ssmfield, serverIP.getText() ,Integer.parseInt(serverPort.getText()));
        ssmthread = new Thread(ssm);
        ssmthread.start();
        //Login to server
        pause(2000);
        ssm.sendText("JOIN," + strName + "," + strIP);
        
        mainWindow.setContentPane(theLobby);
        mainWindow.setVisible(false);
        mainWindow.setVisible(true);
        
      }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Could not reach server. Please check your connection", "No Connection", JOptionPane.INFORMATION_MESSAGE);
        System.exit(-1);
      }
    }else if(evt.getSource() == menuBack){  //back button to main menu
      clientModeBut.setVisible(true);
      serverIP.setVisible(false);
      serverPort.setVisible(false);
      namePort.setVisible(false);
      startClientBut.setVisible(false);
      serverModeBut.setVisible(true);
      helpBut.setVisible(true);
      quitBut.setVisible(true);
      menuBack.setVisible(false);
    }else if(evt.getSource() == quitBut){  // quit
      System.exit(0);
    }else if(evt.getSource() == serverModeBut){
      String[] args = {};
      TargetServer.main(args);
    }else if(evt.getSource() == helpBut){
      theHelp.change = 0;
      mainWindow.setContentPane(theHelp);
        mainWindow.setVisible(false);
        mainWindow.setVisible(true);
    }else if(evt.getSource() == nextPage){
      theHelp.change = 1;
      nextPage.setVisible(false);
      nextPage2.setVisible(true);
      menuBack2.setVisible(true);
      theHelp.repaint();
    }else if(evt.getSource() == menuBack2){
      theHelp.change = 0;
      nextPage.setVisible(true);
      nextPage2.setVisible(false);
      menuBack2.setVisible(false);
      theHelp.repaint();
    }else if(evt.getSource() == nextPage2){
      mainWindow.setContentPane(menuPanel);
      mainWindow.setVisible(false);
      mainWindow.setVisible(true);
      nextPage.setVisible(true);
      nextPage2.setVisible(false);
      menuBack2.setVisible(false);
      theHelp.repaint();
    }
    
    
    //Server Messages
    if(evt.getSource() == ssmfield){
      //Recieving Locations
      strLine = ssmfield.getText();
      if (strLine.startsWith("DISC")){ 
        JOptionPane.showMessageDialog(mainWindow, "Lost connection to server!");
        System.exit(0);
      }
      
      if (strLine != null){ //Split message
        strTemp = strLine.split(",");
      }else{
        strLine = "IGNORE,FILLER,LOL";
        strTemp = strLine.split(",");
      }
      
      if(strTemp[0].equals("SCORES")){
        thePanel.intRoundNum = Integer.parseInt(strTemp[1]);
        thePanel.playerName[0] = strTemp[2];
        thePanel.playerName[1] = strTemp[3];
        thePanel.playerName[2] = strTemp[4];
        thePanel.playerName[3] = strTemp[5];
        thePanel.playerScore[0] = Integer.parseInt(strTemp[6]);
        thePanel.playerScore[1] = Integer.parseInt(strTemp[7]);
        thePanel.playerScore[2] = Integer.parseInt(strTemp[8]);
        thePanel.playerScore[3] = Integer.parseInt(strTemp[9]);
      }
      
      if(strTemp[0].equals("ALLRESET")){
        ssm.sendText("JOIN," + strName + "," + strIP);
      }
      
      if(strTemp[0].equals("LOCATION")){
        thePanel.intTimeLeft = Integer.parseInt(strTemp[1]);
        thePanel.targetArray[0][0] = Integer.parseInt(strTemp[2]);
        thePanel.targetArray[0][1] = Integer.parseInt(strTemp[3]);
        thePanel.targetArray[1][0] = Integer.parseInt(strTemp[4]);
        thePanel.targetArray[1][1] = Integer.parseInt(strTemp[5]);
        thePanel.targetArray[2][0] = Integer.parseInt(strTemp[6]);
        thePanel.targetArray[2][1] = Integer.parseInt(strTemp[7]);
        thePanel.targetArray[3][0] = Integer.parseInt(strTemp[8]);
        thePanel.targetArray[3][1] = Integer.parseInt(strTemp[9]);
        thePanel.targetArray[4][0] = Integer.parseInt(strTemp[10]);
        thePanel.targetArray[4][1] = Integer.parseInt(strTemp[11]);
        thePanel.targetArray[5][0] = Integer.parseInt(strTemp[12]);
        thePanel.targetArray[5][1] = Integer.parseInt(strTemp[13]);
        thePanel.targetArray[6][0] = Integer.parseInt(strTemp[14]);
        thePanel.targetArray[6][1] = Integer.parseInt(strTemp[15]);
        thePanel.targetArray[7][0] = Integer.parseInt(strTemp[16]);
        thePanel.targetArray[7][1] = Integer.parseInt(strTemp[17]);
        thePanel.targetArray[8][0] = Integer.parseInt(strTemp[18]);
        thePanel.targetArray[8][1] = Integer.parseInt(strTemp[19]);
        thePanel.targetArray[9][0] = Integer.parseInt(strTemp[20]);
        thePanel.targetArray[9][1] = Integer.parseInt(strTemp[21]);
      }
      
      if(strTemp[0].equals("PLAYERLOC")){
        thePanel.crosshairX0 = Integer.parseInt(strTemp[1]);
        thePanel.crosshairY0 = Integer.parseInt(strTemp[2]);
        thePanel.crosshairX1 = Integer.parseInt(strTemp[3]);
        thePanel.crosshairY1 = Integer.parseInt(strTemp[4]);
        thePanel.crosshairX2 = Integer.parseInt(strTemp[5]);
        thePanel.crosshairY2 = Integer.parseInt(strTemp[6]);
        thePanel.crosshairX3 = Integer.parseInt(strTemp[7]);
        thePanel.crosshairY3 = Integer.parseInt(strTemp[8]);
      }
      
      if (strTemp[0].equals(strIP)){
        if (strTemp[1].equals("DENY")){
          JOptionPane.showMessageDialog(mainWindow, "Server has refused your connection. Probably full!");
          System.exit(0);
        }else if (strTemp[1].equals("ACCEPT")){
          thePanel.intPlayerNum = Integer.parseInt(strTemp[2]);
          sendTimer.start();
          //(1 = 0, 2 = 1, 3 = 2, 4 = 3)
        }
      }
      
      if (strTemp[0].equals("BROADCAST")){
        chat.append(strTemp[1] + "\n");
        chat.scrollRectToVisible(new Rectangle(0,chat.getHeight()-2,1,1));
      }
      
      if (strTemp[0].equals("WAITING")){
        if (strTemp[1].equals("1")){
          switchscreen = 0;
        }
        if (strTemp[1].equals("0")){
          switchscreen = 1;
        }
        theLobby.strWaitingLobbyTime = strTemp[2];
      }
    }
    if(evt.getSource() == sendButton){
      ssm.sendText("CHAT," + thePanel.intPlayerNum + "," + sendChat.getText());
      sendChat.setText("");
    }
    //TImers
    if(evt.getSource() == frameTimer){
      thePanel.repaint();
      theLobby.repaint();
    }
    
    if(evt.getSource() == clientTimer){
      //Tim Code. Check if panel switch required. 
      if(switchscreen == 0 && currentscreen != 0){
        theLobby.add(chatScroll);
        theLobby.add(sendChat);
        theLobby.add(sendButton);
        mainWindow.setContentPane(theLobby);
        mainWindow.setVisible(false);
        mainWindow.setVisible(true);
        currentscreen = 0;
        System.out.println("switched to lobby");
        theLobby.getRootPane().setDefaultButton(sendButton);
      }else if(switchscreen == 1 && currentscreen !=1){
        mainWindow.setContentPane(thePanel);
        mainWindow.setVisible(false);
        mainWindow.setVisible(true);
        currentscreen = 1;
        // Set the blank cursor to the JFrame.
        mainWindow.getContentPane().setCursor(blankCursor);
        System.out.println("switched to main screen");
      }
    }
    
    if(evt.getSource() == sendTimer){ //if its time to send client location
      ssm.sendText("CLIENTLOCATION," + thePanel.intPlayerNum + "," + thePanel.crosshairX + "," + thePanel.crosshairY + "," + thePanel.shot + ",");
      if (thePanel.shot == 1){
        intShotCounter ++;
        if (intShotCounter == 5){
          intShotCounter = 0;
          thePanel.shot = 0;
        }
      }
    }
  }
  public void mouseDragged(MouseEvent evt){
    
  }
  public void mouseMoved(MouseEvent evt){
    thePanel.crosshairX = evt.getX() - 25;
    if(evt.getY() > 600){
      thePanel.crosshairY =  600 - 25;
    }else{
      thePanel.crosshairY = evt.getY() - 25;
    }
  }
  
  public void mousePressed(MouseEvent evt){
  }
  public void mouseExited(MouseEvent evt){
  }
  public void mouseEntered(MouseEvent evt){
  }
  public void mouseClicked(MouseEvent evt){
    System.out.println("Shoot!");
    thePanel.shot = 1;
  }
  public void mouseReleased(MouseEvent evt){
  }
  
  public game(){
    //main window
    mainWindow = new JFrame("Space Blasters");
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.setSize(1280, 800);
    mainWindow.setResizable(false);
    
    //PRESTART PANEL & Main Menu
    menuPanel = new MainPanel();
    menuPanel.setLayout(null);
    
    clientModeBut = new JButton("Connect To Server");
    clientModeBut.addActionListener(this);
    clientModeBut.setSize(400, 35);
    clientModeBut.setLocation(440, 320);
    clientModeBut.setFont(new Font("Calibri", Font.PLAIN, 25));
    clientModeBut.setBackground(Color.BLACK);
    clientModeBut.setForeground(Color.WHITE);
    menuPanel.add(clientModeBut);
    
    serverModeBut = new JButton("Run Server (new window)");
    serverModeBut.addActionListener(this);
    serverModeBut.setSize(400, 35);
    serverModeBut.setLocation(440, 380);
    serverModeBut.setFont(new Font("Calibri", Font.PLAIN, 25));
    serverModeBut.setBackground(Color.BLACK);
    serverModeBut.setForeground(Color.WHITE);
    menuPanel.add(serverModeBut);
    
    helpBut = new JButton("How To Play");
    helpBut.addActionListener(this);
    helpBut.setSize(400, 35);
    helpBut.setLocation(440, 440);
    helpBut.setFont(new Font("Calibri", Font.PLAIN, 25));
    helpBut.setBackground(Color.BLACK);
    helpBut.setForeground(Color.WHITE);
    menuPanel.add(helpBut);
    
    quitBut = new JButton("Quit Game");
    quitBut.addActionListener(this);
    quitBut.setSize(400, 35);
    quitBut.setLocation(440, 500);
    quitBut.setFont(new Font("Calibri", Font.PLAIN, 25));
    quitBut.setBackground(Color.BLACK);
    quitBut.setForeground(Color.WHITE);
    menuPanel.add(quitBut);
    
    serverIP = new JTextField("127.0.0.1");
    serverIP.addActionListener(this);
    serverIP.setSize(200, 35);
    serverIP.setLocation(540, 320);
    serverIP.setFont(new Font("Calibri", Font.PLAIN, 20));
    serverIP.setVisible(false);
    serverIP.setBackground(Color.BLACK);
    serverIP.setForeground(Color.WHITE);
    menuPanel.add(serverIP);
    
    serverPort = new JTextField("9634");
    serverPort.addActionListener(this);
    serverPort.setSize(200, 35);
    serverPort.setLocation(540, 380);
    serverPort.setFont(new Font("Calibri", Font.PLAIN, 20));
    serverPort.setVisible(false);
    serverPort.setBackground(Color.BLACK);
    serverPort.setForeground(Color.WHITE);
    menuPanel.add(serverPort);
    
    namePort = new JTextField("Derp");
    namePort.addActionListener(this);
    namePort.setSize(200, 35);
    namePort.setLocation(540, 440);
    namePort.setFont(new Font("Calibri", Font.PLAIN, 20));
    namePort.setVisible(false);
    namePort.setBackground(Color.BLACK);
    namePort.setForeground(Color.WHITE);
    menuPanel.add(namePort);
    
    startClientBut = new JButton("Connect!");
    startClientBut.addActionListener(this);
    startClientBut.setSize(300, 35);
    startClientBut.setLocation(490, 500);
    startClientBut.setFont(new Font("Calibri", Font.PLAIN, 25));
    startClientBut.setVisible(false);
    startClientBut.setBackground(Color.BLACK);
    startClientBut.setForeground(Color.WHITE);
    menuPanel.add(startClientBut);
    
    menuBack = new JButton("Back");
    menuBack.addActionListener(this);
    menuBack.setSize(100, 30);
    menuBack.setLocation(50, 50);
    menuBack.setFont(new Font("Calibri", Font.PLAIN, 25));
    menuBack.setVisible(false);
    menuBack.setBackground(Color.BLACK);
    menuBack.setForeground(Color.WHITE);
    menuPanel.add(menuBack);
    
    theHelp = new HelpPanel();
    theHelp.setLayout(null);
    
    nextPage = new JButton("Next");
    nextPage.addActionListener(this);
    nextPage.setSize(100, 30);
    nextPage.setLocation(1130, 720);
    nextPage.setFont(new Font("Calibri", Font.PLAIN, 25));
    nextPage.setVisible(true);
    nextPage.setBackground(Color.YELLOW);
    nextPage.setForeground(Color.BLACK);
    theHelp.add(nextPage);
    
    menuBack2 = new JButton("Back");
    menuBack2.addActionListener(this);
    menuBack2.setSize(100, 30);
    menuBack2.setLocation(50, 50);
    menuBack2.setFont(new Font("Calibri", Font.PLAIN, 25));
    menuBack2.setVisible(false);
    menuBack2.setBackground(Color.YELLOW);
    menuBack2.setForeground(Color.BLACK);
    theHelp.add(menuBack2);
    
    nextPage2 = new JButton("Main");
    nextPage2.addActionListener(this);
    nextPage2.setSize(100, 30);
    nextPage2.setLocation(1130, 720);
    nextPage2.setFont(new Font("Calibri", Font.PLAIN, 25));
    nextPage2.setVisible(false);
    nextPage2.setBackground(Color.YELLOW);
    nextPage2.setForeground(Color.BLACK);
    theHelp.add(nextPage2);
    

    
    //Rest of the stuff
    thePanel = new AnimationPanel();
    thePanel.setLayout(null);
    thePanel.addMouseListener(this);
    thePanel.addMouseMotionListener(this);
    
    theLobby = new LobbyPanel();
    theLobby.setLayout(null);
    
    
    ssmfield = new JTextField("HERROOOO....");
    ssmfield.addActionListener(this);
    ssmfield.setSize(600,25);
    ssmfield.setVisible (false);
    ssmfield.setEditable (false);
    
    chat = new JTextArea("");
    chatScroll = new JScrollPane(chat);
    chatScroll.setSize(1200, 200);
    chatScroll.setLocation(40, 500);
    chat.setEditable(false);
    chatScroll.getViewport().setOpaque(false);
    chatScroll.setOpaque(false);
    chat.setOpaque(false);
    chat.setForeground(Color.WHITE);
    chat.setFont(new Font("Calibri", Font.PLAIN, 15));
    
    
    sendChat = new JTextField();
    sendChat.setSize(1100, 25);
    sendChat.setLocation(40, 725);
    sendChat.setOpaque(false);
    sendChat.setForeground(Color.WHITE);
    sendChat.setFont(new Font("Calibri", Font.PLAIN, 15));
    
    
    sendButton = new JButton("Send");
    sendButton.addActionListener(this);
    sendButton.setSize(100, 25);
    sendButton.setLocation(1140, 725);
    sendButton.setOpaque(false);
    sendButton.setContentAreaFilled(false);
    sendButton.setForeground(Color.WHITE);
    sendButton.setFont(new Font("Calibri", Font.PLAIN, 20));
    
    //Networking Component
    try{
      strIP = InetAddress.getLocalHost().getHostAddress();
    }catch(Exception e){
    }
    
        
    frameTimer = new Timer(10, this);
    frameTimer.start();
    
    sendTimer = new Timer(1000/60, this);
    clientTimer = new Timer(1000/3, this);
    
    mainWindow.setContentPane(menuPanel);
    menuPanel.getRootPane().setDefaultButton(startClientBut);
    mainWindow.setVisible(true);
    music.startMidi("goodbye.mid");
  }
  
  public static void pause(int intMS){
    try{
      Thread.sleep(intMS);
    }catch(InterruptedException e){
    } 
    
  }
  
  public static void main(String[] args){
    game theanim = new game(); 
  }
}