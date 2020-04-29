package com.chd.hrp.cost.serviceImpl;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.cost.service.CostStartModService;
import com.chd.hrp.hr.serviceImpl.attendancemanagement.leave.HrApplyingLeavesServiceImpl;
import com.chd.hrp.sys.dao.ModMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.chd.hrp.sys.serviceImpl.base.SysBaseServiceImpl;
import com.chd.hrp.sys.serviceImpl.base.SysConfigServiceImpl;
import com.github.pagehelper.PageInfo;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2016年7月5日 下午5:32:36
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: BELL
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */
@Service("costStartModService")
public class CostStartModServiceImpl implements CostStartModService {
	private static Logger logger = Logger.getLogger(CostStartModServiceImpl.class);

	@Resource(name = "modMapper")
	private final ModMapper modMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name="accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper=null;
	@Resource(name="sysConfigService")
	private final SysConfigService sysConfigService=null;
	
	/**
	 * @Description 查询Mod分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMod(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Mod> list = modMapper.queryMod(entityMap, rowBounds);
		List<Mod> resultList = new ArrayList<Mod>();
		for(Mod temp : list){
			entityMap.put("mod_code", temp.getMod_code());
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if(modStart != null){
				temp.setModStart(modStart);
			}
			resultList.add(temp);
		}
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(resultList, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 添加ModStart
	 * @param ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addModStart(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if( StringTool.isNotBlank(modStart.getStart_month())){
				return "{\"msg\":\"已经启动.\",\"state\":\"true\"}";
			}else{
			
			if(modStart != null){
				modStartMapper.updateModStart(entityMap);
			}else{
				modStartMapper.addModStart(entityMap);
			}
			
			//修改当前系统模块会计期间结账标记:小于当年会计年度、会计月份的期间为已结账状态
			Map<String,Object> accYearMonthMap = new HashMap<String,Object>();
			
			accYearMonthMap.put("group_id", SessionManager.getGroupId());
			accYearMonthMap.put("hos_id", SessionManager.getHosId());
			accYearMonthMap.put("copy_code", SessionManager.getCopyCode());
			
			accYearMonthMap.put("flag", "COST_FLAG");//当前系统结账标记字段名称
			accYearMonthMap.put("user", "COST_USER");//当前系统结账人字段名称
			accYearMonthMap.put("user_id", SessionManager.getUserId());//结账人Id
			accYearMonthMap.put("date", "COST_DATE");//当前系统结账日期字段名称
			
			String year_month = String.valueOf(entityMap.get("start_year")) + String.valueOf(entityMap.get("start_month"));
			accYearMonthMap.put("year_month",year_month);
			
			accYearMonthMapper.updateModAccYearMonth(accYearMonthMap);
			//加载会计期间
			sysConfigService.querySysParaInit(accYearMonthMap);
			return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
			}
			}
		 catch (Exception e) {


			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	
}
	}
