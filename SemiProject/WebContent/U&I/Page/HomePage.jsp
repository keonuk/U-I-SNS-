<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="friend.db.*"%>
<%
	List<BoardBean> boardlist = new ArrayList<BoardBean>();
	boardlist = (ArrayList<BoardBean>) request.getAttribute("boardlist");
	String SessionId = (String) session.getAttribute("id");
%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("#writeForm").hide();
		$("#writeBtn").click(function() {
			$("#writeForm").toggle(500).show();
		});
	});
</script>
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
		<section class="wrapper" style="overflow-y: scroll; height:100%">
			<div class="row" style="margin: 3%">
				<h1 style="margin-left: 0.2%">뉴스피드</h1>
				<hr>
				<button id="writeBtn" class="btn btn-info"
					style="float: right; margin-right: 5%">글쓰기</button>
				<br>
				<form action="BoardAdd.sr" method="post"
					style="width: 60%; margin: auto" enctype="multipart/form-data"
					name="addform">

					<div class="showback" id="writeForm" >

						<div>
							<table style="margin: auto">
								<tr>
									<td>
										<h5>제 목&nbsp;&nbsp;</h5>
									</td>
									<td><input name="BOARD_SUBJECT" type="text" size="50"
										maxlength="10" class="form-control"></td>
								</tr>

								<tr>
									<td>
										<h5>사 진 첨 부&nbsp;&nbsp;</h5>
									</td>
									<td><input name="BOARD_FILE" type="file"></td>
								</tr>
								<tr>
									<td>
										<h5>내 용&nbsp;&nbsp;</h5>
									</td>
									<td><textarea name="BOARD_CONTENT" cols="40" rows="5"
											class="form-control"></textarea></td>
								</tr>

								<tr bgcolor="#cccccc">
									<td colspan="2" style="height: 1px;"></td>
								</tr>
								<tr>
									<td colspan="2"><a href="javascript:addform.submit()"
										class="btn btn-info" style="margin-left: 88%">등록</a></td>
								</tr>
							</table>

						</div>
					</div>
				</form>

				<%
					if (boardlist != null) {
				%>
				<div class="showback" style="margin: 5%; overflow-y: scroll; height:70%">
					<form action="BoardModify.sr" method="post" name="modifyform"
						style="margin-left: 20%">
						<%
							for (int i = 0; i < boardlist.size(); i++) {
									BoardBean b1 = (BoardBean) boardlist.get(i);
						%>
						<table
							style="border: 1px solid black; border-collapse: collapse; background: #ffffe6">
							<tr>
								<td colspan=2
									style="font-size: 10px; width: 60%; text-align: center"><strong>
									<h4 style="font-weight:bold"><%=b1.getId() + " 님의 게시물"%></h4></strong></td>
							</tr>

							<tr>
								<td>제목 :</td>
								<td><%=b1.getSubject()%></td>
							</tr>


							<tr>
								<td>날짜 :</td>
								<td><%=b1.getDate()%></td>
							</tr>


							<tr>
								<td>사진</td>
								<td><img src="././boardupload/<%=b1.getImage()%>" /></td>
							</tr>

							<tr>
								<td>내용</td>
								<td><%=b1.getContent()%></td>
							</tr>
							<br>
							<%
								}
							%>
						</table>
					</form>
				</div>
			</div>

			<%
				} else {
			%>
				<h1>게시된 글이 없습니다.</h1>
			<%
				}
			%>
			<!--/ row -->
		</section>
	</section>
	<!-- /MAIN CONTENT -->

	<!-- js placed at the end of the document so the pages load faster -->
	<script src="./U&I/Page/assets/js/jquery.js"></script>
	<script src="./U&I/Page/assets/js/jquery-3.2.1.js"></script>
	<script src="./U&I/Page/assets/js/bootstrap.min.js"></script>
	<!-- 
	<script class="include" type="text/javascript"
		src="./U&I/Page/assets/js/jquery.dcjqaccordion.2.7.js"></script> -->
	<script src="./U&I/Page/assets/js/jquery.scrollTo.min.js"></script>
	<script src="./U&I/Page/assets/js/jquery.nicescroll.js"></script>

	<!--common script for all pages-->
	<script src="./U&I/Page/assets/js/common-scripts.js"></script>

	<!--script for this page-->
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="./U&I/Page/assets/js/gritter-conf.js"></script>
</body>
</html>