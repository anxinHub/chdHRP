/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.depre.acc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
//import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccAllMapper;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccAllService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资产折旧_所有性质
 * @View: V_ASS_DEPRE_ACC
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assDepreAccAllService")
public class AssDepreAccAllServiceImpl implements AssDepreAccAllService {

	//private static Logger logger = Logger.getLogger(AssDepreAccAllServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assDepreAccAllMapper")
	private final AssDepreAccAllMapper assDepreAccAllMapper = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryAssDepreAccInfo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = assDepreAccAllMapper.queryAssDepreAccInfo(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = assDepreAccAllMapper.queryAssDepreAccInfo(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	@Override
	public List<Map<String, Object>> queryAssDepreAccInfoPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("depre_year_month") != null){
			String year = entityMap.get("depre_year_month").toString().substring(0, 4);
			String month = entityMap.get("depre_year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		List<Map<String, Object>> list = assDepreAccAllMapper.queryAssDepreAccInfo(entityMap);
	
		return list;
	}

}
