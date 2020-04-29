/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.base;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedNoManage;
/**
 * 
 * @Description:
 * 08199 单据号管理表
 * @Table:
 * MED_NO_MANAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedNoManageMapper extends SqlMapper{
	/**
	 * 当前年月单据号管理表中是否存在数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExists(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获取最大的流水号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMaxCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 最大的流水号加一
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMaxNo(Map<String, Object> entityMap) throws DataAccessException;
	
}
