/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wage.AccWageCalMapper;
import com.chd.hrp.acc.dao.wage.AccWageEmpMapper;
import com.chd.hrp.acc.dao.wage.AccWageMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageCarryOverMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageItemCalMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageLogMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeKindMapper;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccWageCal;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.service.wagedata.AccWagePayService;
import com.github.pagehelper.PageInfo;
 
@Service("accWagePayService")
public class AccWagePayServiceImpl implements AccWagePayService {

	private static Logger logger = Logger.getLogger(AccWagePayServiceImpl.class);
	
	public  String cal_english="";
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
	
	@Resource(name = "accWageItemCalMapper")
	private final AccWageItemCalMapper accWageItemCalMapper = null;
	
	@Resource(name = "accWageLogMapper")
	private final AccWageLogMapper accWageLogMapper = null;
	
	@Resource(name = "accWageSchemeKindMapper")
	private final AccWageSchemeKindMapper accWageSchemeKindMapper = null;
	
	@Resource(name = "accWageCalMapper")
	private final AccWageCalMapper accWageCalMapper = null;
    
	@Resource(name = "accWageMapper")
	private final AccWageMapper accWageMapper = null;
	
	@Resource(name = "accWageCarryOverMapper")
	private final AccWageCarryOverMapper accWageCarryOverMapper = null;
	
	@Resource(name = "accWageEmpMapper")
	private final AccWageEmpMapper accWageEmpMapper = null;
	
	/**
	 * @Description 
	 * 工资数据<BR> 添加AccWagePay
	 * @param AccWagePay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWagePay(Map<String,Object> entityMap)throws DataAccessException{
		
		StringBuffer sbf = new StringBuffer();
		
		try {
			
			String msg =queryAccWagePerm(entityMap);
			
			if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			String [] res = entityMap.get("item").toString().split(";");
			
			AccWagePay accWagePay = queryAccWagePayByCode(entityMap);

			if (accWagePay != null) {
				
				String column_item = entityMap.get("item_column").toString();
				
				String [] column = column_item.substring(1, column_item.length()).split(",");
				
				for (int i = 0; i < res.length; i++) {
					
					sbf.append(column[i]+"="+res[i]+",");
					
				}
				
				entityMap.put("sqlValue", sbf.substring(0, sbf.length()-1));
				
				entityMap.put("note", "");
				
				accWagePayMapper.updateAccWagePay(entityMap);

			}else{
				
				String sql ="";
				
				for (int i = 0; i < res.length; i++) {
					
					sql+=","+res[i];
					
				}
				
				entityMap.put("sql", sql);
				
				accWagePayMapper.deleteAccWagePay(entityMap);
				
				accWagePayMapper.addAccWagePay(entityMap);
				
			}
			
			if(entityMap.containsKey("add_type")){
				
				return  "{\"mesg\":\"保存成功\"}";
			}
			
			return  "{\"msg\":\"保存成功\", \"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWagePay\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量添加AccWagePay
	 * @param  AccWagePay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWagePay(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWagePayMapper.addBatchAccWagePay(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWagePay\"}";

		}
	}
	
	/**
	 * @Description 工资数据<BR>
	 *              查询AccWagePay分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccWagePay(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accWagePayMapper.queryAccWagePay(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accWagePayMapper.queryAccWagePay(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/*工资录入页面打印*/
	public List<Map<String,Object>> queryAccWagePayPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		if(entityMap.get("emp_code") != null){
			//用来转换大写
			entityMap.put("emp_code",entityMap.get("emp_code").toString().toUpperCase());
		}
		
		List<Map<String,Object>> list = accWagePayMapper.queryAccWagePay(entityMap);
		
