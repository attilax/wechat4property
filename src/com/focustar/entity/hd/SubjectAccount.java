package com.focustar.entity.hd;

public class SubjectAccount {
	private ScoreType scoreType;
	private ScoreSubject scoreSubject;
	private double score;
	private String remark;
	
	public ScoreSubject getScoreSubject() {
		return scoreSubject;
	}
	public void setScoreSubject(ScoreSubject scoreSubject) {
		this.scoreSubject = scoreSubject;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ScoreType getScoreType() {
		return scoreType;
	}
	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}
	
	
}
