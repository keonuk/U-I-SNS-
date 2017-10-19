package net.semi.actionDo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.FriendBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class FriendViewAction implements Action {

	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		
		SemiDAO frienddao = new SemiDAO();
		List<FriendBean> friendlist = new ArrayList<FriendBean>();
		request.setCharacterEncoding("euc-kr");
		
		HttpSession session = request.getSession();
		// 로그인 중인 사용자의 session id의 친구 목록을 불러옵니다.
		String sessionId = (String)session.getAttribute("id");
		
		// 사용자 id 해당하는 내용을 가져와서 friendlist 객체에 저장합니다.
		friendlist = frienddao.getFriend(sessionId);
		
		// 검색 결과가 없는 경우
		if(friendlist == null) {
			System.out.println("검색 목록이 없습니다.");
		}
		System.out.println("로그인 중인 사용자 아이디 = " + sessionId);
				
		ActionForward forward = new ActionForward();
		request.setAttribute("friendlist", friendlist);
		
		// 포워딩 - false : 디스패쳐로 보냄
		forward.setRedirect(false);
		
		// searchResultPage 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setPath("/U&I/Page/FriendPage.jsp");
		
		return forward;
	}

}
