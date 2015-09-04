package com.ejsell.base.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ejsell.store.entity.SellOut;
import com.ejsell.store.entity.SellReturn;
import com.ejsell.store.service.SellOutService;
import com.opencsv.CSVReader;

/**
 * Action基础实现类
 * 
 * @author 陈捷
 *
 */
@SuppressWarnings({ "deprecation", "resource" })
public class BaseActionImpl {
	Logger logger = Logger.getLogger(this.getClass());
	public String errormessage;// 错误消息
	public final String SUCCESS_CODE = "1";// 成功代码
	public final String FAIL_CODE = "0";// 失败代码

	@Autowired
	public SellOutService sellOutService;

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
	public String genFileName(HttpServletRequest request, String subDir, MultipartFile upload) {
		Date dt = new Date();
		String realPath = request.getRealPath("/");
		String originalFilename = upload.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

		String fileUri = "../upload/" + subDir + "/" + DateUtils.formatDateAsyyyymmdd(dt) + "/";
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

	/**
	 * 读取csv文件，生成销售出货列表
	 * 
	 * @param csvFileName
	 * @return
	 */
	public List<SellOut> readSellOut(String csvFileName) throws Exception {
		String field_model = "商品编号";
		String field_color = "颜色";
		String pre_model = "";// 上一个的商品编号，用来处理本次的商品编号为空的情况
		int model_row_num = 0;// 商品编号行位置
		int color_col_num = 0;// 颜色列位置
		int size_col_36 = 0;// 尺码36列位置
		int size_col_38 = 0;// 尺码38列位置
		int size_col_40 = 0;// 尺码40列位置
		int size_col_42 = 0;// 尺码42列位置
		int size_col_34 = 0;// 尺码34列位置
		int size_col_M = 0;// 尺码M列位置

		// 初始化销售出库列表
		List<SellOut> listSellOut = new ArrayList<SellOut>();

		File inFile = new File(csvFileName);
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inFile), "ISO-8859-1");
		CSVReader csvReader = new CSVReader(inputStreamReader, ',');
		List<String[]> listData = csvReader.readAll();
		int row_num = 0;
		int list_data_size = listData.size();
		// 初始化商品编号行位置、颜色列位置、尺码列位置
		for (int i = 0; i < list_data_size; i++) {
			String[] strs = listData.get(i);
			// 直到循环到商品编号列后，进行定位操作
			if (field_model.equals(new String(strs[1].getBytes("ISO-8859-1"), "GBK"))) {
				model_row_num = i;// 商品编号出现的行位置
				// 第一步，定位颜色列号
				for (int j = 0; j < strs.length; j++) {
					if (field_color.equals(new String(strs[j].getBytes("ISO-8859-1"), "GBK"))) {
						color_col_num = j;
						break;// 定位到颜色的列号后，退出j循环
					}
				}

				// 第二步，定位尺码列号(恩爵数据文件的尺码在商品编号的下一行)
				String[] strs_size = listData.get(i + 1);
				for (int k = 0; k < strs_size.length; k++) {
					if ("34".equals(strs_size[k])) {
						size_col_34 = k;
					}
					if ("36".equals(strs_size[k])) {
						size_col_36 = k;
					}
					if ("38".equals(strs_size[k])) {
						size_col_38 = k;
					}
					if ("40".equals(strs_size[k])) {
						size_col_40 = k;
					}
					if ("42".equals(strs_size[k])) {
						size_col_42 = k;
					}
					if ("M".equals(strs_size[k])) {
						size_col_M = k;
					}
				}

				break;// 定位完尺码列序号后退出k循环
			}

			row_num++;

		}// 初始化商品编号行位置、颜色列位置、尺码列位置结束

		// 判断一下商品编号列位置是否正常
		if (model_row_num == 0) {
			throw new Exception("商品编号列定位失败，请检查数据文件!");
		}

		// 判断一下颜色列位置是否正常
		if (color_col_num == 0) {
			throw new Exception("颜色列定位失败，请检查数据文件!");
		}

