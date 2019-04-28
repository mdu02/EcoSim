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
    /** ensures that plants will never get their moves checked
      */
    public boolean getFinishedMove(){
        return true;
    }
}