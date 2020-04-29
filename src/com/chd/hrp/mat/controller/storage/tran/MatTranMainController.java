/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */ 
package com.chd.hrp.mat.controller.storage.tran;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.ChdToken;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatTranMain;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;
import com.chd.hrp.mat.service.storage.tran.MatTranMainService;
/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MAT_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
   
@Controller
public class MatTranMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatTranMainController.class);
	
	//引入Service服务
	@Resource(name = "matTranMainService")
	private final MatTranMainService matTranMainService = null;
	@Resource(name = "matOutMainService")
	private final MatOutMainService matOutMainService = null;
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainMainPage", method = RequestMethod.GET)
	public String matTranMainMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		
		return "hrp/mat/storage/tran/matTranMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainAddPage", method = RequestMethod.GET)
	public String matTranMainAddPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/tran/matTranMainAdd";
	}

	/**
	 * @Description 
	 * 添加数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/addMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		mapVo.put("maker", SessionManager.getUserId());

		String matJson;
		try {
			matJson = matTranMainService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
		调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainUpdatePage", method = RequestMethod.GET)
	public String matTranMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
		
		if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
		
		if(mapVo.get("copy_code") == null){mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}	
	
		 MatTranMain matTranMain = matTranMainService.queryMatTranMainByCode(mapVo);
		
		mode.addAttribute("group_id", matTranMain.getGroup_id());
		mode.addAttribute("hos_id", matTranMain.getHos_id());
		mode.addAttribute("copy_code", matTranMain.getCopy_code());
		mode.addAttribute("tran_id", matTranMain.getTran_id());
		mode.addAttribute("tran_no", matTranMain.getTran_no());
		mode.addAttribute("tran_method", matTranMain.getTran_method());
		mode.addAttribute("tran_type", matTranMain.getTran_type());
		mode.addAttribute("out_hos_id", matTranMain.getOut_hos_id());
		mode.addAttribute("out_hos_code", matTranMain.getOut_hos_code());
		mode.addAttribute("out_hos_name", matTranMain.getOut_hos_name());
		mode.addAttribute("out_store_id", matTranMain.getOut_store_id());
		mode.addAttribute("out_store_no", matTranMain.getOut_store_no());
		mode.addAttribute("out_store_code", matTranMain.getOut_store_code());
		mode.addAttribute("out_store_name", matTranMain.getOut_store_name());
		mode.addAttribute("in_hos_id", matTranMain.getIn_hos_id());
		mode.addAttribute("in_hos_code", matTranMain.getIn_hos_code());
		mode.addAttribute("in_hos_name", matTranMain.getIn_hos_name());
		mode.addAttribute("in_store_id", matTranMain.getIn_store_id());
		mode.addAttribute("in_store_no", matTranMain.getIn_store_no());
		mode.addAttribute("in_store_code", matTranMain.getIn_store_code());
		mode.addAttribute("in_store_name", matTranMain.getIn_store_name());
		mode.addAttribute("brief", matTranMain.getBrief());
		mode.addAttribute("tran_date", DateUtil.dateToString(matTranMain.getTran_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", matTranMain.getState());
	
		Integer is_apply  = matTranMainService.queryMatTranMainIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p04076", MyConfig.getSysPara("04076"));

		return "hrp/mat/storage/tran/matTranMainUpdate";
	}
	/**
	 * @Description 
	 * 调拨明细查询页面-显示详情
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainPageForShow", method = RequestMethod.GET)
	public String matTranMainPageForShow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
		
		if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
		
		if(mapVo.get("copy_code") == null){mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}	
		
		MatTranMain matTranMain = matTranMainService.queryMatTranMainByCode(mapVo);
		
		mode.addAttribute("group_id", matTranMain.getGroup_id());
		mode.addAttribute("hos_id", matTranMain.getHos_id());
		mode.addAttribute("copy_code", matTranMain.getCopy_code());
		mode.addAttribute("tran_id", matTranMain.getTran_id());
		mode.addAttribute("tran_no", matTranMain.getTran_no());
		mode.addAttribute("tran_method", matTranMain.getTran_method());
		mode.addAttribute("tran_type", matTranMain.getTran_type());
		mode.addAttribute("out_hos_id", matTranMain.getOut_hos_id());
		mode.addAttribute("out_hos_code", matTranMain.getOut_hos_code());
		mode.addAttribute("out_hos_name", matTranMain.getOut_hos_name());
		mode.addAttribute("out_store_id", matTranMain.getOut_store_id());
		mode.addAttribute("out_store_no", matTranMain.getOut_store_no());
		mode.addAttribute("out_store_code", matTranMain.getOut_store_code());
		mode.addAttribute("out_store_name", matTranMain.getOut_store_name());
		mode.addAttribute("in_hos_id", matTranMain.getIn_hos_id());
		mode.addAttribute("in_hos_code", matTranMain.getIn_hos_code());
		mode.addAttribute("in_hos_name", matTranMain.getIn_hos_name());
		mode.addAttribute("in_store_id", matTranMain.getIn_store_id());
		mode.addAttribute("in_store_no", matTranMain.getIn_store_no());
		mode.addAttribute("in_store_code", matTranMain.getIn_store_code());
		mode.addAttribute("in_store_name", matTranMain.getIn_store_name());
		mode.addAttribute("brief", matTranMain.getBrief());
		mode.addAttribute("tran_date", DateUtil.dateToString(matTranMain.getTran_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", matTranMain.getState());
		
		Integer is_apply  = matTranMainService.queryMatTranMainIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/tran/matTranMainForShow";
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String matTranDetail = matTranMainService.queryMatTranDetailByTranID(getPage(mapVo));

		return JSONObject.parseObject(matTranDetail);
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String matTranDetail = matTranMainService.queryMatTranDetailByCode(getPage(mapVo));

		return JSONObject.parseObject(matTranDetail);
	}
		
	/**
	 * @Description 
	 * 更新数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
		调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/updateMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}

		String matJson;
		try {
			matJson = matTranMainService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		/*if(matJson.contains("true")){
			matTranMainService.updateMatTranOutRela(mapVo);
		}*/
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/addOrUpdateMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matTranMainJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		matTranMainJson = matTranMainService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(matTranMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/deleteMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatTranMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("tran_id", ids[3]);
				//用于删除与申请单之前的对应关系
				mapVo.put("rela_type", "2");
				mapVo.put("rela_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }

		String matJson;
		try {
			matJson = matTranMainService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
	    
	  return JSONObject.parseObject(matJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//由于xml中已经to_char （之前我查询的日期是2017-08-11 的日期，查询不到2017-08-11 的数据，必须日期选择为2017-08-12才会查询到）
		//转换日期格式
		/*if(mapVo.get("begin_tran_date") != null && !"".equals(mapVo.get("begin_tran_date"))){
			mapVo.put("begin_tran_date", DateUtil.stringToDate(mapVo.get("begin_tran_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_tran_date") != null && !"".equals(mapVo.get("end_tran_date"))){
			mapVo.put("end_tran_date", DateUtil.stringToDate(mapVo.get("end_tran_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_check_date") != null && !"".equals(mapVo.get("begin_check_date"))){
			mapVo.put("begin_check_date", DateUtil.stringToDate(mapVo.get("begin_check_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_check_date") != null && !"".equals(mapVo.get("end_check_date"))){
			mapVo.put("end_check_date", DateUtil.stringToDate(mapVo.get("end_check_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
		}*/
		
		String matTranJson = null; 
		
		if("0".equals(mapVo.get("show_detail"))){
			matTranJson = matTranMainService.queryMatTranMain(getPage(mapVo));
		}else{
			matTranJson = matTranMainService.queryMatTranDetails(getPage(mapVo));
		}

		return JSONObject.parseObject(matTranJson);
		
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainBySinglePage", method = RequestMethod.GET)
	public String matTranMainBySinglePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		} 
		 
		if(paras != null && !"".equals(paras)){
			
			String[] paraArray = paras.split(","); 
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("store_id", paraArray[0]);
				
			}
			
			if(!"null".equals(paraArray[1])){
							
				mode.addAttribute("store_code", paraArray[1]);
				
			}
			
			if(!"null".equals(paraArray[2])){
				
				mode.addAttribute("store_name", paraArray[2]);
				
			}
			
		}
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/tran/matTranMainBySingle";

	}
	
	/**
	 * @Description 查询数据 MAT_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatInMainBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInMainBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		
		StringBuffer sql = new StringBuffer();
		
		String in_date_start = String.valueOf(mapVo.get("in_date_start"));
		
		if(in_date_start!=null && !"".equals(in_date_start) && !"null".equals(in_date_start)){
			
			sql.append("and in_date > to_date('"+in_date_start+"','yyyy/MM/dd') ");
			
		}

		String in_date_end = String.valueOf(mapVo.get("in_date_end"));
		
		if(in_date_end!=null && !"".equals(in_date_end) && !"null".equals(in_date_end)){
			
			sql.append("and in_date < to_date('"+in_date_end+"','yyyy/MM/dd') ");  
			
		}

		mapVo.put("sql", sql.toString());
		
		String matOutMain = matTranMainService.queryMatInMainBySingle(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);

	}
	
	/**
	 * @Description 查询数据 MAT_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatInDetailBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInDetailBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matOutMain = matTranMainService.queryMatInDetailBySingle(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);

	}
	
	/**
	 * @Description 组装整单调拨材料数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranDetailBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranDetailBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		String matOutMain = matTranMainService.queryMatTranDetailBySingle(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}
	
	/**
	 * @Description 调出确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/outConfirmMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> outConfirmMatTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			
			mapVo.put("checker", SessionManager.getUserId());
			
			mapVo.put("check_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

			listVo.add(mapVo);

		}

		String matJson;
		try {
			matJson = matTranMainService.confirmOutMatTranMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 取消调出确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/unOutConfirmMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unOutConfirmMatTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			mapVo.put("checker", "");
			
			mapVo.put("check_date", "");
			listVo.add(mapVo);

		}

		String matJson;
		try {
			matJson = matTranMainService.unConfirmOutMatTranMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 调入确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/inConfirmMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inConfirmMatTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String date=DateUtil.getSysDate();//获取服务器当前日期

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String user_id = SessionManager.getUserId();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[1]);
			mapVo.put("group_id", ids[0]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			mapVo.put("state", "3");

			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString()) ){
				date = ids[4];
			}

			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			
			mapVo.put("year", year);
			mapVo.put("month", month);
			mapVo.put("confirmer", user_id);
			mapVo.put("confirm_date", date);
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matTranMainService.confirmInMatTranMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 冲账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/balanceConfirmMatTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> balanceConfirmMatTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			
			mapVo.put("tran_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));
			
			mapVo.put("maker", SessionManager.getUserId());
			
			listVo.add(mapVo);

		}

		String matJson;
		try {
			matJson = matTranMainService.offsetMatTranMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranMainByMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranMainByMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String matCheckMain = matTranMainService.queryMatTranMainByMatInv(getPage(mapVo));

		return JSONObject.parseObject(matCheckMain);
		
	}

	/**
	 * @Description 查看出库单跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainUpdateOutPage", method = RequestMethod.GET)
	public String matTranMainUpdateOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		Map<String, Object> matOutMain = matOutMainService.queryMatOutMainByCode(mapVo);
		 
		if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date").toString())){
			 matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
		}
		 
		mode.addAttribute("matOutMain", matOutMain);
		
		String matOutDetail = matOutMainService.queryMatOutDetailByOutId(getPage(mapVo));
		
		mode.addAttribute("matOutDetail", matOutDetail);
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));

		return "hrp/mat/storage/tran/matTranMainOut";
	}

	/**
	 * @Description 
	 * 查看入库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainUpdateInPage", method = RequestMethod.GET)
	public String matTranMainUpdateInPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		Map<String, Object> matInMain = matStorageInService.queryByCode(mapVo);
		
		if(matInMain.get("in_date") != null && !"".equals(matInMain.get("in_date"))){
			matInMain.put("in_date", DateUtil.dateToString((Date)matInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("check_date") != null && !"".equals(matInMain.get("check_date"))){
			matInMain.put("check_date", DateUtil.dateToString((Date)matInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("confirm_date") != null && !"".equals(matInMain.get("confirm_date"))){
			matInMain.put("confirm_date", DateUtil.dateToString((Date)matInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matInMain", matInMain);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/tran/matTranMainIn";
	}
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranPrintSetPage", method = RequestMethod.GET)
	public String matTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		//System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=matTranMainService.queryMatTranByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainImportPage", method = RequestMethod.GET)
	public String matTranMainImportPage(Model mode) throws Exception {

		return "hrp/mat/storage/tran/matTranMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/storage/tran/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","调拨方式TRAN_METHOD：1同价调拨  2 异价调拨调拨类型：1 院内调拨  2 院外调拨.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/storage/tran/readMatTranMainFiles",method = RequestMethod.POST)  
    public String readMatTranMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatTranMain> list_err = new ArrayList<MatTranMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatTranMain matTranMain = new MatTranMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matTranMain.setTran_id(Long.valueOf(temp[3]));
		    		mapVo.put("tran_id", temp[3]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("tran_no", temp[4]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
		    		mapVo.put("year", temp[5]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
		    		mapVo.put("month", temp[6]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matTranMain.setTran_method(Integer.valueOf(temp[7]));
		    		mapVo.put("tran_method", temp[7]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matTranMain.setTran_type(Integer.valueOf(temp[8]));
		    		mapVo.put("tran_type", temp[8]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matTranMain.setOut_hos_id(Long.valueOf(temp[9]));
		    		mapVo.put("out_hos_id", temp[9]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matTranMain.setOut_store_id(Long.valueOf(temp[10]));
		    		mapVo.put("out_store_id", temp[10]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					matTranMain.setOut_store_no(Long.valueOf(temp[11]));
		    		mapVo.put("out_store_no", temp[11]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					matTranMain.setIn_hos_id(Long.valueOf(temp[12]));
		    		mapVo.put("in_hos_id", temp[12]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					matTranMain.setIn_store_id(Long.valueOf(temp[13]));
		    		mapVo.put("in_store_id", temp[13]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					matTranMain.setIn_store_no(Long.valueOf(temp[14]));
		    		mapVo.put("in_store_no", temp[14]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
		    		mapVo.put("brief", temp[15]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					matTranMain.setTran_date(DateUtil.stringToDate(temp[16]));
		    		mapVo.put("tran_date", temp[16]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					matTranMain.setMaker(Long.valueOf(temp[17]));
		    		mapVo.put("maker", temp[17]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					matTranMain.setChecker(Long.valueOf(temp[19]));
		    		mapVo.put("checker", temp[19]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					matTranMain.setCheck_date(DateUtil.stringToDate(temp[20]));
		    		mapVo.put("check_date", temp[20]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					matTranMain.setConfirmer(Long.valueOf(temp[21]));
		    		mapVo.put("confirmer", temp[21]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					matTranMain.setConfirm_date(DateUtil.stringToDate(temp[22]));
		    		mapVo.put("confirm_date", temp[22]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					matTranMain.setState(Integer.valueOf(temp[23]));
		    		mapVo.put("state", temp[23]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					
				MatTranMain data_exc_extis = matTranMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matTranMain.setError_type(err_sb.toString());
					
					list_err.add(matTranMain);
					
				} else {
			  
					String dataJson = matTranMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatTranMain data_exc = new MatTranMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/storage/tran/addBatchMatTranMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatTranMain> list_err = new ArrayList<MatTranMain>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			MatTranMain matTranMain = new MatTranMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("tran_id"))) {
						
					matTranMain.setTran_id(Long.valueOf((String)jsonObj.get("tran_id")));
		    		mapVo.put("tran_id", jsonObj.get("tran_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_no"))) {
						
		    		mapVo.put("tran_no", jsonObj.get("tran_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_method"))) {
						
					matTranMain.setTran_method(Integer.valueOf((String)jsonObj.get("tran_method")));
		    		mapVo.put("tran_method", jsonObj.get("tran_method"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_type"))) {
						
					matTranMain.setTran_type(Integer.valueOf((String)jsonObj.get("tran_type")));
		    		mapVo.put("tran_type", jsonObj.get("tran_type"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("out_hos_id"))) {
						
					matTranMain.setOut_hos_id(Long.valueOf((String)jsonObj.get("out_hos_id")));
		    		mapVo.put("out_hos_id", jsonObj.get("out_hos_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("out_store_id"))) {
						
					matTranMain.setOut_store_id(Long.valueOf((String)jsonObj.get("out_store_id")));
		    		mapVo.put("out_store_id", jsonObj.get("out_store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("out_store_no"))) {
						
					matTranMain.setOut_store_no(Long.valueOf((String)jsonObj.get("out_store_no")));
		    		mapVo.put("out_store_no", jsonObj.get("out_store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_hos_id"))) {
						
					matTranMain.setIn_hos_id(Long.valueOf((String)jsonObj.get("in_hos_id")));
		    		mapVo.put("in_hos_id", jsonObj.get("in_hos_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_store_id"))) {
						
					matTranMain.setIn_store_id(Long.valueOf((String)jsonObj.get("in_store_id")));
		    		mapVo.put("in_store_id", jsonObj.get("in_store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_store_no"))) {
						
					matTranMain.setIn_store_no(Long.valueOf((String)jsonObj.get("in_store_no")));
		    		mapVo.put("in_store_no", jsonObj.get("in_store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brief"))) {
						
		    		mapVo.put("brief", jsonObj.get("brief"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_date"))) {
						
					matTranMain.setTran_date(DateUtil.stringToDate((String)jsonObj.get("tran_date")));
		    		mapVo.put("tran_date", jsonObj.get("tran_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					matTranMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					matTranMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					matTranMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirmer"))) {
						
					matTranMain.setConfirmer(Long.valueOf((String)jsonObj.get("confirmer")));
		    		mapVo.put("confirmer", jsonObj.get("confirmer"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_date"))) {
						
					matTranMain.setConfirm_date(DateUtil.stringToDate((String)jsonObj.get("confirm_date")));
		    		mapVo.put("confirm_date", jsonObj.get("confirm_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matTranMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					
				MatTranMain data_exc_extis = matTranMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matTranMain.setError_type(err_sb.toString());
					
					list_err.add(matTranMain);
					
				} else {
			  
					String dataJson = matTranMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatTranMain data_exc = new MatTranMain();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	/**
	 * 添加页面选择材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainChoiceInvPage", method = RequestMethod.GET)
	public String matTranMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/tran/matTranMainChoiceInv";
	}
	/**
	 * 添加页面选择条码材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/matTranMainChoiceInvBarPage", method = RequestMethod.GET)
	public String matTranMainChoiceInvBarPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/tran/matTranMainChoiceInvBar";
	}
	/**
	 * 选择材料页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryMatTranInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTranInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matOutDetail = matOutMainService.queryMatInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}
	
	/**
	 * @Description 选择材料返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/tran/queryTranInvListByChoiceInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTranInvListByChoiceInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("store_id", ids[3]);
			mapVo.put("inv_id", ids[4]);
			mapVo.put("inv_code", ids[5]);
			mapVo.put("inv_name", ids[6]);
			mapVo.put("batch_no", ids[7]);
			mapVo.put("amount", ids[8]);

			listVo.add(mapVo);
		}
		
		String matOutDetail;
		try {
			matOutDetail = matOutMainService.queryOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			matOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(matOutDetail);
	}
}

