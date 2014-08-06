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



public class HelpPanel extends JPanel{ 
  //properties
  ImageIcon help1 = new ImageIcon(getClass().getResource("pictures/help1.jpg"));
  ImageIcon help2 = new ImageIcon(getClass().getResource("pictures/help2.jpg"));
  int change = 0;
  
  
  //Methods 
  //overides JPanel 
  public void paintComponent(Graphics g){ 
    Graphics2D g2d = (Graphics2D)g; 
    g2d.clearRect(0,0,1280,800);
    if(change == 0){
    help1.paintIcon(this, g2d, 0, 0);
    }else if(change == 1){
    help2.paintIcon(this, g2d, 0, 0);
    }
  }
  
  //Constructors 
  public HelpPanel(){ 
    super();  
  }   
}