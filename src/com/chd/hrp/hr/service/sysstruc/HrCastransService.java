/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrCastrans;

/**
 * 
 * @Description:
 * 级联事务
 * @Colle:
 * HR_CASTRANS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrCastransService {
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHrCastrans(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrCastrans(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrCastrans(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrCastrans(List<HrCastrans> entityList) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrCastrans(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrCastrans queryHrCastransByCode(Map<String, Object> entityMap) throws DataAccessException;

}
