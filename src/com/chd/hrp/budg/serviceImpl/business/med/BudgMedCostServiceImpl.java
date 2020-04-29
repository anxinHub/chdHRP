/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.med;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.med.BudgMedCostMapper;
import com.chd.hrp.budg.entity.BudgMedCost;
import com.chd.hrp.budg.service.business.med.BudgMedCostService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医疗支出预算
 * @Table:
 * BUDG_MED_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedCostService")
public class BudgMedCostServiceImpl implements BudgMedCostService {

	private static Logger logger = Logger.getLogger(BudgMedCostServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedCostMapper")
	private final BudgMedCostMapper budgMedCostMapper = null;
    
	/**
	 * @Description 
	 * 添加医疗支出预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医疗支出预算
		BudgMedCost budgMedCost = queryByCode(entityMap);

		if (budgMedCost != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgMedCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医疗支出预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for( int i = 0; i< entityList.size() ; i++){
				
				addList.add(entityList.get(i));
				
				if( i%1000 == 0){
					budgMedCostMapper.addBatch(addList);
					addList.clear();
				}else if ( i ==  (entityList.size() -1) && addList.size() > 0){
					
					budgMedCostMapper.addBatch(addList);
					addList.clear();
					
				}
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新医疗支出预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgMedCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医疗支出预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgMedCostMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除医疗支出预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgMedCostMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医疗支出预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMedCostMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加医疗支出预算<BR> 
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
		//判断是否存在对象医疗支出预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedCost> list = (List<BudgMedCost>)budgMedCostMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医疗支出预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedCost> list = (List<BudgMedCost>)budgMedCostMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedCost> list = (List<BudgMedCost>)budgMedCostMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医疗支出预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedCostMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗支出预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedCost
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedCostMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗支出预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedCost>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedCostMapper.queryExists(entityMap);
	}
	//科室支出预算查询
	@Override
	public String queryDeptMedCost(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedCost> list = budgMedCostMapper.queryDeptMedCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedCost> list = budgMedCostMapper.queryDeptMedCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	//医院支出预算查询
	@Override
	public String queryHosMedCost(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedCost> list = budgMedCostMapper.queryHosMedCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedCost> list = budgMedCostMapper.queryHosMedCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String queryAllMedCost(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedCost> list = budgMedCostMapper.queryAllMedCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedCost> list = budgMedCostMapper.queryAllMedCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryDeptMedCostMonitor(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		// 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
		int count =  budgMedCostMapper.queryExecuteDataExist(entityMap) ;
		
		if(count == 0 ){
			entityMap.put("flag",-1);
		}
		
		// 查询 所查 预算年度 是否有 数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有数据) 解决  没有数据，前台合计行 展现数据 窜行问题)
		int num =  budgMedCostMapper.queryBudgDataExist(entityMap) ;
		
		if(num == 0 ){
			entityMap.put("budg_flag",-1);
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgMedCostMapper.queryDeptMedCostMonitor(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgMedCostMapper.queryDeptMedCostMonitor(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/*
	 * 科室医疗支出预算报表
	 */
	@Override
	public String queryReportMedDeptCost(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgMedCostMapper.queryReportMedDeptCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgMedCostMapper.queryReportMedDeptCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/*
	 * 医院医疗支出预算报表
	 */
	@Override
	public String queryReportMedHosCost(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgMedCostMapper.queryReportMedHosCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgMedCostMapper.queryReportMedHosCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/*
	 * 医院支出预算报表
	 */
	@Override
	public String queryMedAllCostReport(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgMedCostMapper.queryMedAllCostReport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgMedCostMapper.queryMedAllCostReport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAllMedCostMonitor(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		// 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
		int count =  budgMedCostMapper.queryExecuteDataExist(entityMap) ;
		
		if(count == 0 ){
			entityMap.put("flag",-1);
		}
		
		// 查询 所查 预算年度 是否有 数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有数据) 解决  没有数据，前台合计行 展现数据 窜行问题)
		int num =  budgMedCostMapper.queryBudgDataExist(entityMap) ;
		
		if(num == 0 ){
			entityMap.put("budg_flag",-1);
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgMedCostMapper.queryAllMedCostMonitor(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgMedCostMapper.queryAllMedCostMonitor(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * 其他支出预算执行监控查询
	 */
	
	@Override
	public String queryReportMedElseCostMonitor(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		// 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
		int count =  budgMedCostMapper.queryElseExecuteDataExist(entityMap) ;
		
		if(count == 0 ){
			entityMap.put("flag",-1);
		}
		
		// 查询 所查 预算年度 是否有 数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有数据) 解决  没有数据，前台合计行 展现数据 窜行问题)
		int num =  budgMedCostMapper.queryElseBudgDataExist(entityMap) ;
		
		if(num == 0 ){
			entityMap.put("budg_flag",-1);
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgMedCostMapper.queryReportMedElseCostMonitor(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgMedCostMapper.queryReportMedElseCostMonitor(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 生成  根据年度生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String generateBudgMedCost(Map<String, Object> mapVo) {
		try {
			String str = "";

			//创建集合  封装生成所需数据
			//List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			//查询支出科目是否重复出现编制
			List<Map<String,Object>> subjCountList = budgMedCostMapper.querySubjCount(mapVo);
			for (Map<String, Object> map : subjCountList) {
				//如果支出科目重复编制  则count值大于1 将支出项目名放入str 用作返回信息
				if(Integer.parseInt(map.get("count").toString()) > 1){
					str += map.get("subj_name").toString() + " ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"预算科目: "+str+"重复编制,请检查数据后操作!\",\"state\":\"false\"}";
			}
			
			//查询各支出编制数据 
			List<Map<String, Object>> addList = budgMedCostMapper.queryAddData(mapVo);
			
			//addList.addAll(DataList);
			
			budgMedCostMapper.delete(mapVo);
			budgMedCostMapper.addGenerateBatch(addList);
			
			return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
			
		} catch (DataAccessException e) {
			
			throw new SysException("{\"error\":\"生成失败!\"}");
		}
		
	}
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMedCostMapper.queryDataExist(mapVo) ;
	}
	@Override
	public int querySubjExist(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgMedCostMapper.querySubjExist(mapVo) ;
	}
	@Override
	public String addMonth(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
		budgMedCostMapper.addMonth(mapVo);
		return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			
			throw new SysException("{\"error\":\"添加失败!\"}");
		}
	}
	@Override
	public String queryMonth(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list= budgMedCostMapper.queryMonth(mapVo);
		return ChdJson.toJsonLower(list);
	}
	@Override
	public String delMonth(String tableName,List<Map<String, Object>> mapVo)
			throws DataAccessException {
		budgMedCostMapper.delMonth(tableName,mapVo);
		return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
	}
	@Override
	public String auditOrUnAudit(String tableName,List<Map<String, Object>> mapVo)
			throws DataAccessException {
		budgMedCostMapper.auditOrUnAudit(tableName,mapVo);
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	@Override
	public String affiOrUnAffi(String tableName,List<Map<String, Object>> mapVo)throws DataAccessException {
		budgMedCostMapper.affiOrUnAffi(tableName,mapVo);
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	@Override
	public List<String> queryBudgMedCostState(Map<String, Object> entityMap)throws DataAccessException {
		String params=entityMap.get("paramVo").toString();
		String p[]=params.split(",");
		List<String> list=new ArrayList<String>();
		for(int m=0;m<p.length;m++){
			String[] ps=params.split("@");
			Map<String,Object> pmap=null;
			for(int i=0;i<ps.length;i++){
				pmap=new HashMap<String,Object>();
				pmap.put("group_id", entityMap.get("group_id"));
				pmap.put("hos_id", entityMap.get("hos_id"));
				pmap.put("copy_code", entityMap.get("copy_code"));
				pmap.put("budg_year", ps[0]);
				pmap.put("dept_id", ps[1]);
				pmap.put("subj_code",ps[2]);
				pmap.put("tableName", entityMap.get("tableName"));
				pmap.put("state",entityMap.get("state"));
				if(budgMedCostMapper.queryBudgMedCostState(pmap)==0){
					list.add(JSONObject.toJSONString(pmap));
					break;
				}
			}
		}
		return list;
	}
	@Override
	public String updateMonth(Map<String, Object> mapVo)throws DataAccessException {
		budgMedCostMapper.updateMonth(mapVo);
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	@Override
	public List<Map<String, Object>> queryDeptMedCostMonth(
			Map<String, Object> entityMap) throws DataAccessException {
			return budgMedCostMapper.queryMonth(entityMap);
	}
	
	/**
	 * 根据预算年度查询科室数据是否未审核
	 */
	@Override
	public String queryYearDeptDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException {
		int count = budgMedCostMapper.queryYearDeptDataExistNoCheck(mapVo);
		if (count == 0)
		return "{\"work_msg\":\"操作成功.\",\"work_state\":\"true\"}"; 
		else 
		return "{\"work_msg\":\"操作失败.\",\"work_state\":\"false\"}"; 
	}
	/**
	 * 根据预算年度查询医院数据是存在数据
	 */
	@Override
	public String queryYearHosDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		int count = budgMedCostMapper.queryYearHosDataExist(mapVo);
		if (count == 0)
		return "{\"work_msg\":\"操作成功.\",\"work_state\":\"true\"}"; 
		else 
		return "{\"work_msg\":\"操作失败.\",\"work_state\":\"false\"}"; 
		
	}
	
	/*
	 * 医院医疗支出科室汇总
	 */
	@Override
	public String collectMedMonthCost(Map<String,Object> mapVo) throws DataAccessException{
		
		try {
			
			int count  = budgMedCostMapper.queryDataExist(mapVo);
			if(count >0){
				return "{\"error\":\"该年度科室支出预算存在未确认数据，请先确认后再汇总! 方法 collectMedMonthCost\"}";
			}			
			
			budgMedCostMapper.deleteCollectYear(mapVo);
			budgMedCostMapper.colletMonth(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";	
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 collectMedMonthCost\"}";

		}
	}
	
	/**
	 * 根据预算年度查询科室费用数据是否未审核
	 */
	@Override
	public String queryYearDeptExpensesDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException {
		int count = budgMedCostMapper.queryYearDeptExpensesDataExistNoCheck(mapVo);
		if (count == 0)
		return "{\"work_msg\":\"操作成功.\",\"work_state\":\"true\"}"; 
		else 
		return "{\"work_msg\":\"操作失败.\",\"work_state\":\"false\"}"; 
	}
	/**
	 * 根据预算年度查询科室成本数据是存在科目数据
	 */
	@Override
	public String queryYearDeptSubjDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		
		List<Map<String,Object>> subjList = budgMedCostMapper.queryYearDeptSubjDataExist(mapVo);
		
		String str = "";

		for (Map<String, Object> map : subjList) {
			str += map.get("SUBJ_CODE")+ ",";
		}
		if(str != ""){
			return "{\"error\":\"已存在"+str.substring(0,str.length()-1)+"科目的预算数据,汇总失败!\",\"work_state\":\"false\"}";
		}else
			return "{\"work_msg\":\"操作成功.\",\"work_state\":\"true\"}"; 
		
	}
	
	/*
	 * 医院医疗支出费用汇总
	 */
	@Override
	public String collectMedMonthExpenses(Map<String,Object> mapVo) throws DataAccessException{
		
		try {
			
			budgMedCostMapper.collectMedMonthExpenses(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";	
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 collectMedMonthExpenses\"}";

		}
	}
	
	@Override
	public List<String> queryBudgCostYearDeptState(Map<String, Object> entityMap)throws DataAccessException {
		
		List<String> list=new ArrayList<String>();
		
		return list;
	}
	/**
	 * @Description 
	 * 批量添加医疗支出预算年度<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchDy(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for( int i = 0; i< entityList.size() ; i++){
				
				addList.add(entityList.get(i));
				
				if( i%1000 == 0){
					budgMedCostMapper.addBatchDy(addList);
					addList.clear();
				}else if ( i ==  (entityList.size() -1) && addList.size() > 0){
					
					budgMedCostMapper.addBatchDy(addList);
					addList.clear();
					
				}
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchDy\"}";

		}
		
	}
	/**
	 * 根据主键查询导入数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public int queryImportDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMedCostMapper.queryImportDataExist(mapVo) ;
	}
	
	
}
