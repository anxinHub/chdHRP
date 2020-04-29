package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrDissertation;

/**
 * 论文发表
 * @author Administrator
 *
 */
public interface HrDissertationService {
    /**
     * 增加论文发表
     * @param mapVo
     * @return
     */
	String addDissertation(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrDissertation queryByCodeDissertation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改论文发表
	 * @param mapVo
	 * @return
	 */
	String updateDissertation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除论文发表
	 * @param listVo
	 */
	String  deleteDissertation(List<HrDissertation> listVo)throws DataAccessException;
	/**
	 * 查询论文发表
	 * @param page
	 * @return
	 */
	String queryDissertation(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryDissertationTree(Map<String, Object> mapVo)throws DataAccessException;

}
