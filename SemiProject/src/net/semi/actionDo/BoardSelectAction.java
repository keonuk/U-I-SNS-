package net.semi.actionDo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.SemiDAO;
import friend.db.BoardBean;
import semi.action.Action;
import semi.action.ActionForward;

public class BoardSelectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// db���� ó���ϴ� ������ ���� DAO
		SemiDAO semidao = new SemiDAO();
		List<BoardBean> boardlist = new ArrayList<BoardBean>();

		// ���ǵ� ���̵� ��������
		HttpSession session = request.getSession();
		String Id = (String) session.getAttribute("id");
		System.out.println("BoardSelectAction id ��" + Id);

		boardlist = semidao.getBoardSelect(Id); // ����Ʈ�� �޾ƿɴϴ�.

		// �ش� �������� �� ����� ���� �ִ� ����Ʈ
		request.setAttribute("boardlist", boardlist);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		// �� ��� �������� �̵��ϱ� ���� ��θ� �����մϴ�.
		forward.setPath("./U&I/Page/HomePage.jsp");
		return forward; // BoardController.java
	}

}
