package com.ejsell.store.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ejsell.store.entity.SysConfig;
import com.persistent.impl.Hibernate3CRUDImpl;

/**
 * 系统配置seviceInmpl
 * 
 * @author 陈捷
 *
 */
@Service
public class SysConfigServiceImpl extends Hibernate3CRUDImpl<SysConfig> implements SysConfigService {

	public static String config_size_col_name = "SIZE_COL";
	public static String config_size_jump_line_name = "SIZE_JUMP_LINE";

	@Override
	public List<String> getListSizeName() throws Exception {
		List<String> listSizeName = new ArrayList<String>();

		String hql = "from SysConfig where name='" + config_size_col_name + "' order by id ";
		List<SysConfig> listSysConfig = this.getEntityList(hql);
		if (listSysConfig == null || listSysConfig.size() == 0) {
			throw new Exception("尺码名称未配置，请初始化系统!");
		}

		for (SysConfig sysConfig : listSysConfig) {
			listSizeName.add(sysConfig.getVal());
		}

		return listSizeName;
	}

	public int getSizeJumpLine() throws Exception {
		String hql = "from SysConfig where name='" + config_size_jump_line_name + "'";
		SysConfig sysConfig = this.getEntityByHql(hql, null);
		if (sysConfig == null) {
			throw new Exception("尺码跳跃行未配置，请初始化系统!");
		}
		return Integer.parseInt(sysConfig.getVal());
	}

}
