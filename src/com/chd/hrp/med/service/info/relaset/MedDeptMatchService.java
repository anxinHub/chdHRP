﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.relaset;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedDeptMatch;
/**
 * 
 * @Description:
 * 08114 药品科室配套表
 * @Table:
 * MED_DEPT_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedDeptMatchService extends SqlService{
	
	/**
	 * @Description 
	 * 药品科室配套表<BR>查询 配套表明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMdmDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 生成药品科室配套表编码
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String getMedNextNo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除明细数据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBatchDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	public String queryDeptInvData(Map<String, Object> mapVo) throws DataAccessException;
}