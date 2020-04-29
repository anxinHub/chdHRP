package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrSCIImpactFactor;


/**
 * SCI论文影响因子
 * @author Administrator
 *
 */
public interface HrSCIImpactFactorService {
    /**
     * 增加SCI论文影响因子
     * @param mapVo
     * @return
     */
	String addSCIImpactFactor(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrSCIImpactFactor queryByCodeSCIImpactFactor(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改SCI论文影响因子
	 * @param mapVo
	 * @return
	 */
	String updateSCIImpactFactor(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除SCI论文影响因子
	 * @param listVo
	 */
	String  deleteSCIImpactFactor(List<HrSCIImpactFactor> listVo)throws DataAccessException;
	/**
	 * 查询SCI论文影响因子
	 * @param page
	 * @return
	 */
	String querySCIImpactFactor(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String querySCIImpactFactorTree(Map<String, Object> mapVo)throws DataAccessException;

}
