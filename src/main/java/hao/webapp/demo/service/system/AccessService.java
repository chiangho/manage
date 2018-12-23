package hao.webapp.demo.service.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.db.sql.SqlCommon.SqlOperator;
import hao.framework.db.sql.SqlSearch;
import hao.framework.service.BaseService;
import hao.framework.web.HaoSession;
import hao.framework.web.RequestContext;
import hao.framework.web.auth.shiro.AuthUtils;
import hao.framework.web.auth.shiro.MemberToken;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.AppConstant;
import hao.webapp.demo.dao.system.menu.MenuDao;
import hao.webapp.demo.model.system.Access;
import hao.webapp.demo.model.system.Menu;
import hao.webapp.demo.model.system.Permission;
import hao.webapp.demo.model.system.RoleInfo;

@Service
public class AccessService extends BaseService{

	
	@Autowired(required=true)
	MenuDao menuDao;
	
	@Autowired
	RoleService roleService;

	@Autowired
	PermissionService permissionService;
	
	@Autowired
	MenuService menuService;
	
	/***
	 * 账号登录
	 * @param access
	 * @param password
	 * @throws HaoException 
	 */
	public void login(String userAccess, String password) throws HaoException {
		if(userAccess==null||userAccess.equals("")) {
			throw new HaoException("0000001","账号不能为空");
		}
		//创建钥匙
		MemberToken token = MemberToken.createAccessToken(userAccess,"","",password);
		//认证账号
		AuthUtils.auth(token);
		//根据账号获取账号信息
		Access ac  = queryByField(Access.class,"access",userAccess);
		HaoSession.putAccess(ac);
		HaoSession.putAccessId(ac.getId()+"");
		//查询角色
		Set<RoleInfo> roles = this.roleService.queryAccessRoles(ac.getId());
		if(roles!=null&&roles.size()>0) {
			String[] roleidArray = new String[roles.size()];
			int i=0;
			for(RoleInfo r:roles) {
				roleidArray[i] = r.getRoleCode();
				i++;
			}
			HaoSession.putRoles(roleidArray);
		}
		
		//设置授权
		Set<Permission> permissions = this.permissionService.queryAccessPermissions(ac.getId(),roles);
		if(permissions!=null&&permissions.size()>0) {
			String[] permissionidArray = new String[permissions.size()];
			int i=0;
			for(Permission p:permissions) {
				permissionidArray[i] =p.getPermissionCode();
				i++;
			}
			HaoSession.putPermissions(permissionidArray);
		}
		
		//查询账号，角色，权限对应的菜单
		Set<Menu> menus = this.menuService.queryAccessMenu(ac.getId(),roles,permissions);
		List<Menu> menuList = this.getTreeMenu(Menu.MENU_ROOT,menus);
		HaoSession.put(AppConstant.SESSION_KEY_MENU_PREFIX+ac.getId(), menuList);
	}

	/**
	 * 获取属性结构
	 * @param menus
	 * @return
	 */
	public List<Menu> getTreeMenu(long parentId,Set<Menu> menus) {
		List<Menu> list = new ArrayList<Menu>();
		//查询父节点下的菜单
		for(Menu e:menus) {
			if(e.getParentId()==parentId) {
				list.add(e);
			}
		}
		if(list.size()==0) {
			return list;
		}
		//排序
		Collections.sort(list,new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getSort().compareTo(o2.getSort());
            }
        });
		for(Menu e:list) {
			List<Menu> subMenu = getTreeMenu(e.getId(),menus);
			if(subMenu.size()>0) {
				e.setSubMenuList(subMenu);
			}
		}
		return list;
	}


	/***
	 * 根据账号，手机，邮箱查找用户
	 * @param page
	 * @param access
	 * @param mobilePhone
	 * @param email
	 * @return
	 * @throws HaoException
	 */
	public List<Access> getPageList(Page page,
			String access,
			String mobilePhone,
			String email
			) throws HaoException{
		SqlSearch sqlSearch = new SqlSearch(Access.class);
		if(access!=null) {
			sqlSearch.addCondition("access", SqlOperator.like,access);
		}		
		if(mobilePhone!=null) {
			sqlSearch.addCondition("mobilePhone",SqlOperator.like, mobilePhone);
		}
		if(email!=null) {
			sqlSearch.addCondition("email",SqlOperator.like, email);
		}
		return jdbcDao.queryList(page, sqlSearch);//(page,sqlSearch);
	}

	public Access queryById(String id) throws HaoException {
		if(id==null||id.equals("")) {
			throw new HaoException("000001","账号主键不能为空或者为null");
		}
		return this.queryByField(Access.class, "access", id);
	}
	
	/***
	 * 根据id删除
	 * @param id
	 */
	public void delById(String id) {
		dao.deleteById(Access.class, id);
	}
	
	/**
	 * 保存表单
	 * @throws HaoException
	 */
	public void save() throws HaoException {
		Form form = new Form(Access.class,RequestContext.getHttpRequest());
		
		form.validate("access", ValidateType.REQUIRED);
		form.validate("access",ValidateType.ONLYONE);
		
		if(form.isInsertAction()) {
			form.validate("password", ValidateType.REQUIRED);
			form.validate("password",ValidateType.LENGTH,new String[] {"6"});
			//校验密码确认是否和第一次填写的密码一直
			form.validate("_password",ValidateType.EQUALTO,new String[] {"password"});
		}
	
		if(form.isInsertAction()) {
			form.setPrimaryValue();
			Access access = form.getBean();
			
			this.dao.insert(access);
		}else {
			Map<String,Object> fields= form.getBeanMap();
			this.dao.updateById(Access.class, fields,form.getParamValue("id"));
		}
	}

	public void del(String accessId) {
		// TODO Auto-generated method stub
		//删除账号
		//删除账号的菜单
		//删除权限
		//删除权限的菜单
		//删除角色
		//删除角色的菜单
	}
	
}
