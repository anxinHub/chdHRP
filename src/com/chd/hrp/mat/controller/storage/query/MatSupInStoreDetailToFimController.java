/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.query;

import java.text.ParseException;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.storage.query.MatSupInStoreDetailToFimService;

/**
 * 
 * @Description: 物流统计查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatSupInStoreDetailToFimController extends BaseController {

	private static Logger logger = Logger.getLogger(MatSupInStoreDetailToFimController.class);
	

	//引入Service服务
	@Resource(name = "matSupInStoreDetailToFimService")
	private final MatSupInStoreDetailToFimService matSupInStoreDetailToFimService = null;
	

	

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matSupInStoreDetailToFim", method = RequestMethod.GET)
	public String MatSupInStoreDetailToFim(Model mode) throws Exception {

		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		
		return "hrp/mat/storage/query/MatSupInStoreDetailToFim";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatSupInStoreDetailToFim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupInStoreDetailToFim(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matInSupCount = matSupInStoreDetailToFimService.queryMatSupInStoreDetailToFim(mapVo);

		return JSONObject.parseObject(matInSupCount);

	}
	
	/**
	 * 查询发生过入库业务的 财务分类的编码和名称用于生成 入库材料汇总表(财务)的动态表头
	 * @param mapVo
	 * @param mdl
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/hrp/mat/storage/query/queryOccurMatFimTypeDictForHead",method=RequestMethod.POST)
	@ResponseBody
	public String queryOccurMatFimTypeDictForHead(@RequestParam Map<String, Object> mapVo,Model mdl) throws ParseException{
		if (mapVo.get("group_id")==null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if (mapVo.get("hos_id")==null) {mapVo.put("hos_id", SessionManager.getHosId());}
		if (mapVo.get("copy_code")==null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		if (mapVo.get("user_id")==null) {mapVo.put("user_id", SessionManager.getUserId());}
		
		return matSupInStoreDetailToFimService.queryOccurMatFimTypeDictForHead(mapVo);
	}
	
	
 
 
	
	
}
