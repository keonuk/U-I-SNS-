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
	// 데이터 베이스 작업에 필요한 인터페이스들의 레퍼런스 변수를 선언합니다.
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// 생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public VisitDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
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

			// 성공 유무
			if (result != 0) {
				return true;
			}

			// 컨텐츠 널값체크
			if (visit.getV_CONTENT() == null) {
				return false;
			}

		} catch (Exception e) {
			System.out.println("boardInsert() 에러 : " + e);
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

	// 글의 갯수 구하기
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
			
			System.out.println("getListCount의 x의값==>" + x);
		} catch (Exception ex) {
			System.out.println("getListCount() 에러 : " + ex);
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

	// 글 목록 보기
	public List<VisitBean> getBoardList(int page, int limit, String getId) {
		// page : 페이지
		// limit : 페이지 당 목록의 수
		// BOARD_RE_REF desc, BOARD_RESEQ asc에 의해 정렬한 것을 조건절에
		// 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.

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
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호(1
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호(10

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement(board_list_sql);
			// 쿼리문에 적용하는 set
			// where rnum >= ? and rnum <= ?;
			
			pstmt.setString(1, getId);
			pstmt.setString(2, getId);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, endrow);
			rs = pstmt.executeQuery();

			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				VisitBean board = new VisitBean();
				board.setV_NUM(rs.getInt("V_NUM"));
				board.setV_CONTENT(rs.getString("V_CONTENT"));
				board.setV_TDATE(rs.getString("V_TDATE"));
				board.setV_ID(rs.getString("V_ID"));
				System.out.println("getID==>" + rs.getString("v_id"));

				board.setV_PHOTO(rs.getString("V_PHOTO"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
			}
			return list; // 값을 담은 객체를 저장한 리스트를 호출한 곳으로 가져갑니다.
		} catch (Exception e) {
			System.out.println("getBoardList() 에러 : " + e);
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
	
	//친구 글의 갯수 구하기
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
			
			System.out.println("dao==>fri_getListCount의 x의값==>" + x);
		} catch (Exception ex) {
			System.out.println("getListCount() 에러 : " + ex);
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

	//친구 글 목록 보기
	public List<VisitBean> getBoardList(int page, int limit, String getId,String session_id) {
		// page : 페이지
		// limit : 페이지 당 목록의 수
		// BOARD_RE_REF desc, BOARD_RESEQ asc에 의해 정렬한 것을 조건절에
		// 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.

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
		// 한 페이지당 10개씩 목록인 경우 1페이지, 2페이지
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호(1
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호(10

		try {
			conn = ds.getConnection();
			System.out.println("getConnection");

			pstmt = conn.prepareStatement(board_list_sql);
			// 쿼리문에 적용하는 set
			// where rnum >= ? and rnum <= ?;
			
			pstmt.setString(1, getId);
			pstmt.setString(2, getId);
			pstmt.setString(3, session_id);
			pstmt.setString(4, getId);
			pstmt.setInt(5, startrow);
			pstmt.setInt(6, endrow);
			rs = pstmt.executeQuery();

			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				VisitBean board = new VisitBean();
				board.setV_NUM(rs.getInt("V_NUM"));
				board.setV_CONTENT(rs.getString("V_CONTENT"));
				board.setV_TDATE(rs.getString("V_TDATE"));
				board.setV_ID(rs.getString("V_ID"));
				System.out.println("getID==>" + rs.getString("v_id"));

				board.setV_PHOTO(rs.getString("V_PHOTO"));
				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
			}
			return list; // 값을 담은 객체를 저장한 리스트를 호출한 곳으로 가져갑니다.
		} catch (Exception e) {
			System.out.println("getBoardList() 에러 : " + e);
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
	
	// 최댓값 구하기 (NO)
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
				
				System.out.println("getMaxValueCount의 x의값==>" + x);
			} catch (Exception ex) {
				System.out.println("getListCount() 에러 : " + ex);
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
	
	
	
	
	

	// 작성 날짜 구하기!
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
			System.out.println("getListCount() 에러 : " + ex);
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

	// 상세데이터 가져오기
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
			System.out.println("getDetail 에러 : " + ex);
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
	 * public int boardReply(VisitBeen board){ //board 테이블의 board_num 필드의 최대값을
	 * 구해와서 글을 등록할 때 //글 번호를 순차적으로 지정하기 위함입니다. String
	 * board_max_sql="select max(board_num) from board"; String sql=""; int
	 * num=0;
	 * 
	 * //답변을 할 원문 글 그룹 번호입니다. //답변을 달게 되면 답변 글은 이 번호와 같은 관련글 번호를 갖게 처리되면서 //같은
	 * 그룹에 속하게 됩니다. //글 목록에서 보여줄때 하나의 그룹으로 묶어서 출력됩니다. int
	 * re_ref=board.getBOARD_RE_REF();
	 * 
	 * //답글의 깊이를 의미합니다. 원문에 대한 답글이 출력될 때 한 번 들여쓰기 처리가 //되고 답글에 대한 답글은 들여쓰기가 두 번
	 * 처리되게 합니다. //원문인 경우에는 이 값이 0이고 원문의 답글은 1, 답글의 답글은 2가 됩니다.
	 * 
	 * int re_lev=board.getBOARD_RE_LEV();
	 * 
	 * //같은 관련 글 중에서 해당 글이 출력되는 순서입니다. int re_seq=board.getBOARD_RE_SEQ();
	 * 
	 * try{ conn = ds.getConnection(); pstmt =
	 * conn.prepareStatement(board_max_sql); rs = pstmt.executeQuery();
	 * if(rs.next()) num = rs.getInt(1)+1; else num=1;
	 * 
	 * //BOARD_RE_REF, BOARD_RE_SEQ 값을 확인하여 원문 글에 다른 답글이 있으면 //다른 답글들의
	 * BOARD_RE_SEQ값을 1씩 증가시킵니다. //현재 글을 다른 답글보다 앞에 출력되게 하기 위해서 입니다.
	 * sql="update board " +"set BOARD_RE_SEQ=BOARD_RE_SEQ+1 "
	 * +"where BOARD_RE_REF = ? " +"and BOARD_RE_SEQ > ?";
	 * 
	 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1, re_ref);
	 * pstmt.setInt(2, re_seq); pstmt.executeQuery();
	 * 
	 * //등록할 답변 글의 BOARD_RE_LEV, BOARD_RE_SEQ값을 원문 글보다 1씩 증가 re_seq = re_seq +
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
	 * "");//답변에는 파일을 업로드하지 않습니다. pstmt.setInt(7, re_ref); pstmt.setInt(8,
	 * re_lev); pstmt.setInt(9, re_seq); pstmt.setInt(10, 0);
	 * //BOARD_READCOUNT(조회수)는 0 pstmt.executeUpdate(); return num;//글번호
	 * 
	 * }catch(SQLException ex){ System.out.println("boardReply() 에러 : " + ex);
	 * 
	 * }finally{ if(rs!=null)try{rs.close();}catch(SQLException ex){} if(pstmt
	 * !=null)try{pstmt.close();}catch(SQLException ex){} if(conn!=null)
	 * try{conn.close();}catch(SQLException ex){} } return 0;
	 * 
	 * }//boardReply()메서드 end
	 */ // 글 수정
	public boolean visitModify(VisitBean visitdata, String id) throws Exception {
		String sql = "update SEMI_VISITOR " + "set V_CONTENT=? " + "where V_NUM=? and V_ID = ? ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, visitdata.getV_CONTENT());
			pstmt.setInt(2, visitdata.getV_NUM());
			pstmt.setString(3, id);
			pstmt.executeUpdate();

			// 컨텐츠 널값체크
			if (visitdata.getV_CONTENT() == null) {
				return false;
			}
			return true;

		} catch (Exception ex) {
			System.out.println("visitModify() 에러: " + ex);
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
	}// boardModify()메서드 end

	// 글 삭제
	public boolean visitDelete(int num, String id) {
		String visit_delete_sql = "delete from SEMI_VISITOR where V_num=? and V_ID = ? ";
		int result = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(visit_delete_sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, id);

			// 쿼리 실행 후 삭제된 로우(레코드)갯수가 반환됩니다.
			result = pstmt.executeUpdate();
			// 삭제가 안된 경우에는 false를 반환합니다.
			if (result == 0)
				return false;

			return true;
		} catch (Exception ex) {
			System.out.println("boardDelete() 에러: " + ex);
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
	}// boardDelete()메서드 end

	/*
	 * //글쓴이인지 확인 - 아이디로 확인합니다. public boolean isBoardWriter(int num, String
	 * v_id){ String board_sql="select * from SEMI_VISITOR where V_NUM=?"; try{
	 * conn = ds.getConnection(); pstmt=conn.prepareStatement(board_sql);
	 * pstmt.setInt(1, num); rs=pstmt.executeQuery(); rs.next();
	 * 
	 * if(v_id.equals(rs.getString("V_ID"))){ return true; } }catch(SQLException
	 * ex){ System.out.println("isBoardWriter() 에러 : "+ex); }finally{
	 * if(rs!=null) try{rs.close();}catch(SQLException ex){} if(pstmt!=null)
	 * try{pstmt.close();} catch(SQLException ex){} if(conn!=null)
	 * try{conn.close();}catch(SQLException ex){} } return false;
	 * }//isBoardWriter end
	 * 
	 */
	// 성별 가져오기
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
			System.out.println("getListCount() 에러 : " + ex);
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