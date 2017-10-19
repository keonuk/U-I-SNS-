<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="friend.db.*"%>
<html>
<head>
<style>
table {
	margin-left: auto;
	margin-right: auto;
	width: 500px;
	height: 300px;
	border: 3px solid skyblue;
}

td {
	border: 1px solid skyblue
}

#title {
	background-color: skyblue
}
</style>

</head>
<body>
	<%
      String fr_id = session.getAttribute("fr_id").toString();

      // ���ǿ� ����� ���̵� �����ͼ�
      // �� ���̵� �ش��ϴ� ȸ�������� �����´�.
      SemiDAO dao = new SemiDAO();
      FriendBean memberBean = dao.getFriendInfo(fr_id);
      String new_fr_id = (String) session.getAttribute("new_fr_id");
      String new_fr_name = (String) session.getAttribute("new_fr_name");
      String new_fr_PhotoName = (String) session.getAttribute("new_fr_PhotoName");
      String new_fr_gender = (String) session.getAttribute("new_fr_gender");
      String new_fr_email = (String) session.getAttribute("new_fr_email");
      String new_fr_phoneNum = (String) session.getAttribute("new_fr_phoneNum");
      
      System.out.println("���������� ���ǿ� ������ ģ�� ���� �̸� :" + new_fr_PhotoName);
      System.out.println("���������� ���ǿ� ������ ģ�� �̸� :" + new_fr_name);
      System.out.println("���������� ���ǿ� ������ ģ�� ���̵� :" + new_fr_id);
      
      System.out.println("new_fr_id : " + new_fr_id);
      System.out.println("new_fr_id : " + new_fr_name);
      System.out.println("new_fr_PhotoName : " + new_fr_PhotoName);   
   %>

	<table>
		<tr>
			<td id="title">���̵�</td>
			<td><%=new_fr_id%></td>
		</tr>
		<tr>
			<td id="title">�̸�</td>
			<td><%=new_fr_name%></td>
		</tr>

		<tr>
			<td id="title">����</td>
			<td><%=new_fr_gender%></td>
		</tr>

		<tr>
			<td id="title">�̸���</td>
			<td><%=new_fr_email%></td>
		</tr>

		<tr>
			<td id="title">�޴���ȭ</td>
			<td><%=new_fr_phoneNum%></td>
		</tr>
	</table>
</body>
</html>

