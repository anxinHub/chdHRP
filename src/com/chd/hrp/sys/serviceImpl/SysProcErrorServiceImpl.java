package com.chd.hrp.sys.serviceImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.jdbc.ConnectionFactory;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.SysProcErrorMapper;
import com.chd.hrp.sys.entity.HosLevel;
import com.chd.hrp.sys.entity.SysProcError;
import com.chd.hrp.sys.entity.SysProcErrorExample;
import com.chd.hrp.sys.service.SysProcErrorService;
import com.github.pagehelper.PageInfo;

@Service("sysProcErrorService")
public class SysProcErrorServiceImpl implements SysProcErrorService {

	@Resource(name = "sysProcErrorMapper")
	private final SysProcErrorMapper sysProcErrorMapper = null;
	
	@Override
	public int errorAdd(SysProcError record) {
		// TODO Auto-generated method stub
		return sysProcErrorMapper.insert(record);
	}

	@Override
	public int errorDelById(String id) {
		// TODO Auto-generated method stub
		return sysProcErrorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int errorDelAll() {
		// TODO Auto-generated method stub
		return sysProcErrorMapper.deleteByExample(null);
	}

	@Override
	public String queryErrorsByModCode(String modCode,String mtype) {
		// TODO Auto-generated method stub
		SysProcErrorExample example=new SysProcErrorExample();
		SysProcErrorExample.Criteria ca=example.createCriteria();
		if(modCode!=null&&!modCode.equals("")){
			
			ca.andModCodeEqualTo(modCode);
		}
		if(mtype!=null&&!mtype.equals("")){
			ca.andTypeEqualTo(mtype);
		}
		List<SysProcError> list= sysProcErrorMapper.selectByExample(example);
	
		
		return ChdJson.toJson(list);
	}

	@Override
	public String getMods() {
		String url = LoadSystemInfo.getProjectUrl();
		List<HosLevel> list=new ArrayList<HosLevel>();
		List<String> keys=new ArrayList<String>();
		File file=new File(url + "WEB-INF\\classes\\oracle");
		File[] fs=null;
		File[] fs2=null;
		HosLevel tmp=null;
		if(file.exists()&&file.isDirectory()){
			fs=file.listFiles();
			if(fs.length>0){
				for(File f:fs){
					if(f.exists()&&f.isDirectory()){
						fs2=f.listFiles();
						for(File tf:fs2){
							if(tf.exists()&&tf.isDirectory()){
								//符合条件加入列表
								tmp=new HosLevel();
								tmp.setLevel_code(tf.getName());
								tmp.setLevel_name(tf.getName());
								if(!keys.contains(tmp.getLevel_code())){
									keys.add(tmp.getLevel_code());
									list.add(tmp);
								}
							}
						}
					}
				}
			}
		}
			
		return JSON.toJSONString(list);
	}

	@Override
	public String errorExecuteSql(String sqlIds) throws DocumentException {
		SysProcErrorExample example=new SysProcErrorExample();
		SysProcErrorExample.Criteria ca=example.createCriteria();
		List<String> t=null;
		if(sqlIds!=null){
			t= Arrays.asList(sqlIds.split(","));
			ca.andSqlIdIn(t);
		}
		List<SysProcError> list=sysProcErrorMapper.selectByExample(example);
		File f=null;
		int r=0;
		for(SysProcError e:list){
			if(e.getModCode().equalsIgnoreCase("compile")){
				//编译错误信息，没有文件路径
				continue;
			}
			f=new File(e.getFilePath());
			if(execute(f,e.getSqlId())==0){
				r++;
			}
		}
		
		/*编译存储过程，函数、视图、包头、包体、索引、触发器*/
		sysProcErrorMapper.updateErrorCompile();
		return "{\"msg\":\"总任务："+t.size()+" 成功数："+r+"\",\"state\":\"true\"}";
	}

	private int execute(File file,String sqlId) throws DocumentException{
		int result=2;
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		Element root = doc.getRootElement();		
		List<Element> list = root.elements();
		Connection conn=null;
		try {
			conn=ConnectionFactory.getSystemConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstmt = null;
		if(list!=null && list.size()>0){
			for (Element e : list) {				 
				if(e.attributeValue("id") != null&&e.attributeValue("id").equals(sqlId)){ 
					try{
						pstmt = conn.prepareStatement(e.getText());
						pstmt.execute();
						pstmt.close();	
						int k=errorDelById(sqlId);
						if(k==1){
							result=0;
						}else{
							result=1;
						}
					}catch (SQLException sqle) {
						sqle.printStackTrace();
						SysProcError spe=new SysProcError();					 
						spe.setError(sqle.getMessage());					 
						errorUpdate(spe);					
					} 
					
				}
			}
		}
		return result;
}

	@Override
	public int errorUpdate(SysProcError record) {
		// TODO Auto-generated method stub
		return sysProcErrorMapper.updateByPrimaryKeySelective(record);
	}
	
	/*编译存储过程，函数、视图、包头、包体、索引、触发器*/
	@Override
	public int updateErrorCompile() {
		// TODO Auto-generated method stub
		return sysProcErrorMapper.updateErrorCompile();
	}
}
