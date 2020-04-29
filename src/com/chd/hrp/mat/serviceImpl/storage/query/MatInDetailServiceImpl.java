/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.query;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.matprepay.MatPrePayMainMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatInvBarRoutingMapper;
import com.chd.hrp.mat.dao.storage.query.MatOutDeptMapper;
import com.chd.hrp.mat.dao.storage.query.MatInDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupCountMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatStockDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatStockRoutingMapper;
import com.chd.hrp.mat.dao.storage.query.MatWorkDetailMapper;
import com.chd.hrp.mat.service.storage.query.MatInDetailService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageHelper;
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
@Service("matInDetailService")
public class MatInDetailServiceImpl implements MatInDetailService {

	private static Logger logger = Logger.getLogger(MatInDetailServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matInDetailMapper")
	private final MatInDetailMapper matInDetailMapper = null;

	@Resource(name = "matInSupCountMapper")
	private final MatInSupCountMapper matInSupCountMapper = null;
	
	@Resource(name = "matInSupDetailMapper")
	private final MatInSupDetailMapper matInSupDetailMapper = null;
	
	@Resource(name = "matOutDeptMapper")
	private final  MatOutDeptMapper matOutDeptMapper = null;
	
	@Resource(name = "matStockDetailMapper")
	private final MatStockDetailMapper matStockDetailMapper = null;
	
	@Resource(name = "matWorkDetailMapper")
	private final MatWorkDetailMapper matWorkDetailMapper = null;
	
	@Resource(name = "matStockRoutingMapper")
	private final MatStockRoutingMapper matStockRoutingMapper = null;
	
	@Resource(name = "matInvBarRoutingMapper")
	private final MatInvBarRoutingMapper matInvBarRoutingMapper = null;
	
	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;
	
	@Resource(name = "matOutDetailMapper")
	private final MatOutDetailMapper matOutDetailMapper = null;
	
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	/**
	 * @Description 
	 * 库存明细查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatStorageQueryStockDetail(Map<String, Object> entityMap)
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
			
			List<Map<String, Object>> list = matStockDetailMapper.queryMatStorageQueryStockDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matStockDetailMapper.queryMatStorageQueryStockDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}

	//业务明细
	@Override
	public String queryMatStorageQueryWorkDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matWorkDetailMapper.queryMatStorageQueryWorkDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matWorkDetailMapper.queryMatStorageQueryWorkDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}

	//业务明细打印
	@Override
	public List<Map<String, Object>> queryMatStorageQueryWorkDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = matWorkDetailMapper.queryMatStorageQueryWorkDetail(entityMap);
			
		return list;
	}

	
	/**
	 * @Description 
	 * 库存分布查询 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatStorageQueryStockRouting(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

       SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matStockRoutingMapper.queryMatStorageQueryStockRouting(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matStockRoutingMapper.queryMatStorageQueryStockRouting(entityMap, rowBounds);
			
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
	public String queryMatStorageQueryInvBarRouting(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matInvBarRoutingMapper.queryMatStorageQueryInvBarRouting(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matInvBarRoutingMapper.queryMatStorageQueryInvBarRouting(entityMap, rowBounds);
			
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
	public String queryMatStorageQueryInDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matInDetailMapper.queryMatStorageQueryInDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matInDetailMapper.queryMatStorageQueryInDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		

	}
	/**
	 * @Description 
	 * 入库材料汇总查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatStorageInInv(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matInDetailMapper.queryMatStorageInInv(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matInDetailMapper.queryMatStorageInInv(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
		
	}


	@Override
	public String queryMatStorageQueryMatInSupCount(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
    
			 
			
			List<Map<String, Object>> list = matInSupCountMapper.queryMatStorageQueryMatInSupCount(entityMap);
			List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
			LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
			
			for(int i=0;i<list.size();i++){
				int a=0;
				
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				typeToMoneymap.put((String)map.get("mat_type_code"), map.get("money"));
				
				if(returnDataMap.containsKey(map.get("sup_code")+","+map.get("bus_type_code")))
				{
					
				
					//如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
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
							if(map.get("mat_type_code").equals(sumMoney.get("mat_type_code"))){
							//取出当前数据物资类别所对应的钱
							Double typeMoney=Double.parseDouble(map.get(map.get("mat_type_code")).toString());
							//再取出之前存好的钱，进行运算
							Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("mat_type_code")).toString());
							sumMoney.put(map.get("mat_type_code").toString(),typeMoney+oldtypeMoney);
							}else{
								sumMoney.put(map.get("mat_type_code").toString(),map.get("money"));
								sumMoney.put("mat_type_code", map.get("mat_type_code"));
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
							if(map.get("mat_type_code").equals(sumMoney.get("mat_type_code"))){
								//取出当前数据物资类别所对应的钱
								Double typeMoney=Double.parseDouble(map.get(map.get("mat_type_code")).toString());
								//再取出之前存好的钱，进行运算
								Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("mat_type_code")).toString());
								sumMoney.put(map.get("mat_type_code").toString(),typeMoney+oldtypeMoney);
								}else{
									sumMoney.put(map.get("mat_type_code").toString(),map.get("money"));
									sumMoney.put("mat_type_code", map.get("mat_type_code"));
								}
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
								}
					}
				}else{
					 //如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
					 if("1".equals(entityMap.get("type_level"))){
						 if("1".equals(map.get("type_level").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), map);
							 Map sumMoney= returnDataMap.get(map.get("sup_code")+","+map.get("bus_type_code"));
							//取出当前部门的金额填入到amount_money中
							 sumMoney.put("amount_money",sumMoney.get("money"));
							 sumMoney.put(map.get("mat_type_code"),map.get(map.get("mat_type_code")));
							 sumMoney.put("mat_type_code", map.get("mat_type_code"));
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
							 sumMoney.put(map.get("mat_type_code"),map.get(map.get("mat_type_code")));
							 sumMoney.put("mat_type_code", map.get("mat_type_code"));
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
	public String queryMatStorageQueryMatInSupDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matInSupDetailMapper.queryMatStorageQueryMatInSupDetail(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matInSupDetailMapper.queryMatStorageQueryMatInSupDetail(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
			
	}

	@Override
	public String queryMatStorageQueryMatInSupDetailSet(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matInSupDetailMapper.queryMatStorageQueryMatInSupDetailSet(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matInSupDetailMapper.queryMatStorageQueryMatInSupDetailSet(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
			
	}

	@Override
	public String queryMatStorageQueryMatOutDept(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matOutDeptMapper.queryMatStorageQueryMatOutDept(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matOutDeptMapper.queryMatStorageQueryMatOutDept(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	@Override
	public String queryMatAccountReportTranCollection(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matOutDeptMapper.queryMatAccountReportTranCollection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matOutDeptMapper.queryMatAccountReportTranCollection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String queryMatAccountReportTranDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matOutDeptMapper.queryMatAccountReportTranDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matOutDeptMapper.queryMatAccountReportTranDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	/**
	 * 调拨查询打印
	 */
	@Override
	public List<Map<String, Object>> queryMatAccountReportTranCollectionOrDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("user_id", SessionManager.getUserId());
			
			
			List<Map<String, Object>> list=null;
			if (entityMap.get("show_detail")!=null && "1".equals(String.valueOf(entityMap.get("show_detail")))) {
				list = matOutDeptMapper.queryMatAccountReportTranDetail(entityMap);
			}else{
				list=matOutDeptMapper.queryMatAccountReportTranCollection(entityMap);
			}
		return list;
			
	}

