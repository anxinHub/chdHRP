package com.chd.hrp.htc.serviceImpl.info.basic;

import java.util.Date;
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.info.basic.HtcProDeptDictMapper;
import com.chd.hrp.htc.entity.info.basic.HtcProDeptDict;
import com.chd.hrp.htc.service.info.basic.HtcProDeptDictService;
import com.github.pagehelper.PageInfo;


@Service("htcProDeptDictService")
public class HtcProDeptDictServiceImpl implements HtcProDeptDictService{
	
	private static Logger logger = Logger.getLogger(HtcProDeptDictServiceImpl.class);

	@Resource(name = "htcProDeptDictMapper")
	private final HtcProDeptDictMapper htcProDeptDictMapper = null;

	@Override
	public String addHtcProDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			/*
			 * 判断核算科室编码是否存在
			 * */
			Map<String, Object> existsProDeptMap= new HashMap<String, Object>();
			
			existsProDeptMap.put("group_id", entityMap.get("group_id"));
			existsProDeptMap.put("hos_id", entityMap.get("hos_id"));
			existsProDeptMap.put("copy_code", entityMap.get("copy_code"));
			existsProDeptMap.put("proj_dept_code", entityMap.get("proj_dept_code"));
			
			HtcProDeptDict htcProDept =  htcProDeptDictMapper.queryHtcProDeptByCode(existsProDeptMap);
			
			if(null != htcProDept){
				
				return "{\"error\":\"科室编码重复.\",\"state\":\"false\"}";
			}
			
			Map<String, Object> proDeptMap= new HashMap<String, Object>();
			
			//科室ID
			Long proj_dept_id = htcProDeptDictMapper.queryProjDeptSeq();
			
			proDeptMap.put("group_id", entityMap.get("group_id"));
			proDeptMap.put("hos_id", entityMap.get("hos_id"));
			proDeptMap.put("copy_code", entityMap.get("copy_code"));
			proDeptMap.put("proj_dept_id", proj_dept_id);
			proDeptMap.put("proj_dept_code", entityMap.get("proj_dept_code"));
			proDeptMap.put("proj_dept_name", entityMap.get("proj_dept_name"));
			proDeptMap.put("natur_code", entityMap.get("natur_code"));
			proDeptMap.put("is_stop", entityMap.get("is_stop"));
			proDeptMap.put("is_last", entityMap.get("is_last"));
			proDeptMap.put("spell_code", entityMap.get("spell_code"));
			proDeptMap.put("wbx_code", entityMap.get("wbx_code"));
			
			int result = htcProDeptDictMapper.addHtcProDept(proDeptMap);

			if(result > 0){
				
				proDeptMap.put("proj_dept_no", htcProDeptDictMapper.queryProjDeptDictSeq());
				proDeptMap.put("user_code", SessionManager.getUserCode());
				proDeptMap.put("create_date", new Date());
				proDeptMap.put("dlog", "新增");
				proDeptMap.put("is_disable", 0);
				
				htcProDeptDictMapper.addHtcProDeptDict(proDeptMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcProDeptDict\"}";
		}
	}
	
	@Override
	public String synchroHtcProDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
           try {
			
			htcProDeptDictMapper.synchroHtcProDept(entityMap);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码 synchroHtcProDept\"}";
		}
	}

	@Override
	public String queryHtcProDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcProDeptDict> list = htcProDeptDictMapper.queryHtcProDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcProDeptDict> list = htcProDeptDictMapper.queryHtcProDept(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcProDeptDict queryHtcProDeptByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcProDeptDictMapper.queryHtcProDeptByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcProDept(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
          try {
			
        	htcProDeptDictMapper.deleteBatchHtcProDeptDict(list);
        	  
			htcProDeptDictMapper.deleteBatchHtcProDept(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcProDept\"}";
		}
	}

	@Override
	public String updateHtcProDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   try {
				
			   /*
				 * 判断核算科室编码是否存在
				 * */
				Map<String, Object> existsProDeptMap= new HashMap<String, Object>();
				
				existsProDeptMap.put("group_id", entityMap.get("group_id"));
				existsProDeptMap.put("hos_id", entityMap.get("hos_id"));
				existsProDeptMap.put("copy_code", entityMap.get("copy_code"));
				existsProDeptMap.put("proj_dept_code", entityMap.get("proj_dept_code"));
				
				HtcProDeptDict htcProDept =  htcProDeptDictMapper.queryHtcProDeptByCode(existsProDeptMap);
				
				if(null != htcProDept){
					
					return "{\"error\":\"科室编码重复.\",\"state\":\"false\"}";
				}
			   
				htcProDeptDictMapper.updateHtcProDept(entityMap);
				
                if(entityMap.get("history").equals("true")){
                	
                	Map<String, Object> map = new HashMap<String, Object>();
    				map.put("group_id", entityMap.get("group_id"));
    				map.put("hos_id", entityMap.get("hos_id"));
    				map.put("proj_dept_id", entityMap.get("proj_dept_id"));
    				map.put("is_stop", entityMap.get("is_stop"));

    				htcProDeptDictMapper.updateHtcProDeptDictState(map);
    				
    				entityMap.put("proj_dept_no", htcProDeptDictMapper.queryProjDeptDictSeq());
    				entityMap.put("user_code", SessionManager.getUserCode());
    				entityMap.put("create_date", new Date());
    				entityMap.put("dlog", "变更");
    				entityMap.put("is_disable", 0);
    		     	
    		     	htcProDeptDictMapper.addHtcProDeptDict(entityMap);
                	
                }else{
                	
                	entityMap.put("is_disable", 0);
                	entityMap.put("user_code", SessionManager.getUserCode());
    				entityMap.put("create_date", new Date());
    				entityMap.put("dlog", "变更");

    				htcProDeptDictMapper.updateHtcProDeptDict(entityMap);
                }
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 updateHtcProDept\"}";
			}
	}


}
