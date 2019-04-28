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
    Animal(int inputHealth, int inputXPos, int inputYPos){
        super(inputHealth, inputXPos, inputYPos);
        if (Math.random() < 0.5){
            this.isMale = true;
        }
    }
    /** makeMove
      * @return the new coords
      */
    public int[] makeMove(){
        int i = (int)(Math.random()*4);
        int[] returnArray = {this.getXPos() +movesX[i], this.getYPos() +movesY[i]};
        this.setXPos(this.getXPos() +movesX[i]);
        this.setYPos(this.getYPos() +movesY[i]);
        return returnArray;
    }
    /** getGender
      * @return the gender of the animal
      */
    public boolean getGender(){
        return this.isMale;
    }
}
