package datastructure;

public class DivisionEdge {
	private final char weight;
	private final Division targetDivision;

	public DivisionEdge(char weight, Division targetDivision) {
		this.weight = weight;
		this.targetDivision = targetDivision;
	}

	public String toString() {
		return this.weight + "-->" + this.targetDivision.toString();
	}
}
