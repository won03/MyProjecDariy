package action;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryDAO;
import dao.MemberDAO;
import vo.DiaryVO;
import vo.MemberVO;

/**
 * Servlet implementation class SelectListAction
 */
@WebServlet("/select_list")
public class diary_SelectListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//----------------------- 해당 회원의 일기 리스트 조회 ------------------------------//
		
		//로그인한 id 받아서 
		//해당 id의 diary 기록 list (idx, title, content, the_date 컬럼 내용 포함) 리턴 
		
		request.setCharacterEncoding("utf-8");
		MemberDAO member_dao 	= MemberDAO.getInstance();
		DiaryDAO diary_dao 		= DiaryDAO.getInstance();
		
		String id = request.getParameter("id");
		
		// member table에서 diary table을 조회할 member_idx를 가져온다.
		MemberVO member_vo	= new MemberVO();
		member_vo.setId(id);
		member_vo			= member_dao.selectOne(member_vo);
		
		if(member_vo == null) {
			response.setContentType("text/plain; charset=utf-8");
			response.getWriter().println( "{\"result\":\"F\"}" );
			return;
		}
		
		
		int member_idx		= member_vo.getIdx();
		System.out.println("diary_SelectListAction id로 가져온 member_idx :" + member_idx);
		
		
		// 가져온 member_idx로 diary table에서 diary list를 조회
		List<DiaryVO> diary_vo_list = diary_dao.selectByMember(member_idx);
		
		// diary list를 json으로 출력
		StringBuffer sb				= new StringBuffer();
		sb.append("[");
		
		for(int i = 0 ; i < diary_vo_list.size() ; i++) {
			
			if(i > 0) sb.append(",");
			
			int idx			= diary_vo_list.get(i).getIdx();
			String title 	= diary_vo_list.get(i).getTitle();
			String content 	= diary_vo_list.get(i).getContent();
			Date the_date 	= diary_vo_list.get(i).getThe_date();
			
			String str = String.format(
					
					"{\"idx\":\"%d\","
					+"\"title\":\"%s\","
					+"\"content\":\"%s\","
					+"\"the_date\":\"%s\""
					+ "}"
					, 
					idx,
					title,
					content,
					the_date);
			
			sb.append(str);
		}
		sb.append("]");
		
		String result_list = sb.toString();
		System.out.println(result_list);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result_list );
	}

}
