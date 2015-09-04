package com.ejsell.store.service;

import java.util.List;

import com.ejsell.store.entity.SellReturn;
import com.persistent.dao.HibernateCRUD;

public interface SellReturnService extends HibernateCRUD<SellReturn> {

	/**
	 * 生成导出数据的sql语句
	 * 
	 * @param listSizeName 尺码名称列表
	 * @param isExists 导出数据是否存在销售出货表中，1存在，0不存在
	 * @return
	 */
	String genExportSql(List<String> listSizeName, int isExists);

}
