package com.ejsell.store.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chenjie.util.FileUtils;
import com.ejsell.base.action.BaseAction;
import com.ejsell.base.action.BaseActionImpl;
import com.ejsell.store.entity.SysConfig;
import com.ejsell.store.service.SysConfigService;
import com.ejsell.store.service.SysConfigServiceImpl;

@Controller
public class SysConfigAction extends BaseActionImpl implements BaseAction<SysConfig> {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	SysConfigService sysConfigService;

	@Override
	public String add(SysConfig t) {
		return null;
	}

	@Override
	public String del(Long id) {
		return null;
	}

	@Override
	public String edit(SysConfig t) {
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
	 * 安装页面
	 * 
	 * @return
	 */
	@RequestMapping("/SysConfigAction/installPage")
	public ModelAndView installPage() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	/**
	 * 安装结束页面
	 * 
	 * @param sizeNames 尺码名称(用逗号分隔)
	 * @param sizeJumpLine 尺码行相对于商品编码行跳跃行数。恩爵=1，璐嘉儿=0
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/SysConfigAction/install")
	public ModelAndView install(@RequestParam("sizeNames") String sizeNames, String sizeJumpLine,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("common/Message");

		if (sizeNames == null || sizeNames.trim().isEmpty() || sizeNames.trim().length()==0) {
			mav.addObject("message", "安装失败,尺码不能为空!");
			return mav;
		}

		try {
			// 首先清空原来的配置信息
			String sql_clear_all_config = "truncate table SYS_CONFIG";
			sysConfigService.executeSQL(sql_clear_all_config);

			String[] strSizeName = sizeNames.split(",");
			List<SysConfig> listSysConfig = new ArrayList<SysConfig>();
			for (String sizeName : strSizeName) {
				SysConfig sysConfig = new SysConfig();
				sysConfig.setName(SysConfigServiceImpl.config_size_col_name);
				if(sizeName.isEmpty()){
					mav.addObject("message", "尺码不能为空!配置失败，请重新配置。");
					return mav;
				}
				sysConfig.setVal(sizeName);
				listSysConfig.add(sysConfig);
			}

			SysConfig sysConifg = new SysConfig();
			sysConifg.setName(SysConfigServiceImpl.config_size_jump_line_name);
			sysConifg.setVal(sizeJumpLine);
			listSysConfig.add(sysConifg);

			sysConfigService.addBatch(listSysConfig);
			
			//删除安装文件
			String installFilePath = request.getRealPath("/") + "install.inf";
			installFilePath.replace("\\", "/");
			if (FileUtils.fileExists(installFilePath)) {
				FileUtils.removeFile(installFilePath);
			}
			
			mav.addObject("message", "初始化安装成功!");
		} catch (Exception e) {
			logger.error(e);
			mav.addObject("message", "安装失败，请检查系统!"+e);
		}

		return mav;
	}

}
