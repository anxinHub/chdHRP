
package com.chd.hrp.hr.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrColumn;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedView;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;

public interface HrCommonMapper extends SqlMapper {

	/**
	 * 查询表信息
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public HrTabStruc queryTableInfoByCode(Map<String, Object> entityMap) throws DataAccessException;

	public List<HrColumn> queryColJoinSetList(Map<String, Object> entityMap) throws DataAccessException;

	public List<HrColumn> queryColJoinQueSetList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据部门ID查询该部门所有子部门
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String[] queryChildDeptById(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrStoreTabStrucByStoreType(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * 根据设置查询要显示的列
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrColumn> queryColumnsByStatisticSet(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据设置查询要显示的列
	 * 
	 * @param entityMap
	 * @return
	 */
	public List<HrColumn> queryColumnsByStatisticQueSet(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据用户ID获取权限
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrUserPermByUserId(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据角色ID获取权限
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrRolePermByRoleId(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据部门编码获取部门信息
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询条件连接符
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> queryHrConSignByCode() throws DataAccessException;

	public List<Map<String, Object>> queryDeptAll(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryHrFiiedByTabCode(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryHosEmpKind(Map<String, Object> mapVo);

	public List<Map<String, Object>> querySelectData(Map<String, Object> mapVo);

	public List<HrFiiedView> queryFiiedView(Map<String, Object> colStrucWhere);

	public List<Map<String, Object>> queryHosEmpData(Map<String, Object> mapVo);

	public int updateEmpBatch(@Param("tab_code") String tab_code, @Param("list") List<Map<String, Object>> updateList,@Param("list1") List<Map<String, Object>> keyUpdateList);

	public int addEmpBatch(@Param("tab_code") String tab_code, @Param("list") List<Map<String, Object>> seveList);

	public int queryHosEmpMaxId(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryEmpDataMap(Map<String, Object> deptOrempWhere);

	public List<Map<String, Object>> queryExcelColumn(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryHosDeptData(Map<String, Object> mapVo);

	public int addHosEmpDictBatch(@Param("userId") String userId, @Param("list") List<Map<String, Object>> seveList);

	public int updateHosEmpDictBatch(@Param("userId") String userId, @Param("list") List<Map<String, Object>> seveList);

	public int queryTabExistsData(@Param("sql") String cite_sql);

	// ------------------------Add by
	// alfred---------------------------------------------------------------------
	/**
	 * 根据 store_type_code or tab_code 返回对应HR_STORE_COL_SET 数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrStoreColSet(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据 store_type_code or tab_code 返回对应HR_STORE_QUE_SET 数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrStoreQueSet(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 执行动态SQL 返回Map数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryQuerySQL(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 执行动态SQL 返回Map数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryQuerySQL(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 查询 hr_fiied 开头的相关表
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrFiied(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询 hr_fiied_data （此表为内置数据）
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrFiiedData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询 HR_FIIED_VIEW 外部引用的SQL语句
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrFiiedSql(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询 HR_STORE_CONDITION 档案库默认查询条件
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrStoreCondition(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询 HR_TAB_STRUC 档案库默认查询条件
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrTabStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询 hr_statistic_tab_struc 简单统计 表构建
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrStatisticTabStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询 hr_statistic_custom 简单查询默认查询条件
	 * 
	 * @param entityMap
	 */
	public List<Map<String, Object>> queryHrStatisticCondition(Map<String, Object> entityMap)
			throws DataAccessException;

	public int queryRepeat(@Param("tab_code") String tab_code, @Param("tablePkMap") Map<String, Object> repeatMap)
			throws DataAccessException;

	/**
	 * 查询外部引用下拉框
	 * 
	 * @param tmpSQL
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrHosSelectCite(@Param("sql") String tmpSQL) throws DataAccessException;

	/**
	 * 查询内置下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrHosSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询外部引用下拉框所有数据
	 * 
	 * @param tmpSQL
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrHosSelectLeft(Map<String, Object> urlMap);
	/**
     * 查询表结构
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public List<HrColumn> queryColStruc(Map<String, Object> entityMap)throws DataAccessException;
    /**
     * 导入查询表内数据
     * @param tab_code 
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public List<Map<String, String>> queryTabeData(@Param("tab_code") String tab_code, @Param("map") Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, String>> queryTabeData(StringBuffer selectSql);
}
