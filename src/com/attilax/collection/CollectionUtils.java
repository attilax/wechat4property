/**
 * @author attilax 老哇的爪子
	@since  o7f h56e$
 */
package com.attilax.collection;

import com.attilax.Closure;
import com.attilax.ClosureNoExcpt;
import com.attilax.core;



import static com.attilax.core.*;

import java.util.*;
import java.util.Map.Entry;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;

import javax.management.RuntimeErrorException;

import org.apache.commons.beanutils.BeanUtils;
/** @author attilax 老哇的爪子
 * @since o7f h56e$ */
public class CollectionUtils {

	/**
	 * safe ver
	 * 
	 * @author attilax 老哇的爪子
	 * @since o7f h5759$
	 * 
	 * @param li
	 * @param closure 
	 * @return */
	public static <t> List each(List  li, final Closure closure) {
		// attilax 老哇的爪子 h5759 o7f
		final List li2 = new ArrayList<Object>();

		for (final Object string : li) {
			new com.attilax.tryX<t>() {
				@Override public t $$(Object t) throws Exception {
					// attilax 老哇的爪子 i450 o7f
					Object o = closure.execute(string);
					li2.add(o);
					return null;
				}
			}.$((t) "");

		}
		// attilax 老哇的爪子 i056 o7f
	 
		return li2;

	}

	/**
	 * 
	 @author attilax 老哇的爪子 \t@since Aug 16, 2014 10:56:50 PM$
	 * 
	 * @param li
	 * @param closure
	 * @return
	 */
	public static List each_NS(List  li, final Closure closure) {
		// attilax 老哇的爪子 h5759 o7f
		final List li2 = new ArrayList<Object>();

		for (final Object string : li) {

			// attilax 老哇的爪子 i450 o7f
			Object o;
			try {
				o = closure.execute(string);
			} catch (Exception e) {
				// attilax 老哇的爪子 10:57:21 PM Aug 16, 2014
				throw new RuntimeException(e);
			}
			li2.add(o);

		}
		// attilax 老哇的爪子 i056 o7f

		return li2;

	}

	// attilax 老哇的爪子 h56e o7f

	/** @author attilax 老哇的爪子
	 * @since o7f i056$
	 * 
	 * @param li
	 * @param closure */
	public static List each_safe(List li, final Closure closure) {
		final List li2 = new ArrayList<Object>();

		for (final Object string : li) {
			new com.attilax.tryX<String>() {
				@Override public String $$(Object t) throws Exception {
					// attilax 老哇的爪子 i450 o7f
					Object o = closure.execute(string);
					if(o!=null)
						li2.add(o);
					return null;
				}
			}.$("");

		}
		// attilax 老哇的爪子 i056 o7f
	 
		return li2;

	}

	/**  Deprecated cause use entry as item
	 *  @author attilax 老哇的爪子
	 * @since o7f mb46$
	 * 
	 * @param mp
	 * @param closure
	 * @return */	@SuppressWarnings("all") @Deprecated
	public static Map each_safe(Map mp, final Closure closure) {
		// attilax 老哇的爪子 mb46 o7f
	 final Map li2 = new HashMap();
		@SuppressWarnings("unchecked") List li_ori = new ArrayList(mp.entrySet());
		for (final Object item : li_ori) {
			new com.attilax.tryX<String>() {
				@Override public String $$(Object t) throws Exception {
					// attilax 老哇的爪子 i450 o7f
					Object o = closure.execute(item);
					//li2.entrySet().add(o);  UnsupportedOperationException
					Entry e = (Entry) o;
					li2.put(e.getKey(), e.getValue());
					return null;
				}
			}.$("");

		}

		return li2;

	}

