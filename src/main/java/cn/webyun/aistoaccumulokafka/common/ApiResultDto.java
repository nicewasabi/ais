package cn.webyun.aistoaccumulokafka.common;


import java.io.Serializable;

/**
 * @Project: hebe
 * @Title: ApiResultDto
 * @Description: API结果返回类
 * @author: zhangx
 * @date: 2017年5月18日 上午11:37:28
 * @company: webyun
 * @Copyright: Copyright (c) 2017
 * @version v1.0
 */
public class ApiResultDto<T> implements Serializable {

	private static final long serialVersionUID = 5171727609353787106L;
	
	/**
	 * 状态码 200,404,400
	 */
	private Integer code;
	
	/**
	 * 提示信息
	 */
	private String message;
	
	/**
	 * 操作结果，可以为空
	 */
	private T content;
	
	public ApiResultDto() {
		super();
	}

	public ApiResultDto(Integer code, String message, T content) {
		this.code = code;
		this.message = message;
		this.content = content;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ApiResultDto [code=" + code + ", message=" + message + ", content=" + content + "]";
	}
	
}
