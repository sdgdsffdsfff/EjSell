package com.ejsell.base.action;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.chenjie.util.DateUtils;
import com.chenjie.util.FileUtils;

/**
 * Action����ʵ����
 * 
 * @author �½�
 *
 */
public class BaseActionImpl {

	public String errormessage;// ������Ϣ
	public final String SUCCESS_CODE = "1";// �ɹ�����
	public final String FAIL_CODE = "0";// ʧ�ܴ���

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	// ��ʼ����ҳҳ��
	public Integer initPage(Integer page) {
		return page == null ? 1 : page;
	}

	// ��ʼ����ҳ��С
	public Integer initRows(Integer rows) {
		return rows == null ? 10 : rows;
	}

	/**
	 * ��ʼ�����ڸ�ʽ/�ͻ���ϢУ��
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}

	/**
	 * MVC�쳣����
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ Throwable.class })
	public ModelAndView handlerException(Throwable e) {
		ModelAndView mav = new ModelAndView("/common/Message");
		String message = "";
		if (e instanceof BindException) {
			message = "�����ֶβ��Ϸ�!BindException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof NumberFormatException) {
			message = "��������ת���쳣!NumberFormatException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof TypeMismatchException) {
			message = "�ַ�������ת��Ϊ�������ʹ���!TypeMismatchException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof MissingServletRequestParameterException) {
			message = "ǰ������ȱ�ٲ���!MissingServletRequestParameterException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof SocketException) {
			message = "ǰ��ҳ���Ѿ��ر�!SocketException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof ClientAbortException) {
			message = "ǰ��ҳ���Ѿ��ر�!SocketException";
			mav.addObject(message);
			return mav;
		}

		message = getExceptionCause(e);
		mav.addObject(message);
		return mav;
	}

	public String formatDate() {
		return DateUtils.formatDate(new Date());
	}

	/**
	 * ��ȡ�����ԭ��
	 * 
	 * @param e
	 * @return
	 */
	public String getExceptionCause(Throwable e) {
		String message = "";
		try {
			message = e.getCause().getMessage();
		} catch (Throwable e1) {
			message = e.getMessage();
		}

		return message;
	}


	/**
	 * ��jsonMapת��Ϊjson�ַ���
	 * 
	 * @param jsonMap
	 * @return json�ַ���
	 */
	public String convertJson(Map<String, Object> jsonMap) {
		return JSON.toJSONString(jsonMap);
	}

	/**
	 * ����ָ����С��Map����
	 * 
	 * @param num map��С
	 * @return Map<String, Object>
	 */
	public Map<String, Object> createJsonMap(int num) {
		return new HashMap<String, Object>(num);
	}

	/**
	 * ����Ĭ�ϴ�С��map����
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> createJsonMap() {
		return createJsonMap(10);
	}

	/**
	 * �����ļ���
	 * 
	 * @param request
	 * @param subDir ���ļ���λ��
	 * @param upload spring mvc��MultipartFile
	 * @return �����ļ��к󱣴棬�������ļ��ľ���·��
	 */
	@SuppressWarnings("deprecation")
	public String genFileName(HttpServletRequest request, String subDir, MultipartFile upload) {
		Date dt = new Date();
		String realPath = request.getRealPath("/");
		String originalFilename = upload.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

		String fileUri = "upload/" + subDir + "/" + DateUtils.formatDateAsyyyymmdd(dt) + "/";
		String savePath = realPath + fileUri;
		savePath = savePath.replace("\\", "/");// �滻ת���ַ�
		FileUtils.recursiveDir(savePath);// �ݹ鴴���ļ���
		String fileName = DateUtils.formatTimeAshhmiss(dt) + suffix;
		savePath += fileName;

		return savePath;
	}

	/**
	 * ����JSON״̬��
	 * 
	 * @param e ������Ϣ�����Ϊ������Ϊ����ɹ�������᷵��ǰ�˴�����Ϣ
	 * @return json״̬Map<String, Object>
	 */
	public Map<String, Object> getJsonStatus(Exception e) {
		Map<String, Object> jsonStatus = new HashMap<String, Object>(3);
		if (e == null) {
			// ��������
			jsonStatus.put("code", SUCCESS_CODE);
			return jsonStatus;
		}

		// �������
		jsonStatus.put("code", FAIL_CODE);
		jsonStatus.put("message", getExceptionCause(e));

		return jsonStatus;
	}

	/**
	 * ��ȡJSON״̬�룬�����������message������������ش�����Ϣ
	 * 
	 * @param e �쳣��Ϣ��Ϊ������Ϊ����ִ�д��룬������Ϊִ��ʧ��
	 * @param message ������Ϣ������ִ�з���message��ִ���쳣�����쳣ԭ��
	 * @return json������ϢMap<String, Object>
	 */
	public Map<String, Object> getJsonStatus(Exception e, String message) {
		Map<String, Object> jsonStatus = new HashMap<String, Object>(3);
		if (e == null) {
			jsonStatus.put("code", SUCCESS_CODE);
			jsonStatus.put("message", message);
			return jsonStatus;
		}

		jsonStatus.put("code", FAIL_CODE);
		jsonStatus.put("message", getExceptionCause(e));

		return jsonStatus;
	}

}
