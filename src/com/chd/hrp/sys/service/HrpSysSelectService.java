/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 鏅烘収浜戝悍锛堝寳浜級鏁版嵁绉戞妧鏈夐檺鍏徃
 */
package com.chd.hrp.sys.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public interface HrpSysSelectService {

	public String queryEmpDict(Map<String, Object> map) throws DataAccessException;
	public String queryEmpDictDept(Map<String, Object> map) throws DataAccessException;
	public String queryEmpDictForCode(Map<String, Object> map) throws DataAccessException;

	public String queryEmp(Map<String, Object> map) throws DataAccessException;
	public String queryEmpNew(Map<String, Object> map) throws DataAccessException;

	public String queryHosLevelDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCountriesDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryRegionDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryPoliticalDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryUserDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosInfoDictPerm(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCopyCodeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCopyCodeDictPerm(Map<String, Object> entityMap) throws DataAccessException;

	public String queryRoleDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryModDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryModDictPerm(Map<String, Object> entityMap) throws DataAccessException;

	public String queryModDictAdminPerm(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDeptKindDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDeptDictLast(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDept(Map<String, Object> entityMap) throws DataAccessException;

	public String querySchoolDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryStationDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDutyDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryEmpKindDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryProjTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryProjLevelDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryProjUseDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryProjDict(Map<String, Object> entityMap) throws DataAccessException;

	public String querySupDict(Map<String, Object> entityMap) throws DataAccessException;

	public String querySupTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCusDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCusTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryFacDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryFacTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryStoreDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryStoreTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryUnitDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryPatientTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String querySourceDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosInfo(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosNature(Map<String, Object> entityMap) throws DataAccessException;

	public String queryProjDictDict(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryProjDictDictDet(Map<String, Object> entityMap) throws DataAccessException;

	public String queryStoreDictDict(Map<String, Object> entityMap) throws DataAccessException;

	public String querySetDictDict(Map<String, Object> entityMap) throws DataAccessException;
	
	public String querySupDictDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryPermData(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosUnitDict(Map<String, Object> entityMap) throws DataAccessException;

	// 鑷姩鍑瘉妯″潡绫诲埆
	public String querySysBusiMod(Map<String, Object> entityMap) throws DataAccessException;

	public String querySysSourceNature(Map<String, Object> entityMap) throws DataAccessException;

	//璁拌处浜�
	public String queryAccUserDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//瀹℃牳浜�
	public String queryAuditUserDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//鍒跺崟浜�
	public String queryCreateUserDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//鍑虹撼浜�
	public String queryCashUserDict(Map<String, Object> entityMap) throws DataAccessException;

	//部门类型
	public String queryAccDeptType(Map<String, Object> entityMap) throws DataAccessException;
	//支出性质
	public String queryAccDeptOut(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询科目类别
	 * @param mapVo
	 * @return
	 */
	String queryAccSubjType(Map<String, Object> mapVo);
	/**
	 * 查询科目级次
	 * @param mapVo
	 * @return
	 */
	String querySubjLevel(Map<String, Object> mapVo);
	/**
	 * 凭证类型
	 * @param mapVo
	 * @return
	 */
	String queryVouchType(Map<String, Object> mapVo);
	/**
	 * 查询币种
	 * @param mapVo
	 * @return
	 */
	String queryCur(Map<String, Object> mapVo);
	/**
	 * 查询币种集团专用
	 * @param mapVo
	 * @return
	 */
	String queryGroupCur(Map<String, Object> mapVo);
	/**
	 * 核算类型
	 * @param mapVo
	 * @return
	 */
	String queryCheckType(Map<String, Object> mapVo);
	/**
	 * 科目性质
	 * @param mapVo
	 * @return
	 */
	String querySubjNature(Map<String, Object> mapVo);

	String queryDeptOutSelect(Map<String, Object> mapVo);
 
	
	String queryDeptNatur(Map<String, Object> mapVo);
	
	String queryEmpPay(Map<String, Object> mapVo);
 
	/**
	 * 科目类别下拉框
	 * @param mapVo
	 * @return
	 */
	String querySubjTypeForSelect(Map<String, Object> mapVo);

	String queryDeptType(Map<String, Object> entityMap)
			throws DataAccessException;
	
	//科室字典(根据参数决定是否按权限)
	public String queryHosDept(Map<String, Object> mapVo) throws DataAccessException;
	//查询自定义辅助核算字典对应表
	public String queryAccBusiZCheck(Map<String, Object> mapVo) throws DataAccessException;

	String queryHosNatureDict(Map<String, Object> entityMap)
			throws DataAccessException;

	String queryCopyDict(Map<String, Object> entityMap)
			throws DataAccessException;

	String queryAcctYearDict(Map<String, Object> entityMap)
			throws DataAccessException;
	public String queryHosCopyDict(Map<String, Object> entityMap)throws DataAccessException;
	public String queryAccYearDict(Map<String, Object> entityMap)throws DataAccessException;
	public String queryDeptByRela(Map<String, Object> entityMap)throws DataAccessException;
	public String queryEmpByRela(Map<String, Object> entityMap)throws DataAccessException;
	public String queryProjDictDictByRela(Map<String, Object> entityMap)throws DataAccessException;
	public String querySupDictDictByRela(Map<String, Object> entityMap)throws DataAccessException;
	public String queryCusDictByRela(Map<String, Object> entityMap)throws DataAccessException;

	String queryUserDictBySys(Map<String, Object> entityMap)throws DataAccessException;

	public String querySysHisDeptDict(Map<String, Object> entityMap)throws DataAccessException;
}
