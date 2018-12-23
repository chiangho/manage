package hao.webapp.demo.service.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.service.BaseService;
import hao.webapp.demo.AppConstant;
import hao.webapp.demo.dao.system.menu.MenuDao;
import hao.webapp.demo.model.system.Menu;
import hao.webapp.demo.model.system.MenuMap;
import hao.webapp.demo.model.system.Permission;
import hao.webapp.demo.model.system.RoleInfo;

/***
 * 菜单服务
 * @author chianghao
 */
@Service
public class MenuService extends BaseService{

	@Autowired
	MenuDao menuDao;
	
	/***
	 * 查询账号的菜单
	 * @param id          账号主键
	 * @param roles       角色
	 * @param permissions 权限
	 * @return
	 * @throws HaoException 
	 */
	public Set<Menu> queryAccessMenu(long accessId, Set<RoleInfo> roles, Set<Permission> permissions) throws HaoException {
		//校验是否超级管理员角色。如果是超级管理员则
		Set<Menu> set = new HashSet<Menu>();
		if(existsSupperAdmin(roles)) {
			//如果是超级管理员获取全部菜单
			List<Menu> menus =  queryAll(Menu.class);
			if(menus!=null&&menus.size()>0) {
				for(Menu e:menus) {
					set.add(e);
				}	
			}
			return set;
		}
		// TODO Auto-generated method stub
		List<Menu> accessMenu = menuDao.queryMenuByForeignKey(accessId, MenuMap.MENU_MAP_TYPE_ACCESS);
		
		RoleInfo[] roleArray = roles.toArray(new RoleInfo[roles.size()]);
		long[] roleIds = new long[roleArray.length];
		for(int i=0;i<roleArray.length;i++) {
			roleIds[i] = roleArray[i].getId();
		}
		List<Menu> roleMenu = menuDao.queryMenuByForeignKeys(roleIds,MenuMap.MENU_MAP_TYPE_ROLE);
		
		Permission[] permissionArray = permissions.toArray(new Permission[permissions.size()]);
		long[] permissionIds = new long[permissionArray.length];
		for(int i=0;i<permissionArray.length;i++) {
			permissionIds[i] = permissionArray[i].getId();
		}
		List<Menu> permissionMenu = menuDao.queryMenuByForeignKeys(permissionIds,MenuMap.MENU_MAP_TYPE_PREMISSION);
		
		//整合
		
		if(accessMenu!=null&&accessMenu.size()>0) {
			for(Menu e:accessMenu) {
				set.add(e);
			}
		}
		
		if(roleMenu!=null&&roleMenu.size()>0) {
			for(Menu e:roleMenu) {
				set.add(e);
			}
		}
		if(permissionMenu!=null&&permissionMenu.size()>0) {
			for(Menu e:permissionMenu) {
				set.add(e);
			}	
		}
		return set;
	}


	/***
	 * 检查是否存在超级管理员
	 * @param roles
	 * @return
	 */
	public boolean existsSupperAdmin(Set<RoleInfo> roles) {
		for(RoleInfo r:roles) {
			if(r.getRoleCode().equals(AppConstant.ROLE_CODE_SUPER_ADMIN)) {
				return true;
			}
		}
		return false;
	}

	
	
		
}
