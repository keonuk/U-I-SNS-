//�� ��Ͽ� ���� Action Ŭ����
package net.semi.actionDo;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitBean;
import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;


public class Friend_VisitAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		System.out.println("test2");
		
		VisitDAO visitdao = new VisitDAO();
		VisitBean visitdata = new VisitBean();
		ActionForward forward = new ActionForward();

		request.setCharacterEncoding("euc-kr");
		response.setContentType("text/html;charset=euc-kr");//���� �ѱ� ó��
		
		try {
		//BoardBean ��ü�� �� ��� ������ �Է� ���� �������� �����մϴ�.
		//v_num������ 
		visitdata.setV_NUM(Integer.parseInt(request.getParameter("v_num"))+1);
		
		//���ǵ� ���̵� ������
		/*visitdata.setV_ID(request.getParameter("���̵�///"));*/
		
		//v_date ������
		visitdata.setV_TDATE(request.getParameter("v_tdate"));
		
		//v_content ������
		visitdata.setV_CONTENT(request.getParameter("v_content"));
		
		//v_photo ������
		//���ǵ�  ���̵� �� ȸ������ ��������
		HttpSession session = request.getSession();
		String new_fr_id = (String)session.getAttribute("new_fr_id");
		String fri_sex = (String)session.getAttribute("fri_sex");

/*		// ���ǵ� ģ�� ���̵� ��������
		String getId = (String) session.getAttribute("new_fr_id");*/
		
		visitdata.setV_PHOTO(fri_sex);
		visitdata.setV_ID(new_fr_id);

		
		System.out.println("setV_NUM===>"+ Integer.parseInt(request.getParameter("v_num"))+1);
		System.out.println("setV_TDATE===>"+ request.getParameter("v_tdate"));
		System.out.println("setV_CONTENT===>"+ request.getParameter("v_content"));
		
		//�� ��� ó���� ���� DAO�� boardInsert() �޼��带 ȣ���մϴ�.
		//�� ��� ������ �Է��� ������ ����Ǿ� �ִ� boarddata��ü�� �����մϴ�.
		boolean result = false;
		
		result = visitdao.visitInsert(visitdata);
		
		//�� ��Ͽ� ������ ��� null�� ��ȯ�մϴ�.
		if (result == false) {
			System.out.println("�Խ��� ��� ����");
			//request.setCharacterEncoding("euc-kr");
			response.setContentType("text/html;charset=euc-kr");//���� �ѱ� ó��
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('������ �ݵ�� �ۼ��� �ֽʽÿ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		System.out.println("�Խ��� ��� �Ϸ�");
		
		//�� ����� �Ϸ�Ǹ� �� ����� �ܼ��� �����ֱ⸸ �� ���̹Ƿ�
		//Redirect���θ� true�� �����մϴ�.
		forward.setRedirect(true);
		//�̵��� ��θ� �����մϴ�.
		forward.setPath("./FriendVisitListAction.sr");
		return forward;
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return null;
}
	

}
