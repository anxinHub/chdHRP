/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.query;

import java.io.File;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.qrcode.MatrixToImageWriter;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.query.MatDuraQueryService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 
 * @Description: 耐用品查询  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatDuraQueryController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraQueryController.class);

	// 引入Service服务
	@Resource(name = "matDuraQueryService")
	private final MatDuraQueryService matDuraQueryService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	/**
	 * @Description 耐用品流转查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/tranPage.do", method = RequestMethod.GET)
	public String tranPage(Model mode) throws Exception {

		return "hrp/mat/dura/query/tran";
	}

	/**
	 * @Description 耐用品流转查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryTran", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryTran(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryDeptUse(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 耐用品在库库存查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/storeStockPage.do", method = RequestMethod.GET)
	public String storeStockPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/query/storeStock";
	}

	/**
	 * @Description 耐用品在库库存查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryStoreStock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryStoreStock(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryStoreStock(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品科室在用量查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/deptUsePage.do", method = RequestMethod.GET)
	public String deptUsePage(Model mode) throws Exception {
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("user_id", SessionManager.getUserId());
		mode.addAttribute("user_msg", matCommonService.getLoginUserMsg(map));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/dura/query/deptUse";
	}

	/**
	 * @Description 耐用品科室在用量查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryDeptUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryDeptUse(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryDeptUse(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品明细账页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/accountStatedPage.do", method = RequestMethod.GET)
	public String accountStatedPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/query/accountStated";
	}

	/**
	 * @Description 耐用品明细账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryAccountStated", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryAccountStated(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryAccountStated(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 全院耐用品数量分布页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/distributionPage.do", method = RequestMethod.GET)
	public String distributionPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/query/distribution";
	}

	/**
	 * @Description 全院耐用品数量分布
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryDistribution", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryDistribution(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryDistribution(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品库存查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/stockPage.do", method = RequestMethod.GET)
	public String aueryStockPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));

		return "hrp/mat/dura/query/stock";
	}

	/**
	 * @Description 耐用品库存查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryStock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryStock(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryStock(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品收发存报表页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/balanceReportPage.do", method = RequestMethod.GET)
	public String balanceReportPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/query/balanceReport";
	}

	/**
	 * @Description 耐用品收发存报表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryBalanceReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryBalanceReport(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryBalanceReport(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品五五摊销报表页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/amortizePage.do", method = RequestMethod.GET)
	public String amortizePage(Model mode) throws Exception {

		return "hrp/mat/dura/query/amortize";
	}

	/**
	 * @Description 耐用品五五摊销报表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryAmortize", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryAmortize(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryAmortize(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品领用查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/receptionPage.do", method = RequestMethod.GET)
	public String receptionPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));

		return "hrp/mat/dura/query/reception";
	}

	/**
	 * @Description 耐用品报废明细表科室
	 * @param mode
	 * @return String
	 * @throws Exception
	 */ 
	@RequestMapping(value = "/hrp/mat/dura/query/scrapDetailPage.do", method = RequestMethod.GET)
	public String scrapDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/query/scrapDetail";
	}
	/**
	 * @Description 耐用品报废明细表仓库
	 * @param mode
	 * @return String
	 * @throws Exception
	 */ 
	@RequestMapping(value = "/hrp/mat/dura/query/scrapStoreDetailPage.do", method = RequestMethod.GET)
	public String scrapStoreDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/query/scrapStoreDetail";
	}
	/**
	 * @Description  耐用品报废明细表查询科室
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryScrapDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryScrapDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryScrapDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	
	/**
	 * @Description  耐用品报废明细表查询仓库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryScrapDetailStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryScrapDetailStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryScrapDetailStore(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 耐用品领用查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryReception", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryReception(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matDuraQueryService.queryMatDuraQueryReception(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
	
	@RequestMapping(value = "/hrp/mat/dura/query/matInvSelectorPage", method = RequestMethod.GET)
	public String accSubjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData ) throws Exception {
	 
			mode.addAttribute("listBoxData", new String((listBoxData.toString()).getBytes("iso-8859-1"),"utf-8"));
		  
		return "hrp/mat/dura/query/matDuraSelector";
	}
	
	//科目tree
	@RequestMapping(value = "/hrp/mat/dura/query/queryInvBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySubjBySelector(
			@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String inv = matDuraQueryService.queryInvBySelector(mapVo);
		response.getWriter().print(inv);
		return null;
	}
	
	/**
	 * @Description 耐用品二维码查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/qrcodePage.do", method = RequestMethod.GET)
	public String qrcodePage(Model mode) throws Exception {
		
		return "hrp/mat/dura/query/qrcode";
	}
	
	/**
	 * @Description 耐用品二维码查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/queryMatDuraQueryQrCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraQueryQrCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson = matDuraQueryService.queryMatDuraQueryQrCode(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 耐用品二维码生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/query/createMatDuraQrCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createMatDuraQrCode(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			int width = 250;
	        int height = 250;
	        // 二维码的图片格式
	        String format = "png";
	        Hashtable<EncodeHintType,String> hints = 
	                                      new Hashtable<EncodeHintType,String>();
	        // 内容所使用编码
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	        
	        // 获取系统路径
	        String url = this.getClass().getResource("").getPath();
			int subLen = url.indexOf("WEB-INF");
			String path = url.substring(0, subLen);
			// 判断路径是否存在 
			String truePath = path + "\\" + SessionManager.getGroupId() + "\\" +
		        		SessionManager.getHosId() + "\\" + SessionManager.getCopyCode() + "\\mat\\dura";
			File file = new File(truePath);
			// 不存在则创建
			if(!file.exists()){
				file.mkdirs();
			}
			
			String[] invMap = mapVo.get("ParamVo").toString().split(",");
			for(String inv : invMap){
				String[] invInfo = inv.split("@");
				
				String text = "材料编码:" + invInfo[0] 
						    + "材料名称:" + invInfo[1]
						    + "进价:" + invInfo[2]
						    + "条码:" + invInfo[3];
				
		        BitMatrix bitMatrix = new MultiFormatWriter()
		                  .encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		        // 生成二维码
		        File outputFile = new File(truePath+ "\\"+ invInfo[3] +".png");
		        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			}
			
			retMap.put("state", "true");
			retMap.put("true", "生成成功！");
		}catch(Exception e){
			retMap.put("state", "false");
			retMap.put("error", "生成失败！");
		}
		
		return retMap;
	}
}
