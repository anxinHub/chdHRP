/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.disposal;
 

 
import org.apache.log4j.Logger;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
import com.chd.base.BaseController;
/**
 * 
 * @Description:
 * 051001资产处置主表
 * @Table:
 * ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class AssDisposalSearch extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssDisposalSearch.class);
	
	//引入Service服务
//	@Resource(name = "assDisposalMainService")
//	private final AssDisposalMainService assDisposalMainService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalSearchPage", method = RequestMethod.GET)
	public String assDisposalSearchPage(Model mode) throws Exception {

		return "hrp/ass/assdisposalmain/assDisposalSearch";

	}

	 
	/**
	 * @Description 
	 * 查询数据 051001资产处置主表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
//	@RequestMapping(value = "/hrp/ass/assdisposalmain/queryAssDisposalMain", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> queryAssDisposalMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//		
//	    if(mapVo.get("group_id") == null){
//			
//		mapVo.put("group_id", SessionManager.getGroupId());
//		
//		}
//		
//		if(mapVo.get("hos_id") == null){
//			
//		mapVo.put("hos_id", SessionManager.getHosId());
//		
//		}
//		
//		if(mapVo.get("copy_code") == null){
//			
//		mapVo.put("copy_code", SessionManager.getCopyCode());
//        
//		}
//		if(mapVo.get("acct_year") == null){
//			
//		mapVo.put("acct_year", SessionManager.getAcctYear());
//        
//		}
// 
//	    
//		String assDisposalMain = assDisposalMainService.queryAssDisposalMain(getPage(mapVo));
//
//		return JSONObject.parseObject(assDisposalMain);
//		
//	}
	
   
}

