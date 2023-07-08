package top.huhuiyu.servlet.entity;

import top.huhuiyu.servlet.util.JsonUtil;

import java.io.Serializable;

/**
 * token信息类
 *
 * @author 胡辉煜
 */
public class TokenInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  private String ip = "";
  private TbUser user = null;

  public TokenInfo() {
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public TbUser getUser() {
    return user;
  }

  public void setUser(TbUser user) {
    this.user = user;
  }

  @Override
  public String toString() {
    try {
      return JsonUtil.stringify(this);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
