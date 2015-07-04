package org.erp.tarak.subVariantProperties;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SubVariantProperties")
public class SubVariantProperties implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="subVariantPropertiesSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long subVariantPropertiesId;
	private String subVariantPropertiesName;

	public long getSubVariantPropertiesId() {
		return subVariantPropertiesId;
	}

	public void setSubVariantPropertiesId(long subVariantPropertiesId) {
		this.subVariantPropertiesId = subVariantPropertiesId;
	}

	public String getSubVariantPropertiesName() {
		return subVariantPropertiesName;
	}

	public void setSubVariantPropertiesName(String subVariantPropertiesName) {
		this.subVariantPropertiesName = subVariantPropertiesName;
	}

}
