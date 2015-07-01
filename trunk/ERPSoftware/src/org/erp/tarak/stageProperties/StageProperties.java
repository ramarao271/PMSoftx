package org.erp.tarak.stageProperties;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "StageProperties")
public class StageProperties implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="stagePropertiesSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long stagePropertiesId;
	private String stagePropertiesName;

	public long getStagePropertiesId() {
		return stagePropertiesId;
	}

	public void setStagePropertiesId(long stagePropertiesId) {
		this.stagePropertiesId = stagePropertiesId;
	}

	public String getStagePropertiesName() {
		return stagePropertiesName;
	}

	public void setStagePropertiesName(String stagePropertiesName) {
		this.stagePropertiesName = stagePropertiesName;
	}

}
