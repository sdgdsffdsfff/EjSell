package com.ejsell.base.action;

import org.springframework.web.servlet.ModelAndView;

/**
 * ����Action�ӿ�
 * 
 * @author �½�
 *
 * @param <T>
 */
public interface BaseAction<T> {

	/**
	 * ��ӷ���
	 * 
	 * @param t ʵ����
	 * @return SpringMVCģ����ͼ
	 */
	public String add(T t);

	/**
	 * ɾ������
	 * 
	 * @param id ʵ��id
	 * @return SpringMVCģ����ͼ
	 */
	public String del(Long id);

	/**
	 * �޸ķ���
	 * 
	 * @param t ʵ����
	 * @return SpringMVCģ����ͼ
	 */
	public String edit(T t);

	/**
	 * ���ҳ��
	 * 
	 * @return SpringMVCģ����ͼ
	 */
	public ModelAndView addPage();

	/**
	 * �޸�ҳ��
	 * 
	 * @param id ʵ����id
	 * @return SpringMVCģ����ͼ
	 */
	public ModelAndView editPage(Long id);

	/**
	 * Json�б�ҳ��
	 * 
	 * @return json�б�ҳ
	 */
	public ModelAndView jsonListPage();

	/**
	 * ��ȡjson��Ϣ
	 * 
	 * @param sqlwhere sql��ѯ����
	 * @param page ��ҳҳ��(Ĭ��1)
	 * @param rows ��ҳ��С(Ĭ��10)
	 * @return json��������{rows:������Ϣ,total:�ܼ�¼��}
	 */
	public String getJsonList(String sqlwhere, Integer page, Integer rows);

}
