package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryDAO;
import dao.MemberDAO;
import vo.DiaryVO;

/**
 * Servlet implementation class SelectDetailAction
 */
@WebServlet("/select_detail")
public class diary_SelectDetailAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//----------------------- 해당 일기 idx의 일기 content 조회 ------------------------------//
		
		request.setCharacterEncoding("utf-8");
		DiaryDAO diary_dao = DiaryDAO.getInstance();
		
		int idx 			= Integer.parseInt(request.getParameter("idx"));
		
		DiaryVO diary_vo 	= diary_dao.selectByIdx(idx);
		String result = null;
		
		if( diary_vo != null ) {
			result 		= String.format("{\"content\":\"%s\"}", diary_vo.getContent());
		}
		else { 
			result		= "{\"content\":\"\"}";
		}
		
		System.out.println("SelectDetailAction 결과 확인: " + result);
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result );
	}

}
