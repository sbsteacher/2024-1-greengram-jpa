package com.green.greengram.common;

import com.green.greengram.user.model.UserInfo;
import com.green.greengram.user.model.UserInfoRoles;

import java.util.ArrayList;
import java.util.List;

public class MyCommonUtils {

    public static UserInfoRoles convertToUserInfoRoles(List<UserInfo> list) {
        if(list == null || list.size() == 0) {
            return null;
        }
        UserInfoRoles userInfoRoles = new UserInfoRoles();
        List<String> roles = new ArrayList();
        UserInfo userInfo = list.get(0);

        userInfoRoles.setUserId(userInfo.getUserId());
        userInfoRoles.setCreatedAt(userInfo.getCreatedAt());
        userInfoRoles.setUpdatedAt(userInfo.getUpdatedAt());
        userInfoRoles.setUid(userInfo.getUid());
        userInfoRoles.setUpw(userInfo.getUpw());
        userInfoRoles.setNm(userInfo.getNm());
        userInfoRoles.setPic(userInfo.getPic());
        userInfoRoles.setRoles(roles);

        for(UserInfo ui : list) {
            roles.add(ui.getRole());
        }

        return userInfoRoles;
    }
}
