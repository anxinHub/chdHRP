package com.chd.hrp.ass.controller.tend;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.transformer.DateToString;
import org.jasypt.commons.CommonUtils;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.DateUtil;
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.tend.AssTendDetail;
import com.chd.hrp.ass.entity.tend.AssTendFile;
import com.chd.hrp.ass.entity.tend.AssTendMain;
import com.chd.hrp.ass.service.apply.AssApplyDeptService;
import com.chd.hrp.ass.service.tend.AssTendService;
import com.chd.hrp.mat.entity.MatVenCertDetail;

/**
 * 招标管理
 * @author cyw
 *
 */
@Controller
public class AssTendController extends BaseController{
	@Resource(name="assTendService")
	private final AssTendService assTendService=null;
	
	// 引入Service服务
	@Resource(name = "assApplyDeptService")
	private final AssApplyDeptService assApplyDeptService = null;
	
	/**
	 * 主页跳转
	 * @param model
	 * @return
	 */
  @RequestMapping(value="/hrp/ass/asstend/asstendMainPage",method=RequestMethod.GET)
	public String asstendMainPage(Model model){
	  model.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/asstend/asstendMainPage";
	}
  /**
   * 采购申请单页面跳转
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/assTendApplyPage",method=RequestMethod.GET)
	public String assTendApplyPage(Model model){
	  model.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/asstend/asstendaddapply";
	}
  /**
   * 设备招标单明细添加页面跳转
   * @param bid_id
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/assTendDetailAddPage",method=RequestMethod.GET)
  public String assTendDetailAdd(@RequestParam ("bid_id")String bid_id,Model model){
	  model.addAttribute("bid_id", bid_id);
	  return "hrp/ass/asstend/assTendDetailAdd";
  }
  
  /**
   * 设备招标单明细更新页面跳转
   * @param bid_id
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/assTendDetailUpdatePage",method=RequestMethod.GET)
  public String assTendDetailUpdate(@RequestParam Map<String, Object> mapVo,Model model){
	  
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	  AssTendDetail assTendDetail=assTendService.queryAsstendDetaill(mapVo);
	  
	  model.addAttribute("ass_name", assTendDetail.getAss_name());
	  model.addAttribute("ass_id",assTendDetail.getAss_id() );
	  model.addAttribute("proj_name", assTendDetail.getPrj_name());
	  model.addAttribute("bidd_budgno", assTendDetail.getBidd_budgno());
	  model.addAttribute("bidd_budgprice",assTendDetail.getBidd_budgprice());
	  model.addAttribute("bidd_budemoney",  (assTendDetail.getBidd_budgno())*(assTendDetail.getBidd_budgprice()));
	  
	  model.addAttribute("ass_brand",assTendDetail.getBidd_price());
	  model.addAttribute("bidd_budgfunction", assTendDetail.getBidd_budgfunction());
	  model.addAttribute("bidd_budgreachdate",DateUtil.dateToString(assTendDetail.getBidd_budgreachdate(), "yyyy-MM-dd"));
	  model.addAttribute("bidd_price", assTendDetail.getBidd_price());
	  model.addAttribute("bidd_no",assTendDetail.getBidd_no());
	  model.addAttribute("bidd_value", assTendDetail.getBidd_value());
	  model.addAttribute("bidd_budgevaluation", assTendDetail.getBidd_budgevaluation());
	  model.addAttribute("fac_id", assTendDetail.getFac_id());
	  model.addAttribute("fac_name", assTendDetail.getFac_name());
	  model.addAttribute("bidd_budgid", assTendDetail.getBidd_budgid());
	  model.addAttribute("bidd_budgidname", assTendDetail.getBidd_budgidname());
	  model.addAttribute("bid_id",mapVo.get("bid_id") );
	  model.addAttribute("detail_id", mapVo.get("detail_id"));
	  return "hrp/ass/asstend/assTendDetailUpdate";
  }
  /**
   * 明细页面跳转
   * @param bid_id  主表ID
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/assTendDetailPage",method=RequestMethod.GET)
  public String assTendDetailPage(@RequestParam ("bid_id") String bid_id,@RequestParam ("bid_state") String bid_state,Model model){
	  //页面跳转的同时查出主表信息并且返回
	  Map<String, Object> mapVo=new HashMap<String, Object>();
	  mapVo.put("bid_id",bid_id );
	  mapVo.put("bid_state",bid_state );
	  mapVo.put("group_id", SessionManager.getGroupId());
	  mapVo.put("hos_id", SessionManager.getHosId());
	  mapVo.put("copy_code", SessionManager.getCopyCode());
	  AssTendMain assTendMain=assTendService.queryassTendMain(mapVo);
	  if(assTendMain!=null){
	  model.addAttribute("bid_state", bid_state) ;
	  model.addAttribute("bid_id", assTendMain.getBid_id());
	  model.addAttribute("bid_code", assTendMain.getBid_code());
	  model.addAttribute("bid_method", assTendMain.getBid_method());
	  model.addAttribute("bid_tenderee", assTendMain.getBid_tenderee());
	  model.addAttribute("bid_tenderaddress", assTendMain.getBid_tenderaddress());
	  model.addAttribute("bid_phone", assTendMain.getBid_phone());
	  model.addAttribute("bid_purchasemode", assTendMain.getBid_purchasemode());
	  model.addAttribute("value_name", assTendMain.getValue_name());
	  model.addAttribute("bid_bond", assTendMain.getBid_bond());
	  model.addAttribute("bid_condition", assTendMain.getBid_condition());
	  model.addAttribute("bid_purstart", DateUtil.dateToString(assTendMain.getBid_purstart(), "yyyy-MM-dd"));
	  model.addAttribute("bid_purend", DateUtil.dateToString(assTendMain.getBid_purend(), "yyyy-MM-dd"));
	  model.addAttribute("bid_puraddr", assTendMain.getBid_puraddr());
	  model.addAttribute("bid_notice", assTendMain.getBid_notice());
	  model.addAttribute("bid_agente",assTendMain.getBid_agent());
	  model.addAttribute("bid_agenter", assTendMain.getBid_agenter());
	  model.addAttribute("bid_agentphone", assTendMain.getBid_agentphone());
	  model.addAttribute("bid_openplace", assTendMain.getBid_openplace());
	  model.addAttribute("bid_answerdate", DateUtil.dateToString(assTendMain.getBid_answerdate(), "yyyy-MM-dd"));
	  model.addAttribute("bid_calibratedate", DateUtil.dateToString(assTendMain.getBid_calibratedate(), "yyyy-MM-dd") );
	  model.addAttribute("bid_noticedate", DateUtil.dateToString(assTendMain.getBid_noticedate(), "yyyy-MM-dd") );
	  model.addAttribute("bid_start",DateUtil.dateToString( assTendMain.getBid_start(), "yyyy-MM-dd"));
	  model.addAttribute("bid_end", DateUtil.dateToString( assTendMain.getBid_end(), "yyyy-MM-dd"));
	  model.addAttribute("bid_committee",assTendMain.getBid_committee());
  
	  }else{
		  model.addAttribute("bid_state", bid_state) ;
		  model.addAttribute("bid_id", bid_id);
	  }
	  return "hrp/ass/asstend/asstenddetail";
  }
  /**
   * 上传页面跳转
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/upLodePage",method=RequestMethod.GET)
  public String upLodePage(@RequestParam Map<String, Object> mapVo,Model model){
	  
	  
	  model.addAttribute("bid_id", mapVo.get("bid_id"));
	  
	  return "hrp/ass/asstend/upLode";
  }
  
  
  /**
   * 文件页面跳转
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/assTendFilePage",method=RequestMethod.GET)
  public String assTendFilePage(@RequestParam Map<String, Object> mapVo,Model model){
	  
	  
	  model.addAttribute("bid_id", mapVo.get("bid_id"));
	  
	  return "hrp/ass/asstend/asstendFile";
  }
  
  /**
   * 文件页面跳转
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/assTendAddPage",method=RequestMethod.GET)
  public String assTendAddPage(@RequestParam Map<String, Object> mapVo,Model model){

	  return "hrp/ass/asstend/asstendAdd";
  }
  /**
   * 采购方式下拉框
   * @param mapvo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/queryTendMethod" ,method=RequestMethod.POST)
  @ResponseBody
  public String queryTendMethod(@RequestParam Map<String, Object> mapVo,Model model){
	  
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  return assTendService.queryTendMethod(mapVo);
  }
  
  /**
   * 主页面查询方法
   * @param mapvo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/queryAsstendMain" ,method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> queryAsstendMain(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	  return JSONObject.parseObject(assTendService.queryAsstendMain(getPage(mapVo)));
  }
  
  /**
   * 明细查询方法
   * @param mapvo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/queryAsstendDetail" ,method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> queryAsstendDetail(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	  return JSONObject.parseObject(assTendService.queryAsstendDetail(getPage(mapVo)));
  }
  
  /**
   * 删除
   * @param Param
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/deleteTend",method=RequestMethod.POST)
  @ResponseBody
  public String deleteTend(@RequestParam(value="Param") String Param,Model model){
	  List<Map<String, Object>> listvo=new ArrayList();
	  String[] arrayOfString;
	   int j = (arrayOfString = Param.split(",")).length;
	   
	    for (int i = 0; i < j; i++)
	    {
	      String detail = arrayOfString[i];
	      Map<String, Object> mapVo = new HashMap();
	      mapVo.put("group_id", detail.split("@")[0]);
	      mapVo.put("hos_id", detail.split("@")[1]);
	      mapVo.put("copy_code", detail.split("@")[2]);
	      mapVo.put("bid_id", detail.split("@")[3]);
	      mapVo.put("bid_code", detail.split("@")[4]);
	      
	      listvo.add(mapVo);
	    }
	    String res=assTendService.deleteTend(listvo);
	   return res;
  }
  
 /**
  * 删除明细
  * @param Param
  * @param model
  * @return
  */
  @RequestMapping(value="/hrp/ass/asstend/deleteTendDetail",method=RequestMethod.POST)
  @ResponseBody
  public String deleteTendDetail(@RequestParam(value="Param") String Param,Model model){
	  List<Map<String, Object>> listvo=new ArrayList();
	  String[] arrayOfString;
	   int j = (arrayOfString = Param.split(",")).length;
	   
	    for (int i = 0; i < j; i++)
	    {
	      String detail = arrayOfString[i];
	      Map<String, Object> mapVo = new HashMap();
	      mapVo.put("group_id", detail.split("@")[0]);
	      mapVo.put("hos_id", detail.split("@")[1]);
	      mapVo.put("copy_code", detail.split("@")[2]);
	      mapVo.put("bid_id", detail.split("@")[3]);
	      mapVo.put("detail_id", detail.split("@")[4]);
	      System.out.println(mapVo);
	      listvo.add(mapVo);
	    }
	    String res=assTendService.deleteTendDetail(listvo);
	   return res;
  }
  
