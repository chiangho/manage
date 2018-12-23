package hao.webapp.demo.model.system;

import java.math.BigDecimal;
import java.util.List;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

@Entity(tableName="sys_menu",title="系统菜单")
public class Menu extends BaseModel{
	
	/***
	 * 根节点的编号
	 */
	public static final int MENU_ROOT=0;

	@Attribute(title="名称")
	@Column
	private String name;
	
	@Attribute(title="地址",length=1000)
	@Column(isNull=true)
	private String url;
	
	@Attribute(title="深度",length=20)
	@Column
	private int level;
	
	@Attribute(title="父节点",length=20)
	@Column
	private long parentId;
	
	@Attribute(title="排序",length=20,precision=5)
	@Column
	private BigDecimal sort;
	
	private List<Menu> subMenuList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public List<Menu> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<Menu> subMenuList) {
		this.subMenuList = subMenuList;
	}
	
	
}
