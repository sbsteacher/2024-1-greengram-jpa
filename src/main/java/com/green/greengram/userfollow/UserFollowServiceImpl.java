package com.green.greengram.userfollow;

import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl implements UserFollowService {
    private final UserFollowMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    @Override
    public int postUserFollow(UserFollowReq p) {
        p.setFromUserId(authenticationFacade.getLoginUserId());
        return mapper.insUserFollow(p);
    }

    @Override
    public int deleteUserFollow(UserFollowReq p) {
        p.setFromUserId(authenticationFacade.getLoginUserId());
        return mapper.delUserFollow(p);
    }
}