  /**
   * 新增
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/savetendInfomation",method=RequestMethod.POST)
  @ResponseBody
  public String savetendInfomation(@RequestParam Map<String, Object> mapVo,Model model){
	  
	  //System.out.println(mapVo); 
	  String res=assTendService.savetendInfomation(mapVo);
	  return res;
  }
  /**
   * 设备招标单更新方法
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/updateAssTendMain",method=RequestMethod.POST)
  @ResponseBody
  public String updateAssTendMain(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	  return assTendService.updateAssTendMain(mapVo);
  }
  /**
   * 提交
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/submitAssTend",method=RequestMethod.POST)
  @ResponseBody
  public String submitAssTend(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	    String res=assTendService.updateSubmit(mapVo);
	   return res;
  }
  /**
   * 添加明细表
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/addAssTendDetail",method=RequestMethod.POST)
  @ResponseBody
  public String addAssTendDetail(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	    
	  return assTendService.addAssTendDetail(mapVo);
  }
  
  /**
   * 添加明细表
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/updateAssTendDetail",method=RequestMethod.POST)
  @ResponseBody
  public String updateAssTendDetail(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	    
	  return assTendService.updateAssTendDetail(mapVo);
  }
  /**
   * 资产下拉框
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/queryAssDict",method=RequestMethod.POST)
  @ResponseBody
  public String queryAssDict(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	  return assTendService.queryAssDict(mapVo);
  }
  
  /**
   * 厂家下拉框
   * @param mapVo
   * @param model
   * @return
   */
  @RequestMapping(value="/hrp/ass/asstend/queryAssFac",method=RequestMethod.POST)
  @ResponseBody
  public String queryAssFac(@RequestParam Map<String, Object> mapVo,Model model){
	  if (mapVo.get("group_id") == null) {
	      mapVo.put("group_id", SessionManager.getGroupId());
	    }
	    if (mapVo.get("hos_id") == null) {
	      mapVo.put("hos_id", SessionManager.getHosId());
	    }
	    if (mapVo.get("copy_code") == null) {
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    }
	  
	  return assTendService.queryAssFac(mapVo);
  }
  

  
 	//上传文件
 	@RequestMapping(value="/hrp/ass/asstend/upLodeFile", method = RequestMethod.POST)  
 	 public String upLodeFile(Plupload plupload,HttpServletRequest request,
 	    		HttpServletResponse response,@RequestParam Map<String, Object> mapVo) throws IOException, DataAccessException, ParseException { 
 		String result = "";
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		
 		
 		String basePath = "assFile/tendFile/tend/"+mapVo.get("bid_id") +"/";
 		
 		String fileName = plupload.getName();// 文件原名称
 		
 		// 文件保存目录url
 		String saveUrl = request.getContextPath() + "/" + basePath;

 		String url = saveUrl + fileName;

 			List<File> fileList = UploadUtil.upload(plupload, basePath,request, response);
 			
 			result = "{\"file_path\":\""+url.replaceAll("\\\\","\\\\\\\\")+"\",\"state\":\"true\"}";

 			//System.out.println(url);
 			///更新文件管理表
 			mapVo.put("file_path", url);	
 			mapVo.put("file_name", fileName);	
 			//查看文件是否已经存在
 			List<AssTendFile> list=assTendService.queryAssTendFileExit(mapVo);
 		if(list.size()==0){
 			assTendService.addAssTendFile(mapVo);
 			}
 		response.getWriter().print(result);
 	    return null; 
 	 }
	/**
	 * 文件查询列表
	 * @param mapVo
	 * @param model
	 * @return
	 */
 	@RequestMapping(value="/hrp/ass/asstend/queryAssTendFile",method=RequestMethod.POST)
 	@ResponseBody
 	public Map<String, Object> queryAssTendFile(@RequestParam Map<String, Object> mapVo,Model model){
 		
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		
 		String file=assTendService.queryAssTendFile(getPage(mapVo));
 		return JSONObject.parseObject(file);
 	}
 	/**
 	 * 删除文件
 	 * @param mapVo
 	 * @param model
 	 * @return
 	 */
 	@RequestMapping(value="/hrp/ass/asstend/deleteTendFile",method=RequestMethod.POST)
 	@ResponseBody
 	public String deleteTendFile(@RequestParam Map<String, Object> mapVo,Model model){
 		
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		String basePath = "assFile/tendFile/tend/"+mapVo.get("bid_id") +"/";
 		String file_name=mapVo.get("file_name").toString();
 		FileUtil.deleteFile( LoadSystemInfo.getProjectUrl()+basePath+file_name);
 		String res=assTendService.deleteTendFile(mapVo);
 		return res;
 	}
 /**
  * 招标主表新增方法
  * @param mapVo
  * @param model
  * @return
  */
 	@RequestMapping(value="/hrp/ass/asstend/addAssTendMain",method=RequestMethod.POST)
 	@ResponseBody
 	public String addAssTendMain(@RequestParam Map<String, Object> mapVo,Model model){
 		
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		String res=assTendService.addAssTendMain(mapVo);
 		return res;
 	}
 	/**
 	 * 全选按钮功能
 	 * @param mapVo
 	 * @param model
 	 * @return
 	 */
 	@RequestMapping(value="/hrp/ass/asstend/queryassTendcheckAll",method=RequestMethod.POST)
 	@ResponseBody
 	public String queryassTendcheckAll(@RequestParam Map<String, Object> mapVo,Model model){
 		if(mapVo.get("group_id") == null){
 			mapVo.put("group_id", SessionManager.getGroupId());
 		}
 		if(mapVo.get("hos_id") == null){
 			mapVo.put("hos_id", SessionManager.getHosId());
 		}
 		if(mapVo.get("copy_code") == null){
 			mapVo.put("copy_code", SessionManager.getCopyCode());
 		}
 		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();

		if (!dept_id_no.equals("")) {

			mapVo.put("dept_id", dept_id_no.split("@")[0]);

			mapVo.put("dept_no", dept_id_no.split("@")[1]);

		}

		String res = assTendService.queryassTendcheckAll(mapVo);
 		//System.out.println(res);
 		return res;
 	}
}
