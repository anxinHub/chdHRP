package com.chd.hrp.acc.dao.autovouch.accpubCost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccPubCostRegMapper extends SqlMapper {

	List<Map<String, Object>> queryDeptAllInfoDict(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDeptAllInfoDict(Map<String, Object> mapVo, RowBounds rowBounds);

	void saveAccPubCostReg(Map<String, Object> mapVo);

	void saveAccPubCostRegDept(Map<String, Object> mapVo);

	void saveAccPubCostRegDeptBatch(List<Map<String, Object>> deptList);

	void deleteAccPubCostReg(Map<String, Object> mapVo);

	void deleteAccPubCostRegDept(List<Map<String, Object>> deleteList);

	List<Map<String, Object>> queryAccPubCostRegDept(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccPubCostRegDept(Map<String, Object> mapVo, RowBounds rowBounds);

	void updateAccPubCostRegState(Map<String, Object> mapVo);

	void updateAccPubCostRegDept(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccPubCostRegOtherDept(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccPubCostRegOtherDept(Map<String, Object> mapVo, RowBounds rowBounds);

	Map<String, Object> queryPubCostRegDeptByCode(Map<String, Object> mapVo);

	Map<String, Object> queryPubCostRegOtherDeptByCode(Map<String, Object> mapVo);

	void deleteAccPubCostRegDeptBatch(Map<String, Object> saveMap);

	void deleteAccPubCostRegBatch(List<Map<String, Object>> deleteList);

	void updateAccPubCostRegDeptBatch(List<Map<String, Object>> updateList);

	void extendOtherDept(Map<String, Object> saveMap);

	int queryIsCreateVouch(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccPubCostRegDeptNoSum(Map<String, Object> mapVo);

}
