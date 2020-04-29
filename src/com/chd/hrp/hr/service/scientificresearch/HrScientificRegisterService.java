package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrScientificRegister;


/**
 * 专利登记
 * @author Administrator
 *
 */
public interface HrScientificRegisterService {
    /**
     * 增加专利登记
     * @param mapVo
     * @return
     */
	String addScientificRegister(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrScientificRegister queryByCodeScientificRegister(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改专利登记
	 * @param mapVo
	 * @return
	 */
	String updateScientificRegister(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除专利登记
	 * @param listVo
	 */
	String  deleteScientificRegister(List<HrScientificRegister> listVo)throws DataAccessException;
	/**
	 * 查询专利登记
	 * @param page
	 * @return
	 */
	String queryScientificRegister(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryScientificRegisterTree(Map<String, Object> mapVo)throws DataAccessException;

}
