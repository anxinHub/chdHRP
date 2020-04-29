/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.allot.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表（房屋及建筑物）
 * @Table:
 * ASS_ALLOT_OUT_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutHouseService extends SqlService {
	public String updateAllotOutConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;

	public String queryByAllotInImport(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryByAllotInImportView(Map<String, Object> entityMap)throws DataAccessException;

	Map<String, Object> printAssAllotOutHouseData(Map<String, Object> map) throws DataAccessException;

	public List<String> queryAssAllotInHouseStates(Map<String, Object> mapVo);

	public String queryDetails(Map<String, Object> page);
}
