/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.base;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrProfessional;
/**
 * 
 * @Description:
 * 专业信息
 * @Table:
 * HR_PROFESSIONAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrProfessionalMapper extends SqlMapper{
	/**
	 * 删除专业信息
	 */
	void deleteHrProfessional(List<HrProfessional> entityList);
    /**
     * 添加查询
     * @param entityMap
     * @return
     */
	List<HrProfessional> queryProfessionalById(Map<String, Object> entityMap);
	
}
