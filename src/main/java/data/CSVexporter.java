package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVexporter {
	 private BufferedWriter fileWriter;
	 public void export(String table, String name) {
		 String csvFileName = table.concat("_Export.csv");
		 try(Connection con=ConnectionPool.getInstance().getConnection()){
			 Statement stmt=con.createStatement();
	         ResultSet rs=stmt.executeQuery("SELECT * FROM "+name);
	         fileWriter = new BufferedWriter(new FileWriter(csvFileName));
	         int columnCount = writeHeaderLine(rs);
	         while (rs.next()) {
	                String line = "";
	                 
	                for (int i = 1; i <= columnCount; i++) {
	                    Object valueObject = rs.getObject(i);
	                    String valueString = "";
	                    if (valueObject != null) valueString = valueObject.toString();
	                    System.out.println(valueString);
	                    line = line.concat(valueString);
	                    if (i != columnCount) {
	                        line = line.concat(",");
	                    }
	                }
	                System.out.println(line);
	                 
	                fileWriter.newLine();
	                fileWriter.write(line);            
	            }
	         fileWriter.close();
	             
		 }
		 catch(SQLException e) {
			 System.out.println(e);
		 }
		 catch(IOException e) {
			 System.out.println(e);
		 }
	 }
	
	
	 private String getFileName(String baseName) {
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		    String dateTimeInfo = dateFormat.format(new Date());
		    return baseName.concat(String.format("_%s.csv", dateTimeInfo));
		}
	 
	 private int writeHeaderLine(ResultSet result) throws SQLException, IOException {
	        // write header line containing column names
	        ResultSetMetaData metaData = result.getMetaData();
	        int numberOfColumns = metaData.getColumnCount();
	        String headerLine = "";
	         
	        // exclude the first column which is the ID field
	        for (int i = 1; i <= numberOfColumns; i++) {
	            String columnName = metaData.getColumnName(i);
	            System.out.println(columnName);
	            headerLine = headerLine.concat(columnName).concat(",");
	        }
	         
	        fileWriter.write(headerLine.substring(0, headerLine.length() - 1));
	         
	        return numberOfColumns;
	    }
	
	
	
}

