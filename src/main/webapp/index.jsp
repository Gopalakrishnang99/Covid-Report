<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false"%>
    <%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
      <li class="nav-item active">
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
        <span class="text-lg text-gray-900"> &nbsp;Covid-19 Overview</span>

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
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">At a glance</h1>
          </div>

          <!-- Content Row -->
          <div class="row">

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Total Reported Cases</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${sessionScope.IndexPageValues[0]}</div>
                    </div>
                    <!--<div class="col-auto">
                      <i class="fas fa-calendar fa-2x text-gray-300"></i>
                    </div>-->
                  </div>
                </div>
              </div>
            </div>

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Recovered Patients</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${sessionScope.IndexPageValues[1]}</div>
                    </div>
                   <!--  <div class="col-auto">
                      <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                    </div>-->
                  </div>
                </div>
              </div>
            </div>

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Deaths</div>
                      <div class="row no-gutters align-items-center">
                        <div class="col-auto">
                          <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${sessionScope.IndexPageValues[2]}</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div> 
            </div>

            <!-- Pending Requests Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Death rate</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800"><fmt:formatNumber type = "percent" 
         minFractionDigits = "4" value="${ (sessionScope.IndexPageValues[2]/sessionScope.IndexPageValues[0]) }"/></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Content Row -->

          <div class="row row-cols-1">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Overview</h6>
                </div>
                <div class="card-body border-bottom-info bg-gradient-success text-gray-100">
                  Coronavirus disease (COVID-19) is an infectious disease caused by a newly discovered coronavirus.
                  <br><br>
                  Most people infected with the COVID-19 virus will experience mild to moderate respiratory illness and recover without requiring special treatment.  Older people, and those with underlying medical problems like cardiovascular disease, diabetes, chronic respiratory disease, and cancer are more likely to develop serious illness.
                  <br><br>
                  The best way to prevent and slow down transmission is be well informed about the COVID-19 virus, the disease it causes and how it spreads. Protect yourself and others from infection by washing your hands or using an alcohol based rub frequently and not touching your face. 
                  <br><br>
                  The COVID-19 virus spreads primarily through droplets of saliva or discharge from the nose when an infected person coughs or sneezes, so it’s important that you also practice respiratory etiquette (for example, by coughing into a flexed elbow).
                  <br><br>
                  At this time, there are no specific vaccines or treatments for COVID-19. However, there are many ongoing clinical trials evaluating potential treatments. WHO will continue to provide updated information as soon as clinical findings become available.
                </div>
              </div>
          </div>

          <!-- Content Row -->
          <div class="row row-cols-1">
             <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Symptoms</h6>
                </div>
                <div class="card-body border-bottom-danger bg-gradient-warning text-gray-900">
                  COVID-19 affects different people in different ways. Most infected people will develop mild to moderate illness and recover without hospitalization.
                  <br><br>
                  Most common symptoms:
                  <br>
                  <ul>
                    <li>Fever</li>
                    <li>Dry Cough</li>
                    <li>Tiredness</li>
                  </ul>
                  
                  Less Common Symptoms:
                  <br>
                  <ul>
                    <li>Aches and pains</li>
                    <li>Sore throat</li>
                    <li>Diarrhoea</li>
                    <li>Conjunctivitis</li>
                    <li>Headache</li>
                    <li>Loss of taste or smell</li>
                  </ul>
                  
                  Serious symptoms:
                  <br>
                  <ul>
                    <li>Difficulty in breathing</li>
                    <li>Chest pain</li>
                    <li>Loss of speech or movement</li>
                  </ul>
                  Seek immediate medical attention if you have serious symptoms.  Always call before visiting your doctor or health facility. 
                  <br><br>
                  People with mild symptoms who are otherwise healthy should manage their symptoms at home. 
                  <br><br>
                  On average it takes 5–6 days from when someone is infected with the virus for symptoms to show, however it can take up to 14 days. 
                </div>
              </div>
          </div>
        <div class="row row-cols-1">
          <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Prevention</h6>
                </div>
                <div class="card-body bg-gradient-info text-gray-100 border-bottom-dark" >
                  To prevent infection and to slow transmission of COVID-19, do the following:
                  <br><br>
                  <ul>
                    <li>Wash your hands regularly with soap and water, or clean them with alcohol-based hand rub.</li>
                    <li>Maintain at least 1 metre distance between you and people coughing or sneezing.</li>
                    <li>Avoid touching your face.</li>
                    <li>Cover your mouth and nose when coughing or sneezing.</li>
                    <li>Stay home if you feel unwell.</li>
                    <li>Refrain from smoking and other activities that weaken the lungs.</li>
                    <li>Practice physical distancing by avoiding unnecessary travel and staying away from large groups of people.</li>
                  </ul>
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
            <span aria-hidden="true">×</span>
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

  <!-- Page level plugins -->
  <script src="vendor/chart.js/Chart.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>

</body>

</html>
    