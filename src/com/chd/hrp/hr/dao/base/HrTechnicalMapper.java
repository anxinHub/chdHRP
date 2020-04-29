/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrTechnical;
/**
 * 
 * @Description:
 * 技术等级
 * @Table:
 * HR_TECHNICAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTechnicalMapper extends SqlMapper{
    /**
     * 删除技术等级
     * @param entityList
     */
	void deleteHrTechnical(List<HrTechnical> entityList);
	/**
	 * 导入时保存数据
	 */
	public int addByImport(List<?> entityMap)throws DataAccessException;
	List<HrTechnical> queryByCodeName(Map<String, Object> entityMap);
	
}
