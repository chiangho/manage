package hao.webapp.demo.model.common_data;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 身份信息
 * @author chianghao
 *
 */
@Entity(title="身份信息")
public class Provinces {

	@Attribute(title="主键",length=11)
	@Column(primary=true)
	private int id;
	
	@Attribute(title="身份编号")
	@Column
	private String provinceid;
	
	
	@Attribute(title="身份名称")
	@Column
	private String province;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
}
