//�� ������ ���� Action Ŭ����
package net.semi.actionDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friend.db.VisitDAO;
import semi.action.Action;
import semi.action.ActionForward;

public class Friend_VisitDeleteAction implements Action {
	 public ActionForward execute(HttpServletRequest request,
			 HttpServletResponse response) 
	 	throws Exception{
		 
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");
		
	   	boolean result=false;
	   	boolean usercheck=false;
	   	int num=Integer.parseInt(request.getParameter("num"));
	   	
	   	VisitDAO visitdao=new VisitDAO();
	   
/*	   	//�� ���� ����� ��û�� ����ڰ� ���� �ۼ��� ��������� �Ǵ��ϱ� ���� 
	   	//�Է��� ��й�ȣ�� ����� ��й�ȣ�� ���Ͽ� ��ġ�ϸ� �����մϴ�.
	   	usercheck
	   	=visitdao.isBoardWriter(num, request.getParameter("BOARD_PASS"));
	   	
	   	//��й�ȣ ��ġ���� �ʴ� ���
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
	   	
	  //���ǵ�  ���̵� ��������
      	HttpSession session = request.getSession();
    	String id = (String)session.getAttribute("id");
   		System.out.println("getId15==> " + id);
	   	
	   	//��й�ȣ ��ġ�ϴ� ��� ���� ó�� �մϴ�.
	   	result=visitdao.visitDelete(num, id);
	   	//���� ó�� ������ ���
	   	if(result==false){
	   		System.out.println("�Խ��� ���� ����");
	   		return null;
	   	}
	   	//���� ó�� ������ ��� - �� ��� ���� ��û�� �����ϴ� �κ��Դϴ�.
	   	System.out.println("�Խ��� ���� ����");
	   	forward.setRedirect(true);
   		forward.setPath("./FriendVisitListAction.sr");
   		return forward;
	 }
}