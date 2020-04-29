/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrPlaint;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ADVERSE EVENT 投诉登记表
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrPlaintService {
    /**
     * 添加投诉登记
     * @param mapVo
     * @return
     */
	String addPlaint(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除投诉登记
     * @param listVo
     * @return
     */
	String deletePlaint(List<HrPlaint> listVo)throws DataAccessException;
	/**
	 * 查询投诉登记
	 * @param page
	 * @return
	 */
	String queryPlaint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询投诉登记
	 * @param mapVo
	 * @return
	 */
	String importPlaint(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询具体数据
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	HrPlaint queryByCode(HrPlaint hrNursingPromotion)throws DataAccessException;
	/**
	 * 提交、确认
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	String confirmPlaint(List<HrPlaint> list)throws DataAccessException;
	/**
	 * 撤回提交
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmPlaint(List<HrPlaint> list)throws DataAccessException;
	/**
 * 打印
 * @param page
 * @return
 * @throws DataAccessException
 */
List<Map<String,Object>> queryPlaintByPrint(Map<String, Object> page)throws DataAccessException;
}
