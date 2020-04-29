/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.requrie.dept;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatRequireDetail;
import com.chd.hrp.mat.entity.MatRequireMain;
/**
 * 
 * @Description:
 * 科室购置计划审核
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatRequireConfirmMapper extends SqlMapper{
	
	
	/**
	 * 科室购置计划--审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 科室购置计划--取消审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAuditCancle(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 科室购置计划--退回科室
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateReturn(List<Map<String, Object>> listVo) throws DataAccessException;
	
}
