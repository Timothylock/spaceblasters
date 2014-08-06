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
import java.awt.image.*; 
import java.io.IOException; 
import javax.imageio.*; 
import java.io.File; 



public class AnimationPanel extends JPanel{ 
  //properties
  int crosshairX = 1500;
  int crosshairY = 1000;
  
  //other crosshairs
  int crosshairX0 = 1500;
  int crosshairY0 = 1000;
  int crosshairX1 = 1500;
  int crosshairY1 = 1000;
  int crosshairX2 = 1500;
  int crosshairY2 = 1000;
  int crosshairX3 = 1500;
  int crosshairY3 = 1000;
  int shot = 0;
  int leaderboardX = 50;
  int leaderboardY = 50;
  ImageIcon background = new ImageIcon(getClass().getResource("pictures/background.jpg"));
  ImageIcon personalscore = new ImageIcon(getClass().getResource("pictures/personalscore.png"));
  ImageIcon leaderboard = new ImageIcon(getClass().getResource("pictures/leaderboard.png"));
  ImageIcon crosshair = new ImageIcon(getClass().getResource("pictures/crosshair.png"));
  ImageIcon crosshair0 = new ImageIcon(getClass().getResource("pictures/crosshair0.png"));
  ImageIcon crosshair1 = new ImageIcon(getClass().getResource("pictures/crosshair1.png"));
  ImageIcon crosshair2 = new ImageIcon(getClass().getResource("pictures/crosshair2.png"));
  ImageIcon crosshair3 = new ImageIcon(getClass().getResource("pictures/crosshair3.png"));
  ImageIcon shotAnimation = new ImageIcon(getClass().getResource("pictures/shotAnimation.png"));
  ImageIcon gun = new ImageIcon(getClass().getResource("pictures/gun.png"));
  int targetArray[][] = new int[10][2];
  ImageIcon target1 = new ImageIcon(getClass().getResource("pictures/target1.png"));
  ImageIcon target2 = new ImageIcon(getClass().getResource("pictures/target2.png"));
  ImageIcon target3 = new ImageIcon(getClass().getResource("pictures/target3.png"));
  ImageIcon target4 = new ImageIcon(getClass().getResource("pictures/target4.png"));
  ImageIcon target5 = new ImageIcon(getClass().getResource("pictures/target5.png"));
  ImageIcon target6 = new ImageIcon(getClass().getResource("pictures/target6.png"));
  ImageIcon target7 = new ImageIcon(getClass().getResource("pictures/target7.png"));
  ImageIcon target8 = new ImageIcon(getClass().getResource("pictures/target8.png"));
  ImageIcon target9 = new ImageIcon(getClass().getResource("pictures/target9.png"));
  ImageIcon target10 = new ImageIcon(getClass().getResource("pictures/target10.png"));
  int intPlayerNum = 1;
  int intRoundNum;
  int moving = 0;
  int intTimeLeft;
  ImageIcon round1 = new ImageIcon(getClass().getResource("pictures/round1.png"));
  ImageIcon round2 = new ImageIcon(getClass().getResource("pictures/round2.png"));
  ImageIcon round3 = new ImageIcon(getClass().getResource("pictures/round3.png"));
  
  
//leaderboard variables
  String playerName[] = new String[4];
  int playerScore[] = new int[4];
  
  
  //Methods 
  //overides JPanel 
  public void paintComponent(Graphics g){ 
    Graphics2D g2d = (Graphics2D)g; 
    g2d.setFont(new Font("Calibri", Font.PLAIN, 15));
    g2d.clearRect(0,0,1280,800);
    background.paintIcon(this, g2d, 0, 0);
    leaderboard.paintIcon(this, g2d, leaderboardX, leaderboardY);
    
    if(!playerName[0].equals("Player 1: null") && !playerName[0].equals("Player 2: null") && !playerName[0].equals("Player 3: null") && !playerName[0].equals("Player 4: null")){  //TIM EDIT. The string did not contain a null value. It was actually a string value that said "null"
      g2d.drawString(playerName[0], leaderboardX + 13, leaderboardY + 100);
      if(playerScore[0] == 0){
      g2d.drawString(playerScore[0]+"", leaderboardX + 230, leaderboardY + 100);
      }else if(playerScore[0] > 0 && playerScore[0] < 1000){
      g2d.drawString(playerScore[0]+"", leaderboardX + 215, leaderboardY + 100);
      }else if(playerScore[0] >= 1000 && playerScore[0] < 10000){
      g2d.drawString(playerScore[0]+"", leaderboardX + 207, leaderboardY + 100);
      }else if(playerScore[0] >= 10000 && playerScore[0] < 100000){
      g2d.drawString(playerScore[0]+"", leaderboardX + 200, leaderboardY + 100);
      }else if(playerScore[0] >= 100000 && playerScore[0] < 1000000){
      g2d.drawString(playerScore[0]+"", leaderboardX + 192, leaderboardY + 100);
      }
      if(intPlayerNum != 0){
        crosshair0.paintIcon(this, g2d, crosshairX0, crosshairY0);
      }
    }
    if(!playerName[1].equals("Player 1: null") && !playerName[1].equals("Player 2: null") && !playerName[1].equals("Player 3: null") && !playerName[1].equals("Player 4: null")){  //TIM EDIT. The string did not contain a null value. It was actually a string value that said "null"
      g2d.drawString(playerName[1], leaderboardX + 13, leaderboardY + 125);
      if(playerScore[1] == 0){
      g2d.drawString(playerScore[1]+"", leaderboardX + 230, leaderboardY + 125);
      }else if(playerScore[1] > 0 && playerScore[1] < 1000){
      g2d.drawString(playerScore[1]+"", leaderboardX + 215, leaderboardY + 125);
      }else if(playerScore[1] >= 1000 && playerScore[1] < 10000){
      g2d.drawString(playerScore[1]+"", leaderboardX + 207, leaderboardY + 125);
      }else if(playerScore[1] >= 10000 && playerScore[1] < 100000){
      g2d.drawString(playerScore[1]+"", leaderboardX + 200, leaderboardY + 125);
      }else if(playerScore[1] >= 100000 && playerScore[1] < 1000000){
      g2d.drawString(playerScore[1]+"", leaderboardX + 192, leaderboardY + 125);
      }
      if(intPlayerNum != 1){
        crosshair1.paintIcon(this, g2d, crosshairX1, crosshairY1);
      }
    }
    if(!playerName[2].equals("Player 1: null") && !playerName[2].equals("Player 2: null") && !playerName[2].equals("Player 3: null") && !playerName[2].equals("Player 4: null")){  //TIM EDIT. The string did not contain a null value. It was actually a string value that said "null"
      g2d.drawString(playerName[2], leaderboardX + 13, leaderboardY + 150);
      if(playerScore[2] == 0){
      g2d.drawString(playerScore[2]+"", leaderboardX + 230, leaderboardY + 150);
      }else if(playerScore[2] > 0 && playerScore[2] < 1000){
      g2d.drawString(playerScore[2]+"", leaderboardX + 215, leaderboardY + 150);
      }else if(playerScore[2] >= 1000 && playerScore[2] < 10000){
      g2d.drawString(playerScore[2]+"", leaderboardX + 207, leaderboardY + 150);
      }else if(playerScore[2] >= 10000 && playerScore[2] < 100000){
      g2d.drawString(playerScore[2]+"", leaderboardX + 200, leaderboardY + 150);
      }else if(playerScore[2] >= 100000 && playerScore[2] < 1000000){
      g2d.drawString(playerScore[2]+"", leaderboardX + 192, leaderboardY + 150);
      }
      if(intPlayerNum != 2){
        crosshair2.paintIcon(this, g2d, crosshairX2, crosshairY2);
      }
    }
    if(!playerName[3].equals("Player 1: null") && !playerName[3].equals("Player 2: null") && !playerName[3].equals("Player 3: null") && !playerName[3].equals("Player 4: null")){  //TIM EDIT. The string did not contain a null value. It was actually a string value that said "null"
      g2d.drawString(playerName[3], leaderboardX + 13, leaderboardY + 175);
      if(playerScore[3] == 0){
      g2d.drawString(playerScore[3]+"", leaderboardX + 230, leaderboardY + 175);
      }else if(playerScore[3] > 0 && playerScore[3] < 1000){
      g2d.drawString(playerScore[3]+"", leaderboardX + 215, leaderboardY + 175);
      }else if(playerScore[3] >= 1000 && playerScore[3] < 10000){
      g2d.drawString(playerScore[3]+"", leaderboardX + 207, leaderboardY + 175);
      }else if(playerScore[3] >= 10000 && playerScore[3] < 100000){
      g2d.drawString(playerScore[3]+"", leaderboardX + 200, leaderboardY + 175);
      }else if(playerScore[3] >= 100000 && playerScore[3] < 1000000){
      g2d.drawString(playerScore[3]+"", leaderboardX + 192, leaderboardY + 175);
      }
      if(intPlayerNum != 3){
        crosshair3.paintIcon(this, g2d, crosshairX3, crosshairY3);
      }
    }
    personalscore.paintIcon(this, g2d, 540, 5);
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Calibri", Font.PLAIN, 30));
    if(playerScore[intPlayerNum] == 0){
    g2d.drawString(playerScore[intPlayerNum]+"", 635, 39);
    }else if(playerScore[intPlayerNum] > 0 && playerScore[intPlayerNum] < 1000){
    g2d.drawString(playerScore[intPlayerNum]+"", 625, 39);
    }else if(playerScore[intPlayerNum] >= 1000 && playerScore[intPlayerNum] < 10000){
    g2d.drawString(playerScore[intPlayerNum]+"", 615, 39);
    }else if(playerScore[intPlayerNum] >= 10000 && playerScore[intPlayerNum] < 100000){
    g2d.drawString(playerScore[intPlayerNum]+"", 608, 39);
    }else if(playerScore[intPlayerNum] >= 100000 && playerScore[intPlayerNum] < 1000000){
    g2d.drawString(playerScore[intPlayerNum]+"", 602, 39);
    }
    if(intTimeLeft < 10){
    g2d.drawString(intTimeLeft+"", 1250, 33);
    }else if(intTimeLeft >= 10){
    g2d.drawString(intTimeLeft+"", 1234, 33);
    }
    if(intRoundNum == 1){
      round1.paintIcon(this, g2d, 390, 250);
    }else if(intRoundNum == 2){
      round2.paintIcon(this, g2d, 390, 250);
    }else if(intRoundNum == 3){
      round3.paintIcon(this, g2d, 390, 250);
    }
    target1.paintIcon(this, g2d, targetArray[0][0], targetArray[0][1]);
    target2.paintIcon(this, g2d, targetArray[1][0], targetArray[1][1]);
    target3.paintIcon(this, g2d, targetArray[2][0], targetArray[2][1]);
    target4.paintIcon(this, g2d, targetArray[3][0], targetArray[3][1]);
    target5.paintIcon(this, g2d, targetArray[4][0], targetArray[4][1]);
    target6.paintIcon(this, g2d, targetArray[5][0], targetArray[5][1]);
    target7.paintIcon(this, g2d, targetArray[6][0], targetArray[6][1]);
    target8.paintIcon(this, g2d, targetArray[7][0], targetArray[7][1]);
    target9.paintIcon(this, g2d, targetArray[8][0], targetArray[8][1]);
    target10.paintIcon(this, g2d, targetArray[9][0], targetArray[9][1]);
    if(shot == 1){
      shotAnimation.paintIcon(this, g2d, crosshairX-20, crosshairY-20);
    }
    crosshair.paintIcon(this, g2d, crosshairX, crosshairY);
    gun.paintIcon(this, g2d, crosshairX-45, 600);
    if(leaderboardX == 50){
      moving = 1;
    }else if(leaderboardX == 980){
      moving = -1;
    }
    leaderboardX = leaderboardX + moving;
  }
  
  //Constructors 
  public AnimationPanel(){ 
    super();  
  }   
}