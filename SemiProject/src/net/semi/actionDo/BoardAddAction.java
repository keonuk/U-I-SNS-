package net.semi.actionDo;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import friend.db.BoardBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class BoardAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SemiDAO boarddao = new SemiDAO();
		BoardBean boarddata = new BoardBean();
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();

		request.setCharacterEncoding("euc-kr");
		response.setContentType("text/html;charset=euc-kr");
		
		String realFolder = "";
		// webContent �Ʒ��� �� ���� �����ϼ���
		String saveFolder = "boardupload"; // �Խ��� ���ε� ����
		int fileSize = 5 * 1024 * 1024; // ���ε��� ������ �ִ� �������Դϴ�. 5MB

		// ���� ���� ��θ� �����մϴ�.
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);
		// realFolder=request.getSession().getServletContext().getRealFolder()
		System.out.println("realFolder = " + realFolder);
		boolean result = false;
		String id = (String) session.getAttribute("id");

		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, realFolder, fileSize, "euc-kr", new DefaultFileRenamePolicy());
			// BoardBean ��ü�� �� ��� ������ �Է¹��� �������� �����մϴ�.
			boarddata.setId(id);
			boarddata.setSubject(multi.getParameter("BOARD_SUBJECT"));
			boarddata.setContent(multi.getParameter("BOARD_CONTENT"));

			// ���ε��� ���ϸ��� ���ε��� ������ ��ü ��ο��� ���� �̸��� �����մϴ�.
			boarddata.setImage(multi.getFilesystemName((String) multi.getFileNames().nextElement()));

			// �� ��� ó���� ���� DAO�� boardInsert()�޼��带 ��ȸ�մϴ�.
			// �� ��� ������ �Է��� ������ ����Ǿ� �ִ� boarddata ��ü�� �����մϴ�.
			result = boarddao.boardInsert(boarddata);

			PrintWriter out = response.getWriter();
			// �� ��Ͽ� ������ ��� null�� ��ȯ�մϴ�.
			if (result == false) {
				out.println("<script>alert('�� �ø��⿡ �����ϼ̽��ϴ�.'); history.go(-1);</script>");
				System.out.println("�Խ��� ��� ����");
				return null;
			}
			System.out.println("�Խ��� ��� �Ϸ�");
			out.println("<script>alert('���� ��ϵǾ����ϴ�.');location.href='./HomeView.sr';</script>");
			out.close();

			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
