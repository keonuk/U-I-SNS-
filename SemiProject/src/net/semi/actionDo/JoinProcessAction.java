package net.semi.actionDo;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import friend.db.FriendBean;
import friend.db.SemiDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class JoinProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		FriendBean friendbean = new FriendBean();
		SemiDAO semidao = new SemiDAO();
		request.setCharacterEncoding("euc-kr");

		String realFolder = "";
		// webContent �Ʒ��� �� ���� �����ϼ���
		String saveFolder = "upload";

		int fileSize = 5 * 1024 * 1024; // ���ε��� ������ �ִ� �������Դϴ�. 5MB

		// ���� ���� ��θ� �����մϴ�.
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);
		// realFolder=request.getSession().getServletContext().getRealFolder()
		System.out.println("realFolder = " + realFolder);
		
		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(
					request, 
					realFolder, 
					fileSize, 
					"euc-kr", 
					new DefaultFileRenamePolicy());
			
			// ȸ������ ������ �Է¹��� ������ String ������ �����մϴ�.
			String id = multi.getParameter("join_id");
			String pass = multi.getParameter("join_pass");
			String name = multi.getParameter("join_name");
			String birth = multi.getParameter("join_birth");
			String gender = multi.getParameter("gender");
			String phoneNum = multi.getParameter("join_phNum");
			String email = multi.getParameter("join_email");

			// FriendBean ��ü�� ȸ�� ���� ������ �Է¹��� �������� �����մϴ�.
			friendbean.setM_Id(id);
			friendbean.setM_pass(pass);
			friendbean.setM_name(name);
			friendbean.setM_birth(birth);
			friendbean.setM_gender(gender);
			friendbean.setM_phoneNum(phoneNum);
			friendbean.setM_email(email);
			
			// ���ε��� ������ �������� ���ε��� ������ ��ü ��ο��� ���� �̸��� �����մϴ�.
			friendbean.setM_PhotoName(
					multi.getFilesystemName(
							(String) multi.getFileNames().nextElement()));
			// ȸ�� ���� ó���� ���� DAO�� insert()�޼��带 ��ȸ�մϴ�.
			// ȸ�� ���� ������ �Է��� ������ ����Ǿ� �ִ�friendbean ��ü�� �����մϴ�.
			
			System.out.println("Action���� " + friendbean.getM_PhotoName());
			
			int result = semidao.insert(friendbean);

			response.setContentType("text/html;charset=euc-kr");
			
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			// ������ �� ��� result�� 1�� ��ȯ�ϰ�, ������ ��� null�� ��ȯ�մϴ�.
			if (result == 1) {
				out.println("alert('ȯ���մϴ�~ �α��� �ϼ���.');");
				out.println("location.href = './MainView.sr';");
			} else {
				out.println("alert('���̵� �ߺ��Ǿ����ϴ�. �ٽ� �Է��ϼ���');");
				out.println("location.href = './MainView.sr';");
			}
			out.println("</script>");
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}