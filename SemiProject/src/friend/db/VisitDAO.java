package friend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class VisitDAO {
	// ������ ���̽� �۾��� �ʿ��� �������̽����� ���۷��� ������ �����մϴ�.
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// �����ڿ��� JNDI ���ҽ��� �����Ͽ� Connection ��ü�� ���ɴϴ�.
	public VisitDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}

	public boolean visitInsert(VisitBean visit) {

		int result = 0;
		String sql = "";

		try {
			conn = ds.getConnection();

			sql = "insert into SEMI_VISITOR " + "(v_num, v_content, v_tdate, v_id , v_photo) ";
			sql += "values(?, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, visit.getV_NUM());
			pstmt.setString(2, visit.getV_CONTENT());
			pstmt.setString(3, visit.getV_TDATE());
			pstmt.setString(4, visit.getV_ID());
			pstmt.setString(5, visit.getV_PHOTO());

			result = pstmt.executeUpdate();

			// ���� ����
			if (result != 0) {
				return true;
			}

			// ������ �ΰ�üũ
			if (visit.getV_CONTENT() == null) {
				return false;
			}

		} catch (Exception e) {
			System.out.println("boardInsert() ���� : " + e);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return false;
	}

	// ���� ���� ���ϱ�
	public int getListCount(String getId) {
		int x = 0;

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement("select count(*) from (select rownum rnum, V_NUM, V_ID, V_CONTENT, V_TDATE, V_PHOTO "
					+ "from (select * from "
					+ "(select fr_id from semi_friend_list where semi_friend_list.my_id= ?) a, "
					+ "semi_visitor where a.fr_id = semi_visitor.v_id "
					+ "and semi_visitor.v_id = ? "
					+ "union all "
					+ "select '' fr_id, v_num, v_id, v_content, v_tdate, v_photo from semi_visitor "
					+ "where v_id = ?) " 
					+ "order by V_NUM desc) ");
			
			pstmt.setString(1, getId);
			pstmt.setString(2, getId);
			pstmt.setString(3, getId);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
			
			System.out.println("getListCount�� x�ǰ�==>" + x);
		} catch (Exception ex) {
			System.out.println("getListCount() ���� : " + ex);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;
	}

	// �� ��� ����
	public List<VisitBean> getBoardList(int page, int limit, String getId) {
		// page : ������
		// limit : ������ �� ����� ��
		// BOARD_RE_REF desc, BOARD_RESEQ asc�� ���� ������ ���� ��������
		// �´� rnum�� ���� ��ŭ �������� �������Դϴ�.

		String board_list_sql = "select * from( "
				+ "select rownum rnum, V_NUM, V_ID, V_CONTENT, V_TDATE, V_PHOTO "
				+ "from ( "
				+ "(select fr_id, v_num, v_id, v_content, v_tdate, v_photo "
				+ "from (select fr_id from semi_friend_list where semi_friend_list.my_id= ? ) a, semi_visitor "
				+ "where a.fr_id = semi_visitor.v_id ) "
				+ "union "
				+ "( select '' as fr_id, v_num, v_id, v_content, v_tdate, v_photo "
				+ "from semi_visitor "
				+ "where v_id = ? ) "
				+ "order by V_NUM desc )) "
				+ "where rnum >= ? and rnum <= ? ";

		List<VisitBean> list = new ArrayList<VisitBean>();
		// �� �������� 10���� ����� ��� 1������, 2������
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ(1
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ(10

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement(board_list_sql);
			// �������� �����ϴ� set
			// where rnum >= ? and rnum <= ?;
			
			pstmt.setString(1, getId);
			pstmt.setString(2, getId);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();

			// DB���� ������ �����͸� VO��ü�� ����ϴ�.
			while (rs.next()) {
				VisitBean board = new VisitBean();
				board.setV_NUM(rs.getInt("V_NUM"));
				board.setV_CONTENT(rs.getString("V_CONTENT"));
				board.setV_TDATE(rs.getString("V_TDATE"));
				board.setV_ID(rs.getString("V_ID"));
				System.out.println("getID==>" + rs.getString("v_id"));

				board.setV_PHOTO(rs.getString("V_PHOTO"));
				list.add(board); // ���� ���� ��ü�� ����Ʈ�� �����մϴ�.
			}
			return list; // ���� ���� ��ü�� ������ ����Ʈ�� ȣ���� ������ �������ϴ�.
		} catch (Exception e) {
			System.out.println("getBoardList() ���� : " + e);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return null;
	}
	
	//ģ�� ���� ���� ���ϱ�
	public int getListCount(String getId,String session_id) {
		int x = 0;

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement("select count(*) from ( "
					+ "select rownum rnum, V_NUM, V_ID, V_CONTENT, V_TDATE, V_PHOTO "
					+ "from ( "
					+ "(select fr_id, v_num, v_id, v_content, v_tdate, v_photo "
					+ "from (select fr_id from semi_friend_list where semi_friend_list.my_id= ?) a, "
					+ "semi_visitor where a.fr_id = semi_visitor.v_id "
					+ "and semi_visitor.v_id = ? ) "
					+ "union all "
					+ "(select fr_id, v_num, v_id, v_content, v_tdate, v_photo "
					+ "from (select fr_id from semi_friend_list where semi_friend_list.my_id= ?) a, "
					+ "semi_visitor where a.fr_id = semi_visitor.v_id "
					+ "and semi_visitor.v_id = ? ) "
					+ "order by V_NUM desc )) ");
			
			pstmt.setString(1, getId);
			pstmt.setString(2, getId);
			pstmt.setString(3, session_id);
			pstmt.setString(4, getId);

			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
			
			System.out.println("dao==>fri_getListCount�� x�ǰ�==>" + x);
		} catch (Exception ex) {
			System.out.println("getListCount() ���� : " + ex);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;
	}

	//ģ�� �� ��� ����
	public List<VisitBean> getBoardList(int page, int limit, String getId,String session_id) {
		// page : ������
		// limit : ������ �� ����� ��
		// BOARD_RE_REF desc, BOARD_RESEQ asc�� ���� ������ ���� ��������
		// �´� rnum�� ���� ��ŭ �������� �������Դϴ�.

		String board_list_sql = "select * from( "
				+ "select rownum rnum, V_NUM, V_ID, V_CONTENT, V_TDATE, V_PHOTO "
				+ "from ( "
				+ "(select fr_id, v_num, v_id, v_content, v_tdate, v_photo "
				+ "from (select fr_id from semi_friend_list where semi_friend_list.my_id= ? ) a, semi_visitor "
				+ "where a.fr_id = semi_visitor.v_id "
				+ "and semi_visitor.v_id = ? ) "
				+ "union all "
				+ "( select fr_id, v_num, v_id, v_content, v_tdate, v_photo "
				+ "from (select fr_id from semi_friend_list where semi_friend_list.my_id= ? ) a, semi_visitor "
				+ "where a.fr_id = semi_visitor.v_id "
				+ "and semi_visitor.v_id = ? ) "
				+ "order by V_NUM desc )) "
				+ "where rnum >= ? and rnum <= ? ";

		List<VisitBean> list = new ArrayList<VisitBean>();
		// �� �������� 10���� ����� ��� 1������, 2������
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ(1
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ(10

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement(board_list_sql);
			// �������� �����ϴ� set
			// where rnum >= ? and rnum <= ?;
			
			pstmt.setString(1, getId);
			pstmt.setString(2, getId);
			pstmt.setString(3, session_id);
			pstmt.setString(4, getId);
			pstmt.setInt(5, startrow);
			pstmt.setInt(6, endrow);
			rs = pstmt.executeQuery();

			// DB���� ������ �����͸� VO��ü�� ����ϴ�.
			while (rs.next()) {
				VisitBean board = new VisitBean();
				board.setV_NUM(rs.getInt("V_NUM"));
				board.setV_CONTENT(rs.getString("V_CONTENT"));
				board.setV_TDATE(rs.getString("V_TDATE"));
				board.setV_ID(rs.getString("V_ID"));
				System.out.println("getID==>" + rs.getString("v_id"));

				board.setV_PHOTO(rs.getString("V_PHOTO"));
				list.add(board); // ���� ���� ��ü�� ����Ʈ�� �����մϴ�.
			}
			return list; // ���� ���� ��ü�� ������ ����Ʈ�� ȣ���� ������ �������ϴ�.
		} catch (Exception e) {
			System.out.println("getBoardList() ���� : " + e);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return null;
	}
	
	// �ִ� ���ϱ� (NO)
		public int getMaxValueCount(String getId) {
			int x = 0;

			try {
				conn = ds.getConnection();
				System.out.println("getConnection");

				pstmt = conn.prepareStatement("select max(v_num) from (select rownum rnum, V_NUM, V_ID, V_CONTENT, V_TDATE, V_PHOTO "
						+ "from (select * from "
						+ "(select fr_id from semi_friend_list where semi_friend_list.my_id= ?) a, "
						+ "semi_visitor where a.fr_id = semi_visitor.v_id "
						+ "union all "
						+ "select '' fr_id, v_num, v_id, v_content, v_tdate, v_photo from semi_visitor "
						+ "where v_id = ?) " 
						+ "order by V_NUM desc) ");
				
				pstmt.setString(1, getId);
				pstmt.setString(2, getId);
				
				rs = pstmt.executeQuery();

				if (rs.next()) {
					x = rs.getInt(1);
				}
				
				System.out.println("getMaxValueCount�� x�ǰ�==>" + x);
			} catch (Exception ex) {
				System.out.println("getListCount() ���� : " + ex);
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
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
					}
			}
			return x;
		}
	
	
	
	
	

	// �ۼ� ��¥ ���ϱ�!
	public String getWriteDate() {
		String w_date = "";

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement("SELECT SYSDATE w_date FROM DUAL");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				w_date = rs.getString("w_date");
			}
		} catch (Exception ex) {
			System.out.println("getListCount() ���� : " + ex);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return w_date;
	}

	// �󼼵����� ��������
	public VisitBean getDetail(int num, String id) throws Exception {
		VisitBean visit = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select * from SEMI_VISITOR where V_NUM = ? and  V_ID = ? ");
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				visit = new VisitBean();
				visit.setV_NUM(rs.getInt("V_NUM"));
				visit.setV_ID(rs.getString("V_ID"));
				visit.setV_CONTENT(rs.getString("V_CONTENT"));
				visit.setV_TDATE(rs.getString("V_TDATE"));
			}
			return visit;
		} catch (Exception ex) {
			System.out.println("getDetail ���� : " + ex);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return null;
	}

	/*
	 * public int boardReply(VisitBeen board){ //board ���̺��� board_num �ʵ��� �ִ밪��
	 * ���ؿͼ� ���� ����� �� //�� ��ȣ�� ���������� �����ϱ� �����Դϴ�. String
	 * board_max_sql="select max(board_num) from board"; String sql=""; int
	 * num=0;
	 * 
	 * //�亯�� �� ���� �� �׷� ��ȣ�Դϴ�. //�亯�� �ް� �Ǹ� �亯 ���� �� ��ȣ�� ���� ���ñ� ��ȣ�� ���� ó���Ǹ鼭 //����
	 * �׷쿡 ���ϰ� �˴ϴ�. //�� ��Ͽ��� �����ٶ� �ϳ��� �׷����� ��� ��µ˴ϴ�. int
	 * re_ref=board.getBOARD_RE_REF();
	 * 
	 * //����� ���̸� �ǹ��մϴ�. ������ ���� ����� ��µ� �� �� �� �鿩���� ó���� //�ǰ� ��ۿ� ���� ����� �鿩���Ⱑ �� ��
	 * ó���ǰ� �մϴ�. //������ ��쿡�� �� ���� 0�̰� ������ ����� 1, ����� ����� 2�� �˴ϴ�.
	 * 
	 * int re_lev=board.getBOARD_RE_LEV();
	 * 
	 * //���� ���� �� �߿��� �ش� ���� ��µǴ� �����Դϴ�. int re_seq=board.getBOARD_RE_SEQ();
	 * 
	 * try{ conn = ds.getConnection(); pstmt =
	 * conn.prepareStatement(board_max_sql); rs = pstmt.executeQuery();
	 * if(rs.next()) num = rs.getInt(1)+1; else num=1;
	 * 
	 * //BOARD_RE_REF, BOARD_RE_SEQ ���� Ȯ���Ͽ� ���� �ۿ� �ٸ� ����� ������ //�ٸ� ��۵���
	 * BOARD_RE_SEQ���� 1�� ������ŵ�ϴ�. //���� ���� �ٸ� ��ۺ��� �տ� ��µǰ� �ϱ� ���ؼ� �Դϴ�.
	 * sql="update board " +"set BOARD_RE_SEQ=BOARD_RE_SEQ+1 "
	 * +"where BOARD_RE_REF = ? " +"and BOARD_RE_SEQ > ?";
	 * 
	 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1, re_ref);
	 * pstmt.setInt(2, re_seq); pstmt.executeQuery();
	 * 
	 * //����� �亯 ���� BOARD_RE_LEV, BOARD_RE_SEQ���� ���� �ۺ��� 1�� ���� re_seq = re_seq +
	 * 1; re_lev = re_lev + 1;
	 * 
	 * sql="insert into board " +
	 * "(BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT," +
	 * "BOARD_CONTENT,BOARD_FILE,BOARD_RE_REF," + "BOARD_RE_LEV,BOARD_RE_SEQ," +
	 * "BOARD_READCOUNT,BOARD_DATE) " + "values(?,?,?,?,?,?,?,?,?,?,sysdate)";
	 * 
	 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1, num);
	 * pstmt.setString(2, board.getBOARD_NAME()); pstmt.setString(3,
	 * board.getBOARD_PASS()); pstmt.setString(4, board.getBOARD_SUBJECT());
	 * pstmt.setString(5, board.getBOARD_CONTENT()); pstmt.setString(6,
	 * "");//�亯���� ������ ���ε����� �ʽ��ϴ�. pstmt.setInt(7, re_ref); pstmt.setInt(8,
	 * re_lev); pstmt.setInt(9, re_seq); pstmt.setInt(10, 0);
	 * //BOARD_READCOUNT(��ȸ��)�� 0 pstmt.executeUpdate(); return num;//�۹�ȣ
	 * 
	 * }catch(SQLException ex){ System.out.println("boardReply() ���� : " + ex);
	 * 
	 * }finally{ if(rs!=null)try{rs.close();}catch(SQLException ex){} if(pstmt
	 * !=null)try{pstmt.close();}catch(SQLException ex){} if(conn!=null)
	 * try{conn.close();}catch(SQLException ex){} } return 0;
	 * 
	 * }//boardReply()�޼��� end
	 */ // �� ����
	public boolean visitModify(VisitBean visitdata, String id) throws Exception {
		String sql = "update SEMI_VISITOR " + "set V_CONTENT=? " + "where V_NUM=? and V_ID = ? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, visitdata.getV_CONTENT());
			pstmt.setInt(2, visitdata.getV_NUM());
			pstmt.setString(3, id);
			pstmt.executeUpdate();

			// ������ �ΰ�üũ
			if (visitdata.getV_CONTENT() == null) {
				return false;
			}
			return true;

		} catch (Exception ex) {
			System.out.println("visitModify() ����: " + ex);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return false;
	}// boardModify()�޼��� end

	// �� ����
	public boolean visitDelete(int num, String id) {
		String visit_delete_sql = "delete from SEMI_VISITOR where V_num=? and V_ID = ? ";
		int result = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(visit_delete_sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, id);

			// ���� ���� �� ������ �ο�(���ڵ�)������ ��ȯ�˴ϴ�.
			result = pstmt.executeUpdate();
			// ������ �ȵ� ��쿡�� false�� ��ȯ�մϴ�.
			if (result == 0)
				return false;

			return true;
		} catch (Exception ex) {
			System.out.println("boardDelete() ����: " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}

		return false;
	}// boardDelete()�޼��� end

	/*
	 * //�۾������� Ȯ�� - ���̵�� Ȯ���մϴ�. public boolean isBoardWriter(int num, String
	 * v_id){ String board_sql="select * from SEMI_VISITOR where V_NUM=?"; try{
	 * conn = ds.getConnection(); pstmt=conn.prepareStatement(board_sql);
	 * pstmt.setInt(1, num); rs=pstmt.executeQuery(); rs.next();
	 * 
	 * if(v_id.equals(rs.getString("V_ID"))){ return true; } }catch(SQLException
	 * ex){ System.out.println("isBoardWriter() ���� : "+ex); }finally{
	 * if(rs!=null) try{rs.close();}catch(SQLException ex){} if(pstmt!=null)
	 * try{pstmt.close();} catch(SQLException ex){} if(conn!=null)
	 * try{conn.close();}catch(SQLException ex){} } return false;
	 * }//isBoardWriter end
	 * 
	 */
	// ���� ��������
	public String getsex(String getId) {
		String sex = "";
		String visit_sql = "select M_GENDER " + "from SEMI_MEMBER " + "where M_ID = ? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(visit_sql);
			pstmt.setString(1, getId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sex = rs.getString(1);
			}

		} catch (Exception ex) {
			System.out.println("getListCount() ���� : " + ex);
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return sex;
	}

}