<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="friend.db.*"%>
<%@ page import="java.util.*"%>
<%
	// 사용자의 내용이 담겨 있는 frienddata 속성의 데이터를 FriendBean 타입으로 변환하여 가져옵니다.
	List<FriendBean> searchList = (ArrayList<FriendBean>) request.getAttribute("searchList");
%>

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
					<a href="./MyPageView.sr"> <img
						src="./upload/<%=session.getAttribute("PhotoName")%>"
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
				<h1 style="margin-left: 0.2%">사용자 검색</h1>
				<hr>
				<input type="text" name="search" id="search"
					style="border-radius: 5px; width: 500px; height: 40px; color: black;">
				<button class="btn btn-default">
					<img src="U&I/image/search.jpg" style="width: 30px; height: 30px"
						onclick="go();">
				</button>
				<br> <br>
				<h1 style="margin-left: 0.2%">검색 결과</h1>
				<hr>
				<div class="showback"
					style="overflow-y: scroll; height: 100%; width: 100%;">
					<%
						if (searchList != null) {
							for (int i = 0; i < searchList.size(); i++) {
								FriendBean fb = (FriendBean) searchList.get(i);
					%>


					<div class="col-lg-4 col-md-4 col-sm-4 mb">
						<div class="content-panel pn">
							<div id="spotify"
								style="background-image:url('./upload/<%=fb.getM_PhotoName()%>')">
								<div class="col-xs-4 col-xs-offset-8">
									<a
										href="AddFriend.sr?fr_id=<%=fb.getM_Id()%>&fr_name=<%=fb.getM_name()%>&fr_pname=<%=fb.getM_PhotoName()%>"
										class="btn btn-sm btn-success">Follow</a>
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
						}
						} else {
					%>
					<h1>검색 결과가 없습니다.</h1>
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
		function go() {
			if ($("#search").val() != ''){
				location.href = './SearchResult.sr?search=' + $("#search").val();
			} else if ($("#search").val() == ''){
				window.alert("검색할 아이디 또는 이름를 입력해 주세요.");
				location.href = './SearchView.sr';
			}
		}
	</script>
</body>
</html>