<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!-- 방명록 페이지 -->
<%@ page import="java.util.*"%>
<%@ page import="friend.db.*"%>

<%
	List<VisitBean> visitlist = (ArrayList<VisitBean>) request.getAttribute("visitlist");
	int listcount = ((Integer) request.getAttribute("listcount")).intValue();
	int maxnum = ((Integer) request.getAttribute("maxnum")).intValue();
	int nowpage = ((Integer) request.getAttribute("page")).intValue();
	int maxpage = ((Integer) request.getAttribute("maxpage")).intValue();
	int startpage = ((Integer) request.getAttribute("startpage")).intValue();
	int endpage = ((Integer) request.getAttribute("endpage")).intValue();
	String w_date = request.getAttribute("w_date").toString();
	String sex = (String)session.getAttribute("sex");
	System.out.println("getSex2==>" + sex);
	
	//성별 조건 (이미지 가져오기)
	if(sex.equals("남자") || sex.equals("male") || sex.equals("남")) {
		sex = "1.png";
	}else if(sex.equals("여자") || sex.equals("female") || sex.equals("여")){
		sex = "2.png";
	}
	
	session.setAttribute("sex",sex);
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

<style>
	input[type=submit] {img:url(../image/search.jpg)}
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
					<a href="./MyPageView.sr"> <img
						src="./upload/<%=session.getAttribute("PhotoName") %>" 
						class="img-circle" width="120px" height="120px"></a>
				</p>
					<a href="./MyPageView.sr"><h3 class="centered" style="color: white">
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
				<h1 style="margin-left: 0.2%">방명록</h1>
				<hr>
			<form action="./VisitAddAction.sr" method="post" name="DataInsert">
				<div style ="margin-top:20px; margin-left:10px; width:200px; height:60px;">
					<div style="margin-left: 850px; margin-top:-10px;">
							<input class='btn-default' type='submit' value='등록'
							style="width:70px; height:40px; font-size:15pt;">
					</div>
				</div>
				

					<%-- return write_value --%>
					<input type="hidden" value=<%=maxnum%> name="v_num"> 
					<input type="hidden" value="<%=w_date.substring(0, 10)%>" name="v_tdate">

					<div class="list_write" style = "width: 1000px; margin: 0 auto">
						<table>
							<tr>
								<th class=th_width>NO.<%=maxnum + 1%></th>
								<th><%=session.getAttribute("id")%></th>
								<th>(<%=w_date.substring(0, 10)%>)
								</th>
								
							</tr>
							<tr>
								<td colspan=2 class=th_width><img width=100px
									src="./U&I/image/<%=sex%>"></td>
								<td><textarea rows="4" cols="103" name="v_content"></textarea></td>
							</tr>
						</table>
					</div>
					<div class="list_print" style="overflow:scroll; width:950px; height:600px; padding:5px;
					 margin-right: auto ; margin-left: 325px; margin-top:15px;" >
						<table>
							<%
								if (listcount > 0) { //등록된 글이 있는 경우입니다.
									for (int i = 0; i < visitlist.size(); i++) {
										VisitBean b1 = (VisitBean) visitlist.get(i);
							%>
							<%
							//성별 조건 (이미지 가져오기)
							
							if (b1.getV_PHOTO().equals("1.png")) {
								sex = "1.png";
							} else {
								sex = "2.png";
							}
							%>

							<tr>
								<th class=th_width>No.<%=b1.getV_NUM()%>&nbsp;&nbsp;&nbsp;</th>
								<th><%=session.getAttribute("id")%></th>
								<th>(<%=b1.getV_TDATE()%>)
								</th>
							</tr>
							<tr onmouseover="this.style.backgroundColor='#F8F8F8'"
								onmouseout="this.style.backgroundColor='#FFFFFF'">
								<td colspan=2 class=th_width><img width=100px
									src="./U&I/image/<%=sex%>"></td>
								<td class='td_width'><a
									href="./VisitDetailAction.sr?num=<%=b1.getV_NUM()%>"><%=b1.getV_CONTENT()%></a>
								</td>
							</tr>
							<%
								}
							%>
							</table>
							</div>
							<div style = "width: 800px;  margin-left: 430px;  margin-top: 20px;">
							<table style ="margin: 0 auto;">
							<!-- 페이징 처리하는 문 [이전][1][2][3]..[10][다음] -->
							<tr class="h30 lime center">
								<td colspan=5>
									<%
										if (nowpage <= 1) { //1페이지 이하인 경우
									%> [◀]&nbsp; <%
 										} else { //2페이지 이상인 경우 - 한 페이지 적은 페이지로 이동
									%> <a href="./VisitListAction.sr?page=<%=nowpage - 1%>"> [◀]</a>&nbsp; <%
												}
									%> <%
										for (int a = startpage; a <= endpage; a++) {
											 	if (a == nowpage) { //현재 페이지 - 링크 걸지 않습니다.
										%> [<%=a%>] <%
											 	} else { //현재 페이지 아닌 경우 링크 겁니다.
										%> <a href="./VisitListAction.sr?page=<%=a%>"> [<%=a%>]
																			</a>&nbsp; <%
												}
										%> <%
										}
										%> <!-- 현재 페이지가 가장 큰 페이지보다 크거나 같은 경우 [다음]에 링크 걸지 않습니다. --> <%
										if (nowpage >= maxpage) {
										%> [▶] <%
										} else {
										%> <a href="./VisitListAction.sr?page=<%=nowpage + 1%>">[▶]</a> <%
										}
										%>
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</div>
				</form>
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
</body>
</html>