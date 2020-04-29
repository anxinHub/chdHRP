/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrFiledData;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
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
 

public interface HrFiledDataMapper extends SqlMapper{

	HrFiledData queryByCodeName(Map<String, Object> saveMap);
	HrFiledData queryByCodePrint(Map<String, Object> saveMap);
    /**
     * 修改是否末级为否
     * @param saveList
     */
	void updateBatchLast(List<Map<String, Object>> saveList);
	
	List<HrFiledData> queryHrFiledDataExport(Map<String, Object> entityMap) throws DataAccessException;
	
}
