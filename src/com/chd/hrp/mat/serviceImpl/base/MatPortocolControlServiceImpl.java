/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.base;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.chd.hrp.mat.dao.base.MatPortocolControlMapper;
import com.chd.hrp.mat.service.base.MatPortocolControlService;

@Service("matPortocolControlService")
public class MatPortocolControlServiceImpl implements MatPortocolControlService {

	private static Logger logger = Logger.getLogger(MatPortocolControlServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matPortocolControlMapper")
	private final MatPortocolControlMapper matPortocolControlMapper = null;
		
	@Resource(name = "matPortocolControlService") //方案执行过程 service
	private final MatPortocolControlService matPortocolControlService =null;
	
    /**
     * 材料入库付款协议单价控制返回消息查询
     */
	@Override
	public Map<String, Object> queryControlExecMatInPriceProcess(Map<String, Object> mapVo) throws DataAccessException {

		int work_flag_4 = 0;//提示
		int work_flag_5 = 0;//错误
		StringBuffer control_str = new StringBuffer();
		// 保存明细数据
		List<Map<String, Object>> detail_data = new ArrayList<Map<String, Object>>();// 存放明细
		// 解析明细数据
		JSONArray detail_json = JSONArray.parseArray((String) mapVo.get("work_detail_data"));
		Iterator detail_it = detail_json.iterator();
		while (detail_it.hasNext()) {
			Map<String, Object> mapDetailVo = new HashMap<String, Object>();
			
			JSONObject jsonObj = JSONObject.parseObject(detail_it.next().toString());
			mapDetailVo.put("inv_id", jsonObj.get("work_item_code").toString());
			mapDetailVo.put("inv_price", jsonObj.get("work_item_price").toString());
			mapDetailVo.put("inv_money", jsonObj.get("work_item_money").toString());
			detail_data.add(mapDetailVo);
		}
		mapVo.put("detail_data", detail_data);			
		
 		List<Map<String, Object>> resultList= matPortocolControlMapper.queryMatInPriceControl(mapVo);
 		 		
		for (Map<String, Object> resultMap : resultList) {
			control_str.append(""+resultMap.get("INV_CODE")+" "); //材料编码
			control_str.append(""+resultMap.get("INV_NAME")+","); //材料名称
			work_flag_5 = 1;
		}							
		if (work_flag_5==1){
			control_str.append("材料入库单价大于协议单价，不允许保存");
			return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
		}else if (work_flag_4==1){
			return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
		}
		return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");
	}
	/**
     * 材料入库付款协议总额控制返回消息查询
     */
	@Override
	public Map<String, Object> queryControlExecMatInMoneyProcess(Map<String, Object> mapVo) throws DataAccessException {
		
		int work_flag_4 = 0;//提示
		int work_flag_5 = 0;//错误
		StringBuffer control_str = new StringBuffer();
		// 保存明细数据
		List<Map<String, Object>> detail_data = new ArrayList<Map<String, Object>>();// 存放明细
		// 解析明细数据
		JSONArray detail_json = JSONArray.parseArray((String) mapVo.get("work_detail_data"));
		Iterator detail_it = detail_json.iterator();
		while (detail_it.hasNext()) {
			Map<String, Object> mapDetailVo = new HashMap<String, Object>();
			
			JSONObject jsonObj = JSONObject.parseObject(detail_it.next().toString());
			mapDetailVo.put("inv_id", jsonObj.get("work_item_code").toString());
			mapDetailVo.put("inv_price", jsonObj.get("work_item_price").toString());
			mapDetailVo.put("inv_money", jsonObj.get("work_item_money").toString());
			detail_data.add(mapDetailVo);
		}
		mapVo.put("detail_data", detail_data);
			
 		List<Map<String, Object>> resultList= matPortocolControlMapper.queryMatInMoneyControl(mapVo);

		for (Map<String, Object> resultMap : resultList) {
			control_str.append(""+resultMap.get("RETURN_INFO")+" "); //返回信息
			work_flag_4 = 1;
		}									
		if (work_flag_5==1){
			return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"5\"}");
		}else if (work_flag_4==1){
			return JSONObject.parseObject("{\"work_msg\":\"" +control_str+"\",\"work_flag\":\"4\"}");
		}
		return JSONObject.parseObject("{\"work_msg\":\"无消息\",\"work_flag\":\"1\"}");
	}
}
