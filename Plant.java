/** Plant.java
  * Class of plants
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
class Plant extends Organism{
    Plant(int inputHealth, int inputXPos, int inputYPos){
        super(inputHealth, inputXPos, inputYPos);
    }
    /**getFinishedMove 
     * ensures that plants will never get their moves checked
     * @return true, because plants will never move
      */
    @Override
    public boolean getFinishedMove(){
        return true;
    }
    
    /** toggleFinishedMove
      * does literally nothing
      */
    @Override
    public void toggleFinishedMove(){
    }
}