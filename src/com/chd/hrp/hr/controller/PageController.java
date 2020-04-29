package com.chd.hrp.hr.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.service.HosCommonService;
import com.chd.hrp.hr.service.QueryService;
import com.chd.hrp.hr.service.sc.HrTableStrucService;



/**
 * 
 * 默认拦截路径
 * nm	护理管理
 * os	组织架构
 * pf	人事档案
 * pt	人事调动
 * am	考勤管理
 * srm	科研管理
 * em	教学管理
 * tm	培训管理
 * mm	医务管理
 * hm	院办管理
 * pom	党办管理
 * lm	后勤管理
 * sm	薪资管理
 * cm	合同管理
 * rm	招聘管理
 * 
 * 此路径为人力系统所有业务处理路径根目录 对应hr_table_type表数据 
 * 如果有新增数据 也在此处增加默认
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class PageController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PageController.class);
	
	@Resource(name = "queryService")
	private final QueryService queryService = null;

	// 引入Service服务
	@Resource(name = "hrTableStrucService")
	private final HrTableStrucService hrTableStrucService = null;
	
	@Resource(name = "hosCommonService")
	private final HosCommonService hosCommonService = null;
	
	private static String[] menuNamePrefix = {"add","update","delete","import","export","print"};//按钮菜单前缀

	/**
	 * @Description 主页面跳转
	 * @param hrp/hr/os/basestation/baseStationMainPage.do 
	 * 	      eg:hrp/hr/os/ 会被跟拦截 
	 * 	      eg:basestation/baseStationMainPage.do 会被query方法拦截
	 * @return basestation 为view/hr/os目录下 目录 baseStationMain 为JSP文件
	 * @throws Exception
	 */
	@RequestMapping(value="{*}/{*}MainPage",method = RequestMethod.GET)
	public String mainPage(HttpServletRequest request,Model mode) throws Exception {
		String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
		servletPath = servletPath.replace("Page.do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
				   				
		
        //mode中放入一些基本参数 如果页面使用可以随时调用
		mode.addAttribute("group_id",  SessionManager.getGroupId());
		mode.addAttribute("hos_id",    SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("user_id",   SessionManager.getUserId());
		if(servletPath.indexOf("/pf/")!=-1){
		Map<String, Object> mapVo= new HashMap<String, Object>();
		mapVo.put("table_code", "HOS_EMP");
		mapVo.put("group_id",  SessionManager.getGroupId());
		mapVo.put("hos_id",    SessionManager.getHosId());
		mapVo.put("user_id",   SessionManager.getUserId());
		
		// 权限控制只读
		List<Map<String, Object>> userPerms = hosCommonService.queryHosUserPermByUserId(mapVo);
		if (userPerms != null && userPerms.size() > 0) {
			Map<String, Object> userPerm = userPerms.get(0);
			mode.addAttribute("is_perm", MyConfig.getSysPara("06005")/*MyConfig.getSysPara("06005")*/);
			mode.addAllAttributes(userPerm);
		}
         mode.addAttribute("main_select", MyConfig.getSysPara("06006"));
		}
		
		List<String> li = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		li = (List<String>) SessionManager.getSession().getAttribute("user_perm");

		
		//判断操作是否有使用权限 例如 add update delete 
		String menuNameSuffix = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length())//示例 baseStationMain
								.replace("Main", "");                                                     //baseStation
		
		menuNameSuffix = menuNameSuffix.substring(0,1).toUpperCase()+menuNameSuffix.substring(1);//处理首字母大写 示例//BaseStation
	  //判断是否有权限
		if (li != null && li.size() > 0) {

			for (String s : li) {

				if (s.indexOf(menuNameSuffix) !=-1) {

					list.add(s);

				}

			}

		}
/*		for(String tmpStr : menuNamePrefix) {
			
			if(SessionManager.existsUserPerm(tmpStr+menuNameSuffix)){//tmpStr+menuNameSuffix 示例 add + BaseStation
				
			
				
			}else{
				
				mode.addAttribute(tmpStr+menuNameSuffix+"Display", false);//示例 add + BaseStation + Display
				
			}
			
		}*/
for (String string : list) {
	mode.addAttribute(string, false);//示例 add + BaseStation + Display
}
		return servletPath;
		
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "{*}/{*}AddPage", method = RequestMethod.GET)
	public String BaseAddPage(HttpServletRequest request,Model mode) throws Exception {
	String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
		servletPath = servletPath.replace("Page.do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
				   				
		
        //mode中放入一些基本参数 如果页面使用可以随时调用
		mode.addAttribute("group_id",  SessionManager.getGroupId());
		mode.addAttribute("hos_id",    SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("user_id",   SessionManager.getUserId());
		
	
		return servletPath;
	}

	

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "{*}/{*}UpdatePage", method = RequestMethod.GET)
	public String BaseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request) throws Exception {

		String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
		servletPath = servletPath.replace("Page.do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
	String	path = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()).replace("Update", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
			
		   //mode中放入一些基本参数 如果页面使用可以随时调用
				mode.addAttribute("group_id",  SessionManager.getGroupId());
				mode.addAttribute("hos_id",    SessionManager.getHosId());
				mode.addAttribute("copy_code", SessionManager.getCopyCode());
				mode.addAttribute("user_id",   SessionManager.getUserId());
				mapVo.put("design_code", "queryByCode"+path.substring(0,1).toUpperCase()+path.substring(1));
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("user_id", SessionManager.getUserId());
				
		Map<String, Object> map = queryService.queryByCode(mapVo);
		
		String tableColumn=hrTableStrucService.queryTabColsByCode(mapVo);
	       List<HrTableColumn> listObjectFir = JSON.parseArray(JSON.parseObject(tableColumn).getString("Rows"), HrTableColumn.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 for(String key : map.keySet()){
      		String value1 = String.valueOf(map.get(key)) ;
      		if(!value1.equals("null")){
   			   mode.addAttribute(key,value1);

      		}

				}
        	if(map!=null){
        		   for (HrTableColumn hrTableColumn : listObjectFir) {
        			   String value = String.valueOf(map.get(hrTableColumn.getCol_code().toLowerCase())) ;
        				 if(hrTableColumn.getData_type_code().equals("DATE")&&!value.equals("null")){
                    			   mode.addAttribute(hrTableColumn.getCol_code().toLowerCase(),sdf.format( sdf.parse(map.get(hrTableColumn.getCol_code().toLowerCase()).toString())));
 
              		   }
              			   }
              		   
        			}
        	JSONObject json = new JSONObject();
    		json.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
    		 ValueFilter filter = new ValueFilter() {
 	            @Override
 	            public Object process(Object object, String name, Object value) {
 	                if(value instanceof BigDecimal || value instanceof Double || value instanceof Float){
 	                    return new BigDecimal(value.toString());
 	                }
 	                return value;
 	            }
 	     };
			mode.addAttribute("data", json.toJSONString(map,filter,new SerializerFeature[0]));

    		return servletPath;
	}


}