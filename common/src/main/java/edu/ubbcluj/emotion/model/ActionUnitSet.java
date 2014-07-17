package edu.ubbcluj.emotion.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

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

@Entity
@Table(name = "actionunitlist")
public class ActionUnitSet implements Iterable<ActionUnit>, Serializable, DatasetKey {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "actionunitlist_id")
	private Long				id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "actionunitlist_actionunits", joinColumns = @JoinColumn(name = "actionunitlist_id"), inverseJoinColumns = @JoinColumn(name = "actionunit_id"))
	private Set<ActionUnit>		actionUnits;

	public ActionUnitSet() {
	}

	public ActionUnitSet(Set<ActionUnit> actionUnits) {
		this.actionUnits = actionUnits;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int size() {
		return actionUnits.size();
	}

	@Override
	public Iterator<ActionUnit> iterator() {
		return actionUnits.iterator();
	}

	public Set<ActionUnit> getActionUnits() {
		return actionUnits;
	}

	public void setActionUnits(Set<ActionUnit> actionUnits) {
		this.actionUnits = actionUnits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionUnits == null) ? 0 : actionUnits.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionUnitSet other = (ActionUnitSet) obj;
		if (actionUnits == null) {
			if (other.actionUnits != null)
				return false;
		} else if (!actionUnits.equals(other.actionUnits))
			return false;
		return true;
	}
	
}
