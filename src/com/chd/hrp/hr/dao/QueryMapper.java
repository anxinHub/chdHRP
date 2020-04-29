package com.chd.hrp.hr.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface QueryMapper extends SqlMapper{
    /**
     * 查询序列
     * @param map
     * @return
     */
	int querySeqByTabCode(Map<String, Object> map);
	
	
}
