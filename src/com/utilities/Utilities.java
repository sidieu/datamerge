/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author smuyila
 */
public class Utilities {
    
    private static final String FILE_PATH = Config.getConfig("file.path");
    private static final String JSON_FILE_PATH = FILE_PATH+Config.getConfig("json.file");
    private static final String XML_FILE_PATH = FILE_PATH+Config.getConfig("xml.file");
    private static final String TEMP_CSV_FILE_PATH = FILE_PATH+Config.getConfig("temp.csv.file");
    private static final String FINAL_CSV_FILE_PATH = FILE_PATH+Config.getConfig("final.csv.file");
    
    public static void CSVReader(String filePath,String type) throws IOException {
  
       Report report = null;
       List<Report> reports = new ArrayList();
        try (
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        ) {
             int i = 0;
            for (CSVRecord csvRecord : csvParser) {
                String clientaddress = csvRecord.get("client-address");
                String clientguid = csvRecord.get("client-guid");
                String requesttime = csvRecord.get("request-time");
                String serviceguid = csvRecord.get("service-guid");
                String retriesrequest = csvRecord.get("retries-request");
                String packetsrequested = csvRecord.get("packets-requested");
                String packetsserviced = csvRecord.get("packets-serviced");
                String maxholesize = csvRecord.get("max-hole-size");
                
                if(packetsserviced.equals("0")){
                 // Skip this line
                    }else { 
                    // Write data to the combined CSV file
                    report = new Report();
                    report.setClientaddress(clientaddress);
                    report.setClientguid(clientguid);
                    report.setRequesttime(requesttime);
                    report.setServiceguid(serviceguid);
                    report.setRetriesrequest(retriesrequest);
                    report.setPacketsrequested(packetsrequested);
                    report.setPacketsserviced(packetsserviced);
                    report.setMaxholesize(maxholesize);
                    reports.add(report);
                        ++i;
                    }

            }
            
           Collections.sort(reports, new Comparator<Report>() {
            public int compare(Report o1, Report o2) {
                return o1.getRequesttime().compareTo(o2.getRequesttime());
            }
        });
                if(type.equals("temp")){
                    File file = new File(FINAL_CSV_FILE_PATH); 
                    file.delete();
                    CSVWriter(reports,TEMP_CSV_FILE_PATH);
                }else {
                    CSVWriter(reports,FINAL_CSV_FILE_PATH);
                    System.out.print("===============SUMMARY===============\n");
                    System.out.print("client-guid      =           "+i+"\n");
                }
              

            
        }
    }
    
  
 public static void CSVWriter(List<Report> report, String filePath) throws IOException {
        CSVPrinter jcdCSVPrinter;
         
        try
        {
            Writer jcdWriter = Files.newBufferedWriter(Paths.get(filePath),StandardOpenOption.APPEND,StandardOpenOption.CREATE);
            //add column header names
            jcdCSVPrinter = new CSVPrinter(jcdWriter, CSVFormat.DEFAULT.withHeader("client-address","client-guid","request-time","service-guid","retries-request","packets-requested","packets-serviced","max-hole-size"));
            //Add records to the csv file
            
              for (Report reports : report) {
                jcdCSVPrinter.printRecord(
                        reports.getClientaddress(),
                        reports.getClientguid(),
                        reports.getRequesttime(),
                        reports.getServiceguid(),
                        reports.getRetriesrequest(),
                        reports.getPacketsrequested(),
                        reports.getPacketsserviced(),
                        reports.getMaxholesize()
                );
            }

            jcdCSVPrinter.flush();
             
            jcdCSVPrinter.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Exception: "+e.toString());
        }
    }
 
