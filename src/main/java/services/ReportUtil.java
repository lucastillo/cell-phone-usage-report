package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.CellPhoneUsage;
import models.DetailsSection;
import models.Employee;
import models.HeaderSection;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lcastillo on 8/12/21
 */
public class ReportUtil {

    public static HeaderSection getHeaderSection( List<Employee> employees, List<CellPhoneUsage> cellPhoneUsages, int year ){
        cellPhoneUsages = cellPhoneUsages.stream().filter( cellPhoneUsage -> cellPhoneUsage.getYear() == year )
                .collect( Collectors.toList());
        HeaderSection headerSection = new HeaderSection();
        headerSection.setRunDate( LocalDate.now() );
        headerSection.setAverageMinutes(cellPhoneUsages.stream()
                .mapToInt( CellPhoneUsage::getTotalMinutes ).average().orElse(0.0));
        headerSection.setAverageData( cellPhoneUsages.stream()
                .mapToDouble(CellPhoneUsage::getTotalData).average().orElse(0.0) );
        headerSection.setNumberOfPhones( employees.size() );
        headerSection.setTotalOfMinutes( cellPhoneUsages.stream()
                .mapToInt( CellPhoneUsage::getTotalMinutes ).sum() );
        headerSection.setTotalOfData( cellPhoneUsages.stream()
                .mapToDouble(CellPhoneUsage::getTotalData).sum() );
        return headerSection;
    }

    public static List<Integer> getMinutesByMonth( Employee employee, List<CellPhoneUsage> cellPhoneUsages ){
        cellPhoneUsages = cellPhoneUsages.stream()
                .filter( cellPhoneUsage -> cellPhoneUsage.getEmployeeId() == employee.getEmployeeId() )
                .collect( Collectors.toList());
        List<Integer> monthInfo = new ArrayList<>();
        for ( Month value : Month.values() ) {
            monthInfo.add( cellPhoneUsages.stream()
                    .filter( cellPhoneUsage -> cellPhoneUsage.getMonth().equals( value ) )
                    .mapToInt( CellPhoneUsage::getTotalMinutes ).sum());
        }
        return monthInfo;
    }

    public static List<DetailsSection> getDetailsSection( List<Employee> employees, List<CellPhoneUsage> cellPhoneUsages,
                                                          int year ){
        cellPhoneUsages = cellPhoneUsages.stream()
                .filter( cellPhoneUsage -> cellPhoneUsage.getYear() == year )
                .collect( Collectors.toList());
        List<DetailsSection> detailsSection = new ArrayList<>();
        for ( Employee employee : employees ) {
            detailsSection.add( new DetailsSection( employee, getMinutesByMonth( employee, cellPhoneUsages ) ) );
        }
        return detailsSection;
    }

    public static ByteArrayOutputStream generateReport(List<Employee> employees, List<CellPhoneUsage> cellPhoneUsages
            , int year) throws FileNotFoundException, DocumentException {
        HeaderSection headerSection = getHeaderSection( employees, cellPhoneUsages, year );
        List<DetailsSection> detailsSection = getDetailsSection( employees, cellPhoneUsages, year );

        ByteArrayOutputStream documentBytes = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, documentBytes);
        PdfWriter.getInstance(document, new FileOutputStream( "src/main/resources/Report.pdf" ));
        document.open();

        PdfPTable headerTable = new PdfPTable(6);
        addTableHeader(headerTable, List.of("Report Run Date",
                "Number of Phones",
                "Total Minutes",
                "Total Data",
                "Average Minutes",
                "Average Data"));
        headerTable.addCell(headerSection.getRunDate().toString());
        headerTable.addCell( String.valueOf( headerSection.getNumberOfPhones() ) );
        headerTable.addCell( String.valueOf( headerSection.getTotalOfMinutes() ) );
        headerTable.addCell( String.valueOf( headerSection.getTotalOfData() ) );
        headerTable.addCell( String.valueOf( headerSection.getAverageMinutes() ) );
        headerTable.addCell( String.valueOf( headerSection.getAverageData() ) );
        document.add(headerTable);

        PdfPTable detailsTable = new PdfPTable(6);
        List<String> detailsHeader = new ArrayList<>( List.of( "Employee Id",
                "Employee Name",
                "Model",
                "Purchase Date" ) );
        Arrays.stream( Month.values() ).forEach( month -> detailsHeader.add( "Minutes  "+month ) );
        Arrays.stream( Month.values() ).forEach( month -> detailsHeader.add( "Data  "+month ) );
        addTableHeader(detailsTable, detailsHeader);
        addRows(detailsTable, detailsSection);
        document.add(detailsTable);
        document.close();

        return documentBytes;
    }

    private static void addTableHeader( PdfPTable table, List<String> headerNames ) {
        headerNames.forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor( BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows( PdfPTable table, List<DetailsSection> detailsSections ) {
        detailsSections.forEach( detailsSection -> {
            table.addCell( String.valueOf( detailsSection.getEmployee().getEmployeeId() ) );
            table.addCell(detailsSection.getEmployee().getEmployeeName());
            table.addCell(detailsSection.getEmployee().getModel());
            table.addCell(detailsSection.getEmployee().getPurchaseDate().toString());
            detailsSection.getMontInfo().forEach( month -> table.addCell( String.valueOf( month ) ) );
        } );

    }
}
