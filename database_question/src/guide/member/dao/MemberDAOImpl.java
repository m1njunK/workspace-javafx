package guide.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import guide.member.utils.DBUtil;
import guide.member.vo.BackUpMemberVO;
import guide.member.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {

	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public MemberDAOImpl() {
		conn = DBUtil.getConnection();
		System.out.println(conn);
	}
	
	@Override
	public MemberVO join(MemberVO member) {
		String sql = "INSERT INTO tbl_member(mName,mId,mPw,reg) VALUES(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getmName());
			pstmt.setString(2, member.getmId());
			pstmt.setString(3, member.getmPw());
			pstmt.setLong(4, System.currentTimeMillis());
			
			int result = pstmt.executeUpdate();
			
			if(result == 1) {
				sql = "SELECT mNum, reg FROM tbl_member WHERE mId = '" + member.getmId()+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				if(rs.next()) {
					member.setmNum(rs.getInt(1));
					member.setReg(rs.getLong(2));
					return member;
				}
			}
			
		} catch (SQLException e) {
			
		}finally {
			DBUtil.close(rs,stmt,pstmt);
		}
		return null;
	}

	@Override
	public MemberVO selectMember(String mId, String mPw) {
		MemberVO member = null;
		String sql = "SELECT * FROM tbl_member WHERE mId = ? AND mPw = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mId);
			pstmt.setString(2, mPw);
		
			rs = pstmt.executeQuery();
			if(rs.next()) {
				// int mNum, String mName, String mId, String mPw, long reg
				member = new MemberVO(
						rs.getInt("mNum"),			// 1
						rs.getString("mName"),		// 2
						rs.getString("mId"),		// 3
						rs.getString("mPw"),		// 4
						rs.getLong("reg")			// 5
						
				);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs,pstmt);
		}
		
		return member;
	}

	@Override
	public MemberVO selectMember(int mNum) {
		
		MemberVO member = null;
		
		String sql = "SELECT * FROM tbl_member WHERE mNum = " + mNum;
		System.out.println(sql);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		
			if(rs.next()) {
				member = new MemberVO(rs.getInt("mNum"),rs.getString("mName"),rs.getString("mId"),rs.getString("mPw"),rs.getLong("Reg"));
			}
			
		} catch (SQLException e) {
			
		}finally {
			DBUtil.close(rs,stmt);
		}
		
		return member;
	}

	@Override
	public boolean selectMember(String mId) {
		boolean isChecked = true;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM tbl_member WHERE mid = ?");
			pstmt.setString(1, mId);
			// "SELECT * FROM tbl_member WHERE mId = mId;
			rs = pstmt.executeQuery();
			if(rs.next()) {
				isChecked = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			isChecked = false;
		}finally {
			
			DBUtil.close(rs,pstmt);
			
			/*
			try {
				if(rs != null) rs.close();
			} catch (SQLException e) {}
			
			try {
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {}
			*/
			
		}
		
		return isChecked;
	}

	@Override
	public ArrayList<MemberVO> select() {
		ArrayList<MemberVO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM tbl_member ORDER BY mNum DESC";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				MemberVO member = new MemberVO();
				int mNum = rs.getInt(1);
				String mName = rs.getString(2);
				String mId = rs.getString(3);
				String mPw = rs.getString(4);
				long reg = rs.getLong(5);
				
				member.setmNum(mNum);
				member.setmName(mName);
				member.setmId(mId);
				member.setmPw(mPw);
				member.setReg(reg);
				list.add(member);
				
			}
			
		} catch (SQLException e) {}
		finally {
			DBUtil.close(rs,stmt);
		}
		
		return list;
	}

	@Override
	public int update(MemberVO member) {
		int result = 0;
		
		String sql = "UPDATE tbl_member SET mName = ? WHERE mNum = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getmName());
			pstmt.setInt(2, member.getmNum());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pstmt);
		}
		
		return result;
	}

	@Override
	public int delete(int mNum) {
		
		// 탈퇴 요청한 회원 번호 mNum
		// 탈퇴 요청한 회원 정보를 검색해서 back_up_member 테이블에 등록 시킨 후
		// tbl_member 활성 회원 테이블에서 삭제
		
		int result = 0;
		
		MemberVO deleteMember = selectMember(mNum);
		
		String sql = "INSERT INTO back_up_member VALUES(?,?,?,?,?,now());";
		
		try {
		
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mNum);
			pstmt.setString(2, deleteMember.getmName());
			pstmt.setString(3, deleteMember.getmId());
			pstmt.setString(4, deleteMember.getmPw());
			pstmt.setLong(5, deleteMember.getRealReg());
			
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				sql = "DELETE FROM tbl_member WHERE mNum = "+mNum;
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			DBUtil.close(pstmt,stmt);
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return result;
	}

	@Override
	public ArrayList<BackUpMemberVO> deleteMember() {

		ArrayList<BackUpMemberVO> deletes = new ArrayList<>();
		
		String sql = "SELECT * FROM back_up_member ORDER BY deleteDate DESC";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				BackUpMemberVO vo = new BackUpMemberVO(
						rs.getInt(1),			// mNum
						rs.getString(2),		// mName
						rs.getString(3),		// mId
						rs.getString(4),		// mPw 	
						rs.getLong(5),			// reg
						rs.getTimestamp(6)		// deleteDate
						);
				deletes.add(vo);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs,stmt);
		}
		
		
		return deletes;
	}

}
