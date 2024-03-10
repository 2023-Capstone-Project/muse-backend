package custom.capstone.domain.customization;

import autoparams.customization.Customizer;
import autoparams.generator.ObjectContainer;
import autoparams.generator.ObjectGenerator;
import custom.capstone.domain.members.dto.request.MemberSaveRequestDto;
import custom.capstone.domain.posts.dto.request.PostSaveRequestDto;

import static custom.capstone.domain.posts.domain.PostType.CUSTOM;

public class PostCustomization implements Customizer {

    @Override
    public ObjectGenerator customize(final ObjectGenerator generator) {

        return ((query, context) -> query.getType().equals(MemberSaveRequestDto.class)
                ? new ObjectContainer(factory())
                : generator.generate(query, context));
    }

    private PostSaveRequestDto factory() {

        return new PostSaveRequestDto(
                "무즈 신발 커스텀",
                "무즈가 만든 커스텀 디자인",
                100000,
                "신발",
                CUSTOM
        );
    }
}
