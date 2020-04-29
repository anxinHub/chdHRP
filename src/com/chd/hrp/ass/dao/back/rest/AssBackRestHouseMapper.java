/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back.rest;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产其他退账单主表(房屋及建筑物)
 * @Table:
 * ASS_BACK_REST_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackRestHouseMapper extends SqlMapper{

	void updateBatchConfirm(List<Map<String, Object>> entityMap);

	void updateBackMoney(Map<String, Object> entityMap);

	void collectAssBackHouse(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssBackRestHouseByAssBackNo(Map<String, Object> map);

	List<Map<String, Object>> queryAssBackRestHouseDetailByAssBackNo(Map<String, Object> map);

	List<String> queryAssBackRestHouseStates(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	List<Map<String, Object>> queryDetails(Map<String, Object> entityMap,
			RowBounds rowBounds);
	
}
