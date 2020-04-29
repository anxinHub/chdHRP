/**
* @Copyright: Copyright (c) 2016-1-24 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.flw.entity.flow;

import java.io.Serializable;

/**
* @Title. @Description.
* 流程定义数据表<BR>
* @Version: 1.0
*/


public class ActReProcdef implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 585852784784383979L;
	private String id_;
	private int rev_;
	private String category_;
	private String name_;
	private String key_;
	private int version_;
	private String deployment_id_;
	private String resource_name_;
	private String dgrm_resource_name_;
	private String description_;
	private int has_start_form_key_;
	private int has_graphical_notation_;
	private int suspension_state_;
	private String tenant_id_;//用户ID
	private String user_name;
	private String deploy_time_;//部署时间
	
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
	public String getCategory_() {
		return category_;
	}
	public void setCategory_(String category_) {
		this.category_ = category_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getKey_() {
		return key_;
	}
	public void setKey_(String key_) {
		this.key_ = key_;
	}
	public int getVersion_() {
		return version_;
	}
	public void setVersion_(int version_) {
		this.version_ = version_;
	}
	public String getDeployment_id_() {
		return deployment_id_;
	}
	public void setDeployment_id_(String deployment_id_) {
		this.deployment_id_ = deployment_id_;
	}
	public String getResource_name_() {
		return resource_name_;
	}
	public void setResource_name_(String resource_name_) {
		this.resource_name_ = resource_name_;
	}
	public String getDgrm_resource_name_() {
		return dgrm_resource_name_;
	}
	public void setDgrm_resource_name_(String dgrm_resource_name_) {
		this.dgrm_resource_name_ = dgrm_resource_name_;
	}
	public String getDescription_() {
		return description_;
	}
	public void setDescription_(String description_) {
		this.description_ = description_;
	}
	public int getHas_start_form_key_() {
		return has_start_form_key_;
	}
	public void setHas_start_form_key_(int has_start_form_key_) {
		this.has_start_form_key_ = has_start_form_key_;
	}
	public int getHas_graphical_notation_() {
		return has_graphical_notation_;
	}
	public void setHas_graphical_notation_(int has_graphical_notation_) {
		this.has_graphical_notation_ = has_graphical_notation_;
	}
	public int getSuspension_state_() {
		return suspension_state_;
	}
	public void setSuspension_state_(int suspension_state_) {
		this.suspension_state_ = suspension_state_;
	}
	public String getTenant_id_() {
		return tenant_id_;
	}
	public void setTenant_id_(String tenant_id_) {
		this.tenant_id_ = tenant_id_;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDeploy_time_() {
		return deploy_time_;
	}
	public void setDeploy_time_(String deploy_time_) {
		this.deploy_time_ = deploy_time_;
	}
	
}