package hao.webapp.demo.model;

import java.math.BigDecimal;
import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
/**
 * 应用参数
 * @author chianghao
 *
 */
@Entity(title="应用参数设置")
public class AppSetting {

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
	private BigDecimal averageAnnualAttendanceDays;
	
	
	@Attribute(title="个税起征点",length=20,precision=2)
	@Column
	private BigDecimal taxStartPoint;
	
	public BigDecimal getGjjjs() {
		return gjjjs;
	}
	public void setGjjjs(BigDecimal gjjjs) {
		this.gjjjs = gjjjs;
	}
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
	public BigDecimal getAverageAnnualAttendanceDays() {
		return averageAnnualAttendanceDays;
	}
	public void setAverageAnnualAttendanceDays(BigDecimal averageAnnualAttendanceDays) {
		this.averageAnnualAttendanceDays = averageAnnualAttendanceDays;
	}
	public BigDecimal getTaxStartPoint() {
		return taxStartPoint;
	}
	public void setTaxStartPoint(BigDecimal taxStartPoint) {
		this.taxStartPoint = taxStartPoint;
	}
	
	
	
}
