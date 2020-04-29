
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
import com.chd.hrp.htc.dao.task.basic.HtcPeopleDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcWageItemDictMapper;
import com.chd.hrp.htc.dao.task.deptcost.HtcPeopleWageDetailMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleWageDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcPeopleWageDetailService;
import com.github.pagehelper.PageInfo;


@Service("htcPeopleWageDetailService")
public class HtcPeopleWageDetailServiceImpl implements HtcPeopleWageDetailService {

	private static Logger logger = Logger.getLogger(HtcPeopleWageDetailServiceImpl.class);

	@Resource(name = "htcPeopleWageDetailMapper")
	private final HtcPeopleWageDetailMapper htcPeopleWageDetailMapper = null;

	/**
	 * 
	 */
	@Override
	public String addHtcPeopleWageDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			HtcPeopleWageDetail htcPeopleWageDetail = htcPeopleWageDetailMapper.queryHtcPeopleWageDetailByCode(entityMap);
			if(htcPeopleWageDetail != null){
				return "{\"error\":\"此科室人员类别人员已存在,请重新添加!.\",\"state\":\"false\"}";
			}
		
			htcPeopleWageDetailMapper.addHtcPeopleWageDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}


	@Override
	public String queryHtcPeopleWageItemClumHead(Map<String, Object> entityMap) throws DataAccessException {
		
		 List<Map<String, Object>>  list = htcPeopleWageDetailMapper.queryHtcPeopleWageItemClumHead(entityMap);
		
		 return ChdJson.toJson(list);
	}
	/**
	 * 
	 */
	@Override
	public String queryHtcPeopleWageDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = htcPeopleWageDetailMapper.queryHtcPeopleWageDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = htcPeopleWageDetailMapper.queryHtcPeopleWageDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 
	 */
	@Override
	public HtcPeopleWageDetail queryHtcPeopleWageDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		return htcPeopleWageDetailMapper.queryHtcPeopleWageDetailByCode(entityMap);
	}

	/**
	 * 
	 */
	@Override
	public String deleteHtcPeopleWageDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			htcPeopleWageDetailMapper.deleteHtcPeopleWageDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String deleteBatchHtcPeopleWageDetail(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			htcPeopleWageDetailMapper.deleteBatchHtcPeopleWageDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcPeopleWageDetailItem(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			StringBuffer sb = new StringBuffer();
			String [] res = entityMap.get("item").toString().split(";");
			HtcPeopleWageDetail htcPeopleWageDetail = htcPeopleWageDetailMapper.queryHtcPeopleWageDetailByCode(entityMap);
			if(null != htcPeopleWageDetail){
				
                String column_item = entityMap.get("item_column").toString();
				
				String [] column = column_item.substring(1, column_item.length()).split(",");
                for (int i = 0; i < res.length; i++) {
					
                	sb.append(column[i]+"="+res[i]+",");
					
				}
            	entityMap.put("sqlValue", sb.substring(0, sb.length()-1));
				
			}
			 htcPeopleWageDetailMapper.updateHtcPeopleWageDetail(entityMap);
			return "{\"msgmsg\":\"修改成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
}
