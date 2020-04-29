/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedData;
/**
 * 
 * @Description:
 * 代码项数据表
 * @Table:
 * HR_FIIED_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrFiiedDataMapper extends SqlMapper{

	HrFiiedData queryByCodeName(Map<String, Object> saveMap);
    /**
     * 修改是否末级为否
     * @param saveList
     */
	void updateBatchLast(List<Map<String, Object>> saveList);
	
}
