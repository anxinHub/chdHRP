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

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.base.budgsubj.BudgCostTypeSetMapper;
import com.chd.hrp.budg.entity.BudgCostTypeSet;
import com.chd.hrp.budg.entity.BudgCostTypeSet;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostTypeSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 支出科目类别<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("budgCostTypeSetService")
public class BudgCostTypeSetServiceImpl implements BudgCostTypeSetService {

	private static Logger logger = Logger.getLogger(BudgCostTypeSetServiceImpl.class);
	
	@Resource(name = "budgCostTypeSetMapper")
	private final BudgCostTypeSetMapper budgCostTypeSetMapper = null;
    
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		String str="";
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				addList.add(item) ;
				
			}else{ //修改
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				for(Map<String, Object> item : addList) {
					BudgCostTypeSet budgCostTypeSet = budgCostTypeSetMapper.queryBudgCostTypeSetByCode(item);
					if(budgCostTypeSet!=null){
						 str += "科目编码:"+budgCostTypeSet.getSubj_name()+"; 科目类型:"+budgCostTypeSet.getType_name()+"  ";
					}
				}
				//判断str是否为空,为空正常添加   不为空则返回错误信息
				if(!"".equals(str)){
					return "{\"error\":\"所选年度以下:"+str+"数据对应关系已存在.\",\"state\":\"false\"}";
				}else{
					budgCostTypeSetMapper.addBatchBudgCostTypeSet(addList);
				}
			}
			
			if(updateList.size()>0){
				
				budgCostTypeSetMapper.updateBatchBudgCostTypeSet(updateList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 添加
	 * @param BudgCostTypeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cost_subj_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cost_subj_type_name").toString()));
		
		BudgCostTypeSet budgCostTypeSet = budgCostTypeSetMapper.queryBudgCostTypeSetByCode(entityMap);

		if (budgCostTypeSet != null) {

			return "{\"error\":\"编码：" + entityMap.get("cost_subj_type_code").toString() + "重复.\"}";

		}
		
		try {
			
			budgCostTypeSetMapper.addBudgCostTypeSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBudgCostTypeSet\"}";

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
	public String addBatchBudgCostTypeSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			budgCostTypeSetMapper.addBatchBudgCostTypeSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchBudgCostTypeSet\"}";

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
	public String queryBudgCostTypeSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCostTypeSet> list = budgCostTypeSetMapper.queryBudgCostTypeSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCostTypeSet> list = budgCostTypeSetMapper.queryBudgCostTypeSet(entityMap, rowBounds);
			
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
	public BudgCostTypeSet queryBudgCostTypeSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgCostTypeSetMapper.queryBudgCostTypeSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchBudgCostTypeSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = budgCostTypeSetMapper.deleteBatchBudgCostTypeSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchBudgCostTypeSet\"}";

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
  public String deleteBudgCostTypeSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			budgCostTypeSetMapper.deleteBudgCostTypeSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBudgCostTypeSet\"}";

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
	public String updateBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			if(entityMap.get("type_codeOld").equals(entityMap.get("type_code")) 
					&& entityMap.get("subj_codeOld").equals(entityMap.get("subj_code")) ){
				return "{\"error\":\"数据没有改动,无需修改.\"}";
			}
			BudgCostTypeSet budgCostTypeSet = queryBudgCostTypeSetByCode(entityMap);

			if (budgCostTypeSet != null) {

				return "{\"error\":\"数据已存在.\"}";

			}
			budgCostTypeSetMapper.updateBudgCostTypeSetById(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 错误编码  updateBudgCostTypeSet\"}";

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
	public String updateBatchBudgCostTypeSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			budgCostTypeSetMapper.updateBatchBudgCostTypeSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchBudgCostTypeSet\"}";

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
	public String importBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 支出预算科目下拉框
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryBudgCostSubj(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(budgCostTypeSetMapper.queryBudgCostSubj(mapVo, rowBounds));
	}
	/**
	 * 支出预算科目类别下拉框
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryBudgCostSubjType(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(budgCostTypeSetMapper.queryBudgCostSubjType(mapVo, rowBounds));
	}
	
	@Override
	/**
	 * 查询 支出预算科目类别数据是否已经存在
	 */
	public int queryIsExist(Map<String, Object> mapVo) throws DataAccessException{
		return budgCostTypeSetMapper.queryIsExist(mapVo);
	}

	/**
	 * 继承
	 */
	@Override
	public String extendBudgCostTypeSet(Map<String, Object> mapVo) throws DataAccessException {
		try {
			//当前年度数据
			List<BudgCostTypeSet> listNow = budgCostTypeSetMapper.queryBudgCostTypeSet(mapVo);

			if (listNow.size() >0 ) {

				return "{\"msg\":\"当前年度已存在支出科目类别维护数据.不能继承。请删除当前年度数据后再操作\"}";

			}
			
			mapVo.put("budg_year", Integer.parseInt(String.valueOf(mapVo.get("budg_year")))-1);
			List<BudgCostTypeSet> listPre = budgCostTypeSetMapper.queryBudgCostTypeSet(mapVo);
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>() ;

			if (listPre.size() > 0 ) {
				for(BudgCostTypeSet item : listPre){
					Map<String,Object> map  = new HashMap<String,Object>();
					map.put("group_id", item.getGroup_id());
					map.put("hos_id", item.getHos_id());
					map.put("copy_code", item.getCopy_code());
					map.put("budg_year", Integer.parseInt(String.valueOf(item.getBudg_year()))+1);
					map.put("type_code", item.getType_code());
					map.put("subj_code", item.getSubj_code());
					
					list.add(map);
				}

			}else{
				return "{\"msg\":\"上一年度已不存在支出科目类别维护数据.不能继承\"}";
			}
			
			budgCostTypeSetMapper.addBatchBudgCostTypeSet(list);

			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承过程出错，继承失败 请联系管理员! 错误编码  extendBudgCostTypeSet\"}";

		}
	}
}
