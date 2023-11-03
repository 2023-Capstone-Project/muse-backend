package custom.capstone.domain.posts.application;

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

        final PostImage postImage = PostImage.builder()
                .post(post)
                .imageUrl(imageUrl)
                .build();

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
            for(final String imageUrl : imageUrls) {
                final PostImage postImage = postImageRepository.save(new PostImage(post, imageUrl));
                postImages.add(postImage);
            }
        }

        return postImages;
    }

    public PostImage findPostImagesByPostId(final Long postId) {
        return postImageRepository.findPostImagesByPostId(postId);
    }
}
