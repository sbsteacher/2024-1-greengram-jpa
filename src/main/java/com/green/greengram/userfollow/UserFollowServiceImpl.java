package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl implements UserFollowService {
    private final UserFollowMapper mapper;

    @Override
    public int postUserFollow(UserFollowReq p) {
        return mapper.insUserFollow(p);
    }

    @Override
    public int deleteUserFollow(UserFollowReq p) {
        return mapper.delUserFollow(p);
    }
}
