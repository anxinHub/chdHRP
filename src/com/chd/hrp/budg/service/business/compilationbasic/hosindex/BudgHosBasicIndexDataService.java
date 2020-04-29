/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.hosindex;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院基本指标数据维护
 * @Table:
 * BUDG_HOS_BASIC_INDEX_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosBasicIndexDataService extends SqlService {
	
	/**
	 * 查询 基本指标字典 budg_basic_index_dic  指标数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgBasicIndexData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 主键查询  数据是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据指标编码 查询 基本指标是否存在 导入用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBasicIndexExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 指标名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryIndex(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 最新版导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String importBudgBasicIndex(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 所传基本指标编码 查询其取值方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIndexGetWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgHosBasicIndexData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;


}
