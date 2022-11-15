package nextstep.subway.section;

import static nextstep.subway.line.LineFixture.지하철_노선_생성;
import static nextstep.subway.line.LineFixture.지하철_노선_생성후_아이디_반환;
import static nextstep.subway.section.SectionFixture.구간_등록;
import static nextstep.subway.station.StationFixture.지하철역_생성후_아이디_반환;
import static org.assertj.core.api.Assertions.assertThat;

import nextstep.subway.AcceptanceTest;
import nextstep.subway.dto.StationResponse;
import nextstep.subway.line.LineFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SectionAcceptanceTest extends AcceptanceTest {

    Long 이호선;
    Long 강남역;
    Long 역삼역;
    Long 블루보틀역;

    @BeforeEach
    void setUpLine() {
        강남역 = 지하철역_생성후_아이디_반환("강남역");
        역삼역 = 지하철역_생성후_아이디_반환("역삼역");
        블루보틀역 = 지하철역_생성후_아이디_반환("블루보틀역");
        이호선 = 지하철_노선_생성후_아이디_반환("이호선", "bg-green-600", 7, 강남역, 역삼역);
    }

    /*
    GIVEN 강남역을 상행선 역삼역을 하행선으로 있는 이호선
    WHEN 강남역 역삼역 사이 블루보틀역 등록
    THEN 이호선 강남역 역삼역 사이 블루보틀역 추가
     */
    @Test
    @DisplayName("역 사이에 새로운 역 등록")
    void addStationBetween() {
        //when
        구간_등록(이호선, 강남역, 블루보틀역, 4);

        //then
        assertThat(LineFixture.지하철_노선_조회(이호선).jsonPath().getList("stations", StationResponse.class))
            .hasSize(3)
            .extracting(StationResponse::getName)
            .contains("강남역", "블루보틀역", "역삼역");
    }

}