/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.emp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.emp.AccEmpAccountMapper;
import com.chd.hrp.acc.dao.emp.AccWageTypeMapper;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.entity.AccWageType;
import com.chd.hrp.acc.service.emp.AccEmpAccountService;
import com.chd.hrp.hr.util.StringUtils;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.entity.EmpDict;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 职工账号<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accEmpAccountService")
public class AccEmpAccountServiceImpl implements AccEmpAccountService {

	private static Logger logger = Logger.getLogger(AccEmpAccountServiceImpl.class);
	
	@Resource(name = "accEmpAccountMapper")
	private final AccEmpAccountMapper accEmpAccountMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;
	
	@Resource(name = "accWageTypeMapper")
	private final AccWageTypeMapper accWageTypeMapper = null;
	
    
	/**
	 * @Description 
	 * 职工账号<BR> 添加AccEmpAccount
	 * @param AccEmpAccount entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException{
		
		StringBuffer sql_value = new StringBuffer();
		
		try {
			
			int account = accEmpAccountMapper.queryAccEmpAccountByType(entityMap);

			if (account >1) {

				return "{\"error\":\"工资类别已存在,请核对信息.\"}";

			}
			
			/*sql_value.append(" and (emp_id != "+entityMap.get("emp_id")+"  or emp_id = "+entityMap.get("emp_id")+")");
			
			entityMap.put("sql_value", sql_value);*/
			
			int accEmpAccount = accEmpAccountMapper.queryAccEmpAccountByBank(entityMap);

			if (accEmpAccount >0) {

				return "{\"error\":\"职工信息填写有误,请核对信息.\"}";

			}
			
