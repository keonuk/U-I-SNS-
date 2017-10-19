package net.semi.actionDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.FriendBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class Fri_MyPageViewAction implements Action {

	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		
		SemiDAO frienddao = new SemiDAO();
		FriendBean friendbean = new FriendBean();
		request.setCharacterEncoding("euc-kr");
		
		// 파라미터로 전달받은 검색할 사용자 아이디를  id변수에 저장합니다.
		HttpSession session = request.getSession();
		//String fr_id = (String) session.getAttribute("fr_id");
		String req_id = (String) request.getParameter("getid");
		
		//System.out.println("친구 세션 아이 : " + req_id);
		System.out.println("넘겨준값 : " + req_id);
		// 사용자 id 해당하는 내용을 가져와서 searchlist 객체에 저장합니다.
		friendbean = frienddao.getFriendInfo(req_id);
		
		System.out.println("친구 액션에서 친구 아이디 : " + friendbean.getFr_id());
		System.out.println("친구 액션에서 친구 이름: " + friendbean.getFr_name());
		System.out.println("친구 액션에서 친구 사진이름: " + friendbean.getFr_PhotoName());
		
		session.setAttribute("new_fr_id", friendbean.getFr_id());
		session.setAttribute("new_fr_name", friendbean.getFr_name());
		session.setAttribute("new_fr_PhotoName", friendbean.getFr_PhotoName());
		session.setAttribute("new_fr_gender", friendbean.getFr_gender());
		session.setAttribute("new_fr_email", friendbean.getFr_email());
		session.setAttribute("new_fr_phoneNum", friendbean.getFr_phoneNum());
		
		ActionForward forward = new ActionForward();
		request.setAttribute("friendbean", friendbean);
		
		// 포워딩 - false : 디스패쳐로 보냄
		forward.setRedirect(false);
		
		// searchResultPage 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setPath("./U&I/Page/Fri_MyPagePage.jsp");
		
		return forward;
	}

}
