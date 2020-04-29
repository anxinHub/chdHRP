package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.BusiQuery;

public interface AccBudgAutoVouchMapper extends SqlMapper{
	
	//查询表头
	public List<BusiQuery> queryBudgAutoVouchHead(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryBudgAutoVouch(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryBudgAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	
	//按日期生成凭证，查询业务ID 
	public List<String> queryBudgAutoVouchBusiIdList(Map<String, Object> map) throws DataAccessException;
	
	//判断是否生成凭证，自动凭证通用
	public List<String> queryAutoVouchIsCreate(Map<String, Object> map) throws DataAccessException;
	
	//查询凭证视图字段
	public List<Map<String, Object>> queryAutoVouchView(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询自动凭证数据
	public List<Map<String, Object>> queryVouchJsonByBusi(Map<String, Object> entityMap) throws DataAccessException;
	
	// 查询自动凭证数据-辅助核算
	public List<Map<String, Object>> queryVouchCheckJsonByBusi(Map<String, Object> entityMap) throws DataAccessException;
	

	//保存自动凭证日志临时表 
	public int saveAutoVouchLogTemp(Map<String, Object> entityMap) throws DataAccessException;
	
	//保存自动凭证日志临时表 
	public int saveAutoVouchLogTempByNo(@Param("map")Map<String, Object> map,@Param("list") String[] list)throws DataAccessException;
}
