/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.info.basic.MatPayTypeService;
/**
 * 
 * @Description:
 * MAT_PAY_TYPE
 * @Table:
 * MAT_PAY_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatPayTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(MatPayTypeController.class);
	
	
	@Resource(name = "matPayTypeService")
	private final MatPayTypeService matPayTypeService = null;
   
    
	/**
	 * 支付方式主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/matPayTypeMainPage", method = RequestMethod.GET)
	public String matPayTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/paytype/matPayTypeMain";

	}
	/**
	 * 支付方式--添加页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/matPayTypeAddPage", method = RequestMethod.GET)
	public String matPayTypeAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/paytype/matPayTypeAdd";

	}
	
	/**
	 * 支付方式--继承页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/matPayTypeExtendPage", method = RequestMethod.GET)
	public String matPayTypeExtendPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/paytype/matPayTypeExtend";

	}
	/**
	 * 支付方式--保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/addMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String matPayTypeJson = matPayTypeService.add(mapVo);

		return JSONObject.parseObject(matPayTypeJson);
		
	}
	/**
	 * 支付方式--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/queryMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		mapVo.put("hos_id", SessionManager.getHosId());		
		mapVo.put("copy_code", SessionManager.getCopyCode());		
		String matPayType = matPayTypeService.query(getPage(mapVo));
		return JSONObject.parseObject(matPayType);
		
	}
	
	/**
	 * 支付方式--删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/deleteMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPayType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("pay_code", id.split("@")[0]);
			mapVo.put("group_id", id.split("@")[1]);
			mapVo.put("hos_id", id.split("@")[2]);
			mapVo.put("copy_code", id.split("@")[3]);
            //mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String matPayTypeJson = matPayTypeService.deleteBatch(listVo);
	   return JSONObject.parseObject(matPayTypeJson);
			
	}
	
	/**
	 * 支付方式--更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/matPayTypeUpdatePage", method = RequestMethod.GET)
	
	public String matPayTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
      
		Map<String,Object> matPayType = matPayTypeService.queryByCode(mapVo);
		mode.addAttribute("matPayType", matPayType);
		
		return "hrp/mat/info/basic/paytype/matPayTypeUpdate";
	}
	
	/**
	 * 支付方式--更新
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/updateMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatematPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());		
		}
		
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_name").toString()));	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_name").toString()));
	   
		String matPayTypeJson = matPayTypeService.update(mapVo);
		
		return JSONObject.parseObject(matPayTypeJson);
	}
	
	/**
	 * 支付方式--导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/importMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importmatPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matPayTypeJson =null;// matPayTypeService.import(mapVo);
		
		return JSONObject.parseObject(matPayTypeJson);
	}
	
	/**
	 * 支付方式--继承
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/paytype/extendMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendMatPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());		
		}
		
		
		String matPayTypeJson = matPayTypeService.extendMatPayType(mapVo);

		return JSONObject.parseObject(matPayTypeJson);
	}

}

