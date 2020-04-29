/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.budg.serviceImpl.base.budgsubj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.base.budgsubj.BudgIncomeSubjTypeMapper;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgIncomeSubjType;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 收入科目类别<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("budgIncomeSubjTypeService")
public class BudgIncomeSubjTypeServiceImpl implements BudgIncomeSubjTypeService {

	private static Logger logger = Logger.getLogger(BudgIncomeSubjTypeServiceImpl.class);
	
	@Resource(name = "budgIncomeSubjTypeMapper")
	private final BudgIncomeSubjTypeMapper budgIncomeSubjTypeMapper = null;
    
	/**
	 * @Description 
	 * 收入科目类别<BR> 添加
	 * @param BudgIncomeSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("income_subj_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("income_subj_type_name").toString()));
		
		BudgIncomeSubjType budgIncomeSubjType = queryBudgIncomeSubjTypeByCode(entityMap);

		if (budgIncomeSubjType != null) {

			return "{\"error\":\"编码：" + entityMap.get("income_subj_type_code").toString() + "重复.\"}";

		}
		
		try {
			
			budgIncomeSubjTypeMapper.addBudgIncomeSubjType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBudgIncomeSubjType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量添加
	 * @param  BudgIncomeSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchBudgIncomeSubjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			budgIncomeSubjTypeMapper.addBatchBudgIncomeSubjType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchBudgIncomeSubjType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 查询 分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgIncomeSubjType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgIncomeSubjType> list = budgIncomeSubjTypeMapper.queryBudgIncomeSubjType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgIncomeSubjType> list = budgIncomeSubjTypeMapper.queryBudgIncomeSubjType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgIncomeSubjType queryBudgIncomeSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgIncomeSubjTypeMapper.queryBudgIncomeSubjTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchBudgIncomeSubjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = budgIncomeSubjTypeMapper.deleteBatchBudgIncomeSubjType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchBudgIncomeSubjType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteBudgIncomeSubjType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			budgIncomeSubjTypeMapper.deleteBudgIncomeSubjType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBudgIncomeSubjType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("income_subj_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("income_subj_type_name").toString()));	
		try {
			if(entityMap.get("income_subj_type_code").equals(entityMap.get("income_subj_type_codeOld"))){
				budgIncomeSubjTypeMapper.updateBudgIncomeSubjType(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}
			
			BudgIncomeSubjType budgIncomeSubjType = queryBudgIncomeSubjTypeByCode(entityMap);

			if (budgIncomeSubjType != null) {

				return "{\"error\":\"编码：" + entityMap.get("income_subj_type_code").toString() + "重复.\"}";

			}
			budgIncomeSubjTypeMapper.updateBudgIncomeSubjTypeById(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 错误编码  updateBudgIncomeSubjType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchBudgIncomeSubjType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			budgIncomeSubjTypeMapper.updateBatchBudgIncomeSubjType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchBudgIncomeSubjType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 导入
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
