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
		// webContent 아래에 꼭 폴더 생성하세요
		String saveFolder = "boardupload"; // 게시판 업로드 폴더
		int fileSize = 5 * 1024 * 1024; // 업로드할 파일의 최대 사이즈입니다. 5MB

		// 실제 저장 경로를 지정합니다.
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);
		// realFolder=request.getSession().getServletContext().getRealFolder()
		System.out.println("realFolder = " + realFolder);
		boolean result = false;
		String id = (String) session.getAttribute("id");

		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, realFolder, fileSize, "euc-kr", new DefaultFileRenamePolicy());
			// BoardBean 객체에 글 등록 폼에서 입력받은 정보들을 저장합니다.
			boarddata.setId(id);
			boarddata.setSubject(multi.getParameter("BOARD_SUBJECT"));
			boarddata.setContent(multi.getParameter("BOARD_CONTENT"));

			// 업로드의 파일명은 업로드한 파일의 전체 경로에서 파일 이름만 저장합니다.
			boarddata.setImage(multi.getFilesystemName((String) multi.getFileNames().nextElement()));

			// 글 등록 처리를 위해 DAO의 boardInsert()메서드를 조회합니다.
			// 글 등록 폼에서 입력한 정보가 저장되어 있는 boarddata 객체를 전달합니다.
			result = boarddao.boardInsert(boarddata);

			PrintWriter out = response.getWriter();
			// 글 등록에 실패할 경우 null을 반환합니다.
			if (result == false) {
				out.println("<script>alert('글 올리기에 실패하셨습니다.'); history.go(-1);</script>");
				System.out.println("게시판 등록 실패");
				return null;
			}
			System.out.println("게시판 등록 완료");
			out.println("<script>alert('글이 등록되었습니다.');location.href='./HomeView.sr';</script>");
			out.close();

			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
