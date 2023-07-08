package top.huhuiyu.servlet.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.huhuiyu.servlet.entity.TbUser;
import top.huhuiyu.servlet.util.DBHelp;
import top.huhuiyu.servlet.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * tb_user数据表的DAO
 *
 * @author 胡辉煜
 */
public class TbUserDAO {
  private static final Logger logger = LoggerFactory.getLogger(TbUserDAO.class);
  public static final String SELECT = "select * from tb_user";
  public static final String INSERT = "insert into tb_user(username,password,nickname) values(?,?,?)";

  public static final String SELECT_BY_USERNAME = "select * from tb_user where username = ?";

  /**
   * 演示查询用户列表，记录会被限制在100条，且是添加数据逆序排列
   *
   * @param userInfo 查询参数，提供用户名模糊查询和是否启用
   * @return 用户列表
   * @throws Exception 处理发生异常
   */
  public List<TbUser> query(TbUser userInfo) throws Exception {
    List<TbUser> list = new ArrayList<>();
    Connection connection = DBHelp.getConnection();
    // 处理查询参数
    List<Object> params = new ArrayList<>();
    StringBuilder sql = new StringBuilder();
    userInfo = Optional.ofNullable(userInfo).orElse(new TbUser());
    logger.debug("参数信息：{}", userInfo);
    // 姓名模糊查询
    if (StringUtils.hasTest(userInfo.getUsername())) {
      params.add(String.format("%%%s%%", userInfo.getUsername()));
      sql.append(" and username like ? ");
    }
    // 是否启用
    if (StringUtils.hasTest(userInfo.getEnable())) {
      params.add(userInfo.getEnable());
      sql.append(" and enable = ? ");
    }
    // 如果存在参数就将第一个and替换成where
    String strSql = sql.toString();
    if (strSql.length() > 0) {
      strSql = strSql.replaceFirst("and", "where");
    }
    logger.debug("where语句：{}", strSql);
    PreparedStatement ps = connection.prepareStatement(String.format("%s %s order by uid desc limit 100", SELECT, strSql));
    // 处理?参数
    for (int i = 0; i < params.size(); i++) {
      ps.setObject(i + 1, params.get(i));
    }
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      TbUser tbUser = new TbUser();
      tbUser.setUid(rs.getInt("uid"));
      tbUser.setUsername(rs.getString("username"));
      tbUser.setPassword(rs.getString("password"));
      tbUser.setNickname(rs.getString("nickname"));
      tbUser.setEnable(rs.getString("enable"));
      tbUser.setLastupdate(rs.getTimestamp("lastupdate"));
      list.add(tbUser);
    }
    connection.close();
    return list;
  }

  /**
   * 查询用户名对应的用户信息
   *
   * @param userInfo 用户姓名
   * @return 用户名对应的用户信息
   * @throws Exception 处理发生异常
   */
  public TbUser queryByUsername(TbUser userInfo) throws Exception {
    Connection connection = DBHelp.getConnection();
    TbUser result = null;
    PreparedStatement ps = connection.prepareStatement(SELECT_BY_USERNAME);
    ps.setString(1, userInfo.getUsername());
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      result = new TbUser();
      result.setUid(rs.getInt("uid"));
      result.setUsername(rs.getString("username"));
      result.setPassword(rs.getString("password"));
      result.setNickname(rs.getString("nickname"));
      result.setEnable(rs.getString("enable"));
      result.setLastupdate(rs.getTimestamp("lastupdate"));
    }
    connection.close();
    return result;
  }

  /**
   * 添加用户信息
   *
   * @param userInfo 添加参数，登录名，密码，用户名
   * @return 添加的结果
   * @throws Exception 处理发生异常
   */
  public int add(TbUser userInfo) throws Exception {
    Connection connection = DBHelp.getConnection();
    PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, userInfo.getUsername());
    ps.setString(2, userInfo.getPassword());
    ps.setString(3, userInfo.getNickname());
    int result = ps.executeUpdate();
    // 添加成功就尝试获取自动增长的主键信息
    ResultSet rs = ps.getGeneratedKeys();
    if (result == 1 && rs.next()) {
      userInfo.setUid(rs.getInt(1));
    }
    connection.close();
    return result;
  }
}
