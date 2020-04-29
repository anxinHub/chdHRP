package com.chd.hrp.sys.service.portal;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface SysPortalTitleUserService {
	/**
	 *  保存  门户栏目配置数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addSysPortalTitleUser(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量添加 门户栏目配置数据
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public String addBatchSysPortalTitleUser(List<Map<String,Object>> list) throws DataAccessException;
	
	/**
	 *  更新  门户栏目配置数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateSysPortalTitleUser(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public String deleteSysPortalTitleUser(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public String deleteBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 系统公告 保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addOrUpdateSysNotice(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 发布、取消发布 系统公告
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateSysNoticeState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询公告数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> querySysPortalNoticeByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据主键 查询公告数据是否存在
	 * @param mapVo
	 * @return
	 */
	public int querySysNoticeExist(Map<String, Object> mapVo);
}
