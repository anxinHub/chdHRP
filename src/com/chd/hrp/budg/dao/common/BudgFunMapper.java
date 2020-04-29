/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgFun;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.prm.entity.PrmFun;
/**
 * 
 * @Description:
 * 预算相关函数表
 * @Table:
 * BUDG_FUN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgFunMapper extends SqlMapper{
	
	/**
	 * 查询 函数编码 是否重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryFunDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询函数名称是否别占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException ;
	/**
	 * 查询 函数 存储过程名称 是否重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryFunExistByPrcName(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 查询函数部件
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryComTypePara(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 所有sql 不为空的 数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<BudgFun> queryBudgFunByFile(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 函数参数 部件类型 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgComType(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据 函数编码 查询其参数 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgFunParaByFunCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 函数编码 查询其参数 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<BudgFunPara> queryBudgFunParaByFunCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
}
