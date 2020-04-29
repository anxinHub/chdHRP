/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.rtpaystandard;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 人头付费标准维护
 * @Table:
 * BUDG_RT_PAY_STANDARD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgRtPayStandardMapper extends SqlMapper{

	/**
	 * 根据主键 查询数据是否已存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 导入  校验 医保类型编码 是否村在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医保类型下拉框 主页面
	 */
	public List<Map<String, Object>> queryBudgYBTY(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
}
