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
		
		// �Ķ���ͷ� ���޹��� �˻��� ����� ���̵�  id������ �����մϴ�.
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		System.out.println("���������� �׼ǿ��� ���Ǿ��̵� : " + id);
		// ����� id �ش��ϴ� ������ �����ͼ� searchlist ��ü�� �����մϴ�.
		friendbean = frienddao.getUserInfo(id);
		
		// �˻� ����� ���� ���
		if(friendbean == null) {
			System.out.println("�˻� ����� �����ϴ�.");
		}
		System.out.println("�˻�â�� �Է��� ���̵� = " + id);
		
		ActionForward forward = new ActionForward();
		request.setAttribute("friendbean", friendbean);
		
		// ������ - false : �����ķ� ����
		forward.setRedirect(false);
		
		// searchResultPage �������� �̵��ϱ� ���� ��θ� �����մϴ�.
		forward.setPath("./U&I/Page/MyPagePage.jsp");
		
		return forward;
	}

}
