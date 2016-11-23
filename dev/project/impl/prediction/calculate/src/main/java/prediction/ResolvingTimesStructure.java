package prediction;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class ResolvingTimesStructure {
	private int assigneeID;
	private List<Pair<Integer, Double>> similarities;
	private List<Pair<Integer, Double>> issuesResolvingTimes;
	
	public ResolvingTimesStructure(){
		this.similarities = new ArrayList<Pair<Integer, Double>>();
		this.issuesResolvingTimes = new ArrayList<Pair<Integer, Double>>();
	}
	
	public ResolvingTimesStructure(int assigneeID){
		this.assigneeID = assigneeID;
		this.similarities = new ArrayList<Pair<Integer, Double>>();
		this.issuesResolvingTimes = new ArrayList<Pair<Integer, Double>>();
	}
	
	public ResolvingTimesStructure(int assigneeID, List<Pair<Integer, Double>> similarities){
		this.assigneeID = assigneeID;
		this.similarities = similarities;
		this.issuesResolvingTimes = new ArrayList<Pair<Integer, Double>>();
	}
	
	public int getAssigneeID(){
		return this.assigneeID;
	}
	
	public List<Pair<Integer, Double>> getSimilarities(){
		return this.similarities;
	}
	
	
	public double getIssueResolvingTimeValueByID(int issueID){
		for(Pair<Integer, Double> i : this.issuesResolvingTimes){
			if(i.getKey() == issueID){
				return i.getValue();
			}
		}
		return -1.0;
	}
	
	public void setAssigneeID(int assigneeID){
		this.assigneeID = assigneeID;
	}
	
	public void setSimilarities(List<Pair<Integer, Double>> similarities){
		this.similarities = similarities;
	}
	
	public void setIssuesResolvingTimes(List<Pair<Integer, Double>> issuesResolvingTimes){
		this.issuesResolvingTimes = issuesResolvingTimes;
	}
}
