﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationbasic.deptyblimit;

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
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.business.compilationbasic.deptyblimit.BudgDeptYbLimitMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.dpetexecute.BudgWorkDeptYearExecuteMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.zeyfincome.BudgZeyfIncomeHisMapper;
import com.chd.hrp.budg.entity.BudgDeptYbLimit;
import com.chd.hrp.budg.service.business.compilationbasic.deptybimint.BudgDeptYbLimitService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室医保额度控制
 * @Table:
 * BUDG_DEPT_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgDeptYbLimitService")
public class BudgDeptYbLimitServiceImpl implements BudgDeptYbLimitService {

	private static Logger logger = Logger.getLogger(BudgDeptYbLimitServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDeptYbLimitMapper")
	private final BudgDeptYbLimitMapper budgDeptYbLimitMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgZeyfIncomeHisMapper")
	
	private final BudgZeyfIncomeHisMapper budgZeyfIncomeHisMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgWorkDeptYearExecuteMapper")
	private final BudgWorkDeptYearExecuteMapper budgWorkDeptYearExecuteMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();//科室年度业务概率预算  用List
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
			
		}
		
		try {
			
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				String  str = budgDeptYbLimitMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgDeptYbLimitMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = budgDeptYbLimitMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 添加科室医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		int count  = budgDeptYbLimitMapper.queryDataExist(entityMap);

		if (count > 0 ) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		try {
			
			int state = budgDeptYbLimitMapper.add(entityMap);
			
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
					+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
					+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\",\"state\":\"false\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室医保额度控制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		try {
			
			for(Map<String,Object> item :entityList ){
				
				int count  = budgDeptYbLimitMapper.queryDataExist(item);
				
				if(count > 0){
					str += item.get("year") +"年 "+ item.get("dept_name") +"科室 "+ item.get("insurance_name") +"医保类型;";
				}
			}
			
			if( str != ""){
				
				return "{\"error\":\"添加失败！以下数据:【"+str+"】已存在.\",\"state\":\"false\"}";
				
			}else{
				
				budgDeptYbLimitMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\",\"state\":\"false\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgDeptYbLimitMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室医保额度控制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgDeptYbLimitMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgDeptYbLimitMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室医保额度控制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDeptYbLimitMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室医保额度控制<BR> 
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
		//判断是否存在对象科室医保额度控制
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgDeptYbLimit> list = (List<BudgDeptYbLimit>)budgDeptYbLimitMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgDeptYbLimitMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgDeptYbLimitMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgDeptYbLimit> list = (List<BudgDeptYbLimit>)budgDeptYbLimitMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgDeptYbLimit> list = (List<BudgDeptYbLimit>)budgDeptYbLimitMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室医保额度控制<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptYbLimitMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室医保额度控制<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDeptYbLimit
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptYbLimitMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室医保额度控制<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgDeptYbLimit>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptYbLimitMapper.queryExists(entityMap);
	}
	
	/**
	 * 根据主键 查询科室医保额度控制数据 是否存在
	 */
	@Override
	public int queryDataExist(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgDeptYbLimitMapper.queryDataExist(entityMap);
	}
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryBudgYBTY(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgDeptYbLimitMapper.queryBudgYBTY(entityMap, rowBounds));
	}
	
	/**
	 * 科室 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryBudgDeptDict(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgDeptYbLimitMapper.queryBudgDeptDict(entityMap, rowBounds));
	}
	
	/**
	 * 查询 医保类型是否存在
	 */
	@Override
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptYbLimitMapper.queryInsuranceCodeExist(mapVo);
	}
	
	/**
	 * 根据 科室编码 查询 科室ID
	 */
	@Override
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException {		

		return budgDeptYbLimitMapper.queryDeptID(mapVo);
	}
	@Override
	public List<Map<String, Object>> queryBudgDeptData(Map<String, Object> mapVo) throws DataAccessException {

		return budgDeptYbLimitMapper.queryBudgDeptData(mapVo);
	}
	
	/**
	 * 增量生成
	 */
	@Override
	public int addGenerate(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptYbLimitMapper.addGenerate(mapVo);
	}
	
	/**
	 * 根据年度、科室、医保类型信息 查询 全院医保额度、上年医保收入
	 */
	@Override
	public String qureyLastData(Map<String, Object> mapVo) throws DataAccessException {
		
		String last_yb_income = budgDeptYbLimitMapper.qureyLastYbIncome(mapVo);
		
		String control_limit = budgDeptYbLimitMapper.qureyControlLimit(mapVo);
		
		return "{\"last_yb_income\":\""+last_yb_income+"\",\"control_limit\":\""+control_limit+"\",\"state\":\"true\"}";
	}
	
