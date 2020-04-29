/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.nursing;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.nursing.HrLevelSet;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_LEVEL_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrLevelSetService  {
    /**
     * 增加晋级目标设置
     * @param mapVo
     * @return
     */
	String addLevelSet(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除晋级目标设置
     * @param listVo
     * @return
     */
	String deleteLevelSet(List<HrLevelSet> listVo)throws DataAccessException;
	/**
	 * 查询晋级目标设置
	 * @param page
	 * @return
	 */
	String queryLevelSet(Map<String, Object> page)throws DataAccessException;
	/**
	 * 生成
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String generateLevelSet(Map<String, Object> page)throws DataAccessException;
	/**
	 * 继承
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String copyLevelSet(Map<String, Object> page)throws DataAccessException;

}
