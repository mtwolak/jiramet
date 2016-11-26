package similarity;

public class IssuesSimilarityParameters
{
	private final double SUMMARY_WEIGHT = 0.45;
	private final double DESCRIPTION_WEIGHT = 0.45;
	private final double COMMENTS_WEIGHT = 0.1;

	public double getSummaryWeight() {
		return SUMMARY_WEIGHT;
	}

	public double getDescriptionWeight() {
		return DESCRIPTION_WEIGHT;
	}

	public double getCommentsWeight() {
		return COMMENTS_WEIGHT;
	}
}
