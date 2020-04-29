/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatVenCertDetailMapper;
import com.chd.hrp.mat.entity.MatVenCertDetail;
import com.chd.hrp.mat.service.info.basic.MatVenCertDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * cert_state 1：在用 0：停用
 * @Table:
 * MAT_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matVenCertDetailService")
public class MatVenCertDetailServiceImpl implements MatVenCertDetailService {

	private static Logger logger = Logger.getLogger(MatVenCertDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matVenCertDetailMapper")
	private final MatVenCertDetailMapper matVenCertDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	@Override
	public String addMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		
		//获取对象cert_state 1：在用 0：停用
		
		/*DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fd.parse((fd.format(new Date())));
		Date  enddate =fd.parse(String.valueOf(entityMap.get("end_date")));;
		if(date.before(enddate)){
			entityMap.put("cert_state", 0);
		}else{
			entityMap.put("cert_state", 1);
		}*/

		MatVenCertDetail matVenCertDetail = queryMatVenCertDetailByCode(entityMap);

		if (matVenCertDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matVenCertDetailMapper.addMatVenCertDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatVenCertDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加cert_state 1：在用 0：停用 <BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatVenCertDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matVenCertDetailMapper.addBatchMatVenCertDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatVenCertDetail\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
		 * @throws ParseException 
	*/
	@Override
	public String updateMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		
		if(!entityMap.get("cert_code").equals(entityMap.get("cert_codeOld"))){
			MatVenCertDetail matVenCertDetail = queryMatVenCertDetailByCode(entityMap);

			if (matVenCertDetail != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}
		}
		/*DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fd.parse((fd.format(new Date())));
		Date  enddate =fd.parse(String.valueOf(entityMap.get("end_date")));;
		if(date.before(enddate)){
			entityMap.put("cert_state", 0);
		}else{
			entityMap.put("cert_state", 1);
		}*/
		try {
			
		  int state = matVenCertDetailMapper.updateMatVenCertDetailNew(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 updateMatVenCertDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新cert_state 1：在用 0：停用 <BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatVenCertDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matVenCertDetailMapper.updateBatchMatVenCertDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 updateBatchMatVenCertDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatVenCertDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matVenCertDetailMapper.deleteMatVenCertDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatVenCertDetail\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除cert_state 1：在用 0：停用 <BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatVenCertDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matVenCertDetailMapper.deleteBatchMatVenCertDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatVenCertDetail\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象cert_state 1：在用 0：停用

		MatVenCertDetail matVenCertDetail = queryMatVenCertDetailByCode(entityMap);

		if (matVenCertDetail != null) {

			int state = matVenCertDetailMapper.updateMatVenCertDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matVenCertDetailMapper.addMatVenCertDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatVenCertDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatVenCertDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatVenCertDetail> list = matVenCertDetailMapper.queryMatVenCertDetailSUP(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatVenCertDetail> list = matVenCertDetailMapper.queryMatVenCertDetailSUP(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 查询 指定供应商的证件明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatVenCertDetailCert(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatVenCertDetail> list = matVenCertDetailMapper.queryMatVenCertDetailCert(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatVenCertDetail> list = matVenCertDetailMapper.queryMatVenCertDetailCert(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * @Description 
	 * 获取对象cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatVenCertDetail queryMatVenCertDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matVenCertDetailMapper.queryMatVenCertDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatVenCertDetail
	 * @throws DataAccessException
	*/
	@Override
	public MatVenCertDetail queryMatVenCertDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matVenCertDetailMapper.queryMatVenCertDetailByUniqueness(entityMap);
	}
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public MatVenCertDetail queryMatVenCertDetailByID(Map<String, Object> mapVo) throws DataAccessException{
		return matVenCertDetailMapper.queryMatVenCertDetailByID(mapVo);
	}
	
	/**
	 * 审核/消审/驳回/取消驳回
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateVenCertDetailState(List<Map<String, Object>> listVo) throws DataAccessException {	
		try {
			matVenCertDetailMapper.updateVenCertDetailState(listVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败!\",\"state\":\"false\"}";

		}	 
	}
	/**
	 * 删除 证件附件 清空 文件路径
	 * @param mapVo
	 * @throws DataAccessException
	 */
	@Override
	public void updateVenCertFilePath(Map<String, Object> mapVo) throws DataAccessException{
		
		try {
			matVenCertDetailMapper.updateVenCertFilePath(mapVo);

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

		}	 
		
	}
	
}
