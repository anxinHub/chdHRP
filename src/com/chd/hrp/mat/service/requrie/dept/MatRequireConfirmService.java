/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.requrie.dept;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.mat.entity.MatRequireMain;
/**
 * 
 * @Description:
 * 科室购置计划审核
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatRequireConfirmService extends SqlService{
	/**
	 * 科室购置计划--审核页面查看明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryConfirmDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室购置计划--审核页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryConfirm(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室购置计划--审核
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAudit(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室购置计划--取消审核
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAuditCancle(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室购置计划--退回科室
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateReturn(List<Map<String, Object>> entityList) throws DataAccessException;
	
}
