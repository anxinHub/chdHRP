package com.chd.hrp.htc.dao.income.business.hos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.business.hos.HtcIncomeChargeCostHos;
 
public interface HtcIncomeChargeCostHosMapper extends SqlMapper{
	
	public Map<String, Object> addHtcIncomeChargeCostHos(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcIncomeChargeCostHos> queryHtcIncomeChargeCostHos(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcIncomeChargeCostHos> queryHtcIncomeChargeCostHos(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
