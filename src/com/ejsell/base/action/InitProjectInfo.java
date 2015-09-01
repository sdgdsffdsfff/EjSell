package com.ejsell.base.action;

import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * ��ʼ��������Ŀ����Ϣ
 * 
 * @author �½�
 *
 */
@WebServlet("/InitProjectInfo")
public class InitProjectInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitProjectInfo() {
		super();
	}

	/**
	 * ��ʼ����Ϣ��Application��
	 */
	public void init(ServletConfig config) throws ServletException {
		Enumeration<String> parameterNames = config.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameter = (String) parameterNames.nextElement();
			this.initApplicaton(config, parameter);
		}
		
		//��ʼ����վ���ʴ���
		config.getServletContext().setAttribute("visit_count", 1);
	}

	/**
	 * ����ʼ�������е���Ϣд�뵽Application��
	 * 
	 * @param config
	 * @param parameter
	 */
	private void initApplicaton(ServletConfig config, String parameter) {
		String value = config.getInitParameter(parameter);
		config.getServletContext().setAttribute(parameter, value);
	}

}
