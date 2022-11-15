package nextstep.subway.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Station	upStation;
	@ManyToOne
	private Station downStation;
	private int distance;

	protected Section() {
	}

	public Section(final Station upStation, final Station downStation, final int distance) {
		this.upStation = upStation;
		this.downStation = downStation;
		this.distance = distance;
	}
}
