package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.MemberVO;

/**
 * Servlet implementation class LogInAction
 */
@WebServlet("/login")
public class member_LogInAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//----------------------- 로그인 ------------------------------//
		
		request.setCharacterEncoding("utf-8");
		MemberDAO member_dao = MemberDAO.getInstance();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
//		String id = "wj";
//		String pw = "wj";
		
		// 파라미터로 받은 id로 테이블에 해당 id 레코드를 불러온다. member_vo에 저장
		MemberVO member_vo = new MemberVO();
		member_vo.setId(id); // id는 파라미터로 받은 것!
		member_vo = member_dao.selectOne(member_vo);
		
		String result;
		
		System.out.printf("id=%s&pw=%s\n", id, pw);
		
		// id는 일단 존재
		if(member_vo != null) {
		
			if(member_vo.getPw().equals(pw)) {
				result = "S"; // pw 일치. 로그인 성공
			}else {
				result = "F"; // pw 불일치
			}
			
		// id 레코드에 없음
			
		}else {
			result = "N"; 
		}
		
		
		String result_str = String.format("{\"result\":\"%s\"}", result);
		
		System.out.println("LoginAction 결과 확인 : " + result_str);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result_str );
	}

}
