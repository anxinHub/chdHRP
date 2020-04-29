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
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostBusiSourecDictMapper;
import com.chd.hrp.cost.entity.CostBusiSourecDict;
import com.chd.hrp.cost.service.CostBusiSourecDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 门诊工作量表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costBusiSourecDictService")
public class CostBusiSourecDictServiceImpl implements CostBusiSourecDictService {

	private static Logger logger = Logger.getLogger(CostBusiSourecDictServiceImpl.class);

	@Resource(name = "costBusiSourecDictMapper")
	private final CostBusiSourecDictMapper costBusiSourecDictMapper = null;

	@Override
	public String queryCostBusiSourecDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
         SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<CostBusiSourecDict> list = costBusiSourecDictMapper.queryCostBusiSourecDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostBusiSourecDict> list = costBusiSourecDictMapper.queryCostBusiSourecDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public String addCostBusiSourecDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	     try {
			
	    	 CostBusiSourecDict costBusiSourecDict = costBusiSourecDictMapper.queryCostBusiSourecDictByCode(entityMap);
	    	 
	    	 if(null != costBusiSourecDict){
	    		 
	    		 return "{\"error\":\"数据已经存在.\",\"state\":\"false\"}";
	    	 }
	    	 
	    	 costBusiSourecDictMapper.addCostBusiSourecDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcPeopleTypeDict\"}";
		}
	}

	@Override
	public String deleteBatchCostBusiSourecDict(List<Map<String, Object>> list)
			throws DataAccessException {
		try {
			
			costBusiSourecDictMapper.deleteBatchCostBusiSourecDict(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcPeopleTypeDict\"}";
		}
	}

	@Override
	public CostBusiSourecDict queryCostBusiSourecDictByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return costBusiSourecDictMapper.queryCostBusiSourecDictByCode(entityMap);
	}

	@Override
	public String updateCostBusiSourecDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
          try {
			
			costBusiSourecDictMapper.updateCostBusiSourecDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcPeopleTypeDict\"}";
		}
	}

	
}
