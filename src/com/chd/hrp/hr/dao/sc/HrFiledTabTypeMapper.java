/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sc;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrFiledTabType;
/**
 * 
 * @Description:
 * 代码表分类 如：国家标准、地方标准、医院标准
 * @Table:
 * HR_FileD_TAB_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrFiledTabTypeMapper extends SqlMapper{

	int deleteHrFiledTabType(List<HrFiledTabType> listVo);

	int queryCount(List<HrFiledTabType> listVo);

	HrFiledTabType queryFTabTypeByName(Map<String, Object> entityMap);
	
}
