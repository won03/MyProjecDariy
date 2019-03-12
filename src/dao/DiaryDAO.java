package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MyBatisConnector;
import vo.DiaryVO;

public class DiaryDAO {
	//--------------------------------------- 싱글톤패턴
			private static DiaryDAO single = null;
			SqlSessionFactory factory= null;
			
			public static DiaryDAO getInstance() {
				if( single == null ) {
					single = new DiaryDAO();
				}
				
				return single;
			}
   //---------------------------------------
			
			public DiaryDAO() {
				factory = MyBatisConnector.getInstance().getSqlSessionFactory();
			}
			
  //---------------------------------------

			// 일기 등록
			public int insert(DiaryVO diary_vo) {
				int result;
				
				SqlSession sqlSession = factory.openSession();
				result = sqlSession.insert("diary.diary_insert",diary_vo);
				
				sqlSession.commit();
				sqlSession.close();
				
				return result;
			}

			// 일기 수정
			public int update(DiaryVO diary_vo) {
				int result;
				
				SqlSession sqlSession = factory.openSession();
				result = sqlSession.update("diary.diary_update",diary_vo);
				
				sqlSession.commit();
				sqlSession.close();
				
				return result;
			}

			// 일기 삭제
			public int delete(int idx) {
				int result;
				
				SqlSession sqlSession = factory.openSession();
				result = sqlSession.delete("diary.diary_delete",idx);
				
				sqlSession.commit();
				sqlSession.close();
				
				return result;
			}

			// 해당 회원의 일기 리스트 조회
			public List<DiaryVO> selectByMember(int member_idx) {
				List<DiaryVO> diary_vo;
				
				SqlSession sqlSession = factory.openSession();
				diary_vo = sqlSession.selectList("diary.diary_select_member", member_idx);
				sqlSession.close();
				
				return diary_vo;
			}

			// 일기 idx로 상세 조회
			public DiaryVO selectByIdx(int idx) {
				DiaryVO diary_vo;
				
				SqlSession sqlSession = factory.openSession();
				diary_vo = sqlSession.selectOne("diary.diary_select_idx", idx);
				sqlSession.close();
				
				return diary_vo;
			}
}
