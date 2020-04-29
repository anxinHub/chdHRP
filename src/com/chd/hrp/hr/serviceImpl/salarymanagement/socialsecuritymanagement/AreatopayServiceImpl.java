package com.chd.hrp.hr.serviceImpl.salarymanagement.socialsecuritymanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.salarymanagement.socialsecuritymanagement.AreatopayMapper;
import com.chd.hrp.hr.entity.salarymanagement.socialsecuritymanagement.HrAreatopay;
import com.chd.hrp.hr.service.salarymanagement.socialsecuritymanagement.AreatopayService;
import com.github.pagehelper.PageInfo;

@Service("areatopayService")
public class AreatopayServiceImpl implements AreatopayService {

	private static Logger logger = Logger.getLogger(AreatopayServiceImpl.class);
	
	@Resource(name = "areatopayMapper")
	private final AreatopayMapper areatopayMapper = null;

	@Override
	public List<Map<String, Object>> queryAreatopayInsurtypeSelect(Map<String, Object> mapVo) {
		return areatopayMapper.queryAreatopayInsurtypeSelect(mapVo);
	}

	@Override
	public String addAreapay(Map<String, Object> mapVo) {
		
		try {
			
			//全局新增先删除所有数据
			areatopayMapper.deleteFromAreapay(mapVo);
			
			//转换json字符串
			List<HrAreatopay> list = JSONArray.parseArray(mapVo.get("gridarr").toString(),HrAreatopay.class);
			
			//新增
			int addAreapayCount = areatopayMapper.addAreapay(mapVo,list);
			if(addAreapayCount == 0){
				new SysException();
			}
			
			return "{\"msg\":\"保存成功!\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String queryAreatopay(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= areatopayMapper.queryAreatopay(mapVo);
				
				return ChdJson.toJson(list);
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= areatopayMapper.queryAreatopay(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public List<Map<String, Object>> queryAreatopayInsurtypeSelects(Map<String, Object> mapVo) {
		return areatopayMapper.queryAreatopayInsurtypeSelects(mapVo);
	}

	@Override
	public String importAreatopay(Map<String, Object> entityMap) {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		StringBuilder booleanreturn = new StringBuilder();
		List<HrAreatopay> saveList = new ArrayList<HrAreatopay>();
		
		//查询社保险种code值
		List<Map<String, Object>> codeList = areatopayMapper.queryAreatopayInsurtypeSelect(entityMap);
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		for (Map<String, Object> map : codeList) {
			whetherMap.put(map.get("label").toString(), map.get("id"));
		}
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				superList:
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("area", map.get("area").get(1));
					if(whetherMap.get(map.get("insur_type").get(1)) != null){
						saveMap.put("insur_type", whetherMap.get(map.get("insur_type").get(1)));
					}else {
						booleanreturn.append("<br/>数据填写错误"+map.get("insur_type").get(0));
						continue superList;
					}
					saveMap.put("unit_rate", map.get("unit_rate").get(1));
					saveMap.put("individual_rate", map.get("individual_rate").get(1));
					saveMap.put("remark", map.get("remark").get(1));
					
					for (HrAreatopay hrA: saveList) {
						if(hrA.getAREA().equals(saveMap.get("area")) && hrA.getInsur_type().equals(saveMap.get("insur_type"))){
							failureMsg.append("<br/>投保地区和社保险种重复 "+map.get("area").get(1)+":"+map.get("insur_type").get(1)+" 重复; ");
							failureNum++;
							continue superList;
						}
					}
					
					HrAreatopay hrAreatopay = areatopayMapper.queryImportAreatopay(saveMap);
					if(hrAreatopay != null){
						failureMsg.append("<br/>投保地区和社保险种已存在 "+map.get("area").get(1)+":"+map.get("insur_type").get(1)+" 已存在; ");
						failureNum++;
						continue;
					}
					
					successNum++;
					hrAreatopay = JSON.parseObject(JSON.toJSONString(saveMap), HrAreatopay.class);
					saveList.add(hrAreatopay);
				}
				if(booleanreturn.length() > 0){
					return "{\"error\":\"导入 0 条,失败信息如下! "+booleanreturn+"\",\"state\":\"true\"}";
				}
				if(saveList.size() > 0){
					areatopayMapper.addAreapay(entityMap, saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}
	
}
