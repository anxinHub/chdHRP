package com.chd.base.startup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;

import com.chd.base.jdbc.ConfigInit;
import com.chd.base.jdbc.ConnectionFactory;
import com.chd.hrp.sys.entity.SysProcError;
import com.chd.hrp.sys.service.SysProcErrorService;

public class LoadSqlFile implements InitializingBean{

	private static Logger logger = Logger.getLogger(LoadSqlFile.class);
	private Document doc=null;
	private SAXReader reader = new SAXReader();
	private Connection conn=null;
	private PreparedStatement pstmt = null;
	private static String sqlMsg=null;
	public static String getSqlMsg() {
		return sqlMsg;
	}
	private int count=0;
//	@Resource(name = "sysFunUtilMapper")
//	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name="sysProcErrorService")
	private  final SysProcErrorService sysProcErrorService = null; 
	
	@Override
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		
		String url = LoadSystemInfo.getProjectUrl();

		if (null == url) {
			logger.error("项目路径异常。");
			return;
		}

		//验证HRP数据源
		if (!LoadSystemInfo.isConn()) {
			return;
		}
		
		if (LoadChdInfo.get$001()!=null) {
			return;
		}
		
		if (!ConfigInit.getConfigProperties("loadProc").equals("true")) {
			return;
		}
		
		File file=new File(url + "WEB-INF\\classes\\oracle");
		//logger.debug(LoadSystemInfo.getHrpMap().get("hrpDatatype"));
		try {
			
			conn=ConnectionFactory.getSystemConnection();
			if(conn==null){
				logger.debug("获取数据源连接失败！");
				return;
			}
			
			logger.debug("开始加载SQL文件。");
			
			//清空表数据
			sysProcErrorService.errorDelAll();
			
			readFile(file);
			
			/*编译存储过程，函数、视图、包头、包体、索引、触发器*/
			sysProcErrorService.updateErrorCompile();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			//e.printStackTrace();
			//throw new SysException();
		} finally{
			try {
				ConfigInit.setConfigProperties("loadProc","false");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionFactory.closeAllConn(conn,"system",pstmt,null);
		}
		
	}
	
	//读取WEB-INF\\classes\\oracle目录下面的序列、存储过程、函数、视图
	private void readFile(File file) throws SQLException, DocumentException{
		if(file==null){
			return;
		}
		
		if (file.isDirectory()) {
			// 这里将列出所有的文件夹
			// System.out.println("Dir==>" + f.getAbsolutePath());
			 File[] flist=file.listFiles();
			 for (File f : flist) {
				 if(f.getName().equalsIgnoreCase("05other")){
					 //05other目录不执行
					 continue;
				 }
				 readFile(f);
			 }
            
		} else if (file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase().equalsIgnoreCase("xml")) {
			logger.debug("load sql file:" + file.getName());
			readFileContent(file);
		}
		
		
	}
	
	/**
	 * 一个文件读一次
	 * @param file
	 * @throws SQLException 
	 * @throws DocumentException 
	 */
	private void readFileContent(File file) throws  DocumentException{
		
			doc = reader.read(file);
			Element root = doc.getRootElement();
			
			List<Element> list = root.elements();
			if(list!=null && list.size()>0){
				for (Element e : list) {
					if(!e.getName().equals("sql")){
						continue;
					}
					
					if(e.attributeValue("id") == null || e.attributeValue("id").equals("")){
						continue;
					}
					
					if(e.attributeValue("type") == null || e.attributeValue("type").equals("")){
						continue;
					}
					
					if(e.getText()==null || e.getText().equals("")) {
						continue;
					}
					
					count++;
					logger.debug(count+" load "+e.attributeValue("type")+":" + e.attributeValue("id"));
					//logger.debug(e.getText());
					try{
					pstmt = conn.prepareStatement(e.getText());
			        pstmt.execute();
			        pstmt.close();
					//mybatis执行，没有jdbc直接执行快
				//	sysFunUtilMapper.execDDLSql(e.getText());
					}catch (SQLException sqle) {
						// TODO Auto-generated catch block
						sqlMsg="系统加载SQL文件错误，请到系统平台处理。";
						logger.debug(sqle.getMessage(),sqle);
						//sqle.printStackTrace();
						SysProcError spe=new SysProcError();
						spe.setNote( e.attributeValue("desc"));
						spe.setError(sqle.getMessage());
						spe.setFilePath(file.getAbsolutePath());
						spe.setModCode(getMod(spe.getFilePath()));
						spe.setSqlId(e.attributeValue("id"));
						spe.setType( e.attributeValue("type"));
						sysProcErrorService.errorAdd(spe);
						//throw new SysException();
						
					} 
				}
			}
	}
	
	private static String getMod(String f){
		String mod=null;
		if(f.contains("classes"+File.separator+"oracle"+File.separator)){
			f=f.substring(f.indexOf("classes"+File.separator+"oracle"+File.separator), f.length());
			f=f.replace(File.separator, ",");
			String[] ps=f.split(",");
			if(ps.length>3){			
				if(ps[0].equals("classes")&&ps[1].equals("oracle")){
					mod=ps[3];
				}
			
			}
			//mod=f.substring(f.indexOf("\\classes\\oracle\\"), f.length());
		}
		return mod;
	}
	//执行序列SQL文件，执行完会断号，与cache属性有关。
	private void execSeqFile(File file) throws FileNotFoundException{
		
	        ScriptRunner runner = new ScriptRunner(conn);
	        Resources.setCharset(Charset.forName("utf-8")); //设置字符集,不然中文乱码插入错误
	    //  runner.setLogWriter(null);//设置是否输出日志
        //  runner.runScript(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	        runner.runScript(new FileReader(file));
	     // runner.runScript(Resources.getResourceAsReader("sql/CC20-01.sql"));
	        runner.closeConnection();
	         
	}
}
