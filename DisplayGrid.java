/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;


class DisplayGrid { 
    
    private JFrame frame;
    private int maxX,maxY, GridToScreenRatio;
    private SimMap world;
    
    DisplayGrid(SimMap w) { 
        this.world = w;
        
        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / (world.getYSize()+1);  //ratio to fit in screen as square map
        
        System.out.println("Map size: "+world.getYSize()+" by "+world.getXSize() + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
        
        this.frame = new JFrame("Map of World");
        
        GridAreaPanel worldPanel = new GridAreaPanel();
        
        frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }
    
    
    public void refresh() { 
        frame.repaint();
    }
    
    
    
    class GridAreaPanel extends JPanel {
        public void paintComponent(Graphics g) {        
            //super.repaint();
            
            setDoubleBuffered(true); 
            g.setColor(Color.BLACK);
            
            for(int i = 0; i<world.getXSize();i=i+1)
            { 
                for(int j = 0; j<world.getYSize();j=j+1) 
                { 
                    //colours
                    if (world.returnString(i,j).equals("1")){    //This block can be changed to match character-color pairs
                        g.setColor(Color.BLACK);
                    }else if (world.returnString(i,j).equals("2")){
                        g.setColor(Color.WHITE);
                    } else if (world.returnString(i,j).equals("3")){
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                                       
                    //draw
                    g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    //hp
                    g.setColor(Color.RED);
                    g.drawString(world.returnGender(i,j) + world.returnHealth(i,j), j*GridToScreenRatio, (1+i)*GridToScreenRatio);
                    int[] stats = world.getStats();
                    g.drawString("Turns: " + stats[3], (world.getXSize()+5)*GridToScreenRatio, 150);
                    g.drawString("Total Plants: " + stats[0], (world.getXSize()+5)*GridToScreenRatio, 200);
                    g.drawString("Total Sheep: " + stats[1], (world.getXSize()+5)*GridToScreenRatio, 250);
                    g.drawString("Total Wolves: " + stats[2], (world.getXSize()+5)*GridToScreenRatio, 300);
                }
            }
        }
    }//end of GridAreaPanel
    
} //end of DisplayGrid

