/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.in.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产其他入账主表(房屋及建筑物)
 * @Table:
 * ASS_IN_REST_MAIN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInRestMainHouseService extends SqlService {
	public String initAssInCardHouse(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	Map<String, Object> printAssInRestMainHouseData(Map<String, Object> map) throws DataAccessException;

	public Integer queryBydept(Map<String, Object> entityMap)throws DataAccessException;

	public Integer queryByRdept(Map<String, Object> entityMap)throws DataAccessException;

	public List<String> queryAssInRestMainHouseStates(Map<String, Object> mapVo);

	public String queryDetails(Map<String, Object> page)throws DataAccessException;
}
