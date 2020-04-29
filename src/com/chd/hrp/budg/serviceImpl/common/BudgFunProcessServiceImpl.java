/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgBasicIndexStackMapper;
import com.chd.hrp.budg.dao.common.BudgChargeStandStackMapper;
import com.chd.hrp.budg.dao.common.BudgFunMapper;
import com.chd.hrp.budg.dao.common.BudgFunProcessMapper;
import com.chd.hrp.budg.dao.common.BudgIncomeStackMapper;
import com.chd.hrp.budg.dao.common.BudgIndexStackMapper;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.chd.hrp.budg.service.common.BudgFunService;

 


@Service("budgFunProcessService")
public class BudgFunProcessServiceImpl implements BudgFunProcessService {

	private static Logger logger = Logger.getLogger(BudgFunProcessServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFunProcessMapper")
	private final BudgFunProcessMapper budgFunProcessMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgFunMapper")
	private final BudgFunMapper budgFunMapper = null;
	
	
	
	//引入DAO操作
	@Resource(name = "budgBasicIndexStackMapper")
	private final BudgBasicIndexStackMapper budgBasicIndexStackMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgChargeStandStackMapper")
	private final BudgChargeStandStackMapper budgChargeStandStackMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgIndexStackMapper")
	private final BudgIndexStackMapper budgIndexStackMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgIncomeStackMapper")
	private final BudgIncomeStackMapper budgIncomeStackMapper = null;
	
	@Resource(name = "budgFunService") //计算过程 service
	private final BudgFunService budgFunService = null;
	
    /**
     * 取值函数 过程查看 数据查询
     */
	@Override
	public String queryFunProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		if(mapVo.get("year") !=  null ){
			
			mapVo.put("last_year",Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
			
		}
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> budgFun = budgFunProcessMapper.queryFunInfo(mapVo);
		
		String sql = String.valueOf(budgFun.get("fun_method_eng"));
		
		// 根据 指标编码 查询 预算指标函数参数栈  数据
		List<Map<String,Object>> list =  new ArrayList<Map<String,Object>>();
		
		if("01".equals(mapVo.get("index_type_code"))){//基本指标
			
			list = (List<Map<String, Object>>) budgBasicIndexStackMapper.queryBudgIndexStackByCode(mapVo);
			
		}else if("02".equals(mapVo.get("index_type_code"))){//费用标准
			
			list = (List<Map<String, Object>>) budgChargeStandStackMapper.queryBudgIndexStackByCode(mapVo);
			
			
		}else if("03".equals(mapVo.get("index_type_code"))){ //预算指标
			
			list = (List<Map<String, Object>>) budgIndexStackMapper.queryBudgIndexStackByCode(mapVo);
			
		}else if("04".equals(mapVo.get("index_type_code"))){//收入科目
			
			list = (List<Map<String, Object>>) budgIncomeStackMapper.queryBudgIndexStackByCode(mapVo);
		}
		
		
		
		// 存放 函数参数用Map
		Map<String,Object> map = new HashMap<String,Object>();
		
		if( list.size() > 0 ){
			//循环遍历 函数参数参数栈 数据  取参数值  解析函数
			for(Map<String,Object> item : list){
				// 系统内置参数  集团（group_id）、医院(hos_id)、账套(copy_code)、年度(year：本年  last_year：上年)、月份（month）
				if("group_id".equals(item.get("fun_para_code")) || "hos_id".equals(item.get("fun_para_code")) ||
						 "copy_code".equals(item.get("fun_para_code")) || "year".equals(item.get("fun_para_code"))
						 || "last_year".equals(item.get("fun_para_code")) || "month".equals(item.get("fun_para_code"))){
					
					sql = sql.replace(String.valueOf(item.get("fun_para_code")), "'"+mapVo.get(item.get("fun_para_value").toString())+"'");
					
				}else{
					
					sql = sql.replace(String.valueOf(item.get("fun_para_code")), "'"+item.get("fun_para_value")+"'");
					
				}
				
			}
			
			map.put("sql", sql.substring(0, sql.length()-1));
			
		}else{
			
			return "{\"error\":\"未查到参数栈数据！\",\"state\":\"false\"}";
			
		}
		
		
		try {
			//函数取值
			 budgFunProcessMapper.execFunSql(map);
			
			if("0".equals(String.valueOf(map.get("BUDG_APPCODE")))){
				
				budgFun.put("count_value",map.get("resultValue"));
				
				dataList.add(budgFun);
				
				return ChdJson.toJson(dataList) ;
				
			}else{
				
				return "{\"error\":\""+map.get("BUDG_ERRTEXT")+"\",\"state\":\"false\"}";
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"函数取值失败.\",\"state\":\"false\"}");
		}
		
			
		
	}
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
