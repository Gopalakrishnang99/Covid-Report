<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false"%>
    <%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>COVID-REPORTS</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

       <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="Homepage">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-shield-virus"></i>
        </div>
        <div class="sidebar-brand-text mx-3">COVID-19</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item">
        <a class="nav-link" href="Homepage">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Homepage</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Dataview
      </div>

      <!-- Nav Item - Tables -->
      <li class="nav-item">
        <a class="nav-link" href="Tables">
          <i class="fas fa-fw fa-table"></i>
          <span>Tables</span></a>
      </li>
      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="Charts">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Charts</span></a>
      </li>


      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Actions
      </div>

      <!-- Nav Item - Pass -->
      <li class="nav-item">
        <a class="nav-link" href="Pass">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Pass availability</span></a>
      </li>

      <!-- Nav Item - Report -->
      <li class="nav-item">
        <a class="nav-link" href="GenerateReport">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Generate report</span></a>
      </li>

      <!-- Nav Item - Login/Logout -->
      <c:if test='${sessionScope.userIdentity.emailId=="" }'>
      <li class="nav-item">
        <a class="nav-link" href="Login">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Login</span></a>
      </li>
      </c:if>
      <c:if test='${sessionScope.userIdentity.emailId!="" }'>
      <li class="nav-item">
        <a class="nav-link" href="Logout">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Logout</span></a>
      </li>
      </c:if>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

            <!-- Nav Item - Search Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                  <div class="input-group">
                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                      <button class="btn btn-primary" type="button">
                        <i class="fas fa-search fa-sm"></i>
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </li>

            
            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${sessionScope.userIdentity.firstName} ${sessionScope.userIdentity.lastName}</span>
              </a>
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-4 text-gray-800">Generate report files</h1>

          <div class="row row-cols-1">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Statewise data</h6>
                </div>
                <div class="card-body border-bottom-info text-gray-100">
                  <br>
                  <a id="statePDF" href="DownloadPDF?name=sp" class="btn btn-danger btn-icon-split">
                    <span class="icon text-white-50">
                      <i class="fas fa-download"></i>
                    </span>
                    <span class="text">Download PDF</span>
                  </a>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <a href="DownloadPDF?name=sc" class="btn btn-success btn-icon-split">
                    <span class="icon text-white-50">
                      <i class="fas fa-download"></i>
                    </span>
                    <span class="text">Download CSV</span>
                  </a>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <c:if test='${sessionScope.userIdentity.emailId!="" }'>
                  <a href="#" class="btn btn-danger btn-icon-split" id="stateDatamailPDF">
                    <span class="icon text-white-50">
                      <i class="fas fa-envelope-open"></i>
                    </span>
                    <span class="text">Mail PDF</span>
                  </a>
                  </c:if>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <c:if test='${sessionScope.userIdentity.emailId!="" }'>
                  <a href="#" class="btn btn-success btn-icon-split" id="stateDatamailCSV">
                    <span class="icon text-white-50">
                      <i class="fas fa-envelope-open"></i>
                    </span>
                    <span class="text">Mail CSV</span>
                  </a>
                  </c:if>
                  <br>
                </div>
              </div>
          </div>

          <div class="row row-cols-1">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Timeline data</h6>
                </div>
                <div class="card-body border-bottom-info text-gray-100">
                  <br>
                  <a href="DownloadPDF?name=tp" class="btn btn-danger btn-icon-split">
                    <span class="icon text-white-50">
                      <i class="fas fa-download"></i>
                    </span>
                    <span class="text">Download PDF</span>
                  </a>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <a href="DownloadPDF?name=tc" class="btn btn-success btn-icon-split">
                    <span class="icon text-white-50">
                      <i class="fas fa-download"></i>
                    </span>
                    <span class="text">Download CSV</span>
                  </a>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <c:if test='${sessionScope.userIdentity.emailId!="" }'>
                  <a href="#" class="btn btn-danger btn-icon-split" id="timelineDatamailPDF">
                    <span class="icon text-white-50">
                      <i class="fas fa-envelope-open"></i>
                    </span>
                    <span class="text">Mail PDF</span>
                  </a>
                  </c:if>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <c:if test='${sessionScope.userIdentity.emailId!="" }'>
                  <a href="#" class="btn btn-success btn-icon-split" id="timelineDatamailCSV">
                    <span class="icon text-white-50">
                      <i class="fas fa-envelope-open"></i>
                    </span>
                    <span class="text">Mail CSV</span>
                  </a>
                  </c:if>
                  <br>
                </div>
              </div>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Covid-Reports</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">?</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>
  
 <script>
  $("#stateDatamailPDF").on('click',function(e){
	  //e.preventDefault();
			var saveData = $.ajax({
		      method: 'GET',
		      url: "SendMail",
		      dataType: "text",
		      data:{which:"statePDF"},
		      success: function(resultData) { alert("Successfully Sent") }
		});
		saveData.error(function() { alert("Something went wrong"); });
	  })
	  
	 $("#stateDatamailCSV").on('click',function(e){
	  //e.preventDefault();
			var saveData = $.ajax({
		      method: 'GET',
		      url: "SendMail",
		      dataType: "text",
		      data:{which:"stateCSV"},
		      success: function(resultData) { alert("Successfully Sent") }
		});
		saveData.error(function() { alert("Something went wrong"); });
	  })
	  
	 $("#timelineDatamailPDF").on('click',function(e){
	  //e.preventDefault();
			var saveData = $.ajax({
		      method: 'GET',
		      url: "SendMail",
		      dataType: "text",
		      data:{which:"timelinePDF"},
		      success: function(resultData) { alert("Successfully Sent") }
		});
		saveData.error(function() { alert("Something went wrong"); });
	  })
	  
	 $("#timelineDatamailCSV").on('click',function(e){
	  //e.preventDefault();
			var saveData = $.ajax({
		      method: 'GET',
		      url: "SendMail",
		      dataType: "text",
		      data:{which:"timelineCSV"},
		      success: function(resultData) { alert("Successfully Sent") }
		});
		saveData.error(function() { alert("Something went wrong"); });
	  })
  </script>

</body>

</html>
