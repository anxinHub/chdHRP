/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.storage.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.med.dao.storage.out.MedOutDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedInvBarRoutingMapper;
import com.chd.hrp.med.dao.storage.query.MedOutDeptMapper;
import com.chd.hrp.med.dao.storage.query.MedInDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedInSupCountMapper;
import com.chd.hrp.med.dao.storage.query.MedInSupDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedStockDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedStockRoutingMapper;
import com.chd.hrp.med.dao.storage.query.MedWorkDetailMapper;
import com.chd.hrp.med.service.storage.query.MedInDetailService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 入库明细查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medInDetailService")
public class MedInDetailServiceImpl implements MedInDetailService {

	private static Logger logger = Logger.getLogger(MedInDetailServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "medInDetailMapper")
	private final MedInDetailMapper medInDetailMapper = null;

	@Resource(name = "medInSupCountMapper")
	private final MedInSupCountMapper medInSupCountMapper = null;
	
	@Resource(name = "medInSupDetailMapper")
	private final MedInSupDetailMapper medInSupDetailMapper = null;
	
	@Resource(name = "medOutDeptMapper")
	private final  MedOutDeptMapper medOutDeptMapper = null;
	
	@Resource(name = "medStockDetailMapper")
	private final MedStockDetailMapper medStockDetailMapper = null;
	
	@Resource(name = "medWorkDetailMapper")
	private final MedWorkDetailMapper medWorkDetailMapper = null;
	
	@Resource(name = "medStockRoutingMapper")
	private final MedStockRoutingMapper medStockRoutingMapper = null;
	
	@Resource(name = "medInvBarRoutingMapper")
	private final MedInvBarRoutingMapper medInvBarRoutingMapper = null;
	
	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;
	
	@Resource(name = "medOutDetailMapper")
	private final MedOutDetailMapper medOutDetailMapper = null;
	
