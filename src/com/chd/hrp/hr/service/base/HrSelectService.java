package com.chd.hrp.hr.service.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrSelectService {
	
	/**
	 * 模块下拉框
	 * @return
	 */
	String queryMods();
	/**
	 * 性别下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDicSexSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 民族下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDicNationSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 政治面貌下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDicPoliticalSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 婚姻下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDicMarriageSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 户口性质下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDicResidenceSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询树型部门数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosDeptDictTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 人员档案库分类
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrStoreTypeSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 人员档案库数据集表
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrStoreTabStrucSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabTypeSelect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 数据构建 数据表分类下拉框（新）
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTableTypeSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 数据表下拉框
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabStrucSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 系统结构列 下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrColStrucSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 系统结构列 表格
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrColStrucGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 数据构建 系统结构列 数据类型 下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrColDataTypeSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 组件类型HR_COM_TYP 下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrComTypeSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条件符号HR_CON_SIGN 下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrConSignSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 连接符号HR_JOIN_SIGN 下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrJoinSignSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代码表HR_FIIED_TAB_STRUC
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrFiiedTabStrucDic(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代码项数据表HR_FIIED_DATA
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrFiiedDataDicByTabCol(Map<String, Object> entityMap) throws DataAccessException;

	String queryHrFiiedDataSelect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代码分类
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryTypeFiledTypeSelect(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 人员信息 包括电话 照片
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryEmp(Map<String, Object> mapVo) throws DataAccessException;


	/**
	 * 部门下拉框
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosDeptSelect(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 按部门分类查询人员
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryEmpSelect(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询部门
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDeptTree(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询职务
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDuty(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询职称信息
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTitle(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询护理等级
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDicLevel(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询员工姓名 职称 职务 护理等级 科室
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryEmpDuty(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询人员下拉表格
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrEmpData(Map<String, Object> mapVo) throws DataAccessException;
	
	String queryHosEmpGrid(Map<String, Object> entityMap) throws DataAccessException;

	// 解释:存储过程包名
	String queryHpmOraclePkg(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 函数分类
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHpmFunType(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 绩效函数参数取值
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryHrFunParaMethod(Map<String, Object> mapVo);
    /**
     * 查询岗位
     * @param mapVo
     * @return
     */
	String queryStation(Map<String, Object> mapVo);
    /**
     * 查询职工类别
     * @param mapVo
     * @return
     */
	String queryEmpType(Map<String, Object> mapVo);
	/**
	 * 查询人员详细信息
	 * @param mapVo
	 * @return
	 */
	String queryPersonnel(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询人员下拉框
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryPerson(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 药品权限管理
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryPermTypeSelect(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 药品
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryMedTypeSelect(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询问题定性
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryProbNature(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询问题归类
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryProbType(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 是否下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryIsOrNot(Map<String, Object> entityMap) throws DataAccessException;
    /**
     * 颜色下拉框
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryColorSelect(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 考勤分类
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryAttendTypesSelect(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 单位下拉框
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryHosInfoSelect(Map<String, Object> mapVo)throws DataAccessException;

	String queryOvertimeKind(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 班次下拉框
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryCalssCode(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 出勤科室下拉框
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryCalssDept(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 考勤项目数据集表
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryHrStoreTabStruc(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 职务等级
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDutyLevel(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 职务类别
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDutyKind(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 班次分类
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendCalssType(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 职务名称
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryDutyCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 岗位等级
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryStationLevel(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 考勤项目
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendCodeCla(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 考勤项目分组
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendFZ(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 根据员工查科室
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosDeptByEmpSelect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查获奖情况
	 * @param mapVo
	 * @return
	 * @author yangyunfei
	 */
	String queryPrize(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 代码项下拉加载
	 * @param mapVo
	 * @return
	 * @author Lcl
	 */
	String queryAttendFieldOption(Map<String, Object> mapVo);
	String queryDicAreaAttr(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 考勤项目树
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendItemTree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 考勤项目
	 * @param mapVo
	 * @return
	 */
	String queryAttendTypes(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询所有休假
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAllAttendCode(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改申请下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAppEmpSelect(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 职工分类（提供下拉）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrEmpKindSelect(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查用戶
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryUserSelect(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 培训类别
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosTrainTypeSelect(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 培训方式
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosTrainWaySelect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 考核方式
	 */
	String queryHrExamWaySelect(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询培训地点
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTrainSiteSelect(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 是否考勤是1 的职工
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryEmpSelectAttend(Map<String, Object> mapVo) throws DataAccessException;
	String queryEmpSelectInfo(Map<String, Object> mapVo) throws DataAccessException;
	String queryHosAftDeptSelect(Map<String, Object> mapVo)throws DataAccessException;
	String baseSelect(Map<String, Object> mapVo)throws DataAccessException;
	String queryStManTree(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询定员信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryManNumData(Map<String, Object> mapVo)throws DataAccessException;
	
}
