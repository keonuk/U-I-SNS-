//글 수정 처리 Action 클래스
package net.semi.actionDo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitBean;
import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;

 public class Friend_VisitModifyAction implements Action {
	 public ActionForward execute(HttpServletRequest request,
			                      HttpServletResponse response) 
	 	throws Exception{
		 request.setCharacterEncoding("euc-kr");
		 ActionForward forward = new ActionForward();
		 boolean result = false;
		 
		 //전달받은 파라미터 num 변수에 저장합니다.
		 int num=Integer.parseInt(request.getParameter("v_num"));
		 
		 VisitDAO visitdao=new VisitDAO();
		 VisitBean visitdata=new VisitBean();
		 
		 
		/* //글쓴이 인지 확인하기 위해 저장된 비밀번호와 입력한 비밀번호를 비교합니다.
		 boolean usercheck=
		 visitdao.isBoardWriter(num, request.getParameter("v_id"));
		 //아이디가 다른 경우
		 if(usercheck==false){
		   		response.setContentType("text/html;charset=euc-kr");
		   		PrintWriter out=response.getWriter();
		   		out.println("<script>");
		   		out.println("alert('수정할 권한이 없습니다.');");
		   		out.println("location.href='./BoardList.bo';");
		   		out.println("</script>");
		   		out.close();
		   		return null;
		 }*/
		 //아이디가 일치하는 경우
		 try{
			 System.out.println("v_num==>"+ num);
			 System.out.println("v_id==>" + request.getParameter("v_id"));
			 System.out.println("V_CONTENT==>" +request.getParameter("V_CONTENT"));
			 
			 //수정 내용을 설정합니다.
			 visitdata.setV_NUM(num);
			 visitdata.setV_CONTENT(request.getParameter("V_CONTENT"));
			 
		    //세션된  아이디 가져오기
	      	HttpSession session = request.getSession();
	    	String id = (String)session.getAttribute("id");
	   		System.out.println("getId14==> " + id);
	
		   	//DAO에서 수정 메서드 호출하여 수정합니다.
			 result = visitdao.visitModify(visitdata, id);
			 //수정에 실패한 경우
			 if(result==false){
		   		System.out.println("게시판 수정 실패");
		   		response.setContentType("text/html;charset=euc-kr");//응답 한글 처리
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('내용을 반드시 작성해 주십시오.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
		   		return null;
		   	 }
			 //수정 성공의 경우
		   	 System.out.println("게시판 수정 완료");
		   	 
		   	 forward.setRedirect(true);
		   	 //수정한 글 내용을 보여주기 위해 글 내용 보기 보기 페이지로 이동하기위해 경로를 설정합니다.
		   	 forward.setPath(
		   	   "./FriendVisitListAction.sr");
		   	 return forward;
	   	 }catch(Exception ex){
	   			ex.printStackTrace();	 
		 }
		 return null;
	 }
}