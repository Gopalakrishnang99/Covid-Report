package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadPDF
 */
public class DownloadPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadPDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ext="";
		String userfilename="";
		String fileLocation = "F:\\Statedata.pdf";
		String choice=request.getParameter("name");
		System.out.println(choice);
		if(choice.equals("sp")) {
			fileLocation="F:\\Statedata.pdf";
			userfilename="Statedata";
			ext=".pdf";
		}
		else if(choice.equals("sc")) {
			ext=".csv";
			fileLocation="F:\\Statedata_Export.csv";
			userfilename="Statedata";
		}
		else if(choice.equals("tp")) {
			ext=".pdf";
			fileLocation="F:\\Timelinedata.pdf";
			userfilename="Timelinedata";
		}
		else if(choice.equals("tc")) {
			ext=".csv";
			fileLocation="F:\\Timeline_Export.csv";
			userfilename="Timelinedata";
		}
		String flname=getFileName(userfilename,ext);
		File file = new File(fileLocation);
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream sos = response.getOutputStream();
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + flname);
				
		byte[] buffer = new byte[4096];
 
		while((fis.read(buffer, 0, 4096)) != -1){
			sos.write(buffer, 0, 4096);
		}
		
		fis.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static String getFileName(String baseName, String ext) {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	    String dateTimeInfo = dateFormat.format(new Date());
	    String temp= baseName.concat(String.format("_%s", dateTimeInfo));
	    return temp+ext;
	}

}
