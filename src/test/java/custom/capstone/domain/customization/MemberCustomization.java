package custom.capstone.domain.customization;


import autoparams.customization.Customizer;
import autoparams.generator.ObjectContainer;
import autoparams.generator.ObjectGenerator;
import custom.capstone.domain.members.dto.request.MemberSaveRequestDto;

public class MemberCustomization implements Customizer {

    @Override
    public ObjectGenerator customize(final ObjectGenerator generator) {

        return ((query, context) -> query.getType().equals(MemberSaveRequestDto.class)
                ? new ObjectContainer(factory())
                : generator.generate(query, context));
    }

    private MemberSaveRequestDto factory() {

        return new MemberSaveRequestDto(
                "muse",
                "muse@muse.com",
                "muse123!",
                "muse123!",
                "010-1234-5678"
        );
    }
}
