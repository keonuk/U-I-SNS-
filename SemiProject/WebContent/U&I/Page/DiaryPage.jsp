<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="semi.action.*"%>
<%@ page import="net.semi.actionDo.*"%>
<%@ page import="friend.db.*"%>
<html>
<head>
<title>YOU & I - <%=session.getAttribute("sessionName")%></title>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="Dashboard">
<meta name="keyword"
	content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

<script type="text/javascript" src='U&I/js/jquery-3.2.1.js'></script>

<!-- Bootstrap core CSS -->
<link href="./U&I/Page/assets/css/bootstrap.css" rel="stylesheet" />
<!--external css-->
<link href="U&I/Page/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="U&I/Page/assets/js/gritter/css/jquery.gritter.css" />

<!-- Custom styles for this template -->
<link href="U&I/Page/assets/css/style.css" rel="stylesheet" />
<link href="U&I/Page/assets/css/style-responsive.css" rel="stylesheet" />

<!-- 달력 API 부분 -->
<link rel='stylesheet' type='text/css'
	href='U&I/js/calendar/fullcalendar.css' />
<link type="text/css" href="U&I/js/calendar/fullcalendar.print.css"
	rel="stylesheet" />
<script type="text/javascript" src="U&I/js/calendar/jquery-1.5.2.min.js"></script>
<script type='text/javascript' src='U&I/js/calendar/fullcalendar.js'
	charset="euc-kr"></script>
<script type='text/javascript'
	src='U&I/js/calendar/jquery-ui-1.8.11.custom.min.js' charset="euc-kr"></script>


<%
	String data = (String) request.getAttribute("data");
	request.setCharacterEncoding("euc-kr");
%>

<script type='text/javascript'>
	$(document).ready(function() {

		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();

		var calendar = $('#calendar').fullCalendar({

			header : { // 달력 이동 버튼
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay'
			},

			selectable : true,
			selectHelper : true,
			select : function(start, end, allDay) { // 달력 날짜 선택

				var title = prompt('일정을 입력하세요.'); // 일정 입력
				if (title) {

					calendar.fullCalendar('renderEvent', { //월이나 연도가 바뀌어도 저장된 값이 변하지 않게 등록.
						title : title,
						start : start,
						end : end,
						allDay : allDay
					}, true // 날짜 칸에 선 삽입
					);

					//아작스 - 입력한 데이터 DB에 삽입
					$.ajax({
						type : "post",
						data : {
							"title" : title,
							"start" : start.toString(),
							"end" : end.toString(),
							"allDay" : allDay
						},
						url : "ajaxinsert.sr",
						success : function(data) {
							alert("저장성공");
						}
					})

				}
				calendar.fullCalendar('unselect'); // 입력되고 날짜 선택 풀림

			},
			editable : true,
			events :
<%=data%>
	});

	});
</script>
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
				<h3 class="centered" style="color: white"><%=session.getAttribute("sessionName")%></h3>
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
			<div class="row" style="overflow-y: scroll; height:100%">
				<h1 style="margin-left: 0.2%">다이어리</h1>
				<hr>
				<div class="showback">
					<div id='calendar'></div>
				</div>
			</div>
		</section>
	</section>


	<script src="./U&I/Page/assets/js/jquery.scrollTo.min.js"></script>
	<script src="./U&I/Page/assets/js/jquery.nicescroll.js"
		type="text/javascript"></script>
	<!-- /MAIN CONTENT -->

	<!-- js placed at the end of the document so the pages load faster -->
	<!-- 	<script src="./U&I/Page/assets/js/jquery.js"></script>
	<script src="./U&I/Page/assets/js/jquery-3.2.1.js"></script>
	<script src="./U&I/Page/assets/js/bootstrap.min.js"></script>
	<script class="include" type="text/javascript"
		src="./U&I/Page/assets/js/jquery.dcjqaccordion.2.7.js"></script>

	common script for all pages
	<script src="./U&I/Page/assets/js/common-scripts.js"></script>

	script for this page
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter-conf.js"></script> -->
</body>
</html>