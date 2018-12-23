package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 公司的基础大项目
 * @author chianghao
 *
 */
@Entity(title="项目")
public class CompanyProject extends BaseModel{

	@Attribute(title="项目名称")
	@Column(isNull=true)
	private String name;
	
	@Attribute(title="所属公司")
	@Column(isNull=true)
	private String companyId;
	/**
	 * 公司信息
	 */
	private Company company;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
