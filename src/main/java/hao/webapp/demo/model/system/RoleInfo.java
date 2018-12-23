package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/***
 * 角色信息
 * @author chianghao
 */
@Entity(tableName="sys_role",title="系统角色")
public class RoleInfo extends BaseModel{
	
	@Attribute(title="编号")
	@Column
	String roleCode;
	
	@Attribute(title="名称")
	@Column
	String roleName;


	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
