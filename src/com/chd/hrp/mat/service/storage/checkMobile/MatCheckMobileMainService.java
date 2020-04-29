/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.checkMobile;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatCheckMobileMainService {
	
	/**
	 * 添加数据前检查是否已经生成过数据
	 * @param mapVo
	 * @return
	 */
	String existMatCheckMobileByCreateDate(Map<String, Object> mapVo);
	
	/**
	 * 添加高值材料盘点数据
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	String saveMatCheckMobileMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询移动盘点数据
	 * @param mapVo
	 * @throws DataAccessException
	 * @return
	 */
	String queryMatCheckMobileMain(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 批量删除移动盘点
	 * @param listVo
	 * @return
	 */
	String deleteBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 盘点数据
	 * @param checkedList
	 * @return
	 * @throws DataAccessException
	 */
	String check(List<Map<String, Object>> checkedList) throws DataAccessException;
	
	/**
	 * 根据盘点id查询各盘点材料的高值盘点数量
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryCheckMobilesByCheckId(Map<String, Object> entityMap) throws DataAccessException;

}
