package com.chd.nutz.his.bean;

import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.View;


@Table("sys_user")
public class User {
private String user_code;
private String user_name;

public String getUser_code() {
	return user_code;
}

public void setUser_code(String user_code) {
	this.user_code = user_code;
}

public String getUser_name() {
	return user_name;
}

public void setUser_name(String user_name) {
	this.user_name = user_name;
}

}
