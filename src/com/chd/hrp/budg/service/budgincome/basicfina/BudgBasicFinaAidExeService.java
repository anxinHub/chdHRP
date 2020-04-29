/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.basicfina;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 财政基本补助收入预算执行 
 * @Table:
 * BUDG_BASIC_FINA_AID_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgBasicFinaAidExeService extends SqlService {
	/**
	 * 判断收入预算科目是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCodeExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 收入预算科目下拉框（添加时用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIncomeSubj(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 保存数据（包含 添加、修改 ）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveBudgBasicFinaAidExe(List<Map<String, Object>> listVo) throws DataAccessException;

}
