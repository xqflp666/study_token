package top.huhuiyu.servlet.base;

import java.io.Serializable;

/**
 * 基础应答对象
 *
 * @param <T> 应答结果中的数据类型
 * @author 胡辉煜
 */
public class BaseResult<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private int code = 500;
  private boolean success = false;
  private String message = "";
  private String token = "";
  private T data;

  public BaseResult() {
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
