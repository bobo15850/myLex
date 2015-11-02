package datastructure;

public class DivisionEdge {
	private final char weight;

	public char getWeight() {
		return weight;
	}

	private final Division targetDivision;

	public Division getTargetDivision() {
		return targetDivision;
	}

	public DivisionEdge(char weight, Division targetDivision) {
		this.weight = weight;
		this.targetDivision = targetDivision;
	}

	public String toString() {
		return this.weight + "-->" + this.targetDivision.toString();
	}
}
