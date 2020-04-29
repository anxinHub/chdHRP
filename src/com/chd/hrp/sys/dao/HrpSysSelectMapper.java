/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 鏅烘収浜戝悍锛堝寳浜級鏁版嵁绉戞妧鏈夐檺鍏徃
 */
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.sys.entity.HrpSysSelect;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public interface HrpSysSelectMapper extends SqlMapper {
	// 鑱屽伐鏌ヨ
	public List<HrpSysSelect> queryEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<HrpSysSelect> queryEmpDictDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	// 鑱屽伐鏌ヨ id涓篶ode
	public List<HrpSysSelect> queryEmpDictForCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpSysSelect> queryEmp(Map<String, Object> entityMap) throws DataAccessException;
	public List<HrpSysSelect> queryEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<HrpSysSelect> queryEmpNew(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鍖婚櫌绛夌骇鏌ヨ
	public List<HrpSysSelect> queryHosLevelDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鍖婚櫌鍒嗙被鏌ヨ
	public List<HrpSysSelect> queryHosTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鍥界睄鏌ヨ
	public List<HrpSysSelect> queryCountriesDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鍦板尯鏌ヨ
	public List<HrpSysSelect> queryRegionDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鏀挎不闈㈣矊鏌ヨ
	public List<HrpSysSelect> queryPoliticalDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鐢ㄦ埛鏌ヨ
	public List<HrpSysSelect> queryUserDict(Map<String, Object> entityMap) throws DataAccessException;

	public List<HrpSysSelect> queryUserDictBySys(Map<String, Object> entityMap) throws DataAccessException;
	
	// 鍖婚櫌鏌ヨ
	public List<HrpSysSelect> queryHosInfoDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpSysSelect> queryHosInfoDictPerm(Map<String, Object> entityMap) throws DataAccessException;

	// 璐﹀鏌ヨ
	public List<HrpSysSelect> queryCopyCodeDict(Map<String, Object> entityMap) throws DataAccessException;

	// 璐﹀鏌ヨ鏍规嵁鐢ㄦ埛鏉冮檺鑾峰彇
	public List<HrpSysSelect> queryCopyCodeDictPerm(Map<String, Object> entityMap) throws DataAccessException;

	// 瑙掕壊鏌ヨ
	public List<HrpSysSelect> queryRoleDict(Map<String, Object> entityMap) throws DataAccessException;

	// 绯荤粺妯″潡鏌ヨ
	public List<HrpSysSelect> queryModDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鐢ㄦ埛瀵瑰簲鐨勭郴缁熸ā鍧楁潈闄�
	public List<HrpSysSelect> queryModDictPerm(Map<String, Object> entityMap) throws DataAccessException;

	// 绠＄悊鍛樺搴旂殑绯荤粺妯″潡鏉冮檺
	public List<HrpSysSelect> queryModDictAdminPerm(Map<String, Object> entityMap) throws DataAccessException;

	// 閮ㄩ棬鍒嗙被鏌ヨ
	public List<HrpSysSelect> queryDeptKindDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 閮ㄩ棬鏌ヨ
	public List<HrpSysSelect> queryDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鏈骇绉戝
	public List<HrpSysSelect> queryDeptDictLast(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 閮ㄩ棬鏌ヨ
	public List<HrpSysSelect> queryDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 瀛﹀巻鏌ヨ
	public List<HrpSysSelect> querySchoolDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 宀椾綅鏌ヨ
	public List<HrpSysSelect> queryStationDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鑱屽姟鏌ヨ
	public List<HrpSysSelect> queryDutyDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鑱屽伐鍒嗙被鏌ヨ
	public List<HrpSysSelect> queryEmpKindDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 椤圭洰绫诲瀷鏌ヨ
	public List<HrpSysSelect> queryProjTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 椤圭洰绾у埆鏌ヨ
	public List<HrpSysSelect> queryProjLevelDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 椤圭洰鐢ㄩ�旀煡璇�
	public List<HrpSysSelect> queryProjUseDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 椤圭洰鏌ヨ
	public List<HrpSysSelect> queryProjDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 渚涘簲鍟嗘煡璇�
	public List<HrpSysSelect> querySupDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 渚涘簲鍟嗙被鍒煡璇�
	public List<HrpSysSelect> querySupTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 瀹㈡埛鏌ヨ
	public List<HrpSysSelect> queryCusDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 瀹㈡埛绫诲埆鏌ヨ
	public List<HrpSysSelect> queryCusTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鐢熶骇鍘傚晢鏌ヨ
	public List<HrpSysSelect> queryFacDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鐢熶骇鍘傚晢绫诲埆鏌ヨ
	public List<HrpSysSelect> queryFacTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 搴撴埧鏌ヨ
	public List<HrpSysSelect> queryStoreDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 搴撴埧绫诲埆鏌ヨ
	public List<HrpSysSelect> queryStoreTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鍖婚櫌鍙樻洿鏌ヨ
	public List<HrpSysSelect> queryHosInfo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 璁￠噺鍗曚綅鏌ヨ
	public List<HrpSysSelect> queryUnitDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鐥呬汉绫诲埆鏌ヨ
	public List<HrpSysSelect> queryPatientTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 璧勯噾鏉ユ簮鏌ヨ
	public List<HrpSysSelect> querySourceDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpSysSelect> queryHosNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//加载项目
	public List<HrpSysSelect> queryProjDictDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//加载项目明细
	public List<HrpSysSelect> queryProjDictDictDet(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//加载仓库
	public List<HrpSysSelect> queryStoreDictDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//加载虚仓
	public List<HrpSysSelect> querySetDictDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//加载供应商
	public List<HrpSysSelect> querySupDictDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//数据权限不分页默认取前50条
	public List<HrpSysSelect> queryPermData(Map<String, Object> entityMap) throws DataAccessException;

	// 璁￠噺鍗曚綅
	public List<HrpSysSelect> queryHosUnitDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 鑷姩鍑瘉妯″潡绫诲埆
	public List<HrpSysSelect> querySysBusiMod(Map<String, Object> entityMap) throws DataAccessException;

	// 璧勯噾鏉ユ簮绫诲埆
	public List<HrpSysSelect> querySysSourceNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//鍑瘉鍒跺崟鍏ㄩ儴鐩稿叧璐熻矗浜轰笅鎷夋
	public List<HrpSysSelect> queryNewUserDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//璁拌处浜�
	public List<HrpSysSelect> queryAccUserDict(Map<String, Object> entityMap) throws DataAccessException;

	//瀹℃牳浜�
	public List<HrpSysSelect> queryAuditUserDict(Map<String, Object> entityMap) throws DataAccessException;

	//鍒跺崟浜�
	public List<HrpSysSelect> queryCreateUserDict(Map<String, Object> entityMap) throws DataAccessException;

	//鍑虹撼浜�
	public List<HrpSysSelect> queryCashUserDict(Map<String, Object> entityMap) throws DataAccessException;

	// 部门类型
	public List<HrpSysSelect> queryAccDeptType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	// 支出性质
	public List<HrpSysSelect> queryAccDeptOut(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询会计科目类别
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryAccSubjType(Map<String, Object> entityMap);
	/**
	 * 查询科目级次
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> querySubjLevel(Map<String, Object> mapVo);
	/**
	 * 凭证类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryVouchType(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 币种
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryCur(Map<String, Object> entityMap) throws DataAccessException;
	//集团专用币种
	public List<HrpAccSelect> queryGroupCur(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 核算类型
	 * @param entityMap
	 * @return
	 */
	public List<HrpAccSelect> queryCheckType(Map<String, Object> entityMap);
	/**
	 * 查询科目性质
	 * @param entityMap
	 * @return
	 */
	public List<HrpAccSelect> querySubjNature(Map<String, Object> entityMap);
	/**
	 * 支出性质-现金流量项目
	 * @param mapVo
	 * @return
	 */
	public List<HrpAccSelect> queryDeptOutSelect(Map<String, Object> mapVo);
 
	/**
	 * 发放方式  --- 职工辅助核算
	 * @param mapVo
	 * @return
	 */
	public List<HrpAccSelect> queryEmpPay(Map<String, Object> mapVo);
 
	/**	 * 科目类别
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> querySubjTypeForSelect(Map<String, Object> entityMap) throws DataAccessException;
	// 部门类型
	public List<HrpAccSelect> queryDeptType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 部门性质
	public List<HrpAccSelect> queryDeptNatur(Map<String, Object> entityMap) throws DataAccessException;

	//科室字典(根据参数决定是否按权限)
	public List<HrpAccSelect> queryHosDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//查询自定义辅助核算字典对应表
	public List<HrpAccSelect> queryAccBusiZCheck(Map<String, Object> mapVo) throws DataAccessException;

	//查询行业性质
	public List<Map<String, Object>> queryHosNatureDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//查询账套	
	public List<Map<String, Object>> queryCopyDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	//查询会计年度	
	public List<Map<String, Object>> queryAcctYearDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//
	public List<Map<String, Object>> queryHosCopyDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccYearDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询   集团部门   用于部门辅助账
	public List<HrpSysSelect> queryDeptByRela(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询   集团职工   用于职工辅助账
	public List<HrpSysSelect> queryEmpByRela(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询   集团项目   用于项目辅助账
	public List<HrpSysSelect> queryProjDictDictByRela(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询   集团供应商   用于供应商辅助账
	public List<HrpSysSelect> querySupDictDictByRela(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询   集团客户   用于客户辅助账
	public List<HrpSysSelect> queryCusDictByRela(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询HIS科室
	public List<HrpSysSelect> querySysHisDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
