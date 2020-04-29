package com.chd.hrp.htc.dao.task.projectcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeMateRela;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcDeptChargeMateRelaMapper extends SqlMapper{
	
	public int addHtcDeptChargeMateRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptChargeMateRela> queryHtcDeptChargeMateRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeMateRela> queryHtcDeptChargeMateRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptChargeMateRela queryHtcDeptChargeMateRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcDeptChargeMateRela(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcDeptChargeMateRela(List<Map<String,Object>> entityList)throws DataAccessException;
    
	public int updateHtcDeptChargeMateRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptChargeMateRela>  queryHtcDeptChargeMateRelaCharge(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeMateRela> queryHtcDeptChargeMateRelaCharge(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HtcDeptChargeMateRela> queryHtcDeptChargeMateRelaMate(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeMateRela> queryHtcDeptChargeMateRelaMate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;


}
