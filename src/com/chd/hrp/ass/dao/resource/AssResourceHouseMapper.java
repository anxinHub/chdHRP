/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.resource;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.resource.AssResourceHouse;
/**
 * 
 * @Description:
 * 资产资金来源匹配表_房屋及建筑物
 * @Table:
 * ASS_RESOURCE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssResourceHouseMapper extends SqlMapper{

	List<Map<String, Object>> queryByAssCardNoMap(Map<String, Object> card);

	List<Map<String, Object>> queryByAssCardIn(Map<String, Object> cardMap);

	List<AssResourceHouse> queryByAssCardNo(Map<String, Object> detailVo);

	int updateBatchByChange(List<Map<String, Object>> reSourceUpdateList);

	int addBatchByChange(List<Map<String, Object>> reSourceAddList);
	
	public int updateBatchByPay(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateBatchByBack(List<Map<String, Object>> entityMap) throws DataAccessException;

	int updateBatchByDepre(List<Map<String, Object>> reSourceUpdateList);
	
}
