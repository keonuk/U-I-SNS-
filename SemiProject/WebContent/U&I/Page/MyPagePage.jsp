<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="friend.db.*"%>
<%
	// 로그인 중인 사용자 아이디를 세션값에 저장해서 id변수에 저장합니다. 
	String id = (String) session.getAttribute("id");
	FriendBean memberdata = new FriendBean();
	SemiDAO memberdao = new SemiDAO();
	memberdata = memberdao.getUserInfo(id);
%>
<html>
<head>
<title>YOU & I - <%=session.getAttribute("sessionName")%></title>
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
					<h5>
						<a href="./HomeView.sr"> <%=session.getAttribute("sessionName")%></a>님
						환영합니다.
					</h5>
				</li>
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
					<a href="./MyPageView.sr"> <img
						src="./upload/<%=session.getAttribute("newPhoto")%>"
						class="img-circle" width="120px" height="120px"></a>
				</p>

				<a href="./MyPageView.sr"><h3 class="centered"
						style="color: white">
						<%=session.getAttribute("sessionName")%></h3></a>
				<br>
				<br>
				<br>
				<li class="sub-menu"><a href="./HomeView.sr"
					style="font-size: 20px; color: white"> <span>Newsfeed</span>
				</a></li>

				<li class="sub-menu"><a href="./FriendView.sr"
					style="font-size: 20px; color: white"> <span>Friend</span>
				</a></li>

				<li class="sub-menu"><a href="./SearchView.sr"
					style="font-size: 20px; color: white"> <span>Search</span>
				</a></li>
				<li class="sub-menu"><a href="./DiaryView.sr"
					style="font-size: 20px; color: white"> <span>Diary</span>
				</a></li>
				<li class="sub-menu"><a href="./VisitListAction.sr"
					style="font-size: 20px; color: white"> <span>Visitor</span>
				</a></li>
				<li class="sub-menu"><a href="./MyPageView.sr"
					style="font-size: 20px; color: white"> <span>My Page</span>
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
				<h1 style="margin-left: 0.2%">내 정보</h1>
				<hr>
				<div class="container-fluid text-center">
					<div class="showback" style="height:50%">
					<div class="col-lg-4 col-md-4 col-sm-4 mb">
						<!-- WHITE PANEL - TOP USER -->
						<div>
						<div class="white-panel">
							<div class="white-header">
								<h3>
									ID :
									<%=memberdata.getM_Id()%></h3>
							</div>
							<p style="font-color: black">
								<img src="./upload/<%=memberdata.getM_PhotoName()%>"
									class="img-circle" style="width: 100px; height: 100px">
							</p>

							<p style="font-color: black">
								<b><%=memberdata.getM_name()%></b>
							</p>
						
						<button id="updateBtn" class="btn btn-success"
							onclick="changeView()">정보 수정</button>&nbsp;&nbsp;&nbsp;
					
						<button id="outBtn" class="btn btn-danger"
							onclick="leavemember()">회원 탈퇴</button>
						</div>
					</div>
					</div>
					<!-- /col-md-4 -->
						
					<div class="temp2">
						<jsp:include page="UserInfoForm.jsp"></jsp:include>
					</div>
					</div>
				</div>
			</div>
			<!--/ row -->
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

		<script>
			function changeView() {
				// 내정보 버튼 클릭시 회원정보 보여주는 화면으로 이동
				location.href = "./InfoModifyView.sr";
			}

			function leavemember() {
				var user = confirm("탈퇴 하시겠습니까?");

				if (user == true) {
					location.href = "./LeaveMember.sr";
				} else {
					alert("탈퇴 취소");
				}
			}
		</script>
</body>
</html>