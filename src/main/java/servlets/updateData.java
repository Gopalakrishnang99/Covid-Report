package servlets;

import java.awt.Font;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import data.ConnectionPool;
import data.TableDataBean;

/**
 * Servlet implementation class updateData
 */
public class updateData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<TableDataBean> arr=new ArrayList<TableDataBean>();
		ArrayList<TableDataBean> itr=(ArrayList<TableDataBean>) request.getSession().getAttribute("tableData");
		for(TableDataBean i:itr) {
			TableDataBean temp=new TableDataBean();
			if(request.getParameter(i.getStateName())==null)
				break;
			temp.setStateName(request.getParameter(i.getStateName()));
			temp.setTotalCases(Integer.parseInt(request.getParameter(i.getStateName()+"ToT")));
			temp.setRecoveredCases(Integer.parseInt(request.getParameter(i.getStateName()+"ReC")));
			temp.setDeathCases(Integer.parseInt(request.getParameter(i.getStateName()+"DeA")));
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
	
	public static void updateDB(ArrayList<TableDataBean> inp) {
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			for(TableDataBean i:inp) {
			String preparedSQL="UPDATE covidindia SET totalcases=? , recovered=? , deaths=? WHERE state=?";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setInt(1, i.getTotalCases());
			ps.setInt(2, i.getRecoveredCases());
			ps.setInt(3, i.getDeathCases());
			ps.setString(4, i.getStateName());
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
        	String flname="F:\\Statedata.pdf";
			PdfWriter.getInstance(my_pdf_report, new FileOutputStream(flname));
			my_pdf_report.open(); 
			 PdfPTable my_report_table = new PdfPTable(6);
			 PdfPCell table_cell;
			 Statement stmt=con.createStatement();
	         ResultSet rs=stmt.executeQuery("SELECT * FROM covidindia");
	         table_cell=new PdfPCell(new Phrase("State"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Total cases"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Recovered cases"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Deaths"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Recovery rate"));
             my_report_table.addCell(table_cell);
             table_cell=new PdfPCell(new Phrase("Death rate"));
             my_report_table.addCell(table_cell);
	         while (rs.next()) {                
                 String statename = rs.getString(1);
                 table_cell=new PdfPCell(new Phrase(statename));
                 my_report_table.addCell(table_cell);
                 Integer totalcount=rs.getInt(2);
                 table_cell=new PdfPCell(new Phrase(totalcount.toString()));
                 my_report_table.addCell(table_cell);
                 Integer recovered=rs.getInt(3);
                 table_cell=new PdfPCell(new Phrase(recovered.toString()));
                 my_report_table.addCell(table_cell);
                 Integer deaths=rs.getInt(4);
                 table_cell=new PdfPCell(new Phrase(deaths.toString()));
                 my_report_table.addCell(table_cell);
                 Float recoveryrate=rs.getFloat(5);
                 table_cell=new PdfPCell(new Phrase(recoveryrate.toString()));
                 my_report_table.addCell(table_cell);
                 Float deathrate=rs.getFloat(6);
                 table_cell=new PdfPCell(new Phrase(deathrate.toString()));
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
		exporter.export("F:\\Statedata","covidindia");
	}


}
