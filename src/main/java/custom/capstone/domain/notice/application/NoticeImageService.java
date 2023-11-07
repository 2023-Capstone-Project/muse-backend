package custom.capstone.domain.notice.application;

import custom.capstone.domain.notice.dao.NoticeImageRepository;
import custom.capstone.domain.notice.domain.Notice;
import custom.capstone.domain.notice.domain.NoticeImage;
import custom.capstone.global.exception.ImageNotFoundException;
import custom.capstone.infra.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeImageService {

    private final NoticeImageRepository noticeImageRepository;
    private final S3Uploader s3Uploader;

    /**
     * 공지사항 다중 이미지 저장
     */
    @Transactional
    public List<NoticeImage> saveNoticeImages(final Notice notice, final List<MultipartFile> images) throws IOException {

        final String folderPath = "notice/";

        final List<String> imageUrls = s3Uploader.uploadImageList(images, folderPath);

        final List<NoticeImage> noticeImages = new ArrayList<>();

        if (!imageUrls.isEmpty()) {
            for (final String imageUrl : imageUrls) {
                final NoticeImage noticeImage = noticeImageRepository.save(new NoticeImage(notice, imageUrl));
                noticeImages.add(noticeImage);
            }
        }

        return noticeImages;
    }

    // 공지사항 썸네일 찾기
    @Transactional
    public String findThumbnailUrl(final Notice notice) {
        return notice.getNoticeImages().stream()
                .findFirst()
                .map(NoticeImage::getImageUrl)
                .orElseThrow(ImageNotFoundException::new);
    }

    // 공지사항 이미지 전체 조회
    public List<String> findAllNoticeImages(final Notice notice) {
        return notice.getNoticeImages().stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }
}
