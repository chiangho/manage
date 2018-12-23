package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 工资结算设置的津贴项目
 * @author  chianghao
 * @time    2018年3月31日
 * @version 0.1
 */
@Entity(title="工资津贴")
public class SalaryAllowance extends BaseModel{
	
	@Attribute(title="所属工资")
	@Column
	private long salaryId;
	/**
	 * 名称
	 */
	@Attribute(title="津贴名称")
	@Column
	private String name;
	/**
	 * 公司
	 */
	@Attribute(title="公司信息")
	@Column
	private String companyId;
	/**
	 * 公司名称
	 */
	private Company company;
	/**
	 * 费用计算公式
	 */
	@Attribute(title="费用计算公式")
	@Column
	private String summayScript; 
	/**
	 *  备注说明
	 */
	@Attribute(title="备注说明")
	@Column
	private String remark;
	
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
	public String getSummayScript() {
		return summayScript;
	}
	public void setSummayScript(String summayScript) {
		this.summayScript = summayScript;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
