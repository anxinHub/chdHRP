/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.in;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产入库主表(专用设备)
 * @Table:
 * ASS_IN_MAIN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInMainHouseMapper extends SqlMapper{

	void updateAudit(List<Map<String, Object>> entityMap);

	void updateInMoney(Map<String, Object> entityMap);

	void updateReAudit(List<Map<String, Object>> entityMap);

	void updateConfirm(List<Map<String, Object>> entityMap);

	List<Map<String, Object>> queryAssCardHouseByAssInNo(Map<String, Object> map);

	List<Map<String, Object>> queryAssCardHouseDetailByAssInNo(Map<String, Object> map);

	int queryBydept(Map<String, Object> entityMap);

	int queryByRdept(Map<String, Object> entityMap);

	List<String> queryAssInHouseStates(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	List<Map<String, Object>> queryDetails(Map<String, Object> entityMap,
			RowBounds rowBounds);
	
}
