package com.chd.hrp.ass.dao.apply;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptProof;
import com.chd.hrp.ass.entity.apply.AssApplyDeptProofDetail;

public interface AssApplyDeptProofMapper extends SqlMapper {

	/**
	 * 查询方法
	 * @param paramMap
	 * @param paramRowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public  AssApplyDeptProof queryApplyProof(Map<String, Object> paramMap) throws DataAccessException;
		
		/**
		 * 查询明细方法
		 * @param paramMap
		 * @param paramRowBounds
		 * @return
		 * @throws DataAccessException
		 */
	 public  List<AssApplyDeptProofDetail> queryApplyProofDetail(Map<String, Object> paramMap,RowBounds rowBounds) throws DataAccessException;
	 
	 /**
	  * 删除明细
	  * @param vomap
	  * @return
	  * @throws DataAccessException
	  */
	 public int deleteApplyProofDetail(List<Map<String, Object>> vomap)throws DataAccessException;
	 /**
	  * 删除主表
	  * @param vmap
	  * @return
	  * @throws DataAccessException
	  */
	 public int deleteApplyProof(Map<String, Object> vmap)throws DataAccessException;
	 
	 /**
	  * 根据主表id删除
	  * @param vmap
	  * @return
	  * @throws DataAccessException
	  */
	 public int deleteApplyProofDetailm(Map<String, Object> vmap)throws DataAccessException;
	 
	 /**
	  * 判断主表是否存在
	  * @param paramMap
	  * @return
	  * @throws DataAccessException
	  */
	 public List<AssApplyDeptProof> queryApplyDeptProofExists (Map<String, Object> paramMap) throws DataAccessException;
	 /**
	  * 判断明细数据是否存在
	  * @param paramMap
	  * @return
	  * @throws DataAccessException
	  */
	 public List<AssApplyDeptProofDetail> queryApplyDeptProofDetailExists(Map<String, Object> paramMap) throws DataAccessException;
	 
	 /**
	  * 更新主表信息
	  * @param paramMap
	  * @return
	  * @throws DataAccessException
	  */
	 public int updateApplyDeptProof(Map<String, Object> paramMap) throws DataAccessException;
	 
	 /**
	  * 新增主表信息
	  * @param paramMap
	  * @return
	  * @throws DataAccessException
	  */
	 public int addApplyDeptProof(Map<String, Object> paramMap) throws DataAccessException;
	 
	 /**
	  * 返回主表主键ID
	  * @return
	  * @throws DataAccessException
	  */
	 public Long queryApplyDeptProofSequence()throws DataAccessException;
	 
	 /**
	  * 更新明细表信息
	  * @param paramMap
	  * @return
	  * @throws DataAccessException
	  */
	 public int updateApplyDeptProofDetail(Map<String, Object> paramMap) throws DataAccessException;
	 
	 /**
	  * 添加明细信息
	  * @param paramMap
	  * @return
	  * @throws DataAccessException
	  */
	 public int addApplyDeptProofDetail(Map<String, Object> paramMap) throws DataAccessException;
	
	 /**
	  * 查询 id
	  * @return
	  * @throws DataAccessException
	  */
	public Long queryAssApplyProofSequence() throws DataAccessException;
	
	/**
	 * 查询明细方法  不分页
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryApplyProofDetail(Map<String, Object> item)throws DataAccessException;
	/**
	 * 根据 购置申请信息 查询 论证数据信息
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryApplyProofInfo(List<Map<String, Object>> listVo) throws DataAccessException;

	public int deleteBatchApplyProofDetailByProofId(List<Map<String, Object>> proofList) throws DataAccessException ;

	public int deleteBatchApplyProofById(List<Map<String, Object>> proofList) throws DataAccessException ;
}
