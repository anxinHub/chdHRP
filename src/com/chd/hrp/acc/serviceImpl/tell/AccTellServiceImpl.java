/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.tell;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.books.auxiliaryaccount.AccStoreAuxiliaryAccountMapper;
import com.chd.hrp.acc.dao.tell.AccTellMapper;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.service.tell.AccTellService;
import com.chd.hrp.ass.dao.bill.back.AssBackBillMainMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.github.pagehelper.PageInfo;
 
/**
* @Title. @Description.     
* 出纳账<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
  

@Service("accTellService")
public class AccTellServiceImpl implements AccTellService {

	private static Logger logger = Logger.getLogger(AccTellServiceImpl.class);
	
	@Resource(name = "accTellMapper")
	private final AccTellMapper accTellMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "accStoreAuxiliaryAccountMapper")
	private final AccStoreAuxiliaryAccountMapper accStoreAuxiliaryAccountMapper = null;
	
    
	/**
	 * @Description 
	 * 出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccTell(Map<String,Object> entityMap)throws DataAccessException{
		
		AccTell accTell = queryAccTellByCode(entityMap);

		if (accTell != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accTellMapper.addAccTell(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccTell\"}";

		}

	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量添加AccTell
	 * @param  AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccTell(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accTellMapper.addBatchAccTell(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccTell\"}";

		}
	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTell分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccTell(Map<String,Object> entityMap) throws DataAccessException{
		
		StringBuffer direct = new StringBuffer();
		if(entityMap.get("subj_dire").equals("0")){
			direct.append(" and (nvl(att.debit,0) > 0)");
		}else{
			direct.append(" and (nvl(att.credit,0) > 0)");
		}
		
		entityMap.put("direct", direct.toString());
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccTell> list = accTellMapper.queryAccTell(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTellByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccTell queryAccTellByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accTellMapper.queryAccTellByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量删除AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccTell(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accTellMapper.deleteBatchAccTell(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTell\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 删除AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccTell(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accTellMapper.deleteAccTell(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccTell\"}";

		}
    }
	
	/**
	 * @Description 
	 * 出纳账<BR> 更新AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccTell(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accTellMapper.updateAccTell(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTell\"}";

		}
	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量更新AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccTell(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accTellMapper.updateBatchAccTell(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTell\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 出纳账<BR> 导入AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccTell(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryCashAccount(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccTell> list = accTellMapper.queryCashAccount(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}

	@Override
	@Transactional
	public String collectAccCheck(Map<String, Object> entityMap) throws DataAccessException {
		accTellMapper.queryAccCheck(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJson((List<AccTell>) entityMap.get("objdata"));
	}
	
	/**
	*现金出纳账<BR>
	*查询
	*/
	@Override
	public String queryCashAccountByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0, 4));
		//查询系统启用时间
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		
		entityMap.put("start_year",modStart.getStart_year());
		
		entityMap.put("start_month",modStart.getStart_month());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccTell> list = accTellMapper.queryCashAccountByCode(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccTell> list = accTellMapper.queryCashAccountByCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	*现金出纳账<BR>
	*打印
	*/
	@Override
	public String queryAccCashAccountPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
	
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
	
		entityMap.put("mod_code", "0101");
		
		entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0, 4));
		//查询系统启用时间
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		entityMap.put("start_year",modStart.getStart_year());
		entityMap.put("start_month",modStart.getStart_month());

		List<AccTell> list = accTellMapper.queryCashAccountByCode(entityMap);
		
		return ChdJson.toJson(list);
		
		
	}

	/**
	*银行出纳账<BR>
	*打印
	*/
	@Override
	public String queryAccBankByAccountPrint(Map<String, Object> entityMap)
			throws DataAccessException {
	      entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0, 4));
		
			
			List<AccTell> list = accTellMapper.queryCashAccountByCode(entityMap);
			
			return ChdJson.toJson(list);
	}
	
	
	@Override
	public String queryAccTellSummary(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		//entityMap.put("summary",entityMap.get("summary").toString().toUpperCase()); 
		
		if (sysPage.getTotal()==-1){
			
			List<AccTell> list = accTellMapper.queryAccTellSummary(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccTell> list = accTellMapper.queryAccTellSummary(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String addBatchAccTellSummary(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {
				
				accTellMapper.addBatchAccTellSummary(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccTell\"}";
	
			}
	}

	@Override
	public String addAccTellSummary(Map<String, Object> entityMap)
			throws DataAccessException {

		int count = accTellMapper.SearchAccTellSummaryByExists(entityMap);
		
		if(count > 0 ){
			
			return "{\"error\":\"添加失败 ,该摘要已经存在\"}";
		}
		
		try {
			
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("summary").toString()));
			
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("summary").toString()));
			
				accTellMapper.addAccTellSummary(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccTell\"}";
	
			}
	}

	@Override
	public String deleteBatchAccTellSummary(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			int state = accTellMapper.deleteBatchAccTellSummary(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTell\"}";
	
		}
	}

	@Override
	public String updateAccTellSummary(Map<String, Object> entityMap)
			throws DataAccessException {
		
		int count = accTellMapper.SearchAccTellSummaryByExists(entityMap);
		
		if(count > 0 ){
			
			return "{\"error\":\"更新失败 ,该摘要已经存在\"}";
		}
		
		try { 
			 
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("summary").toString()));
			
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("summary").toString()));
			
			int state = accTellMapper.updateAccTellSummary(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTell\"}";
	
		}
	}

	@Override
	public String importAccTellSummary(Map<String, Object> mapVo) {
		
		try {
			
			List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			
			//String reason = "" ;
			
			if (list.size()==0 || list==null) {
				return	"{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			
			for (Map<String, List<String>> map : list) {
				if(map.get("summary").get(1)==null || "".equals(map.get("summary").get(1))){
					return  "{\"msg\":\""+map.get("summary").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("summary").get(0)+"\"}";

				}
				 
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				
				mapVo1.put("user_id", SessionManager.getUserId());
				
				mapVo1.put("summary", map.get("summary").get(1)); 
				
				//判断摘要是否存在重复
				Map<String, Object> map_summary = new HashMap<String, Object>();
				map_summary.put("group_id", mapVo1.get("group_id"));
				map_summary.put("hos_id", mapVo1.get("hos_id"));
				map_summary.put("copy_code", mapVo1.get("copy_code"));
				map_summary.put("user_id", SessionManager.getUserId());
				map_summary.put("summary", mapVo1.get("summary")); 
				
				AccTell data_exc_extis_name = accTellMapper.SearchAccTellSummaryByCode(map_summary);

				if (data_exc_extis_name != null) {
					
					return  "{\"warn\":\"" +  map.get("summary").get(0) + ",摘要已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+ map.get("summary").get(0) + "\"}"; 

				}
				
				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("summary").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("summary").toString()));
				
				maplist.add(mapVo1);
			} 
				accTellMapper.addBatchAccTellSummary(maplist);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTell\"}";
		}
	}
	
	@Override
	public List<Map<String, Object>> queryCashAccountPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("mod_code", "01");
		
		entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0, 4));
		//查询系统启用时间
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		
		entityMap.put("start_year",modStart.getStart_year());
		
		entityMap.put("start_month",modStart.getStart_month());
			
		List<Map<String, Object>> list = accTellMapper.queryCashAccountPrint(entityMap);
		
		return list;
		
	}
	
	@Override
	@Transactional
	  public List<Map<String, Object>> collectAccCheckPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("tell_flag", 0);
		 
		entityMap.put("acct_flag", 0);
		
		accTellMapper.queryAccCheckPrint(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return (List<Map<String, Object>>)entityMap.get("objdata");
		
	    }
	
	
	/**
	 * @Description 退预付款发票打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAccBankByAccountForTemplatePrint(Map<String, Object> map) throws DataAccessException {

		try {

			if (map.get("group_id") == null) {
				map.put("group_id", SessionManager.getGroupId());
			}
			if (map.get("hos_id") == null) {
				map.put("hos_id", SessionManager.getHosId());
			}
			if (map.get("copy_code") == null) {
				map.put("copy_code", SessionManager.getCopyCode());
			}
			if (map.get("acc_year") == null) {
				map.put("acc_year", map.get("begin_date").toString().substring(0, 4));
			}
			map.put("mod_code", "0101");
			
			Map<String, Object> reMap = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AccTellMapper accTellMapper = (AccTellMapper) context.getBean("accTellMapper");
			Map<String, Object> mainMap=new HashMap<String, Object>();
			mainMap.put("ID", "1");
			mainMap.put("begin_date", map.get("begin_date"));
			mainMap.put("end_date", map.get("end_date"));
			mainMap.put("subj_code", map.get("subj_code"));
			mainMap.put("nature_code", map.get("nature_code"));
			if ("1".equals(String.valueOf(map.get("p_num")))) {
				// 预退款发票打印模板主表
				List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();
				mainList.add(mainMap);
				// 预退款发票打印模板从表
				List<Map<String, Object>> detailList = accTellMapper.queryCashAccountByCodeForTemplatePrint(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			} else {
				Map<String, Object> mainList = mainMap;
				// 预退款发票打印模板从表
				List<Map<String, Object>> detailList = accTellMapper.queryCashAccountByCodeForTemplatePrint(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}

			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	/**
	 * 总账对账查询
	 */
	@Override

	public String queryAllCheckQuery(Map<String, Object> entityMap) {
		/*
		List<Map<String, Object>> subjCode=accTellMapper.querSubjCode(entityMap);
		StringBuffer subj=new StringBuffer();
		for (Map<String, Object> map : subjCode) {
			subj.append(map.get("SUBJ_CODE")+",");
		}
		entityMap.put("subj_code", subj.substring(0, subj.length()-1));*/
		StringBuffer stateSql = new StringBuffer();
		StringBuffer gltSql = new StringBuffer();
		entityMap.put("acc_year_b", entityMap.get("begin_date").toString().substring(0, 4));
		entityMap.put("acc_year_e", entityMap.get("end_date").toString().substring(0, 4));
		entityMap.put("acc_month_b", entityMap.get("begin_date").toString().substring(5, 7));
		entityMap.put("acc_month_e", entityMap.get("end_date").toString().substring(5, 7));
		if(entityMap.get("end_date").toString().equals(entityMap.get("begin_date").toString())){
			stateSql.append("between "+entityMap.get("begin_date").toString().substring(5, 7)+" and " +entityMap.get("end_date").toString().substring(5, 7));
			entityMap.put("stateSql", stateSql);
		}
		if(!entityMap.get("end_date").toString().equals(entityMap.get("begin_date").toString())){
			gltSql.append(">= "+ entityMap.get("begin_date").toString().substring(5, 7));
			entityMap.put("gltSql", gltSql);
		}
		else{
			
			entityMap.put("gltSql", gltSql);
		}
	
		
		List<Map<String,Object>> list=accTellMapper.queryAllCheckQuery(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public List<Map<String, Object>> queryAllCheckQueryPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		
//		List<Map<String, Object>> subjCode=accTellMapper.querSubjCode(entityMap);
//		StringBuffer subj=new StringBuffer();
//		for (Map<String, Object> map : subjCode) {
//			subj.append(map.get("SUBJ_CODE")+",");
//		}
//		entityMap.put("subj_code", subj.substring(0, subj.length()-1));
		entityMap.put("mod_code", "0101");
		entityMap.put("acc_year_b", entityMap.get("begin_date").toString().substring(0, 4));
		entityMap.put("acc_year_e", entityMap.get("end_date").toString().substring(0, 4));
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			
			entityMap.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
			
			entityMap.put("end_yearMonth", entityMap.get("begin_date").toString());
			entityMap.put("mod_code", "01");
		List<Map<String,Object>> list=accTellMapper.queryAllCheckQuery(entityMap);
		
		return list;
	}
}
