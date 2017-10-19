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

public class Visit_ListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		VisitDAO visitdao = new VisitDAO();
		List<VisitBean> visitlist = new ArrayList<VisitBean>();

		int page = 1;
		int limit = 10;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		HttpSession session = request.getSession();
		String getId = (String) session.getAttribute("id");

		int listcount = visitdao.getListCount(getId);
		visitlist = visitdao.getBoardList(page, limit, getId);
		int maxnum = visitdao.getMaxValueCount(getId);

		String sex = visitdao.getsex(getId);
		session.setAttribute("sex", sex);
		System.out.println("getSex==>" + sex);

		String w_date = visitdao.getWriteDate();
		System.out.println("w_date==>" + w_date);

		int maxpage = (listcount + limit - 1) / limit;

		int startpage = ((page - 1) / 10) * 10 + 1;

		int endpage = startpage + 10 - 1;

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

		forward.setPath("./U&I/Page/visit_list.jsp");
		return forward;
	}

}
