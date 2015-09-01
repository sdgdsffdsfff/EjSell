package com.ejsell.store.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ejsell.base.action.BaseAction;
import com.ejsell.base.action.BaseActionImpl;
import com.ejsell.store.entity.SellOut;

public class SellOutAction extends BaseActionImpl implements BaseAction<SellOut> {

	@Override
	public String add(SellOut sellOut) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String del(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(SellOut sellOut) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView addPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView editPage(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView jsonListPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJsonList(String sqlwhere, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 数据导入页
	 * 
	 * @return
	 */
	@RequestMapping("/importSellOutPage")
	public ModelAndView importSellOutPage() {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

}
