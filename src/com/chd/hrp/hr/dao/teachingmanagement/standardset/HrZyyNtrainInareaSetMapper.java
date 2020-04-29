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
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ZYY_NTRAIN_INAREA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrZyyNtrainInareaSetMapper extends SqlMapper{
	 /**
     * 删除住院医规培轮转成绩表（病区）
     * @param entityList
     */
	void deleteHrZyyNtrainInareaSet(List<HrZyyNtrainInareaSet> entityList);
	/**
	 * 查询住院医病区满分标准
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainInareaSet queryInareaSet(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryZyyNtrainInareaByPrint(Map<String, Object> entityMap);
}