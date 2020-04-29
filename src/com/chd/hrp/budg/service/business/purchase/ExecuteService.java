package com.chd.hrp.budg.service.business.purchase;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 资产采购预算执行
 * @author Administrator
 *
 */
public interface ExecuteService extends SqlService{
	/**
	 * 查询资产来源下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String querySourceName(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 单条校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	

}
