package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 历史工资项
 * @author  chianghao
 * @time    2018-3-31
 * @version 0.1
 */
@Entity(title="工资项")
public class HistoryCompanySalaryItem  extends BaseModel{

	@Attribute(title="公司",length=20)
	@Column
	private long   companyId;
	
	@Attribute(title="工资项")
	@Column
	private String name;
	/***
	 * 工资操作符号  0表示扣工资  1表示加工资
	 */
	@Attribute(title="计算符")
	@Column
	private String operation;
	
	@Attribute(title="脚本")
	@Column
	private String script;
	
	@Attribute(title="备注")
	@Column
	private String remark;

	
	private Company company;
	
	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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
	
	
	
	
	
}
