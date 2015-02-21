package com.focustar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.attilax.core;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.util.DateUtil;
import com.attilax.util.PropX;
import com.attilax.util.getConnEx;
import com.attilax.wechat.WechatX;
import com.focustar.entity.TMbWeixinuser;

public class ExtWebsiteX {

	public static void main(String[] args) {

		ExtWebsiteX c = new ExtWebsiteX();
		TMbWeixinuser u = new TMbWeixinuser();
		u.setNickname("nkname");
		u.setOpenid("oid" + filex.getUUidName());

		u.setOpenid("oid0120_154914_910");
		c.getUserinfoFrmWechatHandler(u);
		// try {
		// Map m = c.getUserinfo(u.getOpenid());
		// System.out.println(m.get("UserId"));
		// } catch (noExistThisUser e) {
		//
		// }
		Map m = null;
		String url = "http://218.83.161.190:8973/loginApi.aspx?uname"
				+ m.get("UserName") + "&uid=" + m.get("UserID");
		System.out.println("---fi");
	}

	WechatX wechatC = new WechatX();

	public Map getUserinfo_reqByWechat(HttpServletRequest request)
			throws noExistThisUser {//oQe6zt-6iMqK7TWV0oAv672aAYwU
		String openid = wechatC.getOpenid2(request);
		Map m = getUserinfo(openid);
		return m;

	}

	public Map getUserinfo(String openid) throws noExistThisUser {
		Connection conn;
		try {
			conn = getConnection();

			// 创建一个QueryRunner
			QueryRunner queryRunner = new QueryRunner(true);
			List<Map<String, Object>> list;

			list = queryRunner.query(conn,
					"select * from MJ_User where username='" + openid + "'",
					new MapListHandler());
			if (list.size() == 0)
				throw new noExistThisUser();
			return list.get(0);
		} catch (noExistThisUser e) {
			throw e;
		}

		catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public void getUserinfoFrmWechatHandler(TMbWeixinuser u) {
		
		try {
			
			if (userExist(u.getOpenid()))
				return;
			Connection conn = getConnection();
			// 创建一个QueryRunner
			QueryRunner queryRunner = new QueryRunner(true);
			// QueryRunner run = new QueryRunner(conn,, "Java", "123456");

			// 执行insert语句并返回被更新的记录数量
			String sql = "INSERT INTO User (name,password) VALUES (?,?)";
			sql = "INSERT INTO  [MJ_User] ( [UserName], [Password], [NickName], [RealName], [Sex],"
					+ " [BirthDate], [Age], [ShengXiao], [Constellation], [Education], "
					+ "[Marital], [RegSource], [Phone], [QQ], [MSN],"
					+ " [Height], [Weight], [BloodStyle], [Nation], [Native], "
					+ "[Address], [Monthly], [House], [Car], [CompanyIndustry], "
					+ "[CompanyStyle], [CompanyName], [Job], [School], [EduStartDate],"
					+ " [Major], [Languages], [Smoke], [Drink], [ZuoXi],"
					+ " [DuanLian], [FuMuTongZhu], [Child], [Pet], [XiaoFei], "
					+ "[LangMan], [Skill], [SelfAssessment], [Trait], [Sports], "
					+ "[Hobby], [Entertainment], [WantGo], [Tel], [BlogUrl],"
					+ " [Email], [Postcode], [RegDate], [ChengHao], [Levels],"
					+ " [Recommend1], [Recommend2], [Recommend3], [Recommend4], [OrderNum]) VALUES ("
					+ " N'@uname', N'000', N'@nickname', null, null, "
					+ "null, null, null, null, null,"
					+ " null, null, N'', null, null,"
					+ " null, null, null, null, null,"
					+ " null, null, null, null, null,"
					+ " null, null, null, null, null,"
					+ " null, null, null, null, null, "
					+ "null, null, null, null, null, "
					+ "null, null, null, null, null, "
					+ "null, null, null, null, null, "
					+ "N'', N'100', N'@regdate', null, N'1',"
					+ " null, null, null, null, null)";
			sql = sql
					.replaceAll("@uname", u.getOpenid())
					.replaceAll("@nickname", u.getNickname())
					.replaceAll("@regdate", DateUtil.date2str(new Date(), true));
			System.out.println(sql);
			filex.saveLog(sql, "c:\\sql");

			int inserts = queryRunner.update(conn, sql);

			System.out.println("插入数据" + (inserts == 1 ? "成功" : "失败"));

		} catch (Exception e) {
			filex.saveLog(e, "c:\\wechatFocusLog4majun");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//
		}

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since p1j g_a_0
	 */
	private boolean userExist(String openid) {
		try {
			Map m = getUserinfo(openid);
			System.out.println(m.get("UserId"));
			return true;
		} catch (noExistThisUser e) {
			return false;
		}

	}

	private Connection getConnection() throws getConnEx {
		// com.microsoft.sqlserver.jdbc.SQLServerDriver
		String path = pathx.classPath() + "/website.properties";
		System.out.println(PropX.getConfig(path, "jdbc.url"));
		try {

			Class.forName(PropX.getConfig(path, "jdbc.driverClassName"));
		} catch (ClassNotFoundException e) {
			throw new getConnEx("getconnex" + e.getMessage());
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(
					PropX.getConfig(path, "jdbc.url"),
					PropX.getConfig(path, "jdbc.username"),
					PropX.getConfig(path, "jdbc.password"));
		} catch (SQLException e) {
			throw new getConnEx("getconnex" + e.getMessage());
		}
		return conn;
	}

}
