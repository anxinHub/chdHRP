/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedVenCertTypeMapper;
import com.chd.hrp.med.entity.MedVenCertType;
import com.chd.hrp.med.service.info.basic.MedVenCertTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08604 供应商证件类型
 * @Table:
 * MED_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medVenCertTypeService")
public class MedVenCertTypeServiceImpl implements MedVenCertTypeService {

	private static Logger logger = Logger.getLogger(MedVenCertTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medVenCertTypeMapper")
	private final MedVenCertTypeMapper medVenCertTypeMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
    
	/**
	 * @Description 
	 * 添加08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedVenCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08604 供应商证件类型
		List<MedVenCertType> list = medVenCertTypeMapper.queryMedVenCertTypeByID(entityMap);

		if (list.size()>0) {
			for(MedVenCertType item: list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"已存在\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"已存在\"}";
				}
			}
		}
		try {
			
			int state = medVenCertTypeMapper.addMedVenCertType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedVenCertType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08604 供应商证件类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedVenCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medVenCertTypeMapper.addBatchMedVenCertType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedVenCertType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedVenCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		List<MedVenCertType> list = medVenCertTypeMapper.queryMedVenCertTypeByID(entityMap);

		if (list.size()>0) {
			for(MedVenCertType item: list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"已存在\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"已存在\"}";
				}
			}
		}
		try {
			
		  int state = medVenCertTypeMapper.updateMedVenCertType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedVenCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08604 供应商证件类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedVenCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medVenCertTypeMapper.updateBatchMedVenCertType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedVenCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedVenCertType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medVenCertTypeMapper.deleteMedVenCertType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedVenCertType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08604 供应商证件类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedVenCertType(String param)throws DataAccessException{
		
		try {
			
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			StringBuffer type_ids = new StringBuffer();
			
				for ( String id: param.split(",")) {
					
					Map<String, Object> mapVo=new HashMap<String, Object>();
					
					String[] ids=id.split("@");
					
					//表的主键
					mapVo.put("group_id", ids[0])   ;
					mapVo.put("hos_id", ids[1])   ;
					mapVo.put("copy_code", ids[2])   ;
					mapVo.put("type_id", ids[3]);
					
					listVo.add(mapVo);
					
					type_ids.append(ids[3] + ",");
		      
		    }
			
			//判断采购协议类别是否被引用
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("dict_code", "MED_VEN_CERT_TYPE");
			map.put("dict_id_str",type_ids.substring(0, type_ids.length()-1));
			
			map.put("acc_year", "");
			map.put("p_flag", "");
			
			
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"warn\":\"删除失败,选择的证件类型被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			medVenCertTypeMapper.deleteBatchMedVenCertType(listVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedVenCertType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedVenCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08604 供应商证件类型
		MedVenCertType medVenCertType = queryMedVenCertTypeByCode(entityMap);

		if (medVenCertType != null) {

			int state = medVenCertTypeMapper.updateMedVenCertType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medVenCertTypeMapper.addMedVenCertType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedVenCertType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedVenCertType(Map<String,Object> entityMap) throws DataAccessException{
		
		List<MedVenCertType> list = medVenCertTypeMapper.queryMedVenCertType(entityMap);
		
		return ChdJson.toJson(list);
		
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedVenCertType> list = medVenCertTypeMapper.queryMedVenCertType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedVenCertType> list = medVenCertTypeMapper.queryMedVenCertType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}*/
		
	}
	
	/**
	 * @Description 
	 * 获取对象08604 供应商证件类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedVenCertType queryMedVenCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medVenCertTypeMapper.queryMedVenCertTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08604 供应商证件类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedVenCertType
	 * @throws DataAccessException
	*/
	@Override
	public MedVenCertType queryMedVenCertTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medVenCertTypeMapper.queryMedVenCertTypeByUniqueness(entityMap);
	}
	/**
	 * 查询 与 输入证件类型编码、证件类型名称 相同的记录(判断证件类型编码、证件类型名称是否已存在)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedVenCertType> queryMedVenCertTypeByID(Map<String, Object> mapVo) throws DataAccessException{
		return medVenCertTypeMapper.queryMedVenCertTypeByID(mapVo);
	}
	
}
