package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/***
 * 账户角色表
 * @author chianghao
 */
@Entity(title="账户角色表",tableName="sys_access_role")
public class AccessRole extends BaseModel{

	@Attribute(title="账号主键",length=20)
	@Column
	private long accessId;
	
	@Attribute(title="角色主键",length=20)
	@Column
	private long roleId;
	
	
	public long getAccessId() {
		return accessId;
	}
	public void setAccessId(long accessId) {
		this.accessId = accessId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	
	
}
