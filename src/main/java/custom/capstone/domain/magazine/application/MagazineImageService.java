package custom.capstone.domain.magazine.application;

import custom.capstone.domain.magazine.dao.MagazineImageRespository;
import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.magazine.domain.MagazineImage;
import custom.capstone.global.exception.ImageNotFoundException;
import custom.capstone.infra.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MagazineImageService {

    private final MagazineImageRespository magazineImageRespository;
    private final S3Uploader s3Uploader;

    /**
     * 매거진 다중 이미지 저장
     */
    @Transactional
    public List<MagazineImage> savemagazineImages(final Magazine magazine, final List<MultipartFile> images) throws IOException {

        final String folderPath = "magazine/";

        final List<String> imageUrls = s3Uploader.uploadImageList(images, folderPath);

        final List<MagazineImage> magazineImages = new ArrayList<>();

        if (!imageUrls.isEmpty()) {
            for (final String imageUrl : imageUrls) {
                final MagazineImage magazineImage = magazineImageRespository.save(new MagazineImage(magazine, imageUrl));
                magazineImages.add(magazineImage);
            }
        }

        return magazineImages;
    }

    // 매거진 썸네일 찾기
    @Transactional
    public String findThumbnailUrl(final Magazine magazine) {
        return magazine.getMagazineImages().stream()
                .findFirst()
                .map(MagazineImage::getImageUrl)
                .orElseThrow(ImageNotFoundException::new);
    }

    // 매거진 이미지 전체 조회
    public List<String> findAllMagazineImages(final Magazine magazine) {
        return magazine.getMagazineImages().stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }
}
