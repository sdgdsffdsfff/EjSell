package com.ejsell.store.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ejsell.base.action.BaseAction;
import com.ejsell.base.action.BaseActionImpl;
import com.ejsell.store.entity.SellOut;
import com.ejsell.store.service.SellOutService;

@Controller
public class SellOutAction extends BaseActionImpl implements BaseAction<SellOut> {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private SellOutService sellOutService;

	@Override
	public String add(SellOut sellOut) {

		return null;
	}

	@Override
	public String del(Long id) {

		return null;
	}

	@Override
	public String edit(SellOut sellOut) {

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
	 * 导入代销出库页面
	 * 
	 * @return
	 */
	@RequestMapping("/SellOutAction/importSellOutPage")
	public ModelAndView importSellOutPage() {
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
	@RequestMapping("/SellOutAction/importSellOut")
	public ModelAndView importSellOut(@RequestParam("upload") MultipartFile upload, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("common/Message");
		if (upload.getSize() <= 0) {
			mav.addObject("message", "上传文件不能为空!");
			return mav;
		}

		if (!upload.getOriginalFilename().endsWith(".csv")) {
			mav.addObject("message", "文件格式不正确，请重新选择!");
			return mav;
		}

		String saveFileName = genFileName(request, "SellOut", upload);
		File saveFile = new File(saveFileName);
		try {
			upload.transferTo(saveFile);// 保存文件
			List<SellOut> listSellOut = readSellOut(saveFileName);

			int total_amount = 0;// 总件数
			for (SellOut sellOut : listSellOut) {
				total_amount += sellOut.getAmount();
			}

			sellOutService.addBatch(listSellOut);
			mav.addObject("message", "导入'" + upload.getOriginalFilename() + "'成功，总共导入" + total_amount + "件。");
		} catch (Exception e) {
			logger.error(e);
			mav.addObject("message", getExceptionCause(e));
		}

		return mav;
	}

	@RequestMapping("/toMainPage")
	public ModelAndView toMainPage() {
		ModelAndView mav = new ModelAndView("/index");
		return mav;
	}

	@RequestMapping("/SellOutAction/clearData")
	public ModelAndView clearData() {
		ModelAndView mav = new ModelAndView("/common/Message");
		mav.addObject("message", "清空销售出货、销售退货数据完成。");
		try {
			sellOutService.executeSQL("truncate table sell_out");
			sellOutService.executeSQL("truncate table sell_return");
		} catch (Exception e) {
			logger.error(e);
			mav.addObject("message", "清空数据失败，请检查系统服务是否正常!");
		}

		return mav;
	}

}
