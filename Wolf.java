/** Wolf.java
  * Class of wolves
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
class Wolf extends Animal implements Comparable<Wolf>{
    private final int[] MOVESX = {1,0,-1,0};
    private final int[] MOVESY = {0,1,0,-1};
    Wolf(int inputHealth, int inputXPos, int inputYPos, int newMaxHealth){
        super(inputHealth, inputXPos, inputYPos);
        this.setMaxHealth(newMaxHealth);
    }
    /** makeMove (copied from sheep)
      * Greedy algorithm b/c wolves are also dumb; will seek out an opposite-gendered animal if they can both mate b/c biology
      * the value that both need to be is given, from the SimMap 
      * else it will look for adjacent sheep, and in the absence of either will make a random move
      * @param animals the array that the wolf does pathfinding on
      * @param plants isnt used at the moment
      * @return new x-Pos, y-Pos
      */
    @Override
    public int[] makeMove(Animal[][] animals, Plant[][] plants){
        int[] returnArray = new int[2];
        int r = (int)(Math.random()*4);
        //checks for breeding
        for (int i = r; i<r+4; i++){
            int j = i%4;
            if (inGrid(this.getXPos()+MOVESX[j], this.getYPos()+MOVESY[j],animals.length)
                    && (animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]] instanceof Wolf)){
                if  (animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]].getGender() != this.getGender()
                         && this.canBreed() && animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]].canBreed()){
                    returnArray[0] = this.getXPos() +MOVESX[j];
                    returnArray[1] = this.getYPos() +MOVESY[j];
                    return returnArray;
                }
            }
        }
        //checks for apple users, i mean sheep
        for (int i = r; i<r+4; i++){
            int j = i%4;
            if (inGrid(this.getXPos()+MOVESX[j], this.getYPos()+MOVESY[j],animals.length)
                    && animals[this.getXPos()+MOVESX[j]][this.getYPos()+MOVESY[j]] instanceof Sheep){
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
     /** compareTo
       * helps implement comparable
       * compares by the health variable
       * @param otherWolf the wolf to be compared to
       * @return if health is greater, equal to, or less than the other
       */
    public int compareTo(Wolf otherWolf){
        if (this.getHealth() > otherWolf.getHealth()){
            return 1;
        } else if (this.getHealth() < otherWolf.getHealth()){
            return -1;
        } else{
            return 0;
        }
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