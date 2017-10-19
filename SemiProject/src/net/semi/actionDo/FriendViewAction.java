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
		// �α��� ���� ������� session id�� ģ�� ����� �ҷ��ɴϴ�.
		String sessionId = (String)session.getAttribute("id");
		
		// ����� id �ش��ϴ� ������ �����ͼ� friendlist ��ü�� �����մϴ�.
		friendlist = frienddao.getFriend(sessionId);
		
		// �˻� ����� ���� ���
		if(friendlist == null) {
			System.out.println("�˻� ����� �����ϴ�.");
		}
		System.out.println("�α��� ���� ����� ���̵� = " + sessionId);
				
		ActionForward forward = new ActionForward();
		request.setAttribute("friendlist", friendlist);
		
		// ������ - false : �����ķ� ����
		forward.setRedirect(false);
		
		// searchResultPage �������� �̵��ϱ� ���� ��θ� �����մϴ�.
		forward.setPath("/U&I/Page/FriendPage.jsp");
		
		return forward;
	}

}
