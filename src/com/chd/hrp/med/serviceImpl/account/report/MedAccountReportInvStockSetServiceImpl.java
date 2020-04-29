/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.account.report.MedAccountReportInvStockSetMapper;
import com.chd.hrp.med.entity.MedShowDetail;
import com.chd.hrp.med.entity.MedShowSet;
import com.chd.hrp.med.service.account.report.MedAccountReportInvStockSetService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 081101 药品库存汇总表设置
 * @Table:
 * MED_SHOW_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountReportInvStockSetService")
public class MedAccountReportInvStockSetServiceImpl implements MedAccountReportInvStockSetService {

	private static Logger logger = Logger.getLogger(MedAccountReportInvStockSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountReportInvStockSetMapper")
	private final MedAccountReportInvStockSetMapper medAccountReportInvStockSetMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    
    
	/**
	 * @Description 
	 * 添加081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedShowSet(Map<String,Object> entityMap)throws DataAccessException{
		
		//判断名称编码是否重复
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("field_table", "med_show_set");
		map.put("field_key1", "show_id");
		map.put("field_value1", entityMap.get("show_id"));
		if (sysFunUtilMapper.existsSysCodeNameByAdd(map) > 0){
			return "{\"error\":\"编码：" + entityMap.get("show_id").toString() + "已存在.\"}";
		}
		map.put("field_table", "med_show_set");
		map.put("field_key1", "show_name");
		map.put("field_value1", entityMap.get("show_name"));
		if (sysFunUtilMapper.existsSysCodeNameByAdd(map) > 0){
			return "{\"error\":\"名称：" + entityMap.get("show_name").toString() + "已存在.\"}";
		}
        
        try {
            //新增
        	medAccountReportInvStockSetMapper.addMedShowSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	/**
	 * @Description 
	 * 更新081101 药品库存汇总表设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedShowSet(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//判断名称是否重复
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("field_table", "med_show_set");
			map.put("field_id", "show_id");
			map.put("field_id_value", entityMap.get("old_show_id"));
			map.put("field_key1", "show_id");
			map.put("field_value1", entityMap.get("show_id"));
			if (sysFunUtilMapper.existsSysCodeNameByUpdate(map) > 0){
				return "{\"error\":\"名称：" + entityMap.get("show_name").toString() + "已存在.\"}";
			}
			map.put("field_key1", "show_name");
			map.put("field_value1", entityMap.get("show_name"));
			if (sysFunUtilMapper.existsSysCodeNameByUpdate(map) > 0){
				return "{\"error\":\"名称：" + entityMap.get("show_name").toString() + "已存在.\"}";
			}
			
			medAccountReportInvStockSetMapper.updateMedShowSet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 删除081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedShowSet(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//删除明细
			medAccountReportInvStockSetMapper.deleteMedShowDetail(entityMap);
			//删除主表
			medAccountReportInvStockSetMapper.deleteMedShowSet(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除081101 药品库存汇总表设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteMedShowSetBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//删除明细
			medAccountReportInvStockSetMapper.deleteMedShowDetailBatch(entityList);
			//删除主表
			medAccountReportInvStockSetMapper.deleteMedShowSetBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedShowSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedShowSet> list = medAccountReportInvStockSetMapper.queryMedShowSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedShowSet> list = medAccountReportInvStockSetMapper.queryMedShowSet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 获取对象081101 药品库存汇总表设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedShowSet queryMedShowSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medAccountReportInvStockSetMapper.queryMedShowSetByCode(entityMap);
	}

	/**
	 * @Description 
	 * 添加明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedShowDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			//清空旧明细数据
			medAccountReportInvStockSetMapper.deleteMedShowDetail(entityMap);
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					Map<String,Object> detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailMap.put("show_id", entityMap.get("show_id"));//主表ID
					detailMap.put("bus_type_code",  jsonObj.get("bus_type_code"));//业务类型
					detailList.add(detailMap);
				}
				//如果取得明细数据，则添加
				if(detailList.size() > 0){
					medAccountReportInvStockSetMapper.addMedShowDetail(detailList);
				}
			}
		
			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		}catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"设置失败\"}");
		}
	}
	
	/**
	 * @Description 
	 * 获取明细集合<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedShowDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		List<MedShowDetail> list = medAccountReportInvStockSetMapper.queryMedShowDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}
	
}
