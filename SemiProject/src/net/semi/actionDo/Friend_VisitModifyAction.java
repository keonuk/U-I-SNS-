//�� ���� ó�� Action Ŭ����
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
		 
		 //���޹��� �Ķ���� num ������ �����մϴ�.
		 int num=Integer.parseInt(request.getParameter("v_num"));
		 
		 VisitDAO visitdao=new VisitDAO();
		 VisitBean visitdata=new VisitBean();
		 
		 
		/* //�۾��� ���� Ȯ���ϱ� ���� ����� ��й�ȣ�� �Է��� ��й�ȣ�� ���մϴ�.
		 boolean usercheck=
		 visitdao.isBoardWriter(num, request.getParameter("v_id"));
		 //���̵� �ٸ� ���
		 if(usercheck==false){
		   		response.setContentType("text/html;charset=euc-kr");
		   		PrintWriter out=response.getWriter();
		   		out.println("<script>");
		   		out.println("alert('������ ������ �����ϴ�.');");
		   		out.println("location.href='./BoardList.bo';");
		   		out.println("</script>");
		   		out.close();
		   		return null;
		 }*/
		 //���̵� ��ġ�ϴ� ���
		 try{
			 System.out.println("v_num==>"+ num);
			 System.out.println("v_id==>" + request.getParameter("v_id"));
			 System.out.println("V_CONTENT==>" +request.getParameter("V_CONTENT"));
			 
			 //���� ������ �����մϴ�.
			 visitdata.setV_NUM(num);
			 visitdata.setV_CONTENT(request.getParameter("V_CONTENT"));
			 
		    //���ǵ�  ���̵� ��������
	      	HttpSession session = request.getSession();
	    	String id = (String)session.getAttribute("id");
	   		System.out.println("getId14==> " + id);
	
		   	//DAO���� ���� �޼��� ȣ���Ͽ� �����մϴ�.
			 result = visitdao.visitModify(visitdata, id);
			 //������ ������ ���
			 if(result==false){
		   		System.out.println("�Խ��� ���� ����");
		   		response.setContentType("text/html;charset=euc-kr");//���� �ѱ� ó��
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('������ �ݵ�� �ۼ��� �ֽʽÿ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
		   		return null;
		   	 }
			 //���� ������ ���
		   	 System.out.println("�Խ��� ���� �Ϸ�");
		   	 
		   	 forward.setRedirect(true);
		   	 //������ �� ������ �����ֱ� ���� �� ���� ���� ���� �������� �̵��ϱ����� ��θ� �����մϴ�.
		   	 forward.setPath(
		   	   "./FriendVisitListAction.sr");
		   	 return forward;
	   	 }catch(Exception ex){
	   			ex.printStackTrace();	 
		 }
		 return null;
	 }
}