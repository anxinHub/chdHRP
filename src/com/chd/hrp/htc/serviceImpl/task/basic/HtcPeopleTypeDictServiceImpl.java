package com.chd.hrp.htc.serviceImpl.task.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.dao.task.basic.HtcBonusCostRelaMapper;
import com.chd.hrp.htc.dao.task.basic.HtcPeopleDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcPeopleTypeDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcWageCostRelaMapper;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcPeopleTypeDictService;
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

@Service("htcPeopleTypeDictService")
public class HtcPeopleTypeDictServiceImpl implements HtcPeopleTypeDictService {

	private static Logger logger = Logger.getLogger(HtcPeopleTypeDictServiceImpl.class);

	@Resource(name = "htcPeopleTypeDictMapper")
	private final HtcPeopleTypeDictMapper htcPeopleTypeDictMapper = null;
	
	@Resource(name = "htcPeopleDictMapper")
	private final HtcPeopleDictMapper htcPeopleDictMapper = null;

	@Resource(name = "htcWageCostRelaMapper")
	private final HtcWageCostRelaMapper htcWageCostRelaMapper = null;
	
	@Resource(name = "htcBonusCostRelaMapper")
	private final HtcBonusCostRelaMapper htcBonusCostRelaMapper = null;
	
	@Override
	public String addHtcPeopleTypeDict(Map<String, Object> entityMap) throws DataAccessException {
             
		try {
			
			HtcPeopleTypeDict htcPeopleTypeDict = htcPeopleTypeDictMapper.queryHtcPeopleTypeDictByCode(entityMap);
			
			if(null != htcPeopleTypeDict){
				
				return "{\"error\":\"人员类别编码重复.\",\"state\":\"false\"}";
			}
			
			htcPeopleTypeDictMapper.addHtcPeopleTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		
		}
	}

	/**
	 * 
	 */
	@Override
	public String queryHtcPeopleTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcPeopleTypeDict> list = htcPeopleTypeDictMapper.queryHtcPeopleTypeDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleTypeDict> list = htcPeopleTypeDictMapper.queryHtcPeopleTypeDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	/**
	 * 
	 */
	@Override
	public HtcPeopleTypeDict queryHtcPeopleTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return htcPeopleTypeDictMapper.queryHtcPeopleTypeDictByCode(entityMap);
	}

	/**
	 * 
	 */
	@Override
	public String updateHtcPeopleTypeDict(Map<String, Object> entityMap) throws DataAccessException {

	try {
		    
		    htcPeopleTypeDictMapper.updateHtcPeopleTypeDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateHtcPeopleTypeDict\"}";
		}
	}


	@Override
	public String deleteHtcPeopleTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatchHtcPeopleTypeDict(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
		/*	for(int i = 0; i < list.size(); i++){
				Map<String, Object> mapBo = list.get(i);
				mapBo.put("people_type_code", mapBo.get("peop_type_code"));
				List<HtcPeopleDict> newList = htcPeopleDictMapper.queryHtcPeopleDict(mapBo);
				if(newList.size() != 0){
					return "{\"error\":\"["+mapBo.get("people_type_code")+"]编码已与人员字典表存在关系\"}";
				}
				
				List<HtcWageCostRela> wageList = htcWageCostRelaMapper.queryHtcWageCostRela(mapBo);
				if(wageList.size() != 0){
					return "{\"error\":\"["+mapBo.get("people_type_code")+"]编码已与工资项存在关系\"}";
				}
				
				List<HtcBonusCostRela> bonusList = htcBonusCostRelaMapper.queryHtcBonusCostRela(mapBo);
				if(bonusList.size() != 0){
					return "{\"error\":\"["+mapBo.get("people_type_code")+"]编码已与奖金项存在关系\"}";
				}
			}*/
			
			htcPeopleTypeDictMapper.deleteBatchHtcPeopleTypeDict(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String synchroHtcPeopleTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcPeopleTypeDictMapper.synchroHtcPeopleTypeDict(entityMap);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"同步失败\"}");
		}
		
	}
	
	
	public String impHtcPeopleTypeDict(Map<String, Object> entityMap){
		
		try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			for (Map<String, List<String>> map : list) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("peop_type_code", map.get("peop_type_code").get(1));
				
				mapVo.put("peop_type_name", map.get("peop_type_name").get(1));
				
				mapVo.put("peop_type_desc", map.get("peop_type_desc").get(1));
				
				mapVo.put("is_stop", 0);
				
				mapVo.put("spell_code",StringTool.toPinyinShouZiMu(map.get("peop_type_name").get(1).toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(map.get("peop_type_name").get(1).toString()));
				
				HtcPeopleTypeDict htcPeopleTypeDict = htcPeopleTypeDictMapper.queryHtcPeopleTypeDictByCode(mapVo);
				
				if(null != htcPeopleTypeDict){
					
					return "{\"error\":\""+ map.get("peop_type_code").get(0)+"人员类别编码:"+map.get("peop_type_code").get(1)+"重复.\",\"state\":\"false\"}";
				}
				
				   resultSet.add(mapVo);
			   }
			
			   int  totalCount =list.size();  
			   
		       int  page_size = 1500;  
		       
		       int  requestCount = (totalCount-1)/page_size+1;
		       
			   if(resultSet.size()>0){
				   
			       for (int i = 0; i < requestCount; i++) {
			    	   
			    	   Integer fromIndex = i * page_size;
			    	   
			    	   int toIndex = Math.min(totalCount, (i + 1) * page_size);
			   
			    	   htcPeopleTypeDictMapper.addbatchHtcPeopleTypeDict(resultSet.subList(fromIndex, toIndex)); 
			    	   
				     }
				   
			   }
			   
			
			  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
		
		
		
	}
}
