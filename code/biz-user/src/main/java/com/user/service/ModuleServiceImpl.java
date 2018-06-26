package com.user.service;

import com.user.domain.entity.Module;
import com.user.domain.entity.Permission;
import com.user.domain.entity.User;
import com.user.domain.enums.PermissionType;
import com.user.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void addModule(String moduleName) throws IllegalArgumentException {
        Module existed = moduleRepository.queryByName(moduleName);
        Assert.isNull(existed, "module has already existed");

        Module newModule = new Module();
        newModule.setName(moduleName);
        Module added = moduleRepository.save(newModule);
        permissionService.addPermission(added, PermissionType.CREATE);
        permissionService.addPermission(added, PermissionType.READ);
        permissionService.addPermission(added, PermissionType.UPDATE);
        permissionService.addPermission(added, PermissionType.DELETE);
    }

    @Override
    public void enableModule(Integer moduleId) throws IllegalArgumentException {
        Module module = moduleRepository.queryById(moduleId);
        Assert.notNull(module, "module not existed");
        module.setIsAvailable(true);
        moduleRepository.save(module);
    }

    @Override
    public void disableModule(Integer moduleId) {
        Module module = moduleRepository.queryById(moduleId);
        Assert.notNull(module, "module not existed");
        module.setIsAvailable(false);
        moduleRepository.save(module);
    }

    @Override
    public List<Module> getAllModules() {
        return (List<Module>) moduleRepository.findAll();
    }

    @Override
    public List<Module> getAvailModulesByAdmin() {
        return moduleRepository.queryByIsAvailableTrue();
    }

    @Override
    public Set<Module> getAvailModulesByUser(User user) {
        Set<Module> moduleSet = new HashSet<>();
        Set<Permission> permissions = userInfoService.findPermissionsByUser(user);
        for (Permission p : permissions) {
            if (p.getPermission() == PermissionType.READ) {
                moduleSet.add(p.getModule());
            }
        }
        return moduleSet;
    }

}