	/**
	@author attilax 老哇的爪子
		@since  o7f m54b$
	
	 * @param mp
	 * @param closure
	 * @return
	 */
	public static Map each_safe_o7(Map mp, final Closure closure) {
		// attilax 老哇的爪子  m54b   o7f 
		 final Map li2 = new HashMap();
			@SuppressWarnings("unchecked") List li_ori = new ArrayList(mp.entrySet());
			for (final Object item : li_ori) {
				new com.attilax.tryX<String>() {
					@Override public String $$(Object t) throws Exception {
						// attilax 老哇的爪子 i450 o7f
						Entry e = (Entry) item;
						Object[] oa={e.getKey(),e.getValue()};
						Object val2 = closure.execute(oa);
						
						//li2.entrySet().add(o);  UnsupportedOperationException
						
						li2.put(e.getKey(),val2);
						return null;
					}
				}.$("");

			}

			return li2;
		
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o8e 0_0_45   
	
	 * @param mp
	 * @param closure
	 * @return
	 * @throws Exception 
	 */@SuppressWarnings("all") 
	public static List each(Map mp, final Closure closure) throws Exception {
		// attilax 老哇的爪子  m54b   o7f 
		 final List li2 = new ArrayList();
			List li_ori = new ArrayList(mp.entrySet());
			for (final Object item : li_ori) {
				 
						// attilax 老哇的爪子 i450 o7f
						Entry e = (Entry) item;
					//	Object[] oa={e.getKey(),e.getValue()};
						Object val2 = closure.execute(e);							
						li2.add( val2);
			}

			return li2;
		
	}
	 
	 /**
	  * 
	 @author attilax 老哇的爪子
	 	@since  o8e 0_q_a   
	 
	  * @param mp
	  * @param closure
	  * @return
	  * @throws Exception
	  */
	 public static List each_RE(Map mp, final Closure closure)   {
			// attilax 老哇的爪子  m54b   o7f 
		 
				try {
					return  each(mp,closure) ;
				} catch (Exception e) {
					//  attilax 老哇的爪子 0_r_c   o8e   
					throw new RuntimeException(e);
				}
			
		}

	/**
	@author attilax 老哇的爪子
		@since  o7g Z2x$
	
	 * @param flds
	 * @param closure
	 */
 

	/**
	@author attilax 老哇的爪子
		@since  o7g Z4l$
	
	 * @param flds
	 * @param closure
	 * @return 
	 * @throws cantFindOneMatchElmtException 
	 * @throws Exception 
	 */
	public static Object find(Field[] flds, ClosureNoExcpt closure) throws cantFindOneMatchElmtException   {
		// attilax 老哇的爪子  Z4l   o7g 
		for (Field field : flds) {
			if((Boolean) closure.execute(field))
				return field;
		}
		throw new  cantFindOneMatchElmtException("..cantFindOneMatchElmtException:");
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o7g h5637$
	
	 * @param ids
	 * @param closure
	 * @return
	 */
	public static Object each_safe(Integer[] ids, final Closure closure) {
		// attilax 老哇的爪子  h5637   o7g 
		final List li2 = new ArrayList<Object>();
		for (final Integer id : ids) {
		//	closure.execute(id);
			new com.attilax.tryX<String>() {
				@Override public String $$(Object t) throws Exception {
					// attilax 老哇的爪子 i450 o7f
					Object o = closure.execute(id);
					li2.add(o);
					return null;
				}
			}.$("");
		}
		
	
		return li2;
	 
		
	}

	/**  safe mode 
	@author attilax 老哇的爪子
		@since  o7g iex$
	
	 * @param findAll
	 * @param closure
	 * @return
	 * @throws Exception 
	 */
	public static List filter(List findAll, final Closure closure)  {
		// attilax 老哇的爪子  iex   o7g 
		
		final List li2 = new ArrayList<Object>();
		for (final Object object : findAll) {
			
			new com.attilax.tryX<String>() {
				@Override public String $$(Object t) throws Exception {
					// attilax 老哇的爪子 i450 o7f
					if(!((Boolean) closure.execute(object)))
						li2.add(object) ;
					else
						core.log("--filt id:"+ core.toJsonStr(object));
					return null;
				}
			}.$("");
		}
//		for (final Integer id : ids) {
//		//	closure.execute(id);
//			new com.attilax.tryX<String>() {
//				@Override public String $$(Object t) throws Exception {
//					// attilax 老哇的爪子 i450 o7f
//					Object o = closure.execute(id);
//					li2.add(o);
//					return null;
//				}
//			}.$("");
//		}
		
	
		return li2;
		 
	}

	/**
	@author attilax 老哇的爪子
		@since  o85 l_54_46$
	
	 * @param li
	 * @param closure
	 * @return
	 */
	public static Object reduce(List li,Object iniVal,  final Ireduce  IreduceImp) {
		// attilax 老哇的爪子  l_54_46   o85 
	//	final List li2 = new ArrayList<Object>();
		    Object lastVal = iniVal;
		for (final Object hd_objFmt : li) {			 
					lastVal= IreduceImp.$(hd_objFmt, lastVal);
				 
				 
		}
			 

	 
		// attilax 老哇的爪子 i056 o7f
	 
		return lastVal;

		
	}

	/**
	@author attilax 老哇的爪子
		@since  o8q m_46_51   
	
	 * @param li
	 * @return
	 */
	public static String getIDs(List li,String idName) {
		// attilax 老哇的爪子  m_46_51   o8q 
		String ids="";
		for (Object object : li) {
			try {
				String id=BeanUtils.getProperty(object, idName);
				ids=ids+","+id;
			} catch (IllegalAccessException e) {
				//  attilax 老哇的爪子 m_47_59   o8q   
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				//  attilax 老哇的爪子 m_47_59   o8q   
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				//  attilax 老哇的爪子 m_47_59   o8q   
				e.printStackTrace();
			}	  catch ( Exception e) {
				//  attilax 老哇的爪子 m_47_59   o8q 
				core.warn(e);
				e.printStackTrace();
			}
		}
		return ids;
		
	}

	/**
	@author attilax 老哇的爪子
	\t@since  Aug 30, 2014 8:27:27 PM$
	
	 * @return
	 */
	public static Integer Max(List li) {
		// attilax 老哇的爪子  8:27:27 PM   Aug 30, 2014 
		return (Integer) CollectionUtils.reduce(li, 0, new Ireduce() {

			@Override
			public Object $(Object o, Object lastRetOBj) {
				// attilax 老哇的爪子 11:12:10 PM Aug 16, 2014
				Object r = null;
				if(lastRetOBj  instanceof Integer)
				{
					if((Integer)lastRetOBj>(Integer)o)
					{
						return lastRetOBj;
					}				 
					return r;
				 
				}
				return r;

			}
		});
		
		
	}
	
	
	public static <t> Integer Max(List  li,String propName) {
		// attilax 老哇的爪子  8:27:27 PM   Aug 30, 2014 
		List li2=	CollectionUtils.getIDs_rLi(li, propName);
//		List li2=CollectionUtils.each(li,  new Closure () {
//
//			@Override
//			public Object execute(Object arg0) throws Exception {
//				// attilax 老哇的爪子  8:40:14 PM   Aug 30, 2014 
//				
//				{ 
//				return null;
//				 } 
//				
//				
//			}
//		});
		
		return (Integer) CollectionUtils.reduce(li2, 0, new Ireduce() {

			@Override
			public Object $(Object o, Object lastRetOBj) {
				// attilax 老哇的爪子 11:12:10 PM Aug 16, 2014
				Object r = null;
				if(lastRetOBj  instanceof Integer)
				{
					int int1 = core.toInt(o);
					
					if((Integer)lastRetOBj>int1)
					{
						return lastRetOBj;
					}				 
					return int1;
				 
				}
				return r;

			}
		});
		
		
	}

	/**
	@author attilax 老哇的爪子
	\t@since  Aug 30, 2014 8:42:53 PM$
	
	 * @param li
	 * @param propName
	 * @return
	 */
	@SuppressWarnings("all")
	private static List getIDs_rLi(List li, String propName) {
		// attilax 老哇的爪子  8:42:53 PM   Aug 30, 2014 
		List li2=new ArrayList ();
		for (Object object : li) {
			try {
				 
				String id=BeanUtils.getProperty(object, propName);
				li2.add(Integer.parseInt(id));
			} catch (IllegalAccessException e) {
				//  attilax 老哇的爪子 m_47_59   o8q   
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				//  attilax 老哇的爪子 m_47_59   o8q   
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				//  attilax 老哇的爪子 m_47_59   o8q   
				e.printStackTrace();
			}	  catch ( Exception e) {
				//  attilax 老哇的爪子 m_47_59   o8q 
				core.warn(e);
				e.printStackTrace();
			}
		}
		return  li2;
		
		
	}

	 
}

// attilax 老哇的爪子