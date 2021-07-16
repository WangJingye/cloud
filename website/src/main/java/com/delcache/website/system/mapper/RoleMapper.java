package com.delcache.website.system.mapper;

import com.delcache.website.common.domain.Role;
import com.delcache.website.common.domain.RoleMenu;
import com.delcache.website.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    public List<Long> getRoleMenuList(@Param("id") long id, @Param("status") Object status);

    public void saveRoleMenu(RoleMenu roleMenu);

    public void deleteRoleMenus(@Param("roleId") long roleId, @Param("idList") List<String> idList);

    public void updateRoleMenus(@Param("roleId") long roleId, @Param("idList") List<String> idList,@Param("status") int status);

    public List<Long> getRoleAdminList(@Param("id") long id);

    public void deleteRoleAdminList(@Param("roleId") long roleId, @Param("idList") List<String> idList);

    public void addRoleAdminList(@Param("roleId") long roleId, @Param("idList") List<String> idList);

}
