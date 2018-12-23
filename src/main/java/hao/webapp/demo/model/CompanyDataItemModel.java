package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 公司数据项定义
 * @author chianghao
 *
 */
@Entity(title="公司数据项")
public class CompanyDataItemModel {

	/**
	 * 主键
	 */
	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	/**
	 * 公司主键
	 */
	@Attribute(title="公司")
	@Column
	private long companyId;
	/**
	 * 名称
	 */
	@Attribute(title="数据项名称")
	@Column
	private String name;
	/**
	 * 计算脚本
	 */
	@Attribute(title="脚本")
	@Column
	private String script;
	/***
	 * 备注
	 */
	@Attribute(title="备注")
	@Column
	private String remark;
	
	
	private Company company;
	
	
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