		return list;
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePayByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePay queryAccWagePayByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWagePayMapper.queryAccWagePayByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量删除AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWagePay(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

			String msg =queryAccWagePerm(entityList.get(0));
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			int state = accWagePayMapper.deleteBatchAccWagePay(entityList);
			
			accWageEmpMapper.deleteBatchAccWageEmp(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("删除失败："+e.getMessage());

		}
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 删除AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWagePay(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			String msg =queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			accWagePayMapper.deleteAccWagePay(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWagePay\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWagePay(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			String msg =queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			accWagePayMapper.updateAccWagePay(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWagePay\"}";

		}
	}
	
	/**
	 * @Description 工资数据<BR>
	 *              批量更新AccWagePay
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String collectBatchAccWagePay(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
//		List<AccSubj> list = new ArrayList<AccSubj>();
		String prm_ErrTxt = "";
		try {
			
			/*1、提取工资项的计算公式
			List<Map<String,Object>> list =accWagePayMapper.queryAccWageItemPayOfCalName(entityMap);
			
			查出所有职工分类和工资套
			List<AccWageItemCal> itemCalList = accWageItemCalMapper.queryAccWageItemCalByCollect(entityMap);
			
			collectWageItem(list,itemCalList,entityMap);*/
			
			String msg = queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
			}else */
			
			if ("1".equals(msg)) {
				return "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
			}
			
			accWagePayMapper.collectAccWagePay(entityMap);
			String prm_AppCode = entityMap.get("prm_AppCode").toString();
			if ("0".equals(prm_AppCode)) {
				// 计算成功！提交事务
				transactionManager.commit(status);
				return "{\"msg_text\":\"计算成功.\",\"is_ok\":\"" + prm_AppCode + "\"}";
			} else if ("-1".equals(prm_AppCode) || "100".equals(prm_AppCode)) {
				// 计算失败！回滚事务
				transactionManager.rollback(status);
			}
			
			if (!"".equals(entityMap.get("prm_ErrTxt").toString()) 
					&& null != entityMap.get("prm_ErrTxt").toString()) {
				prm_ErrTxt = entityMap.get("prm_ErrTxt").toString();
			}
			
			return "{\"msg_text\":\"" + prm_ErrTxt + ".\",\"is_ok\":\"" + prm_AppCode + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"计算失败 数据库异常 请联系管理员! 错误编码  collectBatchAccWagePay\"}";
		}
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 导入AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWagePay(List<Map<String, Object>>  entityMap)throws DataAccessException{

		try {
			
			String msg =queryAccWagePerm(entityMap.get(0));
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}

			accWagePayMapper.addBatchAccWagePayImport(entityMap);

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryTrendColumn(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<AccWageItems> list = accWageItemsMapper.queryAccWageItems(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String queryEmpByWageCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AccWagePay> list= accWagePayMapper.queryEmpByWageCode(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String queryAccWageItemPay(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accWagePayMapper.queryAccWageItemPay(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accWagePayMapper.queryAccWageItemPay(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public String addAccWagePayByList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			String msg =queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			accWagePayMapper.deleteAccWagePay(entityMap);
			
			accWagePayMapper.addAccWagePayByList(entityMap);
			
			return "{\"msg\":\"批量录入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"批量录入失败 数据库异常 请联系管理员! 错误编码 addAccWagePayByList\"}";

		}
	}

	@Override
	public String queryAccWagePayGrid(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if(entityMap.containsKey("scheme_id")&&!"".equals(entityMap.get("scheme_id"))){
			
			 list = accWagePayMapper.queryAccWagePayGrid(entityMap);
			
			return ChdJson.toJson(list);
		}
		
		 list = accWagePayMapper.queryAccWageItemGrid(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String funCollect(Map<String, Object> entityMap,List<AccWageItems> list)
			throws DataAccessException {
		
		List<Map<String, Object>> listCal =accWagePayMapper.queryAccWageItemPayOfCalName(entityMap);
		
		for (AccWageItems accWageItems : list) {
			
			if(listCal.get(0).get("CAL_ENG").toString().indexOf(accWageItems.getItem_name())>0){
				
				if("2".equals(accWageItems.getItem_cal().toString())){
					
					entityMap.put("item_id", accWageItems.getItem_id());
					
					cal_english ="("+funCollect(entityMap,list)+")";
					
				}else{
					
					cal_english+=listCal.get(0).get("CAL_ENG").toString();
					
				}

			}
			
		}
		return cal_english;
	}

	@Override
	public List<Map<String, Object>> queryAccWageItemList(
			Map<String, Object> entityMap) throws DataAccessException {
		
		if(!"".equals(entityMap.get("scheme_id"))){
			
			 return accWagePayMapper.queryAccWagePayGrid(entityMap);
			
		}
		
		 return accWagePayMapper.queryAccWageItemGrid(entityMap);
	}

	@Override
	public List<AccWagePay> queryAccEmpDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuffer sql= new StringBuffer();
		
		StringBuffer sqlWhere= new StringBuffer();
		
		/*if(!"".equals(entityMap.get("scheme_id"))&&null != entityMap.get("scheme_id")){
			
			sql.append("  acc_wage_scheme_kind awsk left join hos_emp_dict hed on  awsk.emp_id = hed.emp_id and hed.is_stop=0 ");
			
			sqlWhere.append("  and awsk.scheme_id="+entityMap.get("scheme_id"));
			
			//sqlWhere.append("  and wage_code="+entityMap.get("wage_code"));
			
		}else{*/
			
			sql.append("  acc_wage_pay awe left join hos_emp_dict hed on awe.group_id = hed.group_id  ");
			
			sql.append(" and awe.hos_id = hed.hos_id and awe.emp_id = hed.emp_id and hed.is_stop = 0 ");
			
			sqlWhere.append("  and wage_code='"+entityMap.get("wage_code")+"'");
		//}
		
		entityMap.put("sql", sql);
		
		entityMap.put("sqlWhere", sqlWhere);
		
		return accWagePayMapper.queryAccEmpDetail(entityMap);
	}
	
	@Override
	public String queryAccWageEmpDetailByAdd(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder sql = new StringBuilder();
		String[] time = entityMap.get("acc_year").toString().split("\\.");
		entityMap.put("acc_year", time[0] + "." + String.valueOf(Integer.parseInt(time[1])));
		String[] item_column = entityMap.get("column_item").toString().split(",");
		for (int i = 1; i < item_column.length; i++) {
			sql.append(",round(nvl(" + item_column[i] + ", 0), 2) as " + item_column[i]);
		}
		entityMap.put("sql", sql.substring(1));
		return ChdJson.toJson(accWagePayMapper.extendAccEmpDetailByQuery(entityMap));
	}


	@Override
	public List<AccWageItems> queryAccWageItem(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accWageItemsMapper.queryAccWageItems(entityMap);
	}

	@Override
	public String addAccWagePayByWageEmp(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer sql = new StringBuffer();
		try {
			String msg = queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
			}else */
			
			if ("1".equals(msg)) {
				return "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
			}
			
			// 查询上个月数据，有数据继承is_jc=1的工资项
			AccWageItems wage_item = accWageItemsMapper.queryAccWageColumnItems(entityMap);
			
			if(wage_item.getColumn_item()==null){
				
				return "{\"msg\":\"未找到本年度工资项目.\",\"state\":\"true\"}";
			}
			
			// 生成本月工资表之前先删除工资发放表中的数据
			accWagePayMapper.deleteAccWagePay(entityMap);
			
			// 将符合acc_wage_emp的职工添加到acc_wage_pay里面
			accWagePayMapper.addAccWagePayByWageEmp(entityMap);// 通过工资套与职工的关联关系
			
			// 将符合acc_wage_emp_kind职工分类的职工添加到acc_wage_pay里面 
			//accWagePayMapper.addAccWagePayByEmpKindBatch(entityMap);// 通过工资套与职工分类的关联关系
			
			String[] id = wage_item.getColumn_item().split(",");
			for (String column : id) {
				if (!"".equals(column)) {
					sql.append(column + "=0,");
				}
			}
			entityMap.put("sqlValue", sql.substring(0, sql.length() - 1));
			entityMap.put("note", "");
			accWagePayMapper.updateAccWagePay(entityMap);
			
			/*int result = accWagePayMapper.queryAccWagePayByLastMonth(entityMap);
			
			if(result>0){
				
				List<AccWageItems> itemList = queryAccWageItem(entityMap);
				   
				for (AccWageItems accWageItems : itemList) {
					
					Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("column_item", accWageItems.getColumn_item());
						
						map.put("wage_code", entityMap.get("wage_code"));
						
						map.put("group_id", entityMap.get("group_id"));
						
						map.put("hos_id", entityMap.get("hos_id"));
						
						map.put("copy_code", entityMap.get("copy_code"));
						
						if("01".equals(entityMap.get("acc_month").toString())){
							
							map.put("acc_year",String.valueOf(Integer.parseInt(entityMap.get("acc_year").toString())-1));
							
							map.put("acc_month","12");
							
						}else{
							
							map.put("acc_month",String.valueOf(Integer.parseInt(entityMap.get("acc_month").toString())-1));
						}
						
						AccWagePay accWagePay=accWagePayMapper.queryAccWagePayByColumn(map);
					
						map.put("column_value", accWagePay.getNote());
						
						map.put("emp_id", accWagePay.getEmp_id());
						
						list.add(map);
				}
				
				accWagePayMapper.extendAccWagePay(list);
				
			}*/
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException("生成失败！");
		}
	}
	
	//工资套合并
	@Override
	public String addAccWagePayCombine(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuffer sql = new StringBuffer();
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		List<String> list2 = new ArrayList<String>();
		
		try {
			//根据工资套查询出对应的合并公式
			List<AccWageCal> accWageCalList = accWageCalMapper.queryAccWageCalByCal(entityMap);
			//如果list>0则表示合并的存在要计算的工资项目
			if(accWageCalList.size()>0){
				//遍历合并工资套的计算公式
				for (int i = 0; i < accWageCalList.size(); i++) {
					
					AccWageCal map = accWageCalList.get(i);
					
					String item_cal=map.getCal_eng().toString();
					
					Pattern  p=  Pattern.compile("\\{.*?\\}");        
			        
					Matcher m = p.matcher(map.getCal_eng().toString());
					
					while (m.find()) {
						
						list2.add(m.group());
			           
			        } 
					
					Map<String, Object> calMap = new HashMap<String, Object>();
					
					calMap.put("group_id", entityMap.get("group_id"));
					
					calMap.put("hos_id", entityMap.get("hos_id"));
					
					calMap.put("copy_code", entityMap.get("copy_code"));
					
					calMap.put("acc_year", entityMap.get("acc_year"));
					
					calMap.put("acc_month", entityMap.get("acc_month"));
					
					calMap.put("kind_code", map.getKind_code());
					
					calMap.put("wage_code", entityMap.get("wage_code"));
					
					calMap.put("column_item", "a."+map.getColumn_item());
					
					for (int j = 0; j < list2.size(); j++) {
						
						sql.setLength(0);

						sql.append(" nvl((select "+list2.get(j).split("\\.")[1].replaceAll("\\{","").replaceAll("\\}", "")+" from acc_wage_pay b ");
						
						sql.append(" where a.emp_id=b.emp_id and a.emp_no=b.emp_no and b.group_id= '"+entityMap.get("group_id")+"' and b.hos_id='"+entityMap.get("hos_id")+"'");
						
						sql.append(" and b.copy_code= '"+entityMap.get("copy_code")+"' and b.acc_year='"+entityMap.get("acc_year")+"'");
						
						sql.append(" and b.acc_month= '"+entityMap.get("acc_month")+"'");
						
						sql.append(" and b.wage_code= '"+list2.get(j).split("\\.")[0].replaceAll("\\{","").replaceAll("\\}", "")+"'");
						
						if(!"0".equals(map.getKind_code())){
							
							sql.append(" and b.kind_code='"+map.getKind_code()+"'");
							
						}
						
						sql.append("),0)");
						
						item_cal=item_cal.replace(list2.get(j),sql );
					}
				
					calMap.put("cal_eng",item_cal.replaceAll("\\{", "").replaceAll("\\}", ""));
					
					list.add(calMap);
					
				}
				
				accWagePayMapper.updateBatchAccWagePay(list);
				
				//计算合并工资套
				return "{\"msg\":\"合并成功.\",\"state\":\"true\"}";
			}
			
			return "{\"msg\":\"所选工资套没有可以合并的工资项目.\",\"state\":\"false\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"合并失败 数据库异常 请联系管理员! 错误编码 addAccWagePayCombine\"}";
			
		}
	}

	@Override
	public String queryAccSchemeEmpKindList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return JSON.toJSONString(accWagePayMapper.queryAccSchemeEmpKindList(entityMap));
	}

	@Override
	public String queryAccSchemeWageItemList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return JSON.toJSONString(accWagePayMapper.queryAccSchemeWageItemList(entityMap));
	}

	@Override
	public String queryAccWagePayTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		StringBuilder json = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<AccWagePay> wagePayList = accWagePayMapper.queryAccWagePayTree(entityMap);
		for (AccWagePay accWagePay : wagePayList) {
			// 工资套节点
			json.append(",{'id':'" + accWagePay.getWage_code() + "','pId':'0','name':'"
					+ accWagePay.getWage_code() + " " + accWagePay.getWage_name() + "'}");
			
			// 工资方案节点
			if (!"".equals(accWagePay.getScheme_id()) && null != accWagePay.getScheme_id()) {
				String[] res = accWagePay.getScheme_id().toString().split(",");
				String[] scheme_code = accWagePay.getScheme_code().toString().split(",");
				String[] scheme_name = accWagePay.getScheme_name().toString().split(",");
				for (int j = 0; j < res.length; j++) {
					json.append(",{")
						.append("'id':'" + res[j] + "',")
						.append("'pId':'" + accWagePay.getWage_code() + "',")
						.append("'name':'" + scheme_code[j] + " " + scheme_name[j] + "'}");
				}
			}
		}
		if(json!=null && json.length()>0){
			jsonResult.append(json.substring(1)).append("]}");
		}else{
			jsonResult.append("]}");
		}
		
		return jsonResult.toString();
	}

	@Override
	public String queryAccEmpTetail(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accWagePayMapper.queryAccEmpTetail(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accWagePayMapper.queryAccEmpTetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String extendAccEmpDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		
		String sql="";
		
		String [] time=entityMap.get("acc_year").toString().split("\\.");
		
		if("01".equals(time[1])){
			
			entityMap.put("acc_year", Integer.parseInt(time[0])-1+".12");
			
		}else{
			
			entityMap.put("acc_year", time[0]+"."+String.valueOf(Integer.parseInt(time[1])-1));
		}
		
		String [] item_column=entityMap.get("column_item").toString().split(",");
		
		for (int i = 1; i < item_column.length; i++) {
			
			sql+=item_column[i]+" as "+item_column[i]+",";
			
		}
		
		entityMap.put("sql", sql.substring(0, sql.length()-1));
		
		return ChdJson.toJson(accWagePayMapper.extendAccEmpDetail(entityMap));
	}
	
	@Override
	public String queryAccWageEmpDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder sql = new StringBuilder();
		String [] time=entityMap.get("acc_year").toString().split("\\.");
		entityMap.put("acc_year", time[0]+"."+String.valueOf(Integer.parseInt(time[1])));
		String [] item_column=entityMap.get("column_item").toString().split(",");
		for (int i = 1; i < item_column.length; i++) {
			sql.append(",round("+item_column[i]+",2) as "+item_column[i]);
		}
		entityMap.put("sql", sql.substring(1));
		return ChdJson.toJson(accWagePayMapper.extendAccEmpDetail(entityMap));
	}
	
	@Override
	public String extendAccWagePay(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer addSql = new StringBuffer();
		StringBuffer addSqlValue = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String acc_year = "";// 存参数年月的上个月份的年
		String acc_month = "";// 存参数年月的上个月份
		try {
			String msg = queryAccWagePerm(entityMap);
			if ("1".equals(msg)) {
				return "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
			}
			
			// 计算参数年月的上一个年月
			String act_month = entityMap.get("acc_month").toString();
			String act_year = entityMap.get("acc_year").toString();
			if ("01".equals(entityMap.get("acc_month").toString())) {
				acc_year = String.valueOf(Integer.parseInt(act_year) - 1);
				acc_month = "12";
			} else {
				acc_month = String.valueOf(Integer.parseInt(act_month) - 1);
				acc_year = entityMap.get("acc_year").toString();
			}
			entityMap.put("act_year", acc_year);
			
			entityMap.put("act_month", acc_month);
			
			String [] item_column=entityMap.get("item_column").toString().split(",");
			
			for (int i = 1; i < item_column.length; i++) {
				
				addSql.append(","+item_column[i]);
				
				addSqlValue.append(",0");
			}
			
			entityMap.put("addSql", addSql.substring(1));
			
			entityMap.put("addSqlValue", addSqlValue.substring(1));
			 
			accWagePayMapper.addAccWagePayByWageExtend(entityMap);
			// 查询上个月数据，有数据继承is_jc=1的工资项
			int result = accWagePayMapper.queryAccWagePayByLastMonth(entityMap);
			if (result > 0) {
				entityMap.put("is_jc", "1");
				List<AccWageItems> itemList = queryAccWageItem(entityMap);
				for (AccWageItems accWageItems : itemList) {
					sql.append("a." + accWageItems.getColumn_item() + " = nvl((select b." + accWageItems.getColumn_item()
							+ " from acc_wage_pay b");
					sql.append(" where b.emp_id=a.emp_id and b.emp_no=a.emp_no");
					sql.append(" and b.group_id =" + entityMap.get("group_id") + " and b.hos_id =" + entityMap.get("hos_id"));
					sql.append(" and b.copy_code ='" + entityMap.get("copy_code") + "' and b.wage_code ='" + entityMap.get("wage_code") + "'");
					sql.append(" and b.acc_year = '" + acc_year + "' and to_number(b.acc_month)='" + acc_month + "'),0),");
				}
				entityMap.put("acc_year", act_year);
				entityMap.put("acc_month", act_month);
				entityMap.put("sql", sql.substring(0, sql.length() - 1));
				list.add(entityMap);
				accWagePayMapper.extendAccWagePay(list);
				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"上期没有可以的继承的工资项目数据.\",\"state\":\"false\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码 extendAccWagePay\"}";
		}
	}

	@Override
	public String collectAccWagePayByPerson(Map<String, Object> entityMap)
			throws DataAccessException {

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		List<AccSubj> list = new ArrayList<AccSubj>();
		
		try {
			/*1、提取工资项的计算公式
			List<Map<String,Object>> list =accWagePayMapper.queryAccWageItemPayOfCalName(entityMap);
			
			查出所有职工分类和工资套
			List<AccWageItemCal> itemCalList = accWageItemCalMapper.queryAccWageItemCalByCollect(entityMap);
			
			collectWageItem(list,itemCalList,entityMap);*/
			
			String msg =queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			accWagePayMapper.collectAccWagePayByPerson(entityMap);
			
			String prm_AppCode = entityMap.get("prm_AppCode").toString();
			
			String prm_ErrTxt="";
			
			if("0".equals(prm_AppCode)){
				//计算成功！提交事务
				transactionManager.commit(status);
				
				return "{\"msg_text\":\"计算成功.\",\"is_ok\":\""+prm_AppCode+"\"}";
				
			}else if("-1".equals(prm_AppCode)||"100".equals(prm_AppCode)){
				//计算失败！回滚事务
				transactionManager.rollback(status);
				
			}
			
			if(!"".equals(entityMap.get("prm_ErrTxt").toString()) && null != entityMap.get("prm_ErrTxt").toString()){
				
				prm_ErrTxt = entityMap.get("prm_ErrTxt").toString();
				
			}
			
			return "{\"msg_text\":\""+prm_ErrTxt+".\",\"is_ok\":\""+prm_AppCode+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"计算失败 数据库异常 请联系管理员! 错误编码  collectAccWagePayByPerson\"}";

		}
	}

	@Override
	public Map<String, Object> queryAccWageCulumn(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accWagePayMapper.queryAccWageCulumn(entityMap);
	}
	
	public String queryAccWagePerm(Map<String, Object> entityMap) throws DataAccessException {
		List<AccYearMonth> list = new ArrayList<AccYearMonth>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select acc_year acc_year,wm_concat(acc_month) acc_month from acc_year_month ")
			   .append("where group_id = " + entityMap.get("group_id"))
			   .append(" and hos_id = " + entityMap.get("hos_id"))
			   .append(" and copy_code = " + entityMap.get("copy_code"))
			   .append(" and wage_flag = 1 group by acc_year order by acc_year desc");
			entityMap.put("acc_sql", sql);
			list = accWageCarryOverMapper.queryNextAccYearMonth(entityMap);
			if (list.size() > 0) {
				if (list.get(0).getAcc_year().indexOf(entityMap.get("acc_year").toString()) > -1
						&& list.get(0).getAcc_month().indexOf(entityMap.get("acc_month").toString()) > -1) {
					return "1";
				}
				return "0";
			}
			// 工资期间为空，返回重新维护工资期间
			return "-1";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"获取工资期间失败！\"}";
		}
	} 
	
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWagePay(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			String msg =queryAccWagePerm(entityMap);
			
			/*if("-1".equals(msg)){
				
				return  "{\"msg\":\"请维护工资期间!\",\"state\":\"false\"}";
				
			}else */if("1".equals(msg)){
				
				return  "{\"msg\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
				
			}
			
			accWagePayMapper.auditBatchAccWagePay(entityMap);

			return "{\"msg\":\"成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWagePay\"}";

		}
	}
	
	@Override
	public String updateAccWagePayOfBatch(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer sbf = new StringBuffer();
		try {
			String msg = queryAccWagePerm(entityMap);
			if ("1".equals(msg)) {
				return "{\"warn\":\"工资已经结转,不能进行维护!\",\"state\":\"false\"}";
			}

			String[] res = entityMap.get("item").toString().split(";");
			if(res.length == 0){
				return "{\"warn\":\"没有填写数据.\",\"state\":\"false\"}";
			}
			String column_item = entityMap.get("item_column").toString();
			String[] column = column_item.substring(1, column_item.length()).split(",");

			for (int i = 0; i < res.length; i++) {
				if (!"".equals(res[i]) && null != res[i]) {
					
					String regex = "^(\\+|\\-)";
					
					Pattern pattern = Pattern.compile(regex);
					
					Matcher matcher = pattern.matcher(res[i]);
					
					Boolean exist = matcher.find();  
					
					if(exist){
						sbf.append(column[i] + "=" + column[i] + res[i] + ",");
					}else{
						sbf.append(column[i] + "=" + res[i] + ",");
					}
					
				} 
			}

			entityMap.put("sqlValue", sbf.substring(0, sbf.length() - 1));
			entityMap.put("note", "");
			accWagePayMapper.updateAccWagePayOfBatch(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWagePay\"}";
		}
	}

	@Override
	public int checkEmpNameAndCode(Map<String, Object> mapVo) {
		int count = accWagePayMapper.checkEmpNameAndCode(mapVo);
		return count;
	}

	public List<AccWagePay> queryWageState(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return accWagePayMapper.queryWageState(mapVo);
	}
}
