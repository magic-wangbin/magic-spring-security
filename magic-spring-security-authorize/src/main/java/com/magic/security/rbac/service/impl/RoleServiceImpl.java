/**
 *
 */
package com.magic.security.rbac.service.impl;


import com.magic.security.rbac.domain.Role;
import com.magic.security.rbac.domain.RoleResource;
import com.magic.security.rbac.dto.RoleInfo;
import com.magic.security.rbac.exception.RbacNotFoundException;
import com.magic.security.rbac.repository.ResourceRepository;
import com.magic.security.rbac.repository.RoleRepository;
import com.magic.security.rbac.repository.RoleResourceRepository;
import com.magic.security.rbac.repository.support.QueryResultConverter;
import com.magic.security.rbac.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhailiang
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    /* (non-Javadoc)
     * @see com.imooc.security.rbac.service.RoleService#create(com.imooc.security.rbac.dto.RoleInfo)
     */
    @Override
    public RoleInfo create(RoleInfo info) {
        Role role = new Role();
        BeanUtils.copyProperties(info, role);
        info.setId(roleRepository.save(role).getId());
        return info;
    }

    /* (non-Javadoc)
     * @see com.imooc.security.rbac.service.RoleService#update(com.imooc.security.rbac.dto.RoleInfo)
     */
    @Override
    public RoleInfo update(RoleInfo info) {
        Role role = findRoleById(info.getId());
        BeanUtils.copyProperties(info, role);
        return info;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void delete(Long id) {
        Role role = findRoleById(id);
        if (CollectionUtils.isNotEmpty(role.getAdmins())) {
            throw new RuntimeException("不能删除有下挂用户的角色");
        }
        roleRepository.deleteById(id);
    }
//
//	@Override
//	public String[] getRoleMenus(Long id) {
//		return StringUtils.split(roleRepository.findOne(id).getMenus(), ",");
//	}
//
//	/**
//	 * (non-Javadoc)
//	 * @see com.idea.ams.service.RoleService#setRoleMenu(java.lang.Long, java.lang.String)
//	 */
//	@Override
//	public void setRoleMenu(Long roleId, String menuIds) {
//		Role role = roleRepository.findOne(roleId);
//		role.setMenus(menuIds);
//	}

    /**
     * (non-Javadoc)
     * @see com.idea.ams.service.RoleService#getRoleInfo(Long)
     */
    @Override
    public RoleInfo getInfo(Long id) {
        Role role = findRoleById(id);
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(role, info);
        return info;
    }

    /* (non-Javadoc)
     * @see com.imooc.security.rbac.service.RoleService#findAll()
     */
    @Override
    public List<RoleInfo> findAll() {
        return QueryResultConverter.convert(roleRepository.findAll(), RoleInfo.class);
    }

    @Override
    public String[] getRoleResources(Long id) {
        Role role = findRoleById(id);
        Set<String> resourceIds = new HashSet<>();
        for (RoleResource resource : role.getResources()) {
            resourceIds.add(resource.getResource().getId().toString());
        }
        return resourceIds.toArray(new String[resourceIds.size()]);
    }

    /**
     * (non-Javadoc)
     * @see com.idea.ams.service.RoleService#setRoleMenu(Long, String)
     */
    @Override
    public void setRoleResources(Long roleId, String resourceIds) {
        resourceIds = StringUtils.removeEnd(resourceIds, ",");
        Role role = findRoleById(roleId);
        roleResourceRepository.deleteAll(role.getResources());
        String[] resourceIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(resourceIds, ",");
        for (String resourceId : resourceIdArray) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRole(role);
            roleResource.setResource(resourceRepository.getOne(new Long(resourceId)));
            roleResourceRepository.save(roleResource);
        }
    }

    /**
     * 根据ID查询角色.
     * @param id
     * @return
     */
    private Role findRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RbacNotFoundException("角色不存在！"));
        return role;
    }
}
