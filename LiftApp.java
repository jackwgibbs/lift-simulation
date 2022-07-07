import java.util.ArrayList;
import java.util.Random;

public class LiftApp {
    //Attributes to store the wait times of people served by each lift.
    private static ArrayList<Integer> waitTimesMechanicalOfEachPerson;
    private static ArrayList<Integer> waitTimesEfficientOfEachPerson;

    //Attributes to store the number of floors and people in the system
    private static int numberOfFloors;
    private static int numberOfPeople;

    //Attributes to store the total wait time for each lift, can be divided by no. of people for average.
    public static int totalWaitMechanical = 0;
    public static int totalWaitEfficient = 0;

    //Attributes of the Lift type for each lift.
    private static Lift mechanicalLift;
    private static Lift efficientLift;

    //Attributes of the array type to store the people needed to be served by each lift.
    private static Person[] peopleToBeServedByMechanicalLift;
    private static Person[] peopleToBeServedByEfficientLift;

    //Attributes of the LiftGUI class for the GUI for each lift.
    private static LiftGUI mechanicalLiftGUI;
    private static LiftGUI efficientLiftGUI;

    /**
     * Main method which instantiates objects for program and calls LiftSimulation method.
     */
    public static void main(String[] args) {
        //Create a new StartGUI instance.
        StartGUI startScreen = new StartGUI();

        //Display the start screen.
        startScreen.displayStartScreen();
        //Check whether the submit button has been clicked.
        boolean start = startScreen.hasSelectButtonBeenPressed();
        while (!start) {
            start = startScreen.hasSelectButtonBeenPressed();
            wait(500);
        }

        //Get the number of people and floors for the simulation.
        numberOfFloors = startScreen.getFloors();
        numberOfPeople = startScreen.getPeople();

        //Total number of times to repeat the simulation.
        int totalTests = 50;

        //Definite iteration to repeat the simulation.
        for (int test=0; test<totalTests; test++) {
            //Instantiate all necessary objects.
            instantiateObjects();

            //Populate the building by instantiating people.
            populateBuilding();

            //Run the simulation for each lift type.
            waitTimesMechanicalOfEachPerson = LiftSimulation(mechanicalLift, peopleToBeServedByMechanicalLift, mechanicalLiftGUI);
            waitTimesEfficientOfEachPerson = LiftSimulation(efficientLift, peopleToBeServedByEfficientLift, efficientLiftGUI);

            //Determine the average wait times for each person in each system.
            determineAverageWaitTimes();
        }

        //Display the average wait time for each system.
        System.out.println("Mechanical Average Wait Time:");
        System.out.println(totalWaitMechanical/totalTests);
        System.out.println("Efficient Average Wait Time:");
        System.out.println(totalWaitEfficient/totalTests);
    }

    /**
     * Main method of the program which controls the lift system..
     * @param lift The Lift object (either mechanical or efficient).
     * @param peopleToBeServed Array containing Person objects.
     * @gui LiftGui object which is used to display the lift as a GUI.
     */
    public static ArrayList<Integer> LiftSimulation(Lift lift, Person[] peopleToBeServed, LiftGUI gui){
        //For a large number of tests it is better to disable the GUI for quicker results.
        boolean disableGUI = false;

        //ArrayList for the wait times.
        ArrayList<Integer> waitTimes = new ArrayList<Integer>();

        //Get the total number of people to be served.
        int numberOfPeopleToBeServed;

        //While not everyone has been served
        while (lift.getNumberOfPeopleToBeServed() >0){
            //Get the floor the lift is currently on.
            int currentFloor = lift.getCurrentFloor();

            //Display the GUI.
            if (!disableGUI) {
                gui.displayGUI(currentFloor, lift.getTotalNumberOfPeopleInLift(), lift);
            }

            //Get the number of people to be served.
            numberOfPeopleToBeServed = (peopleToBeServed.length);

            for (int i = 0; i < numberOfPeopleToBeServed; i ++){
                //Get the next person to evaluate.
                Person currentPerson = peopleToBeServed[i];

                if (!currentPerson.hasPersonBeenServed()) {
                    //Check whether the Person should get in the lift.
                    currentPerson.shouldPersonGetInLift(lift);

                    //Check whether the person should get out the lift.
                    boolean hasPersonGotOut = currentPerson.shouldPersonGetOutLift(lift);
                    if (hasPersonGotOut) {
                        //Get the total cost of the trip for the person.
                        int totalCostForPerson = currentPerson.getTotalCostForPerson();
                        lift.decreaseNumberOfPeopleToBeServed();
                        currentPerson.setPersonServed();
                        //Add this total cost to the waitTimes ArrayList.
                        waitTimes.add(totalCostForPerson);
                    }
                }
            }

            //Move the lift to the next floor.
            lift.moveLiftToNextFloor(peopleToBeServed);

            //Wait 0.5 seconds before moving onto the next floor.
            if (!disableGUI) {
                wait(500);
            }

        }
        gui.closeGui();
        //Display wait times at the end of the simulation
        return waitTimes;
    }

