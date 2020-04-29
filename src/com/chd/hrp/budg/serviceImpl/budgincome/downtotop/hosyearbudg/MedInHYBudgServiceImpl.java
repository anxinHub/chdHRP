/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.downtotop.hosyearbudg;

import java.math.BigDecimal;
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
import com.chd.hrp.budg.dao.budgincome.downtotop.hosyearbudg.MedInHYBudgMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptMonth;
import com.chd.hrp.budg.entity.BudgMedIncomeHosYear;
import com.chd.hrp.budg.service.budgincome.downtotop.hosyearbudg.MedInHYBudgService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("medInHYBudgService")
public class MedInHYBudgServiceImpl implements MedInHYBudgService {

	private static Logger logger = Logger.getLogger(MedInHYBudgServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInHYBudgMapper")
	private final MedInHYBudgMapper medInHYBudgMapper = null;
    
	
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
					Map<String,Object> mapVo = medInHYBudgMapper.queryDataExits(item);
					if(mapVo!=null){
						 str += item.get("subj_code")+";";
					}
				}
				//判断str是否为空,为空正常添加   不为空则返回错误信息
				if(!"".equals(str)){
					return "{\"error\":\"所选年以下度科目编码:"+str+"数据已存在.\",\"state\":\"false\"}";
				}else{
					medInHYBudgMapper.addBatch(addList);
				}
			}
			
			if(updateList.size()>0){
				
				medInHYBudgMapper.updateBatch(updateList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * @Description 
	 * 添加医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医院年度医疗收入预算
		BudgMedIncomeHosYear budgMedIncomeHosYear = queryByCode(entityMap);

		if (budgMedIncomeHosYear != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medInHYBudgMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医院年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			String str = "";
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item : entityList){
				
				BudgMedIncomeHosYear budgMedIncomeHosYear = queryByCode(item);
				
				if (budgMedIncomeHosYear != null) {

					str += item.get("year")+"年 医院:"+item.get("dept_name")+" 科目:"+item.get("subj_name")	+";";
				}
			}
			
			if(str != ""){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"已有数据存在.\",\"state\":\"false\"}";
			
			}else{
				
				for(int i= 0 ; i< entityList.size() ; i++){
					
					addList.add(entityList.get(i));
					
					if(i%1000 == 0 ){
						
						medInHYBudgMapper.addBatch(addList);
						
						addList.clear();
						
					}else if ( i == (entityList.size()-1) && addList.size() > 0 ){
						
						medInHYBudgMapper.addBatch(addList);
						
						addList.clear();
					}
					
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medInHYBudgMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医院年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medInHYBudgMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medInHYBudgMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医院年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList) throws DataAccessException{
		
		try {
			
			medInHYBudgMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加医院年度医疗收入预算<BR> 
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
		//判断是否存在对象医院年度医疗收入预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeHosYear> list = (List<BudgMedIncomeHosYear>)medInHYBudgMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = medInHYBudgMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medInHYBudgMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedIncomeHosYear> list = (List<BudgMedIncomeHosYear>)medInHYBudgMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeHosYear> list = (List<BudgMedIncomeHosYear>)medInHYBudgMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医院年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInHYBudgMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedIncomeHosYear
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medInHYBudgMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedIncomeHosYear>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap) throws DataAccessException{
		return medInHYBudgMapper.queryExists(entityMap);
	}
	@Override
	public String queryBudgDept(Map<String, Object> mapVo)	throws DataAccessException {
		
		RowBounds rowBoundsALL = new RowBounds(0, 20);
		
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(medInHYBudgMapper.queryBudgDept(mapVo, rowBounds));
	}
	@Override
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException {
		return medInHYBudgMapper.queryDeptExist(mapVo);
	}
	
	//汇总
	@Override
	public String collectMedInHYBudgUp(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		//查询所选医院 科目
		List<Map<String,Object>> list = medInHYBudgMapper.queryCountData(mapVo);
		
		if(list.size() > 0){
			for (Map<String, Object> item : list) {
				//补充剩余字段
				item.put("count_value", "");
				item.put("remark", "");
				item.put("hos_suggest", "");
				item.put("dept_suggest_sum", "");
				addList.add(item);
			}
		}else{
			return "{\"error\":\"没有需要汇总的数据  或者数据已全部汇总!\"}";
		}
		
		try {
			medInHYBudgMapper.addBatch(addList);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败!\"}";
		}
	}
	/**
	 * 查询 所选年度、科目 的 所有科室上年收入 总和
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	
	@Override
	public String queryLastYearIncome(Map<String, Object> mapVo)
			throws DataAccessException {
		Map<String, Object>  map = medInHYBudgMapper.queryLastYearIncome(mapVo);
		return ChdJson.toJson(map);
	}
}
