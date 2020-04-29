/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 平行记账模板<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgTpMapper extends SqlMapper{
	//列表查询
	public List<Map<String, Object>> queryList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询用于打印
	public List<Map<String, Object>> queryList(Map<String,Object> entityMap) throws DataAccessException;
	
	//精准查询
	public Map<String, Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	//添加
	public int addMain(Map<String,Object> entityMap) throws DataAccessException;
	public int addDetail(List<Map<String,Object>> entityList) throws DataAccessException;
	
	//修改
	public int updateMain(Map<String,Object> entityMap) throws DataAccessException;
	
	//删除明细
	public int deleteDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	//删除
	public int deleteAll(Map<String, Object> entityMap) throws DataAccessException;
	
	//校验名称或者编码是否重复
	public int existsByCode(Map<String, Object> entityMap) throws DataAccessException;
	public int existsByName(Map<String, Object> entityMap) throws DataAccessException;
	
	//获取最大排序号
	public int getMaxSortCode(Map<String, Object> entityMap) throws DataAccessException;
	
	//科目下拉列表
	public List<Map<String, Object>> querySubjSelect(Map<String, Object> entityMap) throws DataAccessException;
	
	//用户下拉列表
	public List<Map<String, Object>> queryUserSelect(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDetailByCodeVouch(Map<String, Object> entityMap) throws DataAccessException;
	
}
