/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrCaltrans;

/**
 * 
 * @Description:
 * 计算事务
 * @Colle:
 * HR_CALTRANS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrCaltransService {
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHrCaltrans(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrCaltrans(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrCaltrans(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrCaltrans(List<HrCaltrans> listVo) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrCaltrans(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrCaltrans queryHrCaltransByCode(Map<String, Object> entityMap) throws DataAccessException;

	String startFuncHrCaltrans(List<Map> listVo)throws DataAccessException;

	String stopFuncHrCaltrans(List<Map> listVo)throws DataAccessException;
    /**
     * 查询树形
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryCaltransFunTypeTree(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询函数
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryHrFun(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询动态表单
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryPrmFunByCode(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询部件
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryHrFunParaByDict(Map<String, Object> mapVo)throws DataAccessException;

}
