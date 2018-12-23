package hao.webapp.demo.model.system;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 菜单和账号、角色、权限的映射关系
 * @author chianghao
 *
 */
@Entity(title="菜单映色表",tableName="sys_menu_map")
public class MenuMap extends BaseModel{

	public static final int MENU_MAP_TYPE_ACCESS = 0;
	public static final int MENU_MAP_TYPE_ROLE = 1;
	public static final int MENU_MAP_TYPE_PREMISSION = 2;
	
	
	@Attribute(title="外键",length=20)
	@Column
	private long foreignKey;
	
	@Attribute(title="映射类型",length=1)
	@Column
	private int  mapType;
	
	@Attribute(title="菜单",length=20)
	@Column
	private long menuId;
	
	public long getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(long foreignKey) {
		this.foreignKey = foreignKey;
	}
	public int getMapType() {
		return mapType;
	}
	public void setMapType(int mapType) {
		this.mapType = mapType;
	}
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	
	
	
	
	
}
