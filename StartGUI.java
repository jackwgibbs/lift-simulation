import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;

public class StartGUI {
    //Attribute submit Boolean to store whether the submit button has been clicked.
    public boolean submit = false;

    //Attribute to store the number of floors the user selects.
    public int floors = 0;

    //Attribute to store the number of people the user selects.
    public int people = 0;

    //Create instance of the JFrame.
    public JFrame frame = new JFrame("Lift Simulation Configuration");

    //Create the JPanels.
    public JPanel panel1 = new JPanel();
    public JPanel panel2 = new JPanel();
    public JPanel panel3 = new JPanel();
    public JPanel panel4 = new JPanel();
    public JPanel panel5 = new JPanel();

    //Create the Slider objects.
    public JSlider floorSlider = new JSlider(JSlider.HORIZONTAL, 2,50,25);
    public JSlider peopleSlider = new JSlider(JSlider.HORIZONTAL, 1,50,25);

    public JButton submitButton = new JButton("Submit");

    //Create two the two labels, one for the number of floors, one for the number of people.
    public JLabel floorLabel = new JLabel("Number of floors in Lift System: 25", JLabel.CENTER);
    public JLabel peopleLabel = new JLabel("Number of people in Lift System: 25", JLabel.CENTER);

    /**
     * Constructor to create the JSwing objects for the start screen GUI.
     */
    public StartGUI() {
        //Configure the frame.
        frame.setSize(250, 400);
        frame.setLayout(new GridLayout(5, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Configure the first slider.
        floorSlider.setMinorTickSpacing(2);
        floorSlider.setPaintTicks(true);
        floorSlider.setPaintLabels(true);

        //Add the positions to the slider.
        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
        position.put(2, new JLabel("2"));
        position.put(10, new JLabel("10"));
        position.put(20, new JLabel("20"));
        position.put(30, new JLabel("30"));
        position.put(40, new JLabel("40"));
        position.put(50, new JLabel("50"));


        floorSlider.setLabelTable(position);

        //Add change listener to the slider so that the value is changed when the slider is moved.
        floorSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                floorLabel.setText("Number of floors in Lift System: " + floorSlider.getValue());
            }
        });

        //Configure the second slider.
        peopleSlider.setMinorTickSpacing(2);
        peopleSlider.setPaintTicks(true);
        peopleSlider.setPaintLabels(true);

        //Add the positions to the slider.
        Hashtable<Integer, JLabel> position2 = new Hashtable<Integer, JLabel>();
        position2.put(1, new JLabel("1"));
        position2.put(10, new JLabel("10"));
        position2.put(20, new JLabel("20"));
        position2.put(30, new JLabel("30"));
        position2.put(40, new JLabel("40"));
        position2.put(50, new JLabel("50"));


        peopleSlider.setLabelTable(position2);

        //Add change listener to the slider so that the value is changed when the slider is moved.
        peopleSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                peopleLabel.setText("Number of people in Lift System: " + peopleSlider.getValue());
            }
        });

        //Configure the submit button.
        submitButton.setBounds(50,100,95,30);
        //Add action listener to the button.
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Set submit to true since the button has been pressed.
                submit = true;

                //Get the number of floors and people selected.
                floors = floorSlider.getValue();
                people = peopleSlider.getValue();

                //Close the frame.
                frame.dispose();
            }
        });
    }

    /**
     * Method to display the start screen GUI.
     */
    public void displayStartScreen(){
        // Add the slider to the panel
        panel1.add(floorSlider);
        panel2.add(floorLabel);
        panel3.add(peopleSlider);
        panel4.add(peopleLabel);
        panel5.add(submitButton);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.setVisible(true);
    }

    /**
     * Method to return whether the submit button has been pressed.
     */
    public boolean hasSelectButtonBeenPressed(){
        //Return whether the submit button has been selected.
        if (submit){
            System.out.print("People: ");
            System.out.println(people);
            System.out.print("Floors: ");
            System.out.println(floors);
            return true;
        }
        return false;
    }

    /**
     * Method to return the number of floors selected.
     */
    public int getFloors(){
        //Return the number of floors the user chose.
        return floors;
    }

    /**
     * Method to return the number of people selected.
     */
    public int getPeople(){
        //Return the number of people the user chose.
        return people;
    }
}