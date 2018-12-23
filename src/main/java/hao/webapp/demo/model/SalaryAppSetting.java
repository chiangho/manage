package hao.webapp.demo.model;

import java.math.BigDecimal;
import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;


/**
 * 工资结算需要的社保信息
 * @author chianghao
 *
 */
@Entity(title="应用参数设置")
public class SalaryAppSetting {

	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	
	@Attribute(title="所属工资")
	@Column
	private long salaryId;
	
	@Attribute(title="公积金基数",length=10,precision=2)
	@Column
	private BigDecimal  gjjjs;
	
	@Attribute(title="社保基数",length=10,precision=2)
	@Column
	private BigDecimal  sbjs;
	
	@Attribute(title="社保养老",length=10,precision=2)
	@Column
	private BigDecimal  sbblYanglao;
	
	@Attribute(title="社保医疗",length=10,precision=2)
	@Column
	private BigDecimal  sbblYiliao;
	
	@Attribute(title="社保失业",length=10,precision=2)
	@Column
	private BigDecimal  sbblShiye;
	
	@Attribute(title="公积金",length=10,precision=2)
	@Column
	private BigDecimal  sbblGongjijin;
	
	@Attribute(title="年平均出勤天数",length=10,precision=2)
	@Column
	private String averageAnnualAttendanceDays;
	
	
	@Attribute(title="个税起征点",length=20,precision=2)
	@Column
	private BigDecimal taxStartPoint;
	
	public BigDecimal getSbjs() {
		return sbjs;
	}
	public void setSbjs(BigDecimal sbjs) {
		this.sbjs = sbjs;
	}
	public BigDecimal getSbblYanglao() {
		return sbblYanglao;
	}
	public void setSbblYanglao(BigDecimal sbblYanglao) {
		this.sbblYanglao = sbblYanglao;
	}
	public BigDecimal getSbblYiliao() {
		return sbblYiliao;
	}
	public void setSbblYiliao(BigDecimal sbblYiliao) {
		this.sbblYiliao = sbblYiliao;
	}
	public BigDecimal getSbblShiye() {
		return sbblShiye;
	}
	public void setSbblShiye(BigDecimal sbblShiye) {
		this.sbblShiye = sbblShiye;
	}
	public BigDecimal getSbblGongjijin() {
		return sbblGongjijin;
	}
	public void setSbblGongjijin(BigDecimal sbblGongjijin) {
		this.sbblGongjijin = sbblGongjijin;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(long salaryId) {
		this.salaryId = salaryId;
	}
	public String getAverageAnnualAttendanceDays() {
		return averageAnnualAttendanceDays;
	}
	public void setAverageAnnualAttendanceDays(String averageAnnualAttendanceDays) {
		this.averageAnnualAttendanceDays = averageAnnualAttendanceDays;
	}
	public BigDecimal getGjjjs() {
		return gjjjs;
	}
	public void setGjjjs(BigDecimal gjjjs) {
		this.gjjjs = gjjjs;
	}
	public BigDecimal getTaxStartPoint() {
		return taxStartPoint;
	}
	public void setTaxStartPoint(BigDecimal taxStartPoint) {
		this.taxStartPoint = taxStartPoint;
	}
	
	
	
}
