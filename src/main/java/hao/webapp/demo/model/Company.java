package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 公司信息
 * @author chianghao
 *
 */
@Entity(title="公司信息")
public class Company {
	/***
	 * 主键
	 */
	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	/**
	 * 名称
	 */
	@Attribute(title="名称")
	@Column
	private String name;
	
	@Attribute(title="是否删除",remark="1表示删除，0表示未删除")
	@Column
	private int isDel;
	
	
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	
}
