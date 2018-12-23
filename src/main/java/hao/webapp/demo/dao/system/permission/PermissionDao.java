package hao.webapp.demo.dao.system.permission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import hao.webapp.demo.model.system.Permission;
import hao.webapp.demo.model.system.RoleInfo;

public interface PermissionDao{


	/***
	 * 查询账号的权限
	 * @param accessId
	 * @return
	 */
	List<Permission> queryAccessPermission(@Param("accessId") long accessId);

	
	/***
	 * 
	 * @param roleIds
	 * @return
	 */
	List<Permission> queryRolePermisssion(@Param("roleIds")  RoleInfo[] roleIds);
	
}