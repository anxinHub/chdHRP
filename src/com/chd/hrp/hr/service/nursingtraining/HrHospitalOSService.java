package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrHospitalOS;

/**
 * 院外学习记录
 * @author Administrator
 *
 */
public interface HrHospitalOSService {
    /**
     * 增加院外学习记录
     * @param mapVo
     * @return
     */
	String addHospitalOS(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrHospitalOS queryByCodeHospitalOS(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改院外学习记录
	 * @param mapVo
	 * @return
	 */
	String updateHospitalOS(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除院外学习记录
	 * @param listVo
	 */
	String  deleteHospitalOS(List<HrHospitalOS> listVo)throws DataAccessException;
	/**
	 * 查询院外学习记录
	 * @param page
	 * @return
	 */
	String queryHospitalOS(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryHospitalOSTree(Map<String, Object> mapVo)throws DataAccessException;

}
