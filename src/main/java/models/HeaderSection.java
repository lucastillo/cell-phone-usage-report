package models;

import java.time.LocalDate;

/**
 * Created by lcastillo on 8/12/21
 */
public class HeaderSection {
    private LocalDate runDate;
    private int numberOfPhones;
    private long totalOfMinutes;
    private double totalOfData;
    private double averageMinutes;
    private double averageData;

    public HeaderSection() {
    }

    public HeaderSection( LocalDate runDate, int numberOfPhones, long totalOfMinutes, double totalOfData, double averageMinutes, double averageData ) {
        this.runDate = runDate;
        this.numberOfPhones = numberOfPhones;
        this.totalOfMinutes = totalOfMinutes;
        this.totalOfData = totalOfData;
        this.averageMinutes = averageMinutes;
        this.averageData = averageData;
    }

    public LocalDate getRunDate() {
        return runDate;
    }

    public void setRunDate( LocalDate runDate ) {
        this.runDate = runDate;
    }

    public int getNumberOfPhones() {
        return numberOfPhones;
    }

    public void setNumberOfPhones( int numberOfPhones ) {
        this.numberOfPhones = numberOfPhones;
    }

    public long getTotalOfMinutes() {
        return totalOfMinutes;
    }

    public void setTotalOfMinutes( long totalOfMinutes ) {
        this.totalOfMinutes = totalOfMinutes;
    }

    public double getTotalOfData() {
        return totalOfData;
    }

    public void setTotalOfData( double totalOfData ) {
        this.totalOfData = totalOfData;
    }

    public double getAverageMinutes() {
        return averageMinutes;
    }

    public void setAverageMinutes( double averageMinutes ) {
        this.averageMinutes = averageMinutes;
    }

    public double getAverageData() {
        return averageData;
    }

    public void setAverageData( double averageData ) {
        this.averageData = averageData;
    }
}
