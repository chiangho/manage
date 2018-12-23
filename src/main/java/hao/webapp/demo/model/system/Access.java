package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

@Entity(tableName="sys_access",title="系统账号")
public class Access extends BaseModel{

	
	@Attribute(title="账号")
	@Column
	private String access;
	
	@Attribute(title="手机号码")
	@Column
	private String mobilePhone;
	
	@Attribute(title="邮箱")
	@Column
	private String email;
	
	@Attribute(title="姓名")
	@Column
	private String userName;
	
	@Attribute(title="密码")
	@Column
	private String password;
	
	
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
