//글 삭제에 대한 Action 클래스
package net.semi.actionDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class VisitDeleteAction implements Action {
	 public ActionForward execute(HttpServletRequest request,
			 HttpServletResponse response) 
	 	throws Exception{
		 
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");
		
	   	boolean result=false;
	   	boolean usercheck=false;
	   	int num=Integer.parseInt(request.getParameter("num"));
	   	
	   	VisitDAO visitdao=new VisitDAO();
	   
/*	   	//글 삭제 명령을 요청한 사용자가 글을 작성한 사용자인지 판단하기 위해 
	   	//입력한 비밀번호와 저장된 비밀번호를 비교하여 일치하면 삭제합니다.
	   	usercheck
	   	=visitdao.isBoardWriter(num, request.getParameter("BOARD_PASS"));
	   	
	   	//비밀번호 일치하지 않는 경우
	   	if(usercheck==false){
	   		response.setContentType("text/html;charset=euc-kr");
	   		PrintWriter out=response.getWriter();
	   		out.println("<script>");
	   		out.println("alert('삭제할 권한이 없습니다.');");
	   		out.println("location.href='./BoardList.bo';");
	   		out.println("</script>");
	   		out.close();
	   		return null;
	   	}*/
	   	
	  //세션된  아이디 가져오기
      	HttpSession session = request.getSession();
    	String id = (String)session.getAttribute("id");
   		System.out.println("getId15==> " + id);
	   	
	   	//비밀번호 일치하는 경우 삭제 처리 합니다.
	   	result=visitdao.visitDelete(num, id);
	   	//삭제 처리 실패한 경우
	   	if(result==false){
	   		System.out.println("게시판 삭제 실패");
	   		return null;
	   	}
	   	//삭제 처리 성공한 경우 - 글 목록 보기 요청을 전송하는 부분입니다.
	   	System.out.println("게시판 삭제 성공");
	   	forward.setRedirect(true);
   		forward.setPath("./VisitListAction.sr");
   		return forward;
	 }
}