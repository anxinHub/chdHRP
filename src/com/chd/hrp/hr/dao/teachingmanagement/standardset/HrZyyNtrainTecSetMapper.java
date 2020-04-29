/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.teachingmanagement.standardset;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainTecSet;
/**
 * 
 * @Description:
 * 住院医师规培轮转成绩标准设置(医技)
 * @Table:
 * HR_ZYY_NTRAIN_TEC_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com 
 * @Version: 1.0
 */
 

public interface HrZyyNtrainTecSetMapper extends SqlMapper{
	/**
	 * 删除
	 * @param entityList
	 */
	void deleteHrZyyNtrainTecSet(List<HrZyyNtrainTecSet> entityList)throws DataAccessException;
	/**
	 * 查询住院医满分标准(医技)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainTecSet queryTecSet(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryZyyNtrainTecSetByPrint(Map<String, Object> entityMap);
}
