/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamerge;

import com.utilities.Config;
import com.utilities.Utilities;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author smuyila
 */
public class Datamerge {

    /**
     * @param args the command line arguments
     */
    private static final String FILE_PATH = Config.getConfig("file.path");
    private static final String CSV_FILE_PATH = FILE_PATH+Config.getConfig("csv.file");
    private static final String TEMP_CSV_FILE_PATH = FILE_PATH+Config.getConfig("temp.csv.file");
    
    public static void main(String[] args) throws IOException {
              
                // Start Read CSV File, exclude all  packets-serviced equal to zero and output on CSV temp file.
              Utilities.CSVReader(CSV_FILE_PATH,"temp");
              // Start Read XML File, exclude all  packets-serviced equal to zero and output on CSV temp file.
              Utilities.XMLReader();
              // Start Read JSON File, exclude all  packets-serviced equal to zero, output on CSV temp file
              Utilities.JSONReader();
              // Start Read CSV temp File Sort by request-time in ascending order and output on CSV final combined file
              Utilities.CSVReader(TEMP_CSV_FILE_PATH,"final");
              // delete temp file
              File file = new File(TEMP_CSV_FILE_PATH); 
                if(file.delete()) 
                { 
                    System.out.println("Temp File deleted successfully"); 
                } 
                else
                { 
                    System.out.println("Failed to delete the Temp file"); 
                } 
    }
    
}
