/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccStoreAttrMapper;
import com.chd.hrp.acc.entity.AccStoreAttr;
import com.chd.hrp.acc.service.AccStoreAttrService;
import com.chd.hrp.sys.dao.DeptMapper;
import com.chd.hrp.sys.dao.EmpMapper;
import com.chd.hrp.sys.dao.StoreDictMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.entity.Emp;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 库房字典属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accStoreAttrService")
public class AccStoreAttrServiceImpl implements AccStoreAttrService {

	private static Logger logger = Logger.getLogger(AccStoreAttrServiceImpl.class);
	
	@Resource(name = "accStoreAttrMapper")
	private final AccStoreAttrMapper accStoreAttrMapper = null;
	
	@Resource(name = "deptMapper")
	private final DeptMapper deptMapper = null;
	
	@Resource(name = "empMapper")
	private final EmpMapper empMapper = null;
    
	@Resource(name = "storeDictMapper")
	private final StoreDictMapper storeDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 添加AccStoreAttr
	 * @param AccStoreAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		AccStoreAttr accStoreAttr = accStoreAttrMapper.queryAccStoreAttrByCode(entityMap);

		if (accStoreAttr != null) {
			
			accStoreAttrMapper.updateAccStoreAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			accStoreAttrMapper.addAccStoreAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccStoreAttr\"}";

		}

	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量添加AccStoreAttr
	 * @param  AccStoreAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccStoreAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accStoreAttrMapper.addBatchAccStoreAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccStoreAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccStoreAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccStoreAttr> list = accStoreAttrMapper.queryAccStoreAttr(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccStoreAttr queryAccStoreAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		AccStoreAttr accStoreAttr =accStoreAttrMapper.queryStoreByCode(entityMap);
		
		if(accStoreAttr!= null){
			
			entityMap.put("dept_id", accStoreAttr.getDept_id());
			
			Dept dept= deptMapper.queryDeptByCode(entityMap);
			
			accStoreAttr.setDept_code(dept.getDept_code());
			
			accStoreAttr.setDept_name(dept.getDept_name());
			
		/*}else if(accStoreAttr.getHead_emp_id() != null){
			*/
			entityMap.put("emp_id", accStoreAttr.getHead_emp_id().toString());
			
			Emp headEmp= empMapper.queryEmpByCode(entityMap);
			
			accStoreAttr.setHead_emp_code(headEmp.getEmp_code());
			
			accStoreAttr.setHead_emp_name(headEmp.getEmp_name());
			
		/*}else if(accStoreAttr.getAcc_emp_id() != null){*/
			
			entityMap.put("emp_id", accStoreAttr.getAcc_emp_id().toString());
			
			Emp accEmp= empMapper.queryEmpByCode(entityMap);
			
			accStoreAttr.setAcc_emp_code(accEmp.getEmp_code());
			
			accStoreAttr.setAcc_emp_name(accEmp.getEmp_name());
			
		/*}else if(accStoreAttr.getSafe_emp_id() != null){*/
			
			entityMap.put("emp_id", accStoreAttr.getSafe_emp_id().toString());
			
			Emp safeEmp= empMapper.queryEmpByCode(entityMap);
			
			accStoreAttr.setSafe_emp_code(safeEmp.getEmp_code());
			
			accStoreAttr.setSafe_emp_name(safeEmp.getEmp_name());
			
		/*}else if(accStoreAttr.getProc_emp_id() != null){*/
			
			entityMap.put("emp_id", accStoreAttr.getProc_emp_id().toString());
			
			Emp procEmp= empMapper.queryEmpByCode(entityMap);
			
			accStoreAttr.setProc_emp_code(procEmp.getEmp_code());
			
			accStoreAttr.setProc_emp_name(procEmp.getEmp_name());
			
		}
		
		return 	accStoreAttr;
		
	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量删除AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccStoreAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accStoreAttrMapper.deleteBatchAccStoreAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccStoreAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 删除AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccStoreAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accStoreAttrMapper.deleteAccStoreAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccStoreAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 更新AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accStoreAttrMapper.updateAccStoreAttr(entityMap);

			//把当前数据修改成历史数据(保留历史数据)
			int updateCount = accStoreAttrMapper.updateStoreDictHistory(entityMap);
			if (updateCount == 0) {
				throw new SysException("修改失败,请重新刷新页面后重试!");
			}
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("store_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("store_name").toString()));
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			entityMap.put("dlog", "修改");
			entityMap.put("is_disable", entityMap.get("is_disable"));
			entityMap.put("is_stop", 0);
			entityMap.put("is_mat", 0);
			entityMap.put("is_ass", 0);
			entityMap.put("is_med", 0);
			entityMap.put("is_sup", 0);
			Map<String, Object> utilMap = new HashMap<String, Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", "");
			utilMap.put("field_table", "HOS_STORE");
			utilMap.put("field_key1", "");
			utilMap.put("field_value1", "");
			utilMap.put("field_key2", "");
			utilMap.put("field_value2", "");

			if (entityMap.get("sort_code").equals("系统生成")) {
				utilMap.remove("field_key2");
				utilMap.put("field_sort", "sort_code");
				int count = sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("sort_code", count);
			}
			
			//添加修改后的数据
			int addCount = storeDictMapper.addStoreDict(entityMap);
			if (addCount == 0) {
				throw new SysException("修改失败,请重新刷新页面后重试!");
			}
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccStoreAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量更新AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccStoreAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accStoreAttrMapper.updateBatchAccStoreAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccStoreAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 导入AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 
	 * 修改库房时<BR> 查询StoreByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccStoreAttr queryStoreByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accStoreAttrMapper.queryStoreByCode(entityMap);
	}

	@Override
	public AccStoreAttr queryHosStoreByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accStoreAttrMapper.queryHosStoreByCode(entityMap);
	}
}
