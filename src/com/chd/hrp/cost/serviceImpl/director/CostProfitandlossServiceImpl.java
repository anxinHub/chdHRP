package com.chd.hrp.cost.serviceImpl.director;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.director.CostProfitandlossMapper;
import com.chd.hrp.cost.service.director.CostProfitandlossService;
import com.github.pagehelper.PageInfo;


@Service("costProfitandlossService")
public class CostProfitandlossServiceImpl implements CostProfitandlossService{

	private static Logger logger = Logger.getLogger(CostProfitandlossServiceImpl.class);
	
	@Resource(name = "costProfitandlossMapper")
	private final CostProfitandlossMapper costProfitandlossMapper = null;

	@Override
	public String queryCostShare(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costProfitandlossMapper.queryCostShare(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costProfitandlossMapper.queryCostShare(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	/**
	 * 分摊汇总表打印
	 */
	@Override
	public List<Map<String, Object>> queryCostSharePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=costProfitandlossMapper.queryCostSharePrint(entityMap);
		return list;
	}
	
	
	@Override
	public String querycostShareCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costProfitandlossMapper.querycostShareCost(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costProfitandlossMapper.querycostShareCost(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	

	

	@Override
	public String queryCostShareCostDir(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costProfitandlossMapper.queryCostShareCostDir(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costProfitandlossMapper.queryCostShareCostDir(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	@Override
	public String queryCostShareCostAss(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costProfitandlossMapper.queryCostShareCostAss(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costProfitandlossMapper.queryCostShareCostAss(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	@Override
	public String queryCostShareCostMed(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costProfitandlossMapper.queryCostShareCostMed(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costProfitandlossMapper.queryCostShareCostMed(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}
}
