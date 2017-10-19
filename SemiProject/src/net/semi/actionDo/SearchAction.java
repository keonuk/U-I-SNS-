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

public class SearchAction implements Action {

	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		
		SemiDAO frienddao = new SemiDAO();
		List<FriendBean> searchList = new ArrayList<FriendBean>();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		request.setCharacterEncoding("euc-kr");
		
		// �Ķ���ͷ� ���޹��� �˻��� ����� ���̵�  id������ �����մϴ�.
		String search = request.getParameter("search");
		
		// ����� id �ش��ϴ� ������ �����ͼ� searchlist ��ü�� �����մϴ�.
		searchList = frienddao.getMember(search, id);
		
		// �˻� ����� ���� ���
		if(searchList == null) {
			System.out.println("�˻� ����� �����ϴ�.");
		}
		System.out.println("�˻�â�� �Է��� ���� = " + search);
		
		ActionForward forward = new ActionForward();
		request.setAttribute("searchList", searchList);
		
		// ������ - false : �����ķ� ����
		forward.setRedirect(false);
		
		// searchResultPage �������� �̵��ϱ� ���� ��θ� �����մϴ�.
		forward.setPath("./U&I/Page/SearchResultPage.jsp");
		
		return forward;
	}

}