	@Override
	public String queryMatStorageQueryMatOutDeptNew(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matOutDeptMapper.queryMatStorageQueryMatOutDeptNew(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matOutDeptMapper.queryMatStorageQueryMatOutDeptNew(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}



	@Override
	public String queryMatStorageQueryOutDetail(Map<String, Object> entityMap)throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matOutDetailMapper.queryMatStorageQueryOutDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matOutDetailMapper.queryMatStorageQueryOutDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String queryMatStorageOutInvCollection(Map<String, Object> entityMap)throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matOutDetailMapper.queryMatStorageOutInvCollection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matOutDetailMapper.queryMatStorageOutInvCollection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}



	@Override
	public String queryMatOutSupMessage(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matOutDetailMapper.queryMatOutSupMessage(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matOutDetailMapper.queryMatOutSupMessage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 入库台账查询
	 */
	@Override
	public String queryMatStorageInvCertDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matInDetailMapper.queryMatStorageInvCertDetail(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matInDetailMapper.queryMatStorageInvCertDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}


	/**
	 * 科室申领统计查询
	 */
	@Override
	public String queryMatApplyCount(Map<String, Object> entityMap)  throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matInDetailMapper.queryMatApplyCount(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matInDetailMapper.queryMatApplyCount(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 供应商入库汇总查询
	 */
	@Override
	public String queryMatInSupBusTypeSum(Map<String, Object> entityMap)  throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matInDetailMapper.queryMatInSupBusTypeSum(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matInDetailMapper.queryMatInSupBusTypeSum(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatInSupBusTypeSumPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matInDetailMapper.queryMatInSupBusTypeSum(entityMap);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryMatInSupCountPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		List<Map<String, Object>> list = matInSupCountMapper.queryMatStorageQueryMatInSupCount(entityMap);
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
		
		for(int i=0;i<list.size();i++){
			int a=0;
			
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			Map<String,Object>map=list.get(i);
			typeToMoneymap.put((String)map.get("mat_type_code"), map.get("money"));
			
			if(returnDataMap.containsKey(map.get("sup_code")+","+map.get("bus_type_code")))
			{
				
			
				//如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
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
						if(map.get("mat_type_code").equals(sumMoney.get("mat_type_code"))){
						//取出当前数据物资类别所对应的钱
						Double typeMoney=Double.parseDouble(map.get(map.get("mat_type_code")).toString());
						//再取出之前存好的钱，进行运算
						Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("mat_type_code")).toString());
						sumMoney.put(map.get("mat_type_code").toString(),typeMoney+oldtypeMoney);
						}else{
							sumMoney.put(map.get("mat_type_code").toString(),map.get("money"));
							sumMoney.put("mat_type_code", map.get("mat_type_code"));
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
						if(map.get("mat_type_code").equals(sumMoney.get("mat_type_code"))){
							//取出当前数据物资类别所对应的钱
							Double typeMoney=Double.parseDouble(map.get(map.get("mat_type_code")).toString());
							//再取出之前存好的钱，进行运算
							Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("mat_type_code")).toString());
							sumMoney.put(map.get("mat_type_code").toString(),typeMoney+oldtypeMoney);
							}else{
								sumMoney.put(map.get("mat_type_code").toString(),map.get("money"));
								sumMoney.put("mat_type_code", map.get("mat_type_code"));
							}
						//将运算后的数据从新封装
						returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
							}
				}
			}else{
				 //如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
				 if("1".equals(entityMap.get("type_level"))){
					 if("1".equals(map.get("type_level").toString())){
							//将转换后的数据填充到通过sql语句查询出来的map中
						 map.putAll(typeToMoneymap);
						 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), map);
						 Map sumMoney= returnDataMap.get(map.get("sup_code")+","+map.get("bus_type_code"));
						//取出当前部门的金额填入到amount_money中
						 sumMoney.put("amount_money",sumMoney.get("money"));
						 sumMoney.put(map.get("mat_type_code"),map.get(map.get("mat_type_code")));
						 sumMoney.put("mat_type_code", map.get("mat_type_code"));
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
						 sumMoney.put(map.get("mat_type_code"),map.get(map.get("mat_type_code")));
						 sumMoney.put("mat_type_code", map.get("mat_type_code"));
						 returnDataMap.put((String) map.get("sup_code")+","+map.get("bus_type_code"), sumMoney);
					 }
				 }
				 
				
			}
		}
		for (String  j : returnDataMap.keySet()) {
			overloadinglist.add(returnDataMap.get(j));
		}
		return overloadinglist;
	}

	@Override
	public Map<String, Object> queryMatStorageQueryStockRoutingPrint(Map<String, Object> mapVo)
			throws DataAccessException {	
		
		try{
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatStockRoutingMapper matStockRoutingMapper = (MatStockRoutingMapper)context.getBean("matStockRoutingMapper");
			Map<String, Object> paraMap = new HashMap<String, Object>();
			
			mapVo.put("user_id", SessionManager.getUserId());    
			
			List<Map<String, Object>> list = matStockRoutingMapper.queryMatStorageQueryStockRoutingPrint(mapVo);	
			
			//主表只存了一个仓库名称
			Map<String,Object> map = new HashMap<String, Object>();;
			map.put("store_name", list.get(0).get("store_name"));
			
			paraMap.put("main", map);
			paraMap.put("detail", list);
			return paraMap;
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryInvBarRoutingPrint(Map<String, Object> mapVo)
			throws DataAccessException {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matInvBarRoutingMapper.queryMatStorageQueryInvBarRouting(mapVo);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryStockDetailPrint(Map<String, Object> mapVo)
			throws DataAccessException {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		mapVo.put("acc_year", mapVo.get("year"));
		mapVo.put("acc_month", mapVo.get("month"));
		
		
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(mapVo);
		AccYearMonth acc=new AccYearMonth();
		if(accYearMonth.size()>0){
			 acc=accYearMonth.get(0);
		}else{
			return new ArrayList();
		}
		
		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
				
		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
		mapVo.put("begin_date", DateUtil.dateToString(begin_date,"yyyy-MM-dd"));
		
		mapVo.put("end_date", DateUtil.dateToString(end_date,"yyyy-MM-dd"));
		List<Map<String, Object>> list = matStockDetailMapper.queryMatStorageQueryStockDetail(mapVo);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryMatInSupDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matInSupDetailMapper.queryMatStorageQueryMatInSupDetail(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryInDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
			List<Map<String, Object>> list=null;
			if (entityMap.get("show_detail")!=null && "1".equals(String.valueOf(entityMap.get("show_detail")))) {
				list = matInDetailMapper.queryMatStorageQueryInDetail(entityMap);
			}else{
				list=matInDetailMapper.queryMatStorageInInv(entityMap);
			}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryMatOutDeptPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		//查询时  登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if(entityMap.get("dept_id") == "" || entityMap.get("dept_id") == null){
			
			entityMap.put("dept_id", "00"); //当 dept_id == 00 时  根据用户 部门权限 限定 申请科室
		}
		
		if(entityMap.get("store_id") == "" || entityMap.get("store_id") == null){
			
			entityMap.put("store_id", "00"); //当 store_id == 00 时  根据用户 仓库权限 限定 响应仓库
		}
		List<Map<String, Object>> list = matOutDeptMapper.queryMatStorageQueryMatOutDept(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageQueryOutDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("user_id", SessionManager.getUserId());
				entityMap.put("show_history", MyConfig.getSysPara("03001"));
			
			
			List<Map<String, Object>> list=null;
			if (entityMap.get("show_detail")!=null && "1".equals(String.valueOf(entityMap.get("show_detail")))) {
				list = matOutDetailMapper.queryMatStorageQueryOutDetail(entityMap);
			}else{
				list=matOutDetailMapper.queryMatStorageOutInvCollection(entityMap);
			}
		return list;
			
	}
	
	@Override
	public List<Map<String, Object>> queryMatStorageQueryOutDetailPrintNew(Map<String, Object> entityMap)
			throws DataAccessException {
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("user_id", SessionManager.getUserId());
				entityMap.put("show_history", MyConfig.getSysPara("03001"));
			List<Map<String, Object>> list = matOutDeptMapper.queryMatStorageQueryMatOutDeptNew(entityMap);
			
			return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStorageInvCertDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matInDetailMapper.queryMatStorageInvCertDetail(entityMap);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatApplyCountPrint(Map<String, Object> entityMap) throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matInDetailMapper.queryMatApplyCount(entityMap);
		return list;
	}

	@Override
	public String queryMatInOutDetail(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = matWorkDetailMapper
					.queryMatInOutDetail(entityMap);

			return ChdJson.toJson(list);

		} else {

			/*RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<Map<String, Object>> list = matWorkDetailMapper
					.queryMatInOutDetail(entityMap, rowBounds);*/
			
			PageHelper.startPage(sysPage.getPage(),
					sysPage.getPagesize());
			List<Map<String, Object>> list = matWorkDetailMapper
					.queryMatInOutDetail(entityMap);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public List<Map<String, Object>> queryMatInOutDetailPrint(Map<String, Object> mapVo) {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId()); 
	
			List<Map<String, Object>> list = matWorkDetailMapper.queryMatInOutDetail(mapVo);

			return list;

	}
	
	@Override
	public String queryMatStoreInvBalanceDetail(Map<String, Object> entityMap) throws ParseException {
		//获取开始日期数组
		//如果没有startDate默认起始时间为1970年1月1日
        String startDate = (entityMap.get("startDate")==null 
        		|| StringUtils.isBlank(entityMap.get("startDate").toString()))?"1970-01-01":entityMap.get("startDate").toString();
        entityMap.put("startDate", startDate);
        String[] startDates=startDate.split("-");
        entityMap.put("year", startDates[0]);
        entityMap.put("month", startDates[1]);
        int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
        
        //获取开始日期的前一个年月信息,用于在结存表中查询  整月  期初信息
        //如果 preMonth 是1970年1月1日计算的结果 则将preMonth 设置为0000年00月 是mat_inv_balance 数据的总期初
		String preMonth = DateUtil.getPreData(startDates[0]+startDates[1]);
		preMonth = flag==0?"000000":preMonth;
		entityMap.put("preMonth", preMonth);
		//获取开始日期当前月的第一天 ,用于计算结存表中不保存的零散的
		String preDate = startDates[0]+"-"+startDates[1]+"-01";
		entityMap.put("preDate", preDate);
		
        SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = matWorkDetailMapper.queryMatStoreInvBalanceDetail(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<Map<String, Object>> list = matWorkDetailMapper.queryMatStoreInvBalanceDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public List<Map<String, Object>> queryMatStoreInvBalanceDetailPrint(Map<String, Object> entityMap) throws ParseException {
		//获取开始日期数组
		//如果没有startDate默认起始时间为1970年1月1日
        String startDate = (entityMap.get("startDate")==null 
        		|| StringUtils.isBlank(entityMap.get("startDate").toString()))?"1970-01-01":entityMap.get("startDate").toString();
        entityMap.put("startDate", startDate);
        String[] startDates=startDate.split("-");
        entityMap.put("year", startDates[0]);
        entityMap.put("month", startDates[1]);

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
        int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
        
        //获取开始日期的前一个年月信息,用于在结存表中查询  整月  期初信息
        //如果 preMonth 是1970年1月1日计算的结果 则将preMonth 设置为0000年00月 是mat_inv_balance 数据的总期初
		String preMonth = DateUtil.getPreData(startDates[0]+startDates[1]);
		preMonth = flag==0?"000000":preMonth;
		entityMap.put("preMonth", preMonth);
		//获取开始日期当前月的第一天 ,用于计算结存表中不保存的零散的
		String preDate = startDates[0]+"-"+startDates[1]+"-01";
		entityMap.put("preDate", preDate);
		
		List<Map<String, Object>> list = matWorkDetailMapper.queryMatStoreInvBalanceDetail(entityMap);
		
		return list;
	}
	/**
	 * 材料出入库明细查询
	 * @throws ParseException 
	 */
	@Override
	public String queryMatStoreInvInOutDetail(Map<String, Object> entityMap) throws ParseException {
		//获取开始日期数组
		//如果没有startDate默认起始时间为1970年1月1日
        String startDate = (entityMap.get("startDate")==null 
        		|| StringUtils.isBlank(entityMap.get("startDate").toString()))?"1970-01-01":entityMap.get("startDate").toString();
        entityMap.put("startDate", startDate);
        String[] startDates=startDate.split("-");
        entityMap.put("year", startDates[0]);
        entityMap.put("month", startDates[1]);
        int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
        
        //获取开始日期的前一个年月信息,用于在结存表中查询  整月  期初信息
        //如果 preMonth 是1970年1月1日计算的结果 则将preMonth 设置为0000年00月 是mat_inv_balance 数据的总期初
		String preMonth = DateUtil.getPreData(startDates[0]+startDates[1]);
		preMonth = flag==0?"000000":preMonth;
		entityMap.put("preMonth", preMonth);
		//获取开始日期当前月的第一天 ,用于计算结存表中不保存的零散的
		String preDate = startDates[0]+"-"+startDates[1]+"-01";
		entityMap.put("preDate", preDate);
		
		List<Map<String, Object>> list = matWorkDetailMapper.queryMatStoreInvInOutDetail(entityMap);
		//sql查询的list中没有发生入库之后的总的库存,所以增加个库存
		List<Map<String, Object>> balanceList=new ArrayList<Map<String, Object>>();
		double balance_amount=0;
		double balance_amount_money=0;
		for (Map<String, Object> map : list) {
			if (map.get("flag")!=null && "init".equals(map.get("flag").toString() )) {
				if(map.get("amount")!=null && StringUtils.isNotBlank(map.get("amount").toString())){
					balance_amount+=Double.parseDouble(map.get("amount").toString());
				}
				if(map.get("amount_money")!=null && StringUtils.isNotBlank(map.get("amount_money").toString())){
					balance_amount_money+=Double.parseDouble(map.get("amount_money").toString());
				}
			}else if (map.get("flag")!=null && "in".equals(map.get("flag").toString() )) {
				if(map.get("amount")!=null && StringUtils.isNotBlank(map.get("amount").toString())){
					balance_amount+=Double.parseDouble(map.get("amount").toString());
				}
				if(map.get("amount_money")!=null && StringUtils.isNotBlank(map.get("amount_money").toString())){
					balance_amount_money+=Double.parseDouble(map.get("amount_money").toString());
				}
			}else if (map.get("flag")!=null && "out".equals(map.get("flag").toString())) {
				if(map.get("amount")!=null && StringUtils.isNotBlank(map.get("amount").toString())){
					balance_amount-=Double.parseDouble(map.get("amount").toString());
				}
				if(map.get("amount_money")!=null && StringUtils.isNotBlank(map.get("amount_money").toString())){
					balance_amount_money-=Double.parseDouble(map.get("amount_money").toString());
				}
			}
			map.put("balance_amount", balance_amount);
			map.put("balance_amount_money", balance_amount_money);
			
			balanceList.add(map);
		}
		return ChdJson.toJson(balanceList);
	}
	/**
	 * 根据id查询store的基本信息
	 */
	@Override
	public String queryStoreMsg(Map<String, Object> entityMap) {
		Map<String, Object> map = matWorkDetailMapper.queryStoreMsg(entityMap);
		return ChdJson.toJson(map);
	}
	/**
	 * 更具inv_id查询材料的信息
	 */
	@Override
	public String queryInvMsg(Map<String, Object> entityMap) {
		Map<String, Object> map = matWorkDetailMapper.queryInvMsg(entityMap);
		return ChdJson.toJson(map);
	}

	@Override
	public String queryOccurMatTypeDictForVariableHead(Map<String, Object> mapVo) {
		return JSON.toJSONString(matInSupCountMapper.queryOccurMatTypeDictForVariableHead(mapVo));
	}
}
