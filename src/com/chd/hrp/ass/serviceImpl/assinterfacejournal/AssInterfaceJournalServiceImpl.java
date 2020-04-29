package com.chd.hrp.ass.serviceImpl.assinterfacejournal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.nutz.castor.castor.String2Object;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.assinterfacejournal.AssInterfaceJournalMapper;
import com.chd.hrp.ass.service.assinterfacejournal.AssInterfaceJournalService;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;

@Service("assInterfaceJournalService")
public class AssInterfaceJournalServiceImpl implements
		AssInterfaceJournalService {

	private static Logger logger=Logger.getLogger(AssInterfaceJournalServiceImpl.class);
	@Resource(name="assInterfaceJournalMapper")
	private final AssInterfaceJournalMapper assInterfaceJournalMapper=null;
	
	
	/**
	 * 查询方法
	 */
	public String queryAssInterfaceJournal(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			SysPage sysPage=new SysPage();
			sysPage=(SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> list=assInterfaceJournalMapper.queryAssInterfaceJournal(mapVo);
				return ChdJson.toJsonLower(list);
			}else{
			RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list=assInterfaceJournalMapper.queryAssInterfaceJournal(mapVo,rowBounds);
	        PageInfo page=new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			}
			}catch(Exception e) {
				logger.warn(e.getMessage(), e);
				throw new SysException(e.getMessage(), e);
			}
	}

	public int insertAssInterfaceJournal(Map<String, Object> mapVo)
			throws DataAccessException {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try{
		
		 ///先获取类名，方法名，类描述，beanname
		Map<String,Object> typeMap=assInterfaceJournalMapper.queryInterfaceType(mapVo);

		String className=typeMap.get("pi_classname")==null?"0":typeMap.get("pi_classname").toString();
		String methodName=typeMap.get("pi_methodname")==null?"":typeMap.get("pi_methodname").toString();
		String beanName=typeMap.get("pi_beanname")==null?"0":typeMap.get("pi_beanname").toString();
		String classDesc=typeMap.get("pi_classdesc")==null?"0":typeMap.get("pi_classdesc").toString();
		String typeName=typeMap.get("pit_typename")==null?"0":typeMap.get("pit_typename").toString();
		
		//处理时间
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalTime time = LocalTime.now(); 
		Date PI_OperateDate=new Date();
		String OperateDate=format.format(PI_OperateDate);
		String OperateTime=formatter.format(time);
		//System.out.println(OperateDate+"#"+OperateTime);
		///将取到的参数加入Map
		mapVo.put("pit_typename", typeName);
		mapVo.put("pi_classname", className);
		mapVo.put("pi_methodname", methodName);
		mapVo.put("pi_beanname", beanName);
		mapVo.put("pi_classdesc", classDesc);
		mapVo.put("pi_operatedate", OperateDate);
		mapVo.put("pi_operatetime", OperateTime);
		int res=0;
			///如果是补录，处理补录时间等,然后更新日志记录
			if(mapVo.get("pi_ismakeup").equals("1")){
				mapVo.put("pi_makeupdate", OperateDate);
				mapVo.put("pi_makeuptime", OperateTime);
				res=assInterfaceJournalMapper.updateAssInterfaceJournal(mapVo);
			}else{
			 res=assInterfaceJournalMapper.insertAssInterfaceJournal(mapVo);
			}
			return res;
		
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		//return null;
	}

/**
 * 补录，调用相关接口方法
 * 
 */
	public String addMakeUpInterface(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			//根据参数查询对应的方法名，类名，参数等信息
		Map<String, Object> map=assInterfaceJournalMapper.queryAssInterfaceJournalByID(mapVo);
		
				String className=map.get("pi_calssname")==null?"":map.get("pi_calssname").toString();  //类名
				String methodName=map.get("pi_methodname")==null?"":map.get("pi_methodname").toString();  //方法名
				String bean_name=map.get("pi_beanname")==null?"":map.get("pi_beanname").toString();           //beanname
				String param=map.get("pi_recorddata")==null?"":map.get("pi_recorddata").toString();   //参数（参数名:参数值，参数名1:参数值1）
				//map.put("bean_name", "assInterfaceJournalService");
				///把所有参数加入Map中，调用其他接口
				Map<String, Object> paramMap=new HashMap<String, Object>(); 
				if((param!=null)&&(!"".equals(param))){
				for (String detail : param.split(",")) {
					String key=detail.split(":")[0];
					String value=detail.split(":")[1];
					if((key!=null)&&(!"".equals(key))){
					paramMap.put(key, value);
					}
				}
				}
				///是否补录标志，在其他接口向合同日志插入记录时使用
				paramMap.put("pi_ismakeup", "1");
				paramMap.put("pi_logid", mapVo.get("pi_logid"));
				List<Map<String,Object>> resList=new ArrayList<Map<String,Object>>();
				/******************************java反射动态执行方法*************************************************/
				
			try{
				 Class<?> clz = Class.forName(className);
				 Object obj;
				 if((bean_name!=null) && (!("").equals(bean_name))){
					 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
					 obj = context.getBean(bean_name);
				 }else{
					 obj = clz.newInstance();
				 }
				     
	             Method method = clz.getMethod(methodName, Map.class);
			     Object resObj=method.invoke(obj, paramMap);
	             
			     if(resObj instanceof List){
			    	 resList=(List<Map<String, Object>>) resObj;
			     }
			     
			}catch(ClassNotFoundException e){

				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new SysException("创建类["+className+"]失败！");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new SysException("访问类["+className+"]失败！");
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new SysException("执行方法["+methodName+"]失败！");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new SysException("实例化类["+className+"]失败！");
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new SysException("没有找到["+methodName+"]方法！");
			}
			
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
			
		}
		 return "{\"msg\":\"补录成功.\",\"state\":\"true\"}";
	}
@Override
public String queryPItype(Map<String, Object> mapVo) throws DataAccessException {
	
	return JSON.toJSONString(assInterfaceJournalMapper.queryPItype(mapVo));
}

}
