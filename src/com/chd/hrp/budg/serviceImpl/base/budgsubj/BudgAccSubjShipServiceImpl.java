
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

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.budg.dao.base.budgsubj.BudgAccSubjShipMapper;
import com.chd.hrp.budg.dao.base.budgsubj.BudgCostSubjMapper;
import com.chd.hrp.budg.dao.base.budgsubj.BudgIncomeSubjMapper;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgIncomeTypeSet;
import com.chd.hrp.budg.service.base.budgsubj.BudgAccSubjShipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 预算科目与会计科目对应关系表
 * @Table:
 * BUDG_ACC_SUBJ_SHIP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgAccSubjShipService")
public class BudgAccSubjShipServiceImpl implements BudgAccSubjShipService {

	private static Logger logger = Logger.getLogger(BudgAccSubjShipServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgAccSubjShipMapper")
	private final BudgAccSubjShipMapper budgAccSubjShipMapper = null;
    
	@Resource(name = "budgIncomeSubjMapper")
	private final BudgIncomeSubjMapper  budgIncomeSubjMapper = null;
	
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper  accSubjMapper = null;
	
	@Resource(name = "budgCostSubjMapper")
	private final BudgCostSubjMapper  budgCostSubjMapper = null;
	
	
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
					BudgAccSubjShip budgAccSubjShip = budgAccSubjShipMapper.queryBudgAccSubjShipByCode(item);
					if(budgAccSubjShip!=null){
						 str += "预算科目:"+budgAccSubjShip.getSubj_name()+"和 会计科目:"+budgAccSubjShip.getAcc_subj_code2()+"  ";
					}
				}
				//判断str是否为空,为空正常添加   不为空则返回错误信息
				if(!"".equals(str)){
					return "{\"error\":\"所选年度以下:"+str+"数据对应关系已存在.\",\"state\":\"false\"}";
				}else{
					budgAccSubjShipMapper.addBatchBudgAccSubjShip(addList);
				}
			}
			
			if(updateList.size()>0){
				
				budgAccSubjShipMapper.updateBatchBudgAccSubjShip(updateList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * @Description 
	 * 添加预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象预算科目与会计科目对应关系表
		BudgAccSubjShip budgAccSubjShip = queryBudgAccSubjShipByCode(entityMap);

		if (budgAccSubjShip != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgAccSubjShipMapper.addBudgAccSubjShip(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBudgAccSubjShip\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加预算科目与会计科目对应关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchBudgAccSubjShip(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgAccSubjShipMapper.addBatchBudgAccSubjShip(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchBudgAccSubjShip\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBudgAccSubjShip(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("acc_subj_code2").equals(entityMap.get("acc_subj_code2Old"))){
				
				int conut = budgAccSubjShipMapper.queryIsExist(entityMap);
				
				if (conut > 0) {

					return "{\"error\":\"对应关系数据已存在.\"}";

				}else{

					int state = budgAccSubjShipMapper.updateBudgAccSubjShip(entityMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				}
				
			}else{
				int conut = budgAccSubjShipMapper.queryIsExist(entityMap);
				
				if (conut > 0) {

					return "{\"error\":\"对应关系数据已存在.\"}";

				}else{
					int state = budgAccSubjShipMapper.updateBudgAccSubjShipById(entityMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				}
			}
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新过程出错,更新失败  请联系管理员! 方法 updateBudgAccSubjShip\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新预算科目与会计科目对应关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchBudgAccSubjShip(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgAccSubjShipMapper.updateBatchBudgAccSubjShip(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchBudgAccSubjShip\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteBudgAccSubjShip(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgAccSubjShipMapper.deleteBudgAccSubjShip(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBudgAccSubjShip\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除预算科目与会计科目对应关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchBudgAccSubjShip(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgAccSubjShipMapper.deleteBatchBudgAccSubjShip(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchBudgAccSubjShip\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgAccSubjShip(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgAccSubjShip> list = budgAccSubjShipMapper.queryBudgAccSubjShip(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgAccSubjShip> list = budgAccSubjShipMapper.queryBudgAccSubjShip(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象预算科目与会计科目对应关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgAccSubjShip queryBudgAccSubjShipByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgAccSubjShipMapper.queryBudgAccSubjShipByCode(entityMap);
	}
	/**
	 * 由会计科目对照生成预算科目
	 * @param mapVo
	 * @return
	 */
	public String setBudgAccSubjShip(Map<String, Object> mapVo) {
		try {
			List budgList = new ArrayList();
			if(mapVo.get("subj_type").equals("04")){
				 budgList = budgIncomeSubjMapper.queryBudgIncomeSubj(mapVo);
				 mapVo.put("subj_type_code", "04");
			}	
			
			if(mapVo.get("subj_type").equals("05")){
				 budgList = budgCostSubjMapper.queryBudgCostSubj(mapVo); 
				 mapVo.put("subj_type_code", "05");
			}
			if(budgList.size() > 0){
				return "{\"msg\":\"所选年度存在预算科目数据，不能由会计科目生成，如想进行此操作，请先删除所选年度预算科目数据.\",\"state\":\"true\"}";
			}
			List<Map<String,Object>>  budgSubjList = new ArrayList<Map<String,Object>>();
			
			List<Map<String,Object>>  subjShipList = new ArrayList<Map<String,Object>>();
			
			mapVo.put("acc_year", mapVo.get("budg_year"));
			
			List<AccSubj> list = accSubjMapper.queryAccSubj(mapVo);
			if(list.size() >0){
				for(AccSubj item : list){

					Map<String,Object> map = new HashMap<String,Object>();
					
					Map<String,Object> relationMap = new HashMap<String,Object>();
					
					map.put("group_id", item.getGroup_id());
					map.put("hos_id", item.getHos_id());
					map.put("copy_code", item.getCopy_code());
					map.put("budg_year", item.getAcc_year());
					map.put("subj_code", item.getSubj_code());
					map.put("subj_name", item.getSubj_name());
					map.put("subj_name_all", item.getSubj_name_all());
					map.put("super_code", item.getSuper_code());
					map.put("subj_level", item.getSubj_level());
					map.put("subj_nature", item.getSubj_type_code());
					map.put("is_last", item.getIs_last());
					map.put("spell_code", item.getSpell_code());
					map.put("wbx_code", item.getWbx_code());
					
					budgSubjList.add(map);
					
					relationMap.put("group_id", item.getGroup_id());
					relationMap.put("hos_id", item.getHos_id());
					relationMap.put("copy_code", item.getCopy_code());
					relationMap.put("budg_year", item.getAcc_year());
					relationMap.put("subj_code", item.getSubj_code());
					relationMap.put("acc_subj_code2", item.getSubj_code());
					relationMap.put("subj_type", item.getSubj_type_code());
					
					subjShipList.add(relationMap);
					
				}
			}else{
				return "{\"msg\":\"无会计科目数据，无法生成.\",\"state\":\"true\"}";
			}
			
			if(mapVo.get("subj_type").equals("04")){
				int count = budgIncomeSubjMapper.addBatchBudgIncomeSubj(budgSubjList);
			}
			
			if(mapVo.get("subj_type").equals("05")){
				int count = budgCostSubjMapper.addBatchBudgCostSubj(budgSubjList);
			}
			int num = budgAccSubjShipMapper.addBatchBudgAccSubjShip(subjShipList);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成过程出错，生成数据失败 ，请联系管理员! 方法 setBudgAccSubjShip\"}";
		}
	}
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 添加页面 会计科目 下拉框
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryAccSubj(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(budgAccSubjShipMapper.queryAccSubj(mapVo, rowBounds));
	}
	/**
	 * 添加页面 收入预算科目  下拉框
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryBudgIncomeTypeSet(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(budgAccSubjShipMapper.queryBudgIncomeTypeSet(mapVo, rowBounds));
	}
	
	/**
	 * 添加页面 收入预算科目  下拉框
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryBudgCostTypeSet(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(budgAccSubjShipMapper.queryBudgCostTypeSet(mapVo, rowBounds));
	}
	/**
	 * 查询数据是否已存在
	 */
	@Override
	public int queryIsExist(Map<String, Object> mapVo)throws DataAccessException {
		return budgAccSubjShipMapper.queryIsExist(mapVo);
	}
	/**
	 * 查询会计科目编码是否存在
	 */
	@Override
	public int queryAccSubjCodeExist(Map<String, Object> mapVo)	throws DataAccessException {
		return budgAccSubjShipMapper.queryAccSubjCodeExist(mapVo);
	}
	
	/**
	 * @Description 
	 * 增量生成 预算收入科目  和 预算收入科目与会计科目对应关系
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String generate(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//查询
			List<Map<String,Object>> dataList = budgAccSubjShipMapper.queryDataFromAccSubj(entityMap);
			
			if(dataList == null || dataList.isEmpty()){
				return "{\"error\":\""+entityMap.get("budg_year")+"年度下会计科目不存在,请检查数据后操作!\"}";
			}
			
			if("04".equals(entityMap.get("subj_type"))){
				//覆盖生成收入预算科目  先删除表中原有数据
				budgIncomeSubjMapper.deleteBudgIncomeSubj(entityMap);
				//删除原有数据后  将从会计科目中取到的数据 批量插入收入科目表中
				budgIncomeSubjMapper.addBatchBudgIncomeSubj(dataList);
				
			}else if("05".equals(entityMap.get("subj_type"))){
				//覆盖生成收入预算科目  先删除表中原有数据
				budgCostSubjMapper.deleteBudgCostSubj(entityMap);
				//删除原有数据后  将从会计科目中取到的数据 批量插入收入科目表中
				budgCostSubjMapper.addBatchBudgCostSubj(dataList);
			}
			
			budgAccSubjShipMapper.deleteBudgAccSubjShip(entityMap);
			
			budgAccSubjShipMapper.addBatchGenerate(dataList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchBudgAccSubjShip\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 继承
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String extend(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//查询当前年度数据是都已经存在  
			int count = budgAccSubjShipMapper.queryDataExist(entityMap);
			//已存在 则不允许继承
			if(count != 0 ){
				
				return "{\"msg\":\"当前年度存在收入预算科目数据，无法继承.\"}";
				
			}else{
				//根据当前年度  获取上年度数据
				entityMap.put("budg_year", Integer.parseInt(String.valueOf(entityMap.get("budg_year")))-1);
				
				//获取上年数据  并在SQL中将年度换成所选年度 形成数据集合  
				List<Map<String, Object>> dataLlist = budgAccSubjShipMapper.queryLastYearData(entityMap);
				
				//若取到数据  则批量添加进表中
				if(dataLlist.size() > 0){
					
					int state = budgAccSubjShipMapper.addBatchBudgAccSubjShip(dataLlist);
					return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";	
					
				}else{
					return "{\"msg\":\"上一年度不存在收入预算科目及与会计科目的对应关系数据，无法继承.\"}";
				}
			}
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchBudgAccSubjShip\"}";
			
		}
		
	}
}