 public static void CSVWriterWithoutHeader(List<Report> report) throws IOException {
        CSVPrinter jcdCSVPrinter;
         
        try
        {
            Writer jcdWriter = Files.newBufferedWriter(Paths.get(TEMP_CSV_FILE_PATH),StandardOpenOption.APPEND,StandardOpenOption.CREATE);
            //add column header names
            jcdCSVPrinter = new CSVPrinter(jcdWriter, CSVFormat.DEFAULT.withSkipHeaderRecord(true));
            //Add records to the csv file
            
              for (Report reports : report) {
                jcdCSVPrinter.printRecord(
                        reports.getClientaddress(),
                        reports.getClientguid(),
                        reports.getRequesttime(),
                        reports.getServiceguid(),
                        reports.getRetriesrequest(),
                        reports.getPacketsrequested(),
                        reports.getPacketsserviced(),
                        reports.getMaxholesize()
                );
            }

            jcdCSVPrinter.flush();
            jcdCSVPrinter.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Exception: "+e.toString());
        }
    }
 

    public static void JSONReader() throws IOException {
       JSONParser jsonParser = new JSONParser();
       StringBuilder sb = new StringBuilder();
       
       Report report = null;
       List<Report> reports = new ArrayList();
       
        try (FileReader reader = new FileReader(JSON_FILE_PATH))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            //JSONArray repotsList = (JSONArray) obj;   
            JSONArray reportsdata = (JSONArray) obj;
            Iterator<JSONObject> iterator = reportsdata.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject) iterator.next();
                 String clientaddress = jsonObject.get("client-address").toString();
                String clientguid = jsonObject.get("client-guid").toString();
                String requesttime = dateFormater(jsonObject.get("request-time").toString());
                String serviceguid = jsonObject.get("service-guid").toString();
                String retriesrequest = jsonObject.get("retries-request").toString();
                String packetsrequested = jsonObject.get("packets-requested").toString();
                String packetsserviced = jsonObject.get("packets-serviced").toString();
                String maxholesize = jsonObject.get("max-hole-size").toString();
                
                if(packetsserviced.equals("0")){
                 // Skip this line
                    } else { 
                    report = new Report();
                    report.setClientaddress(clientaddress);
                    report.setClientguid(clientguid);
                    report.setRequesttime(requesttime);
                    report.setServiceguid(serviceguid);
                    report.setRetriesrequest(retriesrequest);
                    report.setPacketsrequested(packetsrequested);
                    report.setPacketsserviced(packetsserviced);
                    report.setMaxholesize(maxholesize);
                    reports.add(report);
                    ++count;
                }
            }
            
            CSVWriterWithoutHeader(reports);
            //System.out.println("count: "+count);
          
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static void XMLReader() throws IOException {
        String filePath = XML_FILE_PATH;
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        Report report = null;
        List<Report> reports = new ArrayList();
       
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("report");
            //XML is loaded as Document in memory, lets convert it to Object List
            int recordNbre = 0;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                 if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    
                    if(getTagValue("packets-serviced", element).equals("0")){
                    // Skip this line
                    } else {
                        String clientaddress = getTagValue("client-address", element);
                        String clientguid = getTagValue("client-guid", element);
                        String requesttime = getTagValue("request-time", element);
                        String serviceguid = getTagValue("service-guid", element);
                        String retriesrequest = getTagValue("retries-request", element);
                        String packetsrequested = getTagValue("packets-requested", element);
                        String packetsserviced = getTagValue("packets-serviced", element);
                        String maxholesize = getTagValue("max-hole-size", element);
                        
                        report = new Report();
                        report.setClientaddress(clientaddress);
                        report.setClientguid(clientguid);
                        report.setRequesttime(requesttime);
                        report.setServiceguid(serviceguid);
                        report.setRetriesrequest(retriesrequest);
                        report.setPacketsrequested(packetsrequested);
                        report.setPacketsserviced(packetsserviced);
                        report.setMaxholesize(maxholesize);
                        reports.add(report);
                        ++recordNbre;
                    }
                    
                }
            }
            CSVWriterWithoutHeader(reports);
           // System.out.println("number of records client-guid :" + recordNbre);

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }
    
     private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
     
     private static String dateFormater(String dateIn) {
        long unixSeconds = Long.valueOf(dateIn);
        Date date = new java.util.Date(unixSeconds); 
        //format now your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
     
   
     
}
