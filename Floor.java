import javax.swing.*;

public class Floor {
    //Attribute to store the number of the floor.
    private int floorNumber;

    //Attribute floorButton JButton to represent the floor graphically.
    private JButton floorGraphic;

    /**
     * Constructor to create instance of the Floor class.
     * @param floorNumber  The number of the floor
     */
    public Floor(int floorNumber){
        //Set the floorNumber attribute.
        this.floorNumber = floorNumber;

        //Variable to store the number of the floor as a String,
        //so that it can be displayed within a JLabel.
        String floorNumberStr = Integer.toString(floorNumber);

        //Set the floorGraphic attribute to a new JButton instance.
        floorGraphic = new JButton(floorNumberStr);

        //Disable the JButton.
        floorGraphic.setEnabled(false);

        //Set the JButton to opaque.
        floorGraphic.setOpaque(true);
    }

    /**
     * Method to get the floor number.
     */
    public int getFloorNumber(){
        //Return the number of the floor.
        return floorNumber;
    }

    /**
     * Method to get the floor graphic which returns a JButton.
     */
    public JButton getFloorGraphic(){
        //Return the JButton representing the floor graphically.
        return floorGraphic;
    }
}
