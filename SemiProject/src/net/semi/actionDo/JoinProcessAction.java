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
		// webContent 아래에 꼭 폴더 생성하세요
		String saveFolder = "upload";

		int fileSize = 5 * 1024 * 1024; // 업로드할 파일의 최대 사이즈입니다. 5MB

		// 실제 저장 경로를 지정합니다.
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
			
			// 회원가입 폼에서 입력받은 정보를 String 변수에 저장합니다.
			String id = multi.getParameter("join_id");
			String pass = multi.getParameter("join_pass");
			String name = multi.getParameter("join_name");
			String birth = multi.getParameter("join_birth");
			String gender = multi.getParameter("gender");
			String phoneNum = multi.getParameter("join_phNum");
			String email = multi.getParameter("join_email");

			// FriendBean 객체에 회원 가입 폼에서 입력받은 정보들을 저장합니다.
			friendbean.setM_Id(id);
			friendbean.setM_pass(pass);
			friendbean.setM_name(name);
			friendbean.setM_birth(birth);
			friendbean.setM_gender(gender);
			friendbean.setM_phoneNum(phoneNum);
			friendbean.setM_email(email);
			
			// 업로드의 프로필 사진명은 업로드한 파일의 전체 경로에서 파일 이름만 저장합니다.
			friendbean.setM_PhotoName(
					multi.getFilesystemName(
							(String) multi.getFileNames().nextElement()));
			// 회원 가입 처리를 위해 DAO의 insert()메서드를 조회합니다.
			// 회원 가입 폼에서 입력한 정보가 저장되어 있는friendbean 객체를 전달합니다.
			
			System.out.println("Action에서 " + friendbean.getM_PhotoName());
			
			int result = semidao.insert(friendbean);

			response.setContentType("text/html;charset=euc-kr");
			
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			// 삽입이 된 경우 result는 1을 반환하고, 실패할 경우 null을 반환합니다.
			if (result == 1) {
				out.println("alert('환영합니다~ 로그인 하세요.');");
				out.println("location.href = './MainView.sr';");
			} else {
				out.println("alert('아이디가 중복되었습니다. 다시 입력하세요');");
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