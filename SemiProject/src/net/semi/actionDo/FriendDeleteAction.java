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
		
		// 로그인 중인 사용자 sessionid의 친구 목록을 불러옵니다.
		String sessionId = (String)session.getAttribute("id");
		String fr_id = (String)request.getParameter("fr_id");

		System.out.println("로그인 된 사용자 아이디 = " + sessionId);
		System.out.println("언팔로우할 사용자 아이디 = " + fr_id);
		
		
				
		// 입력값을 Friend_list 테이블에 저장하기 위해  MemberDAO 객체로 세션아이디 넘긴다.
		SemiDAO mdao = new SemiDAO();
		int result = mdao.deleteFriend(sessionId, fr_id);
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		// 삭제가 된 경우
		if (result == 1) {
			out.println("alert('팔로우 취소 했습니다.');");
			out.println("location.href = './FriendView.sr';");
		}
		out.println("</script>");
		out.close();
		
		return null;
	}
}