		// 判断一下尺码列位置是否正常
		if (size_col_34 == 0) {
			throw new Exception("尺码34,列定位失败，请检查数据文件!");
		}
		if (size_col_36 == 0) {
			throw new Exception("尺码36,列定位失败，请检查数据文件!");
		}
		if (size_col_38 == 0) {
			throw new Exception("尺码38,列定位失败，请检查数据文件!");
		}
		if (size_col_40 == 0) {
			throw new Exception("尺码40,列定位失败，请检查数据文件!");
		}
		if (size_col_42 == 0) {
			throw new Exception("尺码42,列定位失败，请检查数据文件!");
		}
		if (size_col_M == 0) {
			throw new Exception("尺码M,列定位失败，请检查数据文件!");
		}

		logger.info("model_row_num(商品编号出现的位置):" + model_row_num + ",row_num(循环了几次):" + row_num);

		// 从商品编号行+2开始读取，直到listData.size结束
		for (int i = model_row_num + 2; i < list_data_size; i++) {
			String[] data = listData.get(i);
			if (data[1] != null && !data[1].isEmpty()) {
				pre_model = data[1];// 赋值给上一个商品编号，如果商品编号为空，则可以使用此商品编号
			}

			// 初始化销售出库单
			// 如果尺码列34的编号有数字，则销售出库列表增加一条数据
			if (data[size_col_34] != null && !data[size_col_34].isEmpty()) {
				SellOut sellOut = new SellOut();
				sellOut.setModel(pre_model);
				sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
				sellOut.setSize("34");
				sellOut.setAmount(Integer.parseInt(data[size_col_34]));
				listSellOut.add(sellOut);
			}
			// 如果尺码列36的编号有数字，则销售出库列表增加一条数据
			if (data[size_col_36] != null && !data[size_col_36].isEmpty()) {
				SellOut sellOut = new SellOut();
				sellOut.setModel(pre_model);
				sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
				sellOut.setSize("36");
				sellOut.setAmount(Integer.parseInt(data[size_col_36]));
				listSellOut.add(sellOut);
			}
			// 如果尺码列38的编号有数字，则销售出库列表增加一条数据
			if (data[size_col_38] != null && !data[size_col_38].isEmpty()) {
				SellOut sellOut = new SellOut();
				sellOut.setModel(pre_model);
				sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
				sellOut.setSize("38");
				sellOut.setAmount(Integer.parseInt(data[size_col_38]));
				listSellOut.add(sellOut);
			}
			// 如果尺码列40的编号有数字，则销售出库列表增加一条数据
			if (data[size_col_40] != null && !data[size_col_40].isEmpty()) {
				SellOut sellOut = new SellOut();
				sellOut.setModel(pre_model);
				sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
				sellOut.setSize("40");
				sellOut.setAmount(Integer.parseInt(data[size_col_40]));
				listSellOut.add(sellOut);
			}
			// 如果尺码列42的编号有数字，则销售出库列表增加一条数据
			if (data[size_col_42] != null && !data[size_col_42].isEmpty()) {
				SellOut sellOut = new SellOut();
				sellOut.setModel(pre_model);
				sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
				sellOut.setSize("42");
				sellOut.setAmount(Integer.parseInt(data[size_col_42]));
				listSellOut.add(sellOut);
			}
			// 如果尺码列M的编号有数字，则销售出库列表增加一条数据
			if (data[size_col_M] != null && !data[size_col_M].isEmpty()) {
				SellOut sellOut = new SellOut();
				sellOut.setModel(pre_model);
				sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
				sellOut.setSize("M");
				sellOut.setAmount(Integer.parseInt(data[size_col_M]));
				listSellOut.add(sellOut);
			}

		}// for销售出库列表结束

		csvReader.close();

