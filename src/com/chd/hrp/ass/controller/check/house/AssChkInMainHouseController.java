/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.check.house;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.check.house.AssChkInMainHouse;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.check.house.AssChkInDetailHouseService;
import com.chd.hrp.ass.service.check.house.AssChkInMainHouseService;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(房屋及建筑)
 * @Table:
 * ASS_CHK_IN_MAIN_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssChkInMainHouseController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssChkInMainHouseController.class);
	
	//引入Service服务
	@Resource(name = "assChkInMainHouseService")
	private final AssChkInMainHouseService assChkInMainHouseService = null;
	@Resource(name = "assChkInDetailHouseService")
	private final AssChkInDetailHouseService assChkInDetailHouseService = null;
	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/assChkInHouseMainPage", method = RequestMethod.GET)
	public String assChkInMainHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05060",MyConfig.getSysPara("05060"));
		
		return "hrp/ass/asshouse/checkin/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/assChkInMainHouseAddPage", method = RequestMethod.GET)
	public String assChkInMainHouseAddPage(Model mode) throws Exception {
		mode.addAttribute("ass_nature", "01");
		return "hrp/ass/asscard/assInCardAdd";

	}

	/**
	 * @Description 
	 * 添加数据 资产入库主表(房屋及建筑)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/addAssChkInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssChkInMainHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
       
		String assChkInMainHouseJson = assChkInMainHouseService.add(mapVo);

		return JSONObject.parseObject(assChkInMainHouseJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 资产入库主表(房屋及建筑)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/deleteAssChkInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkInMainHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssChkInMainHouse assChkInMainHouse= assChkInMainHouseService.queryByCode(mapVo);
			
			if(assChkInMainHouse.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assChkInMainHouseJson = assChkInMainHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkInMainHouseJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 资产入库主表(房屋及建筑)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/queryAssInRestMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInMainHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assChkInMainHouse = assChkInMainHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assChkInMainHouse);
		
	}
	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/updateConfirmChkInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkInMainHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assChkInMainJson = "";
		for (String vdata : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = vdata.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssChkInMainHouse assChkInMainHouse = assChkInMainHouseService.queryByCode(mapVo);
			if (assChkInMainHouse.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assChkInMainHouse.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			
			List<Map<String, Object>> list = assChkInDetailHouseService.queryByInit(mapVo);//资产明细数据
			
			if(list.size() == 0){
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for(Map<String, Object> temp : list){
				
				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));
				
				mapExists.put("ass_id", temp.get("ass_id"));
				
				mapExists.put("ass_no", temp.get("ass_no"));
				
				Integer use_state = assCardHouseService.queryCardExistsByAssInNo(mapExists);//通过资产
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state != Integer.parseInt(temp.get("in_amount").toString())){
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardHouseService.queryAssCardByAssInNo(mapExists);
				for(Map<String, Object> cardno : cardList){
					Map<String, Object> MapCard= new HashMap<String, Object>();
					
					MapCard.put("ass_card_no", cardno.get("ass_card_no"));
					MapCard.put("group_id", cardno.get("group_id"));
					MapCard.put("hos_id", cardno.get("hos_id"));
					MapCard.put("copy_code", cardno.get("copy_code"));
					Integer dept_house=assChkInMainHouseService.queryBydept(MapCard);
					Integer dept_r_house=assChkInMainHouseService.queryByRdept(MapCard);
					if (dept_house == 0 || dept_r_house==0) {
						return JSONObject.parseObject("{\"warn\":\"卡片没有维护使用科室,不能入库! \"}");
					}
				}

				
				
				boolean assFlag = true,
					
						priceFlag = true,
						
						venFlag = true;
						
				
				for(Map<String, Object> card : cardList){
					if(verification(temp.get("ass_id"),card.get("ass_id"))){
						assFlag = false;
						break;
					}
					//if(verification(temp.get("in_money"),card.get("price"))){
						//priceFlag = false;
						//break;
					//}
					if(verification(temp.get("BUS_TYPE_NAME"),card.get("bus_type_name"))){
						venFlag = false;
						break;
					}
				}
				if(!assFlag){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if(!priceFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
				if(!venFlag){
					return JSONObject.parseObject("{\"warn\":\"存在供应商不匹配的资产,不能入库! \"}");
				}
			}
			
		}
		
		
		for (String data : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = data.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);
			
			mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());
			
			AssChkInMainHouse assChkInMainHouse = assChkInMainHouseService.queryByCode(mapVo);//主表
			if (assChkInMainHouse.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assChkInMainJson = assChkInMainHouseService.updateConfirmChkInHouse(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assChkInMainJson);
	}
	
	private boolean verification(Object obj1,Object obj2){
		//true为不相等
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
			int number1 = obj1 == null || obj1.equals("") ? 0 : Integer.parseInt(String.valueOf(obj1));
			int number2 = obj2 == null || obj2.equals("") ? 0 : Integer.parseInt(String.valueOf(obj2));
			if(number1 != number2){
				return true;
			}else{
				return false;
			}
		}
		if(obj1 == null && obj2 == null){
			return false;
		}
		if(obj1 == null && obj2 != null){
			return true;
		}
		if(obj1 != null && obj2 == null){
			return true;
		}
		if(obj1 != null && obj2 != null){
			if(!obj1.equals(obj2)){
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}	

  /**
	 * @Description 
	 * 导入跳转页面 资产入库主表(房屋及建筑)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/assChkInMainHouseImportPage", method = RequestMethod.GET)
	public String assChkInMainHouseImportPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/check/checkin/assChkInMainHouseImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 资产入库主表(房屋及建筑)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/ass/asshouse/check/checkin/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","资产入库主表(房屋及建筑).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 资产入库主表(房屋及建筑)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/asshouse/check/checkin/readAssChkInMainHouseFiles",method = RequestMethod.POST)  
    public String readAssChkInMainHouseFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException {
				return null;
		
		
		
	}  
   /**
	 * @Description 
	 * 批量添加数据 资产入库主表(房屋及建筑)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/addBatchAssChkInMainHouse", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssChkInMainHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		return null;}
	
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/check/checkin/queryAssChkInHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assChkInMainHouseService.queryAssChkInHouseStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}

}

