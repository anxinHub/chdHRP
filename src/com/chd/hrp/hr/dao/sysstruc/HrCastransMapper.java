/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrCastrans;
/**
 * 
 * @Description:
 * 级联事务表
 * @Table:
 * HR_CASTRANS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrCastransMapper extends SqlMapper{

	int deleteBatchHrCastrans(List<HrCastrans> entityList) throws DataAccessException;
	
}
