package nextstep.subway.application;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Sections;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import nextstep.subway.dto.LineRequest;
import nextstep.subway.dto.LineResponse;
import nextstep.subway.dto.SectionRequest;
import nextstep.subway.dto.SectionResponse;
import nextstep.subway.dto.UpdateLineRequest;
import nextstep.subway.exception.NoLineException;
import nextstep.subway.exception.NoStationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(LineRepository lineRepository,
        StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public LineResponse createLine(LineRequest lineRequest) {
        Line line = lineRepository.save(lineRequest.toLine(
            findStation(lineRequest.getUpStationId()),
            findStation(lineRequest.getDownStationId()))
        );
        return LineResponse.of(line);
    }

    public List<LineResponse> getLines() {
        return lineRepository.findAll().stream()
            .map(LineResponse::of)
            .collect(Collectors.toList());
    }

    public LineResponse getLine(final Long id) {
        return LineResponse.of(findLine(id));
    }

    @Transactional
    public void updateLine(Long id, UpdateLineRequest request) {
        Line line = findLine(id);
        line.merge(request.toLine());
    }

    @Transactional
    public void deleteLine(Long id) {
        lineRepository.delete(findLine(id));
    }

    @Transactional
    public void addSection(final Long id, final SectionRequest request) {
        Line line = findLine(id);
        Section section = new Section(findStation(request.getUpStationId()),
            findStation(request.getDownStationId()), request.getDistance());
        line.addSection(section);
    }

    private Line findLine(Long id) {
        return lineRepository.findById(id).orElseThrow(NoLineException::new);
    }

    public Station findStation(final Long stationId) {
        return stationRepository.findById(stationId)
            .orElseThrow(NoStationException::new);
    }

    public List<SectionResponse> getSections(final Long id) {
        Line line = findLine(id);
        Sections sections = line.getSections();
        return sections.getSections().stream()
            .map(SectionResponse::of)
            .collect(Collectors.toList());
    }
}
