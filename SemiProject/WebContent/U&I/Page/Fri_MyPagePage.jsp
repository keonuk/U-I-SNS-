<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="friend.db.*"%>
<%
   // 로그인 중인 사용자 아이디를 세션값에 저장해서 id변수에 저장합니다. 
   String fr_id = (String) session.getAttribute("fr_id");
   FriendBean memberdata = new FriendBean();
   SemiDAO memberdao = new SemiDAO();
   //memberdata = memberdao.getFriendInfo(fr_id);
   
   session.setAttribute("friendPhoto", memberdata.getFr_PhotoName());
   session.setAttribute("friendname", memberdata.getFr_name());
   session.setAttribute("friendId", memberdata.getFr_id());
   
   String new_fr_id = (String) session.getAttribute("new_fr_id");
   String new_fr_name = (String) session.getAttribute("new_fr_name");
   String new_fr_PhotoName = (String) session.getAttribute("new_fr_PhotoName");
   
   System.out.println("페이지에서 세션에 세팅한 친구 사진 이름 :" + new_fr_PhotoName);
   System.out.println("페이지에서 세션에 세팅한 친구 이름 :" + new_fr_name);
   System.out.println("페이지에서 세션에 세팅한 친구 아이디 :" + new_fr_id);
   
   System.out.println("new_fr_id : " + new_fr_id);
   System.out.println("new_fr_id : " + new_fr_name);
   System.out.println("new_fr_PhotoName : " + new_fr_PhotoName);   
%>

<html>
<head>
	<title>YOU & I - <%=new_fr_name%></title>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="Dashboard">
<meta name="keyword"
	content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

<!-- Bootstrap core CSS -->
<link href="./U&I/Page/assets/css/bootstrap.css" rel="stylesheet">
<!--external css-->
<link href="U&I/Page/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="U&I/Page/assets/js/gritter/css/jquery.gritter.css" />

<!-- Custom styles for this template -->
<link href="U&I/Page/assets/css/style.css" rel="stylesheet">
<link href="U&I/Page/assets/css/style-responsive.css" rel="stylesheet">

</head>
<body>
	<header class="header black-bg">
		<div class="sidebar-toggle-box">
			<div class="fa fa-bars tooltips" data-placement="right"
				data-original-title="Toggle Navigation"></div>
		</div>
		<!--logo start-->
		<a href="./HomeView.sr" class="logo"><b>YOU & I</b></a>
		<!--logo end-->

		<div class="top-menu">
			<ul class="nav pull-right top-menu">
				<li style="padding: 15px 10px 0 0">
				<h5><a href="./HomeView.sr"> <%=session.getAttribute("sessionName")%></a>님 환영합니다.</h5></li>
				<li><a href="logoutProcess.sr" class="logout">Logout </a></li>

			</ul>
		</div>
	</header>
	<!--header end-->

	<!-- MAIN SIDEBAR MENU -->
	<!--sidebar start-->
	<%
		session.setAttribute("newPhoto", memberdata.getM_PhotoName());
	%>
	<aside>
		<div id="sidebar" class="nav-collapse">
			<!-- sidebar menu start-->
			<ul class="sidebar-menu" id="nav-accordion">
				<p class="centered">
					<a href="./New_MyPageView.sr"> <img
						src="./upload/<%=new_fr_PhotoName%>"
						class="img-circle" width="120px" height="120px"></a>
				</p>

				<a href="./New_MyPageView.sr"><h3 class="centered"
						style="color: white">
						<%=new_fr_name%></h3></a>
				<br>
				<br>
				<br>
				<li class="sub-menu"><a href="./HomeView.sr"
					style="font-size: 20px; color: white"> <span>Newsfeed</span>
				</a></li>
				<li class="sub-menu"><a href="./FriendVisitListAction.sr"
					style="font-size: 20px; color: white"> <span>Visitor</span>
				</a></li>
				<li class="sub-menu"><a href="New_MyPageView.sr"
					style="font-size: 20px; color: white"> <span>Profile</span>
				</a></li>
			</ul>
			<!-- sidebar menu end-->
		</div>
	</aside>
	<!--sidebar end-->

	<!-- MAIN CONTENT -->
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper">
			<div class="row" style="margin: 3%">
			<div class="container-fluid text-center">
			<div class="showback" style="height:50%">
					<div class="col-lg-4 col-md-4 col-sm-4 mb">
						<!-- WHITE PANEL - TOP USER -->
						<div>
						<div class="white-panel">
							<div class="white-header">
								<h1>
									ID : <%=new_fr_id%>
								</h1>
							</div>
							<div style="line-height: 50px; position: static">
								<img src="./upload/<%=new_fr_PhotoName%>"
									class="img-circle" style="width: 100px; height: 100px">
							</div>

							<p style="font-color: black">
								<b><%=new_fr_name%></b>
							</p>
						</div>
					</div>
					</div>
					<!-- /col-md-4 -->
						
					<div>
						<jsp:include page="Fri_UserInfoForm.jsp"></jsp:include>
					</div>
					</div>
			</div>
		</div>
		<!--/ row -->
	</section>
	</section>
	<!-- /MAIN CONTENT -->


	<!-- js placed at the end of the document so the pages load faster -->
	<script src="./U&I/Page/assets/js/jquery.js"></script>
	<script src="./U&I/Page/assets/js/jquery-3.2.1.js"></script>
	<script src="./U&I/Page/assets/js/bootstrap.min.js"></script>
	<script class="include" type="text/javascript"
		src="./U&I/Page/assets/js/jquery.dcjqaccordion.2.7.js"></script>
	<script src="./U&I/Page/assets/js/jquery.scrollTo.min.js"></script>
	<script src="./U&I/Page/assets/js/jquery.nicescroll.js"
		type="text/javascript"></script>

	<!--common script for all pages-->
	<script src="./U&I/Page/assets/js/common-scripts.js"></script>

	<!--script for this page-->
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter-conf.js"></script>
</body>
</html>