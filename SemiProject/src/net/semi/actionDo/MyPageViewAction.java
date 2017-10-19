package net.semi.actionDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.FriendBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class MyPageViewAction implements Action {

	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		
		SemiDAO frienddao = new SemiDAO();
		FriendBean friendbean = new FriendBean();
		request.setCharacterEncoding("euc-kr");
		
		// 파라미터로 전달받은 검색할 사용자 아이디를  id변수에 저장합니다.
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		System.out.println("마이페이지 액션에서 세션아이디 : " + id);
		// 사용자 id 해당하는 내용을 가져와서 searchlist 객체에 저장합니다.
		friendbean = frienddao.getUserInfo(id);
		
		// 검색 결과가 없는 경우
		if(friendbean == null) {
			System.out.println("검색 목록이 없습니다.");
		}
		System.out.println("검색창에 입력한 아이디 = " + id);
		
		ActionForward forward = new ActionForward();
		request.setAttribute("friendbean", friendbean);
		
		// 포워딩 - false : 디스패쳐로 보냄
		forward.setRedirect(false);
		
		// searchResultPage 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setPath("./U&I/Page/MyPagePage.jsp");
		
		return forward;
	}

}
