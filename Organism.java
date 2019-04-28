/** Organism.java
  * Class of all organisms (plants, sheep, wolves)
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
abstract class Organism{
    private int health;
    private int xPos;
    private int yPos;
    private boolean finishedMove = false;
    /** Constructor
      * @param inputHealth the health given
      * @param inputXPos the x position given
      * @param inputYPos the y position given
      */
    Organism(int inputHealth, int inputXPos, int inputYPos){
        this.health = inputHealth;
        this.xPos = inputXPos;
        this.yPos = inputYPos;
    }
    /** getHealth
      * gets health
      * @returns organism health
      */
    public int getHealth(){
        return this.health;
    }
    /** setHealth
      * sets health
      * @param health to be set
      */
    public void setHealth(int newHealth){
        this.health = newHealth;
    }
     /** getXPos
      * gets xPos
      * @returns organism x position
      */
    public int getXPos(){
        return this.xPos;
    }
    /** setXPos
      * sets xPos
      * @param xPos to be set
      */
    public void setXPos(int newXPos){
        this.xPos = newXPos;
    }
    /** getYPos
      * gets yPos
      * @returns organism y position
      */
    public int getYPos(){
        return this.yPos;
    }
    /** setYPos
      * sets yPos
      * @param yPos to be set
      */
    public void setYPos(int newYPos){
        this.yPos = newYPos;
    }
    /** getFinishedMove
      * @return if the object has moved yet
      */
    public boolean getFinishedMove(){
        return finishedMove;
    }
    /** toggleFinishedMove
      * toggles finishedMove variable
      */
    public void toggleFinishedMove(){
        finishedMove = !finishedMove;
    }
}

