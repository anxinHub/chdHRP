/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

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
import com.chd.hrp.acc.dao.commonbuilder.AccEcoTypeMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccFunTypeMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.entity.AccEcoType;
import com.chd.hrp.acc.entity.AccFunType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.sys.dao.CusMapper;
import com.chd.hrp.sys.dao.DeptMapper;
import com.chd.hrp.sys.dao.EmpMapper;
import com.chd.hrp.sys.dao.FacMapper;
import com.chd.hrp.sys.dao.ProjMapper;
import com.chd.hrp.sys.dao.RulesMapper;
import com.chd.hrp.sys.dao.StoreMapper;
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.entity.Cus;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.Fac;
import com.chd.hrp.sys.entity.Proj;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.entity.Store;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.service.RulesService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("rulesService")
public class RulesServiceImpl implements RulesService {

	private static Logger logger = Logger.getLogger(RulesServiceImpl.class);
	
	@Resource(name = "rulesMapper")
	private final RulesMapper rulesMapper = null;
	
	@Resource(name = "accFunTypeMapper")
	private final AccFunTypeMapper accFunTypeMapper = null;
	
	@Resource(name = "accEcoTypeMapper")
	private final AccEcoTypeMapper accEcoTypeMapper = null;
	
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	@Resource(name = "cusMapper")
	private final CusMapper cusMapper = null;
	
	@Resource(name = "deptMapper")
	private final DeptMapper deptMapper = null;
	
	@Resource(name = "empMapper")
	private final EmpMapper empMapper = null;
	
	@Resource(name = "facMapper")
	private final FacMapper facMapper = null;
	
	@Resource(name = "projMapper")
	private final ProjMapper projMapper = null;
	
	@Resource(name = "storeMapper")
	private final StoreMapper storeMapper = null;
	
	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
    
	/**
	 * @Description 添加Rules
	 * @param Rules entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addRules(Map<String,Object> entityMap)throws DataAccessException{
		
		Rules rules = queryRulesByCode(entityMap);

		if (rules != null) {

			return "{\"error\":\"编码：" + rules.getMod_code().toString() + "已存在.\"}";

		}
		
		try {
			
			rulesMapper.addRules(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addRules\"}";

		}

	}
	
	/**
	 * @Description 批量添加Rules
	 * @param  Rules entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchRules(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			rulesMapper.addBatchRules(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRules\"}";

		}
	}
	
	/**
	 * @Description 查询Rules分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryRules(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Rules> list = rulesMapper.queryRules(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询RulesByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Rules queryRulesByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return rulesMapper.queryRulesByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchRules(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = rulesMapper.deleteBatchRules(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchRules\"}";

		}
		
	}
	
		/**
	 * @Description 删除Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteRules(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				rulesMapper.deleteRules(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteRules\"}";

		}
    }
	
	/**
	 * @Description 更新Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateRules(Map<String,Object> entityMap)throws DataAccessException{

		try {
			if(entityMap.get("proj_code").equals("ACC_SUBJ")){
				List<AccSubj> subjList = accSubjMapper.queryAccSubj(entityMap);
				if(subjList.size() > 0){
					return "{\"msg\":\"会计科目已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("ACC_FUN_TYPE")){
				List<AccFunType> funList = accFunTypeMapper.queryAccFunType(entityMap);
				if(funList.size() > 0){
					return "{\"msg\":\"支出功能已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("ACC_ECO_TYPE")){
				List<AccEcoType> ecoList = accEcoTypeMapper.queryAccEcoType(entityMap);
				if(ecoList.size() > 0){
					return "{\"msg\":\"支出经济已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_CUS")){
				List<Cus> cusList = cusMapper.queryCus(entityMap);
				if(cusList.size() > 0){
					return "{\"msg\":\"客户已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_DEPT")){
				List<Dept> deptList = deptMapper.queryDept(entityMap);
				if(deptList.size() > 0){
					return "{\"msg\":\"部门已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_EMP")){
				List<Emp> empList = empMapper.queryEmp(entityMap);
				if(empList.size() > 0){
					return "{\"msg\":\"职工已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_FAC")){
				List<Fac> facList = facMapper.queryFac(entityMap);
				if(facList.size() > 0){
					return "{\"msg\":\"生产厂商已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_PROJ")){
				List<Proj> projList = projMapper.queryProj(entityMap);
				if(projList.size() > 0){
					return "{\"msg\":\"项目已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_STORE")){
				List<Store> storeList = storeMapper.queryStore(entityMap);
				if(storeList.size() > 0){
					return "{\"msg\":\"仓库已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			if(entityMap.get("proj_code").equals("HOS_SUP")){
				List<Sup> supList = supMapper.querySup(entityMap);
				if(supList.size() > 0){
					return "{\"msg\":\"供应商已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}
			
			/*if(entityMap.get("proj_code").equals("BUDG_SUBJ")){
				List<BudgIncomeSubj> budgIncome = budgIncomeSubjMapper.queryBudgIncomeSubj(entityMap);
				if(budgIncome.size() > 0){
					return "{\"msg\":\"收入预算科目已存在数据，不能修改.\",\"state\":\"false\"}";
				}
			}*/
			rulesMapper.updateRules(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRules\"}";

		}
	}
	
	/**
	 * @Description 批量更新Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchRules(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			rulesMapper.updateBatchRules(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRules\"}";

		}
		
	}
	
	/**
	 * @Description 导入Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importRules(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	@Override
	public Map<String, Map<String, Object>> queryRulesByProjCode() throws DataAccessException{
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		return sysBaseService.getHosRulesList(mapVo);
		
	}
}
