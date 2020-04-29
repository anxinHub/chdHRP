package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrStoreCondition;

/**
 * 档案库人员限定配置
 * 
 * @author Administrator
 *
 */
public interface HrStoreConditionService {

	/**
	 * 添加档案库人员限定配置
	 * 
	 * @param entityMap
	 * @return
	 */
	String addStoreConditionPage(Map<String, Object> entityMapMap)throws DataAccessException;

	/**
	 * 查询数据
	 * 
	 * @param entityMap
	 * @return
	 */
	String queryStoreCondition(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 删除档案库人员限定配置
	 * 
	 * @param entityMap
	 * @return
	 */
	String deleteBatch(List<HrStoreCondition> entityMap)throws DataAccessException;



	/**
	 * 左侧列表
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryStoreConditionTree(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询系统结构表
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryHrHosConditionTabStruc(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询系统结构列名
     * @param mapVo
     * @return
     */
	String queryHrHosColStruc(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 条件符号查询
     * @param mapVo
     * @return
     */
	String queryHrHosConSign(Map<String, Object> mapVo)throws DataAccessException;
	 /**
     * 连接符号查询
     * @param mapVo
     * @return
     */
	String queryHrHosJoinSign(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryStoreConditionByPrint(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importExcelCondition(Map<String, Object> mapVo) throws DataAccessException;

	String queryHrTabColStruc(Map<String, Object> mapVo) throws DataAccessException;
}
