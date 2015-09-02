package com.ejsell.base.action;

import org.springframework.web.servlet.ModelAndView;

/**
 * 基础Action接口
 * 
 * @author 陈捷
 *
 * @param <T>
 */
public interface BaseAction<T> {

	/**
	 * 添加方法
	 * 
	 * @param t 实体类
	 * @return SpringMVC模型视图
	 */
	public String add(T t);

	/**
	 * 删除方法
	 * 
	 * @param id 实体id
	 * @return SpringMVC模型视图
	 */
	public String del(Long id);

	/**
	 * 修改方法
	 * 
	 * @param t 实体类
	 * @return SpringMVC模型视图
	 */
	public String edit(T t);

	/**
	 * 添加页面
	 * 
	 * @return SpringMVC模型视图
	 */
	public ModelAndView addPage();

	/**
	 * 修改页面
	 * 
	 * @param id 实体类id
	 * @return SpringMVC模型视图
	 */
	public ModelAndView editPage(Long id);

	/**
	 * Json列表页面
	 * 
	 * @return json列表页
	 */
	public ModelAndView jsonListPage();

	/**
	 * 获取json信息
	 * 
	 * @param sqlwhere sql查询条件
	 * @param page 分页页号(默认1)
	 * @param rows 分页大小(默认10)
	 * @return json类型数据{rows:具体信息,total:总记录数}
	 */
	public String getJsonList(String sqlwhere, Integer page, Integer rows);

}
