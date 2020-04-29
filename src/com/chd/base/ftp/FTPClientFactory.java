package com.chd.base.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.log4j.Logger;


public class FTPClientFactory implements PoolableObjectFactory<FTPClient> {
	 private static Logger logger = Logger.getLogger(FTPClientFactory.class);  
     
	    private FTPClientConfig config;  
	      
	    public FTPClientFactory(FTPClientConfig config) {  
	        this.config = config;  
	    }  
	  
	    @Override  
	    public FTPClient makeObject() throws Exception {  
	        FTPClient ftpClient = new FTPClient();  
	        ftpClient.setConnectTimeout(config.getClientTimeout());  
	        try {  
	               ftpClient.connect(config.getHost(), config.getPort());  
	               int reply = ftpClient.getReplyCode();  
	               if (!FTPReply.isPositiveCompletion(reply)) {  
	                    ftpClient.disconnect();  
	                    logger.warn("FTPServer refused connection");  
	                    return null;  
	               }  
	               boolean result = ftpClient.login(config.getUsername(), config.getPassword());  
	               if (!result) {  
	                   logger.warn("ftpClient login failed... username is {}"+config.getUsername());  
	               }  
	               ftpClient.setFileType(config.getTransferFileType());  
	               ftpClient.setBufferSize(1024);  
	               ftpClient.setControlEncoding(config.getEncoding());  
	               if (config.getPassiveMode().equals("true")) {  
	                    ftpClient.enterLocalPassiveMode();  
	               }  
	          } catch (Exception e) {  
	               logger.error("create ftp connection failed...{}", e);  
	               throw e;  
	          }   
	          
	          return ftpClient;  
	    }  
	  
	    @Override  
	    public void destroyObject(FTPClient ftpClient) throws Exception {  
	        try {  
	            if(ftpClient != null && ftpClient.isConnected()) {  
	                ftpClient.logout();  
	            }  
	        } catch (Exception e) {  
	            logger.error("ftp client logout failed...{}", e);  
	            throw e;  
	        } finally {  
	            if(ftpClient != null) {  
	                ftpClient.disconnect();  
	            }  
	        }  
	          
	    }  
	  
	    @Override  
	    public boolean validateObject(FTPClient ftpClient) {  
	        try {  
	               return ftpClient.sendNoOp();  
	          } catch (Exception e) {  
	              logger.error("Failed to validate client: {}", e);  
	          }  
	        return false;  
	    }  
	  
	    @Override  
	    public void activateObject(FTPClient obj) throws Exception {  
	        //Do nothing  
	          
	    }  
	  
	    @Override  
	    public void passivateObject(FTPClient obj) throws Exception {  
	        //Do nothing  
	          
	    }  
}
