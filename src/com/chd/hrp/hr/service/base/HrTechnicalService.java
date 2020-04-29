package com.chd.hrp.hr.service.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.base.HrTechnical;
/**
 * 
 * @Description:
 * 技术等级
 * @Table:
 * HR_TECHNICAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTechnicalService {
    /**
     * 删除技术等级
     * @param listVo
     * @return
     */
	String deleteHrTechnical(List<HrTechnical> listVo)throws DataAccessException;
    /**
     * 查询左侧菜单
     * @param mapVo
     * @return
     */
	String queryTechnicalLevelTree(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 增加技术等级
	 * @param mapVo
	 * @return
	 */
	String add(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 修改查询数据
	 * @param mapVo
	 * @return
	 */
	HrTechnical queryByCode(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 修改技术等级
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String update(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询技术等级
	 * @param page
	 * @return
	 */
	String query(Map<String, Object> page)throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importData(Map<String, Object> entityMap) throws DataAccessException;

}
