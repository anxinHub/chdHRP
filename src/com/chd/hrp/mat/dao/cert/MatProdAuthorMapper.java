/**
* @Copyright: Copyright (c) 2017-9-13 
* @Company: 杭州亦童科技有限公司
*/
package com.chd.hrp.mat.dao.cert;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatProdAuthorMapper extends SqlMapper{
	
	//列表查询
	public List<Map<String, Object>> queryMatProdAuthorList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatProdAuthorList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	//查询
	public Map<String, Object> queryMatProdAuthorById(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatProdAuthorDetailById(Map<String, Object> map) throws DataAccessException;
	//目标企业字典加载
	public List<Map<String, Object>> queryHosFacSup(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	//保存
	public int addMatProdAuthor(Map<String, Object> map) throws DataAccessException;
	public int updateMatProdAuthor(Map<String, Object> map) throws DataAccessException;
	public int deleteMatProdAuthorDetail(Map<String, Object> map) throws DataAccessException;
	public int addMatProdAuthorDetail(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//更新老证信息
	public int updateMatProdAuthorToOld(Map<String, Object> map) throws DataAccessException;
	//复制老证材料信息
	public int addMatProdAuthorInvByCopyOld(Map<String, Object> map) throws DataAccessException;
	//修改关联材料的厂商与供应商
	public int updateMatProdAuthorInvForFacSup(Map<String, Object> map) throws DataAccessException;
	//删除
	public int deleteMatProdAuthor(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//获取需要删除的明细ID集合用于文件批量删除
	public List<String> queryMatProdAuthorDetailListByDelete(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//修改认证标志
	public int updateMatProdAuthorAuthentState(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//修改审核标志
	public int updateMatProdAuthorCheckState(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//导入
	public int addMatProdAuthorByImp(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//校验是否含不为该状态的数据
	public Integer notExistsMatProdAuthorByState(@Param(value="map")Map<String, Object> map, @Param(value="state_col")String state_col, @Param(value="state_val")int state_val, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	

	//查询材料列表
	public List<Map<String, Object>> queryMatInvList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatInvList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	//查询关联材料
	public List<Map<String, Object>> queryMatProdAuthorInvList(Map<String, Object> map) throws DataAccessException;
	//保存关联材料
	public int deleteMatProdAuthorInv(Map<String, Object> map) throws DataAccessException;
	public int addMatProdAuthorInv(@Param(value="map")Map<String, Object> map, @Param(value="list")List<String> list) throws DataAccessException;
	//选择新证查询
	public List<Map<String, Object>> queryMatProdAuthorChooseList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatProdAuthorChooseList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	//选择新证
	public int updateMatProdAuthorToNewCert(Map<String, Object> map) throws DataAccessException;
	//取消新证
	public int updateMatProdAuthorToUnNewCert(Map<String, Object> map) throws DataAccessException;
}
