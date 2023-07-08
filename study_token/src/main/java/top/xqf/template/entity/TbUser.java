package top.huhuiyu.servlet.entity;

import top.huhuiyu.servlet.util.JsonUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_user表对应的实体类
 *
 * @author 胡辉煜
 */
public class TbUser implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer uid;
  private String username;
  private String password;
  private String nickname;
  private String enable;
  private Date lastupdate;

  public TbUser() {
  }

  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEnable() {
    return enable;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
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
