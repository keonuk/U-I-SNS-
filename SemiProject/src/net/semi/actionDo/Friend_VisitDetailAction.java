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
      //�������̽��� �������� �߻�޼��带 ����

      VisitDAO Visitdao = new VisitDAO();
      VisitBean Visitdata = new VisitBean();

      //<a href="./BoardDetailAction.bo?num=<%=b1.getBOARD_NUM() %>"><%=b1.getBOARD_SUBJECT() %></a>
      //num �Ķ���͸� �޾����Ƿ� getParameter("num");
      //�� ��ȣ �Ķ���� ���� num������ �����մϴ�.
      int num = Integer.parseInt(request.getParameter("num"));
      
    //���ǵ�  ���̵� �� ȸ������ ��������
      	HttpSession session = request.getSession();
    	String id = (String)session.getAttribute("id");
   		System.out.println("getId13==> " + id);
    	
      //���� ������ DAO���� ���� �� ���� ����� boarddata ��ü�� �����մϴ�.
      Visitdata = Visitdao.getDetail(num, id);

      //DAO���� ���� ������ ���� ������ ��� null�� ��ȯ�մϴ�.
      if (Visitdata == null) {
         System.out.println("�󼼺��� ����");
         return null;
      }
      System.out.println("�󼼺��� ����");

      //boarddata ��ü�� request ��ü�� �����մϴ�.
      request.setAttribute("visitdata", Visitdata);
      ActionForward forward = new ActionForward();
      forward.setRedirect(false);
      
      //�� ���� ���� �������� �̵��ϱ� ���� ��θ� �����մϴ�
      forward.setPath("./U&I/Page/fri_visit_view.jsp");
      return forward;

   }

   
   
}