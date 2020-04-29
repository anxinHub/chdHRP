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
import com.chd.hrp.htc.dao.task.deptcost.HtcDeptFassetDetailMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptFassetDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcDeptFassetDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcDeptFassetDetailService")
public class HtcDeptFassetDetailServiceImpl implements HtcDeptFassetDetailService {

	private static Logger logger = Logger.getLogger(HtcDeptFassetDetailServiceImpl.class);

	@Resource(name = "htcDeptFassetDetailMapper")
	private final HtcDeptFassetDetailMapper htcDeptFassetDetailMapper = null;

	@Override
	public String addHtcDeptFassetDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptFassetDetail htcDeptFassetDetail = htcDeptFassetDetailMapper.queryHtcDeptFassetDetailByCode(entityMap);
			
			if(null != htcDeptFassetDetail){
				return "{\"error\":\"数据重复!.\",\"state\":\"false\"}";
			}
			htcDeptFassetDetailMapper.addHtcDeptFassetDetail(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcDeptFassetDetail(List<Map<String,Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
			htcDeptFassetDetailMapper.deleteBatchHtcDeptFassetDetail(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcDeptFassetDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcDeptFassetDetailMapper.updateHtcDeptFassetDetail(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String queryHtcDeptFassetDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptFassetDetail> list = htcDeptFassetDetailMapper.queryHtcDeptFassetDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptFassetDetail> list = htcDeptFassetDetailMapper.queryHtcDeptFassetDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public HtcDeptFassetDetail queryHtcDeptFassetDetailByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptFassetDetailMapper.queryHtcDeptFassetDetailByCode(entityMap);
	}

	@Override
	public String queryHtcDeptFassetCostDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptFassetDetail> list = htcDeptFassetDetailMapper.queryHtcDeptFassetCostDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptFassetDetail> list = htcDeptFassetDetailMapper.queryHtcDeptFassetCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public String checkHtcDeptFassetCostDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptFassetDetail> list = htcDeptFassetDetailMapper.checkHtcDeptFassetCostDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptFassetDetail> list = htcDeptFassetDetailMapper.checkHtcDeptFassetCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
