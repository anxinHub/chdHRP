/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.tell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.ReadFiles;
import com.chd.hrp.acc.dao.tell.AccBankCheckMapper;
import com.chd.hrp.acc.dao.tell.AccTellDayMapper;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccTellDay;
import com.chd.hrp.acc.service.tell.AccBankCheckService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 单位未达账<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBankCheckService")
public class AccBankCheckServiceImpl implements AccBankCheckService {

	private static Logger logger = Logger.getLogger(AccBankCheckServiceImpl.class);
	
	@Resource(name = "accBankCheckMapper")
	private final AccBankCheckMapper accBankCheckMapper = null;
	
	@Resource(name="modStartMapper")
	private final ModStartMapper modStartMapper=null;
	
	@Resource(name = "accTellDayMapper")
	private final AccTellDayMapper accTellDayMapper = null;
    
	/**
	 * @Description 
	 * 单位未达账<BR> 添加AccBankCheck
	 * @param AccBankCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBankCheck(Map<String,Object> entityMap)throws DataAccessException{
		
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		if(modStart == null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}else if(modStart.getStart_year()==null && modStart.getStart_month()==null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}
		
		if(entityMap.get("occur_date") == null || "".equals(entityMap.get("occur_date"))){
			return "{\"error\":\"业务日期不能为空.\"}";
		}
		
		int yearMonth = Integer.parseInt(modStart.getStart_year()+modStart.getStart_month());
		int occuDate= Integer.parseInt(entityMap.get("occur_date").toString().substring(0, 7).replace("-",""));
		if(occuDate>yearMonth){
			return "{\"error\":\"业务日期必须小于出纳账管理系统启用时间.\"}";
		}
		
		try {
			//20190224 hsy应申哲要求修改初始账年度为出纳系统的启用年度
			entityMap.put("acc_year", modStart.getStart_year());
			
			if(entityMap.get("debit")==null || "".equals(entityMap.get("debit"))){
				
				entityMap.remove("debit");
				
				entityMap.put("debit", 0);
			}
			
			if(entityMap.get("credit")==null || "".equals(entityMap.get("credit"))){
				
				entityMap.remove("credit");
				
				entityMap.put("credit", 0);
			}
			
			entityMap.put("is_init", "1");
			accBankCheckMapper.addAccBankCheck(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBankCheck\"}";

		}

	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 批量添加AccBankCheck
	 * @param  AccBankCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccBankCheck(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accBankCheckMapper.addBatchAccBankCheck(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccBankCheck\"}";

		}
	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 查询AccBankCheck分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBankCheck(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccBankCheck> list = accBankCheckMapper.queryAccBankCheckAndSum(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 打印queryAccBankCheckPrint
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>>  queryAccBankCheckAndSumPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("is_init", "1");
		entityMap.put("acc_year", null);
		
		List<Map<String, Object>> list = accBankCheckMapper.queryAccBankCheckAndSumPrint(entityMap);
		
		return list;
	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 查询AccBankCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccBankCheck queryAccBankCheckByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accBankCheckMapper.queryAccBankCheckByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 批量删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBankCheck(List<Map<String,Object>> entityList)throws DataAccessException{

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
	 * 单位未达账<BR> 删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccBankCheck(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accBankCheckMapper.deleteAccBankCheck(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccBankCheck\"}";

		}
    }
	
	/**
	 * @Description 
	 * 单位未达账<BR> 更新AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBankCheck(Map<String,Object> entityMap)throws DataAccessException{
		
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		if(modStart == null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}else if(modStart.getStart_year()==null && modStart.getStart_month()==null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}
		
		if(entityMap.get("occur_date") == null || "".equals(entityMap.get("occur_date"))){
			return "{\"error\":\"业务日期不能为空.\"}";
		}
		
		int yearMonth = Integer.parseInt(modStart.getStart_year()+modStart.getStart_month());
		int occuDate= Integer.parseInt(entityMap.get("occur_date").toString().substring(0, 7).replace("-",""));
		if(occuDate>yearMonth){
			return "{\"error\":\"业务日期必须小于出纳账管理系统启用时间.\"}";
		}

		try {
			
			if(entityMap.get("debit")==null || "".equals(entityMap.get("debit"))){
				
				entityMap.remove("debit");
				
				entityMap.put("debit", 0);
			}
			
			if(entityMap.get("credit")==null || "".equals(entityMap.get("credit"))){
				
				entityMap.remove("credit");
				
				entityMap.put("credit", 0);
			}

			accBankCheckMapper.updateAccBankCheck(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankCheck\"}";

		}
	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 批量更新AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccBankCheck(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accBankCheckMapper.updateBatchAccBankCheck(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankCheck\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 单位未达账<BR> 导入AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> importAccBankCheck(Map<String,Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			//用于查询的Map
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("group_id", SessionManager.getGroupId());
			queryMap.put("hos_id", SessionManager.getHosId());
			queryMap.put("copy_code", SessionManager.getCopyCode());
			queryMap.put("acc_year", MyConfig.getCurAccYear());
			queryMap.put("user_id", SessionManager.getUserId());
			
			//获取科目字典
			List<Map<String, Object>> subjList = accBankCheckMapper.queryAccSubjForImpl(queryMap);
			Map<String, Object> subjMap = new HashMap<String, Object>();
			if(subjList.size() > 0){
				for(Map<String, Object> tmp : subjList){
					subjMap.put(tmp.get("SUBJ_CODE").toString(), tmp.get("SUBJ_NAME"));
				}
			}else{
				retMap.put("state", "false");
				retMap.put("error", "请先维护本年会计科目！");
				return retMap;
			}
			
			//获取结算方式字典
			List<Map<String, Object>> payTypejList = accBankCheckMapper.queryAccPayTypeForImpl(queryMap);
			Map<String, Object> payTypeMap = new HashMap<String, Object>();
			if(payTypejList.size() > 0){
				for(Map<String, Object> tmp : payTypejList){
					payTypeMap.put(tmp.get("PAY_CODE").toString(), tmp.get("PAY_NAME"));
				}
			}
			
			//导入数据
			List<Map<String, List<String>>> dataList = ReadFiles.readFiles(entityMap);
			if(dataList == null || dataList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "未获得导入数据，请稍后再试！");
				return retMap;
			}

			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> map = null;
			List<String> rowList = null;
			Double debit = 0.0;
			Double credit = 0.0;
			int yearMonth = Integer.parseInt(MyConfig.getCurAccYearMonth());
			String occur_date = null;
			for(Map<String, List<String>> dataMap : dataList){
				map = new HashMap<String, Object>();
				/**会计科目校验******begin*****/
				rowList = dataMap.get("subj_code");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，科目编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!subjMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，会计科目不存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				map.put("subj_code", rowList.get(1));
				/**会计科目校验******end*******/

