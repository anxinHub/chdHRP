/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.drugdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室药品支出预算编制
 * @Table:
 * BUDG_DRUG
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDrugService extends SqlService {
	
	/**
	 * 保存（添加、修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo)throws DataAccessException;
	
	/**
	 * 获取科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 获取药品分类下拉框通过
	 * 根据年度从BUDG_MED_TYPE_SUBJ中取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 生成 拷贝数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	//String copyBudgDrug(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * @Description 
	 * 生成  根据年度月份物资分类生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String generateBudgDrug(Map<String, Object> mapVo) throws DataAccessException;
	
	public String budgDrugUpdateAdjRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 校验数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 参数  查询收入预算、上年收入、上年同期支出  计算收入预算增长比例和计算值用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastCostAndRate(Map<String, Object> mapVo) throws DataAccessException ;

	
	
}
