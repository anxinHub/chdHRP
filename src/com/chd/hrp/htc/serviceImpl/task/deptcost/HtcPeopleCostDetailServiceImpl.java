package com.chd.hrp.htc.serviceImpl.task.deptcost;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.deptcost.HtcPeopleCostDetailMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleCostDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcPeopleCostDetailService;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcPeopleCostDetailService")
public class HtcPeopleCostDetailServiceImpl implements HtcPeopleCostDetailService {

	private static Logger logger = Logger.getLogger(HtcPeopleCostDetailServiceImpl.class);

	@Resource(name = "htcPeopleCostDetailMapper")
	private final HtcPeopleCostDetailMapper htcPeopleCostDetailMapper = null;

	@Override
	public String queryHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcPeopleCostDetail> list = htcPeopleCostDetailMapper.queryHtcPeopleCostDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleCostDetail> list = htcPeopleCostDetailMapper.queryHtcPeopleCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public HtcPeopleCostDetail queryHtcPeopleCostDetailByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcPeopleCostDetailMapper.queryHtcPeopleCostDetailByCode(entityMap);
	}

	@Override
	public String deleteHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			htcPeopleCostDetailMapper.deleteHtcPeopleCostDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	/**
	 * 
	 */
	@Override
	public String deleteBatchHtcPeopleCostDetail(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try{
			htcPeopleCostDetailMapper.deleteBatchHtcPeopleCostDetail(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	/**
	 * 
	 */
	@Override
	public String updateHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcPeopleCostDetailMapper.updateHtcPeopleCostDetail(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	// 采集数据
	@Override
	public String collectHtcPeopleCostDetail(Map<String, Object> entityMap) throws DataAccessException {
		htcPeopleCostDetailMapper.collectHtcPeopleCostDetail(entityMap);
		return "{\"msg\":\"" + entityMap.get("msg") + "\",\"state\":\"true\"}";
	}

	// 核对数据
	@Override
	public String queryHtcPeopleCostDetailSumMonth(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcPeopleCostDetail> list = htcPeopleCostDetailMapper.queryHtcPeopleCostDetailSumMonth(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleCostDetail> list = htcPeopleCostDetailMapper.queryHtcPeopleCostDetailSumMonth(entityMap,rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	// 核对数据
	@Override
	public String queryHtcPeopleCostDetailSummary(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcPeopleCostDetail> list = htcPeopleCostDetailMapper.queryHtcPeopleCostDetailSummary(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleCostDetail> list = htcPeopleCostDetailMapper.queryHtcPeopleCostDetailSummary(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
}