			accEmpAccountMapper.addAccEmpAccount(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccEmpAccount\"}";

		}

	}
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量添加AccEmpAccount
	 * @param  AccEmpAccount entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccEmpAccount(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accEmpAccountMapper.addBatchAccEmpAccount(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccEmpAccount\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccEmpAccount(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			//List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccount(entityMap);
			
			//上面的查询语句,是根据系统参数,来控制是否显示职工历史记录的
			//2017/09/26
			//更改查询,只根据职工变更号连接查询,查出已停用职工,避免工资或奖金未发,但是职工已停用,避免导入的职工账号查询不出来的问题
			List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccountList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			//List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccount(entityMap, rowBounds);
			List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccountList(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccEmpAccountIndex(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccountIndex(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccountIndex(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccountByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccEmpAccount queryAccEmpAccountByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accEmpAccountMapper.queryAccEmpAccountByCode(entityMap);
		
	}
	
	public AccEmpAccount queryAccEmpAccountIndexByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accEmpAccountMapper.queryAccEmpAccountIndexByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量删除AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccEmpAccount(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accEmpAccountMapper.deleteBatchAccEmpAccount(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccEmpAccount\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工账号<BR> 删除AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccEmpAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accEmpAccountMapper.deleteAccEmpAccount(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccEmpAccount\"}";

		}
    }
	
	/**
	 * @Description 
	 * 职工账号<BR> 更新AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException{

		StringBuffer sql_value = new StringBuffer();
		
		try {
			
			int account = accEmpAccountMapper.queryAccEmpAccountByType(entityMap);

			if (account >1) {

				return "{\"error\":\"工资类别已存在,请核对信息.\"}";

			}
			
			sql_value.append(" and emp_id != "+entityMap.get("emp_id"));
			
			entityMap.put("sql_value", sql_value);
			
			int accEmpAccount = accEmpAccountMapper.queryAccEmpAccountByBank(entityMap);

			if (accEmpAccount >0) {

				return "{\"error\":\"工资账号已存在,请核对信息.\"}";

			}
			
			accEmpAccountMapper.updateAccEmpAccount(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEmpAccount\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量更新AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccEmpAccount(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accEmpAccountMapper.updateBatchAccEmpAccount(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEmpAccount\"}";

		}
		
	}

	@Override
	public String queryAccEmpAccountCount(Map<String, Object> entityMap)
			throws DataAccessException {
		
		int count = accEmpAccountMapper.queryAccEmpAccountCount(entityMap);
		
		if(count==1){
			
			return "{\"num\":\"1\"}";
		}
		
		return "{\"num\":\"0\"}";
	}

	@Override
	public String queryAccEmpAccountByInter(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<AccEmpAccount> list = accEmpAccountMapper.queryAccEmpAccountByInter(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public AccEmpAccount queryAccEmpAccountByEmp(Map<String, Object> entityMap) throws DataAccessException {
		return accEmpAccountMapper.queryAccEmpAccountByEmp(entityMap);
	}

	
	@Override
	public String importAccEmpAccount(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> queryMap =new HashMap<String,Object>();
			queryMap.put("group_id", SessionManager.getGroupId());
			queryMap.put("hos_id", SessionManager.getHosId());
			queryMap.put("copy_code", SessionManager.getCopyCode());
			
			// 3.查询 基本职工 List
			List<EmpDict> empDictList = empDictMapper.queryEmpDictAll(queryMap);
			//用于存储查询empDictList中的EmpDict对象,以键值对的形式存储,用于判断职工是否存在
			Map<String, EmpDict> empDictMap = new HashMap<String, EmpDict>();
			//将职工List存入Map   键:emp_code 值:empDict
			//将职工List存入Map   键:emp_name 值:empDict
			for(EmpDict empDict : empDictList){
				empDictMap.put(empDict.getEmp_code() + empDict.getEmp_name(), empDict);
			}
			
			// 4.查询 账户类别 List
			List<AccWageType> wageTypeList = accWageTypeMapper.queryAccWageType(queryMap);
			//用于存储查询wageTypeList中的AccWageType对象,以键值对的形式存储,用于判断账户类别是否存在
			Map<String, AccWageType> wageTypeMap = new HashMap<String, AccWageType>();
			//将wageTypeList存入Map   键:type_name 值:accWageType
			for(AccWageType accWageType : wageTypeList){
				wageTypeMap.put(accWageType.getType_name(), accWageType);
			}
			
			//5. 查询所有职工账号
			List<AccEmpAccount> accEmpAccoutList = accEmpAccountMapper.queryAccEmpAccount(queryMap);
			//用于存储查询accEmpAccoutList中的AccEmpAccount对象,以键值对的形式存储,用于判断职工账号否存在
			Map<String, AccEmpAccount> accEmpAccoutMap = new HashMap<String, AccEmpAccount>();
			//将accEmpAccoutList存入Map   键:type_name 值:accWageType
			for(AccEmpAccount accEmpAccount : accEmpAccoutList){
				String key = 
						String.valueOf(accEmpAccount.getGroup_id()) +
						String.valueOf(accEmpAccount.getHos_id()) +
						String.valueOf(accEmpAccount.getCopy_code()) +
						String.valueOf(accEmpAccount.getEmp_id()) +
						String.valueOf(accEmpAccount.getEmp_no()) +
						String.valueOf(accEmpAccount.getAccount_code());
				
				accEmpAccoutMap.put(key, accEmpAccount);
			}
			
			//用于存储传的数据值,判断所导入的数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			//职工账号添加List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			/*//职工账号修改List
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();*/
			//用于记录返回的错误信息
			StringBuffer err_sb = new StringBuffer();
			
			for(Map<String, List<String>> item:liData){
				List<String> emp_code = item.get("职工编码");
				List<String> emp_name = item.get("职工名称");
				List<String> account_name = item.get("账号名");
				List<String> id_number = item.get("身份证号");
				List<String> account_code = item.get("工资账号");
				List<String> type_name = item.get("账户类别名称");
				List<String> emp_bank = item.get("开户银行");
				List<String> emp_arear = item.get("银行地区");
				List<String> note = item.get("备注");
				
				if(StringUtils.isEmpty(emp_code.get(1))){
					continue;
				}
				
				if (emp_code.get(1) == null) {
					return "{\"warn\":\"职工编码为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
				}
				
				if (emp_name.get(1) == null) {
					return "{\"warn\":\"职工名称为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_name.get(0) + "\"}";
				}
				
				if(empDictMap.get(emp_code.get(1) + emp_name.get(1)) == null){
					return "{\"warn\":\"" + emp_code.get(1)+","+emp_name.get(1)+",此职工不存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
				}else{
					EmpDict emp = empDictMap.get(emp_code.get(1) + emp_name.get(1));
					if(emp.getDept_id() == null || "".equals(emp.getDept_id())){
						return "{\"warn\":\"" +emp_code.get(1) +"," + emp_name.get(1) + "此职工所属部门未做维护\",\"state\":\"false\"}";
					}
				}
				
				if (account_name.get(1) == null) {
					return "{\"warn\":\"账号名为空！\",\"state\":\"false\",\"row_cell\":\"" + account_name.get(0) + "\"}";
				}
				
				if (account_code.get(1) == null) {
					return "{\"warn\":\"工资账号为空！\",\"state\":\"false\",\"row_cell\":\"" + account_code.get(0) + "\"}";
				}
				
				if (type_name.get(1) == null) {
					return "{\"warn\":\"账户类别名称为空！\",\"state\":\"false\",\"row_cell\":\"" + type_name.get(0) + "\"}";
				}else{
					if(wageTypeMap.get(type_name.get(1)) == null){
						return "{\"warn\":\""+type_name.get(1)+",账户类别名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + type_name.get(0) + "\"}";
					}
				}
				
				//判断导入数据是否重复
				String key = emp_code.get(1) + "," + emp_name.get(1) + "," +account_code.get(1);
				
				if(exitMap.get(key) != null ){
					err_sb.append(emp_code.get(1)+"职工编码," + emp_name.get(1)+"职工名称," + account_code.get(1)+"工资账号");
				}else{
					exitMap.put(key, key);
				}
				
				//添加数据Map中add到addList
				Map<String,Object> addMap = new HashMap<String,Object>();
				addMap.put("group_id", SessionManager.getGroupId());
				addMap.put("hos_id", SessionManager.getHosId());
				addMap.put("copy_code", SessionManager.getCopyCode());
				
				EmpDict empDict = empDictMap.get(emp_code.get(1) + emp_name.get(1));//获取职工   id,变更号
				addMap.put("emp_id", empDict.getEmp_id());
				addMap.put("emp_no", empDict.getEmp_no());
				addMap.put("id_number", id_number.get(1));
				addMap.put("account_name",account_name.get(1));
				addMap.put("account_code",account_code.get(1));
				addMap.put("type_id", wageTypeMap.get(type_name.get(1)).getType_id());
				addMap.put("is_stop", 0);
				
				if(emp_bank == null || emp_bank.get(1) == null){
					addMap.put("emp_bank", "");
				}else{
					addMap.put("emp_bank", emp_bank.get(1));
				}
				
				if(emp_arear == null || emp_arear.get(1) == null){
					addMap.put("emp_arear", "");
				}else{
					addMap.put("emp_arear",emp_arear.get(1));
				}
				
				if(note == null || note.get(1) == null){
					addMap.put("note", "");
				}else{
					addMap.put("note",note.get(1));
				}
				
				addMap.put("is_bank_same", "1");
				addMap.put("is_city_same", "1");
				
				String is_exit_key = 
						String.valueOf(addMap.get("group_id"))+
						String.valueOf(addMap.get("hos_id"))+
						String.valueOf(addMap.get("copy_code"))+
						String.valueOf(addMap.get("emp_id"))+
						String.valueOf(addMap.get("emp_no"))+
						String.valueOf(addMap.get("account_code"));
				
				if(accEmpAccoutMap.get(is_exit_key) != null){
					return "{\"warn\":\"职工编码:" + emp_code.get(1) + ","+"职工名称:"+ emp_name.get(1)  + ","+"工资账号:"+ account_code.get(1) +"数据已在数据库中存在！\",\"state\":\"false\"}";
				}
				
				addList.add(addMap);
			}
			
			if(err_sb.length() > 0){
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			accEmpAccountMapper.addBatchAccEmpAccount(addList);
			
			// 更新acc_emp_attr中的id_number（员工身份证号）
			List<Map<String, Object>> uList = new ArrayList<Map<String, Object>>();
			for(Map<String, Object> m : addList){
				if(m.get("id_number") != null){
					if(m.get("id_number").toString().trim() != ""){
						uList.add(m);
					}
				}
			}
			if(uList.size() > 0){
				accEmpAccountMapper.updateAccEmpAttrIdNumber(addList);
			}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"导入失败.\"}");
		}
	}
	
	@Override
	public List<Map<String,Object>> queryAccEmpAccountPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
	       
		entityMap.put("hos_id", SessionManager.getHosId());
        
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		if(entityMap.get("emp_name")!= null){
		//用来转换大写
			entityMap.put("emp_name",entityMap.get("emp_name").toString().toUpperCase());
		}
		
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		List<Map<String,Object>> list = accEmpAccountMapper.queryAccEmpAccountPrint(entityMap);
		
		return list;
		
	}
}
