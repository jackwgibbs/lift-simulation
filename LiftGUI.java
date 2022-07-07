import javax.swing.*;
import java.awt.*;

public class LiftGUI {
    //Attribute floorsArray array stores a Floor object for each floor the lift serves.
    private Floor[] floorsArray;

    //Attribute floorsArrayTxt stores a JLabel object for each floor the lift serves,
    //which will display how many people are waiting for the lift on that floor.
    private JLabel[] floorsArrayTxt;

    //Create instance of the JFrame.
    public JFrame frame = new JFrame("Lift Simulation");

    //Create instance of the JPanel
    public JPanel panel = new JPanel();

    //Attribute to store the number of floors in the lift system which the GUI should display.
    private int numberOfFloors;

    //Attribute of JLabel type to display the number of people in the lift at any given time.
    private JLabel peopleInLiftLabel;

    /**
     * Constructor to create instance of the GUI class.
     * @param numberOfFloors  The number of floors in the lift system.
     */
    public LiftGUI(int numberOfFloors) {
        //Set the number of floors attribute.
        this.numberOfFloors = numberOfFloors;
        //Create the floorsArray array of size number of floors +1 (add 1 to include ground floor)

        floorsArray = new Floor[numberOfFloors +1];

        //Create the floorsArrayTxt array of size number of floors +1 (add 1 to include ground floor)
        floorsArrayTxt = new JLabel[numberOfFloors +1];

        //Create the peopleInLiftLabel to display how many people are in the lift.
        peopleInLiftLabel = new JLabel();

        //Set the colour of the peopleInLiftLabel to white.
        peopleInLiftLabel.setForeground(Color.black);

        //Add the label for number of people in the lift to the JPanel.
        panel.add(peopleInLiftLabel);

        //Set the size of the JPanel.
        panel.setPreferredSize(new Dimension(200, 40));

        //Create the floors and floor JLabels.
        for (int i = 0; i <= numberOfFloors; i++) {
            //Create instance of Floor.
            Floor floor = new Floor(i);

            //Add new Floor instance to floorsArray array.
            floorsArray[i] = floor;

            //Create new instance of JLabel.
            JLabel peopleWaitingOnFloorLabel = new JLabel();

            //Set the text color of the JLabel to white.
            peopleWaitingOnFloorLabel.setForeground(Color.black);

            //Display the JLabel at specific x coordinate.
            peopleWaitingOnFloorLabel.setAlignmentX(1000);

            //Add JLabel to the floorsArrayTxt array.
            floorsArrayTxt[i] = peopleWaitingOnFloorLabel;

            //Add the JLabel to the panel.
            panel.add(peopleWaitingOnFloorLabel);

            //Get the JButton graphic for the floor using getFloorGraphic method.
            JButton floorGraphic = floorsArray[i].getFloorGraphic();

            //Add JButton to the panel
            panel.add(floorGraphic);
        }
    }

    /**
     * Method to display the lift using a graphical user interface.
     * This method displays the lift.
     * @param currentFloorNumber   The floor the lift is currently on.
     * @param numberOfPeopleInLift The total number of people in the lift at the current time.
     * @param lift The lift object.
     */
    public void displayGUI(int currentFloorNumber, int numberOfPeopleInLift, Lift lift){
        //Convert the numberOfPeopleInLift to an Integer.
        String numberOfPeopleInLiftStr = Integer.toString(numberOfPeopleInLift);

        //Set the text of the JLabel to display the number of people in the lift.
        peopleInLiftLabel.setText("Number of people in lift: " + numberOfPeopleInLiftStr);

        //Set the position of the JLabel on the panel.
        peopleInLiftLabel.setBounds(10, 0, 500, 30);

        //Add the JLabel to the panel.
        panel.add(peopleInLiftLabel);

        //Integer h stores the y position of the JButton floor graphic.
        int h = 30;

        //Display the graphics for each floor.
        for (int i = floorsArray.length-1; i>=0; i--) {
            //Get the JButton representing the floor using the getFloorGraphic method.
            JButton floorGraphic = floorsArray[i].getFloorGraphic();

            //If the floor is the floor the lift is currently on, change it to a different
            //colour to distinguish which floor the lift is on.
            if (floorsArray[i].getFloorNumber()==currentFloorNumber){
                //Set the colour of the JButton to white.
                floorGraphic.setBackground(Color.white);
            }else{
                floorGraphic.setBackground(Color.gray);
            }

            //Get the JLabel representing the number of people waiting on the floor.
            JLabel peopleWaitingOnFloorLabel = floorsArrayTxt[i];

            //Get the number of people waiting on the floor.
            int peopleWaitingOnFloor = lift.getNumberOfPeopleWaitingOnFloor(i);

            //Convert the number of people waiting on the floor from an integer to a String.
            String peopleWaitingOnFloorStr = Integer.toString(peopleWaitingOnFloor);

            //Set the text of the JLabel to the number of people waiting on the floor.
            peopleWaitingOnFloorLabel.setText(peopleWaitingOnFloorStr);

            //Set the position of the JLabel.
            peopleWaitingOnFloorLabel.setBounds(120, h, 30,20);

            //Add the JLabel to the JPanel.
            panel.add(peopleWaitingOnFloorLabel);

            //Set the position of the JButton representing the floor.
            floorGraphic.setBounds(10,h,100,20);

            //Add the JButton to the JPanel.
            panel.add(floorGraphic);

            //Add 30 so that the next JButton appears above the previous.
            h += 20;
        }
        //Set the layout of the JPanel.
        panel.setLayout(null);

        //Add the JPanel to the JFrame.
        frame.add(panel);

        if (numberOfFloors <5){
            //Set the size of the JFrame.
            frame.setSize(200,(numberOfFloors+1)*50);
        }else{
            //Set the size of the JFrame.
            frame.setSize(200,(numberOfFloors+1)*30);
        }

        //Repaint the JFrame to show latest changes.
        frame.repaint();

        //Show the JFrame.
        frame.setVisible(true);
    }

    /**
     * Method to close th e GUI window.
     */
    public void closeGui(){
        //Close the GUI window.
        frame.dispose();
    }

}