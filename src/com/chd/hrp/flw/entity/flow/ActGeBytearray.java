package com.chd.hrp.flw.entity.flow;

import java.io.Serializable;

public class ActGeBytearray  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2118446152832890266L;
	private String id_;
	private int rev_;
	private String name_;
	private String deployment_id_;
	private byte[] bytes_;
	private int generated_;
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public int getRev_() {
		return rev_;
	}
	public void setRev_(int rev_) {
		this.rev_ = rev_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getDeployment_id_() {
		return deployment_id_;
	}
	public void setDeployment_id_(String deployment_id_) {
		this.deployment_id_ = deployment_id_;
	}
	public byte[] getBytes_() {
		return bytes_;
	}
	public void setBytes_(byte[] bytes_) {
		this.bytes_ = bytes_;
	}
	public int getGenerated_() {
		return generated_;
	}
	public void setGenerated_(int generated_) {
		this.generated_ = generated_;
	}
	
	
}
