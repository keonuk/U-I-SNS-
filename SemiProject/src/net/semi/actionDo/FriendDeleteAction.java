package net.semi.actionDo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class FriendDeleteAction implements Action {
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		ActionForward forward = new ActionForward();

		request.setCharacterEncoding("euc-kr");

		HttpSession session = request.getSession();
		
		// �α��� ���� ����� sessionid�� ģ�� ����� �ҷ��ɴϴ�.
		String sessionId = (String)session.getAttribute("id");
		String fr_id = (String)request.getParameter("fr_id");

		System.out.println("�α��� �� ����� ���̵� = " + sessionId);
		System.out.println("���ȷο��� ����� ���̵� = " + fr_id);
		
		
				
		// �Է°��� Friend_list ���̺� �����ϱ� ����  MemberDAO ��ü�� ���Ǿ��̵� �ѱ��.
		SemiDAO mdao = new SemiDAO();
		int result = mdao.deleteFriend(sessionId, fr_id);
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		// ������ �� ���
		if (result == 1) {
			out.println("alert('�ȷο� ��� �߽��ϴ�.');");
			out.println("location.href = './FriendView.sr';");
		}
		out.println("</script>");
		out.close();
		
		return null;
	}
}
