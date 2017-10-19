<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!-- 방명록 페이지 -->
<%@ page import="java.util.*"%>
<%@ page import="friend.db.*"%>

<%
	//게시물의 내용이 담겨있는 boarddata 속성의 데이터를 BoardBean 타입으로 
	//변환하여 얻습니다.
	VisitBean visit = (VisitBean) request.getAttribute("visitdata");
%>
<html>
<head>
	<title>YOU & I - <%=session.getAttribute("friendname") %></title>
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
		<!--logo start-->
		<a href="./HomeView.sr" class="logo"><b>YOU & I</b></a>
		<!--logo end-->

		<div class="top-menu">
			<ul class="nav pull-right top-menu">
				<li style="padding:15px 10px 0 0">
				<h5><a href="./HomeView.sr"> <%=session.getAttribute("sessionName")%></a>님 환영합니다.</h5></li>
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
					<a href="./Fri_MyPageView.sr"> <img
						src="./upload/<%=session.getAttribute("friendPhoto") %>" class="img-circle"
						width="120px" height="120px"></a>
				</p>
				<a href="./Fri_MyPageView.sr"><h3 class="centered"
						style="color: white">
						<%=session.getAttribute("friendname")%></h3></a>
				<br>
				<br>
				<br>
				<li class="sub-menu"><a href="./HomeView.sr"
					style="font-size: 20px; color: white"> <span>Newsfeed</span>
				</a></li>

				<li class="sub-menu"><a href="./Fri_VisitListAction.sr"
					style="font-size: 20px; color: white"> <span>Visitor</span>
				</a></li>
				<li class="sub-menu"><a href="./Fri_MyPageView.sr"
					style="font-size: 20px; color: white"> <span>Profile</span>
				</a></li>
			</ul>
			<!-- sidebar menu end-->
		</div>
	</aside>
	<!--sidebar end-->

	<!-- MAIN CONTENT -->
	<!--main content start-->
	<section id="main-content" style="padding-left:20px">
	<div class="row mt wrapper main-content">
				<h1>방명록</h1>
				<form method = "post" action = "./FriendVisitModifyAction.sr">
				<input type = "hidden" name = "v_num" value = "<%=visit.getV_NUM()%>">
				<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>
							<div>작성자 &nbsp;</div>
						</td>
						<td><input type ="text" value= "<%=visit.getV_ID()%>" name = v_id readonly></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>
							<div>내 용</div>
						</td>
						<td><textarea name="V_CONTENT" cols="67" rows="15"><%=visit.getV_CONTENT()%></textarea>
						</td>
					</tr>

					<!-- 답변, 수정, 삭제, 목록의 링크를 표시합니다. -->
			
					 
					<tr><td>&nbsp;</td></tr>
					<tr>			
						 <td rowspan='2' colspan="2" class="h30 lime center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 <input type="submit" value ="수정"> &nbsp;&nbsp;
						 <input type="button" value = "삭제" onclick = "location.href='./FriendVisitDelete.sr?num=<%=visit.getV_NUM()%>'">&nbsp;&nbsp;
						 <input type="button" value = "리스트" onclick = "location.href='./FriendVisitListAction.sr'">	&nbsp;&nbsp;</td>
					
					</tr>
				
				</table>
				</form>
				</div>
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