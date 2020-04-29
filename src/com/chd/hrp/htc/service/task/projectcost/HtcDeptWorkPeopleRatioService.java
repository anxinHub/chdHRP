package com.chd.hrp.htc.service.task.projectcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptWorkPeopleRatio;
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

public interface HtcDeptWorkPeopleRatioService {

	public String addHtcDeptWorkPeopleRatio(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptWorkPeopleRatio(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptWorkPeopleRatio queryHtcDeptWorkPeopleRatioByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptWorkPeopleRatio(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcDeptWorkPeopleRatio(Map<String,Object> entityMap)throws DataAccessException;
}
