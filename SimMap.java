/** simMap.java
  * class to store the map and perform operations
  * @author Michael Du
  * @since 2019-04-17
  * @version 1.00
  */
class SimMap{
    private final int babyHealth = 20;
    private final int xSize;
    private final int ySize;
    private final double plantSpawnRate;
    private final int plantHealth;
    private final int numWolves;
    private final int wolfHealth;
    private final int numSheep;
    private final int sheepHealth;
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
           int inputWolfHealth, int inputNumSheep, int inputSheepHealth){
        this.xSize = inputXSize;
        this.ySize = inputYSize;
        this.plantSpawnRate = inputPlantSpawnRate;
        this.plantHealth = inputPlantHealth;
        this.numWolves = inputNumWolves;
        this.wolfHealth = inputWolfHealth;
        this.numSheep = inputNumSheep;
        this.sheepHealth = inputSheepHealth;
        this.mapGrid = new Organism[this.ySize][this.xSize];
    }
    /**getXSize
      * @return the x-size
      */
    public int getXSize(){
        return this.xSize;
    }
      /**getYSize
      * @return the y-size
      */
    public int getYSize(){
        return this.ySize;
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
    public int returnHealth(int xCoord, int yCoord){
        if (mapGrid[xCoord][yCoord] instanceof Organism){
            return mapGrid[xCoord][yCoord].getHealth();
        }
        return 0;
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
        for (int i = 0; i<xSize; i++){
            for (int j = 0; j<ySize; j++){
                if ((Math.random() < this.plantSpawnRate) && mapGrid[i][j] == null){
                    mapGrid[i][j] = new Plant(this.plantHealth, i, j);
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
            i = (int)(Math.random()*this.xSize);
            j = (int)(Math.random()*this.ySize);
        }
        while (mapGrid[i][j] != null);
        if (isWolf){
            this.mapGrid[i][j] = new Wolf(health, i,j);
        }else{
            this.mapGrid[i][j] = new Sheep(health, i,j);
        }
    }
    /**initalizeAnimals
      * starts the map off with a certain amount of animals
      */
    public void initializeAnimals(){
        for (int i = 0; i<this.numWolves; i++){
            spawnAnimal(true, this.wolfHealth);
        }
        for (int j = 0; j< this.numSheep; j++){
            spawnAnimal(false, this.sheepHealth);
        }
    }
    /** removeHealth
      * takes off 1 health from each animal and kills animals
      */
    public void removeHealth(){
        for (int i = 0; i<this.xSize; i++){
            for (int j = 0; j<this.ySize; j++){
                if (this.mapGrid[i][j] instanceof Animal){
                    this.mapGrid[i][j].setHealth(this.mapGrid[i][j].getHealth()-1);
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
        for (int i = 0; i<this.xSize; i++){
            for (int j = 0; j<this.ySize; j++){
                if (this.mapGrid[i][j] != null && this.mapGrid[i][j] instanceof Animal 
                        && this.mapGrid[i][j].getFinishedMove() == false){
                    //check to make sure index within bounds
                    int i2;
                    int j2;
                    do {
                        int[]newCoords = ((Animal)this.mapGrid[i][j]).makeMove(this.mapGrid, 2*this.babyHealth);
                        i2 = newCoords[0];
                        j2 = newCoords[1];
                    } while (i2 >= this.xSize || j2>= this.ySize || i2 < 0 || j2 < 0);
                    // wolf eats sheep
                    if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i2][j2] instanceof Sheep){
                        this.mapGrid[i][j].setHealth(this.mapGrid[i][j].getHealth() + this.mapGrid[i2][j2].getHealth());
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        this.mapGrid[i2][j2].toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    //sheep eaten by wolf
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i2][j2] instanceof Wolf){
                        this.mapGrid[i2][j2].setHealth(this.mapGrid[i][j].getHealth() + this.mapGrid[i2][j2].getHealth());
                        this.mapGrid[i2][j2].toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    // wolf tramples grass
                    else if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i2][j2] instanceof Plant){
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        this.mapGrid[i2][j2].toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    // sheep eats plants
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i2][j2] instanceof Plant){
                        this.mapGrid[i][j].setHealth(this.mapGrid[i][j].getHealth() + this.mapGrid[i2][j2].getHealth());
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        this.mapGrid[i2][j2].toggleFinishedMove();
                        this.mapGrid[i][j] = null;
                    }
                    // sheep breed
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i][j].getHealth() >= (this.babyHealth*2)
                                 && this.mapGrid[i2][j2] instanceof Sheep && this.mapGrid[i2][j2].getHealth() >= (this.babyHealth*2)
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() != ((Animal)this.mapGrid[i][j]).getGender()){
                        this.mapGrid[i][j].setHealth(this.mapGrid[i][j].getHealth() -this.babyHealth);
                        this.mapGrid[i][j].toggleFinishedMove();
                        this.mapGrid[i2][j2].setHealth(this.mapGrid[i2][j2].getHealth() -this.babyHealth);
                        this.mapGrid[i2][j2].toggleFinishedMove();
                        spawnAnimal(false, this.babyHealth);      
                    }
                    //same gendered sheep (sheep are not agressive)
                    else if (this.mapGrid[i][j] instanceof Sheep && this.mapGrid[i2][j2] instanceof Sheep
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() == ((Animal)this.mapGrid[i][j]).getGender()){
                        this.mapGrid[i][j].toggleFinishedMove();
                    }
                     //same gendered wolves (FIGHT!)
                    else if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i2][j2] instanceof Wolf
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() == ((Animal)this.mapGrid[i][j]).getGender()){
                        
                        this.mapGrid[i][j].toggleFinishedMove();
                        
                    }
                    //wolf breed
                    else if (this.mapGrid[i][j] instanceof Wolf && this.mapGrid[i][j].getHealth() >= (this.babyHealth*2)
                                 && this.mapGrid[i2][j2] instanceof Wolf && this.mapGrid[i2][j2].getHealth() >= (this.babyHealth*2)
                                 && ((Animal)this.mapGrid[i2][j2]).getGender() != ((Animal)this.mapGrid[i][j]).getGender()){
                        this.mapGrid[i][j].setHealth(this.mapGrid[i][j].getHealth() -this.babyHealth);
                        this.mapGrid[i][j].toggleFinishedMove();
                        this.mapGrid[i2][j2].setHealth(this.mapGrid[i2][j2].getHealth() -this.babyHealth);
                        this.mapGrid[i2][j2].toggleFinishedMove();
                        spawnAnimal(true, this.babyHealth);
                    }
                    else{
                        this.mapGrid[i2][j2] = this.mapGrid[i][j];
                        this.mapGrid[i2][j2].setXPos(i2);
                        this.mapGrid[i2][j2].setYPos(j2);
                        this.mapGrid[i2][j2].toggleFinishedMove();
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
        for (int i = 0; i<this.xSize; i++){
            for (int j = 0; j<this.ySize; j++){
                if (this.mapGrid[i][j] != null && this.mapGrid[i][j].getFinishedMove() == true){
                    this.mapGrid[i][j].toggleFinishedMove();
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
        for (int i = 0; i<this.xSize; i++){
            for (int j = 0; j<this.ySize; j++){
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
}