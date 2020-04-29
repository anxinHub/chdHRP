/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.teachingmanagement;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.teachingmanagement.HrWithTeach;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_WITH_TEACH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrWithTeachService {
	/**
	 * 查询轮转科室信息  下拉框
	 * @return
	 * @throws DataAccessException
	 */
	String queryDeptComboBox(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询  双击工号出现下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryComboBox(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
    String deleteHrWithTeach(List<HrWithTeach> entityList)throws DataAccessException;
    /**
   	 * 导入
   	 * @param mapVo
   	 * @return
   	 * @throws DataAccessException
   	 */
   	String importWithTeach(Map<String, Object> mapVo)throws DataAccessException;
   	/**
   	 * 打印
   	 * 
   	 * @param page
   	 * @return
   	 * @throws DataAccessException
   	 */
   	List<Map<String, Object>> queryWithTeachByPrint(Map<String, Object> page)throws DataAccessException;
   	/**
   	 * 保存
   	 * @param mapVo
   	 * @return
   	 * @throws DataAccessException
   	 */
	String addOrUpdate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String query(Map<String, Object> page)throws DataAccessException;
}
