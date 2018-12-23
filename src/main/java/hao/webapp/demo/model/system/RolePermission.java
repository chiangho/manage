package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/***
 * 角色权限表
 * @author chianghao
 */
@Entity(title="角色权限管理表",tableName="sys_role_permission")
public class RolePermission extends BaseModel{

	@Attribute(title="角色主键",length=20)
	@Column
	private long roleId;
	
	@Attribute(title="权限主键",length=20)
	@Column
	private long permissionId;
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}
	
	
	
}
