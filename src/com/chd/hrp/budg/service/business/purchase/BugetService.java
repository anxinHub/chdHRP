package com.chd.hrp.budg.service.business.purchase;

import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlService;
/**
 * 资产采购预算
 * @author Administrator
 *
 */
public interface BugetService extends SqlService{
	/**
	 * 生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String copyBuget(Map<String, Object> mapVo) throws DataAccessException;

}
