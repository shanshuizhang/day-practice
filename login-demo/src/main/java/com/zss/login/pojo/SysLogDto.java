package com.zss.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
* 日志实体Dto
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogDto implements Serializable {

	private static final long serialVersionUID = -5398795297842978376L;

	private Long id;
//	用户名
	private String username;
//	归属模块
	private String module;
//	执行方法的参数值
	private String params;
	private String remark;
//	是否执行成功
	private Boolean flag;

	private Date createTime;
}
