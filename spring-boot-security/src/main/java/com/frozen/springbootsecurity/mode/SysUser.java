package com.frozen.springbootsecurity.mode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiumin
 * @create 2019/1/12 17:04
 * @desc
 **/
@Table(name = "sys_user")
@Entity
@DynamicInsert
@DynamicUpdate
public class SysUser implements Serializable {

    private static final long serialVersionUID = -2533603076594909149L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @ManyToMany(targetEntity = SysRole.class,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRole> roles = new ArrayList();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
