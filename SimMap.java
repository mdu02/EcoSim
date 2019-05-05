import java.util.Arrays;
/** simMap.java
  * class to store the map and perform operations
  * @author Michael Du
  * @since 2019-04-17
  * @version 1.00
  */
class SimMap{
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
    private Animal[][] animalGrid;
    private Plant[][] plantGrid;
    private final int BABYHEALTH= 20;
    private int[] plantDeaths = {0};
    private int[] sheepDeaths = {0,0,0};
    private int[] wolfDeaths = {0,0,0};
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
        this.animalGrid = new Animal[this.YSIZE][this.XSIZE];
        this.plantGrid = new Plant[this.YSIZE][this.XSIZE];
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
    /** spawnPlants
      * spawns plants with a frequency given by the object's variables
      */
    public void spawnPlants(){
        for (int i = 0; i<XSIZE; i++){
            for (int j = 0; j<YSIZE; j++){
                if ((Math.random() < this.PLANTSPAWNRATE) && plantGrid[i][j] == null){
                    this.plantGrid[i][j] = new Plant(this.PLANTHEALTH, i, j);
                }
            }
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
    /** returnAnimal
      * @param xCoord the x-coordinate of the point
      * @param yCoord the y-coordinate of the point
      * @return the animal at a given position
      */
    public String returnAnimal(int xCoord, int yCoord){
        if (animalGrid[xCoord][yCoord] instanceof Wolf){
            return "1";
        } else if (animalGrid[xCoord][yCoord] instanceof Sheep){
            return "2";
        }
        return "3";
    }
    /** returnPlant
      * @param xCoord the x-coordinate of the point
      * @param yCoord the y-coordinate of the point
      * @return the plant at a given position
      */
    public String returnPlant(int xCoord, int yCoord){
        if (plantGrid[xCoord][yCoord] instanceof Plant){
            return "1";
        }
        return "2";
    }
    /** returnPlantHealth
      * @return the plant health at a given position
      */
    public String returnPlantHealth(int xCoord, int yCoord){
        if (plantGrid[xCoord][yCoord] instanceof Plant){
            return String.valueOf(plantGrid[xCoord][yCoord].getHealth());
        }
        return "";
    }
    /** returnAnimalHealth
      * @return the animall health at a given position
      */
    public String returnAnimalHealth(int xCoord, int yCoord){
        if (animalGrid[xCoord][yCoord] instanceof Animal){
            return String.valueOf(animalGrid[xCoord][yCoord].getHealth());
        }
        return "";
    }
    /* returnGender
     * @return the gender of a given position
     */
    public String returnGender(int xCoord, int yCoord){
        if (animalGrid[xCoord][yCoord] instanceof Animal){
            if (animalGrid[xCoord][yCoord].getGender()){
                return "M";
            } else {
                return "F";
            }
        }
        return "";
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
        } while (animalGrid[i][j] != null);
        if (isWolf){
            this.animalGrid[i][j] = new Wolf(health, i, j, this.WOLFMAXHEALTH);
        }else{
            this.animalGrid[i][j] = new Sheep(health, i, j, this.SHEEPMAXHEALTH);
        }
    }
    /** removeHealth
      * takes off 1 health from each animal and kills animals
      */
    public void removeHealth(){
        for (int i = 0; i<this.XSIZE; i++){
            for (int j = 0; j<this.YSIZE; j++){
                if (this.animalGrid[i][j] instanceof Animal){
                    this.animalGrid[i][j].addHealth(-1);
                    if (this.animalGrid[i][j].getHealth() <= 0){
                        if (this.animalGrid[i][j] instanceof Sheep){
                            sheepDeaths[0]++;
                        } else {
                            wolfDeaths[0]++;
                        }
                        this.animalGrid[i][j] = null;
                    }
                }
            }
        }
    }
    /** addAge
      * adds 1 health from each animal and kills animals
      */
    public void addAge(){
        for (int i = 0; i<this.XSIZE; i++){
            for (int j = 0; j<this.YSIZE; j++){
                if (this.animalGrid[i][j] instanceof Animal){
                    this.animalGrid[i][j].incrementAge();
                    if (this.animalGrid[i][j].tooOld()){
                        if (this.animalGrid[i][j] instanceof Wolf){
                            wolfDeaths[2]++;
                        } else {
                            sheepDeaths[2]++;
                        }
                        this.animalGrid[i][j] = null;
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
                if (this.animalGrid[i][j] != null && !(this.animalGrid[i][j].cantMove())){
                    //check to make sure index within bounds
                    int i2;
                    int j2;
                    int[]newCoords = this.animalGrid[i][j].makeMove(this.animalGrid, this.plantGrid);
                    i2 = newCoords[0];
                    j2 = newCoords[1];
                    // wolf eats sheep
                    if (this.animalGrid[i][j] instanceof Wolf && this.animalGrid[i2][j2] instanceof Sheep){
                        this.animalGrid[i][j].addHealth(this.animalGrid[i2][j2].getHealth()/3);
                        this.animalGrid[i2][j2] = this.animalGrid[i][j];
                        this.animalGrid[i2][j2].setXPos(i2);
                        this.animalGrid[i2][j2].setYPos(j2);
                        this.animalGrid[i2][j2].setFinishedMove(true);
                        this.animalGrid[i][j] = null;
                        sheepDeaths[1]++;
                    }
                    //sheep eaten by wolf
                    else if (this.animalGrid[i][j] instanceof Sheep && this.animalGrid[i2][j2] instanceof Wolf){
                        this.animalGrid[i2][j2].addHealth(this.animalGrid[i][j].getHealth()/3);
                        this.animalGrid[i2][j2].setFinishedMove(true);
                        this.animalGrid[i][j] = null;
                        sheepDeaths[1]++;
                    }
                    // sheep breed
                    else if (this.animalGrid[i][j] instanceof Sheep && this.animalGrid[i2][j2] instanceof Sheep
                                 && (this.animalGrid[i2][j2].getGender() != this.animalGrid[i][j].getGender())
                            && this.animalGrid[i][j].canBreed() && this.animalGrid[i2][j2].canBreed()){
                        this.animalGrid[i][j].addHealth(-this.BABYHEALTH);
                        this.animalGrid[i][j].setFinishedMove(true);
                        this.animalGrid[i][j].setLastBreed();
                        this.animalGrid[i2][j2].addHealth(-this.BABYHEALTH);
                        this.animalGrid[i2][j2].setFinishedMove(true);
                        this.animalGrid[i2][j2].setLastBreed();
                        spawnAnimal(false, this.BABYHEALTH);      
                    }
                    // sheep eats plants
                    else if (this.animalGrid[i][j] instanceof Sheep && this.plantGrid[i2][j2] instanceof Plant){
                        this.animalGrid[i][j].addHealth(this.plantGrid[i2][j2].getHealth());
                        this.animalGrid[i2][j2] = this.animalGrid[i][j];
                        this.animalGrid[i2][j2].setXPos(i2);
                        this.animalGrid[i2][j2].setYPos(j2);
                        this.animalGrid[i2][j2].setFinishedMove(true);
                        this.animalGrid[i][j] = null;
                        this.plantGrid[i2][j2] = null;
                        plantDeaths[0]++;
                    }
                    //same gendered sheep (sheep are not agressive)
                    else if (this.animalGrid[i][j] instanceof Sheep && this.animalGrid[i2][j2] instanceof Sheep
                                 && (this.animalGrid[i2][j2].getGender() == this.animalGrid[i][j].getGender())){
                        this.animalGrid[i][j].setFinishedMove(true);
                    }
                     //same gendered wolves (FIGHT!) (uh the reason for the somewhat messy code is b/c i wanted to use the
                    //compareof function
                    else if (this.animalGrid[i][j] instanceof Wolf && this.animalGrid[i2][j2] instanceof Wolf
                                 && (this.animalGrid[i2][j2].getGender() == this.animalGrid[i][j].getGender())){
                        Wolf[] tempArray = {(Wolf)this.animalGrid[i][j],(Wolf)this.animalGrid[i2][j2]};
                        Arrays.sort(tempArray);
                        tempArray[0].addHealth(-10);
                        for (int k = 0; k<2; k++){
                            if (tempArray[k].getHealth() <= 0){
                                animalGrid[tempArray[k].getXPos()][tempArray[k].getXPos()] = null;
                                wolfDeaths[1]++;
                            }
                        }
                        tempArray[0].setFinishedMove(true);
                        tempArray[1].setFinishedMove(true);                      
                    }
                    //wolf breed
                    else if (this.animalGrid[i][j] instanceof Wolf && this.animalGrid[i2][j2] instanceof Wolf
                                 && (this.animalGrid[i2][j2].getGender() != this.animalGrid[i][j].getGender())
                            && this.animalGrid[i][j].canBreed() && this.animalGrid[i2][j2].canBreed()){
                        this.animalGrid[i][j].addHealth(-this.BABYHEALTH);
                        this.animalGrid[i][j].setFinishedMove(true);
                        this.animalGrid[i][j].setLastBreed();
                        this.animalGrid[i2][j2].addHealth(-this.BABYHEALTH);
                        this.animalGrid[i2][j2].setFinishedMove(true);
                        this.animalGrid[i2][j2].setLastBreed();
                        spawnAnimal(true, this.BABYHEALTH);
                    }
                    else{
                        this.animalGrid[i2][j2] = this.animalGrid[i][j];
                        this.animalGrid[i2][j2].setXPos(i2);
                        this.animalGrid[i2][j2].setYPos(j2);
                        this.animalGrid[i2][j2].setFinishedMove(true);
                        this.animalGrid[i][j] = null;
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
                if (this.animalGrid[i][j] != null && this.animalGrid[i][j].cantMove() == true && this.animalGrid[i][j] instanceof Animal){
                    this.animalGrid[i][j].setFinishedMove(false);
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
                if (this.plantGrid[i][j] instanceof Plant){
                    numberArray[0]++;
                } 
                if (this.animalGrid[i][j] instanceof Sheep){
                    numberArray[1]++;
                } else if (this.animalGrid[i][j] instanceof Wolf){
                    numberArray[2]++;
                }
            }
        }
        numberArray[3] = this.turnNumber;
        return numberArray;
    }
    /** getDeaths
      * returns array of all death counters
      * @return a 2d array of the death countes
      */
    public int[][] getDeaths(){
        int[][] deathArray = new int[3][];
        deathArray[0] = plantDeaths;
        deathArray[1] = sheepDeaths;
        deathArray[2] = wolfDeaths;
        return deathArray;
    }
    /** gameEnded
      * tells if game is in a ended state
      * @return true if the game has ended
      */
    public boolean gameEnded(){
        int[] mapStats = this.getStats();
        return (mapStats[0] == 0 || mapStats[1] == 0 || mapStats[2] == 0);
    }
}