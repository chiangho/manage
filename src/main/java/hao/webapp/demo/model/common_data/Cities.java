package hao.webapp.demo.model.common_data;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

@Entity(title="城市信息")
public class Cities {

	@Attribute(title="主键",length=11)
	@Column(primary=true)
	private int id;
	
	@Attribute(title="城市编号")
	@Column
	private String cityid;
	
	@Attribute(title="城市名称")
	@Column
	private String city;
	
	@Attribute(title="身份编号")
	@Column
	private String provinceid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	
	
	
	
}
