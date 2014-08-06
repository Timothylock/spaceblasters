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



public class LobbyPanel extends JPanel{ 
  //properties
  ImageIcon background = new ImageIcon(getClass().getResource("pictures/lobbyBackground.jpg"));
  ImageIcon startTimer = new ImageIcon(getClass().getResource("pictures/startTimer.png"));
  ImageIcon messageboard = new ImageIcon(getClass().getResource("pictures/messageboard.png"));
  String strWaitingLobbyTime;
  int messageX = 50;
  int messageY = 100;
  int moving = 0;
  
  
  //Methods 
  //overides JPanel 
  public void paintComponent(Graphics g){ 
    Graphics2D g2d = (Graphics2D)g; 
    g2d.setFont(new Font("Calibri", Font.PLAIN, 25));
    g2d.setColor(Color.WHITE);
    g2d.clearRect(0,0,1280,800);
    background.paintIcon(this, g2d, 0, 0);
    startTimer.paintIcon(this, g2d, 10, 10);
    if(strWaitingLobbyTime != null){
    g2d.drawString(strWaitingLobbyTime, 175, 44);
    }
    messageboard.paintIcon(this, g2d, messageX, messageY);
    if(messageX == 50){
      moving = 1;
    }else if(messageX == 980){
      moving = -1;
    }
    messageX = messageX + moving;
  }
  
  //Constructors 
  public LobbyPanel(){ 
    super();  
  }   
}