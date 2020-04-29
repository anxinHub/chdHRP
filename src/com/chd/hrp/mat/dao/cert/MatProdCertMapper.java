
package com.chd.hrp.mat.dao.cert;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
public interface MatProdCertMapper extends SqlMapper{
	//产品注册证列表查询
	public List<Map<String, Object>> queryMatProdCertList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatProdCertList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	//产品注册证查询
	public Map<String, Object> queryMatProdCertById(Map<String, Object> map) throws DataAccessException;
	//产品注册证保存
	public int addMatProdCert(Map<String, Object> map) throws DataAccessException;
	public int updateMatProdCert(Map<String, Object> map) throws DataAccessException;
	//更新老证信息
	public int updateMatProdCertToOld(Map<String, Object> map) throws DataAccessException;
	//复制老证材料信息
	public int addMatProdCertInvByCopyOld(Map<String, Object> map) throws DataAccessException;
	//产品注册证删除
	public int deleteMatProdCert(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//产品注册证修改认证标志
	public int updateMatProdCertAuthentState(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//产品注册证修改审核标志
	public int updateMatProdCertCheckState(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//产品注册证导入
	public int addMatProdCertByImp(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//校验是否含不为该状态的数据
	public Integer notExistsMatProdCertByState(@Param(value="map")Map<String, Object> map, @Param(value="state_col")String state_col, @Param(value="state_val")int state_val, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	

	//查询材料列表
	public List<Map<String, Object>> queryMatInvList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatInvList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	//查询关联材料
	public List<Map<String, Object>> queryMatProdCertInvList(Map<String, Object> map) throws DataAccessException;
	//保存关联材料
	public int deleteMatProdCertInv(Map<String, Object> map) throws DataAccessException;
	public int addMatProdCertInv(@Param(value="map")Map<String, Object> map, @Param(value="list")List<String> list) throws DataAccessException;
	//修改对应关系中证件编号字段
	public int updateMatProdCertInvForCertCode(Map<String, Object> map) throws DataAccessException;
	//选择新证查询
	public List<Map<String, Object>> queryMatProdCertChooseList(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatProdCertChooseList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	//选择新证
	public int updateMatProdCertToNewCert(Map<String, Object> map) throws DataAccessException;
	//取消新证
	public int updateMatProdCertToUnNewCert(Map<String, Object> map) throws DataAccessException;
}
