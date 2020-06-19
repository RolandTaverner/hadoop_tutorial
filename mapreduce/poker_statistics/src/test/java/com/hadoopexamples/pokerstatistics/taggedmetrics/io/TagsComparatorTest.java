package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import java.util.SortedMap;
import java.util.TreeMap;

import com.hadoopexamples.pokerstatistics.taggedmetrics.io.TagsComparator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TagsComparatorTest extends TestCase {
	public TagsComparatorTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TagsComparatorTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testTagsComparatorEqual() {
		SortedMap<Integer, String> lhs = new TreeMap<Integer, String>();
		lhs.put(0, "a");
		lhs.put(1, "a");
		lhs.put(2, "a");
		
		SortedMap<Integer, String> rhs = new TreeMap<Integer, String>();
		rhs.put(0, "a");
		rhs.put(1, new String("a"));
		rhs.put(2, new String("a"));
		
		assertTrue(TagsComparator.compare(lhs, rhs) == 0);
	}
	
	public void testTagsComparatorLesser() {
		SortedMap<Integer, String> lhs = new TreeMap<Integer, String>();
		lhs.put(0, "a");
		lhs.put(1, "a");
		lhs.put(2, "a");
		
		SortedMap<Integer, String> rhs = new TreeMap<Integer, String>();
		rhs.put(0, "a");
		rhs.put(1, "b");
		rhs.put(2, "a");
		
		assertTrue(TagsComparator.compare(lhs, rhs) == -1);
	}
	
	public void testTagsComparatorGreater() {
		SortedMap<Integer, String> lhs = new TreeMap<Integer, String>();
		lhs.put(0, "a");
		lhs.put(1, "a");
		lhs.put(2, "b");
		
		SortedMap<Integer, String> rhs = new TreeMap<Integer, String>();
		rhs.put(0, "a");
		rhs.put(1, "a");
		rhs.put(2, "a");
		
		assertTrue(TagsComparator.compare(lhs, rhs) == 1);
	}
	
	public void testTagsComparator3() {
		SortedMap<Integer, String> lhs = new TreeMap<Integer, String>();
		lhs.put(2, "c");
		lhs.put(1, "b");
		lhs.put(0, "a");
		
		SortedMap<Integer, String> rhs = new TreeMap<Integer, String>();
		rhs.put(0, "a");
		rhs.put(1, "b");
		rhs.put(2, "b");
		
		assertTrue(TagsComparator.compare(lhs, rhs) == 1);
	}
}
