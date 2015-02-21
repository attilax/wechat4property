package com.focustar.entity.hd;

public class ScoreType {

	private String uuid;
	private String code;
	private String name;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(ScoreType eqScoreType){
		if(eqScoreType != null){
			//业务要求只显示积分
			if(this.code.equals(eqScoreType.getCode())){
				return true;
			}
		}
		return false;
	}
	
}
