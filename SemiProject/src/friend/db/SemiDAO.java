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
	// 데이터 베이스 작업에 필요한 인터페이스들의 레퍼런스 변수를 선언합니다.
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	// 생성자에서 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public SemiDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}

	// 회원 가입 insert 메소드
	public int insert(FriendBean friendbean) {
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("INSERT INTO Semi_Member VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setString(1, friendbean.getM_Id());
			pstmt.setString(2, friendbean.getM_pass());
			pstmt.setString(3, friendbean.getM_name());
			pstmt.setString(4, friendbean.getM_birth());
			pstmt.setString(5, friendbean.getM_gender());
			pstmt.setString(6, friendbean.getM_email());
			pstmt.setString(7, friendbean.getM_phoneNum());
			
			// 회원 가입시 사진 첨부를 하지 않았을 때, 사진 이름 profile.png로 저장
			if (friendbean.getM_PhotoName() == null) {
				String Pname = "profile.png";
				System.out.println("DAO에서 사진 첨부 안했을 때" + Pname);
				pstmt.setString(8, Pname);
				// 첨부를 했을 때 첨부한 사진 이름 저장.
			} else {
				System.out.println("DAO에서 사진 첨부 했을 때" + friendbean.getM_PhotoName());
				pstmt.setString(8, friendbean.getM_PhotoName());
			}

			// result : 쿼리 실행 성공(로우 생성)하면 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			result = -1;
			System.out.println("회원 등록 에러입니다.");
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

	// 로그인 검사
	public int isId(String id, String pass) {
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("select m_Id, m_pass from semi_member where m_Id = ?");
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			// rs 쿼리문 next()메소드 실행 - id값 계속 찾기
			if (rs.next()) {
				// 검색한 id의 row에서 두 번째 컬럼의 값(비밀번호)과
				// 입력받은 pass값과 같은지 비교
				if (rs.getString(2).equals(pass)) {
					result = 1; // 아이디와 비밀번호 일치하는경우
				} else {
					result = 0; // 비밀번호 일치하지 않는경우
				}
			} else {
				result = -1; // 아이디가 존재하지 않습니다.
			}
			// primary key 제약조건 위반했을 경우 발생하는 에러
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			result = -1;
			System.out.println("없는 아이디 에러입니다.");
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

	// 친구 목록의 내용을 DAO에서 읽은 후 얻은 결과를 List 객체에 저장합니다.
	public List<FriendBean> getMember(String search, String id) {
		FriendBean friend = null;
		List<FriendBean> list = new ArrayList<FriendBean>();
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			friend = new FriendBean();
			// 사용자 아이디와 이름으로 검색해서 결과 보여주는 쿼리문
			String sql = "select m_photoName, m_Id, m_name " + "from ( select * from semi_member where m_id != ?) "
					+ "where( m_Id = ? or m_name = ? ) ";
			// pstmt 객체에 쿼리문 담기
			pstmt = con.prepareStatement(sql);
			// ? 값에 검색할 이름 세팅합니다.
			pstmt.setString(1, id);
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			// 실행한 쿼리문 결과 rs객체에 담습니다.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// friendBean 객체 friend에 모든 컬럼의 값 담기
				friend = new FriendBean();
				friend.setM_PhotoName(rs.getString("m_photoName"));
				friend.setM_Id(rs.getString("m_Id"));
				friend.setM_name(rs.getString("m_name"));
				// 리스트에 저장하기
				list.add(friend);
			}
		} catch (Exception ex) {
			System.out.println("getMember() 에러:" + ex);
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
		// list 반환
		return list;
	}

	// 친구 목록 가져오기
	public List<FriendBean> getFriend(String id) {
		FriendBean friend = null;
		List<FriendBean> list = new ArrayList<FriendBean>();
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			// 사용자 아이디로 검색해서 결과 보여주는 쿼리문
			// 조인쿼리쓰기
			/*
			 * String sql = "select m_PhotoName, m_Id, m_name " +
			 * "from semi_member " + "where my_id = ?";
			 */

			String sql = "select m_photoname, m_Id, m_name " + "from (select fr_id from semi_friend_list "
					+ "where semi_friend_list.my_id = ? ) a, semi_member " + "where a.fr_id = semi_member.m_Id";
			// pstmt 객체에 쿼리문 담기
			pstmt = con.prepareStatement(sql);
			// ? 값에 검색할 이름 세팅합니다.
			pstmt.setString(1, id);
			System.out.println("getFriend()에서 세션아이디 : " + id);
			// 실행한 쿼리문 결과 rs객체에 담습니다.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// friendBean 객체 friend에 모든 컬럼의 값 담기
				friend = new FriendBean();
				friend.setM_PhotoName(rs.getString("m_photoName"));
				friend.setM_Id(rs.getString("m_Id"));
				friend.setM_name(rs.getString("m_name"));
				// 리스트에 저장하기
				list.add(friend);
			}
		} catch (Exception ex) {
			System.out.println("getFriend() 에러:" + ex);
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
		// list 반환
		return list;
	}

	// 프로필 사진 화면에 나타내기위한 메소드
	public String loadPhoto(String id) {
		String Pname = null;
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("select m_photoName from semi_member where m_Id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Pname = rs.getString("m_photoName");
			}
			System.out.println("사진 이름은 : " + Pname);
		} catch (Exception ex) {
			System.out.println("getFriend() 에러:" + ex);
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

	// 사용자 이름 세션에 저장하기 위한 메소드
	public String sessionName(String id) {
		String sessionName = null;
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("select m_name from semi_member where m_Id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sessionName = rs.getString(1);
			}
			System.out.println("로그인 사용자 세션 아이디 : " + sessionName);
		} catch (Exception ex) {
			System.out.println("sessionName() 에러:" + ex);
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

	// 친구 목록 테이블에 추가하는 메소드
	/*
	 * public int addFriend(String my_id, String searched_id, String fr_name,
	 * String fr_pname) { // 자기자신은 추가할 수 없게 하기위해 세션아이디값과 검색 결과의 아이디값이 같은지 비교
	 * FriendBean friend = null; List<FriendBean> list = new
	 * ArrayList<FriendBean>(); try { con = ds.getConnection();
	 * System.out.println("연결 성공!"); // 이미 팔로우한 친구의 팔로우 버튼을 누르는 경우 친구 목록 테이블에
	 * 삽입이 되지 않게 해야함 pstmt =
	 * con.prepareStatement("select fr_id from semi_friend_list where my_id = ?"
	 * ); pstmt.setString(1, my_id); rs = pstmt.executeQuery();
	 * 
	 * while (rs.next()) { // friendBean 객체 friend에 모든 컬럼의 값 담기 friend = new
	 * FriendBean(); friend.setFr_id(rs.getString("fr_id")); // 리스트에 저장하기
	 * list.add(friend); } System.out.println("while문 끝"); if
	 * (list.get(0).equals(null)) { // 내가 나를 검색했을 경우 0return ==> 팔로우 불가능 if
	 * (my_id.equals(searched_id)) { return 0; } else { pstmt =
	 * con.prepareStatement("insert into SEMI_friend_list values (?, ?, ?, ?)");
	 * pstmt.setString(1, my_id); pstmt.setString(2, searched_id);
	 * pstmt.setString(3, fr_name); pstmt.setString(4, fr_pname);
	 * 
	 * // result : 쿼리 실행 성공(로우 생성)하면 1, else -1 result = pstmt.executeUpdate();
	 * } } else { return 2; } } catch (Exception ex) { result = -1;
	 * System.out.println("addFriend() 에러:" + ex); } finally { if (rs != null)
	 * try { rs.close(); } catch (SQLException ex) { } if (pstmt != null) try {
	 * pstmt.close(); } catch (SQLException ex) { } if (con != null) try {
	 * con.close(); } catch (SQLException ex) { } } return result; }
	 */
	public int addFriend(String my_id, String searched_id) {
		// 자기자신은 추가할 수 없게 하기위해 세션아이디값과 검색 결과의 아이디값이 같은지 비교
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

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

			// result : 쿼리 실행 성공(로우 생성)하면 1 , else 0
			result = pstmt.executeUpdate();

		} catch (Exception ex) {
			result = -1;
			System.out.println("addFriend() 에러:" + ex);
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

	// 친구 끊기 메소드
	public int deleteFriend(String sessionId, String fr_id) {
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("delete semi_friend_list where my_id = ? and fr_id = ?");
			System.out.println("DAO에서 로그인 세션아이디 : " + sessionId);
			System.out.println("DAO에서 언팔로우 할 친구 아이디 : " + fr_id);
			pstmt.setString(1, sessionId);
			pstmt.setString(2, fr_id);

			// result : 쿼리 실행 성공(로우 생성)하면 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception ex) {
			result = -1;
			System.out.println("deleteFriend() 에러:" + ex);
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

	// MyPage : 정보 보여주는 쿼리
	public FriendBean getUserInfo(String id) {
		FriendBean member = null;

		try {
			// 쿼리
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("SELECT * FROM semi_member WHERE m_Id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) // 회원정보를 DTO에 담는다.
			{
				// 자바빈에 정보를 담는다.
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
			System.out.println("getUserInfo() 에러 : " + ex);
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

	// 회원 정보 update메소드
	public boolean updateMember(FriendBean member, String photo) throws SQLException {

		String sql = "update semi_member set m_pass=?, m_email=?, " + "m_phoneNum=?, m_photoName=? where m_Id=?";

		int result = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getM_pass());
			pstmt.setString(2, member.getM_email());
			pstmt.setString(3, member.getM_phoneNum());
			
			// 정보수정 시 사진은 바꾸지 않았을때
			if (member.getM_PhotoName() == null) {
				pstmt.setString(4, photo);
			// 프로필 사진을 새로 첨부했을 때
			} else {
				pstmt.setString(4, member.getM_PhotoName());
			}
			pstmt.setString(5, member.getM_Id());

			result = pstmt.executeUpdate();
			

			if (result == 0)
				return false;

			return true;

		} catch (Exception ex) {
			System.out.println("updateMember() 에러 : " + ex);
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

	// 형석 DAO
	// Diary 에 데이터 뿌려주는 메서드
	public List<DiaryBean> getDiary(String id) {
		DiaryBean diary = null;
		List<DiaryBean> list = new ArrayList<DiaryBean>();

		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			// 사용자 아이디와 이름으로 검색해서 결과 보여주는 쿼리문
			String sql = "select start_date, end_date, title from semi_diary where Id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// friendBean 객체 friend에 모든 컬럼의 값 담기
				diary = new DiaryBean();
				diary.setStart_date(rs.getString("start_date"));
				diary.setEnd_date(rs.getString("end_date"));
				diary.setTitle(rs.getString("title"));
				// 리스트에 저장하기
				list.add(diary);
			}
		} catch (Exception ex) {
			System.out.println("getMember() 에러:" + ex);
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
		// list 반환
		return list;
	}

	// 다이어리 목록 테이블에 추가하는 메소드
	public int insertDiary(String id, String title, String start_date, String end_date) {
		// TODO Auto-generated method stub
		/* DiaryBean d = null; */
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("INSERT INTO semi_diary VALUES(?, ?, ?, ?)");

			pstmt.setString(1, id);
			pstmt.setString(2, start_date);
			pstmt.setString(3, end_date);
			pstmt.setString(4, title);

			// result : 쿼리 실행 성공(로우 생성)하면 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			result = -1;
			System.out.println("에러입니다.");
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

	// 회원 탈퇴 메소드
	public int leave(String leaveId) {
		try {
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("delete semi_member where m_id = ?");
			pstmt.setString(1, leaveId);
			

			// result : 쿼리 실행 성공(로우 생성)하면 1, else -1
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			result = -1;
			System.out.println("에러입니다.");
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

	// 친구목록에서 친구 사진 누르면 이동하는 친구 페이지
	public FriendBean getFriendInfo(String fr_id) {
		FriendBean member = null;

		try {
			// 쿼리
			con = ds.getConnection();
			System.out.println("연결 성공!");

			pstmt = con.prepareStatement("SELECT * FROM semi_member WHERE m_Id=?");
			pstmt.setString(1, fr_id);
			rs = pstmt.executeQuery();

			if (rs.next()) // 회원정보를 DTO에 담는다.
			{
				// 자바빈에 정보를 담는다.
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
			System.out.println("getFriendInfo() 에러 : " + ex);
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
			System.out.println("boardInsert() 에러:" + ex);
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

	// 나와 친구들의 게시물을 불러오는 메소드
	public List<BoardBean> getBoardSelect(String id) {
		List<BoardBean> list = new ArrayList<BoardBean>();
		try {
			con = ds.getConnection();
			System.out.println("getBoardSelect() 연결 : getConnection");

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
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				BoardBean board = new BoardBean();
				board.setId(rs.getString("id"));
				board.setSubject(rs.getString("subject"));
				board.setDate(rs.getString("boarddate"));
				board.setImage(rs.getString("image"));
				board.setContent(rs.getString("content"));
				// System.out.println("getID==>" + rs.getString("id"));
				System.out.println("게시판리스트 메소드 : " + board.getSubject());

				list.add(board); // 값을 담은 객체를 리스트에 저장합니다.
			}
			return list; // 값을 담은 객체를 저장한 리스트를 호출한 곳으로 가져갑니다.
		} catch (Exception e) {
			System.out.println("getBoardSelect() 에러 : " + e);
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