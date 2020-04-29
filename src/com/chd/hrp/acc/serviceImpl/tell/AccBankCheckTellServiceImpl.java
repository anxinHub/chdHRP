package com.chd.hrp.acc.serviceImpl.tell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.tell.AccBankCheckMapper;
import com.chd.hrp.acc.dao.tell.AccBankCheckTellMapper;
import com.chd.hrp.acc.entity.AccBankCheckTell;
import com.chd.hrp.acc.service.tell.AccBankCheckTellService;
import com.github.pagehelper.PageInfo;

@Service("accBankCheckTellService")
public class AccBankCheckTellServiceImpl implements AccBankCheckTellService {
	
	private static Logger logger = Logger.getLogger(AccBankCheckServiceImpl.class);
	
	@Resource(name = "accBankCheckTellMapper")
	private final AccBankCheckTellMapper accBankCheckTellMapper = null;
	
	@Resource(name = "accBankCheckMapper")
	private final AccBankCheckMapper accBankCheckMapper = null;

	@Autowired
	private DataSourceTransactionManager txManager;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccBankCheckTell(Map<String, Object> entityMap) throws DataAccessException {
	/*	if(entityMap.get("page")==null){
			entityMap.put("page", 1);
			entityMap.put("pagesize", 99999999);
		}*/
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBankCheckTellMapper.queryAccBankCheckTell(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBankCheckTell(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccBankCheckTell> list = accBankCheckTellMapper.queryAccBankCheckTell(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccBankCheckTell> list = accBankCheckTellMapper.queryAccBankCheckTell(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 添加AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBankCheckTell(Map<String, Object> entityMap) throws DataAccessException {
			if(entityMap.get("debit") == null || "".equals(entityMap.get("debit"))){
				entityMap.put("debit", "0");
			}
			
			if(entityMap.get("credit") == null || "".equals(entityMap.get("credit"))){
				entityMap.put("credit", "0");
			}
			
			if(entityMap.get("summary") == null || "".equals(entityMap.get("summary"))){
				entityMap.put("summary", " ");
			}
			
			if(entityMap.get("pay_type_code") == null || "".equals(entityMap.get("pay_type_code"))){
				entityMap.put("pay_type_code", " ");
			}
			
			if(entityMap.get("check_no") == null || "".equals(entityMap.get("check_no"))){
				entityMap.put("check_no", " ");
			}
			entityMap.put("is_init", "0");
			
		try {
			accBankCheckMapper.addAccBankCheck(entityMap);
			return "{\"\":\"添加成功.\",\"state\":\"true\",\"id\":\""+entityMap.get("bank_id")+"\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBankCheckTell\"}";
		}
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBankCheckTell(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accBankCheckMapper.deleteBatchAccBankCheck(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBankCheck\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 修改AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBankCheckTell(Map<String, Object> entityMap) throws DataAccessException {
		
		if("".equals(entityMap.get("debit"))){
			
			entityMap.put("debit", "0");
		}
		
		if("".equals(entityMap.get("credit"))){
			
			entityMap.put("credit", "0");
		}
		
		if("".equals(entityMap.get("summary"))){
			
			entityMap.put("summary", " ");
		}
		
		if("".equals(entityMap.get("pay_type_code"))){
			
			entityMap.put("pay_type_code", "");
		}
		
		if("".equals(entityMap.get("check_no"))){
			
			entityMap.put("check_no", " ");
		}
		
		if(entityMap.get("check_user") == null || "".equals(entityMap.get("check_user"))){
			
			entityMap.put("check_user", 0);
		}
		
		try {
			

			accBankCheckMapper.updateAccBankCheck(entityMap);
			
			return "{\"\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankCheckTell\"}";
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectqueryAccBankCheckTellPrint(Map<String, Object> entityMap) throws DataAccessException {

		/*	entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0,4));
		
			List<Map<String, Object>> list = accBankCheckTellMapper.queryAccBankCheckTellPrint(entityMap);
			
			return list;*/
		if(entityMap.get("group_id") == null){
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
			}
			if(entityMap.get("hos_id") == null){
				
				entityMap.put("hos_id", SessionManager.getHosId());
			
			}
			if(entityMap.get("copy_code") == null){
				
				entityMap.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
		entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0,4));
		if( entityMap.get("is_check").toString().equals("1")){
			entityMap.put("is_check","1");
		}else{
			entityMap.put("is_check","0");
		}
		entityMap.put("page", 1);
		entityMap.put("pagesize", 99999999);
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBankCheckTellMapper.queryAccBankCheckTell(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return (List<Map<String,Object>>)entityMap.get("rst");
	}
	@Override
	public String addAccBankCheckTellBatch(List<Map<String, Object>> saveList) {
		String msg=null;
		try {
			
   if(saveList.size()>0){
	   
	for (Map<String, Object> entityMap : saveList) {
		if(entityMap.get("debit") == null || "".equals(entityMap.get("debit"))){
			entityMap.put("debit", "0");
		}
		
		if(entityMap.get("credit") == null || "".equals(entityMap.get("credit"))){
			entityMap.put("credit", "0");
		}
		
		if(entityMap.get("summary") == null || "".equals(entityMap.get("summary"))){
			entityMap.put("summary", " ");
		}
		
		if(entityMap.get("pay_type_code") == null || "".equals(entityMap.get("pay_type_code"))){
			entityMap.put("pay_type_code", " ");
		}
		
		if(entityMap.get("check_no") == null || "".equals(entityMap.get("check_no"))){
			entityMap.put("check_no", " ");
		}
		entityMap.put("is_init", "0");
		
	
		accBankCheckMapper.addAccBankCheck(entityMap);
		//return "{\"\":\"添加成功.\",\"state\":\"true\",\"id\":\""+entityMap.get("bank_id")+"\"}";
		 msg= "{\"\":\"添加成功.\",\"state\":\"true\"}";
	}
	
}
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg=  "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBankCheckTell\"}";
		}
	
		return msg;
		
	}
	

}
