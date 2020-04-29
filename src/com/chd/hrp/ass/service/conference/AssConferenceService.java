package com.chd.hrp.ass.service.conference;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.tend.AssTendFile;

public interface AssConferenceService {
   
	/**
	 * 查询方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssConference(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssConferenceByID(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveAssConference(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 更新方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAssConference(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 删除方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteConference(Map<String, Object> mapVo)throws DataAccessException;
	
   /**
    * 删除参会人员
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	public String deleteConferencePerson(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	  * 添加附件表信息
	  * @return
	  * @throws DataAccessException
	  */
	 public String addAssTendFile(Map<String, Object> mapVo)throws DataAccessException ;
	 
	 /**
	  * 查看上传文件是否已经存在
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public List<AssTendFile> queryAssTendFileExit(Map<String, Object> mapVo) throws DataAccessException;
	 
	 /**
	  * 文件查询
	  * @param mapVo
	  * @param model
	  * @return
	  */
	 public String queryAssTendFile( Map<String, Object> mapVo)throws DataAccessException;
	 /**
	  * 删除文件
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public String deleteTendFile( Map<String, Object> mapVo)throws DataAccessException;
	
}
