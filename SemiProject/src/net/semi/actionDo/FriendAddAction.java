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
		// 로그인 중인 사용자의 session id의 친구 목록을 불러옵니다.
		String sessionId = (String)session.getAttribute("id");
		
		String fr_id = (String)request.getParameter("fr_id");
		String fr_name = (String)request.getParameter("fr_name");
		String fr_pname = (String)request.getParameter("fr_pname");
		
		System.out.println("팔로우할 사용자 아이디 = " + fr_id);
		System.out.println("팔로우할 사용자 이름 = " + fr_name);
		System.out.println("팔로우할 사용자 사진명= " + fr_pname);
				
		// 입력값을 Friend_list 테이블에 저장하기 위해  MemberDAO 객체로 세션아이디 넘긴다.
		SemiDAO mdao = new SemiDAO();
		System.out.println("addFriend 메소드 호출 전 ");
		int result = mdao.addFriend(sessionId, fr_id);
		
		
		System.out.println("액션에서 friendlist"+friendlist);
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");

		// 삽입이 된 경우
		if (result == 1) {
			out.println("alert('팔로우했습니다.');");
			out.println("history.back();");
		} else if (result == 0) {
			out.println("alert('자기 자신은 팔로우할 수 없습니다.');");
			out.println("history.back();");
		} else if (result == 2) {
			out.println("alert('이미 팔로잉 중입니다.');");
			out.println("history.back();");
		}/*
		
		// 로그인 사용자의 sessionId, 팔로우한 사용자 fr_id에 해당하는 내용을 가져와서 friendlist 객체에 저장합니다.
		friendlist = mdao.noaddFriend(sessionId, fr_id);
		if(friendlist != null) {
			out.println("alert('이미 팔로우 중입니다.');");
			out.println("history.back();");
		}*/
		out.println("</script>");
		out.close();
		
		return null;
	}
}
