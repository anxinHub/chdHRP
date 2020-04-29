package com.chd.hrp.sup.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface SupBaseService {
	//材料字典列表（有材料库存）
		public String queryMatInvList(Map<String,Object> entityMap) throws DataAccessException;
		
		public String querySupDict(Map<String,Object> entityMap) throws DataAccessException;
		public List<?> querySupDictList(Map<String,Object> entityMap) throws DataAccessException;
		
		/**
		 * @Description 判断批号有效日期是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		public String queryMatInvBatchInva(Map<String,Object> entityMap) throws DataAccessException;

		/**
		 * @Description 判断批号灭菌日期是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		public String queryMatInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException;
		/**
		 * @Description 判断批号单价是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		public String queryMatInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
}
