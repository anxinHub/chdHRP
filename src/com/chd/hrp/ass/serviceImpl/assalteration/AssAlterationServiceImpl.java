package com.chd.hrp.ass.serviceImpl.assalteration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.assalteration.AssAlterationMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssPropertyMonthMain;
import com.chd.hrp.ass.service.assalteration.AssAlterationService;
import com.github.pagehelper.PageInfo;

@Service("assAlterationService")
public class AssAlterationServiceImpl implements AssAlterationService {
	
	@Resource(name="assAlterationMapper")
	private final AssAlterationMapper assAlterationMapper = null ;

	@Override
	public String queryAssAlteration(Map<String, Object> entityMap) {
SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> ListVo=assAlterationMapper.queryAssAlteration(entityMap);
			
			return ChdJson.toJson(ListVo);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> ListVo=assAlterationMapper.queryAssAlteration(entityMap,rowBounds);
			
			PageInfo page = new PageInfo(ListVo);
			
			return ChdJson.toJson(ListVo, page.getTotal());
			
		}
		
		/*List<Map<String,Object>> requestList = new ArrayList<Map<String,Object>>();
		Map<String,Object> entryMap = new HashMap<String, Object>();
		for (Map<String, Object> map : ListVo) {
			if("0".equals(map.get("is_init").toString())){
				map.get("sum_money");
			}
			if(map.get("buy_type").equals("01")||map.get("buy_type").equals("02")||
			   map.get("buy_type").equals("03")||map.get("buy_type").equals("05")||
			   map.get("buy_type").equals("06")||map.get("buy_type").equals("07")||
			   map.get("buy_type").equals("13")||map.get("buy_type").equals("15"))
			{
				
			}
		}*/
		//return ChdJson.toJson(ListVo);
	}

	@Override
	public List<Map<String, Object>> queryAssAlterationMainPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("year_month")!= null ){
			String year = entityMap.get("year_month").toString().substring(0, 4);
			String month = entityMap.get("year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		List<Map<String, Object>> list = assAlterationMapper.queryAssAlterationMainPrint(entityMap);
	
		return list;
	} 

}
