package hao.webapp.demo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 工资批次
 * @author chianghao
 *
 */
@Entity(title="员工工资")
public class SalaryBatchEmployee extends BaseModel{

	@Attribute(title="结算批次")
	@Column
	private long salaryBatchId;
	//身份证
	private String  idCard;
	//工号
	private String  workerNo;
	//结算工资时员工当时的信息
	private HistoryEmploymentInfo employmentInfo;
	//员工基本信息
	private Employee              employee;
	//工资
	private BigDecimal salary;
	//实际工资（即发放）
	private BigDecimal realSalary;
	//发放时间
	private Date       paymentDay;
	
	
	
	
}
