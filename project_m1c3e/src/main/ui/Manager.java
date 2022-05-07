package ui;

import model.Doctor;
import model.EventLog;
import model.Event;
import model.Management;
import model.Patient;


import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static javafx.application.Platform.exit;
import static org.omg.CORBA.ORB.init;

//REPRESENTS THE UI
//Code based on https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class Manager extends JFrame {
    Management mainManagement;
    Doctor doctorFound;

    private static final String JSON_STORE = "./data/hospitalManagement.json";
    JsonReader jsonReader;
    JsonWriter jsonWriter;

    public static final Integer WIDTH = 1000;
    public static final Integer HEIGHT = 700;
    JInternalFrame managerView;
    JTextField name;
    JTextField conditions;
    JTextField dob;
    JTextField doctor;
    JTextField prevName;
    JInternalFrame inPanel;
    JInternalFrame outPanel;
    JInternalFrame logo;
    JLabel outputText = new JLabel();

    JLabel option1;
    JLabel option2;
    JLabel option3;
    JLabel option4;
    JLabel option5;
    JTable table;
    JInternalFrame tableFrame;

    //MODIFIES:this
    // EFFECTS: creates a new Management object and new JsonReader and JsonWriter objects for persistence
    public Manager() {
        mainManagement = new Management();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        initialiseGUI();
        init();
    }

    //MODIFIES: this
    //EFFECTS: initialise and update the GUI and constructs the interface the user sees when they open the application


    private void initialiseGUI() {


        JDesktopPane desktop = new JDesktopPane();
        exitApplication();
        addMouseListener(new DesktopFocusAction());
        initializeFrames();
        managerViewFormatting();
        setContentPane(desktop);
        setTitle("CPSC 210: Patient Management System");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        addButtonPanel();
        addInputPanel();
        addOutPanel();
        addLogo();


        logoSetUp();

        managerViewSetUp();
        desktop.add(managerView);

        inPanelSetUp();
        desktop.add(inPanel);

        tableSetUp();
        desktop.add(tableFrame, BorderLayout.PAGE_END);


        outPanelSetUp();

        desktop.add(outPanel);


        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void exitApplication() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {


                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    EventLog finalLog = EventLog.getInstance();
                    Iterator<Event> iter = finalLog.iterator();
                    while (iter.hasNext()) {
                        System.out.println(iter.next().getDescription());
                    }
                    System.exit(0);

                }
            }

        });
    }


    //MODIFIES:this
    //EFFECTS: formatting the manager view.

    private void managerViewFormatting() {
        managerView.setLayout(new BorderLayout());
        managerView.setSize(WIDTH, HEIGHT);
        managerView.setBackground(Color.blue);
    }

    //MODIFIES:this
    //EFFECTS: setting up the manager view.

    private void managerViewSetUp() {
        managerView.setLocation(200, 0);


        managerView.pack();
        managerView.setVisible(true);
    }
    //MODIFIES:this
    //EFFECTS: setting up the logo view.

    private void logoSetUp() {
        logo.setBounds(860, 0, 100, 80);
        logo.setVisible(true);
        logo.pack();
        add(logo);
    }
    //MODIFIES:this
    //EFFECTS: setting up the outPanel view.

    private void outPanelSetUp() {
        outPanel.setBounds(300, 300, 300, 300);
        outPanel.pack();
        outPanel.setVisible(true);
        outPanel.setLocation(0, 200);
    }
    //MODIFIES:this
    //EFFECTS: setting up the table view.

    private void tableSetUp() {
        table = new JTable(new TableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        tableFrame.add(scrollPane);
        tableFrame.setBounds(200, 200, 300, 300);
        tableFrame.pack();
        tableFrame.setVisible(true);
        tableFrame.setLocation(475, 170);
    }
    //MODIFIES:this
    //EFFECTS: initializes all the internal frames and add the mouse listener

    private void initializeFrames() {

        managerView = new JInternalFrame("Patient management system", false, false, false, false);
        inPanel = new JInternalFrame("Input Panel", false, false, false, false);
        tableFrame = new JInternalFrame();
        outPanel = new JInternalFrame("Output Panel", false, false, false, false);
        logo = new JInternalFrame("logo", false, false, false, false);
    }
    // MODIFIES:this
    //EFFECTS: retrieves the logo from the file and puts it in a logopanel.

    private void addLogo() {
        BufferedImage logoImage = null;
        try {

            logoImage = ImageIO.read(new File("./data/ubcLogo.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel logoPanel = new JPanel();
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoPanel.add(logoLabel);
        logoPanel.setBounds(100, 200, 10, 10);


        logo.add(logoPanel, BorderLayout.NORTH);


    }
    //MODIFIES: this
    //EFFECTS: helper function which sets up the input panel.

    private void inPanelSetUp() {
        inPanel.setLocation(0, 0);
        inPanel.pack();
        inPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: add the buttons to a JPanel and assigns it to manager view window.
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        buttonPanel.setBackground(Color.green);
        buttonPanel.setBounds(40, 80, 200, 200);
        buttonPanel.setBackground(Color.gray);
        buttonPanel.add(new JButton(new ProcessAdd()));
        buttonPanel.add(new JButton(new ProcessRename()));
        buttonPanel.add(new JButton(new ProcessRemove()));
        buttonPanel.add(new JButton(new ProcessSearch()));
        buttonPanel.add(new JButton(new ProcessFindConditions()));
        buttonPanel.add(new JButton(new ProcessFindDoctors()));
        buttonPanel.add(new JButton(new ProcessEmptyBeds()));
        buttonPanel.add(new JButton(new ProcessLoad()));
        buttonPanel.add(new JButton(new ProcessSave()));


        managerView.add(buttonPanel, BorderLayout.WEST);
    }

    //MODIFIES: this
    //EFFECTS: add the required text fields to create the input panel

    private void addInputPanel() {
        initialiseTextFields();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1));
        inputPanel.setBackground(Color.green);
        inputPanel.setBounds(40, 80, 500, 500);
        inputPanel.setBackground(Color.gray);
        option1 = new JLabel("Name");
        inputPanel.add(option1);
        inputPanel.add(name);

        option2 = new JLabel("Doctor");
        inputPanel.add(option2);
        inputPanel.add(doctor);

        option3 = new JLabel("conditions");
        inputPanel.add(option3);
        inputPanel.add(conditions);


        option4 = new JLabel("Date of birth");
        inputPanel.add(option4);
        inputPanel.add(dob);

        option5 = new JLabel("Previous name");
        inputPanel.add(option5);
        inputPanel.add(prevName);
        inPanel.setBounds(0, 0, 300, 300);


        inPanel.add(inputPanel, BorderLayout.EAST);


    }
    //MODIFIES: this
    //EFFECTS: constructs textfield for entering patient information

    private void initialiseTextFields() {
        prevName = new JTextField(5);
        name = new JTextField(5);
        doctor = new JTextField(5);
        conditions = new JTextField(5);
        dob = new JTextField(5);
    }
    //MODIFIES: this
    //EFFECTS: adds the outPanel view.

    private void addOutPanel() {
        JPanel output = new JPanel();
        output.setLayout(new GridLayout(4, 4));
        output.setBounds(40, 80, 500, 500);
        output.setBackground(Color.gray);
        output.add(outputText);
        outPanel.add(output, BorderLayout.WEST);
        outPanel.pack();

    }

    //MODIFIES:this
    //EFFECTS: prompts the user to add the details of the patients and adds only if there's enough space.
    public void processAdd() {
        try {
            if (mainManagement.findNumberOfEmptyBed() > 0) {

                String inputName = name.getText();
                String inputConditions = conditions.getText();
                Integer inputDob = Integer.parseInt(dob.getText());
                String inputDoctor = doctor.getText();
                mainManagement.addPatientToManagement(inputName, inputConditions, inputDob, inputDoctor);
            } else {
                outputText.setText("No empty beds. Remove patients to continue.");
            }
            initialiseGUI();

        } catch (Exception e) {
            outputText.setText("Incorrect input, try again.");
        }
        initialiseGUI();
    }

    //MODIFIES:this
    //EFFECTS: prompts the user to enter the name of the patient to be removed and call the remove patient function.
    public void processRemove() {

        String inputName = name.getText();
        String result = mainManagement.removePatient(inputName);
        outputText.setText(result);
        initialiseGUI();
    }

    //MODIFIES:this
    //EFFECTS: prompts the user to enter the previous name of the patient to be renamed
    // and new name, call the remove patient function.
    public void processRename() {

        String inputOldName = prevName.getText();
        String inputNewName = name.getText();
        String result = mainManagement.renamePatient(inputOldName, inputNewName);
        outputText.setText(result);
        initialiseGUI();

    }

    //MODIFIES:this
    //EFFECTS: searches the system for the patients and print out the details.
    public void processSearch() {
        //Scanner name = new Scanner(System.in);
        String inputName = name.getText();
        try {
            Patient result = mainManagement.searchPatients(inputName);
            outputText.setText("<html>Name:" + result.getName() + "<br/>Condition:" + result.getCondition()
                    + "<br/>Doctor:" + result.getDoctor() + "<br/>Date of birth:" + result.getDob() + "</html>");

        } catch (Exception e) {
            outputText.setText("Not found");
        }
        initialiseGUI();


    }
    //MODIFIES:this
    //EFFECTS: prompts the user to enter name of the patient and prints out their condition.

    public void processConditions() {

        String patientName = name.getText();
        try {
            outputText.setText("The patient suffers from "
                    + mainManagement.searchPatientCondition(patientName));
        } catch (Exception e) {
            outputText.setText("Patient not present in the system.");
        }
        initialiseGUI();

    }
    //MODIFIES:this
    //EFFECTS: Searches for a doctor that can treat the given disease

    public void processFindDoctors() {

        String issues = conditions.getText();
        try {
            doctorFound = mainManagement.findDoctor(issues);
            outputText.setText("Suggested Doctor: " + doctorFound.getName() + " " + doctorFound.getContact());

        } catch (Exception e) {
            outputText.setText("Couldn't find an eligible doctor");
        }
        initialiseGUI();

    }
    //MODIFIES:this
    //EFFECTS: Gives the number of empty beds and the number of patients that can be treated

    public void findNumberOfEmptyBeds() {
        outputText.setText("The number of empty beds is "
                + mainManagement.findNumberOfEmptyBed().toString());
        initialiseGUI();
    }

    //MODIFIES:this
    //EFFECTS: Loads the patients saved previously

    public void processLoad() {
        try {
            mainManagement = jsonReader.read();
            outputText.setText("loaded");
        } catch (IOException e) {
            outputText.setText("Unable to read from file: " + JSON_STORE);
        }
        initialiseGUI();


    }
    //MODIFIES:this
    //EFFECTS: Saves the patients to a file.

    private void processSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(mainManagement);
            jsonWriter.close();
            outputText.setText("Saved ");
        } catch (FileNotFoundException e) {
            outputText.setText("Unable to write to file ");
        }
        initialiseGUI();
    }


    //Represents action to be taken when user clicks desktop to switch focus. (Needed for key handling.)

    private class DesktopFocusAction extends MouseAdapter {
        //MODIFIES:this
        // EFFECTS:Represents action to be taken when user clicks desktop to switch focus. (Needed for key handling.)
        @Override
        public void mouseClicked(MouseEvent e) {
            Manager.this.requestFocusInWindow();
        }
    }

    //helps to make a process add object to assign it to a button.
    private class ProcessAdd extends AbstractAction {
        ProcessAdd() {
            super("Add Patient");
        }
        //MODIFIES:this
        //EFFECTS: helps to make a process add object to assign it to a button.

        @Override
        public void actionPerformed(ActionEvent e) {
            processAdd();


        }
    }




    // helps to make a process rename object to assign it to a button.

    private class ProcessRename extends AbstractAction {
        ProcessRename() {
            super("Rename Patient");
        }
        //MODIFIES:this
        //EFFECTS: helps to make a process rename object to assign it to a button.

        @Override
        public void actionPerformed(ActionEvent e) {
            processRename();

        }
    }

    //helps to make a process remove object to assign it to a button.

    private class ProcessRemove extends AbstractAction {
        ProcessRemove() {
            super("Remove Patient");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a process remove object to assign it to a button.

        @Override
        public void actionPerformed(ActionEvent e) {
            processRemove();

        }
    }


    //helps to make a process search object to assign it to a button.
    private class ProcessSearch extends AbstractAction {
        ProcessSearch() {
            super("Search Patient");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a process search object to assign it to a button.
        @Override
        public void actionPerformed(ActionEvent e) {
            processSearch();

        }
    }

    //helps to make a process find conditions object to assign it to a button.
    private class ProcessFindConditions extends AbstractAction {
        ProcessFindConditions() {
            super("Find Conditions");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a process find conditions object to assign it to a button.
        @Override
        public void actionPerformed(ActionEvent e) {
            processConditions();

        }
    }


    //helps to make a find doctor object to assign it to a button.

    private class ProcessFindDoctors extends AbstractAction {
        ProcessFindDoctors() {
            super("Find Doctors");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a find doctor object to assign it to a button.
        @Override
        public void actionPerformed(ActionEvent e) {
            processFindDoctors();


        }
    }


    //helps to make a process empty bed object to assign it to a button.

    private class ProcessEmptyBeds extends AbstractAction {
        ProcessEmptyBeds() {
            super("Find Number of empty beds");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a process empty bed object to assign it to a button.
        @Override
        public void actionPerformed(ActionEvent e) {
            findNumberOfEmptyBeds();

        }
    }

    //  make a process load object to assign it to a button.

    private class ProcessLoad extends AbstractAction {
        ProcessLoad() {
            super("Load Patient");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a process load object to assign it to a button.
        @Override
        public void actionPerformed(ActionEvent e) {
            processLoad();

        }
    }

    // helps to make a process save object to assign it to a button.

    private class ProcessSave extends AbstractAction {
        ProcessSave() {
            super("Save Patient");
        }

        //MODIFIES:this
        //EFFECTS: helps to make a process save object to assign it to a button.
        @Override
        public void actionPerformed(ActionEvent e) {
            processSave();

        }
    }


    //EFFECTS: Converts the patient information into a 2d array for the table

    public Object[][] patientsToArray() {
        Object[][] result = new Object[mainManagement.getPatients().size()][4];
        int i = 0;
        for (Patient p : mainManagement.getPatients()) {

            result[i][0] = p.getName();
            result[i][1] = p.getDob();
            result[i][2] = p.getDoctor();
            result[i][3] = p.getCondition();
            i++;
        }
        return result;
    }

    // models the table and formats it.

    class TableModel extends AbstractTableModel {
        private final String[] columnNames = {"Name",
                "DOB",
                "Doctor",
                "Conditions"};

        private final Object[][] data = patientsToArray();

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
    }

    //EFFECTS: JTable uses this method to determine the default renderer
    // editor for each cell.

    public Class getColumnClass(int c) {
        return table.getValueAt(0, c).getClass();
    }

}

