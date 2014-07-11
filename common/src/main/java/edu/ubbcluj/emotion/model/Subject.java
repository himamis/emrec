package edu.ubbcluj.emotion.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "subject")
public class Subject implements Iterable<Sequence>, Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "subject_id")
	private Long				id;

	@Column(name = "subject", unique = true)
	private String				subject;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "subject_sequences", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "sequence_id"))
	private List<Sequence>		sequences;

	public Subject() {
	}

	public Subject(final String subject) {
		this.subject = subject;
	}

	public Subject(final String subject, final List<Sequence> sequences) {
		this.subject = subject;
		this.sequences = sequences;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Sequence> getSequences() {
		return sequences;
	}

	public void setSequences(final List<Sequence> sequences) {
		this.sequences = sequences;
	}

	@Transient
	public Sequence get(final int index) {
		return sequences.get(index);
	}

	public int size() {
		return sequences.size();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public Iterator<Sequence> iterator() {
		return sequences.iterator();
	}

}
