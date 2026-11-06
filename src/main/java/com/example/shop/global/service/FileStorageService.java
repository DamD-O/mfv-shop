package com.example.shop.global.service;

import com.example.shop.global.entity.ImageCategory;
import com.example.shop.global.exception.MfvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/**
 * @author madey
 * @DATE 2026-08-25
 * @description 이미지 관련 작업
 */

@Component
public class FileStorageService {
    private static final Set<String> EXT_LIST = Set.of("jpg", "jpeg", "png", "gif", "bmp", "webp");

    @Value("${file.upload-dir}")
    private String uploadDir;

    //업로드 디렉토리가 없을 경우 생성하고, 컨트롤러로 부터 전달 받은 파일과 원본 파일명 UUID이용해서 고유한 파일명 생성, 그뒤에 파일 스트림을 파일 시스템에 복사해서 저장
    public String store(MultipartFile file, ImageCategory category) throws IOException
    {
        String subFolderName = category.getFolderName();
        //파일 저장 : images/entity항목/파일명
        Path uploadPath = Paths.get(uploadDir, subFolderName);
        Files.createDirectories(uploadPath);

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || !originalFilename.contains(".")) throw new MfvException("파일이름이 없거나 확장자가 없습니다.");

        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        if (!EXT_LIST.contains(ext.toLowerCase())) throw new MfvException("jpg, jpeg, png, gif, bmp, webp 파일만 업로드 할 수 있습니다.");

        String saveName = UUID.randomUUID() + "." + ext.toLowerCase(); //중복 방지

        Path filePath = Paths.get(uploadDir, subFolderName, saveName);
        file.transferTo(filePath); //실제 파일 저장

        return "/images/" + subFolderName + "/" + saveName;
    }

    public void delete(String filePath) throws IOException
    {
        //이미지 삭제
        Path fullPath = Paths.get(uploadDir, filePath.replace("/images/", ""));
        Files.deleteIfExists(fullPath);
    }
}
