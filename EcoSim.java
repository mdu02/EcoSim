import java.util.Scanner;
import java.io.IOException;
/**EcoSim.java
  * Eco Simulation main class
  * @author Michael Du
  * @since 2019-04-17
  * @version 1.00
  */

/*Things that this game implements
 * self-made graphics of questionable quality
 * A separate grid for animals and plants
 * Age has been set up, but this is really hard to balance right. go to the animal class and uncomment the line to try it
 * (as of now, animals will die if their age reaches Integer.MAX_VALUE
 * Sheep and Wolves both have a greedy algorithm which looks to 1. breed 2. eat in their immediate vicinity
 * Both animals have a gender, which is randomly set, and determines their behaviour adjacent to the same species
 * counts deaths of each species, including plants
 */
class EcoSim{
    public static void main(String[] args) throws IOException{
        int turntime;
        int gridSize;
        double plantSpawnRate;
        int plantHealth;
        int numWolves;
        int wolfHealth;
        int wolfMaxHealth;
        int numSheep;
        int sheepHealth;
        int sheepMaxHealth;
        Scanner in = new Scanner(System.in);
        System.out.println("Use Own Settings? Enter Y/N: ");
        String settings = in.next();
        
        if (settings == "Y"){
            System.out.println("How many ms should each turn be?");
            turntime = in.nextInt();
            System.out.println("How big should it be (ideally less than 40)");
            gridSize = in.nextInt();
            System.out.println("Plant Spawn Rate (between 0 and 1)?");
            plantSpawnRate = in.nextDouble();
            System.out.println("Plant Health?");
            plantHealth = in.nextInt();
            System.out.println("Number of Wolves?");
            numWolves = in.nextInt();
            System.out.println("Wolf Health?");
            wolfHealth = in.nextInt();
            System.out.println("Wolf Health Cap?");
            wolfMaxHealth = in.nextInt();
            System.out.println("Number of Sheep?");
            numSheep = in.nextInt();
            System.out.println("Sheep Health?");
            sheepHealth = in.nextInt();
            System.out.println("Sheep Health Cap?");
            sheepMaxHealth = in.nextInt();
        } else {
            turntime = 500;
            gridSize = 40;
            plantSpawnRate = 0.08;
            plantHealth = 20;
            numWolves = 8;
            wolfHealth = 50;
            wolfMaxHealth = 200;
            numSheep = 300;
            sheepHealth = 25;
            sheepMaxHealth = 100;
//            turntime = 3000;
//            gridSize = 5;
//            plantSpawnRate = 0.25;
//            plantHealth = 20;
//            numWolves = 1;
//            wolfHealth = 50;
//            wolfMaxHealth = 200;
//            numSheep = 8;
//            sheepHealth = 25;
//            sheepMaxHealth = 100;
        }
        SimMap organismMap = new SimMap(gridSize, gridSize, plantSpawnRate, plantHealth, numWolves,
                                        wolfHealth, wolfMaxHealth, numSheep, sheepHealth, sheepMaxHealth);
        organismMap.initializeAnimals();
        //Set up Grid Panel
        DisplayGrid grid = new DisplayGrid(organismMap);
        int turnNumber = 0;
        int[] typeStats;
        do{
            //Things to do before every turn
            organismMap.spawnPlants();
            organismMap.moveAnimals();
            organismMap.setFalse();
            organismMap.removeHealth();
            organismMap.addAge();
//         console output
//            turnNumber++;
//            System.out.println("Turn Number: " + turnNumber);
//            typeStats = organismMap.getStats();
//            System.out.println("Current Plants: " + typeStats[0]);
//            System.out.println("Current Sheep: " + typeStats[1]);
//            System.out.println("Current Wolves: " + typeStats[2]);
            //Small delay
            try{ Thread.sleep(turntime); }catch(Exception e) {};
            grid.refresh();
        } while (!organismMap.gameEnded());
    }
}