	/**
	 * 历史数据比例分解
	 */
	@Override
	public String updateResolveRate(Map<String, Object> mapVo)	throws DataAccessException {
		
		try{
			//查询 分解数据
			List<Map<String,Object>> dataList  = budgDeptYbLimitMapper.qureyResolveData(mapVo);
			
			budgDeptYbLimitMapper.updateResolveRate(dataList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败！\",\"state\":\"true\"}");
			
		}
		
	}
	@Override
	public String budgDeptYbLimitImportNewPage(Map<String, Object> map) throws DataAccessException {
		 try{
				
				String content=map.get("content").toString();
				
				List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
				
				if(liData==null || liData.size()==0){
					return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
				}
				
				Map<String, Object> entityMap=new HashMap<String,Object>();
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				
				//查询科室编码
				List<Map<String,Object>> listDeptIdDict = budgWorkDeptYearExecuteMapper.queryBudgDeptId(entityMap);		
				// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> dictDeptMap = new HashMap<String, Map<String, Object>>();
			
				for(Map<String, Object> dict : listDeptIdDict){
					if(dict.get("dept_name") != null){
						dictDeptMap.put(dict.get("dept_name").toString(), dict);//改成name
						
					}
				}
				
				
				//查询医保类型编码
				List<Map<String,Object>> listInsurancecodeDict = budgZeyfIncomeHisMapper.queryBudgHosInsuranceCode(entityMap);		
				// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			
				for(Map<String, Object> dict : listInsurancecodeDict){
					if(dict.get("insurance_name") != null){
						dictMap.put(dict.get("insurance_name").toString(), dict);//改成name
						
					}
				}
				
				List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
				
				
				for(Map<String,List<String>> item : liData ){
					 
					List<String> budg_year = item.get("年度【必填】") ;
					
					List<String> insurance_name = item.get("医保类型名称【必填】") ;
					
					List<String> dept_name = item.get("科室名称【必填】") ;
				
					List<String> dept_control_limit = item.get("科室医保额度【必填】") ;
					
					List<String> remark = item.get("备注") ;
					
					if(budg_year == null){
						
						return "{\"warn\":\"" + budg_year.get(0) + ",年度为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_year.get(0) + "\"}";
						
					}
					
					if(insurance_name == null){
						//get(0)获取坐标
						return "{\"warn\":\"" + insurance_name.get(0) + ",医保类型名称！\",\"state\":\"false\",\"row_cell\":\"" + insurance_name.get(0) + "\"}";
					
					}else{
						
						if(dictMap.get(insurance_name.get(1)) == null){
							//get(1)获取单元格的值
							return "{\"warn\":\"" + insurance_name.get(0) + ",医保类型名称！\",\"state\":\"false\",\"row_cell\":\"" + insurance_name.get(0) + "\"}";
						}
					}
					if(dept_name == null){
						//get(0)获取坐标
						return "{\"warn\":\"" + dept_name.get(0) + ",科室名称！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					
					}else{
						
						if(dictDeptMap.get(dept_name.get(1)) == null){
							//get(1)获取单元格的值
							return "{\"warn\":\"" + dept_name.get(0) + ",科室名称！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if(dept_control_limit == null){
						
						return "{\"warn\":\"" + dept_control_limit.get(0) + ",科室医保额度为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_control_limit.get(0) + "\"}";
						
					}else{
						
						 try{
							 Double.parseDouble((dept_control_limit.get(1)));
							   
						 }catch(NumberFormatException e){
							 
							 return "{\"warn\":\"" + dept_control_limit.get(0) + ",科室医保额度输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + dept_control_limit.get(0) + "\"}";
						 }
						
					}
	               
					
					//存放 正确信息用Map
					Map<String,Object> returnMap = new HashMap<String,Object>();
					
					returnMap.put("group_id", SessionManager.getGroupId());
					
					returnMap.put("hos_id", SessionManager.getHosId());
					
					returnMap.put("copy_code", SessionManager.getCopyCode());
					
					returnMap.put("remark", remark.get(1));
								
					returnMap.put("year", budg_year.get(1));
					
					returnMap.put("rate", 0);
					
					returnMap.put("dept_control_limit", dept_control_limit.get(1));
					
					returnMap.putAll(dictDeptMap.get(dept_name.get(1)));
					
					returnMap.putAll(dictMap.get(insurance_name.get(1)));
					
					returnList.add(returnMap);
					
				}
				StringBuffer err_sb = new StringBuffer();
				
				//校验 数据重复
				for( int i = 0; i < returnList.size(); i++ ){
					
					for(int j = i + 1 ; j < returnList.size(); j++){
						
						
						if(returnList.get(i).get("dept_name").equals(returnList.get(j).get("dept_name")) &&returnList.get(i).get("year").equals(returnList.get(j).get("year")) && returnList.get(i).get("insurance_name").equals(returnList.get(j).get("insurance_name"))){
							
							err_sb.append(returnList.get(i).get("dept_name")+"科室名称,"+returnList.get(i).get("year")+"年度,"+returnList.get(i).get("insurance_name")+"医保类型名称");  
						}
					}
					
				}
				
				if( err_sb.length() > 0){
					
					 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
					
				}else{
				
	             String str = addBatch(returnList);
					
					if("false".equals(JSONObject.parseObject(str).get("state"))){
						
						return str ;
					}else{					
											
						return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
					}
					
				}
		 }catch(Exception e){
				e.printStackTrace();
				logger.debug(e.getMessage());
				throw new SysException(e.getMessage());
		 }
		
	}
	
}
