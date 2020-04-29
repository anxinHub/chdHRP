/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.autovouch.acccoodeptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 基本数字表<BR>
* @Author: XuXin
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCooProjDetailMapper extends SqlMapper{
	
	public void deleteAccCooProjDetailBatch(Map<String, Object> mapVo) throws DataAccessException;
	
	public void addAccCooProjDetail(List<Map<String, Object>> detailList) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccCooProjDetail(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccCooProjObj(Map<String, Object> mapVo) throws DataAccessException;

	public int deleteAccCooProjDetail(List<Map<String, Object>> list)throws DataAccessException;
	
	public int insertAccProjDetailByImport(List<Map<String, Object>> list)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccCooProjDetailPrint(Map<String, Object> mapVo);

}
