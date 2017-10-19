package net.semi.actionDo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.action.Action;
import semi.action.ActionForward;

@WebServlet("*.sr")
public class UNIFrontController extends javax.servlet.http.HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * ��û�� ��ü URL�߿��� ��Ʈ ��ȣ ���� ���� ������ ���ڿ����� ��ȯ�˴ϴ�. ��)
		 * http://localhost:8088/JspProject/BoardList.bo�� ���
		 * "/JspProject/BoardList.bo" ��ȯ�˴ϴ�.
		 */
		String RequestURI = request.getRequestURI();
		System.out.println("RequestURI2 = " + RequestURI);

		// getContextPath() : ���ؽ�Ʈ ��ΰ� ��ȯ�˴ϴ�.
		// contextPath�� "/JspProject"�� ��ȯ�˴ϴ�.
		String contextPath = request.getContextPath();
		System.out.println("contextPath2 = " + contextPath);

		// RequestURI���� ���ؽ�Ʈ ��� ���� ���� �ε��� ��ġ�� ���ں���
		// ������ ��ġ�� ���ڱ��� �����մϴ�.
		// command�� "/BoardList.bo" ��ȯ�˴ϴ�.
		String command = RequestURI.substring(contextPath.length());
		System.out.println("command2 = " + command);

		// �ʱ�ȭ
		ActionForward forward = null;
		Action action = null;

		if (command.equals("/MainView.sr")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./U&I/Page/MainPage.jsp");
		} else if (command.equals("/loginProcess.sr")) {
			action = new LoginProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/joinProcess.sr")) {
			action = new JoinProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/logoutProcess.sr")) {
			action = new LogoutProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/SearchView.sr")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./U&I/Page/SearchPage.jsp");
		} else if (command.equals("/AddFriend.sr")) {
			action = new FriendAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/FriendDeleteAction.sr")) {
			action = new FriendDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/HomeView.sr")) {
			action = new BoardSelectAction(); // �����ǵ� �����͸� �����´�.
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/BoardAdd.sr")) {
			action = new BoardAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ������ ���̾ ��Ʈ�ѷ�
		else if (command.equals("/DiaryView.sr")) {
			action = new DiarySelectAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/ajaxinsert.sr")) {
			action = new DiaryInsertAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// SCMOON Write Visit Source
		else if (command.equals("/VisitListAction.sr")) {
			action = new Visit_ListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (command.equals("/VisitDetailAction.sr")) {
			action = new VisitDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/VisitModifyAction.sr")) {
			action = new VisitModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/VisitDelete.sr")) {
			action = new VisitDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/VisitAddAction.sr")) {
			System.out.println("test1");
			action = new VisitAddAction();
			System.out.println("test3");
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ģ�� ���� �߰�
		// SCMOON Write Visit Source
		else if (command.equals("/FriendVisitListAction.sr")) {
			action = new Friend_Visit_ListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (command.equals("/FriendVisitDetailAction.sr")) {
			action = new Friend_VisitDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/FriendVisitModifyAction.sr")) {
			action = new Friend_VisitModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/FriendVisitDelete.sr")) {
			action = new Friend_VisitDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/FriendVisitAddAction.sr")) {
			System.out.println("test1");
			action = new Friend_VisitAddAction();
			System.out.println("test3");
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MyPageView.sr")){
			action = new MyPageViewAction();// �������� ���� ��ĳ����
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/SearchResult.sr")) {
			action = new SearchAction();// �������� ���� ��ĳ����
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/SearchResult.sr")) {
			action = new SearchAction();// �������� ���� ��ĳ����
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/FriendView.sr")) {
			action = new FriendViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/InfoModify.sr")) {
			action = new InfoModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/InfoModifyView.sr")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./U&I/Page/UserInfoModifyForm.jsp");
		}
		else if (command.equals("/LeaveMember.sr")) {
			action = new LeaveMemberAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/Fri_MyPageView.sr")) {
			action = new Fri_MyPageViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/New_MyPageView.sr")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./U&I/Page/Fri_MyPagePage.jsp");
		}
		else if (command.equals("/Fri_VisitListAction.sr")) {
			action = new Friend_Visit_ListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		if (forward != null) {
			if (forward.isRedirect()) { // �����̷�Ʈ �˴ϴ�.
				response.sendRedirect(forward.getPath());
			} else {// �������˴ϴ�.
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	// doProcess(request,response)�޼��带 �����Ͽ� ��û�� GET����̵�
	// POST������� ���۵Ǿ� ���� ���� �޼��忡�� ��û�� ó���� �� �ֵ��� �Ͽ����ϴ�.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}
