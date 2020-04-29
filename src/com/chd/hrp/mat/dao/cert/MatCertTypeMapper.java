
package com.chd.hrp.mat.dao.cert;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
public interface MatCertTypeMapper extends SqlMapper{
	//证件类型查询
	public List<Map<String, Object>> queryMatCertTypeList(Map<String, Object> map) throws DataAccessException;
	//证件类型保存
	public int addMatCertType(Map<String, Object> map) throws DataAccessException;
	public int updateMatCertType(Map<String, Object> map) throws DataAccessException;
	//证件类型删除
	public int deleteMatCertType(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//证件类型修改停用标
	public int updateMatCertTypeIsStop(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//导入时使用用于判断类别是否存在
	public List<Map<String, Object>> queryMatCertTypeKindListForImport() throws DataAccessException;
	//证件类型导入
	public int addMatCertTypeByImp(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
}
