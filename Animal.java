/** Animal.java
  * Class of all animals (sheep, wolves)
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
abstract class Animal extends Organism{
    private int[] movesX = {1,0,-1,0};
    private int[] movesY = {0,1,0,-1};
    private boolean isMale;
    private boolean finishedMove = false;
    Animal(int inputHealth, int inputXPos, int inputYPos){
        super(inputHealth, inputXPos, inputYPos);
        if (Math.random() < 0.5){
            this.isMale = true;
        }
    }
    /** makeMove
      * @return the new coords
      */
    public int[] makeMove(Organism[][]map, int healthThreshold){
        int i = (int)(Math.random()*4);
        int[] returnArray = {this.getXPos() +movesX[i], this.getYPos() +movesY[i]};
        return returnArray;
    }
    /** getGender
      * @return the gender of the animal
      */
    public boolean getGender(){
        return this.isMale;
    }
     /** getFinishedMove
      * @return if moves are finished yet
      */
    public boolean getFinishedMove(){
        return this.finishedMove;
    }   
    /** toggleFinishedMove
      * toggles finishedMove variable
      */
    public void toggleFinishedMove(){
        finishedMove = !finishedMove;
    }
}
