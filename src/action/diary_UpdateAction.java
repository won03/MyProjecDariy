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

/**
 * Servlet implementation class UpdateAction
 */
@WebServlet("/update")
public class diary_UpdateAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//----------------------- 일기 수정 update ------------------------------//
		
		request.setCharacterEncoding("utf-8");
		DiaryDAO diary_dao = DiaryDAO.getInstance();
		
		int idx 				= Integer.parseInt(request.getParameter("idx")); //content_id
		String content 			= request.getParameter("content");
		String title 			= request.getParameter("title");
		LocalDate localDate 	= LocalDate.now();
		Date the_date 			= Date.valueOf(localDate);
		
		/* 확인을 위한
		int idx 				= 3;
		String content 			= "고라니들을 혼내줍시다";
		String title 			= "고라니들에 대한 조처 결정";
		LocalDate localDate 	= LocalDate.now();
		Date the_date 			= Date.valueOf(localDate);
		*/
		
		DiaryVO diary_vo = new DiaryVO(idx, title, content, the_date);
		int result_count = diary_dao.update(diary_vo);
		
		String result;
		if(result_count > 0) {
			result = "S";
		}else {
			result = "F";
		}
		
		String result_str = String.format("{\"result\":\"%s\"}", result);
		
		System.out.println("diary_UpdateAction 결과 확인 : " + result_str);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result_str );
	}

}
