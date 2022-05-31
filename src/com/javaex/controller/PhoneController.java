package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;


@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;

	
	//생성자
    //디폴트 생성자 사용
    //메소드 gs
  	

	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//코드작성
		
		
		//포스트방식일때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String action =  request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {
			
			//데이터 가져오기
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> phoneList = phoneDao.getPersonList();
			System.out.println(phoneList);
			//데이터 + html --> jsp로 실행
			
			//request에 데이터 추가
			request.setAttribute("pList", phoneList);
			
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
			
			/*
			 * RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/list.jsp");
			 * rd.forward(request, response);
			 */
			
		}else if("writeForm".equals(action)) {//action을 뒤로넣어주면 오류가 안생김 "writeForm".equals(action)
			//포어드
			WebUtil.forward(request, response, "WEB-INF/writeForm.jsp");
			/*
			 * RequestDispatcher rd= request.getRequestDispatcher("/writeForm.jsp");
			 * rd.forward(request, response);
			 */
		}else if("write".equals(action)) {
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			PhoneDao phoneDao = new PhoneDao();
			PersonVo personVo = new PersonVo(name, hp , company);
			
			phoneDao.personInsert(personVo);
			
			response.sendRedirect("./pbc?action=list");
			
		}else if ("updateForm".equals(action)) {
			PhoneDao phoneDao = new PhoneDao();
			int id = Integer.parseInt(request.getParameter("id"));
			PersonVo personVo = phoneDao.getPerson(id);
			
			request.setAttribute("personVo", personVo);
			
			WebUtil.forward(request, response, "WEB-INF/updateForm.jsp");
		}else if ("update".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			PhoneDao phoneDao = new PhoneDao();
			PersonVo personVo = new PersonVo(id,name,hp,company);
			
			phoneDao.personUpdate(personVo);
			
			response.sendRedirect("./pbc?action=list");
		}else if("delete".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			PhoneDao phoneDao = new PhoneDao();
			
			int count = phoneDao.personDelete(id);
			System.out.println(count);
			
			
			response.sendRedirect("./pbc?action=list");
			
			
		}else {
			System.out.println("action 파라미터 없음");
		}
		
		
	

	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
