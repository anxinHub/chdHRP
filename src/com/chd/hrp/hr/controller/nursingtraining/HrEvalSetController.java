package com.chd.hrp.hr.controller.nursingtraining;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.entity.nursingtraining.HrEvalSet;
import com.chd.hrp.hr.service.nursingtraining.HrEvalSetService;

/**
 * 
 * @ClassName: HrEvalSetController
 * @Description: 护理考核标准设定 HR_EVAL_SET
 * @author zn
 * @date 2018年1月19日 下午3:03:53
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/nursingtraining/hrevalset")
public class HrEvalSetController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrEvalSetController.class);
		
	@Resource(name = "hrEvalSetService")
	private final HrEvalSetService hrEvalSetService = null;

	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEvalSetMainPage", method = RequestMethod.GET)
	public String hrEvalSetMainPage(Model mode) throws Exception {
		return "hrp/hr/nursingtraining/hrevalset/hrEvalSetMain";
	}

	/**
	 * 查询数据
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrEvalSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEvalSet(@RequestParam Map<String,Object> mapVo) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<HrEvalSet> list = hrEvalSetService.queryHrEvalSet(mapVo);
		return ChdJson.toJson(list);
	}
	
	/**
	 * 保存数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrEvalSet", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrEvalSet(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		try {
			List<HrEvalSet> dataList = JSONArray.parseArray(mapVo.get("paramVo").toString(), HrEvalSet.class);
			List<String> yearList = new ArrayList<String>();
			for (HrEvalSet hrEvalSet : dataList) {
				if(hrEvalSet.getYear() == null || StringUtils.isBlank(hrEvalSet.getYear())){
					return "{\"error\":\"年度字段不能为空\"}";
				}
				if(hrEvalSet.getEval_code() == null || StringUtils.isBlank(hrEvalSet.getEval_code())){
					return "{\"error\":\"考核名称字段不能为空\"}";
				}
				if(hrEvalSet.getEval_goal() == null){
					return "{\"error\":\"考核合格分字段不能为空\"}";
				}
				
				if(yearList.contains(hrEvalSet.getYear())){
					return "{\"error\":\"年度不允许重复\"}";
				}
				hrEvalSet.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
				hrEvalSet.setHos_id(Long.parseLong(SessionManager.getHosId()));
				
				yearList.add(hrEvalSet.getYear());
			}
			
			//先删后加
			hrEvalSetService.deleteBatchHrEvalSet(dataList);
			hrEvalSetService.saveBatchHrEvalSet(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"数据保存失败\"}";
		}
		
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrEvalSet", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrEvalSet(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			List<HrEvalSet> dataList = JSONArray.parseArray(mapVo.get("paramVo").toString(), HrEvalSet.class);
			List<HrEvalSet> entityList = new ArrayList<HrEvalSet>();//重新组装List,用于组装正确的删除数据,避免误删除操作
			
			for(HrEvalSet hrEvalSet : dataList){
				
				//判断主键是否为空,避免误删数据
				if(hrEvalSet.getGroup_id() == null || hrEvalSet.getHos_id() == null || hrEvalSet.getYear() == null){
					continue;
				}
				if("".equals(String.valueOf(hrEvalSet.getGroup_id())) ||
					"".equals(String.valueOf(hrEvalSet.getHos_id())) ||
					"".equals(hrEvalSet.getYear())
				){
					continue;
				}
				
				entityList.add(hrEvalSet);
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
		
			hrEvalSetService.deleteBatchHrEvalSet(entityList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"数据删除失败\"}";
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	
}
