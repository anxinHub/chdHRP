package com.chd.hrp.mat.dao.dura.termend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatDuraFinalChargeMapper extends SqlMapper {
	//判断是否存在未确认单据
	public int existsMatDuraFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException;
	//月末统一结账
	public String updateMatDuraFinalCharge(Map<String, Object> entityMap) throws DataAccessException;
	//月末统一反结账
	public String updateMatDuraFinalInverse(Map<String, Object> entityMap)throws DataAccessException;
	
}