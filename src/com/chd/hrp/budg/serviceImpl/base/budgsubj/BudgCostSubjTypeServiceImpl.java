/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.budg.serviceImpl.base.budgsubj;

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
import com.chd.hrp.budg.dao.base.budgsubj.BudgCostSubjTypeMapper;
import com.chd.hrp.budg.entity.BudgCostSubjType;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjTypeService;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 支出科目类别<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("budgCostSubjTypeService")
public class BudgCostSubjTypeServiceImpl implements BudgCostSubjTypeService {

	private static Logger logger = Logger.getLogger(BudgCostSubjTypeServiceImpl.class);
	
	@Resource(name = "budgCostSubjTypeMapper")
	private final BudgCostSubjTypeMapper budgCostSubjTypeMapper = null;
    
	/**
	 * @Description 
	 * 支出科目类别<BR> 添加
	 * @param BudgCostSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cost_subj_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cost_subj_type_name").toString()));
		
		BudgCostSubjType budgCostSubjType = queryBudgCostSubjTypeByCode(entityMap);

		if (budgCostSubjType != null) {

			return "{\"error\":\"编码：" + entityMap.get("cost_subj_type_code").toString() + "重复.\"}";

		}
		
		try {
			
			budgCostSubjTypeMapper.addBudgCostSubjType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBudgCostSubjType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量添加
	 * @param  BudgCostSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchBudgCostSubjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			budgCostSubjTypeMapper.addBatchBudgCostSubjType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchBudgCostSubjType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 查询 分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgCostSubjType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCostSubjType> list = budgCostSubjTypeMapper.queryBudgCostSubjType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCostSubjType> list = budgCostSubjTypeMapper.queryBudgCostSubjType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgCostSubjType queryBudgCostSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgCostSubjTypeMapper.queryBudgCostSubjTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchBudgCostSubjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = budgCostSubjTypeMapper.deleteBatchBudgCostSubjType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchBudgCostSubjType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteBudgCostSubjType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			budgCostSubjTypeMapper.deleteBudgCostSubjType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBudgCostSubjType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cost_subj_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cost_subj_type_name").toString()));	
		try {
			if(entityMap.get("cost_subj_type_code").equals(entityMap.get("cost_subj_type_codeOld"))){
				budgCostSubjTypeMapper.updateBudgCostSubjType(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}
			
			BudgCostSubjType budgCostSubjType = queryBudgCostSubjTypeByCode(entityMap);

			if (budgCostSubjType != null) {

				return "{\"error\":\"编码：" + entityMap.get("cost_subj_type_code").toString() + "重复.\"}";

			}
			budgCostSubjTypeMapper.updateBudgCostSubjTypeById(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 错误编码  updateBudgCostSubjType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchBudgCostSubjType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			budgCostSubjTypeMapper.updateBatchBudgCostSubjType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchBudgCostSubjType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 导入
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
