
package com.chd.hrp.mat.controller.eva;

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
import com.chd.hrp.mat.service.eva.MatEvaTargetService;

@Controller
@RequestMapping(value="/hrp/mat/eva/target")
public class MatEvaTargetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatEvaTargetController.class);
	
	//引入Service服务
	@Resource(name = "matEvaTargetService")
	private final MatEvaTargetService matEvaTargetService = null;
	
	//主页跳转
	@RequestMapping(value = "/matEvaTargetMainPage", method = RequestMethod.GET)
	public String matEvaTargetMainPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTargetMain";
	}

	/************************指标分类****************************************************/
	//主页左侧树指标分类查询
	@RequestMapping(value = "queryMatEvaTargetTypeTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatEvaTargetTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matEvaTargetService.queryMatEvaTargetTypeTree(mapVo);

		return retJson;
	}
	//指标分类维护页面跳转
	@RequestMapping(value = "/matEvaTargetTypePage", method = RequestMethod.GET)
	public String matEvaTargetTypePage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTargetType";
	}
	//指标分类编码规则查询
	@RequestMapping(value = "queryMatEvaTargetTypeRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaTargetTypeRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matEvaTargetService.queryMatEvaTargetTypeRules(mapVo);

		return JSONObject.parseObject(retJson);
	}
	//指标分类保存
	@RequestMapping(value = "/saveMatEvaTargetType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaTargetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.saveMatEvaTargetType(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//指标分类删除
	@RequestMapping(value = "/deleteMatEvaTargetType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatEvaTargetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.deleteMatEvaTargetType(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}

	/************************标准标度****************************************************/
	//标准标度页面跳转
	@RequestMapping(value = "/matEvaScaleListPage", method = RequestMethod.GET)
	public String matEvaScaleListPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaScaleList";
	}
	//标准标度查询
	@RequestMapping(value = "queryMatEvaScaleList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaScaleList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matEvaTargetService.queryMatEvaScaleList(mapVo);

		return JSONObject.parseObject(retJson);
	}
	//标准标度维护页面跳转
	@RequestMapping(value = "/matEvaScalePage", method = RequestMethod.GET)
	public String matEvaScalePage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaScale";
	}
	//标准标度保存
	@RequestMapping(value = "/saveMatEvaScale", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaScale(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.saveMatEvaScale(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//标准标度删除
	@RequestMapping(value = "/deleteMatEvaScale", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatEvaScale(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.deleteMatEvaScale(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}

	/************************指标********************************************************/
	//指标查询
	@RequestMapping(value = "queryMatEvaTargetList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaTargetList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matEvaTargetService.queryMatEvaTargetList(mapVo);

		return JSONObject.parseObject(retJson);
	}
	//指标维护页面跳转
	@RequestMapping(value = "/matEvaTargetPage", method = RequestMethod.GET)
	public String matEvaTargetAddPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTarget";
	}
	//指标保存
	@RequestMapping(value = "/saveMatEvaTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.saveMatEvaTarget(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//指标删除
	@RequestMapping(value = "/deleteMatEvaTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatEvaTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.deleteMatEvaTarget(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//指标导入页面跳转
	@RequestMapping(value = "/matEvaTargetImportPage", method = RequestMethod.GET)
	public String matEvaTargetImportPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTargetImport";
	}
	//指标导入
	@RequestMapping(value = "/addMatEvaTargetByImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatEvaTargetByImp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.addMatEvaTargetByImp(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}

	/************************指标标度****************************************************/
	//指标标度查询
	@RequestMapping(value = "queryMatEvaTargetScaleList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaTargetScaleList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matEvaTargetService.queryMatEvaTargetScaleList(mapVo);

		return JSONObject.parseObject(retJson);
	}
	//指标标度维护页面跳转
	@RequestMapping(value = "/matEvaTargetScalePage", method = RequestMethod.GET)
	public String matEvaTargetScaleAddPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTargetScale";
	}
	//指标标度保存
	@RequestMapping(value = "/saveMatEvaTargetScale", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaTargetScale(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.saveMatEvaTargetScale(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//指标标度删除
	@RequestMapping(value = "/deleteMatEvaTargetScale", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatEvaTargetScale(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.deleteMatEvaTargetScale(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//指标标度引入标准标度
	@RequestMapping(value = "/addMatEvaTargetScaleByBZ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatEvaTargetScaleByBZ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.addMatEvaTargetScaleByBZ(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//批量设置指标标度维护页面跳转
	@RequestMapping(value = "/matEvaTargetScaleBatchPage", method = RequestMethod.GET)
	public String matEvaTargetScaleBatchPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTargetScaleBatch";
	}
	//批量设置指标标度保存
	@RequestMapping(value = "/saveMatEvaTargetScaleBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaTargetScaleBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.saveMatEvaTargetScaleBatch(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/************************指标扣分项**************************************************/
	//指标扣分项查询
	@RequestMapping(value = "queryMatEvaTargetDeductList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaTargetDeductList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matEvaTargetService.queryMatEvaTargetDeductList(mapVo);

		return JSONObject.parseObject(retJson);
	}
	//指标扣分项维护页面跳转
	@RequestMapping(value = "/matEvaTargetDeductPage", method = RequestMethod.GET)
	public String matEvaTargetDeductAddPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/target/matEvaTargetDeduct";
	}
	//指标扣分项保存
	@RequestMapping(value = "/saveMatEvaTargetDeduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaTargetDeduct(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.saveMatEvaTargetDeduct(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//指标删除
	@RequestMapping(value = "/deleteMatEvaTargetDeduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatEvaTargetDeduct(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matEvaTargetService.deleteMatEvaTargetDeduct(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

