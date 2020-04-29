package com.chd.hrp.sys.service.baseData;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.entity.AccVouchType;
import com.chd.hrp.sys.entity.Rules;

public interface SysAccSubjService {
	
	/**
	 * 查询会计科目
	 * @param mapVo
	 * @return
	 */
	String queryAccSubj(Map<String, Object> mapVo);
	/**
	 * 删除会计科目
	 * @param listVo
	 * @return
	 */
	String deleteBatchAccSubj(List<Map<String, Object>> listVo);
	/**
	 * 查询科目编码规则
	 * @param mapVo
	 * @return
	 */
	Rules queryRulesByCode(Map<String, Object> mapVo);
	/**
	 * 添加会计科目
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addAccSubj(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询会计科目
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	AccSubj queryAccSubjByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 判断科目是否使用
	 * @param mapVo
	 * @return
	 */
	String existsSubjCheck(Map<String, Object> mapVo);
	int existsVouchLederBySubjCheck(Map<String, Object> mapVo);
	String queryCheckTypeBySubjId(Map<String, Object> entityMap)
			throws DataAccessException;
	AccCheckType queryCheckType(Map<String, Object> entityMap)
			throws DataAccessException;
	AccCheckType queryCheckColumn(Map<String, Object> entityMap)
			throws DataAccessException;
	String updateAccSubj(Map<String, Object> entityMap)
			throws DataAccessException;
	List<Map<String, Object>> queryAccSubjPrint(Map<String, Object> entityMap)
			throws DataAccessException;
	AccSubj queryAccSubjCode(Map<String, Object> entityMap)
			throws DataAccessException;
	AccCur queryAccCurByCode(Map<String, Object> entityMap)
			throws DataAccessException;
	AccSubjType queryAccSubjTypeByCode(Map<String, Object> entityMap)
			throws DataAccessException;
	AccSubjNature queryAccSubjNatureByCode(Map<String, Object> entityMap)
			throws DataAccessException;
	AccVouchType queryAccVouchTypeByCode(Map<String, Object> entityMap)
			throws DataAccessException;
	AccDeptAttr queryAccOutDeptByName(Map<String, Object> entityMap)
			throws DataAccessException;
	AccCheckType queryAccCheckTypeByName(Map<String, Object> entityMap)
			throws DataAccessException;
	List<AccSubj> accSubjImport(List<Map<String, Object>> entityMap)
			throws DataAccessException;
	String updateBatchAccSubj(List<Map<String, Object>> entityList)
			throws DataAccessException;
	AccSubj queryAccSubj_id(Map<String, Object> mapVo);
	String addBatchAccSubjExtend(Map<String, Object> mapVo)
			throws DataAccessException;
	//账簿中的科目选择器
	public String queryGroupSubjBySelector(Map<String,Object> entityMap) throws DataAccessException;

}
