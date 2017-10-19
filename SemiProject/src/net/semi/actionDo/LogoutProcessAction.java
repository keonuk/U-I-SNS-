package net.semi.actionDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import semi.action.Action;
import semi.action.ActionForward;

public class LogoutProcessAction implements Action {
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		session.invalidate();
		forward.setPath("./MainView.sr");
		forward.setRedirect(true);
		
		return forward;
	}
}
