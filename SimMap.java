import java.util.Arrays;
/** simMap.java
  * class to store the map and perform operations
  * @author Michael Du
  * @since 2019-04-17
  * @version 1.00
  */
class SimMap{
    private final int babyHealth = 20;
    private final int XSIZE;
    private final int YSIZE;
    private final double PLANTSPAWNRATE;
    private final int PLANTHEALTH;
    private final int NUMWOLVES;
    private final int WOLFHEALTH;
    private final int WOLFMAXHEALTH;
    private final int NUMSHEEP;
    private final int SHEEPHEALTH;
    private final int SHEEPMAXHEALTH;
    private int turnNumber;
    private Organism[][] mapGrid;
    /** Constructor
      * @param inputXSize x-size of the map
      * @param inputYSize y-size of the map
      * @param inputPlantSpawnRate the rate at which plants spawn
      * @param inputPlantHealth the health of each plant
      * @param inputNumWolves number of wolves
      * @param inputWolfHealth the health of each wolf
      * @param inputNumSheep the number of sheep
      * @param inputSheepHealth health of each sheep
      */
    SimMap(int inputXSize, int inputYSize, double inputPlantSpawnRate, int inputPlantHealth, int inputNumWolves,
           int inputWolfHealth, int inputWolfMaxHealth, int inputNumSheep, int inputSheepHealth, int inputSheepMaxHealth){
        this.XSIZE = inputXSize;
        this.YSIZE = inputYSize;
        this.PLANTSPAWNRATE = inputPlantSpawnRate;
        this.PLANTHEALTH = inputPlantHealth;
        this.NUMWOLVES = inputNumWolves;
        this.WOLFHEALTH = inputWolfHealth;
        this.WOLFMAXHEALTH = inputWolfMaxHealth;
        this.NUMSHEEP = inputNumSheep;
        this.SHEEPHEALTH = inputSheepHealth;
        this.SHEEPMAXHEALTH = inputSheepMaxHealth;
        this.mapGrid = new Organism[this.YSIZE][this.XSIZE];
    }
    /**getXSize
      * @return the x-size
      */
    public int getXSize(){
        return this.XSIZE;
    }
      /**getYSize
      * @return the y-size
      */
    public int getYSize(){
        return this.YSIZE;
    }
    /** returnString
      * @return the string at a given position
      */
    public String returnString(int xCoord, int yCoord){
        if (mapGrid[xCoord][yCoord] instanceof Wolf){
            return "1";
        } else if (mapGrid[xCoord][yCoord] instanceof Sheep){
            return "2";
        } else if (mapGrid[xCoord][yCoord] instanceof Plant){
            return "3";
        }
        return "4";
    }
    /** returnHealth
      * @return the health at a given position
      */
    public String returnHealth(int xCoord, int yCoord){
        if (mapGrid[xCoord][yCoord] instanceof Organism){
            return String.valueOf(mapGrid[xCoord][yCoord].getHealth());
        }
        return "";
    }
    /* returnGender
     * @return the gender of a given position
     */
    public String returnGender(int xCoord, int yCoord){
        if (mapGrid[xCoord][yCoord] instanceof Animal){
            if (((Animal)mapGrid[xCoord][yCoord]).getGender()){
                return "M";
            } else {
                return "F";
            }
        }
        return "";
    }
    /** spawnPlants
      * spawns plants with a frequency given by the object's variables
      */
    public void spawnPlants(){
        for (int i = 0; i<XSIZE; i++){
            for (int j = 0; j<YSIZE; j++){
                if ((Math.random() < this.PLANTSPAWNRATE) && mapGrid[i][j] == null){
                    mapGrid[i][j] = new Plant(this.PLANTHEALTH, i, j);
                }
            }
        }
    }
    
