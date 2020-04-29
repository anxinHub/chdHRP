package com.chd.hrp.ass.controller.assmap;

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
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.service.assmap.AssAssMapService;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.service.DeptDictService;

@Controller
public class AssAssMapController extends BaseController{
	private static Logger logger = Logger.getLogger(AssAssMapController.class);
	
	//引入Service服务
	@Resource(name = "assAssMapService")
	private final AssAssMapService assAssMapService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;
	
	@Resource(name = "assNoDictService")
	private final AssNoDictService assNoDictService = null;
	
	private final String [] ASS_NATURS = {"01@房屋及建筑","02@专用设备","03@一般设备","04@其他固定资产","05@其他无形资产","06@土地来源"};
	
	private final String [] DEPT_TYPES = {"01@管理","02@医辅","03@医技","04@临床"};
	
	@RequestMapping(value = "/hrp/ass/assmap/assAllHosAssDistributionPage", method = RequestMethod.GET)
	public String assAllHosAssDistributionPage(Model mode) throws Exception {

		return "hrp/ass/assmap/assAllHosAssDistribution";
	}
	
	@RequestMapping(value = "/hrp/ass/assmap/assDeptAssDistributionPage", method = RequestMethod.GET)
	public String assDeptAssDistributionPage(Model mode) throws Exception {

		return "hrp/ass/assmap/assDeptAssDistribution";
	}
	
	@RequestMapping(value = "/hrp/ass/assmap/assAssCirculationViewPage", method = RequestMethod.GET)
	public String assAssCirculationViewPage(Model mode) throws Exception {

		return "hrp/ass/assmap/assAssCirculationView";
	}
	
	@RequestMapping(value = "/hrp/ass/assmap/assDistributionPage", method = RequestMethod.GET)
	public String assDistributionPage(Model mode) throws Exception {

		return "hrp/ass/assmap/assDistribution";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assmap/queryAssStoreDistribution", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssStoreDistribution(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String re = assAssMapService.queryAssStoreDistribution(mapVo);
		return JSONObject.parseObject(re);
	}
	
	
	@RequestMapping(value = "/hrp/ass/assmap/queryAssDeptDistribution", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDeptDistribution(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String re = assAssMapService.queryAssDeptDistribution(mapVo);
		return JSONObject.parseObject(re);
	}
	
	@RequestMapping(value = "/hrp/ass/assmap/queryDeptAssDistribution", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptAssDistribution(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String re = assAssMapService.queryDeptAssDistribution(mapVo);
		return JSONObject.parseObject(re);
	}
	
	@RequestMapping(value = "/hrp/ass/assmap/queryAssCirculationView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCirculationView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String re = assAssMapService.queryAssCirculationView(mapVo);
		re = re.replaceAll("null", "0");
		return JSONObject.parseObject(re);
	}
	
	@RequestMapping(value = "/hrp/ass/assmap/queryDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		StringBuilder jsonResult = new StringBuilder();
		String re = "";
		jsonResult.append("{data:[");
		
		for(int i = 0 ; i < DEPT_TYPES.length; i++){//循环添加科室类型
			mapVo.put("type_code", DEPT_TYPES[i].split("@")[0]);
			List<DeptDict> dictList = deptDictService.queryDeptDictByType(mapVo);
			int rows = 0;
			if(i == 0){
				jsonResult.append("{");
			}else{
				jsonResult.append(",{");
			}
			jsonResult.append("name:'"+DEPT_TYPES[i].split("@")[1]+"',value:4");
			if(dictList.size() > 0){
				jsonResult.append(",children:[");
			}
			for(DeptDict temp : dictList){//循环添加每一个科室
				if (rows == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				rows++;
				jsonResult.append("name:'"+temp.getDept_name()+"',value:2,children:[");
				for(int j = 0 ; j < ASS_NATURS.length ; j++){//循环添加资产性质
					if(j == 0){
						jsonResult.append("{");
					}else{
						jsonResult.append(",{");
					}
					jsonResult.append("name:'"+ASS_NATURS[j].split("@")[1]+"',value:"+ASS_NATURS.length+"");
					mapVo.put("ass_naturs", ASS_NATURS[j].split("@")[0]);
					List<AssNoDict> assList = assNoDictService.queryAssNoDictList(mapVo);
					if(assList.size() > 0){
						jsonResult.append(",children:[");
					}
					for(int s = 0 ; s < assList.size(); s++){//循环添加资产性质下的资产
						if(s == 0){
							jsonResult.append("{");
						}else{
							jsonResult.append(",{");
						}
						jsonResult.append("name:'"+assList.get(s).getAss_name()+"',value:2");
						//此处循环资产以下的节点for(int d = 0; d < xxx; d++){}
						jsonResult.append("}");
					}
					if(assList.size() > 0){
						jsonResult.append("]");
					}
					jsonResult.append("}");
				}
				jsonResult.append("]}");
			}
			if(dictList.size() > 0){
				jsonResult.append("]");
			}
			jsonResult.append("}");
		}
		jsonResult.append("]}");
		re = jsonResult.toString();
		return JSONObject.parseObject(re);
	}
}