				/**摘要校验******begin*****/
				rowList = dataMap.get("summary");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					/*retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，摘要不能为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;*/
					map.put("summary", null);
				}else{
					map.put("summary", rowList.get(1));
				}
				/**摘要校验******end*******/
				
				/**借方金额校验******begin*****/
				rowList = dataMap.get("debit");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					debit = 0.0;
					map.put("debit", 0.0);
				}else{
					debit = Double.parseDouble(rowList.get(1));
					map.put("debit", debit);
				}
				map.put("debit_w", 0.0);
				/**借方金额校验******end*******/

				/**贷方金额校验******begin*****/
				rowList = dataMap.get("credit");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					if(debit == 0){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，借贷方金额不能同时为空！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					credit = 0.0;
					map.put("credit", 0.0);
				}else{
					credit = Double.parseDouble(rowList.get(1));
					if((credit > 0 && debit > 0) || (debit == 0 && credit == 0)){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，借贷方金额必须只填写一个！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					map.put("credit", credit);
				}
				map.put("credit_w", 0.0);
				/**贷方金额校验******end*******/
				
				/**余额校验******begin*****/
				rowList = dataMap.get("bal");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("bal", 0.0);
				}else{
					map.put("bal", Double.parseDouble(rowList.get(1)));
				}
				map.put("bal_w", 0.0);
				/**余额校验******end*******/

				/**票据号校验******begin*****/
				rowList = dataMap.get("check_no");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("check_no", null);
				}else{
					map.put("check_no", rowList.get(1));
				}
				/**票据号校验******end*******/

				/**单据号校验******begin*****/
				rowList = dataMap.get("business_no");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("business_no", null);
				}else{
					map.put("business_no", rowList.get(1));
				}
				/**单据号校验******end*******/

				/**发生日期校验******begin*****/
				rowList = dataMap.get("occur_date");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，发生日期不能为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}else{
					occur_date = rowList.get(1);
					//校验格式
					if(occur_date.split("-").length != 3){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，日期格式应为文本型的【年-月-日】！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					if(Integer.parseInt(occur_date.substring(0, 7).replace("-", "")) < yearMonth){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，发生日期所在期间已结账！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					map.put("occur_date", rowList.get(1));
				}
				/**发生日期校验******end*******/

				/**结算方式校验******begin*****/
				rowList = dataMap.get("pay_type_code");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("pay_type_code", null);
				}else{
					if(!payTypeMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，结算方式不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					map.put("pay_type_code", rowList.get(1));
				}
				/**结算方式校验******end*******/
				
				//添加默认值
				map.put("group_id", queryMap.get("group_id"));
				map.put("hos_id", queryMap.get("hos_id"));
				map.put("copy_code", queryMap.get("copy_code"));
				map.put("acc_year", queryMap.get("acc_year"));
				map.put("is_check", 0);
				map.put("check_user", null);
				map.put("check_date", null);
				map.put("is_init", entityMap.get("is_init"));
				map.put("is_import", entityMap.get("is_import"));
				
				list.add(map);
			}
			
			//添加
			accBankCheckMapper.addBatchAccBankCheck(list);
			
			retMap.put("state", "true");
			retMap.put("msg", "导入成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage() == null ? "操作失败！" : e.getMessage());
		}
		return retMap;
	}

	@Override
	public String queryAccTellDayByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<AccTellDay> list = accTellDayMapper.queryAccTellDay(entityMap);
		
		if(list.size() > 0 ){
			return "{\"name\":\"1\"}";
		}
		return "{\"name\":\"0\"}";
	}

	@Override
	public String addAccBankCheckInstall(Map<String, Object> entityMap)
			throws DataAccessException {
			
		try {
			if(entityMap.get("subj_code")==null || entityMap.get("subj_code").toString().equals("")){
				return "{\"error\":\"科目不能为空！\",\"state\":\"false\"}";
			}
			
			accBankCheckMapper.saveAccBankBalInit(entityMap);
			return "{\"msg\":\"保存成功！\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}
	}


	@Override
	public String queryAccBankCheckCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		AccBankCheck accBankCheck = accBankCheckMapper.queryAccBankCheckCode(entityMap);
		
		if(accBankCheck != null){
			
			return "{\"money\":\""+accBankCheck.getBal()+"\",\"bank_id\":\""+accBankCheck.getBank_id()+"\"}";

		}
		
		return "{\"money\":\"-1\",\"bank_id\":\"null\"}";
	}

}
