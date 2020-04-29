package com.chd.hrp.pac.dao.cmitype;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.cmitype.PACTInterfaceType;


public interface PACTInterfaceTypeMapper extends SqlMapper{
	//删除,更新状态
	public int deletePACTInterfaceTypByStatus(List<PACTInterfaceType> list);
	
	//新增
	
	public int addBatchPACTInterfaceType(List<?> entityMap)throws DataAccessException;
	//保存
	public int updatePACTInterfaceTyp(List<PACTInterfaceType> list);
	
	//全查询
	public List<Map<String, Object>> queryPACTInterfaceType(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryPACTInterfaceType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
}