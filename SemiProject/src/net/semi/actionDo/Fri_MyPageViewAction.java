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
		
		// �Ķ���ͷ� ���޹��� �˻��� ����� ���̵�  id������ �����մϴ�.
		HttpSession session = request.getSession();
		//String fr_id = (String) session.getAttribute("fr_id");
		String req_id = (String) request.getParameter("getid");
		
		//System.out.println("ģ�� ���� ���� : " + req_id);
		System.out.println("�Ѱ��ذ� : " + req_id);
		// ����� id �ش��ϴ� ������ �����ͼ� searchlist ��ü�� �����մϴ�.
		friendbean = frienddao.getFriendInfo(req_id);
		
		System.out.println("ģ�� �׼ǿ��� ģ�� ���̵� : " + friendbean.getFr_id());
		System.out.println("ģ�� �׼ǿ��� ģ�� �̸�: " + friendbean.getFr_name());
		System.out.println("ģ�� �׼ǿ��� ģ�� �����̸�: " + friendbean.getFr_PhotoName());
		
		session.setAttribute("new_fr_id", friendbean.getFr_id());
		session.setAttribute("new_fr_name", friendbean.getFr_name());
		session.setAttribute("new_fr_PhotoName", friendbean.getFr_PhotoName());
		session.setAttribute("new_fr_gender", friendbean.getFr_gender());
		session.setAttribute("new_fr_email", friendbean.getFr_email());
		session.setAttribute("new_fr_phoneNum", friendbean.getFr_phoneNum());
		
		ActionForward forward = new ActionForward();
		request.setAttribute("friendbean", friendbean);
		
		// ������ - false : �����ķ� ����
		forward.setRedirect(false);
		
		// searchResultPage �������� �̵��ϱ� ���� ��θ� �����մϴ�.
		forward.setPath("./U&I/Page/Fri_MyPagePage.jsp");
		
		return forward;
	}

}
