package com.chd.hrp.ass.serviceImpl.dailymanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dailymanagement.AssRepairTroubleMapper;
import com.chd.hrp.ass.entity.repair.AssRepairRecDetail;
import com.chd.hrp.ass.service.dailymanagement.AssRepairTroubleService;
import com.github.pagehelper.PageInfo;


@Service("assRepairTroubleService")
public class AssRepairTroubleServiceImpl  implements AssRepairTroubleService{
	
	
	private static Logger logger = Logger.getLogger(AssRepairTroubleServiceImpl.class);
	
	
	//引入DAO操作
		@Resource(name = "assRepairTroubleMapper")
		private final AssRepairTroubleMapper assRepairTroubleMapper = null;
	/**
	 * 查询维修故障统计	
	 */
	@Override
	public String querytrouble(Map<String, Object> entityMap)throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairTrouble(entityMap);
			//listVo.add(list);
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairTrouble(entityMap, rowBounds);
		//	listVo.add(list);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 查看维修费用
	 */
	@Override
	public String queryAssRepairRec(Map<String, Object> entityMap) {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairRec(entityMap);
			//listVo.add(list);
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairRec(entityMap, rowBounds);
		//	listVo.add(list);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 查看维修工时
	 */
	@Override
	public String queryAssRepairHours(Map<String, Object> entityMap) {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairHours(entityMap);
			//listVo.add(list);
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairHours(entityMap, rowBounds);
		//	listVo.add(list);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 查询维修费用对比
	 */
	@Override
	public String queryAssRepairMoneyContrast(Map<String, Object> entityMap) {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairMoneyContrast(entityMap);
			//listVo.add(list);
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>>  list = assRepairTroubleMapper.queryAssRepairMoneyContrast(entityMap, rowBounds);
		//	listVo.add(list);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	

}
