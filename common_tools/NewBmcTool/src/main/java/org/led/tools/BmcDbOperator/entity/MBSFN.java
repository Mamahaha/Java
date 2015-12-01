package org.led.tools.BmcDbOperator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class MBSFN {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER, mappedBy = "mbsfn")
	private List<PMCH> pmchList;	
	
	@Index
	@ManyToOne
	@ForeignKey
	@JoinColumn(name = "ENODEB_ID")
	private ENodeB eNodeB;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<PMCH> getPmchList() {
		return pmchList;
	}

	public void setPmchList(List<PMCH> pmchList) {
		this.pmchList = pmchList;
	}

	public ENodeB getENodeB() {
		return eNodeB;
	}

	public void setENodeB(ENodeB eNodeB) {
		this.eNodeB = eNodeB;
	}
}
