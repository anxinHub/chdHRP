/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
 
package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostChargeItemArrtMapper;
import com.chd.hrp.cost.dao.CostInComeCollectionMapper;
import com.chd.hrp.cost.dao.CostIncomeMapper;
import com.chd.hrp.cost.entity.CostChargeItemArrt;
import com.chd.hrp.cost.entity.CostDeptCost;
import com.chd.hrp.cost.entity.CostIncome;
import com.chd.hrp.cost.service.CostInComeCollectionService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室收入归集
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costInComeCollectionService")
public class CostInComeCollectionServiceImpl implements CostInComeCollectionService {

	private static Logger logger = Logger.getLogger(CostInComeCollectionServiceImpl.class);

	@Resource(name = "costInComeCollectionMapper")
	private final CostInComeCollectionMapper costInComeCollectionMapper = null;
	
	@Resource(name = "costIncomeMapper")
	private final CostIncomeMapper costIncomeMapper = null;

	@Resource(name = "costChargeItemArrtMapper")
	private final CostChargeItemArrtMapper costChargeItemArrtMapper = null;
	
	@Override
	public String queryIncomeCollection(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostIncome> list = costInComeCollectionMapper.queryIncomeCollection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostIncome> list = costInComeCollectionMapper.queryIncomeCollection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}

	}
	
	@Override
	public String queryIncomeCollectionPrmHead(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costInComeCollectionMapper.queryIncomeCollectionPrmHead(entityMap);
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryIncomeCollectionPrm(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("group_id", SessionManager.getGroupId());
		m.put("hos_id", SessionManager.getHosId());
		m.put("copy_code", SessionManager.getCopyCode());
		/*//收费项目集合
		List<CostChargeItemArrt> prm = (List<CostChargeItemArrt>) costChargeItemArrtMapper.queryCostChargeItemArrtByKindCode(m);
		//由于绩效成本中 收费项目 特殊  和普通账套下的收费项目数量相差太大,数量超过一定量后，页面会加载崩溃， 为避免页面加载崩溃 暂时控制收费项目个数为50个
		List<CostChargeItemArrt> prm_new=new ArrayList<CostChargeItemArrt>();
		if(prm.size()>50){
			for (int i = 0; i < prm.size(); i++) {
				if(i>50){
					break;
				}
				prm_new.add(prm.get(i));
            } 
			
		}else{
			prm_new=prm;
		}
		
		for (CostChargeItemArrt costChargeItemArrt : prm_new) {
			System.out.println(costChargeItemArrt);
		}*/
		List<Map<String, Object>> prm_new = costInComeCollectionMapper.queryIncomeCollectionPrmHead(entityMap);
		entityMap.put("prm", prm_new);
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = costInComeCollectionMapper.queryIncomeCollectionPrm(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = costInComeCollectionMapper.queryIncomeCollectionPrm(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostInComeCollectionPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costInComeCollectionMapper.queryCostInComeCollectionPrint(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryCostInComeCollectionPrmPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> prm_new = costInComeCollectionMapper.queryIncomeCollectionPrmHead(entityMap);
		entityMap.put("prm", prm_new);
		
		List<Map<String, Object>> list=costInComeCollectionMapper.queryIncomeCollectionPrm(entityMap);
		
		return list;
		
	}
	
	@Override
	public String queryCostCollectionMainCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = costInComeCollectionMapper.queryCostCollectionMainCheck(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = costInComeCollectionMapper.queryCostCollectionMainCheck(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	@Override
	public String queryCostCollectionDetailCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = costInComeCollectionMapper.queryCostCollectionDetailCheck(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = costInComeCollectionMapper.queryCostCollectionDetailCheck(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	
	@Override
	public String addIncomeCollection(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>>  map =new ArrayList<Map<String,Object>>();
		
		try {
			
			/*
			 * 1.按照收入(类别)归集 
			 * 2.按照收入(项目)归集 
			 * */
			String para_value = MyConfig.getSysPara("03002");
			
			if("1".equals(para_value)){
				
				List<Map<String,Object>>  map_main = costInComeCollectionMapper.queryIncomeMain(entityMap);
				if(map_main.size() == 0 ){
					return "{\"error\":\" 收入采集(类别)没有数据！无法归集\"}";
				}
				
				map = map_main;
			}
			
           if("2".equals(para_value)){
				
				List<Map<String,Object>>  map_detail = costInComeCollectionMapper.queryIncomeDetail(entityMap);
				if(map_detail.size() == 0 ){
					return "{\"error\":\" 收入采集(项目)没有数据！无法归集\"}";
				}
				
				map = map_detail;
			}
			
			costIncomeMapper.deleteCostIncome(entityMap);
			
			for (Map<String, Object> inComeMapVo:map) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", entityMap.get("group_id"));
				
				mapVo.put("hos_id", entityMap.get("hos_id"));
				
				mapVo.put("copy_code", entityMap.get("copy_code"));
				
				mapVo.put("charge_kind_id", inComeMapVo.get("charge_kind_id"));
				
				mapVo.put("acc_year", inComeMapVo.get("acc_year"));
				
				mapVo.put("acc_month", inComeMapVo.get("acc_month"));
				
				mapVo.put("appl_dept_id", inComeMapVo.get("appl_dept_id"));
				
				mapVo.put("appl_dept_no", inComeMapVo.get("appl_dept_no"));
				
				mapVo.put("exec_dept_id", inComeMapVo.get("exec_dept_id"));
				
				mapVo.put("exec_dept_no", inComeMapVo.get("exec_dept_no"));
				
				mapVo.put("money", inComeMapVo.get("money"));
				
				mapVo.put("create_user", entityMap.get("create_user"));
				
				mapVo.put("create_date", new Date());
				
				list.add(mapVo);
				
			}
			
			   //默认1500条批量提交一次
               int  totalCount =list.size();  
			   
		       int  page_size = 1500;  
		       
		       int  requestCount = (totalCount-1)/page_size+1;
		       
	           if(list.size()>0){
				   
			       for (int i = 0; i < requestCount; i++) {
			    	   
			    	   Integer fromIndex = i * page_size;
			    	   
			    	   int toIndex = Math.min(totalCount, (i + 1) * page_size);
			   
			    	   costIncomeMapper.addBatchCostIncome(list.subList(fromIndex, toIndex));
			    	   
				     }
			   }
			
			
			return "{\"msg\":\"归集成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addIncomeCollection\"}";
		}

	}

}
