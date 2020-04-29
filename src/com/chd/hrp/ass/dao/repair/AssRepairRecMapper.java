/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.repair;

import java.util.List;
import java.util.Map;


import java.util.Map;




import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.repair.AssRepairRec;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_REC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRepairRecMapper extends SqlMapper{

	/**
	 * 资产维修主表打印
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryaAssRepairRecBatch(Map<String, Object> map);
	/**
	 * 资产维修从表打印
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryAssRepairRec_detail(Map<String, Object> map);
	Map<String, Object> querAssRepairRecByPrint(Map<String, Object> map);


	int auditAssRepairRec(Map<String, Object> map);

	int backAssRepairRec(Map<String, Object> map);

	void countAssRepairRec(Map<String, Object> map);
	List<AssRepairRec> querytrouble(Map<String, Object> entityMap);
	List<AssRepairRec> querytrouble(Map<String, Object> entityMap,
			RowBounds rowBounds);

	
}
