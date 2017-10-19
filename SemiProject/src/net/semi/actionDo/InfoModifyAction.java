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
		// WebContent아래에 꼭 폴더 생성하세요
		String saveFolder = "upload";

		int fileSize = 5 * 1024 * 1024; // 업로드할 파일의 최대 사이즈 입니다. 5MB

		// 실제 저장 경로를 지정합니다.
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

			// MemberBean 객체에 글 등록 폼에서 입력 받은 정보들을 저장합니다.
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
			// 업로드의 파일명은 업로드한 파일의 전체 경로에서 파일 이름만 저장합니다.
			memberdata.setM_PhotoName(
					multi.getFilesystemName(
							(String)multi.getFileNames().nextElement()));

			FriendBean temp = memberdao.getUserInfo(id);
			System.out.println(temp.getM_Id());
			System.out.println(temp.getM_email());
			
			String prephoto = (String) session.getAttribute("PhotoName");
			// 회원 정보수정 위해 DAO의 updateMember()메서드를 호출합니다.
			// 정보 수정 폼에서 입력한 정보가 저장되어 있는 memberdata객체를 전달합니다.
			result = memberdao.updateMember(memberdata, prephoto);
			
			// 바꾼 사진을 세션값으로 갱신
			session.setAttribute("PhotoName", memberdata.getM_PhotoName());

			// 정보 수정에 실패할 경우 null을 반환합니다.
			if (result == false) {
				System.out.println("회원 정보수정 실패");
				return null;
			}
			System.out.println("정보수정 성공");

			// 정보 수정이 완료되면 회원 정보 단순히 보여주기만 할 것이므로
			// Redirect여부를 true로 설정합니다.
			forward.setRedirect(true);
			// 이동할 경로를 지정합니다.
			forward.setPath("./MyPageView.sr");
			return forward;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
