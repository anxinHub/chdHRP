/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.storage.checkMobile;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatCheckMobile;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_CHECK_MOBILE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatCheckMobileMainMapper extends SqlMapper{
	
	/**
	 * 添加高值材料盘点数据
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	int addMatCheckMobileMain(@Param("list") List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 根据盘点日期和仓库查询盘点数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryMatCheckByCheckDate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据代销品盘点日期和仓库查询盘点数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryMatAFFICheckByCheckDate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 通过盘点日期和仓库id查询是否有盘点数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int countCheckByCheckDate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int existMatCheckMobileByCheckDate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询主页数据
	 * @param mapVo
	 * @return
	 */
	List<MatCheckMobile> queryMatCheckMobileMain(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 删除已经存在的数据
	 * @param entityMap
	 * @return
	 */
	int deleteByCreateDate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 盘点数据
	 * @param checkedList
	 */
	int check(@Param("list") List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 将任务单所有状态改为结束
	 * @return
	 */
	int updateStateToEnd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据盘点id查询各盘点材料的高值盘点数量
	 */
	List<Map<String, Object>> queryCheckMobilesByCheckId(Map<String, Object> entityMap) throws DataAccessException;
	
}
