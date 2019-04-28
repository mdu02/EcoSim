/** Wolf.java
  * Class of wolves
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
class Wolf extends Animal implements Comparable<Wolf>{
    Wolf(int inputHealth, int inputXPos, int inputYPos){
        super(inputHealth, inputXPos, inputYPos);
    }
    public int compareTo(Wolf otherWolf){
        if (this.getHealth() > otherWolf.getHealth()){
            return 1;
        } else if (this.getHealth() < otherWolf.getHealth()){
            return -1;
        } else{
            return 0;
        }
    }
}