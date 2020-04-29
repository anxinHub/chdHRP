/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.budg.serviceImpl.budgsysset;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgsysset.BudgNoRuleMapper;
import com.chd.hrp.budg.service.budgsysset.BudgNoRulesService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("budgNoRuleService")
public class BudgNoRulesServiceImpl implements BudgNoRulesService {

	private static Logger logger = Logger.getLogger(BudgNoRulesServiceImpl.class);
	
	@Resource(name = "budgNoRuleMapper")
	private final BudgNoRuleMapper budgNoRuleMapper = null;
	
	/**
	 * @Description 查询Rules分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryRules(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String,Object>> list = budgNoRuleMapper.queryRules(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String updateBatchRules(List<Map<String, Object>> listVo) throws DataAccessException {
		
		String str = "";
		try {
			for (Map<String, Object> map : listVo) {
				int num = budgNoRuleMapper.queryRulesByCode(map);
				
				if(num != 0) {
					str +=  map.get("table_name") + "  ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\""+str+"已经存有业务数据,不可修改!\",\"state\":\"false\"}";
			}
			
			budgNoRuleMapper.updateBatchRules(listVo);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRole\"}";
		}
	}

	@Override
	public String updateRules(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
