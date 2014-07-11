package edu.ubbcluj.emotion.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "actionunitlist")
public class ActionUnitList implements Iterable<ActionUnit>, Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "actionunitlist_id")
	private Long				id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "actionunitlist_actionunits", joinColumns = @JoinColumn(name = "actionunitlist_id"), inverseJoinColumns = @JoinColumn(name = "actionunit_id"))
	private List<ActionUnit>	actionUnits;

	public ActionUnitList() {
	}

	public ActionUnitList(List<ActionUnit> actionUnits) {
		this.actionUnits = actionUnits;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public ActionUnit get(int index) {
		return actionUnits.get(index);
	}

	public int size() {
		return actionUnits.size();
	}

	@Override
	public Iterator<ActionUnit> iterator() {
		return actionUnits.iterator();
	}

	public List<ActionUnit> getActionUnits() {
		return actionUnits;
	}

	public void setActionUnits(List<ActionUnit> actionUnits) {
		this.actionUnits = actionUnits;
	}

}
