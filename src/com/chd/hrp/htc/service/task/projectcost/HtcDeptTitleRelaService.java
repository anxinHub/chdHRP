package com.chd.hrp.htc.service.task.projectcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptTitleRela;
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

public interface HtcDeptTitleRelaService {

	public String addHtcDeptTitleRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptTitleRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptTitleRela queryHtcDeptTitleRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptTitleRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptTitleRela(Map<String,Object> entityMap)throws DataAccessException;
}
