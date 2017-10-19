package net.semi.actionDo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class LoginProcessAction implements Action {
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		ActionForward forward = new ActionForward();

		request.setCharacterEncoding("euc-kr");
		
		String id = request.getParameter("login_id");
		String pass = request.getParameter("login_pass");
				
		// �Է°��� DB�� ����� ���� ���ϱ� ����  MemberDAO ��ü�� ������ �� �ѱ��
		SemiDAO mdao = new SemiDAO();
		int result = mdao.isId(id, pass);
		
		String loadPhoto = mdao.loadPhoto(id);
		String sessionName = mdao.sessionName(id);
		
		System.out.println("���̵� ��й�ȣ �˻� ����� " + result);
		System.out.println("���� �̸������ " + loadPhoto);
		System.out.println("�̸� : " + sessionName);
		
		if (result == 1) {
			HttpSession session = request.getSession();
			// �α��� ����
			session.setAttribute("id", id);
			session.setAttribute("PhotoName", loadPhoto);
			session.setAttribute("sessionName", sessionName);
			System.out.println("�α��� ����  id =" + id + " pass = " + pass);
			
			forward.setRedirect(false);
			forward.setPath("HomeView.sr");		
			return forward;
		} else {
			String message = "";
			if(result == -1)
				message = "���̵� �������� �ʽ��ϴ�.";
			else
				message = "��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
		
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + message + "');");
			out.println("location.href = './MainView.sr';");
			out.println("</script>");
			out.close();

			return null;
		}
	}
}
