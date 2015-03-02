package AaaCfg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.attilax.Closure;
import com.attilax.db.Hb4JdbcX;
import com.attilax.lang.Closure2;
import com.attilax.persistence.Hbx;
import com.attilax.persistence.HbxX;
import com.attilax.persistence.PX;
import com.attilax.util.HibernateSessionFactory;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.BaseImpl;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.name.Names;

public class IocX {
	static Injector ati;
	public static Map<String, Object> map = new HashMap();

	public static void ini() {
		// map.put("getUserinfoFrmWechatHandler", new
		// Closure<TMbWeixinuser,Object>(){
		//
		// @Override
		// public Object execute(TMbWeixinuser arg0)
		// throws Exception {
		// ExtWebsiteX x=new ExtWebsiteX();
		// x.getUserinfoFrmWechatHandler(arg0);
		// return null;
		// }});

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since o92 j_48_40
	 * 
	 * @param class1
	 * @return
	 */
	public static <t> t getBean(Class<t> class1) {
		// attilax 老哇的爪子 j_48_40 o92
		if (ati == null) // ini singleon patter..
		{
			ati = Guice.createInjector(new Module() {

				@Override
				public void configure(Binder bd) {
					// ServiceLoctor4vod.inidb();
					// attilax 老哇的爪子 j_s_37 o92
					// bd.bind(DBX.class).to(DbxMybatis.class);
					 // bd.bind(guiceT.class); ///jeig bind self def zeush okd
					// ,can not jwemen bind..
					// all def inj can def bind self...can auto bind
//					 bd.bind(Session.class).toInstance( (Session) new Closure2(){
//
//						@Override
//						public Object execute(Object arg0) {
//							SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
//							Session ss;
//							
//							return sessionFactory.openSession();
//						}}.execute(null));
					 bd.bind(Session.class).toProvider(new Provider<Session>(){

						@Override
						public Session get() {
							SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
							Session ss;
							
							return sessionFactory.openSession();
						}});
					 bd.bind(Connection.class).toProvider(new Provider<Connection>(){

						@Override
						public Connection get() {
//							SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
//							Connection c=	SessionFactoryUtils.getDataSource(sessionFactory ).getConnection();
 							try {
								return new Hb4JdbcX().getConnection();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}});
//					bd.bind(ConnectionProvider.class).toInstance(
//							(ConnectionProvider) new Closure2() {
//
//								@Override
//								public Object execute(Object arg0) {
//									BaseImpl basedao = new BaseImpl();
////									final Map m = new HashMap();
////									m.put("conn", null);
////
////								//	m.
////									// 定义一个匿名类，实现了Work接口
////									Work work = new Work() {
////										public void execute(
////												Connection connection)
////												throws SQLException {
////											// 通过JDBC API执行用于批量更新的SQL语句
////											// conn=connection;
////											m.put("conn", connection);
////										}
////									};
//
//									// 执行work
//								//	basedao.getSession().doWork(work);
//									SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
//									Connection c;
//									try {
//									c=	new Hb4JdbcX().getConnection();
//
////ConnectionProvider cp = ConnectionProvider.newConnectionProvider (cfg.getProperties());
////cp.getConnection();
//
////Session ssn=sessionFactory.openSession();
//									//	ConnectionProvider cp = ((SessionFactoryImplementor)ssn.getSessionFactory()).getConnectionProvider();
//									//	  c = cp.getConnection();
//										//c = SessionFactoryUtils.getDataSource(sessionFactory ).getConnection();
//									} catch (SQLException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//										throw new RuntimeException(e);
//									}
//									return c;
//								}
//							}.execute(null));
					// bd.bind(GvDownloadTaskSvs.class);
					bd.bindConstant().annotatedWith(Names.named("thql"))
							.to(" from TUserUsers ");
				}
			});
		}

		return ati.getInstance(class1);

	}

}
