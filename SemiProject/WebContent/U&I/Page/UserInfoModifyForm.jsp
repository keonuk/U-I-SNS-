<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="friend.db.*"%>
<%
	String id = session.getAttribute("id").toString();

	SemiDAO dao = new SemiDAO();
	FriendBean memberBean = dao.getUserInfo(id);
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

</head>
<style type="text/css">
table {
	border: 3px solid skyblue;
}

td {
	border: 1px solid skyblue
}

#title {
	background-color: skyblue
}
</style>

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
				<h1 style="margin-left: 0.2%">정보 수정</h1>
				<hr>

				<div class="showback" style="height: 50%">
					<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
					<!-- 값(파라미터) 전송은 POST 방식 -->
					<div style="margin-left: 45%">
						<form method="post" action="InfoModify.sr"
							enctype="multipart/form-data" name="userInfo">
							<table>
								<tr>
									<td id="title">아이디</td>
									<td><input type="text" name="id" maxlength="50"
										value="<%=memberBean.getM_Id()%>"></td>
								</tr>
								<tr>
									<td id="title">비밀번호</td>
									<td><input type="password" name="password" maxlength="50"
										value="<%=memberBean.getM_pass()%>"></td>
								</tr>
							</table>
							<br> <br>

							<table>
								<tr>
									<td id="title">이름</td>
									<td><%=memberBean.getM_name()%></td>
								</tr>

								<tr>
									<td id="title">성별</td>
									<td><input type="text" name="gender"
										value="<%=memberBean.getM_gender()%>" /></td>
								</tr>
								<tr>
									<td id="title">이메일</td>
									<td><input type="text" name="mail"
										value="<%=memberBean.getM_email()%>" /></td>
								</tr>

								<tr>
									<td id="title">휴대전화</td>
									<td><input type="text" name="phone"
										value="<%=memberBean.getM_phoneNum()%>" /></td>
								</tr>
							</table>
							<br> <br>
							<h4>프로필 사진 변경</h4>
							<input type="file" value="사진변경" name="myimg" />
							<div style="margin: 5%">
								<input type="submit" class="btn btn-success" value="수정">&nbsp;&nbsp;&nbsp;
								<input type="button" class="btn btn-danger" value="취소"
									onclick="javascript:window.location='MyPageView.sr'">
							</div>
						</form>
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