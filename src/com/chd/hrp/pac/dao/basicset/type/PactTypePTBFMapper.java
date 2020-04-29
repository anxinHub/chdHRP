package com.chd.hrp.pac.dao.basicset.type;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
public interface PactTypePTBFMapper extends SqlMapper {
	
	/**
	 * 查询功能
	 * 
	 */
  public List<Map<String ,Object>>queryPactPtbf(Map<String, Object> maoVo,RowBounds rowBounds) throws DataAccessException;
  
  
  /**
	 * 查询（不分页、根据ID查询）
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactPtbf(Map<String, Object> maoVo) throws DataAccessException;
	
/**
 * 查询根据ID查询
 * 
 * 
 */
	public Map<String, Object> queryPactPtbfId(Map<String, Object> maoVo) throws DataAccessException;
	
	
	/**
	 * @author lh0225
	 * 保存方法
	 * 
	 */
	public int savePacPtbfAction   (Map<String ,Object>mapVo) throws DataAccessException;
	
	
	/**
	 * 更新方法
	 * @author lh0225
	 */
	
	public int updatePacPtbfAction (Map<String ,Object>mapVo) throws DataAccessException;
	
	/**
	 * @author lh0225
	 * 删除方法
	 */
 
	public int deletePacPtbfAction(Map<String ,Object>mapVo) throws DataAccessException;
	
	/***
	 * 
	 * 获取按钮ID
	 * @author lh0225
	 * 
	 */
	public Long queryBBID(Map<String ,Object>mapVo) throws DataAccessException;
	
	/**
	 * 增加按钮数据
	 * @author lh0225
	 * 
	 */
	public int savebbidmethod(Map<String ,Object>mapVo) throws DataAccessException;
}
