//package com.magic.security.core.social.config;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.security.SocialAuthenticationProvider;
//import org.springframework.social.security.SocialUserDetailsService;
//
//import java.util.List;
//
//public class CusSocialAuthenticationProvider extends SocialAuthenticationProvider {
//
//    private UsersConnectionRepository usersConnectionRepository;
//
//    public CusSocialAuthenticationProvider(UsersConnectionRepository usersConnectionRepository, SocialUserDetailsService userDetailsService) {
//        super(usersConnectionRepository, userDetailsService);
//        this.usersConnectionRepository = usersConnectionRepository;
//    }
//
//    @Override
//    protected String toUserId(Connection<?> connection) {
//        List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
//        // only if a single userId is connected to this providerUserId
//        if (CollectionUtils.isNotEmpty(userIds)) {
//            return userIds.get(0);//取值第一个
//        }
////        return (userIds.size() == 1) ? userIds.iterator().next() : null;
////        return super.toUserId(connection);
//        return null;
//    }
//}
