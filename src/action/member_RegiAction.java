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
 * Servlet implementation class RegiAction
 */
@WebServlet("/regi")
public class member_RegiAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//----------------------- 회원 가입 ------------------------------//
		
		request.setCharacterEncoding("utf-8");
		MemberDAO member_dao = MemberDAO.getInstance();
		
		String id	= request.getParameter("id");
		String pw	= request.getParameter("pw");
		String name = request.getParameter("name");
		
 		
		
		MemberVO member_vo		= new MemberVO(id, pw, name);
		MemberVO member_vo_same = member_dao.selectOne(member_vo);
		
		String result;
		// 아이디 중복가 중복되는 회원이 없다면!
		if(member_vo_same == null) {
			//중복없으면 Y
			result="Y";
			int result_count = member_dao.insert(member_vo); // 회원 테이블에 인서트, 가입시킨다.
			
			if(result_count > 0) {
				result = "S"; // 인서트 성공!
			}else {
				result = "D"; // 어떤 문제로 인해 인서트 실패
			}
		
		// 아이디가 중복임	
		}else {
			result = "F";
		}
		
		String result_str = String.format("{\"result\":\"%s\"}", result);
		/*서버 응답결과을 볼수있는 로직*/ 
		System.out.println("RegiAction 결과 확인 : " + result_str);
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println( result_str );
		
	}
}
