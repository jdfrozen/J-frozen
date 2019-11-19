package com.frozen.springbootsecurity.mode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiumin
 * @create 2019/1/12 17:12
 * @desc
 **/
@Table(name = "sys_menu")
@Entity
@DynamicInsert
@DynamicUpdate
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -8371023174541278121L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToMany(targetEntity = SysRole.class,fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "sys_role_menu",joinColumns = {@JoinColumn(name = "menu_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRole> roles = new ArrayList();

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "url_path")
    private String urlPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
