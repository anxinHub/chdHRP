/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.base;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatNoManage;
/**
 * 
 * @Description:
 * 04199 单据号管理表
 * @Table:
 * MAT_NO_MANAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatNoManageMapper extends SqlMapper{
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

	/**
	 * @param <T>
	 * @Description 
	 * 获取仓库Alias用于生成单号<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryStoreAliasById(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 获取业务类型type_flag用于生成单号<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBusTypeFlagByCode(Map<String,Object> entityMap)throws DataAccessException;
}
