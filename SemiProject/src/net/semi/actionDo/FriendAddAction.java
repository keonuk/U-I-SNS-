package net.semi.actionDo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.FriendBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class FriendAddAction implements Action {
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		
		request.setCharacterEncoding("euc-kr");
		List<FriendBean> friendlist = new ArrayList<FriendBean>();
		HttpSession session = request.getSession();
		// �α��� ���� ������� session id�� ģ�� ����� �ҷ��ɴϴ�.
		String sessionId = (String)session.getAttribute("id");
		
		String fr_id = (String)request.getParameter("fr_id");
		String fr_name = (String)request.getParameter("fr_name");
		String fr_pname = (String)request.getParameter("fr_pname");
		
		System.out.println("�ȷο��� ����� ���̵� = " + fr_id);
		System.out.println("�ȷο��� ����� �̸� = " + fr_name);
		System.out.println("�ȷο��� ����� ������= " + fr_pname);
				
		// �Է°��� Friend_list ���̺� �����ϱ� ����  MemberDAO ��ü�� ���Ǿ��̵� �ѱ��.
		SemiDAO mdao = new SemiDAO();
		System.out.println("addFriend �޼ҵ� ȣ�� �� ");
		int result = mdao.addFriend(sessionId, fr_id);
		
		
		System.out.println("�׼ǿ��� friendlist"+friendlist);
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");

		// ������ �� ���
		if (result == 1) {
			out.println("alert('�ȷο��߽��ϴ�.');");
			out.println("history.back();");
		} else if (result == 0) {
			out.println("alert('�ڱ� �ڽ��� �ȷο��� �� �����ϴ�.');");
			out.println("history.back();");
		} else if (result == 2) {
			out.println("alert('�̹� �ȷ��� ���Դϴ�.');");
			out.println("history.back();");
		}/*
		
		// �α��� ������� sessionId, �ȷο��� ����� fr_id�� �ش��ϴ� ������ �����ͼ� friendlist ��ü�� �����մϴ�.
		friendlist = mdao.noaddFriend(sessionId, fr_id);
		if(friendlist != null) {
			out.println("alert('�̹� �ȷο� ���Դϴ�.');");
			out.println("history.back();");
		}*/
		out.println("</script>");
		out.close();
		
		return null;
	}
}
