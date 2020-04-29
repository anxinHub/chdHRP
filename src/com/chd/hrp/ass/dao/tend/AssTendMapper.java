package com.chd.hrp.ass.dao.tend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tend.AssTendDetail;
import com.chd.hrp.ass.entity.tend.AssTendFile;
import com.chd.hrp.ass.entity.tend.AssTendMain;
/**
 * 招标管理
 * @author cyw
 *
 */
public interface AssTendMapper extends SqlMapper {
  /**
   * 招标方式
   * @param mapvo
   * @return
   * @throws DataAccessException
   */
	public List<Map<String, Object>> queryTendMethod(Map<String, Object> mapvo) throws DataAccessException;
	/**
	 * 查询主方法
	 * @param mapVo
	 * @param bounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssTendMain> queryAsstendMain(Map<String, Object> mapVo,RowBounds bounds)throws DataAccessException;
	
	/**
	 * 查询主方法
	 * @param mapVo
	 * @param bounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssTendMain> queryAsstendMain(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 判断是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public AssTendMain queryAsstendMainExit(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 返回主表信息(明细连接页面使用)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public AssTendMain queryAsstendMainPage(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 删除主表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteTend(List<Map<String, Object>> mapVo)throws DataAccessException;
	
	/**
	 * 删除明细
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteTendDetail(List<Map<String, Object>> mapVo) throws DataAccessException;
	
	/**
	 * 删除明细
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteTendDetailm(List<Map<String, Object>> mapVo) throws DataAccessException;
	/**
	 * 查询明细方法
	 * @param mapVo
	 * @param bounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssTendDetail> queryAsstendDetail(Map<String, Object> mapVo,RowBounds bounds)throws DataAccessException;
	
	/**
	 * 查询明细方法
	 * @param mapVo
	 * @param bounds
	 * @return
	 * @throws DataAccessException
	 */
	public AssTendDetail queryAsstendDetail(Map<String, Object> mapVo)throws DataAccessException;
	
	 /**
	  * 新增方法
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int savetendInfomation(Map<String, Object> mapVo) throws DataAccessException ;
	 
	 /**
	  * 返回当前主键
	  * @return
	  * @throws DataAccessException
	  */
	 public Long queryAssTendSequence()throws DataAccessException ;
	 
	 /**
	  * 插入明细表数据
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int saveAssTendDetail(Map<String, Object> mapVo)throws DataAccessException ;
	 
	 /**
	  * 插入明细表数据
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int saveTendApplyMap(Map<String, Object> mapVo)throws DataAccessException ;
	 
	 /**
	  * 更新主表
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int updateTendMain(Map<String, Object> mapVo)throws DataAccessException ;
	
	 /**
	  * 删除关联表
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int deletemap(List<Map<String, Object>> mapVo)throws DataAccessException ;
	 
	 /**
	  * 设备招标单更新方法
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
	 public int updateAssTendMain (Map<String, Object> mapVo) throws DataAccessException ;
	 
	 /**
	  * 明细添加方法
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
     public int addAssTendDetail(Map<String, Object> mapVo) throws DataAccessException ;
     
	 /**
	  * 明细更新方法
	  * @param mapVo
	  * @return
	  * @throws DataAccessException
	  */
     public int updateAssTendDetail(Map<String, Object> mapVo) throws DataAccessException ;
     /**
      * 资产
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
     public List<Map<String, Object> > queryAssDict(Map<String, Object> mapVo) throws DataAccessException ;
     
     /**
      * 厂家
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
     public List<Map<String, Object> > queryAssFac(Map<String, Object> mapVo) throws DataAccessException ;
     
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
     
     /**
      * 主表新增方法
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
     public int addAssTendMain(Map<String, Object> mapVo)throws DataAccessException;
      /**
       * 提交
       * @param mapVo
       * @return
       * @throws DataAccessException
       */
 	public int updateSubmit(Map<String, Object> mapVo)
			throws DataAccessException;
 	/**
 	 * 更新主表金额
 	 * @param mapVo
 	 * @return
 	 * @throws DataAccessException
 	 */
 	public int updateBidValue(Map<String, Object> mapVo)
			throws DataAccessException;
 	
}
