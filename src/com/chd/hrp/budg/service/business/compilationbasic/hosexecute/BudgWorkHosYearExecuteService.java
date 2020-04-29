/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.hosexecute;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院业务执行数据
 * @Table:
 * BUDG_WORK_HOS_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosYearExecuteService extends SqlService {

/**
 * 判断指标编码是否存在
 * @param mapVo
 * @return
 * @throws DataAccessException
 */
public int queryIndexCode(Map<String, Object> mapVo)throws DataAccessException;

public String budgWorkHosYearExecuteImport(Map<String, Object> mapVo) throws DataAccessException;

public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo)throws DataAccessException;

}
