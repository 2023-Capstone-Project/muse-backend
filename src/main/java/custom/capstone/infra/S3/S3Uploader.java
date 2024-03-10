package custom.capstone.infra.S3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 이미지 파일 업로드
     */
    public String uploadImage(final MultipartFile image, final String folderPath) throws IOException{

        // TODO: 유효한 이미지 파일 형식 지정하기
        // validImage(image);

        final String key = UUID.randomUUID().toString();

        final PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(folderPath + key)
                .contentType(image.getContentType())
                .contentLength(image.getSize())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));

        return s3Client.utilities().getUrl(
                GetUrlRequest.builder()
                        .bucket(bucket)
                        .key(folderPath + key)
                        .build()
        ).toExternalForm();
    }

    /**
     * 다중 이미지 파일 업로드
     */
    public List<String> uploadImageList(final List<MultipartFile> images, final String folderPath) throws IOException {

        final List<String> imageUrls = new ArrayList<>();

        if (!images.isEmpty()) {
            for (final MultipartFile image : images) {
                imageUrls.add(uploadImage(image, folderPath));
            }
        }

        return imageUrls;
    }

    // TODO: 유효한 파일 형식
//    private void validImage(final MultipartFile image) {
//        final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
//        final List<String> ALLOWED_IMAGE_CONTENT_TYPES = List.of("image/jpeg", "image/png", "image/gif");
//
//        if (image.getSize() > MAX_FILE_SIZE) {
//                throw new IllegalStateException("File size exceeds the maximum allowed size (5MB).");
//            }
//
//        if (!ALLOWED_IMAGE_CONTENT_TYPES.contains(image.getContentType())) {
//            throw new IllegalStateException("Invalid file format. Allowed formats: JPEG, PNG, GIF.");
//        }
//    }
}
