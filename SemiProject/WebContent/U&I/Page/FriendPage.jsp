<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!-- 친구 목록 페이지 -->
<%@ page import="friend.db.*"%>
<%@ page import="java.util.*"%>
<%
	// 사용자의 내용이 담겨 있는 frienddata 속성의 데이터를 FriendBean 타입으로 변환하여 가져옵니다.
	List<FriendBean> friendlist = (ArrayList<FriendBean>) request.getAttribute("friendlist");
%>
<!DOCTYPE html>
<html>
<head>
<title>YOU & I - <%=session.getAttribute("sessionName")%></title>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
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
<style>
table>td {
	line-height: 40px;
	font-size: 30px
}
</style>
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
	<aside>
		<div id="sidebar" class="nav-collapse">
			<!-- sidebar menu start-->
			<ul class="sidebar-menu" id="nav-accordion">
				<p class="centered">
					<a href="Fri_MyPagePage.sr"> <img
						src="./upload/<%=session.getAttribute("PhotoName")%>"
						class="img-circle" width="120px" height="120px"></a>
				</p>

				<a href="MyPageView.sr"><h3 class="centered"
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
		<section class="wrapper" style="overflow-y: scroll; height:100%">
			<div class="row" style="margin: 3%; overflow-y: scroll">
				<h1 style="margin-left: 0.2%">친구 목록</h1>
				<hr>

				<div class="showback" style="padding-bottom:100%">
					<%
						if (friendlist != null) {
							for (int i = 0; i < friendlist.size(); i++) {
								FriendBean fb = (FriendBean) friendlist.get(i);
					%>

					<div class="col-lg-4 col-md-4 col-sm-4 mb">
						<div class="content-panel pn">
							<div id="spotify"
								style="background-image:url('./upload/<%=fb.getM_PhotoName()%>')"
								onclick="javacript:location='./Fri_MyPageView.sr?getid=<%=fb.getM_Id()%>'">
								<div class="col-xs-4 col-xs-offset-8">
									<a href="./FriendDeleteAction.sr?fr_id=<%=fb.getM_Id()%>"
										class="btn btn-sm btn-danger">UnFollow </a>
								</div>
								<div class="sp-title">
									<h3><%=fb.getM_name()%></h3>
								</div>
							</div>
							<p class="followers">
								<i class="fa fa-user"></i>&nbsp;&nbsp;&nbsp;ID :
								<%=fb.getM_Id()%></p>
						</div>
					</div>
					<%
						session.setAttribute("fr_id", fb.getM_Id());
					%>
					<%
						}
						} else {
					%>
						<h1>친구가 없습니다. 사용자 검색으로 찾아보세요!</h1>
					<%
						}
					%>
				</div>
			</div>
		</section>
	</section>
	<!-- /MAIN CONTENT -->

	<!-- js placed at the end of the document so the pages load faster -->
	<script src="./U&I/Page/assets/js/jquery.js"></script>
	<script src="./U&I/Page/assets/js/jquery-3.2.1.js"></script>
	<script src="./U&I/Page/assets/js/bootstrap.min.js"></script>
	<script class="include" type="text/javascript"
		src="./U&I/Page/assets/js/jquery.dcjqaccordion.2.7.js"></script>
	<!-- 
	<script src="./U&I/Page/assets/js/jquery.scrollTo.min.js"></script>
	<script src="./U&I/Page/assets/js/jquery.nicescroll.js"
		type="text/javascript"></script> -->

	<!--common script for all pages-->
	<script src="./U&I/Page/assets/js/common-scripts.js"></script>

	<!--script for this page-->
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter-conf.js"></script>
</body>
</html>