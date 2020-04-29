package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostEarningsMapper;
import com.chd.hrp.cost.dao.analysis.c01.C01Mapper;
import com.chd.hrp.cost.service.CostEarningsService;
import com.chd.hrp.cost.service.analysis.c01.C01Service;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 结余分析服务类<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costEarningsService")
public class CostEarningsServiceImpl implements CostEarningsService {

	private static Logger logger = Logger.getLogger(CostEarningsServiceImpl.class);

	@Resource(name = "costEarningsMapper")
	private final CostEarningsMapper costEarningsMapper = null;

	@Override
	public String queryAnalysisC0101(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0101(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0101(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	
	@Override
	public String queryAnalysisC0102(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0102(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0102(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0103(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0103(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0103(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0104(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0104(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0104(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	/*
	 * 医疗收入各级成本收益表
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0101print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
       List<Map<String, Object>> list =  (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0101(entityMap);
		
		return list;
	}

	/*
	 * 科室医疗收益明细表
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0104print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
    List<Map<String, Object>> list =  (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0104(entityMap);
		
		return list;
	}


	/*
	 * 临床服务类科室医疗收益明细表
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0103print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 List<Map<String, Object>> list =  (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0103(entityMap);
			
	   return list;
	}



	/*
	 * 医院收入各级成本收益表
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0102print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 List<Map<String, Object>> list =  (List<Map<String, Object>>) costEarningsMapper.queryAnalysisC0102(entityMap);
			
		   return list;
	}

}
