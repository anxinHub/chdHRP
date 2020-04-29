/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.sc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sc.HrTableStruc;

/**
 * 
 * @Description:
 * 数据表构建
 * @Table:
 * HR_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTableStrucService {
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHrTableStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrTableStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrTableStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrTableStruc(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTableStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTableStruc queryHrTableStrucByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询数据(左侧树型菜单)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTableStrucTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询tab_code序列
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabCodeSeq() throws DataAccessException;

	/**
	 * 根据code更新数据表字段
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateTabColByTabCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code更新数据表SQL
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateTabSqlByTabCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code更新数据表列设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateColSetByTabCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code查询数据表字段
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryTabColsByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code查询数据表列设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryColSetByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code查询数据表SQL
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryTabSqlsByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新是否停用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrTableStrucIsStop(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 生成数据表SQL
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String generateSqlStatement(Map<String, Object> entityMap) throws DataAccessException;
	
	String queryHrTableStrucExport(Map<String, Object> entityMap) throws DataAccessException;

	String addBatchTableStruc(List<HrTableStruc> list) throws DataAccessException;

	/**
	 * 导入旧版本数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String copyHrTableStrucByOld(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据多表名查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTableColByCodes(Map<String, Object> entityMap) throws DataAccessException;
	String queryHrTableColByCodes2(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 保存SQL语句
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String saveSqlStatement(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除SQL语句
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteSqlStatement(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询数据 用于sql输入提示{表名:[列名,...]}
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTableWords(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询数据  代码表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrFiledTableStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 创建表结构
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String createTableStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改表结构
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String alterTableStruc(Map<String, Object> entityMap) throws DataAccessException;

}
