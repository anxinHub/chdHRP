/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.repair;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface AssRepairRecService extends SqlService {

	String queryAssRepairRecDetail(Map<String, Object> mapVo);

	String deleteAssRepairRecDetail(List<Map<String, Object>> listVo);
	/**
	 * 资产维修打印
	 */
	Map<String, Object> queryAssRepairRecDY(Map<String, Object> map)
			throws DataAccessException;

	String auditAssRepairRec(List<Map<String, Object>> listVo);

	String backAssRepairRec(List<Map<String, Object>> listVo);

	String countAssRepairRec(List<Map<String, Object>> listVo);

}
