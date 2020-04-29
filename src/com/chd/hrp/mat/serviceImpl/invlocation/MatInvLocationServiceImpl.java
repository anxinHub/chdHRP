package com.chd.hrp.mat.serviceImpl.invlocation;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;
import javax.xml.ws.http.HTTPException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.mat.dao.invlocation.MatInvLocationMapper;
import com.chd.hrp.mat.entity.DzbqMain;
import com.chd.hrp.mat.entity.DzbqShanDeng;
import com.chd.hrp.mat.entity.DzbqView;
import com.chd.hrp.mat.service.invlocation.MatInvLocationService;
import com.chd.task.mat.MatBean;

@Service("matInvLocationService")
public class MatInvLocationServiceImpl implements MatInvLocationService {
	private static Logger logger = Logger.getLogger(MatInvLocationServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matInvLocationMapper")
	private final MatInvLocationMapper matInvLocationMapper = null;

	@Override
	public String queryInvLocation(Map<String, Object> mapVo) {

		List<DzbqView> list = matInvLocationMapper.queryInvLocation(mapVo);

		return null == list ? null : Json.toJson(list);
	}

	@Override
	public List<DzbqView> queryInvLocationList(Map<String, Object> mapVo) {

		List<DzbqView> list = matInvLocationMapper.queryInvLocation(mapVo);

		return null == list ? null : list;
	}

	@Override
	public List<DzbqShanDeng> queryOutInv(Map<String, Object> mapVo) {
		List<DzbqShanDeng> list = matInvLocationMapper.queryOutInv(mapVo);

		return null == list ? null : list;
	}

	@Override
	public List<DzbqShanDeng> queryInInv(Map<String, Object> mapVo) {
		List<DzbqShanDeng> list = matInvLocationMapper.queryInInv(mapVo);

		return null == list ? null : list;
	}

	/**
	 * webservice 调用电子标签
	 * 
	 * @param json
	 */
	@Override
	public void matInvlocation(List list, String type) {

		
		//测试
		//-------------------------------------------------------
		/*DzbqMain main = new DzbqMain();

		main.setGoodsList(list);
		main.setShopCode("0000");
		main.setTemplate("02");

		String dtJson = Json.toJson(main, JsonFormat.full());

		logger.debug("请求参数：" + dtJson);*/
		//-------------------------------------------------------
		
		PropertiesProxy conf = Mvcs.ctx().getDefaultIoc().get(PropertiesProxy.class, "conf");

		String url = conf.get("invLocation.url");
		logger.debug("获取材料货位发布的webservice地址：" + url);
		Object[] result = null;
		if (!Strings.isEmpty(url)) {

			try {

				long beginTime = System.currentTimeMillis(); // 开始时间
				// 动态构建服务端服务类
				JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
				Client client = clientFactory.createClient(url);
				logger.debug("################1######################" );

				long endTime = System.currentTimeMillis(); // 开始时间

				if (type.equals("01")) {
					// 设备信息显示 单一传递
					view(list, client, "02");

				} else if (type.equals("02")) {
					// 闪灯和灯塔 单一传递
					shandeng(list, client, "02");
				} else if (type.equals("03")) {
					// 数据、闪灯、灯塔 统一传递
					view(list, client, "02");
				}
				logger.debug("################2###################" );
				long end = System.currentTimeMillis(); // 开始时间
				long re_q = endTime - beginTime;
				long re_p = end - endTime;
				long sum = end - beginTime;
				logger.debug("调用请求时间：" + re_q + " 毫秒");
				logger.debug("调用响应时间：" + re_p + " 毫秒");
				logger.debug("调用请求响应总时间：" + sum + " 毫秒");

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

				throw new SysException("{\"error\":\"易泰勒设备材料货位同步失败 \"}");
			}

		}
	}

	/**
	 * 
	 * @param list
	 *            传递的json
	 * @param client
	 *            客户端
	 * @param template
	 *            设备显示模板
	 * @return
	 * @throws Exception
	 */
	private String view(List list, Client client, String template) throws Exception {

		if (Lang.isEmpty(list)) {
			logger.equals("传递的集合为空");
			return null;
		}

		Object[] result = null;

		DzbqMain main = new DzbqMain();

		main.setGoodsList(list);
		main.setShopCode("0000");
		main.setTemplate(template);

		String dtJson = Json.toJson(main, JsonFormat.full());

		/**
		 * 动态调用服务端接口方法 invoke(方法名,方法参数 可多个参数 。。。)
		 */
		logger.debug("请求方法：PushDataContext");
		logger.debug("请求参数：" + dtJson);
		// 灯塔方法调用
		result = client.invoke("PushDataContext", dtJson);

		logger.debug("易泰勒返回信息：" + Json.toJson(result));

		return Json.toJson(result);
	}
	/**
	 * 
	 * @param list
	 *            传递的json
	 * @param client
	 *            客户端
	 * @param template
	 *            设备显示模板
	 * @return
	 * @throws Exception
	 */
	private String shandeng(List list, Client client, String template) throws Exception {

		if (Lang.isEmpty(list)) {
			logger.equals("传递的集合为空");
			return null;
		}

		Object[] result = null;

		DzbqMain main = new DzbqMain();

		main.setGoodsList(list);
		main.setShopCode("0000");
		main.setTemplate(template);

		String dtJson = Json.toJson(main, JsonFormat.full());

		/**
		 * 动态调用服务端接口方法 invoke(方法名,方法参数 可多个参数 。。。)
		 */
		logger.debug("请求方法：PushDataContextEmpty");
		logger.debug("灯塔请求参数：" + dtJson);
		// 灯塔方法调用
		result = client.invoke("PushDataContextEmpty", dtJson);
		logger.debug("易泰勒返回信息：" + Json.toJson(result));

		return Json.toJson(result);

	}

	

	@Override
	public List<DzbqShanDeng> queryInvlocationShanDeng(Map<String, Object> mapVo) {
		List<DzbqShanDeng> list = matInvLocationMapper.queryInvlocationShanDeng(mapVo);
		return null == list ? null : list;
	}

	/**
	 * 传递的参数中必须有key flag值<br>
	 * <span> mapVo.put('flag','01') </br>
	 * or mapVo.put('flag','02')</br>
	 * flag:01 入库业务 <br>
	 * 02:出库有任务 <span>
	 */
	@Override
	public List<DzbqView> querySendData(Map<String, Object> mapVo) {
		List<DzbqView> list = matInvLocationMapper.querySendData(mapVo);

		return null == list ? null : list;
	}

}
