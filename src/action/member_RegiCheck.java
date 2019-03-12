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
 * Servlet implementation class member_RegiCheck
 */
@WebServlet("/regiCheck")
public class member_RegiCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//--------------------아이디체크----------------------------------//
		request.setCharacterEncoding("utf-8");
		MemberDAO member_dao = MemberDAO.getInstance();
		String id=request.getParameter("id");
		
		MemberVO member_vo		= new MemberVO(id);
		MemberVO member_vo_same = member_dao.selectOne(member_vo);
		String result;
		if(member_vo_same==null) {
			result="Y";
		}else {
			result="F";
		}
		
		/*서버 응답결과을 볼수있는 로직*/ 
		String result_str=String.format("{\"result\":\"%s\"}", result);
		System.out.println("RegiCheck 결과 확인 : " + result_str);
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println(result_str);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */


}
