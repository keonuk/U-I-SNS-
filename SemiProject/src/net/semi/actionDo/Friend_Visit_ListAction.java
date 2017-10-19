package net.semi.actionDo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitBean;
import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class Friend_Visit_ListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		VisitDAO visitdao = new VisitDAO();
		List<VisitBean> visitlist = new ArrayList<VisitBean>();

		int page = 1;
		int limit = 10;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		// 세션된 친구 아이디 가져오기
		HttpSession session = request.getSession();
		String getId = (String) session.getAttribute("new_fr_id");
		System.out.println("FRI_Visit_ListAction.java==>new_fr_id ==" + getId);

		// 세션된  아이디 가져오기
		String session_id = (String) session.getAttribute("id");
		System.out.println("FRI_Visit_ListAction.java==>session_id ==" + session_id);
		
		int listcount = visitdao.getListCount(getId, session_id); // 총건수 구하기
		visitlist = visitdao.getBoardList(page, limit, getId, session_id); // 총 리스트 구하기
		int maxnum = visitdao.getMaxValueCount(getId); // 넘버 값 구하기

		// 친구 성별 구하기
		String new_fr_sex = visitdao.getsex(getId);
		System.out.println("FRI_Visit_ListAction.java==>fri_sex ==" + new_fr_sex);
		session.setAttribute("fri_sex", new_fr_sex);
		
		
		//=================================================================================//
		

		// 세션된 성별 구하기
		String session_sex = visitdao.getsex(session_id);
		System.out.println("FRI_Visit_ListAction.java==>session_sex ==" + session_sex);
		session.setAttribute("session_sex", session_sex);
		//===================================================================================//

		String w_date = visitdao.getWriteDate();
		System.out.println("w_date==>" + w_date);

		/*
		 * 총 페이지 수 = (DB에 저장된 총 리스트의 수 + 한 페이지에서 보여주는 리스트의 수 - 1)/한 페이지에서 보여주는
		 * 리스트의 수
		 * 
		 * 예를 들어 한페이지에서 보여주는 리스트의 수가 10개인 경우 예1) DB에 저장된 총 리스트의 수가 0이면 총 페이지수는
		 * 0페이지 예2) DB에 저장된 총 리스트의 수가 ( 1-10 )이면 총 페이지수는 1페이지 예3) DB에 저장된 총 리스트의
		 * 수가 ( 11-20 )이면 총 페이지수는 2페이지 예4) DB에 저장된 총 리스트의 수가 ( 21-30 )이면 총 페이지수는
		 * 3페이지
		 * 
		 */

		int maxpage = (listcount + limit - 1) / limit;

		/*
		 * startpage : 현재 페이지 그룹에서 맨 처음에 표시될 페이지 수를 의미합니다. ([1], [11], [21]
		 * 등...) 보여줄 페이지가 30개일 경우 [1][2][3] ... [30]까지 다 표시되면 너무 많기 때문에 보통 한
		 * 페이지에는 10페이지 정도까지 이동할 수 있게 표시합니다. 예) 페이지 그룹이 아래와 같은 경우
		 * [1][2][3][4][5][6][7][8][9][10] 페이지 그룹의 시작 페이지는 startpage에 마지막 페이지는
		 * endpage에 구합니다.
		 * 
		 * 예로 1~10페이지의 내용을 나타낼때는 페이지 그룹은 [1][2]...[10]로 표시되고 11~20페이지의 내용을
		 * 페이지 그룹은 [11][12][13]..[20]로 표시됩니다.
		 */
		int startpage = ((page - 1) / 10) * 10 + 1;

		int endpage = startpage + 10 - 1;

		/*
		 * 마지막 그룹의 마지막 페이지 값은 최대 페이지 값입니다. 예로 마지막 페이지 그룹이 [21] - [30]인 경우
		 * 시작페이지(startpage => 21)와 마지막페이지(endpage => 30) 이지만 최대 페이지(maxpage)가
		 * 25라면 [21] - [25]까지만 표시되도록 합니다.
		 */

		if (endpage > maxpage)
			endpage = maxpage;

		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);

		request.setAttribute("startpage", startpage);

		request.setAttribute("endpage", endpage);

		request.setAttribute("listcount", listcount);

		request.setAttribute("maxnum", maxnum);

		request.setAttribute("visitlist", visitlist);

		request.setAttribute("w_date", w_date);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		forward.setPath("./U&I/Page/fri_visit_list.jsp");
		return forward;
	}

}
