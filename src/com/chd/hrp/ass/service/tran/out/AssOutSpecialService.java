/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.tran.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.tran.out.AssOutDetailSpecial;
/**
 * 
 * @Description:
 * 050902 资产领用表(专用设备)
 * @Table:
 * ASS_OUT_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssOutSpecialService extends SqlService {
	public String updateConfirmOutSpecial(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;
	
	public List<AssOutDetailSpecial> queryByOutNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assOutSpecialByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
	
	public String importAssInMainByOut(Map<String, Object> entityMap) throws DataAccessException;

	public String offsetOutSpecial(List<Map<String, Object>> listVo) throws DataAccessException;

	
	
}
