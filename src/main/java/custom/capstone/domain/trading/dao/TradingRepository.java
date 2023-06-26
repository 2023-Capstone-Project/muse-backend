package custom.capstone.domain.trading.dao;

import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.domain.TradingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TradingRepository extends JpaRepository<Trading, Long> {
    @Modifying
    @Query("update Trading t" +
            " set t.status = :status" +
            " where t.id = :id")
    Optional<Trading> findById(@Param("id") final Long tradingId,
                               @Param("status") final TradingStatus status);
}