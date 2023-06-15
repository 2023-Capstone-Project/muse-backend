package custom.capstone.domain.trading.api;

import custom.capstone.domain.trading.application.TradingService;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.dto.TradingSaveRequestDto;
import custom.capstone.domain.trading.dto.TradingUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "거래 API")
@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradingApiController {
    private final TradingService tradingService;

    @Operation(summary = "거래 생성")
    @PostMapping
    public Long saveTrading(@RequestBody final TradingSaveRequestDto requestDto) {
        return  tradingService.saveTrading(requestDto);
    }

    @Operation(summary = "거래 수정")
    @PatchMapping("/{tradingId}/edit")
    public Long updateTrading(@PathVariable("tradingId") final Long id,
                              @RequestBody TradingUpdateRequestDto requestDto) {
        return tradingService.updateTrading(id, requestDto);
    }

    @Operation(summary = "거래 조회")
    @GetMapping("/{tradingId}")
    public Trading findTradingById(@PathVariable("tradingId") final Long id) {
        return tradingService.findById(id);
    }

    @Operation(summary = "거래 취소")
    @DeleteMapping("/{tradingId}")
    public Long deleteTrading(@PathVariable("tradingId") final Long id) {
        tradingService.deleteTrading(id);
        return id;
    }
}
