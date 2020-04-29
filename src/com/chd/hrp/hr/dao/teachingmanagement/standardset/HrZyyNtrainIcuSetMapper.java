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
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
/**
 * 
 * @Description:
 * 
 * @Table:住院医师规培轮转成绩标准设置（急诊ICU）
 * HR_ZYY_NTRAIN_ICU_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrZyyNtrainIcuSetMapper extends SqlMapper{
	/**
	 * 删除
	 * @param entityList
	 */
	void deleteHrZyyNtrainIcuSet(List<HrZyyNtrainIcuSet> entityList);
	/**
	 * 查询ICU满分标准
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainIcuSet queryIcuSet (Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryZyyNtrainIcuSetByPrint(Map<String, Object> entityMap);
}
