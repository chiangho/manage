package hao.webapp.demo.dao.system.role_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import hao.webapp.demo.model.system.RoleInfo;

public interface RoleInfoDao{

	List<RoleInfo> queryByAccessId(@Param("accessId") long accessId);
	
}