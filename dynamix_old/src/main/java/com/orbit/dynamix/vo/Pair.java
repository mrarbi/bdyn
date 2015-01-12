package com.orbit.dynamix.vo;

public class Pair<ID, VALUE> {

	private ID id;

	private VALUE value;

	public Pair(ID id, VALUE value) {
		this.id = id;
		this.value = value;
	}

	public ID getId() {
		return id;
	}

	public VALUE getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return id.hashCode() ^ value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Pair))
			return false;
		Pair pairo = (Pair) o;
		return this.id.equals(pairo.getId()) && this.value.equals(pairo.getValue());
	}

}
