package com.chd.hrp.ass.service.tend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.hrp.ass.entity.tend.AssTendDetail;
import com.chd.hrp.ass.entity.tend.AssTendFile;
import com.chd.hrp.ass.entity.tend.AssTendMain;


/**
 * 招标管理页面接口
 * @author cyw
 *
 */
public interface AssTendService {

	/**
	 * 招标方式下拉框
	 * @param mapvo
	 * @return
	 * @throws DataAccessException
	 */
 public String queryTendMethod (Map<String, Object> mapvo) throws DataAccessException ;
 
 /**
  * 主查询方法
  * @param mapvo
  * @return
  * @throws DataAccessException
  */
 public String queryAsstendMain(Map<String, Object> mapvo) throws DataAccessException ;
 /**
  * 明细表查询
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String queryAsstendDetail(Map<String, Object> mapVo)throws DataAccessException;

 /**
  * 明细表查询
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public AssTendDetail queryAsstendDetaill(Map<String, Object> mapVo)throws DataAccessException;

 
 /**
  * 删除
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String deleteTend(List<Map<String, Object>> mapVo) throws DataAccessException ;
 
 /**
  * 删除明细
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String deleteTendDetail(List<Map<String, Object>> mapVo) throws DataAccessException ;
 
 /**
  * 新增方法
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String savetendInfomation(Map<String, Object> mapVo) throws DataAccessException ;
 
 /**
  * 明细连接页面主表信息查询方法
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public AssTendMain queryassTendMain (Map<String, Object> mapVo) throws DataAccessException ;
 
 /**
  * 设备招标单更新方法
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String updateAssTendMain (Map<String, Object> mapVo) throws DataAccessException ;
 
 /**
  * 新增明细信息
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String addAssTendDetail(Map<String, Object> mapVo) throws DataAccessException ;
 
 /**
  * 更新明细信息
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String updateAssTendDetail(Map<String, Object> mapVo) throws DataAccessException ;
 
 /**
  * 资产下拉
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String queryAssDict(Map<String, Object> mapVo) throws DataAccessException ;

 /**
  * 厂家下拉
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String queryAssFac(Map<String, Object> mapVo) throws DataAccessException ;
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
 /**
  * 主表新增方法
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String addAssTendMain(Map<String, Object> mapVo)throws DataAccessException;
 
 /**
  * 提交
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
 public String updateSubmit(Map<String, Object> mapVo)throws DataAccessException;
 
 public String queryassTendcheckAll(Map<String, Object> mapVo)throws DataAccessException;
 
}
