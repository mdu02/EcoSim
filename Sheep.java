/** Sheep.java
  * Class of sheep
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
class Sheep extends Animal{
    private final int[] MOVESX = {1,0,-1,0};
    private final int[] MOVESY = {0,1,0,-1};
    Sheep(int inputHealth, int inputXPos, int inputYPos, int newMaxHealth){
        super(inputHealth, inputXPos, inputYPos);
        this.setMaxHealth(newMaxHealth);
    }
    /** makeMove
      * Greedy algorithm b/c sheep are dumb; will seek out an opposite-gendered animal if they can both mate b/c biology
      * the value that both need to be is given, from the SimMap 
      * else it will look for adjacent food, and in the absence of either will make a random move, including bumping into wolf
      * @param animals the array that the sheep does pathfinding on
      * @param plants the array that displays the sheep food
      * @return new x-Pos, y-Pos
      */
    @Override
    public int[] makeMove(Animal[][] animals, Plant[][] plants){
        int[] returnArray = new int[2];
        int r = (int)(Math.random()*4);//this is necessary, else the sheep will end up piling at the bottom right corner
        //checks for breeding
        for (int i = r; i<r+4; i++){
            int j = i%4;
            if (inGrid(this.getXPos()+MOVESX[j], this.getYPos()+MOVESY[j],animals.length)
                    && (animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]] instanceof Sheep)){
                if  (animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]].getGender() != this.getGender()
                         && this.canBreed() && animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]].canBreed()){
                    returnArray[0] = this.getXPos() +MOVESX[j];
                    returnArray[1] = this.getYPos() +MOVESY[j];
                    return returnArray;
                }
            }
        }
        //checks for food
        for (int i = r; i<r+4; i++){
            int j = i%4;
            if (inGrid(this.getXPos()+MOVESX[j], this.getYPos()+MOVESY[j],animals.length)
                    && plants[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]] instanceof Plant){
                returnArray[0] = this.getXPos() +MOVESX[j];
                returnArray[1] = this.getYPos() +MOVESY[j];
                return returnArray;
            }
        }
        //the random
        do{
            int i = (int)(Math.random()*4);
            returnArray[0] = this.getXPos() +MOVESX[i];
            returnArray[1] = this.getYPos() +MOVESY[i];
        } while (!inGrid(returnArray[0],returnArray[1], animals.length));
        return returnArray;
    }
    
    /** inGrid
      * gets rid of having to have like six conditions?
      * @param xPos
      * @param yPos
      * @param gridSize
      */
    private boolean inGrid(int inputXPos, int inputYPos, int gridSize){
        if (inputXPos<gridSize && inputYPos<gridSize && inputXPos >= 0 && inputYPos >= 0){
            return true;
        }
        return false;
    }
}