	/**
	 * @Description 
	 * 库存明细查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStorageQueryStockDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));
		
		
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);
		AccYearMonth acc=new AccYearMonth();
		if(accYearMonth.size()>0){
			 acc=accYearMonth.get(0);
		}else{
			return ChdJson.toJson(new ArrayList());
		}
		
		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
				
		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
		entityMap.put("begin_date", DateUtil.dateToString(begin_date,"yyyy-MM-dd"));
		
		entityMap.put("end_date", DateUtil.dateToString(end_date,"yyyy-MM-dd"));
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medStockDetailMapper.queryMedStorageQueryStockDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medStockDetailMapper.queryMedStorageQueryStockDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}

	//业务明细
	@Override
	public String queryMedStorageQueryWorkDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));
		
		
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);
		AccYearMonth acc=new AccYearMonth();
		if(accYearMonth.size()>0){
			 acc=accYearMonth.get(0);
		}else{
			return ChdJson.toJson(new ArrayList());
		}
		
		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
				
		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
		entityMap.put("begin_date", DateUtil.dateToString(begin_date,"yyyy-MM-dd"));
		
		entityMap.put("end_date", DateUtil.dateToString(end_date,"yyyy-MM-dd"));
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medWorkDetailMapper.queryMedStorageQueryWorkDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medWorkDetailMapper.queryMedStorageQueryWorkDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}

	
	/**
	 * @Description 
	 * 库存分布查询 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStorageQueryStockRouting(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

       SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medStockRoutingMapper.queryMedStorageQueryStockRouting(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medStockRoutingMapper.queryMedStorageQueryStockRouting(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 条码分布查询 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStorageQueryInvBarRouting(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medInvBarRoutingMapper.queryMedStorageQueryInvBarRouting(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medInvBarRoutingMapper.queryMedStorageQueryInvBarRouting(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 入库明细查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStorageQueryInDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medInDetailMapper.queryMedStorageQueryInDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medInDetailMapper.queryMedStorageQueryInDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		

	}


	@Override
	public String queryMedStorageQueryMedInSupCount(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
    
			 
			
			List<Map<String, Object>> list = medInSupCountMapper.queryMedStorageQueryMedInSupCount(entityMap);
			List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
			LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
			
			for(int i=0;i<list.size();i++){
				int a=0;
				
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				typeToMoneymap.put((String)map.get("med_type_code"), map.get("money"));
				
				if(returnDataMap.containsKey(map.get("sup_code")+","+map.get("bus_type_code")))
				{
					
				
					//如果前台传的级次为非末级，只取药品类别为首级的金额进行合计，
					if("1".equals(entityMap.get("type_level"))){
					//总金额
						if("1".equals(map.get("type_level").toString())){
							map.putAll(typeToMoneymap);
							//根据部门編碼取出取出之前存入（或运算后）的总金额
							Map sumMoney=returnDataMap.get(map.get("sup_code")+","+map.get("bus_type_code"));
							Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
							//最新一条数据的金额
							Double count2= Double.parseDouble(map.get("money").toString());
							//两条数据相加修改amount_money
							sumMoney.put("amount_money", count+count2);
							if(map.get("med_type_code").equals(sumMoney.get("med_type_code"))){
							//取出当前数据药品类别所对应的钱
							Double typeMoney=Double.parseDouble(map.get(map.get("med_type_code")).toString());
							//再取出之前存好的钱，进行运算
							Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("med_type_code")).toString());
							sumMoney.put(map.get("med_type_code").toString(),typeMoney+oldtypeMoney);
							}else{
								sumMoney.put(map.get("med_type_code").toString(),map.get("money"));
								sumMoney.put("med_type_code", map.get("med_type_code"));
							}
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
						}
					}else {
						if("1".equals(map.get("is_last").toString())){
							map.putAll(typeToMoneymap);
							//根据部门編碼取出取出之前存入（或运算后）的总金额
							Map sumMoney=returnDataMap.get(map.get("sup_code")+","+map.get("bus_type_code"));
							Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
							//最新一条数据的金额
							Double count2= Double.parseDouble(map.get("money").toString());
							//两条数据相加修改amount_money
							sumMoney.put("amount_money", count+count2);
							if(map.get("med_type_code").equals(sumMoney.get("med_type_code"))){
								//取出当前数据药品类别所对应的钱
								Double typeMoney=Double.parseDouble(map.get(map.get("med_type_code")).toString());
								//再取出之前存好的钱，进行运算
								Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("med_type_code")).toString());
								sumMoney.put(map.get("med_type_code").toString(),typeMoney+oldtypeMoney);
								}else{
									sumMoney.put(map.get("med_type_code").toString(),map.get("money"));
									sumMoney.put("med_type_code", map.get("med_type_code"));
								}
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
								}
					}
				}else{
					 //如果前台传的级次为非末级，只取药品类别为首级的金额进行合计，
					 if("1".equals(entityMap.get("type_level"))){
						 if("1".equals(map.get("type_level").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), map);
							 Map sumMoney= returnDataMap.get(map.get("sup_code")+","+map.get("bus_type_code"));
							//取出当前部门的金额填入到amount_money中
							 sumMoney.put("amount_money",sumMoney.get("money"));
							 sumMoney.put(map.get("med_type_code"),map.get(map.get("med_type_code")));
							 sumMoney.put("med_type_code", map.get("med_type_code"));
							 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
						 }
					 }else {
						 if("1".equals(map.get("is_last").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), map);
							 Map sumMoney= returnDataMap.get(map.get("sup_code")+","+map.get("bus_type_code"));
							 sumMoney.put("amount_money",map.get("money"));
							 sumMoney.put(map.get("med_type_code"),map.get(map.get("med_type_code")));
							 sumMoney.put("med_type_code", map.get("med_type_code"));
							 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
						 }
					 }
					 
					
				}
			}
			for (String  j : returnDataMap.keySet()) {
				overloadinglist.add(returnDataMap.get(j));
			}
			return ChdJson.toJson(overloadinglist );
		}
		
	

	@Override
	public String queryMedStorageQueryMedInSupDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = medInSupDetailMapper.queryMedStorageQueryMedInSupDetail(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = medInSupDetailMapper.queryMedStorageQueryMedInSupDetail(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
			
	}


	@Override
	public String queryMedStorageQueryMedOutDept(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = medOutDeptMapper.queryMedStorageQueryMedOutDept(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = medOutDeptMapper.queryMedStorageQueryMedOutDept(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}



	@Override
	public String queryMedStorageQueryOutDetail(Map<String, Object> entityMap)throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medOutDetailMapper.queryMedStorageQueryOutDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medOutDetailMapper.queryMedStorageQueryOutDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}



	@Override
	public String queryMedOutSupMessage(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medOutDetailMapper.queryMedOutSupMessage(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medOutDetailMapper.queryMedOutSupMessage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 入库台账查询
	 */
	@Override
	public String queryMedStorageInvCertDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medInDetailMapper.queryMedStorageInvCertDetail(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medInDetailMapper.queryMedStorageInvCertDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}


	/**
	 * 科室申领统计查询
	 */
	@Override
	public String queryMedApplyCount(Map<String, Object> entityMap)  throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medInDetailMapper.queryMedApplyCount(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medInDetailMapper.queryMedApplyCount(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 供应商入库汇总查询
	 */
	@Override
	public String queryMedInSupBusTypeSum(Map<String, Object> entityMap)  throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medInDetailMapper.queryMedInSupBusTypeSum(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medInDetailMapper.queryMedInSupBusTypeSum(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
}
