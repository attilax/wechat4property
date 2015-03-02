package com.attilax.corePkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

import com.attilax.t;
import com.attilax.io.pathx;

/**  must extends ClassLoader  ,biers defineClass cant use..its protect level
 * 动态加载class文件
 * 
 * @author Ken
 * @since 2013-02-17
 * 
 */
public class DynamicClassLoader extends ClassLoader  {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		dasf a=DynamicClassLoader.newNew0(dasf.class);
		System.out.println(a);
//		try {
//		Class<dasf> cls=(Class<dasf>) Class.forName(dasf.class.getCanonicalName(),true, new DynamicClassLoader());
//		dasf b=cls.newInstance();
//		System.out.println(b);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	public static<t> t newNew0(Class<t> class1) {
		Class<t> cls = null;
		try {
			cls = (Class<t>) Class.forName(class1.getCanonicalName(),true, new DynamicClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t b = null;
		try {
			b = cls.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public static <t> t newNew(Class<t> cls) {
		DynamicClassLoader loader = new DynamicClassLoader();
		Class<t> clazz;
		try {
			clazz = (Class<t>) loader.loadClass(pathx.classPath(),
					cls.getCanonicalName());
		} catch (ClassNotFoundException e) {
		 
			throw new RuntimeException("ClassNotFoundException:"
					+ cls.getCanonicalName(), e);
		}
		// Method method = clazz.getMethod("sayHello", String.class);
		try {
			return (clazz.newInstance());
		} catch (InstantiationException e) {

			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	// 文件最后修改时间
	private long lastModified;

	// 加载class文件的classpath
	private String classPath;

	/**
	 * 检测class文件是否被修改
	 * 
	 * @param filename
	 * @return
	 */
	private boolean isClassModified(String name) {
		File file = getFile(name);
		if (file.lastModified() > lastModified) {
			return true;
		}
		return false;
	}

	public Class<?> loadClass(String classPath, String name)
			throws ClassNotFoundException {
		this.classPath = classPath;
		if (isClassModified(name)) {
			return findClass(name);
		}
		return null;
	}

	/**
	 * 获取class文件的字节码
	 * 
	 * @param name
	 *            类的全名
	 * @return
	 */
	private byte[] getBytes(String name) {
		byte[] buffer = null;
		FileInputStream in = null;
		try {
			File file = getFile(name);
			lastModified = file.lastModified();
			in = new FileInputStream(file);
			buffer = new byte[in.available()];
			in.read(buffer);
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}

	/**
	 * 获取class文件的真实路径
	 * 
	 * @param name
	 * @return
	 */
	private File getFile(String name) {
		String simpleName = "";
		String packageName = "";
		if (name.indexOf(".") != -1) {
			simpleName = name.substring(name.lastIndexOf(".") + 1);
			packageName = name.substring(0, name.lastIndexOf(".")).replaceAll(
					"[.]", "/");
		} else {
			simpleName = name;
		}
		
		//ati o0L
		if(classPath==null)
			classPath=	pathx.classPath();
		File file = new File(MessageFormat.format("{0}/{1}/{2}.class",
				classPath, packageName, simpleName));
		return file;
	}

//	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		//com.attilax.corePkg.classloaderT
		byte[] byteCode = getBytes(name);
		return    defineClass(null, byteCode, 0, byteCode.length);
	}
}