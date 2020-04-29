package com.chd.hrp.htc.service.task.deptcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleBonusDetail;
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

public interface HtcPeopleBonusDetailService {

	public String addHtcPeopleBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcPeopleBonusItemClumHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryHtcPeopleBonusDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcPeopleBonusDetail queryHtcPeopleBonusDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcPeopleBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcPeopleBonusDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	 
	public String updateHtcPeopleBonusDetailItem(Map<String,Object> entityMap)throws DataAccessException;
}
