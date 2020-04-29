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
import com.chd.hrp.htc.dao.task.deptcost.HtcPeopleBonusDetailMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleBonusDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcPeopleBonusDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcPeopleBonusDetailService")
public class HtcPeopleBonusDetailServiceImpl implements HtcPeopleBonusDetailService {

	private static Logger logger = Logger.getLogger(HtcPeopleBonusDetailServiceImpl.class);

	@Resource(name = "htcPeopleBonusDetailMapper")
	private final HtcPeopleBonusDetailMapper htcPeopleBonusDetailMapper = null;

	@Override
	public String addHtcPeopleBonusDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			HtcPeopleBonusDetail htcPeopleBonusDetail = htcPeopleBonusDetailMapper.queryHtcPeopleBonusDetailByCode(entityMap);
			
			if(null != htcPeopleBonusDetail){
				
				return "{\"error\":\"此科室人员类别人员已存在,请重新添加!.\",\"state\":\"false\"}";
			}
			
			htcPeopleBonusDetailMapper.addHtcPeopleBonusDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	@Override
	public String queryHtcPeopleBonusItemClumHead(Map<String, Object> entityMap)
			throws DataAccessException {
		 List<Map<String, Object>>  list = htcPeopleBonusDetailMapper.queryHtcPeopleBonusItemClumHead(entityMap);
		 return ChdJson.toJson(list);
	}
	@Override
	public String queryHtcPeopleBonusDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = htcPeopleBonusDetailMapper.queryHtcPeopleBonusDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = htcPeopleBonusDetailMapper.queryHtcPeopleBonusDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcPeopleBonusDetail queryHtcPeopleBonusDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		return htcPeopleBonusDetailMapper.queryHtcPeopleBonusDetailByCode(entityMap);
	}

	@Override
	public String deleteHtcPeopleBonusDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			htcPeopleBonusDetailMapper.deleteHtcPeopleBonusDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcPeopleBonusDetail(List<Map<String, Object>> entityList) throws DataAccessException{
		
		try{
			htcPeopleBonusDetailMapper.deleteBatchHtcPeopleBonusDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcPeopleBonusDetailItem(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			StringBuffer sb = new StringBuffer();
			String [] res = entityMap.get("item").toString().split(";");
			HtcPeopleBonusDetail htcPeopleBonusDetail = htcPeopleBonusDetailMapper.queryHtcPeopleBonusDetailByCode(entityMap);
               if(null != htcPeopleBonusDetail){
				
                String column_item = entityMap.get("item_column").toString();
				
				String [] column = column_item.substring(1, column_item.length()).split(",");
                for (int i = 0; i < res.length; i++) {
					
                	sb.append(column[i]+"="+res[i]+",");
					
				}
            	entityMap.put("sqlValue", sb.substring(0, sb.length()-1));
            	
            	htcPeopleBonusDetailMapper.updateHtcPeopleBonusDetail(entityMap);
			}
			
			return "{\"msgmsg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
}
