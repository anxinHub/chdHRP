/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bill;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bill.AssBillStage;
/**
 * 
 * @Description:
 * 051601 发票卡片分期付款
 * @Table:
 * ASS_BILL_STAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBillStageMapper extends SqlMapper{
	List<Map<String, Object>> queryByBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<AssBillStage> queryBillStageByBillNo(Map<String,Object> entityMap)throws DataAccessException;	
	
	List<AssBillStage> queryBillStageByBillNo(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;	
	
	List<AssBillStage> queryByConfirmAssBill(Map<String,Object> entityMap)throws DataAccessException;	
	
	Integer queryBillStageMaxNo(Map<String,Object> entityMap)throws DataAccessException;
}
