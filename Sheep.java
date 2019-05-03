/** Sheep.java
  * Class of sheep
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
class Sheep extends Animal{
    private int[] movesX = {1,0,-1,0};
    private int[] movesY = {0,1,0,-1};
    Sheep(int inputHealth, int inputXPos, int inputYPos, int newMaxHealth){
        super(inputHealth, inputXPos, inputYPos);
        this.setMaxHealth(newMaxHealth);
    }
    /** makeMove
      * Greedy algorithm b/c sheep are dumb; will seek out an opposite-gendered animal if they can both mate b/c biology
      * the value that both need to be is given, from the SimMap 
      * else it will look for adjacent food, and in the absence of either will make a random move, including bumping into wolf
      * @param map the array that the sheep does pathfinding on
      * @param healthThreshold the least health that both need to have to mate
      * @return new x-Pos, y-Pos
      */
    //@Override
    public int[] makeMove(Organism[][] map, int healthThreshold){
        int[] returnArray = new int[2];
        int r = (int)(Math.random()*4);//this is necessary, else the sheep will end up piling at the bottom right corner
        //checks for breeding
        for (int i = r; i<r+4; i++){
            int j = i%4;
            if (this.getXPos()+movesX[j]<map.length && this.getYPos()+movesY[j]<map[0].length
                    && this.getXPos()+movesX[j] >= 0 && this.getYPos()+movesY[j] >= 0
                    && (map[this.getXPos()+movesX[j]][this.getYPos()+movesY[j]] instanceof Sheep)){
                if  ((((Sheep)(map[this.getXPos()+movesX[j]][this.getYPos()+movesY[j]])).getGender() != this.getGender())
                         && (this.getHealth() >= healthThreshold) 
                         && (map[this.getXPos()+movesX[j]][this.getYPos()+movesY[j]].getHealth() >= healthThreshold)){
                    returnArray[0] = this.getXPos() +movesX[j];
                    returnArray[1] = this.getYPos() +movesY[j];
                    return returnArray;
                }
            }
        }
        //checks for food
        for (int i = r; i<r+4; i++){
            int j = i%4;
            if (this.getXPos()+movesX[j]<map.length && this.getYPos()+movesY[j]<map[0].length
                    && this.getXPos()+movesX[j] >= 0 && this.getYPos()+movesY[j] >= 0
                    && map[this.getXPos()+movesX[j]][this.getYPos()+movesY[j]] instanceof Plant){
                returnArray[0] = this.getXPos() +movesX[j];
                returnArray[1] = this.getYPos() +movesY[j];
                return returnArray;
            }
        }
        //the "else"
        int i = (int)(Math.random()*4);
        returnArray[0] = this.getXPos() +movesX[i];
        returnArray[1] = this.getYPos() +movesY[i];
        return returnArray;
    }
    /** inGrid
      * gets rid of having to have like six conditions?
      */
}