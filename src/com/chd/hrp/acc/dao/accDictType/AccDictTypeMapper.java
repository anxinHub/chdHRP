package com.chd.hrp.acc.dao.accDictType;

import com.chd.base.SqlMapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface AccDictTypeMapper extends SqlMapper{

	//模糊查询
	List<Map<String, Object>> queryDictMp(Map<String, Object> mapVo);
	List<Map<String, Object>> queryDictCodeMp(Map<String, Object> mapVo);
	//打印查询
	List<Map<String, Object>> queryDictPrintMp(Map<String, Object> mapVo) throws DataAccessException;

	//添加
	int addDictMp(Map<String, Object> mapVo);
	//删除
	int deleteDictMp(Map<String, Object> mapVo);
	//修改
	int updateDictMp(Map<String, Object> mapVo);



}
