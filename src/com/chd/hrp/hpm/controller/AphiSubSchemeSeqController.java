package com.chd.hrp.hpm.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.entity.AphiSubSchemeSeq;
import com.chd.hrp.hpm.serviceImpl.AphiSubSchemeSeqServiceImpl;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiSubSchemeSeqController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiSubSchemeSeqController.class);

	@Resource(name = "aphiSubSchemeSeqServiceImpl")
	private final AphiSubSchemeSeqServiceImpl aphiSubSchemeSeqServiceImpl = null;

	// 维护页面跳转
	@RequestMapping(value = "/hpm/subschemeseq/subSchemeSeqMainPage", method = RequestMethod.GET)
	public String subSchemeSeqMainPage(Model mode) throws Exception {

		return "hpm/subschemeseq/subSchemeSeqMain";

	}

	// 添加页面
	@RequestMapping(value = "/hpm/subschemeseq/subSchemeSeqAddPage", method = RequestMethod.GET)
	public String subSchemeSeqAddPage(Model mode) throws Exception {

		return "hpm/subschemeseq/subSchemeSeqAdd";

	}

	// 保存
	@RequestMapping(value = "/hpm/subschemeseq/addSubSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSubSchemeSeq(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String subSchemeSeqJson = aphiSubSchemeSeqServiceImpl.addSubSchemeSeq(mapVo);

		return JSONObject.parseObject(subSchemeSeqJson);

	}

	// 查询
	@RequestMapping(value = "/hpm/subschemeseq/querySubSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySubSchemeSeq(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String subSchemeSeq = aphiSubSchemeSeqServiceImpl
				.querySubSchemeSeq(getPage(mapVo));

		return JSONObject.parseObject(subSchemeSeq);

	}

	// 删除
	@RequestMapping(value = "/hpm/subschemeseq/deleteSubSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSubSchemeSeq(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
		
			Map<String, Object> mapVo = new HashMap<String, Object>();

			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("temp", id);// 实际实体类变量
			
			listVo.add(mapVo);
		}
		
		String subSchemeSeqJson = aphiSubSchemeSeqServiceImpl
				.deleteSubSchemeSeq(listVo);
		/*
		 * String bonusItemDictJson =
		 * bonusItemDictService.deleteBonusItemDictById(ParamVo);
		 */
		
		return JSONObject.parseObject(subSchemeSeqJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hpm/subschemeseq/subSchemeSeqUpdatePage", method = RequestMethod.GET)
	public String subSchemeSeqUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		AphiSubSchemeSeq subSchemeSeq = new AphiSubSchemeSeq();
		
		subSchemeSeq = aphiSubSchemeSeqServiceImpl.querySubSchemeSeqByCode(mapVo);
		
		//mode.addAttribute("group_id", subSchemeSeq.getGroupId());
		//mode.addAttribute("hos_id", subSchemeSeq.getHosId());
		mode.addAttribute("copy_code", subSchemeSeq.getCopy_code());
		
		mode.addAttribute("sub_scheme_seq_no",
				subSchemeSeq.getSub_scheme_seq_no());
		
		mode.addAttribute("sub_scheme_note", subSchemeSeq.getSub_scheme_note());

		return "hpm/subschemeseq/subSchemeSeqUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hpm/subschemeseq/updateSubSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSubSchemeSeq(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String subSchemeSeqJson = aphiSubSchemeSeqServiceImpl.updateSubSchemeSeq(mapVo);

		return JSONObject.parseObject(subSchemeSeqJson);
	}

}
