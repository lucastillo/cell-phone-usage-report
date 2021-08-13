package models;

import java.time.Month;
import java.util.List;
import java.util.Map;

/**
 * Created by lcastillo on 8/12/21
 */
public class DetailsSection {
    private Employee employee;
    private List<Integer> montInfo;

    public DetailsSection( Employee employee, List<Integer> montInfo ) {
        this.employee = employee;
        this.montInfo = montInfo;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee( Employee employee ) {
        this.employee = employee;
    }

    public List<Integer> getMontInfo() {
        return montInfo;
    }

    public void setMontInfo( List<Integer> montInfo ) {
        this.montInfo = montInfo;
    }
}
