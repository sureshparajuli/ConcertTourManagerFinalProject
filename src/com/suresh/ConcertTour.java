package com.suresh;

import java.util.Date;
//concert tour class to store concert information
public class ConcertTour {
    private int id = 0;
    private String concertPerformerName;
    private Date concertDate;
    private String concertVenue;
    private String concertCity;
    private double concertTotalBudget;
    private double concertWagesForCrew;
    private double concertTotalRevenue;
    private double distanceTravelledForConcert;

    //default conctructor
    public ConcertTour(){}

    //create concert tour object with all parameters
    public ConcertTour(int id, String concertPerformerName
            , Date concertDate
            , String concertVenue
            , String concertCity
            , double concertTotalBudget
            , double concertWagesForCrew
            , double concertTotalRevenue, double distanceTravelledForConcert)
    {
        this.concertPerformerName = concertPerformerName;
        this.concertDate = concertDate;
        this.concertVenue = concertVenue;
        this.concertCity = concertCity;
        this.concertTotalBudget = concertTotalBudget;
        this.concertWagesForCrew = concertWagesForCrew;
        this.concertTotalRevenue = concertTotalRevenue;
        this.distanceTravelledForConcert = distanceTravelledForConcert;
    }

    //get and set methods
    public int getId()
    {
        return this.id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getConcertPerformerName()
    {
        return this.concertPerformerName;
    }
    public Date getConcertDate()
    {
        return this.concertDate;
    }

    public String getConcertVenue()
    {
        return this.concertVenue;
    }
    public String getConcertCity()
    {
        return this.concertCity;
    }
    public double getConcertTotalBudget()
    {
        return this.concertTotalBudget;
    }
    public double getConcertWagesForCrew()
    {
        return this.concertWagesForCrew;
    }
    public double getConcertTotalRevenue()
    {
        return this.concertTotalRevenue;
    }
    public double getDistanceTravelledForConcert()
    {
        return this.distanceTravelledForConcert;
    }
}















