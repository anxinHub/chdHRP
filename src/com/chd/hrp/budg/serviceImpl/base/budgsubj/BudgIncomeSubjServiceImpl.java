
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgsubj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgsubj.BudgIncomeSubjMapper;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 收入预算科目
 * @Table:
 * INCOME_BUDG_SUBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgIncomeSubjService")
public class BudgIncomeSubjServiceImpl implements BudgIncomeSubjService {

	private static Logger logger = Logger.getLogger(BudgIncomeSubjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgIncomeSubjMapper")
	private final BudgIncomeSubjMapper budgIncomeSubjMapper = null;
	// 引入DAO操作
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	/**
	 * @Description 
	 * 添加收入预算科目<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException{
		Map<String,Object> tmpMap=new HashMap<String,Object>();
		tmpMap.put("subj_name_all", entityMap.get("subj_name_all"));
		tmpMap.put("group_id", entityMap.get("group_id"));
		tmpMap.put("hos_id", entityMap.get("hos_id"));
		tmpMap.put("copy_code", entityMap.get("copy_code"));
		tmpMap.put("budg_year", entityMap.get("budg_year"));
		int a=budgIncomeSubjMapper.queryNameExist(tmpMap);
		if(a>0){
			return "{\"error\":\"科目全称重复,请重新添加.\"}";
		}
		BudgIncomeSubj incomeBudgSubj = queryBudgIncomeSubjByCode(entityMap);

		if (incomeBudgSubj != null) {

			return "{\"error\":\"科目编码已被占用,请重新添加.\"}";

		}
		String super_Code=getSuperCode(entityMap);
		Map<String,Object> map=JSONObject.parseObject(super_Code);
		if(map.size()>2){
		if(!map.get("super_code").toString().equals("")||map.get("super_code")!=null){
			entityMap.put("super_code", map.get("super_code"));
			if(!map.get("super_code").toString().equals("0")){
				Map<String,Object> updateMap=new HashMap<String, Object>();
				updateMap.put("group_id", entityMap.get("group_id"));
				updateMap.put("hos_id", entityMap.get("hos_id"));
				updateMap.put("copy_code", entityMap.get("copy_code"));
				updateMap.put("budg_year", entityMap.get("budg_year"));
				updateMap.put("subj_code", map.get("super_code"));
				updateMap.put("is_last", 0);
				
				budgIncomeSubjMapper.updateIsLast(updateMap);
			}
			
		}
		}else{
			return "{\"error\":\"保存失败，不符合编码规则\"}";
		}
		/*int count  = budgIncomeSubjMapper.queryNameExist(entityMap);
		if(count > 0){
			return "{\"error\":\"科目名称已被占用,请重新添加.\"}";
		}*/
		
		try {
			
			int state = budgIncomeSubjMapper.addBudgIncomeSubj(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addIncomeBudgSubj\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加收入预算科目<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchBudgIncomeSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgIncomeSubjMapper.addBatchBudgIncomeSubj(entityList);
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			budgIncomeSubjMapper.prcUpdateBudgIncomeSubjALL(mapVo);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchIncomeBudgSubj\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新收入预算科目<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			//BUDG_SUBJ_CODE  
			Map<String,Object> tmpMap=new HashMap<String,Object>();
			tmpMap.put("subj_name_all", entityMap.get("subj_name_all"));
			tmpMap.put("group_id", entityMap.get("group_id"));
			tmpMap.put("hos_id", entityMap.get("hos_id"));
			tmpMap.put("copy_code", entityMap.get("copy_code"));
			tmpMap.put("budg_year", entityMap.get("budg_year"));
			int a=budgIncomeSubjMapper.queryNameExist(tmpMap);
			if(a>0){
				return "{\"error\":\"科目全称重复,请重新添加.\"}";
			}
			int code=budgIncomeSubjMapper.queryBudgSubjKind(entityMap);
			if(code>0){
				return "{\"error\":\"修改失败，已被以下业务使用：预算收入科目与收费类别对应关系.\"}";
			}
			if(entityMap.get("budg_year").equals(entityMap.get("budg_yearOld")) && entityMap.get("subj_code").equals(entityMap.get("subj_codeOld"))){
				int state = budgIncomeSubjMapper.updateBudgIncomeSubj(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			}else{
				BudgIncomeSubj incomeBudgSubj = budgIncomeSubjMapper.queryBudgIncomeSubjByCode(entityMap);
				if(incomeBudgSubj != null){
					return "{\"error\":\"数据重复,请重新添加.\"}";
				}else{
					int state = budgIncomeSubjMapper.updateBudgIncomeSubjByCode(entityMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				}
			}
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新过程出错，更新失败.请联系管理员! 方法 updateIncomeBudgSubj\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新收入预算科目<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchBudgIncomeSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgIncomeSubjMapper.updateBatchBudgIncomeSubj(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchIncomeBudgSubj\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除收入预算科目<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteBudgIncomeSubj(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgIncomeSubjMapper.deleteBudgIncomeSubj(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteIncomeBudgSubj\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除收入预算科目<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchBudgIncomeSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgIncomeSubjMapper.deleteBatchBudgIncomeSubj(entityList);
			budgIncomeSubjMapper.updateBatchBudgIncomeSubjLast(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchIncomeBudgSubj\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集收入预算科目<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgIncomeSubj(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgIncomeSubj> list = budgIncomeSubjMapper.queryBudgIncomeSubj(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgIncomeSubj> list = budgIncomeSubjMapper.queryBudgIncomeSubj(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象收入预算科目<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgIncomeSubj queryBudgIncomeSubjByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgIncomeSubjMapper.queryBudgIncomeSubjByCode(entityMap);
	}
	
	/**
	 * 根据输入的科目编码 自动生成其相应的 科目级次、上级编码、科目全称
	 * @param mapVo
	 * @return
	 */
	public String  getSuperCode(@RequestParam Map<String, Object> mapVo){
	    
		if(mapVo.get("rules") == null){
			 return "{\"error\":\"收入预算科目编码规则未设置,不允许添加！\"}";
		}
		String super_cod="TOP";
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
	    		 mapVo.put("super_code",super_cod );
	    	 }else{
	    		 int super_level =  subj_level - 1;//上级级次
	        	 int subPosition = position.get(super_level);//上级级次位置
	        	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
	        	 mapVo.put("super_code", super_code);
	        	 //上级科目编码、科目全称是否存在
	        	  BudgIncomeSubj superSubj = budgIncomeSubjMapper.qureySurp_code(mapVo);
	        	 
	        	 if(superSubj != null ){
	        		 mapVo.put("super_name", superSubj.getSubj_name_all());
	        	 }else{
	        		 return "{\"error\":\"输入科目编码的上级科目编码不存在，不允许添加、修改，请添加上级科目后再操作！\"}";
	        	 }
	    	 }
	    	 return ChdJson.toJson(mapVo) ;
	    }else{
	    	return "{\"error\":\"添加失败 不符合编码规则 请重新输入！\"}";
	    }
	
	}
	
	/**
	 * 继承上一年度的收入预算科目及与会计科目的对应关系
	 * @param entityMap
	 * @return
	 */
	public String extendBudgIncomeSubj(Map<String, Object> entityMap) {
		try {
			//查询当前年度数据是都已经存在  
			int count = budgIncomeSubjMapper.queryDataExist(entityMap);
			//已存在 则不允许继承
			if(count != 0 ){
				
				return "{\"msg\":\"当前年度存在收入预算科目数据，无法继承.\"}";
				
			}else{
				//根据当前年度  获取上年度数据
				entityMap.put("budg_year", Integer.parseInt(String.valueOf(entityMap.get("budg_year")))-1);
				
				//获取上年数据  并在SQL中将年度换成所选年度 形成数据集合  
				List<Map<String, Object>> dataLlist = budgIncomeSubjMapper.queryLastYearData(entityMap);
				
				//若取到数据  则批量添加进表中
				if(dataLlist.size() > 0){
					
					int state = budgIncomeSubjMapper.addBatchBudgIncomeSubj(dataLlist);
					return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";	
					
				}else{
					return "{\"msg\":\"上一年度不存在支出预算科目及与会计科目的对应关系数据，无法继承.\"}";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
	    	return "{\"error\":\"继承过程出错，继承失败！方法 extendIncomeBudgSubj\"}";
		}
	}
	public int queryDelDate(Map<String, Object> entityMap) {
		int code=budgIncomeSubjMapper.queryBudgSubjKind(entityMap);
		return code;
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
	public String budgBathUpdate(List<Map<String, Object>> listVo)
			throws DataAccessException {
		
		try {
			
			//BUDG_SUBJ_CODE  
			
			
					int state = budgIncomeSubjMapper.budgBathUpdate(listVo);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新过程出错，更新失败.请联系管理员! 方法 updateIncomeBudgSubj\"}";

		}	
		
	}
	public Map<String, Object> querySup(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgIncomeSubjMapper.querySup(entityMap);
	}
	public Map<String, Object> queryAccTypeCodeByCode(Map<String, Object> entityMap) {
		// TODO Auto-generated method stub
		return budgIncomeSubjMapper.queryAccTypeCodeByCode(entityMap);
	}

}
