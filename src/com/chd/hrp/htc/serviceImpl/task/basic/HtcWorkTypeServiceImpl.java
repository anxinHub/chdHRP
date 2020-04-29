
package com.chd.hrp.htc.serviceImpl.task.basic;

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
import com.chd.hrp.htc.dao.task.basic.HtcWorkDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcWorkTypeMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWorkDict;
import com.chd.hrp.htc.entity.task.basic.HtcWorkType;
import com.chd.hrp.htc.service.task.basic.HtcWorkTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcWorkTypeService")
public class HtcWorkTypeServiceImpl implements HtcWorkTypeService {

	private static Logger logger = Logger.getLogger(HtcWorkTypeServiceImpl.class);
	
	@Resource(name = "htcWorkTypeMapper")
	private final HtcWorkTypeMapper htcWorkTypeMapper = null;
    
	@Resource(name = "htcWorkDictMapper")
	private final HtcWorkDictMapper htcWorkDictMapper = null;
	/**
	 * 
	 */
	@Override
	public String addHtcWorkType(Map<String,Object> entityMap)throws DataAccessException{
	   	
		try{
			HtcWorkType htcWorkType = htcWorkTypeMapper.queryHtcWorkTypeByCode(entityMap);
		
			if(null != htcWorkType){
				return "{\"error\":\"职务编码重复.\",\"state\":\"false\"}";
			}
			
			htcWorkTypeMapper.addHtcWorkType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}	
	}
	
	/**
	 * 
	 */
	@Override
	public String queryHtcWorkType(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkType> list = htcWorkTypeMapper.queryHtcWorkType(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkType> list = htcWorkTypeMapper.queryHtcWorkType(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 
	 */
	@Override
	public HtcWorkType queryHtcWorkTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcWorkTypeMapper.queryHtcWorkTypeByCode(entityMap);
	}
	
	
	
	@Override
    public String deleteHtcWorkType(Map<String, Object> entityMap) throws DataAccessException {
			
		try{
			
			htcWorkTypeMapper.deleteHtcWorkType(entityMap);
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
	public String deleteBatchHtcWorkType(List<Map<String, Object>> entityList)throws DataAccessException{
		try{
			/*for(int i =0; i < entityList.size(); i++){
				List<HtcWorkDict> newList = htcWorkDictMapper.queryHtcWorkDict(entityList.get(i));
				if(newList.size() != 0){
					return "{\"error\":\"["+entityList.get(i).get("work_type_code")+"]已与作业产生对应关系\"}";
				}
			}*/
			
			htcWorkTypeMapper.deleteBatchHtcWorkType(entityList);
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
	public String updateHtcWorkType(Map<String,Object> entityMap)throws DataAccessException{
			
		try {
			
			htcWorkTypeMapper.updateHtcWorkType(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}


}
