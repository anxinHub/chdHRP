/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.mat.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 安全库存调整<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface MatSafeChangeMapper extends SqlMapper{
	//主查询
	public List<Map<String, Object>> queryMatSafeChangeList(Map<String, Object> map)throws DataAccessException;
	public List<Map<String, Object>> queryMatSafeChangeList(Map<String, Object> map, RowBounds rowBounds)throws DataAccessException;
	
	//加载主表
	public Map<String, Object> queryMatSafeChangeById(Map<String, Object> map)throws DataAccessException;
	//加载明细表
	public List<Map<String, Object>> queryMatSafeChangeDetailById(Map<String, Object> map) throws DataAccessException;
	
	//保存主表
	public int addMatSafeChange(Map<String, Object> map) throws DataAccessException;
	//修改主表
	public int updateMatSafeChangeById(Map<String, Object> map) throws DataAccessException;
	//保存明细表
	public int addMatSafeChangeDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//校验发票状态
	public int existsMatSafeChangeState(Map<String, Object> map) throws DataAccessException;
	public int existsMatSafeChangeStateBatch(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//删除发票
	public int deleteMatSafeChange(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//删除发票明细（保存时使用）
	public int deleteMatSafeChangeDetailById(Map<String, Object> map) throws DataAccessException;
	
	//审核or消审
	public int updateMatSafeChangeState(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//调整单更新到安全库存中
	public int updateMatStoreInvBySafeChange(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//确认单据
	public int confirmMatSafeChange(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//材料列表查询
	public List<Map<String, Object>> queryMatInvBySafeChange(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	//主表模板打印
	public Map<String, Object> queryMatSafeChangePrintByTemlate(Map<String,Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatSafeChangePrintByTemlateBatch(Map<String,Object> map) throws DataAccessException;
	//明细表模板打印
    public List<Map<String, Object>> queryMatSafeChangeDetailPrintByTemlate(Map<String,Object> map) throws DataAccessException;
}
