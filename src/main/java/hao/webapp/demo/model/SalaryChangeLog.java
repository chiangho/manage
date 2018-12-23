package hao.webapp.demo.model;

import java.math.BigDecimal;
import java.util.Date;
import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 工资调整记录
 * @author chianghao
 *
 */
@Entity(title="工资调整日志")
public class SalaryChangeLog {

	/**
	 * 主键
	 */
	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	/**
	 * 日期
	 */
	@Attribute(title="调薪日期")
	@Column
	private Date date;
	/**
	 * 工号
	 */
	@Attribute(title="工号")
	@Column
	private long workerNo;
	/**
	 * 调整时当前工资计算方式
	 */
	@Attribute(title="调薪当前月薪资结算方式",remark="0调整后算，1一半对一半算，2 按调整前工资算")
	@Column(defaultValue="1")
	private int  currentSalaryType;
	/**
	 * 调整后的工资
	 */
	@Attribute(title="调薪前工资")
	@Column
	private BigDecimal afterSalary;
	/**
	 * 调整前工资
	 */
	@Attribute(title="调薪后工资")
	@Column
	private BigDecimal beforeSslary;
	
	/**
	 * 身份证号码
	 */
	@Attribute(title="员工身份号")
	@Column
	private String idCard;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getWorkerNo() {
		return workerNo;
	}
	public void setWorkerNo(long workerNo) {
		this.workerNo = workerNo;
	}
	public int getCurrentSalaryType() {
		return currentSalaryType;
	}
	public void setCurrentSalaryType(int currentSalaryType) {
		this.currentSalaryType = currentSalaryType;
	}
	public BigDecimal getAfterSalary() {
		return afterSalary;
	}
	public void setAfterSalary(BigDecimal afterSalary) {
		this.afterSalary = afterSalary;
	}
	public BigDecimal getBeforeSslary() {
		return beforeSslary;
	}
	public void setBeforeSslary(BigDecimal beforeSslary) {
		this.beforeSslary = beforeSslary;
	}
	
}
