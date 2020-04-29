package com.chd.hrp.hr.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HrSelectMapper extends SqlMapper {

	/**
	 * 查询部门（树型结构）
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHosDeptDictTree(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * 人事档案库分类
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrStoreTypeSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 人员档案库数据集
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrStoreTabStrucSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 数据表分类
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrTabTypeSelect(Map<String, Object> entityMap)
			throws DataAccessException;
	
	/**
	 * 数据构建 数据表分类(新)
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrTableTypeSelect(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * 数据构建 数据表
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrTabStrucSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 系统结构列
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrColStrucSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 系统结构列 表格
	 * 
	 * @param entityMap
	 * @return col_code,col_name,note
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrColStrucGrid(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * 数据构建 系统结构列 数据类型
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrColDataTypeSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 组件类型HR_COM_TYP
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrComTypeSelect(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * 条件符号HR_CON_SIGN
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrConSignSelect(Map<String, Object> entityMap)
			throws DataAccessException;

	/**
	 * 连接符号HR_JOIN_SIGN
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrJoinSignSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代码表HR_FIIED_TAB_STRUC
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrFiiedTabStrucDic(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代码分类
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryTypeFiledTypeSelect(Map<String, Object> mapVo)
			throws DataAccessException;

	/**
	 * 代码项数据表HR_FIIED_DATA
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrFiiedDataDicByTabCol(
			Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryHrFiiedDataSelect(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询人员信息 带分页
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryEmp(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查询人员信息
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryEmp(Map<String, Object> entityMap);

	/**
	 * 部门下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHosDeptSelect(Map<String, Object> entityMap);

	/**
	 * 按部门分类查询人员
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryEmpSelect(Map<String, Object> entityMap);

	/**
	 * 查询部门树形
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDeptTree(Map<String, Object> entityMap);

	/**
	 * 查询职务
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDuty(Map<String, Object> entityMap);

	/**
	 * 查询职称
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrTitle(Map<String, Object> entityMap);

	/**
	 * 查询护理等级
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDicLevel(Map<String, Object> entityMap);

	/**
	 * 查询员工姓名 职称 职务 护理等级 科室
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryEmpDuty(Map<String, Object> entityMap);

	/**
	 * 查询人员下拉表格
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrEmpData(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHosEmpGrid(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHpmOraclePkg(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHpmFunType(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHrFunParaMethod(
			Map<String, Object> entityMap, RowBounds rowBounds);

	/**
	 * 查询岗位
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStation(Map<String, Object> entityMap);

	/**
	 * 查询职工类别
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryEmpType(Map<String, Object> entityMap);

	/**
	 * 
	 * 查询职工详细信息
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPersonnel(Map<String, Object> entityMap);

	/**
	 * 人员下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPerson(Map<String, Object> entityMap);

	/**
	 * 药品权限下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPermTypeSelect(Map<String, Object> entityMap);

	/**
	 * 药品下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryMedTypeSelect(Map<String, Object> entityMap);

	/**
	 * 性别下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDicSexSelect(Map<String, Object> entityMap);

	/**
	 * 查询定性
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryProbNature(Map<String, Object> entityMap);

	/**
	 * 查询问题归类
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryProbType(Map<String, Object> entityMap);

	/**
	 * 是否下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryIsOrNot(Map<String, Object> entityMap);

	/**
	 * 查询是否存在
	 * 
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryEmpById(Map<String, Object> saveMap);

	/**
	 * 查询科室是否存在
	 * 
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryDeptById(Map<String, Object> saveMap);

	/**
	 * 颜色下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryColorSelect(Map<String, Object> entityMap);

	/**
	 * 考勤分类
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendTypesSelect(
			Map<String, Object> entityMap);

	/**
	 * 查询单位信息
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHosInfoSelect(Map<String, Object> entityMap);

	List<Map<String, Object>> queryOvertimeKind(Map<String, Object> entityMap);

	/**
	 * 班次下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryCalssCode(Map<String, Object> entityMap);

	/**
	 * 出勤科室下拉框
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryCalssDept(Map<String, Object> entityMap);

	/**
	 * 考勤项目数据集
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrStoreTabStruc(Map<String, Object> entityMap);

	/**
	 * 系统模块数据
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryMod(Map<String, Object> entityMap);

	/**
	 * 职务等级
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDutyLevel(Map<String, Object> entityMap);

	/**
	 * 职务类别
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDutyKind(Map<String, Object> entityMap);

	/**
	 * 班次分类
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendCalssType(Map<String, Object> entityMap);

	/**
	 * 职务名称
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDutyCode(Map<String, Object> entityMap);

	/**
	 * 岗位等级
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStationLevel(Map<String, Object> entityMap);

	/**
	 * 考勤项目
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendCodeCla(Map<String, Object> entityMap);

	/**
	 * 岗位信息
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStationInfo(Map<String, Object> entityMap);

	/**
	 * 部门分类
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDeptKind(Map<String, Object> entityMap);

	/**
	 * 考勤项目分组
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendFZ(Map<String, Object> entityMap);

	/**
	 * 根据员工查科室信息
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHosDeptByEmpSelect(
			Map<String, Object> entityMap);

	/**
	 * 查获奖情况
	 * 
	 * @param mapVo
	 * @return
	 * @author yangyunfei
	 */
	List<Map<String, Object>> queryPrize(Map<String, Object> mapVo);

	/**
	 * 代码项下拉加载
	 * 
	 * @param mapVo
	 * @return
	 * @author Lcl
	 */
	List<Map<String, Object>> queryAttendFieldOption(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDicAreaAttr(Map<String, Object> mapVo);

	List<Map<String, Object>> queryEmpDuty(Map<String, Object> entityMap,
			RowBounds rowBounds);

	/**
	 * 考勤项目
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendTypes(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAttendItemTree(Map<String, Object> entityMap);

	/**
	 * 查询科室
	 * 
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryDeptByCode(Map<String, Object> saveMap);

	/**
	 * 查询职工
	 * 
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryEmpByCode(Map<String, Object> saveMap);

	List<Map<String, Object>> queryAllAttendCode(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAppEmpSelect(Map<String, Object> entityMap);

	/**
	 * 职工分类（提供下拉）
	 * 
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryHrEmpKindSelect(Map<String, Object> mapVo);

	/**
	 * 查询系统用户
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryUserSelect(Map<String, Object> entityMap);
   /**
    * 查询培训类别
    * @param entityMap
    * @return
    */
	List<Map<String, Object>> queryHosTrainTypeSelect(Map<String, Object> entityMap);
   /**
    * 查询培训方式
    * @param entityMap
    * @return
    */
   List<Map<String, Object>> queryHosTrainWaySelect(Map<String, Object> entityMap);

   /**
    * 查询考核方式
    */
   List<Map<String, Object>> queryHrExamWaySelect(Map<String, Object> mapVo);
    /**
     * 查询培训地点
     * @param mapVo
     * @return
     */
   List<Map<String, Object>> queryHrTrainSiteSelect(Map<String, Object> mapVo);
   /**
    * 查询人员按部门下拉框(只查考勤是的人)
    * @param entityMap
    * @return
    */

	List<Map<String, Object>> queryEmpSelectAttend(Map<String, Object> entityMap);

   List<Map<String, Object>> queryEmpSelectInfo(Map<String, Object> mapVo);

List<Map<String, Object>> queryHosAftDeptSelect(Map<String, Object> entityMap);

List<Map<String, java.lang.Object>> baseSelect(Map<String, Object> entityMap);

List<Map<String, Object>> queryStManTree(Map<String, Object> entityMap);

List<Map<String, Object>> queryManNumData(Map<String, Object> entityMap);
}
