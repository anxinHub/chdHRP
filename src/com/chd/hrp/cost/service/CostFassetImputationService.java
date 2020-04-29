/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostMaterialDetail;

/**
* @Title. @Description.
* 固定资产归集<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetImputationService {


	/**
	 * @Description 
	 * 固定资产归集<BR> 查询queryCostFassetImputation分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostFassetImputation(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
