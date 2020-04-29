
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgsubj;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgsubj.BudgAccSubjShipMapper;
import com.chd.hrp.budg.dao.base.budgsubj.BudgCostSubjMapper;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 数据
 * @Table:
 * COST_BUDG_SUBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgCostSubjService")
public class BudgCostSubjServiceImpl implements BudgCostSubjService {

	private static Logger logger = Logger.getLogger(BudgCostSubjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCostSubjMapper")
	private final BudgCostSubjMapper budgCostSubjMapper = null;
	
	@Resource(name = "budgAccSubjShipMapper")
	private final BudgAccSubjShipMapper budgAccSubjShipMapper = null;
	// 引入DAO操作
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("subj_name", entityMap.get("subj_name"));
		map.put("budg_year", entityMap.get("budg_year"));
		
		int a=budgCostSubjMapper.qureyNameExist(map);
		if(a>0){
			return "{\"error\":\"科目名称重复,请重新添加.\"}";
		}
		//获取对象
		BudgCostSubj costBudgSubj = queryBudgCostSubjByCode(entityMap);

		if (costBudgSubj != null) {

			return "{\"error\":\"科目编码已被占用,请重新添加.\"}";
		}
		
		/*int count = budgCostSubjMapper.qureyNameExist(entityMap);
		
		if(count > 0 ){
			return "{\"error\":\"科目名称已被占用,请重新添加.\"}";
		}*/
		
		try {
			
				int state = budgCostSubjMapper.addBudgCostSubj(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBudgCostSubj\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchBudgCostSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCostSubjMapper.addBatchBudgCostSubj(entityList);
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			budgCostSubjMapper.prcUpdateBudgCostSubjALL(mapVo);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchBudgCostSubj\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("budg_year").equals(entityMap.get("budg_yearOld")) && entityMap.get("subj_code").equals(entityMap.get("subj_codeOld"))){
				int state = budgCostSubjMapper.updateBudgCostSubj(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			}else{
				BudgCostSubj costBudgSubj = budgCostSubjMapper.queryBudgCostSubjByCode(entityMap);
				if(costBudgSubj != null){
					return "{\"error\":\"数据重复,请重新添加.\"}";
				}else{
					int state = budgCostSubjMapper.updateBudgCostSubjByCode(entityMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				}
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新过程出错，更新失败  请联系管理员! 方法 updateBudgCostSubj\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchBudgCostSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCostSubjMapper.updateBatchBudgCostSubj(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchBudgCostSubj\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteBudgCostSubj(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgCostSubjMapper.deleteBudgCostSubj(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBudgCostSubj\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchBudgCostSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCostSubjMapper.deleteBatchBudgCostSubj(entityList);
			budgCostSubjMapper.updateBatchBudgCostSubjLast(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchBudgCostSubj\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgCostSubj(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCostSubj> list = budgCostSubjMapper.queryBudgCostSubj(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCostSubj> list = budgCostSubjMapper.queryBudgCostSubj(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgCostSubj queryBudgCostSubjByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgCostSubjMapper.queryBudgCostSubjByCode(entityMap);
	}
	
	@Override
	/**
	 * 根据输入的科目编码 自动生成其相应的 科目级次、上级编码
	 * @param mapVo
	 * @return
	 */
	public String  getSuperCode(@RequestParam Map<String, Object> mapVo){
	    
		if(mapVo.get("rules") == null){
			 return "{\"error\":\"支出预算科目编码规则未设置,不允许添加！\"}";
		}
		
	    String rules = (String)mapVo.get("rules");//编码规则4-2-2....
	    
	    String subj_code = (String)mapVo.get("subj_code");//科目编码
	    
	    String [] ruless  = rules.split("-");
	    
	    Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
	    
	    Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
	    
	    int super_num = 0;
	    
	    for(int i = 0; i < ruless.length; i++){
	    	int num = Integer.parseInt(ruless[i].replace(" ", ""));
	    	super_num += num;
	    	maxNumMap.put(super_num, i + 1);
	    	position.put(i + 1, super_num);
	    }
	    
	    if(maxNumMap.containsKey(subj_code.length())){//编码匹配
	    	 int subj_level = maxNumMap.get(subj_code.length());
	    	 mapVo.put("subj_level", subj_level);
	    	 if(subj_level == 1){
	    		 mapVo.put("super_code", "0");
	    	 }else{
	    		 int super_level =  subj_level - 1;//上级级次
	        	 int subPosition = position.get(super_level);//上级级次位置
	        	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
	        	 mapVo.put("super_code", super_code);
	        	//上级科目编码、科目全称是否存在

	        	 BudgCostSubj superSubj= budgCostSubjMapper.qureySurp_code(mapVo);
	        		        	 
	        	 if(superSubj != null ){
	        		 mapVo.put("super_name", superSubj.getSubj_name_all());
	        	 }else{
	        		 return "{\"error\":\"输入部门编码的上级科目编码不存在，不允许添加、修改，请添加上级科目后再操作！\"}";
	        	 }
	    	 }
	    	 return ChdJson.toJson(mapVo) ;
	    }else{
	    	return "{\"error\":\"添加失败 不符合编码规则 请重新输入！\"}";
	    }
	
	}
	
	/**
	 * 继承上一年度的支出预算科目
	 * @param entityMap
	 * @return
	 */
	public String extendBudgCostSubj(Map<String, Object> entityMap) {
		try {
			//查询当前年度数据是都已经存在  
			int count = budgCostSubjMapper.queryDataExist(entityMap);
			//已存在 则不允许继承
			if(count != 0 ){
				
				return "{\"msg\":\"当前年度存在收入预算科目数据，无法继承.\"}";
				
			}else{
				//根据当前年度  获取上年度数据
				entityMap.put("budg_year", Integer.parseInt(String.valueOf(entityMap.get("budg_year")))-1);
				
				//获取上年数据  并在SQL中将年度换成所选年度 形成数据集合  
				List<Map<String, Object>> dataLlist = budgCostSubjMapper.queryLastYearData(entityMap);
				
				//若取到数据  则批量添加进表中
				if(dataLlist.size() > 0){
					
					int state = budgCostSubjMapper.addBatchBudgCostSubj(dataLlist);
					return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";	
					
				}else{
					return "{\"msg\":\"上一年度不存在支出预算科目及与会计科目的对应关系数据，无法继承.\"}";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
	    	return "{\"error\":\"继承过程出错，继承失败！方法 extendBudgCostSubj\"}";
		}
	}
	/**
	 * 批量更新支出预算科目 科目类别
	 * @param entityMap
	 * @return
	 */
	@Override
	public String updateBatchCostType(List<Map<String, Object>> listVo)
			throws DataAccessException {
		
		try {
			
			int state = budgCostSubjMapper.updateBatchCostType(listVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新过程出错，更新失败.请联系管理员! 方法 updateBatchCostType\"}";

		}	
		
	}
	@Override
	public String isExistsDataByTable(String tableName, Object id)
			throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("dict_id_str", id);
		entityMap.put("dict_code", tableName.toUpperCase());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		entityMap.put("p_flag", "1");
		sysFunUtilMapper.querySysDictDelCheck(entityMap);

		return entityMap.get("reNote") != null ? entityMap.get("reNote").toString() : null;
	}
	@Override
	public Map<String, Object> querySup(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgCostSubjMapper.querySup(entityMap);
	}
}
