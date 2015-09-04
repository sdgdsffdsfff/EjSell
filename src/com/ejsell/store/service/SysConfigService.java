package com.ejsell.store.service;

import java.util.List;

import com.ejsell.store.entity.SysConfig;
import com.persistent.dao.HibernateCRUD;

public interface SysConfigService extends HibernateCRUD<SysConfig> {

	/**
	 * 获取尺码名称列表
	 * @return
	 * @throws Exception
	 */
	public List<String> getListSizeName() throws Exception;
	
	/**
	 * 获取尺码跳跃行
	 * @return
	 * @throws Exception
	 */
	public int getSizeJumpLine() throws Exception;

}
