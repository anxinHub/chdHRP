/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrDisputeStatisticsMapper;
import com.chd.hrp.hr.service.medicalmanagement.HrDisputeStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM 赔款处理统计
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrDisputeStatisticsService")
public class HrDisputeStatisticsServiceImpl implements HrDisputeStatisticsService {

	private static Logger logger = Logger.getLogger(HrDisputeStatisticsServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrDisputeStatisticsMapper")
	private final HrDisputeStatisticsMapper hrDisputeStatisticsMapper = null;
	
	
	

	/**
	 * 查询赔款处理统计
	 */
	@Override
	public String queryDisputeStatistics(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrDisputeStatisticsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrDisputeStatisticsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}




	@Override
	public List<Map<String, Object>> queryDisputeStatisticsByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrDisputeStatisticsMapper.queryDisputeStatisticsByPrint(entityMap));
		 return list;
	}

	
	
}
