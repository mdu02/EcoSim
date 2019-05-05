/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat (and also michael du)
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


class DisplayGrid{ 
    private JFrame frame;
    private int maxX,maxY, GridToScreenRatio;
    private SimMap world;
    final BufferedImage grassImage;
    final BufferedImage sheepImage;
    final BufferedImage wolfImage;
    DisplayGrid(SimMap w) throws IOException{ 
        this.world = w;
        grassImage = ImageIO.read(new File("grass.png"));
        sheepImage = ImageIO.read(new File("sheep.png"));
        wolfImage = ImageIO.read(new File("wolf.png"));
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
        public void paintComponent(Graphics g){      
            //super.repaint();
            setDoubleBuffered(true); 
            g.setColor(Color.BLACK);
            for(int i = 0; i<world.getXSize();i=i+1)
            { 
                for(int j = 0; j<world.getYSize();j=j+1) {
                    if (world.returnPlant(i,j).equals("1")){
                        g.drawImage(grassImage, j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio,null,null);
                        //hp
                        g.setColor(Color.RED);
                        g.drawString(world.returnPlantHealth(i,j), j*GridToScreenRatio, (1+i)*GridToScreenRatio);
                    } else {
                        g.setColor(Color.GRAY);
                        g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    }
                     //colours
                    if (world.returnAnimal(i,j).equals("1")){    //This block can be changed to match character-color pairs
                        g.drawImage(wolfImage, (int)((j+0.2)*GridToScreenRatio), i*GridToScreenRatio, (int)(0.8*GridToScreenRatio), (int)(0.8*GridToScreenRatio), null,null);
                        g.setColor(Color.BLACK);
                        g.drawRect((int)((j+0.2)*GridToScreenRatio), i*GridToScreenRatio, (int)(0.8*GridToScreenRatio), (int)(0.8*GridToScreenRatio));
                        //hp
                        g.setColor(Color.RED);
                        g.drawString(world.returnGender(i,j) + world.returnAnimalHealth(i,j), (int)((j+0.2)*GridToScreenRatio), (int)((i+0.8)*GridToScreenRatio));
                    }else if (world.returnAnimal(i,j).equals("2")){
                        g.drawImage(sheepImage, (int)((j+0.2)*GridToScreenRatio), i*GridToScreenRatio, (int)(0.8*GridToScreenRatio), (int)(0.8*GridToScreenRatio), null,null);
                        g.setColor(Color.BLACK);
                        g.drawRect((int)((j+0.2)*GridToScreenRatio), i*GridToScreenRatio, (int)(0.8*GridToScreenRatio), (int)(0.8*GridToScreenRatio));
                        //hp
                        g.setColor(Color.RED);
                        g.drawString(world.returnGender(i,j) + world.returnAnimalHealth(i,j), (int)((j+0.2)*GridToScreenRatio), (int)((i+0.8)*GridToScreenRatio));
                    }
                    //draw
                        g.setColor(Color.BLACK);
                        g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                }
            }
            int[] stats = world.getStats();
            int[][] deathStats = world.getDeaths();
            g.setColor(Color.BLACK);
            g.drawString("Turns: " + stats[3], (world.getXSize()+0)*GridToScreenRatio, 150);
            g.drawString("Total Plants: " + stats[0], (world.getXSize()+0)*GridToScreenRatio, 200);
            g.drawString("Total Sheep: " + stats[1], (world.getXSize()+0)*GridToScreenRatio, 250);
            g.drawString("Total Wolves: " + stats[2], (world.getXSize()+0)*GridToScreenRatio, 300);
            g.drawString("Plants died to sheep: " + deathStats[0][0], (world.getXSize()+0)*GridToScreenRatio, 350);
            g.drawString("Sheep died to starvation: " + deathStats[1][0], (world.getXSize()+0)*GridToScreenRatio, 400);
            g.drawString("Sheep died to wolves: " + deathStats[1][1], (world.getXSize()+0)*GridToScreenRatio, 450);
            g.drawString("Sheep died to old age: " + deathStats[1][2], (world.getXSize()+0)*GridToScreenRatio, 500);
            g.drawString("Wolves died to starvation: " + deathStats[2][0], (world.getXSize()+0)*GridToScreenRatio, 550);
            g.drawString("Wolves died to fights: " + deathStats[2][1], (world.getXSize()+0)*GridToScreenRatio, 600);
            g.drawString("Wolves died to old age: " + deathStats[2][2], (world.getXSize()+0)*GridToScreenRatio, 650);
        }
    }//end of GridAreaPanel
    
} //end of DisplayGrid

