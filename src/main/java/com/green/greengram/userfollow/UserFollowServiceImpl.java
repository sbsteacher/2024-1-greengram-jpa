package com.green.greengram.userfollow;

import com.green.greengram.entity.User;
import com.green.greengram.entity.UserFollow;
import com.green.greengram.security.AuthenticationFacade;

import com.green.greengram.user.UserRepository;
import com.green.greengram.userfollow.model.UserFollowDeleteReq;
import com.green.greengram.userfollow.model.UserFollowPostReq;
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
    private final UserFollowRepository repository;
    private final UserRepository userRepository;

    @Override
    public int postUserFollow(UserFollowPostReq p) {
        User fromUser = userRepository.getReferenceById(authenticationFacade.getLoginUserId());
        User toUser = userRepository.getReferenceById(p.getToUserId());

        UserFollow userFollow = new UserFollow();
        userFollow.setFromUser(fromUser);
        userFollow.setToUser(toUser);

        repository.save(userFollow);

        return 1;
    }

    @Override
    public int deleteUserFollow(UserFollowDeleteReq p) {
        //p.setFromUserId(authenticationFacade.getLoginUserId());
        //return mapper.delUserFollow(p);
        return 1;
    }
}
