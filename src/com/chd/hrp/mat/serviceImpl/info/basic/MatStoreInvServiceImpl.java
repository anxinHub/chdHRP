/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.info.basic;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;
import javax.xml.ws.http.HTTPException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.info.basic.MatStoreInvMapper;
import com.chd.hrp.mat.entity.MatStoreInv;
import com.chd.hrp.mat.service.info.basic.MatStoreInvService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 04110 仓库材料信息
 * @Table: MAT_STORE_INV
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("matStoreInvService")
public class MatStoreInvServiceImpl implements MatStoreInvService {

	private static Logger logger = Logger.getLogger(MatStoreInvServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matStoreInvMapper")
	private final MatStoreInvMapper matStoreInvMapper = null;

	/**
	 * @Description 添加04110 仓库材料信息<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addMatStoreInv(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象04110 仓库材料信息
		MatStoreInv matStoreInv = queryMatStoreInvByCode(entityMap);

		if (matStoreInv != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		try {
			int state = matStoreInvMapper.addMatStoreInv(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStoreInv\"}";
		}

	}

	/**
	 * @Description 批量添加04110 仓库材料信息<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchMatStoreInv(List<Map<String, Object>> entityList) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			matStoreInvMapper.deleteBatchMatStoreInv(entityList);

			matStoreInvMapper.addBatchMatStoreInv(entityList);

			// System.out.println("============addB============"+entityList.toString());

			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < entityList.size(); i++) {
				map.put("group_id", entityList.get(i).get("group_id"));
				map.put("hos_id", entityList.get(i).get("hos_id"));
				map.put("copy_code", entityList.get(i).get("copy_code"));
				map.put("inv_id", entityList.get(i).get("inv_id"));
				map.put("store_id", entityList.get(i).get("store_id"));
				if (StringTool.isNotBlank(entityList.get(i).get("location_id"))) {
					map.put("location_id", entityList.get(i).get("location_id"));
					listVo.add(map);
				}
			}

			String code = MyConfig.getSysPara("04035");

			if (Integer.parseInt(code) == 1) {
				if (listVo.size() > 0) {

					// System.out.println("aaaaaaaaaaaaaaaaaaaaa"+listVo);
					matStoreInvMapper.addBatchMatLocationInv(listVo);
				}

			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStoreInv\"}";
		}

	}

	@Override
	public String addMatStoreInvAll(Map<String, Object> entityMap) throws DataAccessException

	{
		try {
			matStoreInvMapper.deleteStoreMatStoreInv(entityMap);

			matStoreInvMapper.addStoreMatStoreInv(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

			// return "{\"error\":\"操作失败\"}";
		}

	};

	/**
	 * @Description 更新04110 仓库材料信息<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateMatStoreInv(Map<String, Object> entityMap) throws DataAccessException {
		try {

			matStoreInvMapper.updateMatStoreInv(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

			// return "{\"error\":\"操作失败\"}";
		}
	}

	/**
	 * @Description 批量更新04110 仓库材料信息<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addSafeMatStoreInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			matStoreInvMapper.updateBatchMatStoreInv(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\" 保存失败\"}");

			// return "{\"error\":\" 保存失败\"}";
		}
	}

	/**
	 * @Description 删除04110 仓库材料信息<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteMatStoreInv(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = matStoreInvMapper.deleteMatStoreInv(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatStoreInv\"}";

		}

	}

	/**
	 * @Description 仓库安全设置 --查询
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String querySafeMatStoreInv(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<MatStoreInv> list = matStoreInvMapper.querySafeMatStoreInv(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<MatStoreInv> list = matStoreInvMapper.querySafeMatStoreInv(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 仓库安全设置 --删除
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteSafeMatStoreInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			matStoreInvMapper.updateBatchMatStoreInv(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStoreInv\"}";
		}
	}

	/**
	 * @Description 批量删除04110 仓库材料信息<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchMatStoreInv(List<Map<String, Object>> entityList) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			// System.out.println("============1============"+entityList.toString());

			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < entityList.size(); i++) {
				map.put("group_id", entityList.get(i).get("group_id"));
				map.put("hos_id", entityList.get(i).get("hos_id"));
				map.put("copy_code", entityList.get(i).get("copy_code"));
				map.put("inv_id", entityList.get(i).get("inv_id"));

				if ("-1".equals(entityList.get(i).get("location_id"))) {

				} else {
					map.put("location_id", entityList.get(i).get("location_id"));
					listVo.add(map);
				}
			}

			// System.out.println(listVo.size()+"============2============"+map.toString());
			matStoreInvMapper.deleteBatchMatStoreInv(entityList);
			String code = MyConfig.getSysPara("04035");
			if (Integer.parseInt(code) == 1) {
				if (listVo.size() > 0) {
					matStoreInvMapper.deleteBatchMatLocationInv(listVo);
				}
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStoreInv\"}";
		}
	}

	/**
	 * @Description 添加04110 仓库材料信息<BR>
	 *              对货位进行操作
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdateMatStoreInv(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象04110 仓库材料信息
		MatStoreInv matStoreInv = queryMatStoreInvByCode(entityMap);

		if (matStoreInv != null) {

			int state = matStoreInvMapper.updateMatLocationInv(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			int state = matStoreInvMapper.addMatLocationInv(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatStoreInv\"}";
		}

	}

	/**
	 * @Description 获取对象04110 仓库材料信息<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public MatStoreInv queryMatStoreInvByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matStoreInvMapper.queryMatStoreInvByCode(entityMap);
	}

	/**
	 * @Description 获取04110 仓库材料信息<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return MatStoreInv
	 * @throws DataAccessException
	 */
	@Override
	public MatStoreInv queryMatStoreInvByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return matStoreInvMapper.queryMatStoreInvByUniqueness(entityMap);
	}

