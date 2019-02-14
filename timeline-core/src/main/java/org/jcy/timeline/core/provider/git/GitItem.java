package org.jcy.timeline.core.provider.git;

import org.jcy.timeline.core.model.Item;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitItem extends Item {

	private final String content;
	private final String author;

	public String getContent() {
		return this.content;
	}

	public String getAuthor() {
		return this.author;
	}

	/**
	 *
	 * @param id
	 * @param timeStamp
	 * @param author
	 * @param content
	 */
	public GitItem(String id, long timeStamp, String author, String content) {
		super(timeStamp, id);
		// TODO - implement GitItem.GitItem
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param commit
	 */
	static String getContent(RevCommit commit) {
		// TODO - implement GitItem.getTimeStamp
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param commit
	 */
	static String getAuthor(RevCommit commit) {
		// TODO - implement GitItem.getTimeStamp
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parameter
	 */
	static long getTimeStamp(RevCommit parameter) {
		// TODO - implement GitItem.getTimeStamp
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param commit
	 */
	static String getId(RevCommit commit) {
		// TODO - implement GitItem.getId
		throw new UnsupportedOperationException();
	}

	/**
	 * Compares this object with the specified object for order.  Returns anegative integer, zero, or a positive integer as this object is lessthan, equal to, or greater than the specified object.<p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==-sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (Thisimplies that <tt>x.compareTo(y)</tt> must throw an exception iff<tt>y.compareTo(x)</tt> throws an exception.)<p>The implementor must also ensure that the relation is transitive:<tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies<tt>x.compareTo(z)&gt;0</tt>.<p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, forall <tt>z</tt>.<p>It is strongly recommended, but <i>not</i> strictly required that<tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, anyclass that implements the <tt>Comparable</tt> interface and violatesthis condition should clearly indicate this fact.  The recommendedlanguage is "Note: this class has a natural ordering that isinconsistent with equals."<p>In the foregoing description, the notation<tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical<i>signum</i> function, which is defined to return one of <tt>-1</tt>,<tt>0</tt>, or <tt>1</tt> according to whether the value of<i>expression</i> is negative, zero or positive.
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 * @throws ClassCastException if the specified object's type prevents it from being compared to this object.
	 */
	public int compareTo(Item o) {
		// TODO - implement GitItem.compareTo
		throw new UnsupportedOperationException();
	}

}