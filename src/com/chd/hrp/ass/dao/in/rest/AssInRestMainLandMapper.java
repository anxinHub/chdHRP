/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.in.rest;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产其他入账主表(土地)
 * @Table:
 * ASS_IN_REST_MAIN_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInRestMainLandMapper extends SqlMapper{
	void updateAudit(List<Map<String, Object>> entityMap);

	void updateInMoney(Map<String, Object> entityMap);

	void updateReAudit(List<Map<String, Object>> entityMap);

	void updateConfirm(List<Map<String, Object>> entityMap);

	Integer queryBydept(Map<String, Object> entityMap);

	Integer queryByRdept(Map<String, Object> entityMap);
	
	List<Map<String, Object>> queryAssInRestMainByAssInNo(Map<String, Object> map);

	List<Map<String, Object>> queryAssInRestMainDetailByAssInNo(Map<String, Object> map);

	List<String> queryAssInRestMainLandStates(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	List<Map<String, Object>> queryDetails(Map<String, Object> entityMap,
			RowBounds rowBounds);
}
