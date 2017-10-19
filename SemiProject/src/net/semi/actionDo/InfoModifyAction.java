package net.semi.actionDo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import friend.db.FriendBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class InfoModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		SemiDAO memberdao = new SemiDAO();
		FriendBean memberdata = new FriendBean();
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println(id);

		String realFolder = "";
		// WebContent�Ʒ��� �� ���� �����ϼ���
		String saveFolder = "upload";

		int fileSize = 5 * 1024 * 1024; // ���ε��� ������ �ִ� ������ �Դϴ�. 5MB

		// ���� ���� ��θ� �����մϴ�.
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);
		// realFolder=request.getSession().getServletContext().getRealPath
		System.out.println("realFolder= " + realFolder);

		boolean result = false;

		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(
					request, 
					realFolder, 
					fileSize, 
					"euc-kr", 
					new DefaultFileRenamePolicy());

			// MemberBean ��ü�� �� ��� ������ �Է� ���� �������� �����մϴ�.
			System.out.println(multi.getParameter("password"));
			System.out.println(multi.getParameter("mail"));
			System.out.println(multi.getParameter("phone"));
			System.out.println(
					multi.getFilesystemName(
							(String)multi.getFileNames().nextElement()));
			
			memberdata.setM_Id(id);
			memberdata.setM_pass(multi.getParameter("password"));
			memberdata.setM_email(multi.getParameter("mail"));
			memberdata.setM_phoneNum(multi.getParameter("phone"));
			// ���ε��� ���ϸ��� ���ε��� ������ ��ü ��ο��� ���� �̸��� �����մϴ�.
			memberdata.setM_PhotoName(
					multi.getFilesystemName(
							(String)multi.getFileNames().nextElement()));

			FriendBean temp = memberdao.getUserInfo(id);
			System.out.println(temp.getM_Id());
			System.out.println(temp.getM_email());
			
			String prephoto = (String) session.getAttribute("PhotoName");
			// ȸ�� �������� ���� DAO�� updateMember()�޼��带 ȣ���մϴ�.
			// ���� ���� ������ �Է��� ������ ����Ǿ� �ִ� memberdata��ü�� �����մϴ�.
			result = memberdao.updateMember(memberdata, prephoto);
			
			// �ٲ� ������ ���ǰ����� ����
			session.setAttribute("PhotoName", memberdata.getM_PhotoName());

			// ���� ������ ������ ��� null�� ��ȯ�մϴ�.
			if (result == false) {
				System.out.println("ȸ�� �������� ����");
				return null;
			}
			System.out.println("�������� ����");

			// ���� ������ �Ϸ�Ǹ� ȸ�� ���� �ܼ��� �����ֱ⸸ �� ���̹Ƿ�
			// Redirect���θ� true�� �����մϴ�.
			forward.setRedirect(true);
			// �̵��� ��θ� �����մϴ�.
			forward.setPath("./MyPageView.sr");
			return forward;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
