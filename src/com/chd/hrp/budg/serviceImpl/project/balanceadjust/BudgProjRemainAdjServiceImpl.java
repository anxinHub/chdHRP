/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.balanceadjust;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.budg.dao.project.balanceadjust.BudgProjReAdjDetMapper;
import com.chd.hrp.budg.dao.project.balanceadjust.BudgProjRemainAdjMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjYearMapper;
import com.chd.hrp.budg.entity.BudgProjRemainAdj;
import com.chd.hrp.budg.service.project.balanceadjust.BudgProjRemainAdjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 状态（STATE），取自系统字典表
 * “01新建、02已提交、03已审核”
 * @Table:
 * BUDG_PROJ_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjRemainAdjService")
public class BudgProjRemainAdjServiceImpl implements BudgProjRemainAdjService {

	private static Logger logger = Logger.getLogger(BudgProjRemainAdjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjRemainAdjMapper")
	private final BudgProjRemainAdjMapper budgProjRemainAdjMapper = null;
    @Resource(name = "budgProjReAdjDetMapper")
    private final BudgProjReAdjDetMapper budgProjReAdjDetMapper = null;
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			String str = "";
			
			//BUDG_PROJ_RE_ADJ_DET 经费调整明细  添加用List
			List<Map<String,Object>> adjList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("DetailData")));
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				//封装调整明细 数据信息
				Map<String,Object> adjMap = new HashMap<String,Object>();
				
				adjMap.put("group_id", entityMap.get("group_id"));
				adjMap.put("hos_id", entityMap.get("hos_id"));
				adjMap.put("copy_code", entityMap.get("copy_code"));
				adjMap.put("adj_code", entityMap.get("adj_code"));//经费余额调整单号
				adjMap.put("proj_id",jsonObj.get("proj_name").toString().split(",")[0]);//项目ID
				adjMap.put("source_id",jsonObj.get("source_id"));//资金来源ID
				adjMap.put("remain_adj",  jsonObj.get("remain_adj"));//调整金额
				adjList.add(adjMap);
			}	
			
			budgProjRemainAdjMapper.add(entityMap);
			 
			budgProjReAdjDetMapper.addBatch(adjList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjRemainAdjMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			String str = "";
			
			//BUDG_PROJ_RE_ADJ_DET 经费调整明细  添加用List
			List<Map<String,Object>> adjList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("DetailData")));
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				//封装调整明细 数据信息
				Map<String,Object> adjMap = new HashMap<String,Object>();
				
				adjMap.put("group_id", entityMap.get("group_id"));
				adjMap.put("hos_id", entityMap.get("hos_id"));
				adjMap.put("copy_code", entityMap.get("copy_code"));
				adjMap.put("adj_code", entityMap.get("adj_code"));//经费余额调整单号
				adjMap.put("proj_id",jsonObj.get("proj_name").toString().split(",")[0]);//项目ID
				adjMap.put("source_id",jsonObj.get("source_id"));//资金来源ID
				adjMap.put("remain_adj",  jsonObj.get("remain_adj"));//调整金额
				adjList.add(adjMap);
			}	
			
			
			budgProjRemainAdjMapper.update(entityMap);
			budgProjReAdjDetMapper.delete(entityMap);
			budgProjReAdjDetMapper.addBatch(adjList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgProjRemainAdjMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除状态
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgProjRemainAdjMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
				budgProjReAdjDetMapper.deleteBatch(entityList);
				
				budgProjRemainAdjMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgProjRemainAdj> list = (List<BudgProjRemainAdj>)budgProjRemainAdjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgProjRemainAdjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgProjRemainAdjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgProjRemainAdj> list = (List<BudgProjRemainAdj>)budgProjRemainAdjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgProjRemainAdj> list = (List<BudgProjRemainAdj>)budgProjRemainAdjMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjRemainAdjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取状态 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgProjRemainAdj
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjRemainAdjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgProjRemainAdj>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjRemainAdjMapper.queryExists(entityMap);
	}
	@Override
	public String queryUsableAmount(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return budgProjRemainAdjMapper.queryUsableAmount(entityMap);
	}

	/**
	 * @Description 
	 * 生成 预算调整单号
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String setBudgApplyCode(Map<String,Object> mapVo) throws DataAccessException{
		
		mapVo.put("month", "");
		mapVo.put("table_code", "BUDG_PROJ_REMAIN_ADJ") ;
		mapVo.put("table_name", "经费余额调整表") ;
		mapVo.put("prefixe", "JFYETZ") ;
		mapVo.put("seq_no", 6);
		
		BudgNoManager budgNoManager = budgNoManagerMapper.queryBudgNoManagerByCode(mapVo);
		
		String pref = "JFYETZ";

		String max_no = "1";
		
		String no = "1" ;
		if(budgNoManager == null){
			
			mapVo.put("max_no", 1);
			
			budgNoManagerMapper.addBudgNoManager(mapVo);
			
		}else{
			
			max_no = String.valueOf(budgNoManager.getMax_no());
			
			no = max_no ;
			
			budgNoManagerMapper.updateBudgNoManagerMaxNo(mapVo);
		}
		
		for(int i= 0 ; i< 6- no.length() ; i++){
			
			max_no = "0"+ max_no ;
			
		}
		
		return pref + mapVo.get("year") + max_no;
	}
	/*
	 * 审核   更改状态
	 */
	@Override
	public String updateAdjSate(List<Map<String, Object>> listVo) throws DataAccessException {
		
		String str = "";
		
		try {
			
			for (Map<String, Object> map : listVo) {
				
				//根据单号 查询明细表   获取项目ID  资金来源ID 经费余额调整 等数据  用于更新年度主表数据
				List<Map<String, Object>> reDetList = budgProjReAdjDetMapper.queryForDetail(map);
				for (Map<String, Object> mapVo : reDetList) {
					//获取当前年度
					int Year=Calendar.getInstance().get(Calendar.YEAR);
					mapVo.put("budg_year", Year);
				}
				budgProjRemainAdjMapper.updateProjYear(reDetList);
				
				//更新年度明细后  更改自身数据状态 为已审核
				String adjCode = String.valueOf(map.get("adj_code"));//获取单号 
				//根据单号  查询数据状态
				String state = budgProjRemainAdjMapper.queryState(map);
				//02 提交状态  下允许审核
				if(!"01".equals(state)){
					
					str += adjCode+" ";
					
				}else{
					//审核时  更改审核人  审核日期 为 当前操作人  操作日期
					map.put("checker", SessionManager.getUserId());
					
					//获取当前日期  Date类型
					Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
					map.put("check_date", date);
					
					//提交状态时  更改状态为已审核
					map.put("state", "02");
				}
			}
			
			if(str.length() != 0){
				return "{\"error\":\"调整单号:"+str+"的数据状态不是新建状态 , 不能执行审核操作!\"}";
			}
			
			int state = budgProjRemainAdjMapper.updateAdjSate(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败! 方法 updateAdjSate\"}");

		}
	}
	
	/*
	 * 销审   更改状态
	 */
	@Override
	public String updateCancelBatch(List<Map<String, Object>> listVo) throws DataAccessException {
		
		String str = "";
		
		try {
			
			for (Map<String, Object> map : listVo) {
				
				//根据单号 查询明细表   获取项目ID  资金来源ID 经费余额调整 等数据  用于更新年度主表数据
				List<Map<String, Object>> reDetList = budgProjReAdjDetMapper.queryForDetail(map);
				
				for (Map<String, Object> mapVo : reDetList) {
					//获取当前年度
					int Year=Calendar.getInstance().get(Calendar.YEAR);
					mapVo.put("budg_year", Year);
				}
				
				budgProjRemainAdjMapper.updateCancelProjYear(reDetList);
				
				//更新年度明细后  更改自身数据状态 为已审核
				String adjCode = String.valueOf(map.get("adj_code"));//获取单号 
				//根据单号  查询数据状态
				String state = budgProjRemainAdjMapper.queryState(map);
				//02 提交状态  下允许审核
				if(!"02".equals(state)){
					
					str += adjCode+" ";
					
				}else{
					//销审时  更改审核人  审核日期 为 ""
					map.put("checker", "");
					
					map.put("check_date", "");
					
					//销审时  更改状态为已提交
					map.put("state", "01");
				}
			}
			
			if(str.length() != 0){
				return "{\"error\":\"调整单号:"+str+"的数据状态不是审核状态 , 不能执行销审操作!\"}";
			}
			
			int state = budgProjRemainAdjMapper.updateAdjSate(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败! 方法 updateCancelBatch\"}");
			
		}
	}
	
	/**
	 * 根据项目 查询资金来源 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryBudgSourceByProj(Map<String, Object> mapVo)
			throws DataAccessException {
		
		 return JSON.toJSONString(budgProjRemainAdjMapper.queryBudgSourceByProj(mapVo));
	}
	
	/**
	 * @Description 
	 * 根据项目ID 资金来源ID  查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryProjMessage(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return ChdJson.toJson(budgProjRemainAdjMapper.queryProjMessage(mapVo));
		
	}
	
	/**
	 * @Description 
	 * 根据条件组合   查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryProjDetailByCondition(Map<String, Object> mapVo)throws DataAccessException {
		
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgProjRemainAdjMapper.queryProjDetailByCondition(mapVo);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgProjRemainAdjMapper.queryProjDetailByCondition(mapVo, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	
	/**
	 * @Description 
	 * 修改查询明细数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String querBudgProjBalanceDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjRemainAdjMapper.querBudgProjBalanceDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjRemainAdjMapper.querBudgProjBalanceDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
}
