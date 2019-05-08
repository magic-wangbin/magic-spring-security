package com.magic.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetialsService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(MyUserDetialsService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名", username);
        //根据用户名查询出来的密码
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是:" + password);

        return buildUser(username, password);
    }

    /**
     * 根据用户Id查询第三方用户登录表信息.
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("第三方用户登录userId:" + userId);
        //根据用户userId
        return buildUser(userId, "");
    }

    /**
     * 创建用户.
     *
     * @param userId
     * @return
     */
    private SocialUserDetails buildUser(String userId, String password) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结

        return new SocialUser(userId, password,
            true, true, true, true,
            AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
