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

import com.chd.hrp.budg.dao.common.BudgCommonInfoMapper;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;

/**
 * 
 * @Description:
 * 预算公用  信息 （如：预算科室、预算指标、收入科目、支出科目等）
 * @Table:
 * BUDG_FUN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgCommonInfoService")
public class BudgCommonInfoServiceImpl implements BudgCommonInfoService {

	private static Logger logger = Logger.getLogger(BudgCommonInfoServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCommonInfoMapper")
	private final BudgCommonInfoMapper budgCommonInfoMapper = null;
	
	/**
	 * 科室基本信息查询
	 */
	@Override
	public List<Map<String, Object>> queryDeptData(Map<String, Object> mapVo)	throws DataAccessException {
		
		return budgCommonInfoMapper.queryDeptData(mapVo);
	}
	
	/**
	 * 查询所有工资项目信息
	 */
	@Override
	public List<Map<String, Object>> queryBudgWageItemList(Map<String, Object> entityMap) throws DataAccessException {

		return budgCommonInfoMapper.queryBudgWageItemList(entityMap);
	}

	/**
	 * 查询 所有职工信息
	 */
	@Override
	public List<Map<String, Object>> queryEmpData(Map<String, Object> map) throws DataAccessException {

		return budgCommonInfoMapper.queryEmpData(map);
	}
	/**
	 * 查询所有职工类别信息
	 */
	@Override
	public List<Map<String, Object>> queryBudgEmpType(Map<String, Object> map) throws DataAccessException {
		
		return budgCommonInfoMapper.queryBudgEmpType(map);
	}
	
	/**
	 * 查询 资金来源 信息
	 */
	@Override
	public List<Map<String, Object>> querySourceInfo(Map<String, Object> map) throws DataAccessException {
		
		return budgCommonInfoMapper.querySourceInfo(map);
	}
	
	/**
	 * 查询 资产分类信息(根据名称 匹配编码)
	 */
	@Override
	public List<Map<String, Object>> queryAssType(Map<String, Object> map) throws DataAccessException {
		
		return budgCommonInfoMapper.queryAssType(map);
	}
	
	/**
	 * 查询 预算物资分类信息(根据名称 匹配编码) BUDG_MAT_TYPE_SUBJ物资分类与预算科目对应关系表
	 */
	@Override
	public Map<String, Object> queryBudgMatType(Map<String, Object> map) throws DataAccessException {
		
		return budgCommonInfoMapper.queryBudgMatType(map);
	}
}
