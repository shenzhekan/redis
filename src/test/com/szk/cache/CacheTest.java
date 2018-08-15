package com.szk.cache;


import com.szk.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.szk.pojo.Role;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheTest {

    @Autowired
    private RoleService roleServiceImpl;

    public void setRoleServiceImpl(RoleService roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    @Test
    public void redisCacheTest1(){
        Role role = new Role();
        role = roleServiceImpl.getRole(2l);

    }

    @Test
    public void redisCacheTest2(){
        Role role = new Role();
        role.setRoleName("ÄÐÖ÷");
        role.setNote("ÑÕÖµµ£µ±");
        roleServiceImpl.insertRole(role);
    }
}
