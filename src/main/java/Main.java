import com.itextpdf.text.DocumentException;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import models.CellPhoneUsage;
import models.Employee;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import services.ReportUtil;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by lcastillo on 8/12/21
 */
public class Main {

    public static final String CELL_PHONE_CSV = "src/main/resources/CellPhone.csv";
    public static final String CELL_PHONE_USAGE_BY_MONTH_CSV = "src/main/resources/CellPhoneUsageByMonth.csv";

    public static void main( String[] args ) throws IOException, CsvException, DocumentException, PrinterException {
        System.out.println("Please enter the year:");
        Scanner scanner = new Scanner(System.in);
        int year = Integer.parseInt( scanner.nextLine() );
        Main printer = new Main();
        List<Employee> employees = new CsvToBeanBuilder(new FileReader( CELL_PHONE_CSV ))
                .withType(Employee.class)
                .build()
                .parse();

        List<String[]> data = new CSVReader(new FileReader( CELL_PHONE_USAGE_BY_MONTH_CSV )).readAll();
        List<CellPhoneUsage> cellPhoneUsages = new ArrayList<>();

        for (String[] row : data.subList( 1, data.size() )) {
            cellPhoneUsages.add( new CellPhoneUsage(Integer.parseInt( row[0] ),
                    Integer.parseInt(row[1].split( "/" )[2]),
                    Month.of( Integer.parseInt( row[1].split( "/" )[0] ) ),
                    Integer.parseInt( row[2] ), Double.parseDouble( row[3] )));
        }

        ByteArrayOutputStream report = ReportUtil.generateReport( employees, cellPhoneUsages, year );
        try{

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Available Printers:");

        for (PrintService printService : printServices) {
            System.out.println("\t" + printService.getName());
        }

        printer.print( report );
        }catch ( Exception e ){
            System.out.println("There are not available printers\n" +
                    "Please check the resources directory to see the pdf");
        }
    }

    public void print( ByteArrayOutputStream documentBytes) throws IOException, PrinterException {

        ByteArrayInputStream bytes = new ByteArrayInputStream(documentBytes.toByteArray());

        PDDocument document = PDDocument.load(bytes);

        PrintService myPrintService = this.findPrintService();
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        printerJob.setPageable(new PDFPageable(document));
        printerJob.setPrintService(myPrintService);

        printerJob.print();

    }

    private PrintService findPrintService() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        return printServices[0];
    }
}
