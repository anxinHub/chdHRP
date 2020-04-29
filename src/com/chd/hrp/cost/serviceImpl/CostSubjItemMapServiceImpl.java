/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

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
import com.chd.hrp.cost.dao.CostSubjItemMapMapper;
import com.chd.hrp.cost.entity.CostSubjItemMap;
import com.chd.hrp.cost.service.CostSubjItemMapService;
import com.github.pagehelper.PageInfo;

@Service("costSubjItemMapService")
public class CostSubjItemMapServiceImpl implements CostSubjItemMapService {

	private static Logger logger = Logger.getLogger(CostSubjItemMapServiceImpl.class);

	@Resource(name = "costSubjItemMapMapper") 
	private final CostSubjItemMapMapper costSubjItemMapMapper = null;

	@Override
	public String addCostSubjItemMap(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			CostSubjItemMap costSubjItemMap = costSubjItemMapMapper.queryCostSubjItemMapByCode(entityMap);
			
			if(null != costSubjItemMap){
				
				return "{\"error\":\"此关系已经存在.\",\"state\":\"false\"}";
			}
			
			costSubjItemMapMapper.addCostSubjItemMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchCostSubjItemMap(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			
    	   costSubjItemMapMapper.addBatchCostSubjItemMap(list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String deleteCostSubjItemMap(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  costSubjItemMapMapper.deleteCostSubjItemMap(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBatchCostSubjItemMap(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 costSubjItemMapMapper.deleteBatchCostSubjItemMap(list);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override 
	public String updateCostSubjItemMap(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			 CostSubjItemMap costSubjItemMap = costSubjItemMapMapper.queryCostSubjItemMapByCode(entityMap);
				
				if(null != costSubjItemMap){
					
					return "{\"error\":\"此关系已经存在.\",\"state\":\"false\"}";
				}
				
			 costSubjItemMapMapper.updateCostSubjItemMap(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	@Override
	public String updateBatchCostSubjItemMap(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 costSubjItemMapMapper.updateBatchCostSubjItemMap(list);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	@Override
	public CostSubjItemMap queryCostSubjItemMapByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return costSubjItemMapMapper.queryCostSubjItemMapByCode(entityMap);
	}

	@Override
	public String queryCostSubjItemMap(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
	    sysPage = (SysPage) entityMap.get("sysPage");
	    if (sysPage.getTotal() == -1) {
	      List<CostSubjItemMap> list = costSubjItemMapMapper.queryCostSubjItemMap(entityMap);
	      return ChdJson.toJson(list);
	    } else {
	      RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	      List<CostSubjItemMap> list = costSubjItemMapMapper.queryCostSubjItemMap(entityMap, rowBounds);
	      PageInfo page = new PageInfo(list);
	      return ChdJson.toJson(list, page.getTotal());
	    }
	}

	

}
