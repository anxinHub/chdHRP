/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.relaset;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedStoreMatch;
/**
 * 
 * @Description:
 * 08113 药品仓库配套表
 * @Table:
 * MED_STORE_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStoreMatchService extends SqlService{

	/**
	 * @Description 
	 * 药品仓库配套表<BR> 查询 材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	public String querySmmDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 药品仓库配套表<BR> 更新配套表明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMsmDetail(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 系统  自动生成 仓库配套表编码   （规则   仓库别名+“—”年份（后两位）+月份+流水号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String getMedNextNo(Map<String, Object> entityMap) throws DataAccessException;
	
}
