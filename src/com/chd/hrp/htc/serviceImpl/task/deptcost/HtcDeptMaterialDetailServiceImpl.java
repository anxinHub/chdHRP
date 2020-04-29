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
import com.chd.hrp.htc.dao.task.deptcost.HtcDeptMaterialDetailMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptMaterialDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcDeptMaterialDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcDeptMaterialDetailService")
public class HtcDeptMaterialDetailServiceImpl implements HtcDeptMaterialDetailService {

	private static Logger logger = Logger.getLogger(HtcDeptMaterialDetailServiceImpl.class);

	@Resource(name = "htcDeptMaterialDetailMapper")
	private final HtcDeptMaterialDetailMapper htcDeptMaterialDetailMapper = null;

	@Override
	public String addHtcDeptMaterialDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			HtcDeptMaterialDetail htcDeptMaterialDetail = htcDeptMaterialDetailMapper.queryHtcDeptMaterialDetailByCode(entityMap);
			
			if(null != htcDeptMaterialDetail){
				return "{\"error\":\"当前数据已存在.\",\"state\":\"false\"}";
			}
			htcDeptMaterialDetailMapper.addHtcDeptMaterialDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcDeptMaterialDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();	
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcDeptMaterialDetail> list = htcDeptMaterialDetailMapper.queryHtcDeptMaterialDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptMaterialDetail> list = htcDeptMaterialDetailMapper.queryHtcDeptMaterialDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public HtcDeptMaterialDetail queryHtcDeptMaterialDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcDeptMaterialDetailMapper.queryHtcDeptMaterialDetailByCode(entityMap);
		
	}

	@Override
	public String deleteHtcDeptMaterialDetail(Map<String, Object> entityMap) throws DataAccessException {

		try{
			htcDeptMaterialDetailMapper.deleteHtcDeptMaterialDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String deleteBatchHtcDeptMaterialDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try{
			htcDeptMaterialDetailMapper.deleteBatchHtcDeptMaterialDetail(entityList);
			
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
	public String updateHtcDeptMaterialDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

			htcDeptMaterialDetailMapper.updateHtcDeptMaterialDetail(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
    public String queryHtcDeptMaterialCostDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();	
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcDeptMaterialDetail> list = htcDeptMaterialDetailMapper.queryHtcDeptMaterialCostDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptMaterialDetail> list = htcDeptMaterialDetailMapper.queryHtcDeptMaterialCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
    }

	@Override
    public String checkHtcDeptMaterialCostDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcDeptMaterialDetail> list = htcDeptMaterialDetailMapper.checkHtcDeptMaterialCostDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptMaterialDetail> list = htcDeptMaterialDetailMapper.checkHtcDeptMaterialCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
    }
}