		return listSellOut;
	}

	/**
	 * 根据系统的配置来进行定位操作
	 * 
	 * @param csvFileName
	 * @param listSizeName 尺码名称
	 * @param sizeJumpLine 尺码行对应商品编号行需要跳跃的行数
	 * @return
	 * @throws Exception
	 */
	public List<SellOut> readSellOutByConfig(String csvFileName, List<String> listSizeName, int sizeJumpLine) throws Exception {
		String field_model = "商品编号";
		String field_color = "颜色";
		String pre_model = "";// 上一个的商品编号，用来处理本次的商品编号为空的情况
		int model_row_num = 0;// 商品编号行位置
		int color_col_num = 0;// 颜色列位置

		Map<String, Integer> map_size_col = new HashMap<String, Integer>();// 尺码列的哈希表

		// 初始化销售出库列表
		List<SellOut> listSellOut = new ArrayList<SellOut>();

		File inFile = new File(csvFileName);
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inFile), "ISO-8859-1");
		CSVReader csvReader = new CSVReader(inputStreamReader, ',');
		List<String[]> listData = csvReader.readAll();
		int row_num = 0;// 初始化行号
		int list_data_size = listData.size();
		// 初始化商品编号行位置、颜色列位置、尺码列位置
		for (int i = 0; i < list_data_size; i++) {
			String[] strs = listData.get(i);
			// 直到循环到商品编号列后，进行定位操作
			if (field_model.equals(new String(strs[1].getBytes("ISO-8859-1"), "GBK"))) {
				model_row_num = i;// 商品编号出现的行位置
				// 第一步，定位颜色列号
				for (int j = 0; j < strs.length; j++) {
					if (field_color.equals(new String(strs[j].getBytes("ISO-8859-1"), "GBK"))) {
						color_col_num = j;
						break;// 定位到颜色的列号后，退出j循环
					}
				}

				// 第二步，定位尺码列号(恩爵数据文件的尺码在商品编号的下一行=1,璐嘉儿是在商品编号同一行=0)
				String[] strs_size = listData.get(i + sizeJumpLine);
				for (int k = 0; k < strs_size.length; k++) {
					for (String sizeName : listSizeName) {
						if (sizeName.equals(strs_size[k])) {
							map_size_col.put(sizeName, k);
						}
					}
				}

				break;// 定位完尺码列序号后退出k循环
			}

			row_num++;

		}// 初始化商品编号行位置、颜色列位置、尺码列位置结束

		// 判断一下商品编号列位置是否正常
		if (model_row_num == 0) {
			throw new Exception("商品编号列定位失败，请检查数据文件!");
		}

		// 判断一下颜色列位置是否正常
		if (color_col_num == 0) {
			throw new Exception("颜色列定位失败，请检查数据文件!");
		}

		// 判断一下尺码列位置是否正常
		for (String sizeName : listSizeName) {
			// 如果尺码列的哈希表中不存在当前的尺码名称，则抛出异常
			if (!map_size_col.containsKey(sizeName)) {
				throw new Exception("尺码" + sizeName + ",列定位失败，请检查数据文件!");
			}
		}

		logger.info("model_row_num(商品编号出现的位置):" + model_row_num + ",row_num(循环了几次):" + row_num);

		// 从商品编号行+尺码跳跃行+1开始读取，直到listData.size结束
		for (int i = (model_row_num + sizeJumpLine + 1); i < list_data_size; i++) {
			String[] data = listData.get(i);
			if (data[1] != null && !data[1].isEmpty()) {
				pre_model = data[1];// 赋值给上一个商品编号，如果商品编号为空，则可以使用此商品编号
			}

			// 初始化销售出库单
			// 如果尺码列有数据，则销售出库列表增加一条数据
			for (String sizeName : listSizeName) {
				if (data[map_size_col.get(sizeName)] != null && !data[map_size_col.get(sizeName)].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(new String(data[color_col_num].getBytes("ISO-8859-1"), "GBK"));
					sellOut.setSize(sizeName);
					sellOut.setAmount(Integer.parseInt(data[map_size_col.get(sizeName)]));
					listSellOut.add(sellOut);
				}
			}

		}// for销售出库列表结束

		csvReader.close();

		return listSellOut;
	}

	/**
	 * 将销售出货列表转换成销售退货列表
	 * 
	 * @param listSellOut 销售出货列表
	 * @return
	 */
	public List<SellReturn> convertSellOutToSellReturn(List<SellOut> listSellOut) {
		List<SellReturn> listSellReturn = new ArrayList<SellReturn>();
		for (SellOut sellOut : listSellOut) {
			SellReturn sellReturn = new SellReturn();
			sellReturn.setModel(sellOut.getModel());
			sellReturn.setColor(sellOut.getColor());
			sellReturn.setSize(sellOut.getSize());
			sellReturn.setAmount(sellOut.getAmount());
			listSellReturn.add(sellReturn);
		}

		return listSellReturn;
	}


}
