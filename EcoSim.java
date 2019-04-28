import java.util.Scanner;
//ToDo: AI? Counter, other inputs, debug code?
/**EcoSim.java
  * Eco Simulation main class
  * @author Michael Du
  * @since 2019-04-17
  * @version 1.00
  */
class EcoSim{
    public static void main(String[] args){
        /*
         Scanner in = new Scanner(System.in); 
         System.out.println("How big should it be");
         int gridSize = in.nextInt();
         System.out.println("Plant Spawn Rate?");
         double plantSpawnRate = in.nextDouble();
         System.out.println("Plant Health?");
         int plantHealth = in.nextInt();
         */
        int gridSize = 25;
        double plantSpawnRate = 0.08;
        int plantHealth = 5;
        int numWolves =10;
        int wolfHealth = 40;
        int numSheep = 60;
        int sheepHealth = 25;
        SimMap organismMap = new SimMap(gridSize, gridSize, plantSpawnRate, plantHealth,  numWolves,
                                        wolfHealth, numSheep, sheepHealth);
        organismMap.initializeAnimals();
        String[][] map = new String[gridSize][gridSize];
        moveItemsOnGrid(map, organismMap);
        //Set up Grid Panel
        DisplayGrid grid = new DisplayGrid(map);
        int turnNumber = 0;
        int[] typeNumbers;
        while(true) {
            //Things to do before every turn
            organismMap.spawnPlants();
            organismMap.moveAnimals();
            organismMap.setFalse();
            organismMap.removeHealth();
            //refresh things
            moveItemsOnGrid(map, organismMap);
            //debug
            //DisplayGridOnConsole(map);
            turnNumber++;
            System.out.println("Turn Number" + turnNumber);
            typeNumbers = organismMap.getNumbers();
            System.out.println("Current Plants" + typeNumbers[0]);
            System.out.println("Current Sheep" + typeNumbers[1]);
            System.out.println("Current Wolves" + typeNumbers[2]);
            //Small delay
            try{ Thread.sleep(200); }catch(Exception e) {};
            grid.refresh();
        }
    }
    public static void moveItemsOnGrid(String[][] map, SimMap simMap) { 
        for(int i = 0; i<map[0].length;i++){
            for(int j = 0; j<map.length;j++){ 
                map[i][j]= simMap.returnString(i,j);
            }
        }
    }
    
    //DEBUG CODE REMOVE LATER
    public static void DisplayGridOnConsole(String[][] map) { 
        for(int i = 0; i<map.length;i++){        
            for(int j = 0; j<map[0].length;j++) 
                System.out.print(map[i][j]+" ");
            System.out.println("");
        }
    }
}
