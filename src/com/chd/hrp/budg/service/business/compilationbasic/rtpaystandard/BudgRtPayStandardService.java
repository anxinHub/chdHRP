/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.rtpaystandard;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgRtPayStandardService extends SqlService {
 
	/**
	 * 根据主键 查询数据 是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
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
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryBudgYBTY(Map<String, Object> mapVo) throws DataAccessException;

	public String budgRTPaystandardImportNewPage(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

}
