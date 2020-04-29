package com.chd.hrp.mat.controller.storage.maintain;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.dao.storage.maintain.MatMaintainMapper;
import com.chd.hrp.mat.service.maintain.MatMaintainService;

@Controller
public class MatMaintainController extends BaseController {
	
	private static Logger logger=Logger.getLogger(MatMaintainController.class);
	
	//引入service服务
	@Resource(name="matMaintainService")
	private final MatMaintainService matMaintainService=null;
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/matMaintainMainPage",method=RequestMethod.GET)
	public String matMaintainMainPage(){
		return "hrp/mat/storage/maintain/matMaintainMain";
	}
	/**
	 * 跳转到添加界面
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/matMaintainMainAddPage",method=RequestMethod.GET)
	public String matMaintainMainAddPage(){
		return "hrp/mat/storage/maintain/matMaintainMainAdd";
	}
	/**
	 * 查询仓库材料用于养护记录
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/queryStoreExistInvForMaintain",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreExistInvForMaintain(@RequestParam Map<String, Object> mapVo,Model model){
		
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id")==null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		return JSONObject.parseObject(matMaintainService.queryStoreExistInvForMaintain(mapVo));
	}
	
	@RequestMapping(value="/hrp/mat/storage/maintain/addMatMaintainMainAndDetail",method=RequestMethod.POST)
	@ResponseBody
	public String addMatMaintainMainAndDetail(@RequestParam Map<String, Object> mapVo,Model mdl){
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id")==null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		return matMaintainService.addMatMaintainMainAndDetailByStoreAndMatType(mapVo);
	}
	@RequestMapping(value="/hrp/mat/storage/maintain/matMaintainUpdatePage",method=RequestMethod.GET)
	public String matMaintainUpdatePage(@RequestParam Map<String, Object> mapVo,Model mdl){
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		Map<String, Object> matMaintainMain=matMaintainService.queryMatMaintainMainByID(mapVo);
		mdl.addAttribute("mt_id",matMaintainMain.get("mt_id"));
		mdl.addAttribute("mt_no",matMaintainMain.get("mt_no"));
		mdl.addAttribute("make_date",matMaintainMain.get("make_date").toString().substring(0,10));
		mdl.addAttribute("store_id",matMaintainMain.get("store_id"));
		mdl.addAttribute("store_no",matMaintainMain.get("store_no"));
		mdl.addAttribute("mat_type_id",matMaintainMain.get("mat_type_id"));
		mdl.addAttribute("mat_type_no",matMaintainMain.get("mat_type_no"));
		mdl.addAttribute("mat_type_code",matMaintainMain.get("mat_type_code"));
		mdl.addAttribute("mat_type_name",matMaintainMain.get("mat_type_name"));
		mdl.addAttribute("brief",matMaintainMain.get("brief"));
		
		return "hrp/mat/storage/maintain/matMaintainMainUpdate";
	}
	/**
	 * 通过养护记录主表主键查询对应明细信息用于修改页面的显示
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/queryMatMaintainDetailByMtID",method=RequestMethod.POST)
	@ResponseBody
	public String queryMatMaintainDetailByMtID(@RequestParam Map<String, Object> mapVo){
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return matMaintainService.queryMatMaintainDetailByMtID(mapVo);
	}
	/**
	 * 修改养护记录主表和明细
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/updateMatMaintainMainAndDetail",method=RequestMethod.POST)
	@ResponseBody
	public String updateMatMaintainMainAndDetail(@RequestParam Map<String, Object> mapVo){
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return matMaintainService.updateMatMaintainMainAndDetail(mapVo);
	}
	/**
	 * 查询养护记录主表
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/queryMatMaintainMain",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatMaintainMain(@RequestParam Map<String, Object> mapVo){
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return JSONObject.parseObject(matMaintainService.queryMatMaintainMain(mapVo));
	}
	/**
	 * 批量删除养护主表和明细表
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/maintain/deleteMatMaintainMainAndDetail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatMaintainMainAndDetail(@RequestParam(value = "ParamVo") String paramVo){
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("mt_id", ids[3]);
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matMaintainService.deleteMatMaintainMainAndDetail(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	}
}
