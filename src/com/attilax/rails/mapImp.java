/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-23 下午3:14:37$
 */
package com.attilax.rails;

import java.util.HashMap;

/**
 *  * @author  attilax 老哇的爪子
 * @param <V>
 * @param <K>
 *@since  2014-6-23 下午3:14:37$
 *
 */
public class mapImp< K,V> extends HashMap<K, V> {
	//  attilax 老哇的爪子 下午3:14:38   2014-6-23   
	
	public mapImp add(K key,V value){
		put(key, value);
		return this;
	}
}

//  attilax 老哇的爪子