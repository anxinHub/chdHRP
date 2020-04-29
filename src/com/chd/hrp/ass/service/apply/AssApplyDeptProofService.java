package com.chd.hrp.ass.service.apply;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.apply.AssApplyDeptProof;
public interface AssApplyDeptProofService {
   /**
    * 论证主表查询方法
    * @param paramMap
    * @param paramRowBounds
    * @return
    * @throws DataAccessException
    */
	public AssApplyDeptProof queryApplyProof(Map<String, Object> paramMap)  throws DataAccessException;
	
	/**
	    * 论证主表查询方法
	    * @param paramMap
	    * @param paramRowBounds
	    * @return
	    * @throws DataAccessException
	    */
		public String queryApplyProofDetail(Map<String, Object> paramMap)  throws DataAccessException;
		
		/**
		 * 删除论证明细
		 * @param vomap
		 * @return
		 * @throws DataAccessException
		 */
		public String deleteApplyProofDetail(List<Map<String, Object>> vomap) throws DataAccessException;
		
		/**
		 * 删除论证表
		 * @param vmap
		 * @return
		 * @throws DataAccessException
		 */
		public String deleteApplyProof(Map<String, Object> vmap)throws DataAccessException;
		
		/**
		 * 更新或者新增论证信息
		 * @param vmap
		 * @return
		 * @throws DataAccessException
		 */
		public String saveorupdateApplyProof(Map<String, Object> vmap)throws DataAccessException;

		public String addApplyProof(Map<String, Object> entityMap)
				throws DataAccessException;
		/**
		 * 根据 购置申请信息 删除 论证数据
		 * @param listVo
		 * @return
		 * @throws DataAccessException
		 */
		public String deleteProofInfo(List<Map<String, Object>> listVo) throws DataAccessException;
}
