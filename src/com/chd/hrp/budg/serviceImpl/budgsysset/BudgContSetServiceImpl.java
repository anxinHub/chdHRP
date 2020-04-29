/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.budg.serviceImpl.budgsysset;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgsysset.BudgContSetMapper;
import com.chd.hrp.budg.entity.BudgWageChange;
import com.chd.hrp.budg.service.budgsysset.BudgContSetService;

/**
* @Title. @Description.
* 系统启用<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("budgContSetService")
public class BudgContSetServiceImpl implements BudgContSetService {

	private static Logger logger = Logger.getLogger(BudgContSetServiceImpl.class);
	
	@Resource(name = "budgContSetMapper")
	private final BudgContSetMapper budgContSetMapper = null;
	
	/**
	 * @Description 
	 * 系统启用<BR> 添加BudgContSet
	 * @param BudgContSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveBudgContSet(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int count = budgContSetMapper.existsBudgContSetByCode(entityMap);
			
			if (count > 0) {
					
				budgContSetMapper.updateBudgContSet(entityMap);
				
			} else {
				budgContSetMapper.saveBudgContSet(entityMap);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"系统异常 请联系管理员! 错误编码 addBudgContSet\"}";
		}
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询BudgContSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgContSet(Map<String,Object> entityMap) throws DataAccessException{
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgContSetMapper.queryBudgContSet(entityMap);
		
		//根据数据主键  查询控制节点下拉框  并拼接成json字符串 作为参数返回
		for (Map<String, Object> map : list) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			
			List<Map<String,Object>> noteList = budgContSetMapper.queryBudgContNote(map);
			
			String noteJson = ChdJson.toJson(noteList);
			
			map.put("noteJson", noteJson);
			
			returnMap.putAll(map);
			
			returnList.add(returnMap);
		}
		
		return ChdJson.toJson(returnList);
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 删除BudgContSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteBudgContSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			budgContSetMapper.deleteBudgContSet(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBudgContSet\"}";

		}
    }
	
	/**
	 * 控制节点下拉框
	 */
	@Override
	public String queryBudgContNote(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(budgContSetMapper.queryBudgContNote(entityMap));
	}
	
	/**
	 * 根据控制节点 查询反向节点code  name
	 */
	@Override
	public String queryReNodeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(budgContSetMapper.queryReNodeByCode(entityMap));
	}
	
}
