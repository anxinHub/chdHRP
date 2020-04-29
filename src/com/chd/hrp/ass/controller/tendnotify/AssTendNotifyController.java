package com.chd.hrp.ass.controller.tendnotify;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.tendnotify.AssTendNotifyService;

/**
 * 招标通知书
 * 
 * @author Administrator
 *
 */
@Controller
public class AssTendNotifyController extends BaseController {
	@Resource(name = "assTendNotifyService")
	private AssTendNotifyService assTendNotifyService = null;

	/**
	 * 主页跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hrp/ass/asstendnotify/assTendNotifyMainPage", method = RequestMethod.GET)
	public String asstendMainPage(Model model) {
		model.addAttribute("ass_05005", MyConfig.getSysPara("05005"));
		return "hrp/ass/asstendnotify/assTendNotifyMain";
	}

	/**
	 * 主页面查询方法
	 * 
	 * @param mapvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hrp/ass/asstendnotify/queryAssTendNotifyMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAsstendMain(@RequestParam Map<String, Object> mapVo, Model model) {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String ven_id_no = mapVo.get("ven_id") == null ? "" : mapVo.get("ven_id").toString();
		mapVo.put("ven_id", ven_id_no.split("@")[0]);

		return JSONObject.parseObject(assTendNotifyService.queryAssTendNotifyMain(getPage(mapVo)));
	}

	/**
	 * 上传页面跳转
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendnotify/upLodePage", method = RequestMethod.GET)
	public String upLodePage(@RequestParam Map<String, Object> mapVo, Model mode,
			@RequestParam(value = "bid_id", required = false) String bid_id,
			@RequestParam(value = "is_flag", required = false) String is_flag) throws Exception {
		if (bid_id != null) {
			mode.addAttribute("bid_id", bid_id);
		}
		if (is_flag != null) {
			mode.addAttribute("is_flag", is_flag);
		}
		return "hrp/ass/asstendnotify/upLode";
	}

	/**
	 * 上传文件
	 * 
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mapVo
	 * @return
	 * @throws IOException
	 * @throws DataAccessException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/hrp/ass/asstendnotify/upLodeFile", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String upLodeFile(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bid_id", required = false) String bid_id,
			@RequestParam(value = "is_flag", required = false) String is_flag)
			throws IOException, DataAccessException, ParseException {
		String result = "";
		Map<String, Object> mapVo = new HashMap<>();
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String basePath = "assTendFile/notifyFile/" + bid_id + "/";
		String fileName = plupload.getName();// 文件原名称
		mapVo.put("bid_winfiledr", fileName);
		if (is_flag.equals("01")) {
			mapVo.put("file_type", "中标文件");
			basePath = "assTendFile/notifyFile/" + bid_id + "/";
		}
		if (is_flag.equals("02")) {
			mapVo.put("file_type", "招标文件");
			basePath = "assTendFile/tendFile/" + bid_id + "/";
		}
		// 文件保存目录url
		String saveUrl = request.getContextPath() + "/" + basePath;
		String url = saveUrl + fileName;
		mapVo.put("bid_id", bid_id);
		mapVo.put("file_path", url);
		mapVo.put("file_name", fileName);
		mapVo.put("file_code", "");
		mapVo.put("bid_filedr", fileName);

		int tend = assTendNotifyService.addAssTendFile(mapVo);
		int file = assTendNotifyService.updateAssTendMain(mapVo);

		List<File> fileList = UploadUtil.upload(plupload, basePath, request, response);
		if (tend > 0 && file > 0) {
			result = "{\"file_path\":\"" + url.replaceAll("\\\\", "\\\\\\\\") + "\",\"state\":\"true\"}";
		} else {
			result = "{\"file_path\":\"" + url.replaceAll("\\\\", "\\\\\\\\") + "\",\"state\":\"false\"}";
		}
		return result;
	}

	/**
	 * 主页面查询方法
	 * 
	 * @param mapvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hrp/ass/asstendnotify/queryAssTendFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTendFile(@RequestParam Map<String, Object> mapVo, Model model,
			@RequestParam(value = "bid_id", required = false) String bid_id,
			@RequestParam(value = "is_flag", required = false) String is_flag) {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if ("01".equals(is_flag)) {
			mapVo.put("file_type", "中标文件");
		}
		if ("02".equals(is_flag)) {
			mapVo.put("file_type", "招标文件");
		}
		mapVo.put("bid_id", bid_id);
		return JSONObject.parseObject(assTendNotifyService.queryAssTendFile(mapVo));
	}

	/**
	 * 刪除文件
	 * 
	 * @param Param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hrp/ass/asstendnotify/deleteTendFile", method = RequestMethod.POST)
	@ResponseBody
	public String deleteTendFile(@RequestParam(value = "Param") String Param, Model model) {
		Map<String, Object> mapVo = new HashMap();
		mapVo.put("bid_id", Param.split("@")[0]);
		mapVo.put("file_path", Param.split("@")[1]);
		mapVo.put("file_name", Param.split("@")[2]);
		mapVo.put("file_type", Param.split("@")[3]);
		String basePath = "assTendFile/notifyFile/" + Param.split("@")[0] + "/";
		if ("中标文件".equals(Param.split("@")[3])) {
			basePath = "assTendFile/notifyFile/" + Param.split("@")[0] + "/";
		}
		if ("招标文件".equals(Param.split("@")[3])) {
			basePath = "assTendFile/tendFile/" + Param.split("@")[0] + "/";
		}
 		String file_name=mapVo.get("file_name").toString();
 		Boolean b = FileUtil.deleteFile( LoadSystemInfo.getProjectUrl()+basePath+file_name); //删除文件
 		int sta = assTendNotifyService.deleteTendFile(mapVo);
 		if(b && sta>0) {
 			 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
 		}
 		 return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteTend\"}";
	}

}
