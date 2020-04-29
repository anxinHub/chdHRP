package com.chd.hrp.hr.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface DeleteMapper extends SqlMapper{
	/**
	 * 删除
	 * @param entityMap
	 */
	
	void deleteBatchBase(List<String> stringSqlList);

}
