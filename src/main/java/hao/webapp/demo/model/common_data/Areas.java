package hao.webapp.demo.model.common_data;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;


@Entity(title="区域")
public class Areas {

	@Attribute(title="主键",length=11)
	@Column(primary=true)
	private int     id;
	
	@Attribute(title="区域编号")
	@Column
	private String  areaid;
	
	@Attribute(title="区域名称")
	@Column
	private String  area;
	
	@Attribute(title="城市编号")
	@Column
	private String  cityid;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	 
	 
	
}
