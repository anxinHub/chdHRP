/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.teachingmanagement.standardset;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainTecSet;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ZYY_NTRAIN_TEC_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrZyyNtrainTecSetService  {
	String deleteHrZyyNtrainTecSet(List<HrZyyNtrainTecSet> entityList) throws DataAccessException;
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	String mathHrZyyNtrainTecSet(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 住院医满分标准查询(医技)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainTecSet queryTecSet(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importZyyNtrainTecSet(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryZyyNtrainTecSetByPrint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String addOrUpdate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String query(Map<String, Object> page)throws DataAccessException;
}
