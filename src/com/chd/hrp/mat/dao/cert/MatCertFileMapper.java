/**
* @Copyright: Copyright (c) 2017-9-13 
* @Company: 杭州亦童科技有限公司
*/
package com.chd.hrp.mat.dao.cert;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatCertFileMapper extends SqlMapper{
	
	/**
	 * @Description 列表查询
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String, Object>> queryMatCertFileList(@Param(value="table_code") String table_code, @Param(value="id_col") String id_col, @Param(value="id_val") String id_val)throws DataAccessException;
	
	/**
	 * @Description 查询需删除的文件
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String, Object>> queryMatCertFileDeleteList(@Param(value="table_code") String table_code, @Param(value="id_col") String id_col, @Param(value="id_list") List<String> id_list)throws DataAccessException;
	
	/**
	 * @Description 批量添加
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	public int addMatCertFileBatch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String,Object>> list)throws DataAccessException;
	
	/**
	 * @Description 按路径删除文件记录
	 * @param Map<String,Object>(必须包含ids键)
	 * @return String
	 * @throws Exception
	*/
	public int deleteMatCertFileByPath(@Param(value="table_code") String table_code, @Param(value="id_col") String id_col, @Param(value="id_val") String id_val, @Param(value="list") List<Map<String, String>> list)throws DataAccessException;
	
	/**
	 * @Description 批量删除
	 * @param 
	 * @return String
	 * @throws Exception
	*/
	public int deleteMatCertFile(@Param(value="table_code") String table_code, @Param(value="id_col") String id_col, @Param(value="id_list") List<String> id_list)throws DataAccessException;
}
