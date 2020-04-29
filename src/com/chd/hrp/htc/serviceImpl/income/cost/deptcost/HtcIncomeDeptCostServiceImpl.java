package com.chd.hrp.htc.serviceImpl.income.cost.deptcost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.d;

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
import com.chd.hrp.htc.dao.income.cost.deptcost.HtcIncomeDeptCostMapper;
import com.chd.hrp.htc.dao.info.basic.HtcCostItemDictMapper;
import com.chd.hrp.htc.dao.info.basic.HtcDeptDictMapper;
import com.chd.hrp.htc.entity.income.cost.deptcost.HtcIncomeDeptCost;
import com.chd.hrp.htc.entity.info.basic.HtcCostItemDict;
import com.chd.hrp.htc.service.income.cost.deptcost.HtcIncomeDeptCostService;
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

@Service("htcIncomeDeptCostService")
public class HtcIncomeDeptCostServiceImpl implements HtcIncomeDeptCostService {

	private static Logger logger = Logger.getLogger(HtcIncomeDeptCostServiceImpl.class);

	@Resource(name = "htcIncomeDeptCostMapper")
	private final HtcIncomeDeptCostMapper htcIncomeDeptCostMapper = null;
	
	@Resource(name = "htcDeptDictMapper")
	private final HtcDeptDictMapper htcDeptDictMapper = null;
	
	@Resource(name = "htcCostItemDictMapper")
	private final HtcCostItemDictMapper htcCostItemDictMapper = null;

