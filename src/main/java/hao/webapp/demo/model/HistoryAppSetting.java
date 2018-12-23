package hao.webapp.demo.model;

import java.math.BigDecimal;
import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
/**
 * 历史应用参数
 * @author chianghao
 *
 */
@Entity(title="应用参数设置")
public class HistoryAppSetting {

	@Attribute(title="社保基数",precision=2)
	@Column
	private BigDecimal  sbjs;
	
	@Attribute(title="社保养老",precision=2)
	@Column
	private BigDecimal  sbblYanglao;
	
	@Attribute(title="社保医疗",precision=2)
	@Column
	private BigDecimal  sbblYiliao;
	
	@Attribute(title="社保失业",precision=2)
	@Column
	private BigDecimal  sbblShiye;
	
	@Attribute(title="公积金",precision=2)
	@Column
	private BigDecimal  sbblGongjijin;
	
	
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
}