	/**
	 * 根据条件查询 （联合） 物资材料记录
	 * 
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMatStoreInvNew(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = matStoreInvMapper.queryMatStoreInvNew(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = matStoreInvMapper.queryMatStoreInvNew(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatInv(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = matStoreInvMapper.queryMatInv(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = matStoreInvMapper.queryMatInv(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 根据仓库Id 查询 与其存在对应关系的所有物资材料Id
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvStoreId(Map<String, Object> mapVo) throws DataAccessException {
		return matStoreInvMapper.queryMatInvStoreId(mapVo);
	}

	@Override
	public String updateBatchMatStoreInv(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 仓库材料定义 保存材料
	 */
	@Override
	public String addMatStoreInvCert(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> invList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> locationList = new ArrayList<Map<String, Object>>();
			String user_id = SessionManager.getUserId();
			Date date = new Date();
			if (entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))) {

				String[] params = entityMap.get("detailData").toString().split("@");

				for (String param : params) {

					Map<String, Object> invMap = new HashMap<String, Object>();
					invMap.put("group_id", entityMap.get("group_id"));
					invMap.put("hos_id", entityMap.get("hos_id"));
					invMap.put("copy_code", entityMap.get("copy_code"));
					invMap.put("inv_id", param.split(",")[0]);// 材料ID
					invMap.put("is_apply", param.split(",")[1]);// 是否申领
					invMap.put("store_id", entityMap.get("store_id"));
					invMap.put("oper", user_id);
					invMap.put("oper_date", date);
					invList.add(invMap);
				}

				for (String pm : params) {

					Map<String, Object> locationMap = new HashMap<String, Object>();
					locationMap.put("group_id", entityMap.get("group_id"));
					locationMap.put("hos_id", entityMap.get("hos_id"));
					locationMap.put("copy_code", entityMap.get("copy_code"));
					locationMap.put("inv_id", pm.split(",")[0]);// 材料ID
					locationMap.put("store_id", entityMap.get("store_id"));
					locationMap.put("location_id", pm.split(",")[2]);// 货位ID
					locationList.add(locationMap);
				}

			}

			if (invList.size() > 0) {

				matStoreInvMapper.addBatchMatStoreInv(invList);
			}
			String code = MyConfig.getSysPara("04035");
			// System.out.println(">>>>>>>>>"+code);
			if (Integer.parseInt(code) == 1) {
				if (locationList.size() > 0) {
					matStoreInvMapper.addBatchMatLocationInv(locationList);
				}
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInvCert\"}";
		}
	}

	/**
	 * 仓库材料定义 选择材料 全部
	 */
	@Override
	public String queryMatInvAllList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = matStoreInvMapper.queryMatInvAllList(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matStoreInvMapper.queryMatInvAllList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 判断材料是否已经存在于仓库中
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String existsMatInvInStore(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String inv_ids = matStoreInvMapper.existsMatInvInStore(entityMap);
			if (inv_ids == null || "".equals(inv_ids)) {
				return "{\"state\":\"true\"}";
			} else {
				return "{\"state\":\"false\",\"inv_ids\":\"" + inv_ids + "\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInvInStore\"}";
		}
	}

	/**
	 * 查询材料申领科室
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatInvApplyStore(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list = matStoreInvMapper.queryMatInvApplyStore(entityMap);

			int flag = 0;

			for (int x = 0; x < list.size(); x++) {

				String store_id = list.get(x).get("store_id").toString();

				if (!entityMap.get("store_id").toString().equals(store_id)) {

					flag = 1;

					break;
				}
			}

			if (flag == 0) {
				return "{\"state\":\"true\"}";
			} else {
				return "{\"state\":\"false\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvApplyStore\"}";
		}
	}

	/**
	 * 设置默认申领仓库
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public String updateMatInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {

			matStoreInvMapper.updateMatInvApplyStore(entityMap);

			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage());

			throw new SysException("{\"error\":\"操作失败 \"}");
		}

	}

	/**
	 * 取消设置默认申领仓库
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public String updateCancelMatInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {

			matStoreInvMapper.updateMatInvApplyStore(entityMap);

			return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage());

			throw new SysException("{\"error\":\"操作失败 \"}");
		}

	}

	/**
	 * 同步申请仓库到HIS 通过参数控制是否同步 04075 新增关系
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryMatInvApplyStoreByHisAdd(Map<String, Object> entityMap)
			throws DataAccessException {

		return matStoreInvMapper.queryMatInvApplyStoreByHisAdd(entityMap);

	}

	/**
	 * 同步申请仓库到HIS 通过参数控制是否同步 04075 删除关系
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryMatInvApplyStoreByHisDel(Map<String, Object> entityMap)
			throws DataAccessException {

		return matStoreInvMapper.queryMatInvApplyStoreByHisDel(entityMap);

	}

	/**
	 * 用于his同步数据
	 * 
	 * @param inv_ids
	 *            材料id集合
	 * @param store_id
	 *            申请仓库
	 * @param flag
	 *            1：增加 2 删除
	 * @return
	 */
	@Override
	public LinkedHashMap<String, Object> wbPass(String inv_ids, String store_id, int flag) {
		// 用于HIS信息同步 由参数 04075 控制是否传递
		// *****************************开始**************************//
		/**
		 * 同步材料到第三方平台，方式webservice 业务：删除材料 要求：传递的参数顺序要与接口的入参顺序一致，即用LinkHashMap 存储
		 */

		if (("1").equals(MyConfig.getSysPara("04075"))) {

			String invs = inv_ids.substring(0, inv_ids.length() - 1);
			LinkedHashMap<String, Object> wbMap = new LinkedHashMap<String, Object>();
			Map<String, Object> vmap = new HashMap<String, Object>();
			vmap.put("group_id", SessionManager.getGroupId());
			vmap.put("hos_id", SessionManager.getHosId());
			vmap.put("copy_code", SessionManager.getCopyCode());
			vmap.put("store_id", store_id);
			vmap.put("inv_ids", invs);

			List<Map<String, Object>> hisList = new ArrayList<Map<String, Object>>();
			if (flag == 1) {
				hisList = matStoreInvMapper.queryMatInvApplyStoreByHisAdd(vmap);
			}
			if (flag == 2) {
				hisList = matStoreInvMapper.queryMatInvApplyStoreByHisDel(vmap);
			}
			
			//由于his 解析json 不方便 需要转换成全是字符串的方式  可根据各医院的HIS技术水平 特殊处理
			List<Map<String, Object>> hisListNew = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : hisList) {
				Map<String, Object> m=new HashMap<String, Object>();
				for (String key : map.keySet()) {
					m.put(key, Lang.isEmpty(map.get(key))?"":map.get(key).toString());
				}
				
				hisListNew.add(m);
			}
			

			String json = Json.toJson(hisList, JsonFormat.tidy());
			json = json.replace("[", "").replace("]", "");
			wbMap.put("sysname", "HRP");
			wbMap.put("methodname", "HRP物资字典同步");
			wbMap.put("paratype", "JSON");
			wbMap.put("paravalue", json);
			
		    logger.debug(wbMap);
		
			wbMatInv(wbMap);
			
		}

		// *****************************结束**************************//
		return null;
	}

	/**
	 * 通过webservice 同步材料字典给第三方平台 业务包含：添加 修改 删除
	 * 
	 * @param entityMap
	 *            传递的数据参数 采用LinkedHashMap 确保插入的顺序保持一致
	 * 
	 * @return
	 */
	public String wbMatInv(LinkedHashMap<String, Object> entityMap) {
		logger.debug("进入方法");
		long beginTime = System.currentTimeMillis(); // 开始时间

		Object[] paras = new Object[entityMap.size()];
		int count = 0;
		for (String key : entityMap.keySet()) {
			paras[count] = entityMap.get(key);
			count = count + 1;
		}
	
		PropertiesProxy conf = Mvcs.ctx().getDefaultIoc().get(PropertiesProxy.class, "conf");
		
		String url = conf.get("wb.url");
		logger.debug("获取his发布的webservice地址：" + url);
		Object[] result = null;
		if (!Strings.isEmpty(url)) {

			try {
				// 动态构建服务端服务类
				JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
				Client client = clientFactory.createClient(url);

				long endTime = System.currentTimeMillis(); // 开始时间
				/**
				 * 动态调用服务端接口方法 invoke(方法名,方法参数 可多个参数 。。。)
				 */
				logger.debug("请求方法：Jney");
				logger.debug("请求参数："+Json.toJson(paras));

				result = client.invoke("Jney", paras);

				// 返回结果

				long end = System.currentTimeMillis(); // 开始时间
				long re_q = endTime - beginTime;
				long re_p = end - endTime;
				long sum = end - beginTime;
				logger.debug("调用请求时间：" + re_q + " 毫秒");
				logger.debug("调用响应时间：" + re_p + " 毫秒");
				logger.debug("调用请求响应总时间：" + sum + " 毫秒");
				logger.debug("HIS返回信息："+Json.toJson(result));
				
				return (Lang.isEmpty(result) || "".equals(result)) ? null : result[0].toString();

			} catch (Exception e) {

				logger.error(e.getMessage());

				Throwable ta = e.getCause();
				if (ta instanceof SocketTimeoutException) {
					logger.equals("响应超时");
				} else if (ta instanceof HTTPException) {
					logger.equals("服务器地址无效404");
				} else if (ta instanceof XMLStreamException) {
					logger.equals("连接超时");
				} else {
					logger.equals("其他异常");
				}

				throw new SysException("{\"error\":\"HIS同步失败 \"}");
			}

		} else {
		
			return null;
		}

	}
	/**
	 * 用于his同步数据
	 * @param entityMap
	 * 需要更新的材料字典信息
	 * @return
	 */
	public LinkedHashMap<String, Object> wbPassUpdateInv(Map<String, Object> entityMap) {
		
		List<Map<String, Object>> list = matStoreInvMapper.queryMatInvApplyStore(entityMap);
		
		for (Map<String, Object> map : list) {
			String inv_ids=entityMap.get("inv_ids").toString()+",";
			String store_id=map.get("store_id").toString();
			
			return wbPass( inv_ids,  store_id,  1);
		}
		
		return null;
		
	}
	
	/**
	 * 批量修改安全库存
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> updateSafeMatStoreInvBatch(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			//添加常量
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			//解析得Json
			JSONArray json = JSONArray.parseArray((String)map.get("invData"));
			List<String> list = new ArrayList<String>();
			Iterator<Object> it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if(jsonObj.get("inv_id") == null || "".equals(jsonObj.getString("inv_id"))){
					continue;
				}
				list.add(jsonObj.getString("inv_id"));
			}
			
			if(list == null || list.size() == 0){

				retMap.put("state", "false");
				retMap.put("error", "请选择材料！");
				return retMap;
			}
			
			matStoreInvMapper.updateSafeMatStoreInvBatch(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
		
		return retMap;
	}
}
