/** Animal.java
  * Class of all animals (sheep, wolves)
  * @author Michael Du
  * @since 2019-04-16
  * @version 1.00
  */
abstract class Animal extends Organism{
    private boolean isMale = false;
    private boolean finishedMove = false;
    private int maxHealth;
    private int age = 0;
    private int deathAge;
    private int lastBreed = 10; //puberty?
    private int breedThreshold = 40;
    private final int BREEDCOOLDOWN = 15;
    Animal(int inputHealth, int inputXPos, int inputYPos){
        super(inputHealth, inputXPos, inputYPos);
        this.deathAge = Integer.MAX_VALUE;
        //this.deathAge = (int)((Math.random()*30)+70); //death age for each animal is random between 70 and 100
        if (Math.random() < 0.5){
            this.isMale = true;
        }
    }
     /**setMaxHealth
      * sets max health
      * @param new max health
      */
    public void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
    }
    /** getGender
      * @return the gender of the animal
      */
    public boolean getGender(){
        return this.isMale;
    }
     /** cantMove
      * @return if moves are finished yet
      */
    public boolean cantMove(){
        return this.finishedMove;
    }   
    /** canBreed
      * @return if the animal can breed
      */
    public boolean canBreed(){
        return (this.age-this.lastBreed >= this.BREEDCOOLDOWN && this.getHealth()>= this.breedThreshold);
    }
    /** setLastBreed
      * sets lastbreed to current age
      */
    public void setLastBreed(){
        this.lastBreed = this.age;
    }
    /** toggleFinishedMove
      * toggles finishedMove variable
      */
    public void setFinishedMove(boolean bool){
        finishedMove = bool;
    }
    /** addHealth
      * adds health
      * @param health to be set
      */
    public void addHealth(int newHealth){
        this.setHealth(this.getHealth() + newHealth);
        if (this.getHealth() > this.maxHealth){
            this.setHealth(this.maxHealth);
        }
    }
    /** incrementAge
      * increments age
      */
    public void incrementAge(){
        this.age++;
    }
    /**tooOld
      * returns true if animal ought to be dead
      * @return the liveliness of the animal
      */
    public boolean tooOld(){
        return (this.age>this.deathAge);
    }
    /** makeMove
      * makes the best move, as determined by the grid
      * @return the array of the new x,y coordinates of the piece
      */
    abstract int[] makeMove(Animal[][] animals, Plant[][] plants);
}