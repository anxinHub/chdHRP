package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccBadDebtsMapper extends SqlMapper{

	//查询
	List<Map<String, Object>> queryBadDebts(Map<String, Object> mapVo);
	
	//保存
	int addBadDebts(List<Map> list);

	//查询科目
	List<Map<String, Object>> queryAccSubjSelect(Map<String, Object> mapVo);

	//全量更新删除所有数据
	void deleteAddBadDebts();

	int addBadDebtsTo(List<Map> addList);

}
