public class Person {
    //Attribute to hold the floor the person is calling the lift from.
    private int startFloor;

    //Attribute to hold the floor the person is going to.
    private int destinationFloor;

    //Attribute to hold which direction the person is heading.
    private boolean direction;

    //Boolean attribute to indicate whether person is in lift.
    private boolean inLift = false;

    //Attribute to store how long the person has to wait to be served.
    private int waitTime = 0;

    //Attribute to store how long the person is in the lift.
    private int timeInLift = 0;

    //Attribute to store whether the person has been served by the lift.
    private boolean served = false;

    /**
     * Constructor to create instance of the Person class.
     * @param currentFloor  The floor number the person is starting on.
     * @param destinationFloor  The floor the person is going to.
     */
    public Person(int currentFloor, int destinationFloor){
        //Set the start floor attribute.
        this.startFloor = currentFloor;

        //Set the destination floor attribute.
        this.destinationFloor = destinationFloor;

        //Get the direction the person is going.
        if (currentFloor - destinationFloor < 0 ){
            //Person is going up in the lift.
            direction = true;
        }else{
            //Person is going down in the lift.
            direction = false;
        }
    }

    /**
     * Method to check whether a person should get into the lift.
     * @param lift The lift object.
     */
    public void shouldPersonGetInLift(Lift lift){
        //Get the direction of the lift using the getDirection method
        boolean liftDirection = lift.getDirection();

        //Get the capacity of the lift.
        int liftCapacity = lift.getCapacity();

        //Get the number of people in the lift.
        int numberOfPeopleInLift = lift.getTotalNumberOfPeopleInLift();

        int currentFloor = lift.getCurrentFloor();

        //Check whether the person is in the lift.
        if (!inLift) {
            //Check whether the floor the lift is on is the same as the floor the person is
            //waiting on and whether the direction of the lift is the same as the direction
            //the person needs to go.
            //Also check whether the lift is full.
            if ((currentFloor == this.startFloor) && (liftDirection == direction) && numberOfPeopleInLift < liftCapacity) {
                //Set the inLift attribute to true.
                inLift = true;

                //Add person to the lift.
                lift.addPersonToLift(startFloor);

            //Condition for if the lift is at the top or the bottom of the system.
            }else if ((currentFloor == this.startFloor) && (numberOfPeopleInLift <= liftCapacity )){
                if ((currentFloor == 0|| currentFloor == lift.getNumberOfFloors())) {

                    //Set the inLift attribute to true.
                    inLift = true;

                    //Add person to the lift.
                    lift.addPersonToLift(startFloor);
                }
            }else{
                //Add 1 to the waitTime attribute of the person if they do not get in lift.
                this.waitTime ++;
            }
        }
    }

    /**
     * Method to check whether a person should get out the
     * lift, providing they are in it.
     * @param lift The lift object.
     */
    public boolean shouldPersonGetOutLift(Lift lift){
        //Check whether the person is in the lift.
        if (inLift){
            //Check whether the floor the lift is on is the same as the persons destination floor.
            if (lift.getCurrentFloor() == this.destinationFloor){
                //Set the inLift attribute of the person to false.
                inLift = false;

                //Remove the person from the lift.
                lift.removePersonInLift();

                //Return true as the Person has been removed from the lift.
                return true;
            }else{
                //Add 1 to the timeInLift attribute of the person.
                this.timeInLift++;

                //Return false since the Person has not got out the lift.
                return false;
            }
        }
        return false;
    }

    /**
     * Method to calculate the total cost of the journey
     * for a person.
     * Returns the sum of the total wait time and time in the lift,
     * where time is the number of floors.
     */
    public int getTotalCostForPerson(){
        //Return the total wait time for the lift plus the total time in the lift.
        return this.timeInLift + this.waitTime;
    }

    /**
     * Method to return the start floor of the person.
     */
    public int getStartFloor(){
        //Return the start floor of the Person object.
        return startFloor;
    }

    /**
     * Method to get the destination floor of the person.
     */
    public int getDestinationFloor(){
        //Return the destination floor of the Person object.
        return destinationFloor;
    }

    /**
     * Method to get the direction the person will be travelling in the lift.
     */
    public boolean getDirection(){
        //Return the direction the person will be travelling.
        return direction;
    }

    /**
     * Method to return whether the person is in the lift at the current time.
     */
    public boolean InLift(){
        //Return boolean indicating if person is in lift.
        return inLift;
    }

    /**
     * Method to return whether the person has been served by the lift.
     */
    public boolean hasPersonBeenServed(){
        //Return whether the person has been served.
        return served;
    }

    /**
     * Method to set served to true meaning the person has been served by the lift.
     */
    public void setPersonServed(){
        //Set the served boolean to true.
        served = true;
    }
}
