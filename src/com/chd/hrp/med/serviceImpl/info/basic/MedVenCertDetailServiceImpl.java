/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

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
import com.chd.hrp.med.dao.info.basic.MedVenCertDetailMapper;
import com.chd.hrp.med.entity.MedVenCertDetail;
import com.chd.hrp.med.service.info.basic.MedVenCertDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * cert_state 1：在用 0：停用
 * @Table:
 * MED_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medVenCertDetailService")
public class MedVenCertDetailServiceImpl implements MedVenCertDetailService {

	private static Logger logger = Logger.getLogger(MedVenCertDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medVenCertDetailMapper")
	private final MedVenCertDetailMapper medVenCertDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	@Override
	public String addMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		
		//获取对象cert_state 1：在用 0：停用
		
		/*DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fd.parse((fd.format(new Date())));
		Date  enddate =fd.parse(String.valueOf(entityMap.get("end_date")));;
		if(date.before(enddate)){
			entityMap.put("cert_state", 0);
		}else{
			entityMap.put("cert_state", 1);
		}*/

		MedVenCertDetail medVenCertDetail = queryMedVenCertDetailByCode(entityMap);

		if (medVenCertDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medVenCertDetailMapper.addMedVenCertDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedVenCertDetail\"}";

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
	public String addBatchMedVenCertDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medVenCertDetailMapper.addBatchMedVenCertDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedVenCertDetail\"}";

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
	public String updateMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		
		if(!entityMap.get("cert_code").equals(entityMap.get("cert_codeOld"))){
			MedVenCertDetail medVenCertDetail = queryMedVenCertDetailByCode(entityMap);

			if (medVenCertDetail != null) {

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
			
		  int state = medVenCertDetailMapper.updateMedVenCertDetailNew(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 updateMedVenCertDetail\"}";

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
	public String updateBatchMedVenCertDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medVenCertDetailMapper.updateBatchMedVenCertDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 updateBatchMedVenCertDetail\"}";

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
    public String deleteMedVenCertDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medVenCertDetailMapper.deleteMedVenCertDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedVenCertDetail\"}";

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
	public String deleteBatchMedVenCertDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medVenCertDetailMapper.deleteBatchMedVenCertDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedVenCertDetail\"}";

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
	public String addOrUpdateMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象cert_state 1：在用 0：停用

		MedVenCertDetail medVenCertDetail = queryMedVenCertDetailByCode(entityMap);

		if (medVenCertDetail != null) {

			int state = medVenCertDetailMapper.updateMedVenCertDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medVenCertDetailMapper.addMedVenCertDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedVenCertDetail\"}";

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
	public String queryMedVenCertDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedVenCertDetail> list = medVenCertDetailMapper.queryMedVenCertDetailSUP(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedVenCertDetail> list = medVenCertDetailMapper.queryMedVenCertDetailSUP(entityMap, rowBounds);
			
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
	public String queryMedVenCertDetailCert(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedVenCertDetail> list = medVenCertDetailMapper.queryMedVenCertDetailCert(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedVenCertDetail> list = medVenCertDetailMapper.queryMedVenCertDetailCert(entityMap, rowBounds);
			
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
	public MedVenCertDetail queryMedVenCertDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medVenCertDetailMapper.queryMedVenCertDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedVenCertDetail
	 * @throws DataAccessException
	*/
	@Override
	public MedVenCertDetail queryMedVenCertDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medVenCertDetailMapper.queryMedVenCertDetailByUniqueness(entityMap);
	}
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public MedVenCertDetail queryMedVenCertDetailByID(Map<String, Object> mapVo) throws DataAccessException{
		return medVenCertDetailMapper.queryMedVenCertDetailByID(mapVo);
	}
	
}
