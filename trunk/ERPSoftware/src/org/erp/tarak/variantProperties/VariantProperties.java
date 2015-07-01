package org.erp.tarak.variantProperties;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VariantProperties")
public class VariantProperties implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="variantPropertiesSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long variantPropertiesId;
	private String variantPropertiesName;
	private String variantPropertiesType;

	public long getVariantPropertiesId() {
		return variantPropertiesId;
	}

	public void setVariantPropertiesId(long variantPropertiesId) {
		this.variantPropertiesId = variantPropertiesId;
	}

	public String getVariantPropertiesName() {
		return variantPropertiesName;
	}

	public void setVariantPropertiesName(String variantPropertiesName) {
		this.variantPropertiesName = variantPropertiesName;
	}

	public String getVariantPropertiesType() {
		return variantPropertiesType;
	}

	public void setVariantPropertiesType(String variantPropertiesType) {
		this.variantPropertiesType = variantPropertiesType;
	}

}
