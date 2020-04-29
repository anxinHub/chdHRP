package com.chd.base.ftp;

import com.chd.base.jdbc.ConfigInit;

public class FTPClientConfig {
    
    private final String host = ConfigInit.getFtpConfig().getProperty("ftp_host");
	private final int port = Integer.parseInt(ConfigInit.getFtpConfig().getProperty("ftp_port"));
	private final String username = ConfigInit.getFtpConfig().getProperty("ftp_username");
	private final String password = ConfigInit.getFtpConfig().getProperty("ftp_password");
  
    private final String passiveMode = ConfigInit.getFtpConfig().getProperty("ftp_passivemode");  
  
    private final String encoding = ConfigInit.getFtpConfig().getProperty("ftp_encoding");  
  
    private int clientTimeout = Integer.parseInt(ConfigInit.getFtpConfig().getProperty("ftp_clientTimeout"));  
  
    private int bufferSize;  
  
    private int transferFileType;  
  
    private boolean renameUploaded;  
  
    private int retryTime;

	public int getClientTimeout() {
		return clientTimeout;
	}

	public void setClientTimeout(int clientTimeout) {
		this.clientTimeout = clientTimeout;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getTransferFileType() {
		return transferFileType;
	}

	public void setTransferFileType(int transferFileType) {
		this.transferFileType = transferFileType;
	}

	public boolean isRenameUploaded() {
		return renameUploaded;
	}

	public void setRenameUploaded(boolean renameUploaded) {
		this.renameUploaded = renameUploaded;
	}

	public int getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPassiveMode() {
		return passiveMode;
	}

	public String getEncoding() {
		return encoding;
	}  
    
}
