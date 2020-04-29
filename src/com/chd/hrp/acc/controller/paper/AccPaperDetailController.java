package com.chd.hrp.acc.controller.paper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.paper.AccPaperDetailService;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

@Controller
public class AccPaperDetailController extends BaseController {
 
	private static Logger logger = Logger.getLogger(AccPaperDetailController.class);

	@Resource(name = "accPaperDetailService")
	private final AccPaperDetailService accPaperDetailService = null;
	

	/**
	 * 一次领用<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailUseOnePage", method = RequestMethod.GET)
	public String accAccPaperDetailUseOnePage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailUseOne";

	}
	
	/**
	 * <BR>
	 * 一次领用查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/queryAccPaperDetailUseOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperDetailUseOne(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("paper_use_type", "(1,2)");
		
		String queryAccPaperDetailJson = accPaperDetailService.queryAccPaperDetail(getPage(mapVo));

		return JSONObject.parseObject(queryAccPaperDetailJson);

	}
	
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailSetUser", method = RequestMethod.GET)
	public String accAccPaperDetailSetUser(@RequestParam Map<String, Object> mapVo ,Model mode) throws Exception {

		mode.addAttribute("ParamVo", mapVo.get("ParamVo"));
		
		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailSetUser";

	}
	
	/**
	*<BR>
	*一次领用
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseOneReceive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseOneReceive(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String Message = "";
		
		String paramVo =map.get("paramVo").toString();
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
            
            Date date=new Date();
            
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			
			if("".equals(map.get("user_id"))){
				
				mapVo.put("out_user_id1", SessionManager.getUserId());
				
			}else{
				
				mapVo.put("out_user_id1", map.get("user_id").toString());
			}
			
            mapVo.put("out_date1",map.get("out_date1").toString() );
            
            mapVo.put("state1", ids[5]);
            
        	if("2".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"单据已领用! \"}";
				break;
			}
        	
        	if("3".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"作废的单据不许操作! \"}";
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(Message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "领用");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	/**
	*<BR>
	*一次取消领用
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseOneCancelReceive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseOneCancelReceive(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String Message = "";
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("out_user_id1", "");
			
            mapVo.put("out_date1", "");
            
            mapVo.put("state1", ids[5]);
            
        	if(!"2".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"单据已经取消领用! \"}";
				break;
			}
        	
        	if("3".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"作废的单据不许操作! \"}";
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(Message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "取消领用");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	/**
	*<BR>
	*一次作废
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseOneNullify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseOneNullify(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            Date date=new Date();
            
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("inva_user_id", SessionManager.getUserId());
			
            mapVo.put("inva_date", format.format(date));
            
            mapVo.put("state1", ids[5]);
            
        	if("3".equals(ids[6])){
				flag = false;
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"单据已作废\"}");
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "作废");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	/**
	*<BR>
	*一次作废
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseOneCancelNullify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseOneCancelNullify(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
			
            mapVo.put("out_user_id1", "");
			
            mapVo.put("out_date1", "");
            
			mapVo.put("inva_user_id", "");
			
            mapVo.put("inva_date", "");
            
            mapVo.put("state1", ids[5]);
            
        	if(!"3".equals(ids[6])){
				flag = false;
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"单据已取消作废\"}");
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "取消作废");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	/**
	 * 二次领用<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailUseTwoPage", method = RequestMethod.GET)
	public String accAccPaperDetailUseTwoPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailUseTwo";

	}
	
	/**
	 * <BR>
	 * 二次领用查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/queryAccPaperDetailUseTwo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperDetailUseTwo(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("paper_use_type_Two", "(2)");

		String queryAccPaperDetailJson = accPaperDetailService.queryAccPaperDetail(getPage(mapVo));

		return JSONObject.parseObject(queryAccPaperDetailJson);

	}
	
	/**
	*<BR>
	*二次领用
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseTwoReceive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseTwoReceive(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String Message = "";
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
            
            Date date=new Date();
            
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			
			mapVo.put("out_user_id2", SessionManager.getUserId());
			
            mapVo.put("out_date2", format.format(date));
            
            mapVo.put("state2", ids[5]);
            
        	if("2".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"单据已领用! \"}";
				break;
			}
        	
        	if("3".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"作废的单据不许操作! \"}";
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(Message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "领用");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	/**
	*<BR>
	*二次取消领用
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseTwoCancelReceive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseTwoCancelReceive(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String Message = "";
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("out_user_id2", "");
			
            mapVo.put("out_date2", "");
            
            mapVo.put("state2", ids[5]);
            
        	if(!"2".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"单据已经取消领用! \"}";
				break;
			}
        	
        	if("3".equals(ids[6])){
				flag = false;
				Message = "{\"error\":\"作废的单据不许操作! \"}";
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(Message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "取消领用");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	/**
	*<BR>
	*二次作废
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseTwoNullify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseTwoNullify(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            Date date=new Date();
            
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("inva_user_id", SessionManager.getUserId());
			
            mapVo.put("inva_date", format.format(date));
            
            mapVo.put("state2", ids[5]);
            
        	if("3".equals(ids[6])){
				flag = false;
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"单据已作废\"}");
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "作废");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	/**
	*<BR>
	*二次作废
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailUseTwoCancelNullify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailUseTwoCancelNullify(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
			
            mapVo.put("out_user_id2", "");
			
            mapVo.put("out_date2", "");
            
			mapVo.put("inva_user_id", "");
			
            mapVo.put("inva_date", "");
            
            mapVo.put("state2", ids[5]);
            
        	if(!"3".equals(ids[6])){
				flag = false;
				break;
			}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"单据已取消作废\"}");
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "取消作废");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	
	/**
	 * 票据管理<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailManagePage", method = RequestMethod.GET)
	public String accAccPaperDetailManagePage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailManage";

	}
	
	/**
	 * <BR>
	 * 票据管理查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/queryAccPaperDetailManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperDetailManage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperDetailManageJson = accPaperDetailService.queryAccPaperDetailManage(getPage(mapVo));

		return JSONObject.parseObject(accPaperDetailManageJson);

	}
	
	
	
	/**
	 * 单张票据核销<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailSolaPage", method = RequestMethod.GET)
	public String accAccPaperDetailSolaPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailSola";

	}
	
	

	/**
	 * 单张票据核销<BR>
	 * 凭证金额 凭证号维护页面
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accPaperDetailSolaAddPage", method = RequestMethod.GET)
	public String accPaperMainAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("paramVo", mapVo.get("paramVo"));
		
		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailSolaAdd";

	}
	
	/**
	 * 单张票据核销<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/queryAccPaperDetailSola", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperDetailSola(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		//票据类型的管理方式 ：单张管理
		mapVo.put("paper_way_type", "1");
		
		//领用状态
		mapVo.put("state1", "2,3");
		
		String accPaperDetailJson = accPaperDetailService.queryAccPaperDetailSola(getPage(mapVo));

		return JSONObject.parseObject(accPaperDetailJson);

	}
	
	
	/**
	*单张票据核销<BR>
	*确认核销
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailSolaVerification", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailSolaVerification(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String message = "";
		
		//String paramVo = map.get("paramVo").toString();
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            Date date=new Date();
            
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("check_user_id", SessionManager.getUserId());
			
            mapVo.put("check_date", format.format(date));
            
            mapVo.put("is_check", ids[5]);
            
        	if("1".equals(ids[6])){
				flag = false;
				message = "{\"error\":\"单据已核销!\"}";
				break;
			}

        	if(!"".equals(ids[7].toString().trim())){
            	
            	mapVo.put("check_money", ids[7]);
            	
        	}
        	
        	if(!"".equals(ids[8].toString().trim())){
        		
        	    mapVo.put("vouch_no", ids[8].toString().trim());
        	    
        	}
        	if (ids.length == 10) {
        		if(ids[9] != null && (!"".equals(ids[9].toString().trim()))){
        	    	mapVo.put("note", ids[9].toString().trim());
        		}
        	}
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "核销");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	

	/**
	*单张票据核销<BR>
	*确认取消核销
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailSolaCancelVerification", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> udateAccPaperDetailSolaCancelVerification(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String message = "";
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("check_user_id", "");
			
            mapVo.put("check_date", "");
            
            mapVo.put("is_check", ids[5]);
            
        	if("0".equals(ids[6])){
        		
        		message = "{\"error\":\"单据已经取消核销\"}";
        		
				flag = false;
				break;
			}
            
        	if(!"vouch_id".equals(ids[7])){
        		
        		message = "{\"error\":\"已自动核销的单据不许操作\"}";
        		
        		flag = false;
				break;
        	}
        	
        		
        	    mapVo.put("vouch_no", "");
        	    
            	mapVo.put("check_money", "");

        	
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "取消核销");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	
	/**
	*单张票据核销<BR>
	*确认作废
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailSolaNullify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailSolaNullify(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String message = "";
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            Date date=new Date();
            
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("inva_user_id", SessionManager.getUserId());
			
            mapVo.put("inva_date", format.format(date));
            
            mapVo.put("state1", '3');
            
 	        if(!"vouch_id".equals(ids[5])){
        		
        		message = "{\"error\":\"已自动核销的单据不许操作\"}";
        		
        		flag = false;
				break;
        	}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "作废");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	/**
	*单张票据核销<BR>
	*确认作废
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/updateAccPaperDetailSolaCancelNullify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperDetailSolaCancelNullify(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		String message = "";
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("pid", ids[3]);
            
            mapVo.put("paper_num", ids[4]);
			
			mapVo.put("inva_user_id", "");
			
            mapVo.put("inva_date", "");
            
            mapVo.put("state1", '2');
            
             if(!"vouch_id".equals(ids[5])){
        		
        		message = "{\"error\":\"已自动核销的单据不许操作\"}";
        		
        		flag = false;
				break;
        	}
            
            listVo.add(mapVo);
            
        }
		
		if(flag == false){
			
			return JSONObject.parseObject(message);
		}
		
		String updateBatchAccPaperDetailJson = accPaperDetailService.updateBatchAccPaperDetail(listVo, "取消作废");
	   
		return JSONObject.parseObject(updateBatchAccPaperDetailJson);
			
	}
	
	
	/**
	 * 批量票据核销<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailBatchPage", method = RequestMethod.GET)
	public String accAccPaperDetailBatchPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailBatch";

	}
	
	/**
	 * 票据转移<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailTransferPage", method = RequestMethod.GET)
	public String accAccPaperDetailTransferPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailTransfer";

	}
	
	/**
	 * 票据使用情况<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperdetail/accAccPaperDetailUsePage", method = RequestMethod.GET)
	public String accAccPaperDetailUsePage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpaperdetail/accAccPaperDetailUse";

	}
	

	
}