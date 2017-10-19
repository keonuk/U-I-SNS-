package net.semi.actionDo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.DiaryBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class DiarySelectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");

		SemiDAO diarydao = new SemiDAO();
		List<DiaryBean> diarylist = new ArrayList<DiaryBean>();
		ActionForward forward = new ActionForward();

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		System.out.println(" 세션된 id 값은 " + id);

		diarylist = diarydao.getDiary(id);

		String jsondata = "[";
		for (int i = 0; i < diarylist.size(); i++) {

			String title = diarylist.get(i).getTitle();
			String start_date = diarylist.get(i).getStart_date();
			String end_date = diarylist.get(i).getEnd_date();

			String[] start = new String[start_date.length()];
			String[] end = new String[start_date.length()];

			String start_year = "";
			String start_month = "";
			String start_day = "";

			String end_year = "";
			String end_month = "";
			String end_day = "";
			for (int j = 0; j < start_date.length(); j++) {
				start = start_date.split(" ");
				end = end_date.split(" ");

				start_year = start[3];
				start_month = start[1];
				start_day = start[2];

				end_year = end[3];
				end_month = end[1];
				end_day = end[2];
			}
			if (start_month.equals("JAN") || end_month.equals("Jan")) {
				start_month = "0";
				end_month = "0";

			} else if (start_month.equals("FEB") || end_month.equals("FEB")) {
				start_month = "1";
				end_month = "1";

			} else if (start_month.equals("MAR") || end_month.equals("MAR")) {
				start_month = "2";
				end_month = "2";

			} else if (start_month.equals("APR") || end_month.equals("APR")) {
				start_month = "3";
				end_month = "3";

			} else if (start_month.equals("MAY") || end_month.equals("MAY")) {
				start_month = "4";
				end_month = "4";

			} else if (start_month.equals("JUN") || end_month.equals("JUN")) {
				start_month = "5";
				end_month = "5";

			} else if (start_month.equals("JUL") || end_month.equals("JUL")) {
				start_month = "6";
				end_month = "6";

			} else if (start_month.equals("Aug") || end_month.equals("Aug")) {
				start_month = "7";
				end_month = "7";
			} else if (start_month.equals("SEP") || end_month.equals("SEP")) {
				start_month = "8";
				end_month = "8";

			} else if (start_month.equals("OCT") || end_month.equals("OCT")) {
				start_month = "9";
				end_month = "9";

			} else if (start_month.equals("NOV") || end_month.equals("NOV")) {
				start_month = "10";
				end_month = "10";

			} else if (start_month.equals("Dec") || end_month.equals("Dec")) {
				start_month = "11";
				end_month = "11";

			}

			jsondata += "{title: \"" + title + "\", ";
			jsondata += "start: new Date(" + start_year + ", " + start_month + ", " + start_day + "), ";
			jsondata += "end: new Date(" + end_year + ", " + end_month + ", " + end_day + ")} ";

			if (i != diarylist.size() - 1) {
				jsondata += ", ";
			}

		}
		jsondata += "]";

		request.setAttribute("data", jsondata);

		System.out.println(" 넘겨질 데이터들의  값은 " + request.getAttribute("data"));

		forward.setRedirect(false);
		forward.setPath("/U&I/Page/DiaryPage.jsp");

		return forward;
	}

}
