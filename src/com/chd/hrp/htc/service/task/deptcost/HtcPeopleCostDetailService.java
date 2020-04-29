package com.chd.hrp.htc.service.task.deptcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleCostDetail;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcPeopleCostDetailService {

	public String queryHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public HtcPeopleCostDetail queryHtcPeopleCostDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	public String deleteHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException;

	public String deleteBatchHtcPeopleCostDetail(List<Map<String, Object>> entityList) throws DataAccessException;

	public String updateHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException;

	public String collectHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcPeopleCostDetailSumMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcPeopleCostDetailSummary(Map<String, Object> entityMap) throws DataAccessException;
	
}
