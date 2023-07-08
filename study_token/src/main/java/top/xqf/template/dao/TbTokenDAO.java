package top.huhuiyu.servlet.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.huhuiyu.servlet.entity.TbToken;
import top.huhuiyu.servlet.util.DBHelp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

/**
 * tb_token数据表的DAO
 *
 * @author 胡辉煜
 */
public class TbTokenDAO {
  private static final Logger logger = LoggerFactory.getLogger(TbTokenDAO.class);
  public static final String SELECT_BY_TOKEN = "select * from tb_token where token = ?";
  public static final String INSERT = "insert into tb_token(token,token_info) values(?,?)";
  public static final String UPDATE = "update tb_token set token_info = ? where token = ?";

  /**
   * 查询token信息
   *
   * @param token 令牌值
   * @return token对应的信息
   * @throws Exception 处理发生异常
   */
  public TbToken queryByToken(String token) throws Exception {
    Connection connection = DBHelp.getConnection();
    TbToken result = null;
    PreparedStatement ps = connection.prepareStatement(SELECT_BY_TOKEN);
    ps.setString(1, token);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      result = new TbToken();
      result.setToken(rs.getString("token"));
      result.setTokenInfo(rs.getString("token_info"));
      result.setLastupdate(rs.getTimestamp("lastupdate"));
    }
    connection.close();
    return result;
  }

  /**
   * 查询或添加token信息，如果token信息存在就返回，否则就新增
   *
   * @param token token信息
   * @return 添加或者更新之后的token信息
   * @throws Exception 处理发生异常
   */
  public TbToken selectOrInsert(TbToken token) throws Exception {
    Connection connection = DBHelp.getConnection();
    TbToken check = queryByToken(token.getToken());
    PreparedStatement ps;
    if (check == null) {
      // token不存在的情况，新建token并保存到数据库
      token.setToken(UUID.randomUUID().toString());
      token.setTokenInfo("{}");
      ps = connection.prepareStatement(INSERT);
      ps.setString(1, token.getToken());
      ps.setString(2, token.getTokenInfo());
      ps.executeUpdate();
    }
    connection.close();
    return check == null ? token : check;
  }

  /**
   * 更新token信息
   *
   * @param token token信息
   * @throws Exception 处理发生异常
   */
  public void update(TbToken token) throws Exception {
    Connection connection = DBHelp.getConnection();
    PreparedStatement ps = connection.prepareStatement(UPDATE);
    ps.setString(1, token.getTokenInfo());
    ps.setString(2, token.getToken());
    ps.executeUpdate();
    connection.close();
  }
}
