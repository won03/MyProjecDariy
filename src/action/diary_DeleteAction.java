package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryDAO;
import dao.MemberDAO;

/**
 * Servlet implementation class DeleteAction
 */
@WebServlet("/delete")
public class diary_DeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//----------------------- 일기 삭제  ------------------------------//
		
		request.setCharacterEncoding("utf-8");
		DiaryDAO diary_dao = DiaryDAO.getInstance();
		
		int idx = Integer.parseInt(request.getParameter("idx")); //content_id
		int result_count = diary_dao.delete(idx);
		
		String result;
		if(result_count > 0) {
			result = "S";
		}else {
			result = "F";
		}
		
		String result_str = String.format("{\"result\":\"%s\"}", result);
		
		System.out.println("diary_DeleteAction 결과 확인 : " + result_str);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result_str );
	}

}
