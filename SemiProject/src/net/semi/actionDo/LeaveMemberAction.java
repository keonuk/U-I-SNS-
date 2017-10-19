package net.semi.actionDo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class LeaveMemberAction implements Action {

	@Override
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		SemiDAO mdao = new SemiDAO();
		
		HttpSession session = request.getSession();
		String leaveId = (String) session.getAttribute("id");
		System.out.println("����� ȸ�� Ż�� Action�Դϴ�");
		int result = mdao.leave(leaveId);
		response.setContentType("text/html;charset=euc-kr");
		
		PrintWriter out = response.getWriter();
		out.println("<script>");

		// ������ �� ���
		if (result == 1) {
			out.println("alert('ȸ�� Ż�� �Ǿ����ϴ�.');");
			out.println("location.href = './MainView.sr';");
		}
		out.println("</script>");
		return null;
	}

}
