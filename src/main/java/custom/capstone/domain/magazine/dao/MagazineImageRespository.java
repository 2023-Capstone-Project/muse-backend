package custom.capstone.domain.magazine.dao;

import custom.capstone.domain.magazine.domain.MagazineImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineImageRespository extends JpaRepository<MagazineImage, Long> {
}
