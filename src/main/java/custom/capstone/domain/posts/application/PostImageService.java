package custom.capstone.domain.posts.application;

import custom.capstone.global.exception.ImageNotFoundException;
import custom.capstone.domain.posts.dao.PostImageRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostImage;
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
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final S3Uploader s3Uploader;

    /**
     * 게시글 단일 이미지 저장
     */
    @Transactional
    public PostImage savePostImage(final Post post, final MultipartFile image) throws IOException {

        final String folderPath = "post/";

        final String imageUrl = s3Uploader.uploadImage(image, folderPath);

        final PostImage postImage = new PostImage(post, imageUrl);

        return postImageRepository.save(postImage);
    }

    /**
     * 게시글 다중 이미지 저장
     */
    @Transactional
    public List<PostImage> savePostImage(final Post post, final List<MultipartFile> images) throws IOException {

        final String folderPath = "post/";

        final List<String> imageUrls = s3Uploader.uploadImageList(images, folderPath);

        final List<PostImage> postImages = new ArrayList<>();

        if (!imageUrls.isEmpty()) {
            for (final String imageUrl : imageUrls) {
                final PostImage postImage = postImageRepository.save(new PostImage(post, imageUrl));
                postImages.add(postImage);
            }
        }

        return postImages;
    }

    // 게시글 썸네일 찾기
    @Transactional
    public String findThumbnailUrl(final Post post) {
        return post.getPostImages().stream()
                .findFirst()
                .map(PostImage::getImageUrl)
                .orElseThrow(ImageNotFoundException::new);
    }

    // 게시글 이미지 전체 조회
    public List<String> findAllPostImages(final Post post) {
        return post.getPostImages().stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }
}
