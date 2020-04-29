/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgcharge;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.base.budgcharge.BudgChargeStandardDictMapper;
import com.chd.hrp.budg.entity.BudgChargeStandardDict;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStandardDictService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 是否停用（IS_STOP):0否，1是
费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室
 * @Table:
 * BUDG_CHARGE_STANDARD_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgChargeStandardDictService")
public class BudgChargeStandardDictServiceImpl implements BudgChargeStandardDictService {

	private static Logger logger = Logger.getLogger(BudgChargeStandardDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgChargeStandardDictMapper")
	private final BudgChargeStandardDictMapper budgChargeStandardDictMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 
	 * 添加  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		String  str = "";
		//获取对象 费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室 
		Map<String,Object> budgChargeStandardDict = queryByCode(entityMap);

		if (budgChargeStandardDict != null) {
			
			str += "费用标准编码已被占用;";
		}
		
		int count = budgChargeStandardDictMapper.queryNameExist(entityMap);
		
		if(count > 0 ){
			str += "费用标准名称已被占用;";
		}
		if(str != ""){
			return "{\"error\":\""+str+""+"请重新添加.\"}";
		}
		try {
			
			int state = budgChargeStandardDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败  请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加 费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>  
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgChargeStandardDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>  
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int count = budgChargeStandardDictMapper.queryNameExist(entityMap);
			
			if(count > 0 ){
				return "{\"error\":\"费用标准名称已被占用,请重新添加.\"}";
			}else{
				entityMap.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(entityMap.get("charge_stan_name"))));
				
				entityMap.put("wbx_code", StringTool.toWuBi(String.valueOf(entityMap.get("charge_stan_name"))));
			}
			
			
			int state = budgChargeStandardDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败  请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgChargeStandardDictMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败  请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
    		
			
			int state = budgChargeStandardDictMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败   请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "BUDG_CHARGE_STANDARD_DICT");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", entityList.get(0).get("copy_code"));
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+=mapVo.get("charge_stan_code")+",";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
					sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的单病种字典被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			budgChargeStandardDictMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象是否停用（IS_STOP):0否，1是 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgChargeStandardDict> list = (List<BudgChargeStandardDict>)budgChargeStandardDictMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgChargeStandardDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgChargeStandardDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败   请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>  
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgChargeStandardDict> list = (List<BudgChargeStandardDict>)budgChargeStandardDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgChargeStandardDict> list = (List<BudgChargeStandardDict>)budgChargeStandardDictMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgChargeStandardDictMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgChargeStandardDict
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgChargeStandardDictMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgChargeStandardDict>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgChargeStandardDictMapper.queryExists(entityMap);
	}
	
	/**
	 * 判断费用标准名称是否被占用
	 */
	@Override
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException {
		return budgChargeStandardDictMapper.queryNameExist(entityMap);
	}
	/**
	 * 查询费用标准性质编码是否存在
	 */
	@Override
	public int queryStanNatureExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgChargeStandardDictMapper.queryStanNatureExist(mapVo);
	}
	@Override
	public String queryChargeStanDeptSet(Map<String, Object> entityMap)	throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = budgChargeStandardDictMapper.queryChargeStanDeptSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = budgChargeStandardDictMapper.queryChargeStanDeptSet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
}
