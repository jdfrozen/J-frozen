package com.frozen.springbootsecurity.po;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class MenuRole {
    private Long id;

    private Long menuId;

    private Long roleId;

    private Date createTime;

    private Date updateTime;

}
