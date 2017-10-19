package net.semi.actionDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitBean;
import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class Friend_VisitDetailAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("euc-kr");
      //인터페이스에 만들어놓은 추상메서드를 구현

      VisitDAO Visitdao = new VisitDAO();
      VisitBean Visitdata = new VisitBean();

      //<a href="./BoardDetailAction.bo?num=<%=b1.getBOARD_NUM() %>"><%=b1.getBOARD_SUBJECT() %></a>
      //num 파라미터를 받았으므로 getParameter("num");
      //글 번호 파라미터 값을 num변수에 저장합니다.
      int num = Integer.parseInt(request.getParameter("num"));
      
    //세션된  아이디 및 회원성별 가져오기
      	HttpSession session = request.getSession();
    	String id = (String)session.getAttribute("id");
   		System.out.println("getId13==> " + id);
    	
      //글의 내용을 DAO에서 읽은 후 얻은 결과를 boarddata 객체에 저장합니다.
      Visitdata = Visitdao.getDetail(num, id);

      //DAO에서 글의 내용을 읽지 못했을 경우 null을 반환합니다.
      if (Visitdata == null) {
         System.out.println("상세보기 실패");
         return null;
      }
      System.out.println("상세보기 성공");

      //boarddata 객체를 request 객체에 저장합니다.
      request.setAttribute("visitdata", Visitdata);
      ActionForward forward = new ActionForward();
      forward.setRedirect(false);
      
      //글 내용 보기 페이지로 이동하기 위해 경로를 생성합니다
      forward.setPath("./U&I/Page/fri_visit_view.jsp");
      return forward;

   }

   
   
}