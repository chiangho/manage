package hao.webapp.demo.dao.system.menu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import hao.webapp.demo.model.system.Menu;

public interface MenuDao{

	List<Menu> queryMenuByForeignKey(@Param("accessId") long accessId,@Param("foreignKeyType")  int foreignKeyType);

	List<Menu> queryMenuByForeignKeys(@Param("ids")  long[] ids,@Param("foreignKeyType")  int foreignKeyType);
	
}