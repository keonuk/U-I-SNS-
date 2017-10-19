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
		System.out.println("여기는 회원 탈퇴 Action입니다");
		int result = mdao.leave(leaveId);
		response.setContentType("text/html;charset=euc-kr");
		
		PrintWriter out = response.getWriter();
		out.println("<script>");

		// 삽입이 된 경우
		if (result == 1) {
			out.println("alert('회원 탈퇴 되었습니다.');");
			out.println("location.href = './MainView.sr';");
		}
		out.println("</script>");
		return null;
	}

}