    /**spawnAnimals
      * spawns an animal at an unoccupied place
      * @param isWolf determines if wolf or sheep spawned
      */
    private void spawnAnimal(boolean isWolf, int health){
        int i,j;
        do{
            i = (int)(Math.random()*this.XSIZE);
            j = (int)(Math.random()*this.YSIZE);
        }
        while (mapGrid[i][j] != null);
        if (isWolf){
            this.mapGrid[i][j] = new Wolf(health, i, j, this.WOLFMAXHEALTH);
        }else{
            this.mapGrid[i][j] = new Sheep(health, i, j, this.SHEEPMAXHEALTH);
        }
    }
    /**initalizeAnimals
      * starts the map off with a certain amount of animals
      */
    public void initializeAnimals(){
        for (int i = 0; i<this.NUMWOLVES; i++){
            spawnAnimal(true, this.WOLFHEALTH);
        }
        for (int j = 0; j< this.NUMSHEEP; j++){
            spawnAnimal(false, this.SHEEPHEALTH);
        }
    }
    /** removeHealth
      * takes off 1 health from each animal and kills animals
      */
    public void removeHealth(){
        for (int i = 0; i<this.XSIZE; i++){
            for (int j = 0; j<this.YSIZE; j++){
                if (this.mapGrid[i][j] instanceof Animal){
                    ((Animal)(this.mapGrid[i][j])).addHealth(-1);
                    if (this.mapGrid[i][j].getHealth() <= 0){
                        this.mapGrid[i][j] = null;
                    }
                }
            }
        }
    }
    /** moveAnimals
      *  moves all animals from their positions, checks collisions etc.
      */
    public void moveAnimals(){
        for (int i = 0; i<this.XSIZE; i++){
            for (int j = 0; j<this.YSIZE; j++){
                if (this.mapGrid[i][j] != null && this.mapGrid[i][j].cantMove() == false){
                    //check to make sure index within bounds
                    int i2;
                    int j2;
                    do {
                        int[]newCoords = ((Animal)this.mapGrid[i][j]).makeMove(this.mapGrid, 2*this.babyHealth);
                        i2 = newCoords[0];
                        j2 = newCoords[1];
                    } while (i2 >= this.XSIZE || j2>= this.YSIZE || i2 < 0 || j2 < 0);
                    // wolf eats sheep
                    if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i2][j2] instanceof Sheep){
                        ((Animal)(this.mapGrid[i][j])).addHealth(this.mapGrid[i2][j2].getHealth()/5);
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    //sheep eaten by wolf
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i2][j2] instanceof Wolf){
                        ((Animal)(this.mapGrid[i2][j2])).addHealth(this.mapGrid[i][j].getHealth()/5);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    // wolf tramples grass
                    else if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i2][j2] instanceof Plant){
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    // sheep eats plants
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i2][j2] instanceof Plant){
                        ((Animal)(this.mapGrid[i][j])).addHealth(this.mapGrid[i2][j2].getHealth());
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    // sheep breed
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i][j].getHealth() >= (this.babyHealth*2)
                                 && this.mapGrid[i2][j2] instanceof Sheep && this.mapGrid[i2][j2].getHealth() >= (this.babyHealth*2)
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() != ((Animal)this.mapGrid[i][j]).getGender()){
                        ((Animal)(this.mapGrid[i][j])).addHealth(-this.babyHealth);
                        ((Animal)(this.mapGrid[i][j])).toggleFinishedMove();
                        ((Animal)(this.mapGrid[i2][j2])).addHealth(-this.babyHealth);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        spawnAnimal(false, this.babyHealth);      
                    }
                    //same gendered sheep (sheep are not agressive)
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i2][j2] instanceof Sheep
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() == ((Animal)this.mapGrid[i][j]).getGender()){
                        ((Animal)(this.mapGrid[i][j])).toggleFinishedMove();
                    }
                     //same gendered wolves (FIGHT!) //FINISH THIS
                    else if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i2][j2] instanceof Wolf
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() == ((Animal)this.mapGrid[i][j]).getGender()){
                        Wolf[] tempArray = {(Wolf)this.mapGrid[i][j],(Wolf)this.mapGrid[i2][j2]};
                        Arrays.sort(tempArray);
                        tempArray[0].addHealth(-10);
                        tempArray[0].toggleFinishedMove();
                        tempArray[1].toggleFinishedMove();                      
                    }
                    //wolf breed
                    else if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i][j].getHealth() >= (this.babyHealth*2)
                                 && this.mapGrid[i2][j2] instanceof Wolf && this.mapGrid[i2][j2].getHealth() >= (this.babyHealth*2)
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() != ((Animal)this.mapGrid[i][j]).getGender()){
                        ((Animal)(this.mapGrid[i][j])).addHealth(-this.babyHealth);
                        ((Animal)(this.mapGrid[i][j])).toggleFinishedMove();
                        ((Animal)(this.mapGrid[i2][j2])).addHealth(-this.babyHealth);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        spawnAnimal(true, this.babyHealth);
                    }
                    else{
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        ((Animal)(this.mapGrid[i2][j2])).toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                }
            }
        }
        this.turnNumber ++;
    }
    /** setFalse
      * resets all animals to false for next turn around
      */
    public void setFalse(){
        for (int i = 0; i<this.XSIZE; i++){
            for (int j = 0; j<this.YSIZE; j++){
                if (this.mapGrid[i][j] != null && this.mapGrid[i][j].cantMove() == true && this.mapGrid[i][j] instanceof Animal){
                    ((Animal)(this.mapGrid[i][j])).toggleFinishedMove();
                }
            }
        }
    }
    /** getStats
      * gets number of each type
      * @return an array of number of plants, sheep, wolves, turn number
      */
    public int[] getStats(){
        int[] numberArray = {0,0,0,0};
        for (int i = 0; i<this.XSIZE; i++){
            for (int j = 0; j<this.YSIZE; j++){
                if (this.mapGrid[i][j] instanceof Plant){
                    numberArray[0]++;
                } else if (this.mapGrid[i][j] instanceof Sheep){
                    numberArray[1]++;
                } else if (this.mapGrid[i][j] instanceof Wolf){
                    numberArray[2]++;
                }
            }
        }
        numberArray[3] = this.turnNumber;
        return numberArray;
    }
    /** gameEnded
      * tells if game is in a ended state
      * @return true if the game has ended
      */
    public boolean gameEnded(){
        int[] mapStats = this.getStats();
        if (mapStats[0] == 0 || mapStats[1] == 0 || mapStats[2] == 0){
            return true;
        }
        return false;
    }
}