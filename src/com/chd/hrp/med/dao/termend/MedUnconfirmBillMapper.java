/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.med.dao.termend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:  期末处理-未确认单据
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public interface MedUnconfirmBillMapper extends SqlMapper{
	//查询
	public Map<String, Object> query(List<Map<String, Object>> entityMap)throws DataAccessException;
    //常备未确认入库结转下月
	public int updateUnconfirmBillByMedIn(Map<String, Object> entityMap)throws DataAccessException;
	//常备未确认出库结转下月
	public int updateUnconfirmBillByMedOut(Map<String, Object> entityMap)throws DataAccessException;
	//常备未确认调拨结转下月
	public int updateUnconfirmBillByMedTran(Map<String, Object> entityMap)throws DataAccessException;
	//代销未确认入库结转下月
	public int updateUnconfirmBillByAffiIn(Map<String, Object> entityMap)throws DataAccessException;
	//代销未确认出库结转下月
	public int updateUnconfirmBillByAffiOut(Map<String, Object> entityMap)throws DataAccessException;
	//代销未确认调拨库结转下月
	public int updateUnconfirmBillByAffiTran(Map<String, Object> entityMap)throws DataAccessException;
	
}
