/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.hosstandard;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgHosChargeStan;
/**
 * 
 * @Description:
 * 医院费用标准维护
 * @Table:
 * BUDG_HOS_CHARGE_STAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosChargeStanMapper extends SqlMapper{
	
	/**
	 * 导入时校验 费用标准编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryChargeStanCodeExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 主页面换行添加   校验费用标准编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 增量生成 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptChargeStanData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 判断医院费用标准维护数据 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgHosChargeStanExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 主页面 换行添加  费用标准下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBudgChargeStan(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryBudgHosChargeStanCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 所传基本指标编码 查询其取值方法
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIndexGetWay(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;

	
}
