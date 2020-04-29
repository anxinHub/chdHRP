package com.chd.hrp.hr.controller.attendancemanagement.attendresult;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultItemService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 考勤报表
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attendresultItem")
public class HrAttendResultItemController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAttendResultItemController.class);

	// 引入Service服务 
	@Resource(name = "hrAttendResultItemService")
	private final HrAttendResultItemService hrAttendResultItemService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultItemPage", method = RequestMethod.GET)
	public String hrAttendResultItemMainPage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/attendresultitem/hrAttendResultItemPage";
	}
	
	 
	/**
	 * 查询表头
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryResultItemHead", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAttendResultManageHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  if(mapVo.get("kind_code") != null && mapVo.get("kind_code") != ""){
		  mapVo.put("kind_code", mapVo.get("kind_code").toString().replaceAll(";", ","));
        }
	  if(mapVo.get("attend_item") != null && mapVo.get("attend_item") != ""){
		  mapVo.put("attend_item", mapVo.get("attend_item").toString().replaceAll(";", ","));
		  mapVo.put("item_code", mapVo.get("attend_item").toString().replaceAll(";", ","));
        }
	  if(mapVo.get("yh_code") != null && mapVo.get("yh_code") != ""){
		  mapVo.put("yh_code", mapVo.get("yh_code").toString().replaceAll(";", ","));
        }
		String retMap ;
		try {
			 retMap = hrAttendResultItemService.queryResultItemHead(mapVo);
		} catch (Exception e) {
			retMap = e.getMessage();
		}
		return JSONObject.parseObject(retMap);
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResultItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendResultItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String stationTransef;
		try {
			  if(mapVo.get("kind_code") != null && mapVo.get("kind_code") != ""){
				  mapVo.put("kind_code", mapVo.get("kind_code").toString().replaceAll(";", ","));
		        }
			  if(mapVo.get("attend_item") != null && mapVo.get("attend_item") != ""){
				  mapVo.put("attend_item", mapVo.get("attend_item").toString().replaceAll(";", ","));
				  mapVo.put("item_code", mapVo.get("attend_item").toString().replaceAll(";", ","));
		        }
			  if(mapVo.get("yh_code") != null && mapVo.get("yh_code") != ""){
				  mapVo.put("yh_code", mapVo.get("yh_code").toString().replaceAll(";", ","));
		        }
			
			stationTransef = hrAttendResultItemService.queryAttendResultItem(getPage(mapVo));
		} catch (Exception e) {
			
			stationTransef = e.getMessage();
		}

		return JSONObject.parseObject(stationTransef); 

	}

	/**
	 * @Description 查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/queryResultItemHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendResultItemDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String stationTransef;
		try {
			
			stationTransef = hrAttendResultItemService.queryAttendResultItemDetail(mapVo);
		} catch (Exception e) {
			
			stationTransef = e.getMessage();
		}

		return JSONObject.parseObject(stationTransef); 

	}
	*/
	
}
