package smt.action;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import smt.model.User;
import smt.service.ChartService;
import smt.service.UserService;


import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginAction.class);

	private UserService userService;
	private User user;
	
	public String execute( ) throws Exception {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-service.xml");
//		UserService service = (UserService) ctx.getBean("userService");
		System.out.println("execute()");
		return SUCCESS;
	}

	//用户登陆检查
	public void check() throws IOException{
		logger.info("测试日志");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		System.out.println("LoginAction check()方法开始");
		System.out.println("用户名："+user.getUsername()+",密码："+user.getPassword());
		String result = userService.check(user);
		if("success".equals(result)){
			request.getSession().setAttribute("user", userService.getUser(user));
		}
		response.getWriter().print(result);
	}
	
	//check()得到success后进入主页
		public String enter() {
			System.out.println("进入enter()方法");
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
			if (null != user) {
				return "home";
			} else {
				return "timeout";
			}
		}
	
	//注销session
		public String logoff( )  {
			// 消除用户的session
			HttpServletRequest request = ServletActionContext.getRequest();
			@SuppressWarnings("unchecked")
			Enumeration<String> names = request.getSession().getAttributeNames();
			while (names.hasMoreElements()) {
				request.getSession().removeAttribute(names.nextElement());
			}
			return SUCCESS;
		}	
		
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
//change test
