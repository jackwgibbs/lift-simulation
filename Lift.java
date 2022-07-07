public class Lift {
    //Attribute to store the number of people in the lift.
    private int totalNumberOfPeopleInLift;

    //Attribute to store the number of floors in the lift system.
    private int numberOfFloors;

    //Attribute to store the capacity of the lift.
    private int capacity = 10;

    //Attribute to store the current floor the lift is on.
    private int currentFloor;

    //Attribute to the store the direction of the lift (up or down).
    private boolean direction;

    //Array attribute to store the number of people waiting on each floor.
    private int[] numberOfPeopleOnFloorsWaiting;

    //Attribute to depict which lift system is being implemented.
    //false = Mechanical System.
    //true = Efficient System.
    private boolean liftType;

    //Attribute to store how many people need to still be served by the lift.
    private int numberOfPeopleToServe;

    /**
     * Constructor to create an instance of the Lift class.
     * @param numberOfFloors  The number of floors in the lift system.
     * @param capacity The maximum number of people that can enter the lift.
     * @param liftType The type of lift being implemented.
     * @param numberOfPeopleToServe The number of people that the lift must serve.
     */
    public Lift(int numberOfFloors, int capacity, boolean liftType, int numberOfPeopleToServe){
        //Set the number of floors in the lift system.
        this.numberOfFloors = numberOfFloors;

        //Set the capacity of the lift.
        this.capacity = capacity;

        //Set the current floor of the lift.
        this.currentFloor = 0;

        //Set the direction of the lift.
        this.direction = true;

        //Create the number of people waiting on each floor array of size number
        //of floors + 1 (to take into account the ground floor).
        numberOfPeopleOnFloorsWaiting = new int[numberOfFloors +1];

        //Set the lift type.
        this.liftType = liftType;

        //Set the number of people populating the building that need to be served.
        this.numberOfPeopleToServe = numberOfPeopleToServe;
    }

    /**
     * Method to return the capacity of the lift.
     */
    public int getCapacity(){
        //Return the capacity of the lift system.
        return capacity;
    }

    /**
     * Method to return the number of people in the lift.
     */
    public int getTotalNumberOfPeopleInLift(){
        //Return the number of people in the lift.
        return this.totalNumberOfPeopleInLift;
    }

    /**
     * Method to return the floor the lift is currently on.
     */
    public int getCurrentFloor(){
        //Return the floor the lift is currently on.
        return this.currentFloor;
    }

    /**
     * Method to call the relevant method to move the lift to the next floor, based upon
     * what type of lift has been implemented (mechanical or efficient).
     * @param peopleToBeServed Array of all Person objects populating the building.
     */
    public void moveLiftToNextFloor(Person[] peopleToBeServed){
        if (liftType){
            //If the lift type is the efficient lift system (true), the moveLiftToNextFloorEfficient
            //method is called to move the lift.
            moveLiftToNextFloorEfficient(peopleToBeServed);
        }else{
            //If the lift type is mechanical (false), the moveLiftToNextFloorMechanical
            //method is called to move the lift.
            moveLiftToNextFloorMechanical();
        }
    }

    /**
     * Method to change which floor the lift is on (for a lift using my control system).
     * @param peopleToBeServed ArrayList of all Person objects needing to be served by the lift.
     */
     public void moveLiftToNextFloorEfficient(Person[] peopleToBeServed){

        if (currentFloor == 0){
            //If lift is at the bottom, change its direction to up and make it go up 1 floor.
            currentFloor = 1;
            direction = true;

        }else if (currentFloor == numberOfFloors && direction) {
            //If the lift reaches the top, change direction to down and go down one floor.
            currentFloor = numberOfFloors - 1;
            direction = false;

        }else if(currentFloor <numberOfFloors && peopleToBeServed.length !=0){
            //continueInDirection stores whether the lift should continue travelling in
            //the current direction.
            boolean continueInDirection = false;

            //Get the number of people that still need to be served by the lift.
            int numberPeopleToBeServed = peopleToBeServed.length;

            //Iterate through each person that needs to be served.
            for (Person currentPerson : peopleToBeServed) {

                //Get the next Person.
                //Get the direction the person wishes to travel.
                boolean personDirection = currentPerson.getDirection();

                //Get whether the person is in the lift or not.
                boolean personInLift = currentPerson.InLift();

                //Get the persons start floor.
                int startFloor = currentPerson.getStartFloor();

                //Get the persons destination floor.
                int destinationFloor = currentPerson.getDestinationFloor();

                if (direction) {
                    if (personDirection && !personInLift && startFloor > currentFloor) {
                        //If there exists a person not in lift but want to go up and is higher
                        //than the lift, continue up to get them.
                        continueInDirection = true;

                    } else if (personDirection && personInLift && destinationFloor > currentFloor) {
                        //If there exists a person that is in the lift going up who wishes to exit the lift on a higher floor,
                        //then current, continue travelling up to that floor.
                        continueInDirection = true;

                    } else if (!personDirection && !personInLift && startFloor > currentFloor) {
                        //If there exists a person that is not in the lift who wishes to go down but is on
                        //a higher floor, continue travelling up to them.
                        continueInDirection = true;
                    }
                } else {
                    if (!personDirection && personInLift && destinationFloor < currentFloor) {
                        //If there exists a person who is in the lift going down who wishes to exit the lift on a floor lower
                        //than the current floor, continue going down.
                        continueInDirection = true;

                    } else if (!personDirection && !personInLift && startFloor < currentFloor) {
                        //If there exists a person who is not in the lift (which is going down) and is waiting
                        //on a floor lower than the current, continue down to get them.
                        continueInDirection = true;

                    } else if (personDirection && !personInLift && startFloor < currentFloor) {
                        //If there exists a person that is not in the lift who wishes to go up but is on
                        //a lower floor, continue travelling down to them.
                        continueInDirection = true;
                    }
                }
            }

            if (continueInDirection){
                if (direction){
                    //If lift is going up and continuing going up, add 1 to currentFloor.
                    currentFloor +=1;
                }else{
                    //If lift is going down and continuing going down, subtract 1 from currentFloor.
                    currentFloor -=1;
                }
            }else{
                //If lift is going up and changing direction, change direction attribute of lift.
                //If lift is going down and changing direction, change direction attribute of lift.
                direction = !direction;
            }
        }
    }

    /**
     * Method to change which floor the lift is on (for a mechanical lift which
     * goes all the way to the top and all the way back to the bottom).
     */

    public void moveLiftToNextFloorMechanical(){
        if (currentFloor == 0 && !direction){
            //If lift is at the bottom, change its direction to up and make it go up 1 floor.
            currentFloor = 1;
            direction = true;

        }else if(direction  && currentFloor <numberOfFloors){
            //If lift is going up, increase current floor number by 1.
            currentFloor = currentFloor +1;

        }else if (currentFloor == numberOfFloors && direction){
            //If lift is at the top, change direction to down and make it go down 1 floor.
            currentFloor = numberOfFloors-1;
            direction = false;

        }else{
            //If lift is going down, decrease floor number by 1.
            currentFloor = currentFloor - 1;
        }
    }

    /**
     * Method to get the direction of the lift.
     */
    public boolean getDirection(){
        //Return the direction the lift is currently travelling in.
        return this.direction;
    }

    /**
     * Method to add a person to the lift.
     * @param startFloor The floor the person is getting in the lift on.
     */
    public void addPersonToLift(int startFloor){
        //Increase the totalNumberOfPeopleInLift by 1.
        this.totalNumberOfPeopleInLift++;
        setNumberOfPeopleWaitingOnFloorDecrease(startFloor);
    }

    /**
     * Method to remove a person from the lift.
     */
    public void removePersonInLift(){
        //Decrease the totalNumberOfPeopleInLift by 1.
        totalNumberOfPeopleInLift--;
    }

    /**
     * Method to increase the number of people waiting for the lift
     * on a given floor.
     * @param floor The floor to increase number of people waiting.
     */
    public void setNumberOfPeopleWaitingOnFloorIncrease(int floor){
        //Increase the number of people waiting on a particular floor.
        numberOfPeopleOnFloorsWaiting[floor] ++;
    }

    /**
     * Method to decrease the number of people waiting for the lift
     * on a given floor.
     * @param floor The floor to decrease number of people waiting.
     */
    public void setNumberOfPeopleWaitingOnFloorDecrease(int floor){
        //Decrease the number of people waiting on a particular floor.
        numberOfPeopleOnFloorsWaiting[floor] --;
    }

    /**
     * Method to get the number of people waiting for the lift
     * on a given floor.
     * @param floor The floor to get the number of people waiting.
     */
    public int getNumberOfPeopleWaitingOnFloor(int floor){
        //Return the number of people waiting for the lift on a particular floor.
        return numberOfPeopleOnFloorsWaiting[floor];
    }

    /**
     * Method to return the number of floors in the lift system.
     */
    public int getNumberOfFloors(){
        //Return the number of floors in the lift system.
        return numberOfFloors;
    }

    /**
     * Method to return the number of people still needing to be served by the lift.
     */
    public int getNumberOfPeopleToBeServed(){
        //Return the number of people to be served by the lift at the current time.
        return numberOfPeopleToServe;
    }

    /**
     * Method to decrease the number of people needing to be served by the lift.
     */
    public void decreaseNumberOfPeopleToBeServed(){
        //Decrease the number of people to be served by the lift.
        numberOfPeopleToServe--;
    }
}