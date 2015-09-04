package com.ejsell.store.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chenjie.util.DateUtils;
import com.chenjie.util.ExcelUtils;
import com.ejsell.base.action.BaseAction;
import com.ejsell.base.action.BaseActionImpl;
import com.ejsell.store.entity.SellOut;
import com.ejsell.store.entity.SellReturn;
import com.ejsell.store.service.SellReturnService;
import com.ejsell.store.service.SysConfigService;

@Controller
public class SellReturnAction extends BaseActionImpl implements BaseAction<SellReturn> {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SellReturnService sellReturnService;
	@Autowired
	private SysConfigService sysConfigService;

	@Override
	public String add(SellReturn sellReturn) {

		return null;
	}

	@Override
	public String del(Long id) {

		return null;
	}

	@Override
	public String edit(SellReturn sellReturn) {

		return null;
	}

	@Override
	public ModelAndView addPage() {

		return null;
	}

	@Override
	public ModelAndView editPage(Long id) {

		return null;
	}

	@Override
	public ModelAndView jsonListPage() {

		return null;
	}

	@Override
	public String getJsonList(String sqlwhere, Integer page, Integer rows) {

		return null;
	}

	/**
	 * 导入代销回货页面
	 * 
	 * @return
	 */
	@RequestMapping("/SellReturnAction/importSellReturnPage")
	public ModelAndView importSellReturnPage() {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	/**
	 * 销售出货导入
	 * 
	 * @param upload input控件的name命名为upload
	 * @param request (不需要传入)
	 * @param response (不需要传入)
	 */
	@RequestMapping("/SellReturnAction/importSellReturn")
	public ModelAndView importSellReturn(@RequestParam("upload") MultipartFile upload, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("common/Message");
		if (upload.getSize() <= 0) {
			mav.addObject("message", "上传文件不能为空!");
			return mav;
		}

		if (!upload.getOriginalFilename().endsWith(".csv")) {
			mav.addObject("message", "文件格式不正确，请重新选择!");
			return mav;
		}

		String saveFileName = genFileName(request, "SellReturn", upload);
		File saveFile = new File(saveFileName);
		try {
			upload.transferTo(saveFile);// 保存文件
			List<SellOut> listSellOut = readSellOutByConfig(saveFileName, sysConfigService.getListSizeName(), sysConfigService.getSizeJumpLine());// (saveFileName);
			int total_amount = 0;// 总件数
			// 销售出货单转换成销售退货单
			List<SellReturn> listSellReturn = new ArrayList<SellReturn>();
			for (SellOut sellOut : listSellOut) {
				SellReturn sellReturn = new SellReturn();
				sellReturn.setModel(sellOut.getModel());
				sellReturn.setColor(sellOut.getColor());
				sellReturn.setSize(sellOut.getSize());
				sellReturn.setAmount(sellOut.getAmount());
				listSellReturn.add(sellReturn);
				total_amount += sellOut.getAmount();
			}

			sellReturnService.addBatch(listSellReturn);
			mav.addObject("message", "导入'" + upload.getOriginalFilename() + "'成功，总共导入" + total_amount + "件。");
		} catch (Exception e) {
			logger.error(e);
			mav.addObject("message", getExceptionCause(e));
		}

		return mav;
	}

	/**
	 * 导出数据
	 * 
	 * @param isExists 是否存在，1存在销售出货单中，0不存在销售出货单中
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/SellReturnAction/downSellReturn")
	public ModelAndView downSellReturn(int isExists, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<String> listSizeName = sysConfigService.getListSizeName();
		ExcelUtils excel = new ExcelUtils();
		List<String> titles = new ArrayList<String>(9);
		titles.add("商品编号");
		titles.add("颜色");
		List<String> fields = new ArrayList<String>(9);
		fields.add("MODEL");
		fields.add("COLOR");
		for (String sizeName : listSizeName) {
			titles.add("尺码"+sizeName);
			fields.add("SIZE_" + sizeName);
		}

		String fileName = "";
		if (isExists == 1) {
			fileName = "EJSELL_HHSJ(" + DateUtils.formatDateAsyyyymmdd(new Date()) + ").xls";
		} else {
			fileName = "EJSELL_DMSJ(" + DateUtils.formatDateAsyyyymmdd(new Date()) + ").xls";
		}

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/msexcel;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		List<Map<String, Object>> listSellReturn = sellReturnService.queryBySql(sellReturnService.genExportSql(listSizeName, isExists), null);
		InputStream is = excel.getInputStream(titles, fields, listSellReturn);
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buffer = new byte[1024];
		while (is.read(buffer) > 0) {
			bos.write(buffer);
		}
		bos.close();
		is.close();

		return null;
	}

}