	/**
	 * 
	 */
	@Override
	public String addHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException {
		
		
		try {
			
			HtcIncomeDeptCost htcIncomeDeptCost = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostByCode(entityMap);
			
			 if(null != htcIncomeDeptCost){
				
				 return "{\"error\":\"数据已经存在.\",\"state\":\"false\"}";
			}
			 
			 entityMap.put("tot_amount", "".equals(entityMap.get("tot_amount"))?0:entityMap.get("tot_amount"));
			 entityMap.put("prime_amount", "".equals(entityMap.get("prime_amount"))?0:entityMap.get("prime_amount"));
			 entityMap.put("pub_amount", "".equals(entityMap.get("pub_amount"))?0:entityMap.get("pub_amount"));
			 entityMap.put("man_amount", "".equals(entityMap.get("man_amount"))?0:entityMap.get("man_amount"));
			 entityMap.put("ass_amount", "".equals(entityMap.get("ass_amount"))?0:entityMap.get("ass_amount"));
			 
			 htcIncomeDeptCostMapper.addHtcIncomeDeptCost(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
		
	}

	@Override
	public String addBatchHtcIncomeDeptCost(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		return null;
	}
	
	/**
	 * 
	 */
	@Override
	public String queryHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException {
		
         SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomeDeptCost> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCost(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomeDeptCost> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}

	/**
	 * 
	 */
	@Override
	public HtcIncomeDeptCost queryHtcIncomeDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException {
		return htcIncomeDeptCostMapper.queryHtcIncomeDeptCostByCode(entityMap);
	}


	@Override
	public String deleteHtcIncomeDeptCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   try {
				
			   htcIncomeDeptCostMapper.deleteHtcIncomeDeptCost(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcIncomeDeptCost( 
			List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
		   htcIncomeDeptCostMapper.deleteBatchHtcIncomeDeptCost(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String queryHtcIncomeDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException {
	          SysPage sysPage = new SysPage();
		
				sysPage = (SysPage)entityMap.get("sysPage");
				
				if(sysPage.getTotal() == -1){
					
					List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostTotAmount(entityMap);
					
					return ChdJson.toJson(list);
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostTotAmount(entityMap, rowBounds);
					
					PageInfo page = new PageInfo(list);
					
					return ChdJson.toJson(list,page.getTotal());
				}

	}

	@Override
	public String queryHtcIncomeDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException {
		    SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostPrimeAmount(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostPrimeAmount(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}

	}

	@Override
	public String queryHtcIncomeDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException {
		    SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostPubAmount(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostPubAmount(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}

	}

	@Override
	public String queryHtcIncomeDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostManAmount(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostManAmount(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}

	}

	@Override
	public String queryHtcIncomeDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException {
		
		    SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostAssAmount(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostAssAmount(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
	}

	
	public Map<String, Object> deptDictCheck(){
		/*
		 *科室字典
		 **/
		Map<String, Object> backMap = new HashMap<String, Object>();
		Map<String, Object> deptMap = new HashMap<String, Object>();
		deptMap.put("group_id", SessionManager.getGroupId());
		deptMap.put("hos_id", SessionManager.getHosId());
		deptMap.put("is_last", 1);
		List<Map<String,Object>> deptMapResult = htcDeptDictMapper.queryHtcDeptDict(deptMap);
		
		for (Map<String,Object> dept :deptMapResult) {
			backMap.put(dept.get("dept_code").toString(), dept.get("dept_id") + "."+ dept.get("dept_no"));
		}
	    return backMap;
	}
	
	 public Map<String, Object> itemDictCheck(){
		/*
		 *成本项目字典
		 **/
		Map<String, Object> backMap = new HashMap<String, Object>();
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("group_id", SessionManager.getGroupId());
		itemMap.put("hos_id", SessionManager.getHosId());
		itemMap.put("copy_code", SessionManager.getCopyCode());
		itemMap.put("is_last", 1);
		List<HtcCostItemDict>  htcCostItemDict = htcCostItemDictMapper.queryHtcCostItemDict(itemMap);
		
		for (HtcCostItemDict itemDict :htcCostItemDict) {
			backMap.put(itemDict.getCost_item_code(), itemDict.getCost_item_id() + "."+ itemDict.getCost_item_no());
		 }
		
	    return backMap;
	}
	 
	  public Map<String, Object> sourceCheck(){
			/*
			 *资金来源
			 **/
			Map<String, Object> backMap = new HashMap<String, Object>();
			backMap.put("001", "1");
			backMap.put("002", "2");
			backMap.put("003", "3");
			backMap.put("004", "4");
			
		    return backMap;
		}
	
	@Override
	public String impHtcIncomeDeptCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
		    Map<String, Object> dept = deptDictCheck();
		    
		    Map<String, Object> item =itemDictCheck();
		    
		    Map<String, Object> source =sourceCheck();  
		    
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			for (Map<String, List<String>> map : list) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("acc_year", map.get("year_month").get(1).substring(0, 4));
				
				mapVo.put("acc_month", map.get("year_month").get(1).substring(4, 6));
				
				if(null == dept.get(map.get("dept_code").get(1))){
					
					return "{\"error\":\""+ map.get("dept_code").get(0)+"\n科室编码:"+map.get("dept_code").get(1)+"不存在.\",\"state\":\"false\"}";
				
				}else {
			
					String dept_code= dept.get(map.get("dept_code").get(1)).toString();
					int index = dept_code.indexOf(".");
					int lenth = dept_code.length();
					mapVo.put("dept_id", dept_code.substring(0, index));
					mapVo.put("dept_no", dept_code.substring(index+1, lenth));
					
				}
				
                if(null == item.get(map.get("cost_item_code").get(1))){
					
					return "{\"error\":\""+ map.get("cost_item_code").get(0)+"\n成本项目编码:"+map.get("cost_item_code").get(1)+"不存在.\",\"state\":\"false\"}";
				
				}else {
			
					String cost_item_code= item.get(map.get("cost_item_code").get(1)).toString();
					int index = cost_item_code.indexOf(".");
					int lenth = cost_item_code.length();
					mapVo.put("cost_item_id", cost_item_code.substring(0, index));
					mapVo.put("cost_item_no", cost_item_code.substring(index+1, lenth));
					
				}
                
                 if(null == source.get(map.get("source_code").get(1))){
					
					return "{\"error\":\""+ map.get("source_code").get(0)+"\n资金来源编码:"+map.get("source_code").get(1)+"不存在.\",\"state\":\"false\"}";
				
				  }else {

					mapVo.put("source_id", source.get(map.get("source_code").get(1)));
					
				  }
				
                   mapVo.put("tot_amount", null ==(map.get("tot_amount").get(1))?0:Double.parseDouble(map.get("tot_amount").get(1)));
                   mapVo.put("prime_amount", null ==(map.get("prime_amount").get(1))?0:Double.parseDouble(map.get("prime_amount").get(1)));
                   mapVo.put("pub_amount", null ==(map.get("pub_amount").get(1))?0:Double.parseDouble(map.get("pub_amount").get(1)));
                   mapVo.put("man_amount", null ==(map.get("man_amount").get(1))?0:Double.parseDouble(map.get("man_amount").get(1)));
                   mapVo.put("ass_amount", null ==(map.get("ass_amount").get(1))?0:Double.parseDouble(map.get("ass_amount").get(1)));
    			 
                   
                   HtcIncomeDeptCost htcIncomeDeptCost = htcIncomeDeptCostMapper.queryHtcIncomeDeptCostByCode(mapVo);
                   
                   if(null != htcIncomeDeptCost){
                	  
                	   return "{\"error\":\""+ map.get("year_month").get(0)+"\n已存在此条数据.\",\"state\":\"false\"}";
                   }
                   
    			 
                 resultSet.add(mapVo);
			 }
			
			       if(resultSet.size() > 0){
			    	   
			    	   htcIncomeDeptCostMapper.addBatchHtcIncomeDeptCost(resultSet);
			       }
			    
			  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
}
