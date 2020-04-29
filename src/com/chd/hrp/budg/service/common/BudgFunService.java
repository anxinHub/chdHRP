/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;


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
 

public interface BudgFunService extends SqlService {
	
	/**
	 * 函数分类树加载
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFunTypeTree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 函数参数 及其部件信息 查询
	 * @param mapVo
	 * @return
	 */
	public String queryComTypePara(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 重新 加载 函数存储过程
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String initBudgFunProc(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 函数参数 部件类型 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgComType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 函数编码 查询其参数
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgFunParaByFunCode(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 函数参数下拉框 查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgFunParaByDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 存储 预算指标函数参数栈数据 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgIndexStack(List<Map<String, Object>> listVo) throws DataAccessException;


}
