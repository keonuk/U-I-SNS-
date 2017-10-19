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

		// ���ǵ� ģ�� ���̵� ��������
		HttpSession session = request.getSession();
		String getId = (String) session.getAttribute("new_fr_id");
		System.out.println("FRI_Visit_ListAction.java==>new_fr_id ==" + getId);

		// ���ǵ�  ���̵� ��������
		String session_id = (String) session.getAttribute("id");
		System.out.println("FRI_Visit_ListAction.java==>session_id ==" + session_id);
		
		int listcount = visitdao.getListCount(getId, session_id); // �ѰǼ� ���ϱ�
		visitlist = visitdao.getBoardList(page, limit, getId, session_id); // �� ����Ʈ ���ϱ�
		int maxnum = visitdao.getMaxValueCount(getId); // �ѹ� �� ���ϱ�

		// ģ�� ���� ���ϱ�
		String new_fr_sex = visitdao.getsex(getId);
		System.out.println("FRI_Visit_ListAction.java==>fri_sex ==" + new_fr_sex);
		session.setAttribute("fri_sex", new_fr_sex);
		
		
		//=================================================================================//
		

		// ���ǵ� ���� ���ϱ�
		String session_sex = visitdao.getsex(session_id);
		System.out.println("FRI_Visit_ListAction.java==>session_sex ==" + session_sex);
		session.setAttribute("session_sex", session_sex);
		//===================================================================================//

		String w_date = visitdao.getWriteDate();
		System.out.println("w_date==>" + w_date);

		/*
		 * �� ������ �� = (DB�� ����� �� ����Ʈ�� �� + �� ���������� �����ִ� ����Ʈ�� �� - 1)/�� ���������� �����ִ�
		 * ����Ʈ�� ��
		 * 
		 * ���� ��� ������������ �����ִ� ����Ʈ�� ���� 10���� ��� ��1) DB�� ����� �� ����Ʈ�� ���� 0�̸� �� ����������
		 * 0������ ��2) DB�� ����� �� ����Ʈ�� ���� ( 1-10 )�̸� �� ���������� 1������ ��3) DB�� ����� �� ����Ʈ��
		 * ���� ( 11-20 )�̸� �� ���������� 2������ ��4) DB�� ����� �� ����Ʈ�� ���� ( 21-30 )�̸� �� ����������
		 * 3������
		 * 
		 */

		int maxpage = (listcount + limit - 1) / limit;

		/*
		 * startpage : ���� ������ �׷쿡�� �� ó���� ǥ�õ� ������ ���� �ǹ��մϴ�. ([1], [11], [21]
		 * ��...) ������ �������� 30���� ��� [1][2][3] ... [30]���� �� ǥ�õǸ� �ʹ� ���� ������ ���� ��
		 * ���������� 10������ �������� �̵��� �� �ְ� ǥ���մϴ�. ��) ������ �׷��� �Ʒ��� ���� ���
		 * [1][2][3][4][5][6][7][8][9][10] ������ �׷��� ���� �������� startpage�� ������ ��������
		 * endpage�� ���մϴ�.
		 * 
		 * ���� 1~10�������� ������ ��Ÿ������ ������ �׷��� [1][2]...[10]�� ǥ�õǰ� 11~20�������� ������
		 * ������ �׷��� [11][12][13]..[20]�� ǥ�õ˴ϴ�.
		 */
		int startpage = ((page - 1) / 10) * 10 + 1;

		int endpage = startpage + 10 - 1;

		/*
		 * ������ �׷��� ������ ������ ���� �ִ� ������ ���Դϴ�. ���� ������ ������ �׷��� [21] - [30]�� ���
		 * ����������(startpage => 21)�� ������������(endpage => 30) ������ �ִ� ������(maxpage)��
		 * 25��� [21] - [25]������ ǥ�õǵ��� �մϴ�.
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
