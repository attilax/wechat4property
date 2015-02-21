/**
 * @author attilax 老哇的爪子
	@since  o8s i_54_58$
 */
package com.attilax.lang;

import com.attilax.core;
import static com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/** @author attilax 老哇的爪子
 * @since o8s i_54_58$ */
@SuppressWarnings("all") public class casexT {

	/** @author attilax 老哇的爪子
	 * @since o8s i_54_58
	 * 
	 * @param args */
	public static void main(String[] args) {
	 

		// CaseX.$().<String>start()
		// attilax 老哇的爪子 i_54_58 o8s
		String s = CaseX.$().add(new IcaseitemImp(new Icondit() {

			@Override public boolean isConditOk() {
				// attilax 老哇的爪子 l_58_s o8s
				return true;

			}

		}.$()) {
			/** @category case1 */
			@Override public Object exec() {
				// attilax 老哇的爪子 j_d_4 o8s
				System.out.println(" case111");
				return true;

			}

		}).add(new IcaseitemImp(6 > 5) {
			/** @category case2 */
			@Override public Object exec() {
				// attilax 老哇的爪子 j_d_7 o8s
				System.out.println(" case22");
				return false;

			}

		}).start().toString();
		System.out.println("===rzt:" + s);

	}
	// attilax 老哇的爪子 i_54_58 o8s
}

// attilax 老哇的爪子