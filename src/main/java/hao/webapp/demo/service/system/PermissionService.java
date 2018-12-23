package hao.webapp.demo.service.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hao.framework.service.BaseService;
import hao.webapp.demo.dao.system.permission.PermissionDao;
import hao.webapp.demo.model.system.Permission;
import hao.webapp.demo.model.system.RoleInfo;

/**
 * 权限服务
 * @author chianghao
 *
 */
@Service
public class PermissionService extends BaseService{

	@Autowired
	PermissionDao permissionDao;
	
	
	/***
	 * 查询账号的权限
	 * @param accessId 账号主键
	 * @param roles    账号的角色
	 * @return 
	 */
	public Set<Permission> queryAccessPermissions(long accessId, Set<RoleInfo> roles) {
		List<Permission> accessPermissions = permissionDao.queryAccessPermission(accessId);
		List<Permission> rolePermissions = null;
		if(roles!=null&&roles.size()>0) {
			RoleInfo[] roleIds = roles.toArray(new RoleInfo[roles.size()]);
			rolePermissions = permissionDao.queryRolePermisssion(roleIds);
		}
		Set<Permission> set = new HashSet<Permission>();
		if(accessPermissions!=null&&accessPermissions.size()>0) {
			for(Permission p:accessPermissions) {
				set.add(p);
			}
		}
		if(rolePermissions!=null&&rolePermissions.size()>0) {
			for(Permission p:rolePermissions) {
				set.add(p);
			}
		}
		return set;
	}

	
	
	
		
}
