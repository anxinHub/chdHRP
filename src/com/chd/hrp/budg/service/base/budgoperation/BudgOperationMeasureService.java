/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgoperation;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 运营尺度
 * @Table:
 * BUDG_OPERATION_MEASURE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgOperationMeasureService extends SqlService {

	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgOperationMeasure(List<Map<String, Object>> listVo) throws DataAccessException;

	

}
