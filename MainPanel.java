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



public class MainPanel extends JPanel{ 
  //properties
  ImageIcon background = new ImageIcon(getClass().getResource("pictures/mainBackground.jpg"));
  
  
  //Methods 
  //overides JPanel 
  public void paintComponent(Graphics g){ 
    Graphics2D g2d = (Graphics2D)g; 
    g2d.setFont(new Font("Myriad Hebrew", Font.PLAIN, 25));
    g2d.setColor(Color.BLACK);
    g2d.clearRect(0,0,1280,800);
    background.paintIcon(this, g2d, 0, 0);
  }
  
  //Constructors 
  public MainPanel(){ 
    super();  
  }   
}