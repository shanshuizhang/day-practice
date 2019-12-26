package com.zss.login.mapper.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
* 日志实体
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_log")
public class SysLog implements Serializable {

	private static final long serialVersionUID = -5398795297842978376L;

	@TableId(type = IdType.AUTO)
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

	@TableField("createTime")
	private Date createTime;
}
