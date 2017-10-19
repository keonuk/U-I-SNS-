//글 등록에 대한 Action 클래스
package net.semi.actionDo;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitBean;
import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;


public class Friend_VisitAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		System.out.println("test2");
		
		VisitDAO visitdao = new VisitDAO();
		VisitBean visitdata = new VisitBean();
		ActionForward forward = new ActionForward();

		request.setCharacterEncoding("euc-kr");
		response.setContentType("text/html;charset=euc-kr");//응답 한글 처리
		
		try {
		//BoardBean 객체에 글 등록 폼에서 입력 받은 정보들을 저장합니다.
		//v_num값저장 
		visitdata.setV_NUM(Integer.parseInt(request.getParameter("v_num"))+1);
		
		//세션된 아이디 값저장
		/*visitdata.setV_ID(request.getParameter("아이디///"));*/
		
		//v_date 값저장
		visitdata.setV_TDATE(request.getParameter("v_tdate"));
		
		//v_content 값저장
		visitdata.setV_CONTENT(request.getParameter("v_content"));
		
		//v_photo 값저장
		//세션된  아이디 및 회원성별 가져오기
		HttpSession session = request.getSession();
		String new_fr_id = (String)session.getAttribute("new_fr_id");
		String fri_sex = (String)session.getAttribute("fri_sex");

/*		// 세션된 친구 아이디 가져오기
		String getId = (String) session.getAttribute("new_fr_id");*/
		
		visitdata.setV_PHOTO(fri_sex);
		visitdata.setV_ID(new_fr_id);

		
		System.out.println("setV_NUM===>"+ Integer.parseInt(request.getParameter("v_num"))+1);
		System.out.println("setV_TDATE===>"+ request.getParameter("v_tdate"));
		System.out.println("setV_CONTENT===>"+ request.getParameter("v_content"));
		
		//글 등록 처리를 위해 DAO의 boardInsert() 메서드를 호출합니다.
		//글 등록 폼에서 입력한 정보가 저장되어 있는 boarddata객체를 전달합니다.
		boolean result = false;
		
		result = visitdao.visitInsert(visitdata);
		
		//글 등록에 실패할 경우 null을 반환합니다.
		if (result == false) {
			System.out.println("게시판 등록 실패");
			//request.setCharacterEncoding("euc-kr");
			response.setContentType("text/html;charset=euc-kr");//응답 한글 처리
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('내용을 반드시 작성해 주십시오.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		System.out.println("게시판 등록 완료");
		
		//글 등록이 완료되면 글 목록을 단순히 보여주기만 할 것이므로
		//Redirect여부를 true로 설정합니다.
		forward.setRedirect(true);
		//이동할 경로를 지정합니다.
		forward.setPath("./FriendVisitListAction.sr");
		return forward;
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return null;
}
	

}
