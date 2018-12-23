package hao.webapp.demo.model;

import java.math.BigDecimal;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 个税比例
 * @author chianghao
 *
 */
@Entity(title="个税比例")
public class AppIncomeTax {

	/**
	 * 主键
	 */
	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	/**
	 * 下线
	 */
	@Attribute(title="下线 ",length=10,precision=2)
	@Column(isNull=true)
	private BigDecimal lowNum;
	/**
	 * 上线
	 */
	@Attribute(title="上线",length=10,precision=2)
	@Column(isNull=true)
	private BigDecimal upNum;
	/**
	 * 比例
	 */
	@Attribute(title="比例",length=2)
	@Column
	private BigDecimal proportion;
	
	
	@Attribute(title="速算扣除数",length=20,precision=2)
	@Column
	private BigDecimal sskcs;
	
	
	public BigDecimal getLowNum() {
		return lowNum;
	}
	public void setLowNum(BigDecimal lowNum) {
		this.lowNum = lowNum;
	}
	public BigDecimal getUpNum() {
		return upNum;
	}
	public void setUpNum(BigDecimal upNum) {
		this.upNum = upNum;
	}
	public BigDecimal getProportion() {
		return proportion;
	}
	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BigDecimal getSskcs() {
		return sskcs;
	}
	public void setSskcs(BigDecimal sskcs) {
		this.sskcs = sskcs;
	}
	
}
