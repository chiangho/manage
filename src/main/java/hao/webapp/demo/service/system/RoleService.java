package hao.webapp.demo.service.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hao.framework.service.BaseService;
import hao.webapp.demo.dao.system.role_info.RoleInfoDao;
import hao.webapp.demo.model.system.RoleInfo;

/***
 * 角色服务
 * @author chianghao
 *
 */
@Service
public class RoleService extends BaseService{

	
	@Autowired
	private RoleInfoDao roleDao;

	/**
	 * 查询账号的角色
	 * @param accessId
	 * @return
	 */
	public Set<RoleInfo> queryAccessRoles(long accessId) {
		List<RoleInfo> list = roleDao.queryByAccessId(accessId);
		if(list==null||list.size()==0) {
			return new HashSet<RoleInfo>();
		}
		Set<RoleInfo> set = new HashSet<RoleInfo>();
		for(RoleInfo r:list) {
			set.add(r);
		}
		return set;
	}

	
	
		
}
