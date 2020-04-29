/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.wage.AccWageEmpMapper;
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.acc.service.wage.AccWageEmpService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套职工关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageEmpService")
public class AccWageEmpServiceImpl implements AccWageEmpService {

	private static Logger logger = Logger.getLogger(AccWageEmpServiceImpl.class);
	
	@Resource(name = "accWageEmpMapper")
	private final AccWageEmpMapper accWageEmpMapper = null;
    
	/**
	 * @Description 
	 * 工资套职工关系<BR> 添加AccWageEmp
	 * @param AccWageEmp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageEmp(Map<String, Object> paraMap) throws DataAccessException {
		try {
			StringBuilder empIds = new StringBuilder();
			String wageCode = paraMap.get("wage_code").toString();// 工资套编码
			// 目标数据
			List<AccWageEmp> list = JSONArray.parseArray(String.valueOf(paraMap.get("paramVo")), AccWageEmp.class);
			
			// 补充属性
			for(AccWageEmp item : list){
				item.setGroup_id(Long.valueOf(paraMap.get("group_id").toString()));
				item.setHos_id(Long.valueOf(paraMap.get("hos_id").toString()));
				item.setCopy_code(paraMap.get("copy_code").toString());
				item.setWage_code(wageCode);
				item.setNote("");
				
				empIds.append(",").append(item.getEmp_id());
			}
			
			paraMap.put("empIds", empIds.substring(1));
			
			// 检验是否已经存在与工资套编码
			List<AccWageEmp> existsList = (List<AccWageEmp>) accWageEmpMapper.queryExists(paraMap);
			if(existsList.size() > 0){
				StringBuilder sb = new StringBuilder("以下职工已经该工资套关联：");
				for(AccWageEmp we : existsList){
					sb.append("<br/>").append(we.getEmp_code() + " " + we.getEmp_name());
				}
				return "{\"warn\":\"" + sb.toString() + "\",\"state\":\"true\"}";
			}
			
			// 执行保存
			accWageEmpMapper.addAccWageEmpBatch(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("添加工资套与职工关联失败.");
		}
	}
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量添加AccWageEmp
	 * @param  AccWageEmp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageEmp(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageEmpMapper.addBatchAccWageEmp(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageEmp\"}";

		}
	}
	
	/**
	 * @Description 工资套职工关系<BR>
	 *              查询AccWageEmp分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccWageEmp(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<AccWageEmp> list = accWageEmpMapper.queryAccWageEmp(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AccWageEmp> list = accWageEmpMapper.queryAccWageEmp(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageEmp> list = accWageEmpMapper.queryAccWageAttr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageEmp> list = accWageEmpMapper.queryAccWageAttr(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageEmp queryAccWageEmpByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageEmpMapper.queryAccWageEmpByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量删除AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageEmp(List<Map<String,Object>> entityList)throws DataAccessException{

		try { 

				int state = accWageEmpMapper.deleteBatchAccWageEmp(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageEmp\"}";

		}
		
	}
	
	/**
	 * @Description 工资套职工关系<BR>
	 *              删除AccWageEmp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccWageEmp(Map<String, Object> paraMap) throws DataAccessException {
		try {
			String wageCode = paraMap.get("wage_code").toString();
			// 目标数据
			List<AccWageEmp> list = JSONArray.parseArray(String.valueOf(paraMap.get("paramVo")), AccWageEmp.class);
			for(AccWageEmp item : list){
				item.setGroup_id(Long.valueOf(SessionManager.getGroupId()));
				item.setHos_id(Long.valueOf(SessionManager.getHosId()));
				item.setCopy_code(SessionManager.getCopyCode());
				item.setWage_code(wageCode);
			}
			
			accWageEmpMapper.deleteBatchAccWageEmp(JsonListMapUtil.beanToListMap(list));
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageEmp\"}";
		}
	}
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 更新AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageEmp(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageEmpMapper.updateAccWageEmp(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageEmp\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量更新AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageEmp(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageEmpMapper.updateBatchAccWageEmp(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageEmp\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 导入AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageEmp(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccWageEmpList(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageEmp> list = accWageEmpMapper.queryAccWageEmpList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageEmp> list = accWageEmpMapper.queryAccWageEmpList(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
	}
	}

	public String accExtendOldWageEmpToNew(Map<String, Object> mapVo) {
		
		try {
			accWageEmpMapper.accExtendOldWageEmpToNew(mapVo);
			return "{\"msg\":\"数据继承成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"数据继承失败 数据库异常 请联系管理员! 错误编码 accExtendOldWageEmpToNew\"}";
		}
		
		
	}
	
	@Override
	public List<Map<String, Object>> queryAccWageEmpListPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
			entityMap.put("group_id", SessionManager.getGroupId());
			       
			entityMap.put("hos_id", SessionManager.getHosId());
		        
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			if(entityMap.get("emp_history")==null){
				
				entityMap.put("emp_history", MyConfig.getSysPara("021"));
				
			}
			
			if(entityMap.get("emp_code") != null){
				//用来转换大写
				entityMap.put("emp_code",entityMap.get("emp_code").toString().toUpperCase());
			}
			if(entityMap.get("dept_code") != null){
				//用来转换大写
				entityMap.put("dept_code",entityMap.get("dept_code").toString().toUpperCase());
			}
			if(entityMap.get("kind_code") != null){
				//用来转换大写
				entityMap.put("kind_code",entityMap.get("kind_code").toString().toUpperCase());
			}

			List<Map<String, Object>> list = accWageEmpMapper.queryAccWageEmpListPrint(entityMap);
			
			return list;
	}
	
	@Override
	public AccWageEmp queryAccWageEmpCodeByImp(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageEmpMapper.queryAccWageEmpCodeByImp(entityMap);
		
	}

	@Override
	public String queryAccWageEmpNotBind(Map<String, Object> paraMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accWageEmpMapper.queryAccWageEmpNotBind(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accWageEmpMapper.queryAccWageEmpNotBind(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

}
