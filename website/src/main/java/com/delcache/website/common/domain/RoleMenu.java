package com.delcache.website.common.domain;


import com.delcache.website.common.annotation.PrimaryKey;

public class RoleMenu extends BaseEntity {

    @PrimaryKey
    private Long id;

    private Long roleId;

    private Long menuId;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
