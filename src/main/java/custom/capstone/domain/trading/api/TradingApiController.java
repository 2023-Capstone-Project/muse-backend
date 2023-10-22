package custom.capstone.domain.trading.api;

import custom.capstone.domain.trading.application.TradingService;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.dto.request.TradingSaveRequestDto;
import custom.capstone.domain.trading.dto.request.TradingUpdateRequestDto;
import custom.capstone.domain.trading.dto.response.TradingResponseDto;
import custom.capstone.domain.trading.dto.response.TradingSaveResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "거래 API")
@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradingApiController {
    private final TradingService tradingService;

    @Operation(summary = "거래 생성")
    @PostMapping
    public BaseResponse<TradingSaveResponseDto> saveTrading(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody final TradingSaveRequestDto requestDto
    ) {
        final TradingSaveResponseDto result = tradingService.saveTrading(loginEmail, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.TRADING_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "거래 수정")
    @PatchMapping("/{tradingId}")
    public BaseResponse<TradingResponseDto> updateTrading(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("tradingId") final Long id,
            @RequestBody final TradingUpdateRequestDto requestDto
    ) {
        final TradingResponseDto result = tradingService.updateTrading(loginEmail, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.TRADING_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "거래 조회")
    @GetMapping("/{tradingId}")
    public Trading findTradingById(@PathVariable("tradingId") final Long id) {
        return tradingService.findById(id);
    }
}
