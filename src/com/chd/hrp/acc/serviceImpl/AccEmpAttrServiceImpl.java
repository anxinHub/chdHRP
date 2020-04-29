/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;
 
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccEmpAttrMapper;
import com.chd.hrp.acc.dao.wage.AccWageEmpKindMapper;
import com.chd.hrp.acc.dao.wage.AccWageEmpMapper;
import com.chd.hrp.acc.entity.AccEmpAttr;
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.acc.service.AccEmpAttrService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 职工字典属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accEmpAttrService")
public class AccEmpAttrServiceImpl implements AccEmpAttrService {

	private static Logger logger = Logger.getLogger(AccEmpAttrServiceImpl.class);
	
	@Resource(name = "accEmpAttrMapper")
	private final AccEmpAttrMapper accEmpAttrMapper = null;
    
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "accWageEmpMapper")
	private final AccWageEmpMapper accWageEmpMapper = null;
	
	@Resource(name = "accWageEmpKindMapper")
	private final AccWageEmpKindMapper accWageEmpKindMapper = null;
    
	/**
	 * @Description 
	 * 职工字典属性表<BR> 添加AccEmpAttr
	 * @param AccEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		AccEmpAttr accEmpAttr = queryAccEmpAttrByCode(entityMap);

		if (accEmpAttr != null) {

			accEmpAttrMapper.updateAccEmpAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}
		
		try {
			
			accEmpAttrMapper.addAccEmpAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccEmpAttr\"}";

		}

	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量添加AccEmpAttr
	 * @param  AccEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccEmpAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accEmpAttrMapper.addBatchAccEmpAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccEmpAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccEmpAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccEmpAttr> list = accEmpAttrMapper.queryAccEmpAttr(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccEmpAttr queryAccEmpAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accEmpAttrMapper.queryAccEmpAttrByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量删除AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccEmpAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accEmpAttrMapper.deleteBatchAccEmpAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccEmpAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 删除AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccEmpAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accEmpAttrMapper.deleteAccEmpAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccEmpAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 更新AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accEmpAttrMapper.updateAccEmpAttr(entityMap);
			
			if("0".equals(entityMap.get("is_pay").toString())){
				
				entityMap.put("copy_code", SessionManager.getCopyCode());
				
				accWageEmpMapper.deleteAccWageEmp(entityMap);
				
			}else{
				
				entityMap.put("copy_code", SessionManager.getCopyCode());
				
				accWageEmpMapper.deleteAccWageEmp(entityMap);
				
				List<AccWageEmp> accWageEmpKind=accWageEmpKindMapper.queryAccWageEmpKind(entityMap);
				
				for (AccWageEmp accWageEmp : accWageEmpKind) {
					
					entityMap.put("wage_code", accWageEmp.getWage_code());
					
					accWageEmpMapper.addAccWageEmp(entityMap);
				}
				
			}

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEmpAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量更新AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccEmpAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accEmpAttrMapper.updateBatchAccEmpAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEmpAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 导入AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 
	 * 修改职工时<BR> 查询EmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccEmpAttr queryEmpByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accEmpAttrMapper.queryEmpByCode(entityMap);
	}

	/**
	 * @Description 
	 * 添加职工时<BR> 查询HosEmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccEmpAttr queryHosEmpByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accEmpAttrMapper.queryHosEmpByCode(entityMap);
	}
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccEmpNumber(Map<String,Object> entityMap)throws DataAccessException{
		
		List<AccEmpAttr> list =accEmpAttrMapper.queryAccEmpNumber(entityMap);
		
		return ChdJson.toJson(list);
		
	}

	public String queryDeptByEmpid(Map<String, Object> mapVo) {
		List<DeptDict> list =deptDictMapper.queryDeptByEmpid(mapVo);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String initAccEmpAttr(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			if(!"".equals(entityMap.get("emp_id"))&&null != entityMap.get("emp_id")){
				
				String [] emp_ids = entityMap.get("emp_id").toString().split(",");
				
				for (String emp_id : emp_ids) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("emp_id", emp_id.split("@")[0]);
					
					map.put("group_id", entityMap.get("group_id"));
					
					map.put("hos_id", entityMap.get("hos_id"));
					
					map.put("sex", entityMap.get("sex").toString());
					
					map.put("is_pay_box", entityMap.get("is_pay_box").toString());
					
					map.put("is_pay", entityMap.get("is_pay").toString());
					
					map.put("pay_type_code_box", entityMap.get("pay_type_code_box").toString());
					
					map.put("pay_type_code", entityMap.get("pay_type_code").toString());
					
					map.put("station_code_box", entityMap.get("station_code_box").toString());
					
					map.put("station_code", entityMap.get("station_code").toString());
					
					map.put("duty_code_box", entityMap.get("duty_code_box").toString());
					
					map.put("duty_code", entityMap.get("duty_code").toString());
					
					map.put("id_number_box", entityMap.get("id_number_box").toString());
					
					map.put("id_number", entityMap.get("id_number").toString());
					
					map.put("phone_box", entityMap.get("phone_box").toString());
					
					map.put("phone", entityMap.get("phone").toString());
					
					map.put("mobile_box", entityMap.get("mobile_box").toString());
					
					map.put("mobile", entityMap.get("mobile").toString());
					
					map.put("emial_box", entityMap.get("emial_box").toString());
					
					map.put("emial", entityMap.get("emial").toString());
					
					map.put("note_box", entityMap.get("note_box").toString());
					
					map.put("note", entityMap.get("note").toString());
					
					map.put("is_buyer_box", entityMap.get("is_buyer_box").toString());
					
					map.put("is_buyer", entityMap.get("is_buyer").toString());
					
					map.put("countries_code_box", entityMap.get("countries_code_box").toString());
					
					map.put("countries_code", entityMap.get("countries_code").toString());
					
					map.put("attr_code_box", entityMap.get("attr_code_box").toString());
					
					map.put("attr_code", entityMap.get("attr_code").toString());
					
					AccEmpAttr accEmpAttr= accEmpAttrMapper.queryAccEmpAttrByCode(map);
					
					if(accEmpAttr!= null){
						
						accEmpAttrMapper.updateAccEmpAttrById(map);
						
						if("0".equals(entityMap.get("is_pay").toString())){
							
							map.put("copy_code", SessionManager.getCopyCode());
							
							accWageEmpMapper.deleteAccWageEmp(map);
							
						}/*else{
							
							map.put("copy_code", SessionManager.getCopyCode());
							
							accWageEmpMapper.deleteAccWageEmp(map);
							
							AccWageEmp accWageEmpKind=accWageEmpKindMapper.queryAccWageEmpKindBykindCode(map);
							
							entityMap.put("wage_code", accWageEmpKind.getWage_code());
							
							accWageEmpMapper.addAccWageEmp(map);
						}*/
						
					}else{
						
						accEmpAttrMapper.addAccEmpAttrById(map);
					}
					
				}
				
			}else{
				
				accEmpAttrMapper.initAccEmpAttr(entityMap);
				
			}
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 initAccEmpAttr\"}";

		}
	}
}
