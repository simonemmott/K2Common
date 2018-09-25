package com.k2.common;

import com.k2.Util.StringUtil;

/**
 * This Error class is the root of all unchecked errors defined by the k2 utilities
 * 
 * @author simon
 *
 */
public class K2MetaDataError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1286812673410888220L;
	/**
	 * Create an meta data error with the given message
	 * @param message	The error message
	 * @param replacements The objects to convert to strings to replace instances of '{}' in the message
	 */
	public K2MetaDataError(String message, Object ... replacements) {
		super(StringUtil.replaceAll(message, "{}", replacements));
	}
	/**
	 * Create an meta data error for the given throwable cause
	 * @param cause	The throwable that gave rise to this error
	 */
	public K2MetaDataError(Throwable cause) {
		super(cause);
	}
	/**
	 * Create an meta data error for the given message and throwable cause
	 * @param message	The error message
	 * @param cause	The throwable cause
	 * @param replacements The objects to convert to strings to replace instances of '{}' in the message
	 */
	public K2MetaDataError(String message, Throwable cause, Object ... replacements) {
		super(StringUtil.replaceAll(message, "{}", replacements), cause);
	}
	/**
	 * Create an meta data error for the given message and throwable cause defining whether the error can be suppressed and
	 * Whether the stack trace should be writable. 
	 * @param message	The error message
	 * @param cause	The throwable cause
	 * @param enableSuppression	True if the error can be suppressed
	 * @param writableStackTrace	True if the stack trace should be writable
	 * @param replacements The objects to convert to strings to replace instances of '{}' in the message
	 */
	public K2MetaDataError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object ... replacements) {
		super(StringUtil.replaceAll(message, "{}", replacements), cause, enableSuppression, writableStackTrace);
	}

}
