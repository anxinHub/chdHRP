package com.chd.hrp.htc.service.task.projectcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeWorkDetail;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcDeptChargeWorkDetailService {

	public String saveHtcDeptChargeWorkDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptChargeWorkDetailWork(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryHtcDeptChargeWorkDetailTitle(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryHtcDeptChargeWorkDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcDeptChargeWorkDetail(List<Map<String,Object>> entityList)throws DataAccessException;
	
}
