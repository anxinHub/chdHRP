/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.change;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.change.AssChangeDetailInassets;
/**
 * 
 * @Description:
 * 050805 资产原值变动明细(无形资产)
 * @Table:
 * ASS_CHARGE_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChangeDetailInassetsMapper extends SqlMapper{
	List<AssChangeDetailInassets> queryByChangeNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String,Object>> queryByChangeNoMap(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String,Object>> queryByAssCardNo(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	List<Map<String,Object>> queryByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
	
	int updatePriceBySource(Map<String,Object> entityMap)throws DataAccessException;
	
	int updateBatchPriceBySource(List<Map<String,Object>> entityMap)throws DataAccessException;
}
