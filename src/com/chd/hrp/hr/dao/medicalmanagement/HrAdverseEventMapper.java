/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAdverseEvent;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ADVERSE EVENT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrAdverseEventMapper extends SqlMapper{
     /**
      * 删除所有数据
      * @param entityMap
      */
	void deletehrAdverseEvent(Map<String, Object> entityMap);
     /**
      * 删除不良事件
      * @param entityList
      */
	void deleteAdverseEvent(List<HrAdverseEvent> entityList);
	/**
	 * 
	 * @param alllistVo
	 * @return
	 */
	int addBatchAdverseEvent(List<HrAdverseEvent> alllistVo);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAdverseEventByPrint(Map<String, Object> entityMap);
	
}
