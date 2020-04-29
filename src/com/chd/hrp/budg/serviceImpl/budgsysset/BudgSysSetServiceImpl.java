package com.chd.hrp.budg.serviceImpl.budgsysset;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgsysset.BudgSysSetMapper;
import com.chd.hrp.budg.entity.BudgSysSet;
import com.chd.hrp.budg.service.budgsysset.BudgSysSetService;


@Service("budgSysSetService")
public class BudgSysSetServiceImpl implements BudgSysSetService {
	
	private static Logger logger = Logger.getLogger(BudgSysSetServiceImpl.class);
	
	@Resource(name="budgSysSetMapper")
	private final BudgSysSetMapper budgSysSetMapper = null;
	
	
	/**
	 * 预算系统设置方案
	 */
	@Override
	public String addBudgSysSet(Map<String, Object> entityMap) throws DataAccessException {
		
		BudgSysSet budgSysSet = budgSysSetMapper.queryBudgSysSetByCode(entityMap);
		try {
			if (budgSysSet != null) {
				
				int state = budgSysSetMapper.updateBudgSysSet(entityMap);
				return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";
			}else{
		
				int state = budgSysSetMapper.addBudgSysSet(entityMap);
				
				return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBudgSysSet\"}");

		}
		
	}
	/**
	 * 查询 预算系统设置
	 */
	@Override
	public String queryBudgSysSet(Map<String, Object> mapVo) throws DataAccessException {
		Map<String, Object> budgSysSet = budgSysSetMapper.queryBudgSysSet(mapVo);
		
		if(budgSysSet == null){
			return "{\"state\":\"false\"}";
			//return "{\"msg\":\"数据未维护,请维护后查询!\"}";
		}
		return ChdJson.toJson(budgSysSet);
		
	}
	
	/**
	 *主页面跳转 根据当前年度  查询对象
	 * @param map
	 * @return
	 */
	@Override
	public BudgSysSet queryBudgSysSetByCode(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgSysSetMapper.queryBudgSysSetByCode(mapVo);
	}

}