    /**
     * Method to determine the average wait time for one simulation of lift system.
     */
    public static void determineAverageWaitTimes(){
        int mechanicalTotalForTest = 0;
        int efficientTotalForTest = 0;

        for (Integer timesMechanicalOfEachPerson : waitTimesMechanicalOfEachPerson) {
            mechanicalTotalForTest += timesMechanicalOfEachPerson;
        }
        totalWaitMechanical += ( mechanicalTotalForTest / waitTimesMechanicalOfEachPerson.size());

        for (Integer timesEfficientOfEachPerson : waitTimesEfficientOfEachPerson) {
            efficientTotalForTest += timesEfficientOfEachPerson;
        }
        totalWaitEfficient += (efficientTotalForTest / waitTimesEfficientOfEachPerson.size());
    }

    /**
     * Method to instantiate objects required for the simulation.
     */
    public static void instantiateObjects(){
        //Instantiate peopleToBeServed ArrayList.
        peopleToBeServedByMechanicalLift = new Person[numberOfPeople];
        peopleToBeServedByEfficientLift =  new Person[numberOfPeople];

        //Instantiate waitTimes ArrayList.
        waitTimesMechanicalOfEachPerson = new ArrayList<Integer>();
        waitTimesEfficientOfEachPerson = new ArrayList<Integer>();

        //Instantiate Lift objects.
        mechanicalLift = new Lift(numberOfFloors, 10, false, numberOfPeople);
        efficientLift = new Lift(numberOfFloors, 10, true, numberOfPeople);

        //Instantiate new GUI object called gui.
        mechanicalLiftGUI = new LiftGUI(numberOfFloors);
        efficientLiftGUI = new LiftGUI(numberOfFloors);
    }

    /**
     * Method to wait a certain amount of time.
     * @param timeInMS Time in ms to wait.
     */
    public static void wait(int timeInMS){
        try{
            //Stop for the time specified in the timeInMS parameter.
            Thread.sleep(timeInMS);
        }catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method to create Person objects and populate the building.
     * When a Person is created, their start and destination floor are randomly chosen.
     * Two identical Person objects are created at a time since one is served by the mechanical lift,
     * the other by the efficient lift.
     */
    public static void populateBuilding(){
        //Generate random people in the building.
        for (int i = 0; i < numberOfPeople; i++) {
            Random rand = new Random();
            //Get a random start floor
            int startFloor = rand.nextInt(numberOfFloors);
            //Get a random destination floor.
            int destinationFloor = rand.nextInt(numberOfFloors);
            //Ensure the destination floor is not the same as the start floor.
            while (destinationFloor == startFloor){
                destinationFloor = rand.nextInt(numberOfFloors);
            }

            //Create new person object for mechanical lift.
            Person personUsingMechanical = new Person(startFloor, destinationFloor);
            //Create new person object for efficient lift.
            Person personUsingEfficient = new Person(startFloor, destinationFloor);

            //Add the new person to the people to be served array for each lift.
            peopleToBeServedByMechanicalLift[i] = personUsingMechanical;
            peopleToBeServedByEfficientLift[i] = personUsingEfficient;

            //Increase number of people waiting on floor new person is currently on for each lift.
            mechanicalLift.setNumberOfPeopleWaitingOnFloorIncrease(startFloor);
            efficientLift.setNumberOfPeopleWaitingOnFloorIncrease(startFloor);
        }
    }
}