package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/***
 * 权限许可
 * @author chianghao
 */
@Entity(tableName="sys_permission",title="系统权限")
public class Permission extends BaseModel{
	
	
	@Attribute(title="编号")
	@Column
	String permissionCode;
	
	@Attribute(title="名称")
	@Column
	String permissionName;


	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	
}
