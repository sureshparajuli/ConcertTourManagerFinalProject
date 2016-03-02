package com.suresh;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;

//class for concert tour manager UI
public class ConcertTourManager extends JFrame{
    private JPanel rootPanel;
    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JPanel textFieldPanel;
    private JPanel messagePanel;
    private JLabel messageLabel;
    private JTextField textFieldPerformer;
    private JTextField textFieldDateTime;
    private JTextField textFieldConcertVenue;
    private JTextField textFieldConcertCity;
    private JTextField textFieldEstimatedExpense;
    private JTextField textFieldConcertWages;
    private JTextField textFieldDistanceTravelled;
    private JButton buttonAddConcert;
    private JButton quitButton;
    private JTextField textFieldEstimatedRevenue;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";//database driver
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/concert";//connection string to the test database in localhost

    static final String USER = "suresh";   //database login user name
    static final String PASSWORD = "Mysqlpwd1*";   //password for login

    static Connection connection = null;//define connection string
    static Statement statement = null;//define statement
    static PreparedStatement psAddConcertTour = null;//define statement
    static ResultSet rs = null;//define result set

    LinkedList<Statement> allStatements = new LinkedList();

    //constructor for the ConcertTourManager
    public ConcertTourManager() {
        super("Concert Tour Manager Application");//calls super class on JFrame with window name
        setContentPane(rootPanel);//set the rootPanel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exists application on window close
        setSize(700, 600);//set the size of the UI window
        setResizable(false);//make it not resizable
        setFocusable(true);
        setVisible(true);//display the window

        //load jdbc driver
        try {
            //Instantiate the driver
            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException cnfe) {
            messageLabel.setText("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

        //create database connection
        try {
            createConnection();
        } catch (SQLException e) {
            messageLabel.setText("Unable to connect to database. Error message and stack trace follow");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        //action listener for add concert button
        buttonAddConcert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //add the new concert tour information
                addNewConcertTour();
            }
        });

        quitButton.addActionListener(new ActionListener() {//action listener to capture quit button
            @Override
            public void actionPerformed(ActionEvent e) {
                //call cleanup
                cleanup();
                //clicked quit application button
                System.exit(0);
            }
        });
    }

    //add new concert tour
    private void addNewConcertTour(){
        //Create SQL query to add this laptop info to DB

        String performer_name = textFieldPerformer.getText().trim();
        //LocalDateTime concert_datetime;
        Date concert_dt;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            concert_dt = df.parse(textFieldDateTime.getText().trim());
        }catch (ParseException ex)
        {
            messageLabel.setText("Please enter valid date in MM/dd/yyyy HH:mm format");
            return;
        }
        java.sql.Date concert_datetime = new java.sql.Date(concert_dt.getTime());

        String concert_venue = textFieldConcertVenue.getText().trim();
        String concert_city = textFieldConcertCity.getText().trim();

        double total_budget;
        try {
            total_budget = Double.parseDouble(textFieldEstimatedExpense.getText().trim());
        }catch(NumberFormatException nfe)
        {
            messageLabel.setText("Please enter numeric for estimated expense");
            return;
        }

        double total_wages_for_crew;
        try {
            total_wages_for_crew = Double.parseDouble(textFieldConcertWages.getText().trim());
        }catch(NumberFormatException nfe)
        {
            messageLabel.setText("Please enter numeric for wages expense");
            return;
        }

        double total_revenue;
        try {
            total_revenue = Double.parseDouble(textFieldEstimatedRevenue.getText().trim());
        }catch(NumberFormatException nfe)
        {
            messageLabel.setText("Please enter numeric for estimated revenue");
            return;
        }

        double distance_travelled;
        try {
            distance_travelled = Double.parseDouble(textFieldDistanceTravelled.getText().trim());
        }catch(NumberFormatException nfe)
        {
            messageLabel.setText("Please enter numeric for distance to travel");
            return;
        }

        String addNewConcertTourSQLps = "insert into concert_tour (performer_name, concert_datetime, concert_venue, concert_city, total_budget" +
                ", total_wages_for_crew, total_revenue, distance_travelled) VALUES ( ? , ? , ?, ?, ?, ?, ?, ?)" ;

        try {
            psAddConcertTour = connection.prepareStatement(addNewConcertTourSQLps, psAddConcertTour.RETURN_GENERATED_KEYS);
            allStatements.add(psAddConcertTour);
            psAddConcertTour.setString(1, performer_name);
            psAddConcertTour.setDate(2, concert_datetime);
            psAddConcertTour.setString(3, concert_venue);
            psAddConcertTour.setString(4, concert_city);
            psAddConcertTour.setDouble(5, total_budget);
            psAddConcertTour.setDouble(6, total_wages_for_crew);
            psAddConcertTour.setDouble(7, total_revenue);
            psAddConcertTour.setDouble(8, distance_travelled);
            psAddConcertTour.execute();

            //Retrieve new concert tour info id and add it to the convert tour so calling methods can use it.
            ResultSet keys = psAddConcertTour.getGeneratedKeys();
            //We assume just one key, which will be the first thing in the ResultSet
            keys.next();
            //ConcertTour concertTour = new ConcertTour();
            //int concertTourID = keys.getInt(1);
            //concertTour.setId(concertTourID);

        }
        catch (SQLException sqle) {
            String errorMessage = "Error preparing statement or executing prepared statement to add laptop";
            messageLabel.setText(errorMessage);
        }
    }

    //create connection to concert database so it can be used to retrive and store concert information
    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        statement = connection.createStatement();
        allStatements.add(statement);
    }

    //cleanup before close
    public void cleanup() {
        try {
            if (rs != null) {
                rs.close();  //Close result set
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        //Close all of the statements. Stored a reference to each statement in allStatements so we can loop over all of them and close them all.
        for (Statement s : allStatements) {

            if (s != null) {
                try {
                    s.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }

        try {
            if (connection != null) {
                connection.close();  //Close connection to database
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}



















