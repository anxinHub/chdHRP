package com.chd.hrp.med.dao.termend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedFinalChargeMapper extends SqlMapper {
	//判断是否存在未确认单据
	public int existsMedFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException;
	//月末统一结账
	public String updateMedFinalCharge(Map<String, Object> entityMap) throws DataAccessException;
	//月末统一反结账
	public String updateMedFinalInverse(Map<String, Object> entityMap)throws DataAccessException;
	//月末分库房结账
	public String updateMedFinalChargeStore(Map<String, Object> entityMap) throws DataAccessException;
	//月末分库房反结账
	public String updateMedFinalInverseStore(Map<String, Object> entityMap)throws DataAccessException;
	//获取虚仓期间
	public Map<String, Object> queryYearMonthByStoreSet(Map<String, Object> entityMap)throws DataAccessException;
	
}