/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cr.freitonal.client.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * Operations on {@link java.lang.String} that are <code>null</code> safe.
 * </p>
 * 
 * <ul>
 * <li><b>IsEmpty/IsBlank</b> - checks if a String contains text</li>
 * <li><b>Trim/Strip</b> - removes leading and trailing whitespace</li>
 * <li><b>Equals</b> - compares two strings null-safe</li>
 * <li><b>startsWith</b> - check if a String starts with a prefix null-safe</li>
 * <li><b>endsWith</b> - check if a String ends with a suffix null-safe</li>
 * <li><b>IndexOf/LastIndexOf/Contains</b> - null-safe index-of checks
 * <li><b>IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut</b> -
 * index-of any of a set of Strings</li>
 * <li><b>ContainsOnly/ContainsNone/ContainsAny</b> - does String contains
 * only/none/any of these characters</li>
 * <li><b>Substring/Left/Right/Mid</b> - null-safe substring extractions</li>
 * <li><b>SubstringBefore/SubstringAfter/SubstringBetween</b> - substring
 * extraction relative to other strings</li>
 * <li><b>Split/Join</b> - splits a String into an array of substrings and vice
 * versa</li>
 * <li><b>Remove/Delete</b> - removes part of a String</li>
 * <li><b>Replace/Overlay</b> - Searches a String and replaces one String with
 * another</li>
 * <li><b>Chomp/Chop</b> - removes the last part of a String</li>
 * <li><b>LeftPad/RightPad/Center/Repeat</b> - pads a String</li>
 * <li><b>UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize</b> - changes the
 * case of a String</li>
 * <li><b>CountMatches</b> - counts the number of occurrences of one String in
 * another</li>
 * <li><b>IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable</b> - checks the
 * characters in a String</li>
 * <li><b>DefaultString</b> - protects against a null input String</li>
 * <li><b>Reverse/ReverseDelimited</b> - reverses a String</li>
 * <li><b>Abbreviate</b> - abbreviates a string using ellipsis</li>
 * <li><b>Difference</b> - compares Strings and reports on their differences</li>
 * <li><b>LevensteinDistance</b> - the number of changes needed to change one
 * String into another</li>
 * </ul>
 * 
 * <p>
 * The <code>StringUtils</code> class defines certain words related to String
 * handling.
 * </p>
 * 
 * <ul>
 * <li>null - <code>null</code></li>
 * <li>empty - a zero-length string (<code>""</code>)</li>
 * <li>space - the space character (<code>' '</code>, char 32)</li>
 * <li>whitespace - the characters defined by
 * {@link Character#isWhitespace(char)}</li>
 * <li>trim - the characters &lt;= 32 as in {@link String#trim()}</li>
 * </ul>
 * 
 * <p>
 * <code>StringUtils</code> handles <code>null</code> input Strings quietly.
 * That is to say that a <code>null</code> input will return <code>null</code>.
 * Where a <code>boolean</code> or <code>int</code> is being returned details
 * vary by method.
 * </p>
 * 
 * <p>
 * A side effect of the <code>null</code> handling is that a
 * <code>NullPointerException</code> should be considered a bug in
 * <code>StringUtils</code> (except for deprecated methods).
 * </p>
 * 
 * <p>
 * Methods in this class give sample code to explain their operation. The symbol
 * <code>*</code> is used to indicate any input including <code>null</code>.
 * </p>
 * 
 * @see java.lang.String
 * @author Apache Software Foundation
 * @author <a href="http://jakarta.apache.org/turbine/">Apache Jakarta
 *         Turbine</a>
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author Daniel L. Rall
 * @author <a href="mailto:gcoladonato@yahoo.com">Greg Coladonato</a>
 * @author <a href="mailto:ed@apache.org">Ed Korthof</a>
 * @author <a href="mailto:rand_mcneely@yahoo.com">Rand McNeely</a>
 * @author <a href="mailto:fredrik@westermarck.com">Fredrik Westermarck</a>
 * @author Holger Krauth
 * @author <a href="mailto:alex@purpletech.com">Alexander Day Chaffee</a>
 * @author <a href="mailto:hps@intermeta.de">Henning P. Schmiedehausen</a>
 * @author Arun Mammen Thomas
 * @author Gary Gregory
 * @author Phil Steitz
 * @author Al Chou
 * @author Michael Davey
 * @author Reuben Sivan
 * @author Chris Hyzer
 * @author Scott Johnson
 * @since 1.0
 * @version $Id: StringUtils.java 911986 2010-02-19 21:19:05Z niallp $
 */
public class StringUtils {
	// Performance testing notes (JDK 1.4, Jul03, scolebourne)
	// Whitespace:
	// Character.isWhitespace() is faster than WHITESPACE.indexOf()
	// where WHITESPACE is a string of all whitespace characters
	//
	// Character access:
	// String.charAt(n) versus toCharArray(), then array[n]
	// String.charAt(n) is about 15% worse for a 10K string
	// They are about equal for a length 50 string
	// String.charAt(n) is about 4 times better for a length 3 string
	// String.charAt(n) is best bet overall
	//
	// Append:
	// String.concat about twice as fast as StringBuffer.append
	// (not sure who tested this)

	/**
	 * The empty String <code>""</code>.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	// Empty checks
	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	/**
	 * <p>
	 * Joins the elements of the provided <code>Iterator</code> into a single
	 * String containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 * 
	 * @param iterator
	 *            the <code>Iterator</code> of values to join together, may be
	 *            null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, <code>null</code> if null iterator input
	 * @since 2.0
	 */
	public static String join(Iterator iterator, char separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			first.toString();
		}

		// two or more elements
		StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided <code>Iterator</code> into a single
	 * String containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String ("").
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 * 
	 * @param iterator
	 *            the <code>Iterator</code> of values to join together, may be
	 *            null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null iterator input
	 */
	public static String join(Iterator iterator, String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			first.toString();
		}

		// two or more elements
		StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided <code>Collection</code> into a single
	 * String containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 * 
	 * @param collection
	 *            the <code>Collection</code> of values to join together, may be
	 *            null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, <code>null</code> if null iterator input
	 * @since 2.3
	 */
	public static String join(Collection collection, char separator) {
		if (collection == null) {
			return null;
		}
		return join(collection.iterator(), separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided <code>Collection</code> into a single
	 * String containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String ("").
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 * 
	 * @param collection
	 *            the <code>Collection</code> of values to join together, may be
	 *            null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null iterator input
	 * @since 2.3
	 */
	public static String join(Collection collection, String separator) {
		if (collection == null) {
			return null;
		}
		return join(collection.iterator(), separator);
	}
}
