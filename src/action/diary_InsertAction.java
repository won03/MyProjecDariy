package action;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

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
 * Servlet implementation class InsertAction
 */
@WebServlet("/insert")
public class diary_InsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//----------------------- 일기 등록 insert ------------------------------//
		
		request.setCharacterEncoding("utf-8");
		MemberDAO member_dao	= MemberDAO.getInstance();
		DiaryDAO diary_dao		= DiaryDAO.getInstance();
		
		String id			= request.getParameter("id");
		String content		= request.getParameter("content");
		String title		= request.getParameter("title");
		
		LocalDate localDate = LocalDate.now();
		Date the_date		= Date.valueOf(localDate); //날짜 생성
		
		// 파라미터로 받은 id로 member 레코드의 member_idx를 조회.
		MemberVO member_vo = new MemberVO();
		member_vo.setId(id);
		member_vo		= member_dao.selectOne(member_vo);
		int member_idx	= member_vo.getIdx();
		
		// 위에서 가져온 member_idx와 파라미터로 받은 기타 정보를 가지고 diary_vo생성해서 insert
		DiaryVO diary_vo = new DiaryVO(title, content, the_date, member_idx);
		int result_count = diary_dao.insert(diary_vo);
		
		// 결과 확인
		String result;
		if(result_count > 0) {
			result = "S";
			
		}else {
			result = "F";
		}
		
		// json 출력
		String result_str = String.format("{\"result\":\"%s\"}", result);
		
		System.out.println("dairy_InsertAction 결과 확인 : " + result_str);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result_str );
	}
}
