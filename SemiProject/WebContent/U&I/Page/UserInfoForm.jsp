<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="friend.db.*"%>
<html>
<head>
<style>
table {
	border: 1px solid skyblue;
	margin: 0 auto;
	width: 450px;
	height: 200px;
}

tr > td:eq(0) {width:100px}
td {
	border: 1px solid skyblue; line-height:20px;
}

#title {
	background-color: #ebebe0;
	width:100px
}
</style>	
</head>
<body>
	<%
		String id = session.getAttribute("id").toString();

		// 세션에 저장된 아이디를 가져와서
		// 그 아이디 해당하는 회원정보를 가져온다.
		SemiDAO dao = new SemiDAO();
		FriendBean memberBean = dao.getUserInfo(id);
	%>
	<table>
		<tr>
			<td id="title">아이디</td>
			<td><%=memberBean.getM_Id()%></td>
		</tr>
		<tr>
			<td id="title">이름</td>
			<td><%=memberBean.getM_name()%></td>
		</tr>
		<tr>
			<td id="title">성별</td>
			<td><%=memberBean.getM_gender()%></td>
		</tr>
		<tr>
			<td id="title">이메일</td>
			<td><%=memberBean.getM_email()%></td>
		</tr>
		<tr>
			<td id="title">전화번호</td>
			<td class="numeric"><%=memberBean.getM_phoneNum()%></td>
		</tr>
	</table>
</body>
</html>

