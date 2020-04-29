package com.chd.hrp.hr.serviceImpl.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.report.HosPostDistributionMapper;
import com.chd.hrp.hr.service.report.HosPostDistributionService;
import com.github.pagehelper.PageInfo;

@Service("hosPostDistributionService")
public class HosPostDistributionServiceImpl implements HosPostDistributionService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosPostDistributionServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hosPostDistributionMapper")
	private final HosPostDistributionMapper hosPostDistributionMapper = null;


	
	

	/**
	 * @Description 查询结果集<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryHrPostDistribution(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) hosPostDistributionMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) hosPostDistributionMapper.query(entityMap, rowBounds);

		
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}





	@Override
	public List<Map<String, Object>> queryPostDistributionByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hosPostDistributionMapper.queryPostDistributionByPrint(entityMap));
		 return list;
	}





}
