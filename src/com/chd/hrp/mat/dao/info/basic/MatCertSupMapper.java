/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 

 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatCertSupMapper extends SqlMapper{
	
	public int addBatchSup(List<?> entityMap)throws DataAccessException;
	public int deleteSup(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
    public List<?> querySup(Map<String,Object> entityMap) throws DataAccessException;
	public List<?> querySup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
