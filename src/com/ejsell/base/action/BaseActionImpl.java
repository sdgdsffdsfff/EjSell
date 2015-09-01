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
 * Action基础实现类
 * 
 * @author 陈捷
 *
 */
public class BaseActionImpl {

	public String errormessage;// 错误消息
	public final String SUCCESS_CODE = "1";// 成功代码
	public final String FAIL_CODE = "0";// 失败代码

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	// 初始化分页页面
	public Integer initPage(Integer page) {
		return page == null ? 1 : page;
	}

	// 初始化分页大小
	public Integer initRows(Integer rows) {
		return rows == null ? 10 : rows;
	}

	/**
	 * 初始化日期格式/客户信息校验
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}

	/**
	 * MVC异常处理
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ Throwable.class })
	public ModelAndView handlerException(Throwable e) {
		ModelAndView mav = new ModelAndView("/common/Message");
		String message = "";
		if (e instanceof BindException) {
			message = "数据字段不合法!BindException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof NumberFormatException) {
			message = "数字类型转换异常!NumberFormatException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof TypeMismatchException) {
			message = "字符串类型转换为数字类型错误!TypeMismatchException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof MissingServletRequestParameterException) {
			message = "前端请求缺少参数!MissingServletRequestParameterException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof SocketException) {
			message = "前端页面已经关闭!SocketException";
			mav.addObject(message);
			return mav;
		}

		if (e instanceof ClientAbortException) {
			message = "前端页面已经关闭!SocketException";
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
	 * 获取错误的原因
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
	 * 将jsonMap转换为json字符串
	 * 
	 * @param jsonMap
	 * @return json字符串
	 */
	public String convertJson(Map<String, Object> jsonMap) {
		return JSON.toJSONString(jsonMap);
	}

	/**
	 * 创建指定大小的Map对象
	 * 
	 * @param num map大小
	 * @return Map<String, Object>
	 */
	public Map<String, Object> createJsonMap(int num) {
		return new HashMap<String, Object>(num);
	}

	/**
	 * 创建默认大小的map对象
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> createJsonMap() {
		return createJsonMap(10);
	}

	/**
	 * 生成文件名
	 * 
	 * @param request
	 * @param subDir 子文件夹位置
	 * @param upload spring mvc的MultipartFile
	 * @return 生成文件夹后保存，并返回文件的绝对路径
	 */
	@SuppressWarnings("deprecation")
	public String genFileName(HttpServletRequest request, String subDir, MultipartFile upload) {
		Date dt = new Date();
		String realPath = request.getRealPath("/");
		String originalFilename = upload.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

		String fileUri = "upload/" + subDir + "/" + DateUtils.formatDateAsyyyymmdd(dt) + "/";
		String savePath = realPath + fileUri;
		savePath = savePath.replace("\\", "/");// 替换转义字符
		FileUtils.recursiveDir(savePath);// 递归创建文件夹
		String fileName = DateUtils.formatTimeAshhmiss(dt) + suffix;
		savePath += fileName;

		return savePath;
	}

	/**
	 * 返回JSON状态码
	 * 
	 * @param e 错误信息，如果为空则认为处理成功，否则会返回前端错误信息
	 * @return json状态Map<String, Object>
	 */
	public Map<String, Object> getJsonStatus(Exception e) {
		Map<String, Object> jsonStatus = new HashMap<String, Object>(3);
		if (e == null) {
			// 正常代码
			jsonStatus.put("code", SUCCESS_CODE);
			return jsonStatus;
		}

		// 错误代码
		jsonStatus.put("code", FAIL_CODE);
		jsonStatus.put("message", getExceptionCause(e));

		return jsonStatus;
	}

	/**
	 * 获取JSON状态码，正常情况返回message，错误情况返回错误消息
	 * 
	 * @param e 异常信息，为空则认为正常执行代码，否则认为执行失败
	 * @param message 返回消息，正常执行返回message，执行异常返回异常原因
	 * @return json返回信息Map<String, Object>
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
