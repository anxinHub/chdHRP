/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.affi.tran;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.helper.DataUtil;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatAffiTranMain;
import com.chd.hrp.mat.entity.MatTranMain;
import com.chd.hrp.mat.service.affi.out.MatAffiOutCommonService;
import com.chd.hrp.mat.service.affi.tran.MatAffiTranMainService;
/**
 * 
 * @Description:
 * 代销调拨单 
 * @Table:
 * MAT_AFFI_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatAffiTranMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatAffiTranMainController.class);
	
	//引入Service服务
	@Resource(name = "matAffiTranMainService")
	private final MatAffiTranMainService matAffiTranMainService = null;
   
	@Resource(name = "matAffiOutCommonService")
	private final MatAffiOutCommonService matAffiOutCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranMainPage", method = RequestMethod.GET)
	public String matAffiTranMainPage(Model mode) throws Exception {		
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04025", MyConfig.getSysPara("04025"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		
		return "hrp/mat/affi/tran/matAffiTranMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranMainAddPage", method = RequestMethod.GET)
	public String matAffiTranMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/tran/matAffiTranMainAdd";

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
	@RequestMapping(value = "/hrp/mat/affi/tran/addMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("make_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));      
		
		String matJson = matAffiTranMainService.add(mapVo);
		
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
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranMainUpdatePage", method = RequestMethod.GET)
	public String MatAffiTranMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		 Map<String,Object> matAffiTranMain = matAffiTranMainService.queryMainByCode(mapVo);
		 mode.addAttribute("matAffiTranMain", matAffiTranMain);	
		 
		 Integer is_apply  = matAffiTranMainService.queryMatAffiTranMainIsApply(mapVo);
			if(is_apply>0){
				mode.addAttribute("is_apply", "1");
			}else{
				mode.addAttribute("is_apply", "0");
			}

			mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
			mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
			mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
			mode.addAttribute("p04025", MyConfig.getSysPara("04025"));
			mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
			mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
			mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
			mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/tran/matAffiTranMainUpdate";
	}
		
	/**
	 * 修改页面查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatAffiTranDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranDetailById(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiTranMainService.queryMatAffiTranDetailByTranID(getPage(mapVo));

		return JSONObject.parseObject(matJson);

	}
	
/**
 * 修改页面查询明细
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/hrp/mat/affi/tran/queryMatAffiTranDetailByCode", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryMatAffiTranDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}
	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}
	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	
	String matJson = matAffiTranMainService.queryMatAffiTranDetailByCode(getPage(mapVo));

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
	@RequestMapping(value = "/hrp/mat/affi/tran/updateMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		String matJson = matAffiTranMainService.update(mapVo);
		/*if(matJson.contains("true")){
			matAffiTranMainService.updateMatAffiTranRela(mapVo);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/addOrUpdateMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matAffiTranMainJson ="";
		

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
			
			try {
				matAffiTranMainJson = matAffiTranMainService.addOrUpdate(detailVo);
			} catch (Exception e) {
				matAffiTranMainJson = e.getMessage();
				e.printStackTrace();
			}
			
		}
		
		return JSONObject.parseObject(matAffiTranMainJson);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/deleteMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatAffiTranMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("rela_type", "4");
				mapVo.put("rela_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String matJson;
			try {
				matJson = matAffiTranMainService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		StringBuffer sql = new StringBuffer();		
		String tran_date_start = String.valueOf(mapVo.get("tran_date_start"));		
		if(tran_date_start!=null && !"".equals(tran_date_start) && !"null".equals(tran_date_start)){			
			sql.append("and to_char(mtm.tran_date) >= to_date('"+tran_date_start+"','yyyy-MM-dd') ");			
		}

		String tran_date_end = String.valueOf(mapVo.get("tran_date_end"));		
		if(tran_date_end!=null && !"".equals(tran_date_end) && !"null".equals(tran_date_end)){			
			sql.append("and to_char(mtm.tran_date) <= to_date('"+tran_date_end+"','yyyy-MM-dd') ");			
		}
		
		String check_date_start = String.valueOf(mapVo.get("check_date_start"));		
		if(check_date_start!=null && !"".equals(check_date_start) && !"null".equals(check_date_start)){			
			sql.append("and to_char(mtm.check_date) >= to_date('"+check_date_start+"','yyyy-MM-dd') ");			
		}

		String check_date_end = String.valueOf(mapVo.get("check_date_end"));		
		if(check_date_end!=null && !"".equals(check_date_end) && !"null".equals(check_date_end)){			
			sql.append("and to_char(mtm.check_date) <= to_date('"+check_date_end+"','yyyy-MM-dd') ");			
		}
		
		String confirm_date_start = String.valueOf(mapVo.get("confirm_date_start"));		
		if(confirm_date_start!=null && !"".equals(confirm_date_start) && !"null".equals(confirm_date_start)){			
			sql.append("and to_char(mtm.confirm_date) >= to_date('"+confirm_date_start+"','yyyy-MM-dd') ");			
		}

		String confirm_date_end = String.valueOf(mapVo.get("confirm_date_end"));		
		if(confirm_date_end!=null && !"".equals(confirm_date_end) && !"null".equals(confirm_date_end)){			
			sql.append("and to_char(mtm.confirm_date) <= to_date('"+confirm_date_end+"','yyyy-MM-dd') ");			
		}
			
		mapVo.put("sql", sql.toString());
		String matAffiTranMain = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			matAffiTranMain = matAffiTranMainService.queryMatAffiTranMain(getPage(mapVo));
		}else{ 
			matAffiTranMain = matAffiTranMainService.queryMatAffiTranMainDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(matAffiTranMain);
		
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranMainBySinglePage", method = RequestMethod.GET)
	public String matAffiTranMainBySinglePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("store_id", paraArray[0]);
				
			}
		}

		return "hrp/mat/affi/tran/matAffiTranMainBySingle";

	}
	
	/**
	 * @Description 整单出库主表查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatInMainBySingle", method = RequestMethod.POST)
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
		if (mapVo.get("in_date_start") != null) {
			mapVo.put("in_date_start", DateUtil.stringToDate(mapVo.get("in_date_start").toString(), "yyyy-MM-dd"));
			
		}
		if (mapVo.get("in_date_end") != null) {
			mapVo.put("in_date_end",  DateUtil.stringToDate(mapVo.get("in_date_end").toString(), "yyyy-MM-dd"));
			
		}
	
		String matOutMain = matAffiTranMainService.queryMatInMainBySingle(getPage(mapVo));
		return JSONObject.parseObject(matOutMain);

	}
	
	/**
	 * @Description 整单调拨明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatInDetailBySingle", method = RequestMethod.POST)
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
		String matOutMain = matAffiTranMainService.queryMatInDetailBySingle(getPage(mapVo));
		return JSONObject.parseObject(matOutMain);

	}
	/**
	 * @Description 组装整单调拨材料数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatAffiTranDetailBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranDetailBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matOutMain = matAffiTranMainService.queryMatAffiTranDetailBySingle(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}
	/**
	 * @Description 调出确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/outConfirmMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> outConfirmMatAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String confirm_date=DateUtil.getSysDate();//获取服务器当前日期
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Date  date = new Date();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			mapVo.put("checker", SessionManager.getUserId());
			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
				confirm_date =ids[4];
			}
			mapVo.put("check_date", confirm_date);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiTranMainService.outConfirmMatAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/unOutConfirmMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unOutConfirmMatAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);			
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiTranMainService.unOutConfirmMatAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/inConfirmMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inConfirmMatAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String date=DateUtil.getSysDate();//获取服务器当前日期
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String user_id = SessionManager.getUserId();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);

			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
				date = ids[4];
			}

			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			mapVo.put("state", "3");
			mapVo.put("year", year);
			mapVo.put("month", month);
			mapVo.put("confirmer", user_id);
			mapVo.put("confirm_date", date);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiTranMainService.inConfirmMatAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/balanceConfirmMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> balanceConfirmMatAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matJson = matAffiTranMainService.balanceConfirmMatAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatAffiTranMainByMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranMainByMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matCheckMain = matAffiTranMainService.queryMatAffiTranMainByMatInv(getPage(mapVo));

		return JSONObject.parseObject(matCheckMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranMainImportPage", method = RequestMethod.GET)
	public String matAffiTranMainImportPage(Model mode) throws Exception {

		return "hrp/mat/affi/tran/matAffiTranMainImport";

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
	 @RequestMapping(value="hrp/mat/affi/tran/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/mat/affi/tran/readMatAffiTranMainFiles",method = RequestMethod.POST)  
    public String readMatAffiTranMainFiles(Plupload plupload,HttpServletRequest request, HttpServletResponse response,Model mode) throws IOException { 
			 
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
	@RequestMapping(value = "/hrp/mat/affi/tran/addBatchMatAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatAffiTranMain> list_err = new ArrayList<MatAffiTranMain>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiOutMainDirPage", method = RequestMethod.GET)
	public String matAffiOutMainDirPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("store_id", paraArray[0]);
				
			}
		}

		return "hrp/mat/affi/tran/matAffiOutMainDir";

	}
	
	/**
	 * @Description 配套页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiOutMainMatchedPage", method = RequestMethod.GET)
	public String matAffiOutMainMatchedPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("dept_id", paraArray[0]);
				
			}
			
			if(!"null".equals(paraArray[1])){
				
				
				mode.addAttribute("store_id", paraArray[1]);
				
			}
		}
		
		return "hrp/mat/affi/tran/matAffiOutMainMatched";

	}
	
	/**
	 * @Description 历史导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiOutMainHistoryPage", method = RequestMethod.GET)
	public String matAffiOutMainHistoryPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("dept_id", paraArray[0]);
				
			}
			
			if(!"null".equals(paraArray[1])){
				
				
				mode.addAttribute("store_id", paraArray[1]);
				
			}
		}

		return "hrp/mat/affi/tran/matAffiOutMainHistory";

	}
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranPrintSetPage", method = RequestMethod.GET)
	public String matAffiTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/affi/tran/queryMatAffiTranByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=matAffiTranMainService.queryMatAffiTranByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	
	/**
	 * 添加页面选择材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/matAffiTranMainChoiceInvPage", method = RequestMethod.GET)
	public String matAffiTranMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/tran/matAffiTranMainChoiceInv";
	}
	/**
	 * 选择材料页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/queryAffiTranInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiTranInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matOutDetail = matAffiOutCommonService.queryMatAffiInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}
	
	/**
	 * @Description 选择材料返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/tran/queryAffiTranInvListByChoiceInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiTranInvListByChoiceInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matOutDetail = matAffiOutCommonService.queryAffiOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			matOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(matOutDetail);
	}

}

