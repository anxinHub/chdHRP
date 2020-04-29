/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 其他流水码
 * @Table:
 * MAT_NO_OTHER
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedNoOtherMapper extends SqlMapper{
	/**
	 * @Description 
	 * 获取当前个体码 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedNoOther(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 插入个体码
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertMedNoOther(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 更新当前个体码
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedNoOther(Map<String,Object> entityMap) throws DataAccessException;
}
