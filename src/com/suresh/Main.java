package com.suresh;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new  ConcertTourManager();//run concert tour manager ui
            }
        });
    }
}
