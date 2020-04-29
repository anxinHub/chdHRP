package com.chd.hrp.ass.dao.conference;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tend.AssTendFile;

public interface AssConferenceMapper extends SqlMapper {
   
	/**
	 * 查询
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssConference(Map<String, Object> maoVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询（不分页、根据ID查询）
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssConference(Map<String, Object> maoVo) throws DataAccessException;
	
	/**
	 * 查询（不分页、根据ID查询）
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssConferenceByID(Map<String, Object> maoVo) throws DataAccessException;
	
	
	/**
	 * 保存
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public int saveAssConference(Map<String, Object> maoVo) throws DataAccessException;
	
	/**
	 * 更新
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAssConference(Map<String, Object> maoVo) throws DataAccessException;
	
	/**
	 * 删除
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteAssConference(Map<String, Object> maoVo) throws DataAccessException;
	
	/**
	 * 返回主键
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssConferenceSequence()throws DataAccessException ;
	
	/**
	 * 保存参会人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int saveConferencePersonnel (List<Map<String, Object>> mapVo)throws DataAccessException;
	/**
	 * 保存缺席人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int saveConferencePersonneln (List<Map<String, Object>> mapVo)throws DataAccessException;
	 
	/**
	 * 删除人员信息（根据会议记录ID）
	 * @param maoVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteConferencePersonnel(Map<String, Object> maoVo) throws DataAccessException;
	/**
	 * 查询出席人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querypersonelid(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询所有缺席人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querypersonelidn(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除人员信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	
	public int deleteConferencePerson(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 保存其他参会人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int saveOtherPersonel(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询其他参会人
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryOtherPersonel(Map<String, Object> mapVo)throws DataAccessException;
	
	  /**
     * 上传文件回写
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
    public int addAssTendFile(Map<String, Object> mapVo)throws DataAccessException ;
    /**
     * 查询文件列表
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
    public List<AssTendFile> queryAssTendFile(Map<String, Object> mapVo)
			throws DataAccessException;
    
    /**
     * 查询文件列表
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
    public List<AssTendFile> queryAssTendFile(Map<String, Object> mapVo,RowBounds rowBounds)
			throws DataAccessException;
    /**
     * 删除文件
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
    public int deleteTendFile(Map<String, Object> mapVo)
 			throws DataAccessException;
}
