package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MyBatisConnector;
import vo.MemberVO;

public class MemberDAO {
	//--------------------------------------- 싱글톤패턴
			private static MemberDAO single = null;
			SqlSessionFactory factory= null;
			
			public static MemberDAO getInstance() {
				if( single == null ) {
					single = new MemberDAO();
				}
				
				return single;
			}
			
			//---------------------------------------
			

			public MemberDAO() {
				factory = MyBatisConnector.getInstance().getSqlSessionFactory();
			}
			
			//---------------------------------------

			// 회원가입
			public int insert(MemberVO member_vo) {
				int result;
				SqlSession sqlSession = factory.openSession();
				result = sqlSession.insert("member.member_insert",member_vo);
				
				sqlSession.commit();
				sqlSession.close();
				
				return result;
			}

			// 같은 아이디가 있는지 확인
			public MemberVO selectOne(MemberVO member_vo) {
				MemberVO member_vo_same;
				
				SqlSession sqlSession = factory.openSession();
				member_vo_same = sqlSession.selectOne("member.member_select", member_vo);
				sqlSession.close();
				
				return member_vo_same;
			}
			


}
