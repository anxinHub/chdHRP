/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.base;
import java.util.*;

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
import com.chd.hrp.mat.service.base.MatPortocolControlService;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * 
 * @Description:
 * 付款协议控制处理过程
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatPortocolController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPortocolController.class);
	
   
	//引入Service服务
	@Resource(name = "matPortocolControlService")
	private final MatPortocolControlService matPortocolControlService = null;
	
	/**
	 * @Description 
	 * 付款协议控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mate/base/queryControlExecProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryControlExecProcess(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("year", SessionManager.getAcctYear());
		
		Map<String, Object> matPortocolControlStr =JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
		String work_code =mapVo.get("work_code").toString();
		String work_note =mapVo.get("work_note").toString();
		if ("priceControl".equals(work_note)) {
			/**
			 * 材料入库环节控制  单价控制过程查看 返回消息查询
			 */
			matPortocolControlStr= matPortocolControlService.queryControlExecMatInPriceProcess(mapVo);
			return matPortocolControlStr;
		}else if ("totalControl".equals(work_note)) {
			/**
			 * 材料入库环节控制  总额控制过程查看 返回消息查询
			 */
			matPortocolControlStr= matPortocolControlService.queryControlExecMatInMoneyProcess(mapVo);
			return matPortocolControlStr;
		}
		return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");		
	}	
}

