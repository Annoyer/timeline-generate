package org.jcy.timeline.core.model;

import org.jcy.timeline.util.Assertion;

/**
 * Timeline Item.
 */
public abstract class Item implements Comparable<Item> {

	protected final long timeStamp;
	protected final String id;

	public long getTimeStamp() {
		return this.timeStamp;
	}

	public String getId() {
		return this.id;
	}

	/**
	 * Create an Item with specific timestamp and id.
	 * @param timeStamp
	 * @param id
	 */
	public Item(long timeStamp, String id) {
		Assertion.check(timeStamp >= 0, "TIMESTAMP_MUST_NON_NEGATIVE_LONG", timeStamp);
		Assertion.check(id != null, "ID_MUST_NOT_BE_NULL");

		this.timeStamp = timeStamp;
		this.id = id;
	}

	@Override()
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.hashCode();
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		if (!id.equals(other.id)) {
			return false;
		}
		if (timeStamp != other.timeStamp) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this object with the specified object for order.  Returns anegative integer, zero, or a positive integer as this object is lessthan, equal to, or greater than the specified object.<p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==-sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (Thisimplies that <tt>x.compareTo(y)</tt> must throw an exception iff<tt>y.compareTo(x)</tt> throws an exception.)<p>The implementor must also ensure that the relation is transitive:<tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies<tt>x.compareTo(z)&gt;0</tt>.<p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, forall <tt>z</tt>.<p>It is strongly recommended, but <i>not</i> strictly required that<tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, anyclass that implements the <tt>Comparable</tt> interface and violatesthis condition should clearly indicate this fact.  The recommendedlanguage is "Note: this class has a natural ordering that isinconsistent with equals."<p>In the foregoing description, the notation<tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical<i>signum</i> function, which is defined to return one of <tt>-1</tt>,<tt>0</tt>, or <tt>1</tt> according to whether the value of<i>expression</i> is negative, zero or positive.
	 * Use the timestamp and id to compare the items.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 * @throws ClassCastException if the specified object's type prevents it from being compared to this object.
	 */
	public int compareTo(Item o) {
		int result = Long.compare(getTimeStamp(), o.getTimeStamp());
		if (result == 0) {
			result = getId().compareTo(o.getId());
		}
		return result;
	}

}