package net.semi.actionDo;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class DiaryInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=euc-kr");
		
		ActionForward forward = new ActionForward();

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String title = request.getParameter("title");
		String start_date = request.getParameter("start");
		String end_date = request.getParameter("end");

		String title2 = URLDecoder.decode(title, "UTF-8");
		System.out.println("쿼리스트링"+request.getQueryString());
		
		System.out.println("다이어리 insertAction에서 일정 내용 : " + title2);
		String start = start_date.substring(0, 24);
		String end = end_date.substring(0, 24);

		SemiDAO mdao = new SemiDAO();
		int result = mdao.insertDiary(id, title, start, end);

		if (result == 1) {
			forward.setRedirect(false);
			forward.setPath("/U&I/Page/DiaryPage.jsp");
			return forward;
		} else {
			return null;
		}

	}

}
