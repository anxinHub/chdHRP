package com.chd.hrp.ass.service.tendnotify;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * 中标通知书接口
 * @author Administrator
 *
 */
public interface AssTendNotifyService {
	/**
	 * 主查询方法
	 * 
	 * @param mapvo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssTendNotifyMain(Map<String, Object> mapvo) throws DataAccessException;
	
	/**
	 * 添加文件管理表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addAssTendFile(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 更新招标主表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAssTendMain(Map<String,Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询招标文件
	 * @param mapvo
	 * @return
	 * @throws DataAccessException
	 */
	 public String queryAssTendFile(Map<String, Object> mapvo) throws DataAccessException ;
	 
	 /**
	  * 删除
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int deleteTendFile(Map<String, Object> mapVo) throws DataAccessException ;

}
