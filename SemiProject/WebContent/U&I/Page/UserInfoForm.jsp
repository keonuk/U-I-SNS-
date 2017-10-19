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

		// ���ǿ� ����� ���̵� �����ͼ�
		// �� ���̵� �ش��ϴ� ȸ�������� �����´�.
		SemiDAO dao = new SemiDAO();
		FriendBean memberBean = dao.getUserInfo(id);
	%>
	<table>
		<tr>
			<td id="title">���̵�</td>
			<td><%=memberBean.getM_Id()%></td>
		</tr>
		<tr>
			<td id="title">�̸�</td>
			<td><%=memberBean.getM_name()%></td>
		</tr>
		<tr>
			<td id="title">����</td>
			<td><%=memberBean.getM_gender()%></td>
		</tr>
		<tr>
			<td id="title">�̸���</td>
			<td><%=memberBean.getM_email()%></td>
		</tr>
		<tr>
			<td id="title">��ȭ��ȣ</td>
			<td class="numeric"><%=memberBean.getM_phoneNum()%></td>
		</tr>
	</table>
</body>
</html>

