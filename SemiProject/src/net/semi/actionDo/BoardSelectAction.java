package net.semi.actionDo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.SemiDAO;
import friend.db.BoardBean;
import semi.action.Action;
import semi.action.ActionForward;

public class BoardSelectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// db연결 처리하는 문장이 담기는 DAO
		SemiDAO semidao = new SemiDAO();
		List<BoardBean> boardlist = new ArrayList<BoardBean>();

		// 세션된 아이디 가져오기
		HttpSession session = request.getSession();
		String Id = (String) session.getAttribute("id");
		System.out.println("BoardSelectAction id 값" + Id);

		boardlist = semidao.getBoardSelect(Id); // 리스트를 받아옵니다.

		// 해당 페이지의 글 목록을 갖고 있는 리스트
		request.setAttribute("boardlist", boardlist);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		// 글 목록 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setPath("./U&I/Page/HomePage.jsp");
		return forward; // BoardController.java
	}

}
