package com.chd.hrp.prm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmDeptFormulaMethod;


//计算公式
public interface PrmDeptFormuLaMethodService {

	  /**
     * 查询函数
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public PrmDeptFormulaMethod queryPrmDeptFormuLaMethod(Map<String, Object> mapVo)throws DataAccessException ;
     /**
      * 保存计算公式
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	public String savePrmDeptFormula(Map<String, Object> mapVo)throws DataAccessException ;
	
	/**
	 * @Description 
	 * 批量删除 指示灯<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchDeptFormuLaMethod(List<Map<String, Object>> listVo)throws DataAccessException ;
}
