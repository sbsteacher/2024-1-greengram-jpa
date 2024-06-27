package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.UserProfilePatchReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({ UserServiceImpl.class })
@TestPropertySource(
    properties = {
            "file.directory=D:/2024-01/download/greengram_tdd/"
    }
)
public class UserService2Test {
    @Value("${file.directory}") String uploadPath;
    @MockBean UserMapper mapper;
    @MockBean CustomFileUtils customFileUtils;
    @Autowired UserService service;


    @Test
    void patchProfilePic() throws Exception {
        long signedUserId1 = 500;
        UserProfilePatchReq p1 = new UserProfilePatchReq();
        p1.setSignedUserId(signedUserId1);
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "b.jpg", "image/jpg",
                new FileInputStream(String.format("%stest/b.jpg", uploadPath))
        );
        p1.setPic(fm1);
        String checkFileNm1 = "a1b2.jpg";
        given(customFileUtils.makeRandomFileName(fm1)).willReturn(checkFileNm1);

        String fileNm1 = service.patchProfilePic(p1);
        assertEquals(checkFileNm1, fileNm1);

        verify(mapper).updProfilePic(p1);

        String midPath = String.format("user/%d", p1.getSignedUserId());
        String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
        verify(customFileUtils).deleteFolder(delAbsoluteFolderPath);
        verify(customFileUtils).makeFolders(midPath);
        String filePath = String.format("%s/%s", midPath, fileNm1);
        verify(customFileUtils).transferTo(p1.getPic(), filePath);

    }

    @Test
    void patchProfilePic2() throws Exception {
        long signedUserId1 = 600;
        UserProfilePatchReq p1 = new UserProfilePatchReq();
        p1.setSignedUserId(signedUserId1);
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "b.jpg", "image/jpg",
                new FileInputStream(String.format("%stest/b.jpg", uploadPath))
        );
        p1.setPic(fm1);
        String checkFileNm1 = "aaaabbbb.jpg";
        given(customFileUtils.makeRandomFileName(fm1)).willReturn(checkFileNm1);

        String fileNm1 = service.patchProfilePic(p1);
        assertEquals(checkFileNm1, fileNm1);

        verify(mapper).updProfilePic(p1);

        String midPath = String.format("user/%d", p1.getSignedUserId());
        String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
        verify(customFileUtils).deleteFolder(delAbsoluteFolderPath);
        verify(customFileUtils).makeFolders(midPath);
        String filePath = String.format("%s/%s", midPath, fileNm1);
        verify(customFileUtils).transferTo(p1.getPic(), filePath);

    }

}

