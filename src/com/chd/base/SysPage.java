/** 
* 2014-7-24 
* Page.java 
* author:pengjin
*/ 
package com.chd.base; 

public class SysPage {
	
	private int page;//页索引
	private int pagesize;//当页显示数目
	private int total;//总记录数
	private boolean usePager;//是否分页
	public SysPage() {
		super();
	}
	public SysPage(int pagesize, int page, int total) {
		super();
		this.pagesize = pagesize;
		this.page = page;
		this.total = total;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public boolean isUsePager() {
		return usePager;
	}
	public void setUsePager(boolean usePager) {
		this.usePager = usePager;
	}
	
}
