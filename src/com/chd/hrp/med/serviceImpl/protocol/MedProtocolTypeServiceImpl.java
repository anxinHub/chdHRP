/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.protocol;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.protocol.MedProtocolTypeMapper;
import com.chd.hrp.med.entity.MedProtocolType;
import com.chd.hrp.med.service.protocol.MedProtocolTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08501 付款协议类别
 * @Table:
 * MED_PROTOCOL_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medProtocolTypeService")
public class MedProtocolTypeServiceImpl implements MedProtocolTypeService {

	private static Logger logger = Logger.getLogger(MedProtocolTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medProtocolTypeMapper")
	private final MedProtocolTypeMapper medProtocolTypeMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 
	 * 添加08501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedProtocolType(Map<String,Object> entityMap)throws DataAccessException{
		//获取当前物流管理系统的启用年月
		Date current =new Date();
		Map<String, Object> paraMap = medProtocolTypeMapper.queryYearMonth(entityMap);
		if(Integer.parseInt(String.valueOf(current.getYear())+String.valueOf(current.getMonth())) > 
			Integer.parseInt(String.valueOf(paraMap.get("start_year"))+String.valueOf(paraMap.get("start_month")))){
			return ("协议类别维护日期必须在当前物流管理系统的启用年月:"+ paraMap.get("start_year") + paraMap.get("start_month") + "之前！");
		}
		
		//获取对象08501 付款协议类别
		List<MedProtocolType> list = medProtocolTypeMapper.queryMedProtocolTypeByID(entityMap);

		if (list.size()>0) {
			for(MedProtocolType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else if(item.getPre().equals(entityMap.get("pre"))){
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
					}else{
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+" 重复,请重新添加.\"}";
					}
				}else{
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else{
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}
					}
					
				}
			}
		}
		try {
			
			int state = medProtocolTypeMapper.addMedProtocolType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedProtocolType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08501 付款协议类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedProtocolType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medProtocolTypeMapper.addBatchMedProtocolType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedProtocolType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedProtocolType(Map<String,Object> entityMap)throws DataAccessException{
		//获取当前物流管理系统的启用年月
		Date current =new Date();
		Map<String, Object> paraMap = medProtocolTypeMapper.queryYearMonth(entityMap);
		if(Integer.parseInt(String.valueOf(current.getYear())+String.valueOf(current.getMonth())) > 
			Integer.parseInt(String.valueOf(paraMap.get("start_year"))+String.valueOf(paraMap.get("start_month")))){
			return ("协议类别维护日期必须在当前物流管理系统的启用年月:"+ paraMap.get("start_year") + paraMap.get("start_month") + "之前！");
		}
		
		List<MedProtocolType> list = medProtocolTypeMapper.queryMedProtocolTypeByID(entityMap);

		if (list.size()>0) {
			for(MedProtocolType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else if(item.getPre().equals(entityMap.get("pre"))){
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
					}else{
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+" 重复,请重新添加.\"}";
					}
				}else{
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else{
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}
					}
					
				}
			}
		}
		try {
			
		  int state = medProtocolTypeMapper.updateMedProtocolType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败  请联系管理员! 方法 updateMedProtocolType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08501 付款协议类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedProtocolType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medProtocolTypeMapper.updateBatchMedProtocolType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 方法 updateBatchMedProtocolType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedProtocolType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medProtocolTypeMapper.deleteMedProtocolType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedProtocolType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08501 付款协议类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedProtocolType(String paramVo)throws DataAccessException{
		
		try {
			
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			StringBuffer type_ids = new StringBuffer();
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("type_id", ids[3]);
				
				type_ids.append(ids[3] + ",");
				
				listVo.add(mapVo);
	      
			}
			
			
			//判断采购协议类别是否被引用
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("dict_code", "MED_PROTOCOL_TYPE");
			map.put("dict_id_str",type_ids.substring(0, type_ids.length()-1));
			
			map.put("acc_year", "");
			map.put("p_flag", "");
			
			
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"warn\":\"删除失败,选择的协议类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			medProtocolTypeMapper.deleteBatchMedProtocolType(listVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加08501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedProtocolType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08501 付款协议类别
		MedProtocolType medProtocolType = queryMedProtocolTypeByCode(entityMap);

		if (medProtocolType != null) {

			int state = medProtocolTypeMapper.updateMedProtocolType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medProtocolTypeMapper.addMedProtocolType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedProtocolType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedProtocolType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedProtocolType> list = medProtocolTypeMapper.queryMedProtocolType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedProtocolType> list = medProtocolTypeMapper.queryMedProtocolType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedProtocolType queryMedProtocolTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medProtocolTypeMapper.queryMedProtocolTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedProtocolType
	 * @throws DataAccessException
	*/
	@Override
	public MedProtocolType queryMedProtocolTypeByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return medProtocolTypeMapper.queryMedProtocolTypeByUniqueness(entityMap);
	}
	public List<MedProtocolType> queryMedProtocolTypeByID(Map<String, Object> entityMap) throws DataAccessException{
		return medProtocolTypeMapper.queryMedProtocolTypeByID(entityMap);
	}
	/**
	 * 获取当前物流管理系统的启用年月
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryYearMonth(Map<String, Object> mapVo) throws DataAccessException{
		return medProtocolTypeMapper.queryYearMonth(mapVo);
	}
	
}
