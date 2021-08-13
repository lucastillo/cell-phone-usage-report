package models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.Month;
import java.time.Year;


/**
 * Created by lcastillo on 8/12/21
 */
public class CellPhoneUsage {

    
    private int employeeId;

    private int year;

    private Month month;

    private int totalMinutes;

    private double totalData;

    public CellPhoneUsage() {
    }

    public CellPhoneUsage( int employeeId, int year, Month month, int totalMinutes, double totalData ) {
        this.employeeId = employeeId;
        this.year = year;
        this.month = month;
        this.totalMinutes = totalMinutes;
        this.totalData = totalData;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId( int employeeId ) {
        this.employeeId = employeeId;
    }

    public int getYear() {
        return year;
    }

    public void setYear( int year ) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth( Month month ) {
        this.month = month;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes( int totalMinutes ) {
        this.totalMinutes = totalMinutes;
    }

    public double getTotalData() {
        return totalData;
    }

    public void setTotalData( double totalData ) {
        this.totalData = totalData;
    }

    @Override
    public String toString() {
        return "CellPhoneUsage{" +
                "employeeId=" + employeeId +
                ", year=" + year +
                ", month=" + month +
                ", totalMinutes=" + totalMinutes +
                ", totalData=" + totalData +
                '}';
    }
}
