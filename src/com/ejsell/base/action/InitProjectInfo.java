package com.ejsell.base.action;

import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 初始化加载项目的信息
 * 
 * @author 陈捷
 *
 */
@WebServlet("/InitProjectInfo")
public class InitProjectInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitProjectInfo() {
		super();
	}

	/**
	 * 初始化信息到Application中
	 */
	public void init(ServletConfig config) throws ServletException {
		Enumeration<String> parameterNames = config.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameter = (String) parameterNames.nextElement();
			this.initApplicaton(config, parameter);
		}
		
		//初始化网站访问次数
		config.getServletContext().setAttribute("visit_count", 1);
	}

	/**
	 * 将初始化参数中的信息写入到Application中
	 * 
	 * @param config
	 * @param parameter
	 */
	private void initApplicaton(ServletConfig config, String parameter) {
		String value = config.getInitParameter(parameter);
		config.getServletContext().setAttribute(parameter, value);
	}

}
