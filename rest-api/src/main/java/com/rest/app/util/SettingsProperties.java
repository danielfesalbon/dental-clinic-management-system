/**
 * 
 */
package com.rest.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author danielf
 *
 */
@Component
public class SettingsProperties {

	@Value("${spring.datasource.url}")
	private String db_url;
	@Value("${spring.datasource.username}")
	private String db_user;
	@Value("${spring.datasource.password}")
	private String db_password;
	@Value("${server.port}")
	private String app_port;
	@Value("${system.version}")
	private String system_version;
	@Value("${system.name}")
	private String system_name;

	public SettingsProperties() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SettingsProperties(String db_url, String db_user, String db_password, String app_port, String system_version,
			String system_name) {
		super();
		this.db_url = db_url;
		this.db_user = db_user;
		this.db_password = db_password;
		this.app_port = app_port;
		this.system_version = system_version;
		this.system_name = system_name;
	}

	public String getSystem_version() {
		return system_version;
	}

	public void setSystem_version(String system_version) {
		this.system_version = system_version;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getDb_url() {
		return db_url;
	}

	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_password() {
		return db_password;
	}

	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}

	public String getApp_port() {
		return app_port;
	}

	public void setApp_port(String app_port) {
		this.app_port = app_port;
	}

}
