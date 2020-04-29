/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.allot.out;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutLand;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表(土地)
 * @Table:
 * ASS_ALLOT_OUT_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutLandMapper extends SqlMapper{
public int updateAllotOutMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	public Map<String,Object> collectAssAllotOutLand(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAllotOutLand> queryByAllotInImport(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<AssAllotOutLand> queryByAllotInImport(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAllotOutLand> queryByAllotInImportView(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<AssAllotOutLand> queryByAllotInImportView(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAssAllotOutLandByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryAssAllotOutLandDetailByAssInNo(Map<String, Object> map);

	public List<String> queryAssAllotOutLandStates(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
