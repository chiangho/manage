package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/***
 * 账号权限表
 * @author chianghao
 *
 */
@Entity(title="账户权限表",tableName="sys_access_permission")
public class AccessPermission extends BaseModel{

	@Attribute(title="账号主键",length=20)
	@Column
	private long accessId;
	
	@Attribute(title="权限主键",length=20)
	@Column
	private long permissionId;
	
	
	public long getAccessId() {
		return accessId;
	}
	public void setAccessId(long accessId) {
		this.accessId = accessId;
	}
	public long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}
	
	
	
	
}
