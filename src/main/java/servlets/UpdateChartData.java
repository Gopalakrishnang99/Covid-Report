package servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import data.CSVexporter;
import data.ChartAreaData;
import data.ConnectionPool;
import data.TableDataBean;

/**
 * Servlet implementation class UpdateChartData
 */
public class UpdateChartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateChartData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ChartAreaData> arr=new ArrayList<ChartAreaData>();
		ArrayList<ChartAreaData> itr=(ArrayList<ChartAreaData>) request.getSession().getAttribute("chartData");
		for(ChartAreaData i:itr) {
			ChartAreaData temp=new ChartAreaData();
			String id=i.getYear().toString()+i.getMonth();
			if(request.getParameter(id)==null)
				break;
			temp.setTotalCases(Integer.parseInt(request.getParameter(id+"ToT")));
			temp.setRecovered(Integer.parseInt(request.getParameter(id+"ReC")));
			temp.setDeathCases(Integer.parseInt(request.getParameter(id+"DeA")));
			temp.setYear(i.getYear());
			temp.setMonth(i.getMonth());
			arr.add(temp);
		}
		updateDB(arr);
		generatePDF();
		generateCSV();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void updateDB(ArrayList<ChartAreaData> inp) {
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			for(ChartAreaData i:inp) {
			String preparedSQL="UPDATE covidtimeline SET noofcases=? , recovered=? , death=? WHERE (year=? AND month=?)";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setInt(1, i.getTotalCases());
			ps.setInt(2, i.getRecovered());
			ps.setInt(3, i.getDeathCases());
			ps.setInt(4, i.getYear());
			ps.setString(5, i.getMonth());
			ps.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public static void generatePDF() {
		Document my_pdf_report = new Document();
        try (Connection con=ConnectionPool.getInstance().getConnection();){
        	String flname="F:\\Timelinedata.pdf";
			PdfWriter.getInstance(my_pdf_report, new FileOutputStream(flname));
			my_pdf_report.open(); 
			 PdfPTable my_report_table = new PdfPTable(5);
			 PdfPCell table_cell;
			 Statement stmt=con.createStatement();
	         ResultSet rs=stmt.executeQuery("SELECT * FROM covidtimeline");
	         table_cell=new PdfPCell(new Phrase("Year"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Month"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Total cases"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Recovered"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Death"));
             my_report_table.addCell(table_cell);
	         while (rs.next()) {   
	        	 Integer yr=rs.getInt(2);
                 String Year = yr.toString();
                 table_cell=new PdfPCell(new Phrase(Year));
                 my_report_table.addCell(table_cell);
                 
                 String month=rs.getString(3);
                 table_cell=new PdfPCell(new Phrase(month));
                 my_report_table.addCell(table_cell);
                 
                 Integer totalcases=rs.getInt(4);
                 table_cell=new PdfPCell(new Phrase(totalcases.toString()));
                 my_report_table.addCell(table_cell);
                 
                 Integer recovered=rs.getInt(5);
                 table_cell=new PdfPCell(new Phrase(recovered.toString()));
                 my_report_table.addCell(table_cell);
                 
                 Integer death=rs.getInt(6);
                 table_cell=new PdfPCell(new Phrase(death.toString()));
                 my_report_table.addCell(table_cell);
               
                 }
	         my_pdf_report.add(my_report_table);                       
             my_pdf_report.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
	}
	
	public static void generateCSV() {
		CSVexporter exporter = new CSVexporter();
		exporter.export("F:\\Timeline","covidtimeline");
	}

}
