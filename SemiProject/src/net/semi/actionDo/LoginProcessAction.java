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
				
		// 입력값과 DB에 저장된 값과 비교하기 위해  MemberDAO 객체로 데이터 빈 넘긴다
		SemiDAO mdao = new SemiDAO();
		int result = mdao.isId(id, pass);
		
		String loadPhoto = mdao.loadPhoto(id);
		String sessionName = mdao.sessionName(id);
		
		System.out.println("아이디 비밀번호 검사 결과는 " + result);
		System.out.println("사진 이름결과는 " + loadPhoto);
		System.out.println("이름 : " + sessionName);
		
		if (result == 1) {
			HttpSession session = request.getSession();
			// 로그인 성공
			session.setAttribute("id", id);
			session.setAttribute("PhotoName", loadPhoto);
			session.setAttribute("sessionName", sessionName);
			System.out.println("로그인 성공  id =" + id + " pass = " + pass);
			
			forward.setRedirect(false);
			forward.setPath("HomeView.sr");		
			return forward;
		} else {
			String message = "";
			if(result == -1)
				message = "아이디가 존재하지 않습니다.";
			else
				message = "비밀번호가 일치하지 않습니다.";
		
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
