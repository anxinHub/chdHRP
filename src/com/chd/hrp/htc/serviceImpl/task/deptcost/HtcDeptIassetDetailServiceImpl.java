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
import com.chd.hrp.htc.dao.task.deptcost.HtcDeptIassetDetailMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptIassetDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcDeptIassetDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-25 author:alfred
 */

@Service("htcDeptIassetDetailService")
public class HtcDeptIassetDetailServiceImpl implements HtcDeptIassetDetailService {

	private static Logger logger = Logger.getLogger(HtcDeptIassetDetailServiceImpl.class);

	@Resource(name = "htcDeptIassetDetailMapper")
	private final HtcDeptIassetDetailMapper htcDeptIassetDetailMapper = null;

	@Override
	public String addHtcDeptIassetDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptIassetDetail htcDeptIassetDetail = htcDeptIassetDetailMapper.queryHtcDeptIassetDetailByCode(entityMap);
			
			if(null != htcDeptIassetDetail){
				return "{\"error\":\"当前数据已存在\",\"state\":\"false\"}";
			}
			
			htcDeptIassetDetailMapper.addHtcDeptIassetDetail(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcDeptIassetDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptIassetDetail> list = htcDeptIassetDetailMapper.queryHtcDeptIassetDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptIassetDetail> list = htcDeptIassetDetailMapper.queryHtcDeptIassetDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public HtcDeptIassetDetail queryHtcDeptIassetDetailByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptIassetDetailMapper.queryHtcDeptIassetDetailByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptIassetDetail(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			htcDeptIassetDetailMapper.deleteBatchHtcDeptIassetDetail(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcDeptIassetDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				htcDeptIassetDetailMapper.updateHtcDeptIassetDetail(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	@Override
	public String queryHtcDeptIassetCostDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptIassetDetail> list = htcDeptIassetDetailMapper.queryHtcDeptIassetCostDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptIassetDetail> list = htcDeptIassetDetailMapper.queryHtcDeptIassetCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public String checkHtcDeptIassetCostDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptIassetDetail> list = htcDeptIassetDetailMapper.checkHtcDeptIassetCostDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptIassetDetail> list = htcDeptIassetDetailMapper.checkHtcDeptIassetCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}
}
