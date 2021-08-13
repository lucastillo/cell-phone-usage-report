package models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;

/**
 * Created by lcastillo on 8/12/21
 */
public class Employee {

    @CsvBindByName
    private int employeeId;

    @CsvBindByName
    private String employeeName;

    @CsvBindByName
    @CsvDate("yyyyMMdd")
    private LocalDate purchaseDate;

    @CsvBindByName
    private String model;

    public Employee() {
    }

    public Employee( int employeeId, String employeeName, LocalDate purchaseDate, String model ) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.purchaseDate = purchaseDate;
        this.model = model;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId( int employeeId ) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName( String employeeName ) {
        this.employeeName = employeeName;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate( LocalDate purchaseDate ) {
        this.purchaseDate = purchaseDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel( String model ) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", model='" + model + '\'' +
                '}';
    }
}
