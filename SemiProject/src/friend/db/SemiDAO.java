package friend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class SemiDAO {
	// ������ ���̽� �۾��� �ʿ��� �������̽����� ���۷��� ������ �����մϴ�.
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	// �����ڿ��� JNDI ���ҽ��� �����Ͽ� Connection ��ü�� ���ɴϴ�.
	public SemiDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}

	// ȸ�� ���� insert �޼ҵ�
	public int insert(FriendBean friendbean) {
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("INSERT INTO Semi_Member VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setString(1, friendbean.getM_Id());
			pstmt.setString(2, friendbean.getM_pass());
			pstmt.setString(3, friendbean.getM_name());
			pstmt.setString(4, friendbean.getM_birth());
			pstmt.setString(5, friendbean.getM_gender());
			pstmt.setString(6, friendbean.getM_email());
			pstmt.setString(7, friendbean.getM_phoneNum());
			
			// ȸ�� ���Խ� ���� ÷�θ� ���� �ʾ��� ��, ���� �̸� profile.png�� ����
			if (friendbean.getM_PhotoName() == null) {
				String Pname = "profile.png";
				System.out.println("DAO���� ���� ÷�� ������ ��" + Pname);
				pstmt.setString(8, Pname);
				// ÷�θ� ���� �� ÷���� ���� �̸� ����.
			} else {
				System.out.println("DAO���� ���� ÷�� ���� ��" + friendbean.getM_PhotoName());
				pstmt.setString(8, friendbean.getM_PhotoName());
			}

			// result : ���� ���� ����(�ο� ����)�ϸ� 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			result = -1;
			System.out.println("ȸ�� ��� �����Դϴ�.");
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return result;
	}

	// �α��� �˻�
	public int isId(String id, String pass) {
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("select m_Id, m_pass from semi_member where m_Id = ?");
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			// rs ������ next()�޼ҵ� ���� - id�� ��� ã��
			if (rs.next()) {
				// �˻��� id�� row���� �� ��° �÷��� ��(��й�ȣ)��
				// �Է¹��� pass���� ������ ��
				if (rs.getString(2).equals(pass)) {
					result = 1; // ���̵�� ��й�ȣ ��ġ�ϴ°��
				} else {
					result = 0; // ��й�ȣ ��ġ���� �ʴ°��
				}
			} else {
				result = -1; // ���̵� �������� �ʽ��ϴ�.
			}
			// primary key �������� �������� ��� �߻��ϴ� ����
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			result = -1;
			System.out.println("���� ���̵� �����Դϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return result;
	}

	// ģ�� ����� ������ DAO���� ���� �� ���� ����� List ��ü�� �����մϴ�.
	public List<FriendBean> getMember(String search, String id) {
		FriendBean friend = null;
		List<FriendBean> list = new ArrayList<FriendBean>();
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			friend = new FriendBean();
			// ����� ���̵�� �̸����� �˻��ؼ� ��� �����ִ� ������
			String sql = "select m_photoName, m_Id, m_name " + "from ( select * from semi_member where m_id != ?) "
					+ "where( m_Id = ? or m_name = ? ) ";
			// pstmt ��ü�� ������ ���
			pstmt = con.prepareStatement(sql);
			// ? ���� �˻��� �̸� �����մϴ�.
			pstmt.setString(1, id);
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			// ������ ������ ��� rs��ü�� ����ϴ�.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// friendBean ��ü friend�� ��� �÷��� �� ���
				friend = new FriendBean();
				friend.setM_PhotoName(rs.getString("m_photoName"));
				friend.setM_Id(rs.getString("m_Id"));
				friend.setM_name(rs.getString("m_name"));
				// ����Ʈ�� �����ϱ�
				list.add(friend);
			}
		} catch (Exception ex) {
			System.out.println("getMember() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		// list ��ȯ
		return list;
	}

	// ģ�� ��� ��������
	public List<FriendBean> getFriend(String id) {
		FriendBean friend = null;
		List<FriendBean> list = new ArrayList<FriendBean>();
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			// ����� ���̵�� �˻��ؼ� ��� �����ִ� ������
			// ������������
			/*
			 * String sql = "select m_PhotoName, m_Id, m_name " +
			 * "from semi_member " + "where my_id = ?";
			 */

			String sql = "select m_photoname, m_Id, m_name " + "from (select fr_id from semi_friend_list "
					+ "where semi_friend_list.my_id = ? ) a, semi_member " + "where a.fr_id = semi_member.m_Id";
			// pstmt ��ü�� ������ ���
			pstmt = con.prepareStatement(sql);
			// ? ���� �˻��� �̸� �����մϴ�.
			pstmt.setString(1, id);
			System.out.println("getFriend()���� ���Ǿ��̵� : " + id);
			// ������ ������ ��� rs��ü�� ����ϴ�.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// friendBean ��ü friend�� ��� �÷��� �� ���
				friend = new FriendBean();
				friend.setM_PhotoName(rs.getString("m_photoName"));
				friend.setM_Id(rs.getString("m_Id"));
				friend.setM_name(rs.getString("m_name"));
				// ����Ʈ�� �����ϱ�
				list.add(friend);
			}
		} catch (Exception ex) {
			System.out.println("getFriend() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		// list ��ȯ
		return list;
	}

	// ������ ���� ȭ�鿡 ��Ÿ�������� �޼ҵ�
	public String loadPhoto(String id) {
		String Pname = null;
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("select m_photoName from semi_member where m_Id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Pname = rs.getString("m_photoName");
			}
			System.out.println("���� �̸��� : " + Pname);
		} catch (Exception ex) {
			System.out.println("getFriend() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return Pname;
	}

	// ����� �̸� ���ǿ� �����ϱ� ���� �޼ҵ�
	public String sessionName(String id) {
		String sessionName = null;
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("select m_name from semi_member where m_Id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sessionName = rs.getString(1);
			}
			System.out.println("�α��� ����� ���� ���̵� : " + sessionName);
		} catch (Exception ex) {
			System.out.println("sessionName() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return sessionName;
	}

	// ģ�� ��� ���̺� �߰��ϴ� �޼ҵ�
	/*
	 * public int addFriend(String my_id, String searched_id, String fr_name,
	 * String fr_pname) { // �ڱ��ڽ��� �߰��� �� ���� �ϱ����� ���Ǿ��̵𰪰� �˻� ����� ���̵��� ������ ��
	 * FriendBean friend = null; List<FriendBean> list = new
	 * ArrayList<FriendBean>(); try { con = ds.getConnection();
	 * System.out.println("���� ����!"); // �̹� �ȷο��� ģ���� �ȷο� ��ư�� ������ ��� ģ�� ��� ���̺�
	 * ������ ���� �ʰ� �ؾ��� pstmt =
	 * con.prepareStatement("select fr_id from semi_friend_list where my_id = ?"
	 * ); pstmt.setString(1, my_id); rs = pstmt.executeQuery();
	 * 
	 * while (rs.next()) { // friendBean ��ü friend�� ��� �÷��� �� ��� friend = new
	 * FriendBean(); friend.setFr_id(rs.getString("fr_id")); // ����Ʈ�� �����ϱ�
	 * list.add(friend); } System.out.println("while�� ��"); if
	 * (list.get(0).equals(null)) { // ���� ���� �˻����� ��� 0return ==> �ȷο� �Ұ��� if
	 * (my_id.equals(searched_id)) { return 0; } else { pstmt =
	 * con.prepareStatement("insert into SEMI_friend_list values (?, ?, ?, ?)");
	 * pstmt.setString(1, my_id); pstmt.setString(2, searched_id);
	 * pstmt.setString(3, fr_name); pstmt.setString(4, fr_pname);
	 * 
	 * // result : ���� ���� ����(�ο� ����)�ϸ� 1, else -1 result = pstmt.executeUpdate();
	 * } } else { return 2; } } catch (Exception ex) { result = -1;
	 * System.out.println("addFriend() ����:" + ex); } finally { if (rs != null)
	 * try { rs.close(); } catch (SQLException ex) { } if (pstmt != null) try {
	 * pstmt.close(); } catch (SQLException ex) { } if (con != null) try {
	 * con.close(); } catch (SQLException ex) { } } return result; }
	 */
	public int addFriend(String my_id, String searched_id) {
		// �ڱ��ڽ��� �߰��� �� ���� �ϱ����� ���Ǿ��̵𰪰� �˻� ����� ���̵��� ������ ��
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement(
					"select my_id, fr_id " 
					+ "from semi_friend_list " 
					+ "where my_id = ? and fr_id = ?");

			pstmt.setString(1, my_id);
			pstmt.setString(2, searched_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 2;
				return result;
			}

			pstmt = con.prepareStatement("insert into SEMI_friend_list values (?, ?)");
			pstmt.setString(1, my_id);
			pstmt.setString(2, searched_id);

			// result : ���� ���� ����(�ο� ����)�ϸ� 1 , else 0
			result = pstmt.executeUpdate();

		} catch (Exception ex) {
			result = -1;
			System.out.println("addFriend() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	// ģ�� ���� �޼ҵ�
	public int deleteFriend(String sessionId, String fr_id) {
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("delete semi_friend_list where my_id = ? and fr_id = ?");
			System.out.println("DAO���� �α��� ���Ǿ��̵� : " + sessionId);
			System.out.println("DAO���� ���ȷο� �� ģ�� ���̵� : " + fr_id);
			pstmt.setString(1, sessionId);
			pstmt.setString(2, fr_id);

			// result : ���� ���� ����(�ο� ����)�ϸ� 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception ex) {
			result = -1;
			System.out.println("deleteFriend() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	// MyPage : ���� �����ִ� ����
	public FriendBean getUserInfo(String id) {
		FriendBean member = null;

		try {
			// ����
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("SELECT * FROM semi_member WHERE m_Id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) // ȸ�������� DTO�� ��´�.
			{
				// �ڹٺ� ������ ��´�.
				member = new FriendBean();
				member.setM_Id(rs.getString("m_Id"));
				member.setM_pass(rs.getString("m_pass"));
				member.setM_name(rs.getString("m_name"));
				member.setM_gender(rs.getString("m_gender"));
				member.setM_email(rs.getString("m_email"));
				member.setM_phoneNum(rs.getString("m_phoneNum"));
				member.setM_PhotoName(rs.getString("m_photoName"));
			}
			return member;

		} catch (Exception ex) {
			System.out.println("getUserInfo() ���� : " + ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return null;
	} // end getUserInfo

	// ȸ�� ���� update�޼ҵ�
	public boolean updateMember(FriendBean member, String photo) throws SQLException {

		String sql = "update semi_member set m_pass=?, m_email=?, " + "m_phoneNum=?, m_photoName=? where m_Id=?";

		int result = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getM_pass());
			pstmt.setString(2, member.getM_email());
			pstmt.setString(3, member.getM_phoneNum());
			
			// �������� �� ������ �ٲ��� �ʾ�����
			if (member.getM_PhotoName() == null) {
				pstmt.setString(4, photo);
			// ������ ������ ���� ÷������ ��
			} else {
				pstmt.setString(4, member.getM_PhotoName());
			}
			pstmt.setString(5, member.getM_Id());

			result = pstmt.executeUpdate();
			

			if (result == 0)
				return false;

			return true;

		} catch (Exception ex) {
			System.out.println("updateMember() ���� : " + ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return false;
	} // end updateMember

	// ���� DAO
	// Diary �� ������ �ѷ��ִ� �޼���
	public List<DiaryBean> getDiary(String id) {
		DiaryBean diary = null;
		List<DiaryBean> list = new ArrayList<DiaryBean>();

		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			// ����� ���̵�� �̸����� �˻��ؼ� ��� �����ִ� ������
			String sql = "select start_date, end_date, title from semi_diary where Id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// friendBean ��ü friend�� ��� �÷��� �� ���
				diary = new DiaryBean();
				diary.setStart_date(rs.getString("start_date"));
				diary.setEnd_date(rs.getString("end_date"));
				diary.setTitle(rs.getString("title"));
				// ����Ʈ�� �����ϱ�
				list.add(diary);
			}
		} catch (Exception ex) {
			System.out.println("getMember() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		// list ��ȯ
		return list;
	}

	// ���̾ ��� ���̺� �߰��ϴ� �޼ҵ�
	public int insertDiary(String id, String title, String start_date, String end_date) {
		// TODO Auto-generated method stub
		/* DiaryBean d = null; */
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("INSERT INTO semi_diary VALUES(?, ?, ?, ?)");

			pstmt.setString(1, id);
			pstmt.setString(2, start_date);
			pstmt.setString(3, end_date);
			pstmt.setString(4, title);

			// result : ���� ���� ����(�ο� ����)�ϸ� 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			result = -1;
			System.out.println("�����Դϴ�.");
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return result;
	}

	// ȸ�� Ż�� �޼ҵ�
	public int leave(String leaveId) {
		try {
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("delete semi_member where m_id = ?");
			pstmt.setString(1, leaveId);
			

			// result : ���� ���� ����(�ο� ����)�ϸ� 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			result = -1;
			System.out.println("�����Դϴ�.");
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return result;
	}

	// ģ����Ͽ��� ģ�� ���� ������ �̵��ϴ� ģ�� ������
	public FriendBean getFriendInfo(String fr_id) {
		FriendBean member = null;

		try {
			// ����
			con = ds.getConnection();
			System.out.println("���� ����!");

			pstmt = con.prepareStatement("SELECT * FROM semi_member WHERE m_Id=?");
			pstmt.setString(1, fr_id);
			rs = pstmt.executeQuery();

			if (rs.next()) // ȸ�������� DTO�� ��´�.
			{
				// �ڹٺ� ������ ��´�.
				member = new FriendBean();
				member.setFr_id(rs.getString("m_Id"));
				member.setFr_name(rs.getString("m_name"));
				member.setFr_gender(rs.getString("m_gender"));
				member.setFr_email(rs.getString("m_email"));
				member.setFr_phoneNum(rs.getString("m_phoneNum"));
				member.setFr_PhotoName(rs.getString("m_photoName"));
			}
			return member;

		} catch (Exception ex) {
			System.out.println("getFriendInfo() ���� : " + ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		return null;
	}
	
	public boolean boardInsert(BoardBean board) {
		String sql = "";
		int result = 0;

		try {
			con = ds.getConnection();
			sql = "insert into board " + "values (?, ?, ?, sysdate, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, board.getId());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getImage());
			pstmt.setString(4, board.getContent());

			result = pstmt.executeUpdate();
			if (result == 0)
				return false;
			return true;
		} catch (Exception ex) {
			System.out.println("boardInsert() ����:" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return false;
	}

	// ���� ģ������ �Խù��� �ҷ����� �޼ҵ�
	public List<BoardBean> getBoardSelect(String id) {
		List<BoardBean> list = new ArrayList<BoardBean>();
		try {
			con = ds.getConnection();
			System.out.println("getBoardSelect() ���� : getConnection");

			String sql = "select id, subject, boarddate, image, content" + " from ("
					+ " (select id, a.fr_id, subject, boarddate, image, content"
					+ " from (select fr_id from semi_friend_list where semi_friend_list.my_id= ? ) a, board"
					+ " where a.fr_id = board.id" + ")" + " union"
					+ " ( select id, '' as fr_id, subject, boarddate, image, content" + " from board" + " where id = ?"
					+ ")" + " order by boarddate desc" + ")";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			// DB���� ������ �����͸� VO��ü�� ����ϴ�.
			while (rs.next()) {
				BoardBean board = new BoardBean();
				board.setId(rs.getString("id"));
				board.setSubject(rs.getString("subject"));
				board.setDate(rs.getString("boarddate"));
				board.setImage(rs.getString("image"));
				board.setContent(rs.getString("content"));
				// System.out.println("getID==>" + rs.getString("id"));
				System.out.println("�Խ��Ǹ���Ʈ �޼ҵ� : " + board.getSubject());

				list.add(board); // ���� ���� ��ü�� ����Ʈ�� �����մϴ�.
			}
			return list; // ���� ���� ��ü�� ������ ����Ʈ�� ȣ���� ������ �������ϴ�.
		} catch (Exception e) {
			System.out.println("getBoardSelect() ���� : " + e);
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return null;
	}
}