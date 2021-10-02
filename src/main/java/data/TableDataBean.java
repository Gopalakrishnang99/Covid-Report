package data;

import java.io.Serializable;

public class TableDataBean implements Serializable{
	
	private String stateName;
	private Integer totalCases;
	private Integer recoveredCases;
	private Integer deathCases;
	private Float deathRate;
	private Float recoveryRate;
	
	public TableDataBean() {
		
		this.stateName="";
		this.totalCases=0;
		this.recoveredCases=0;
		this.deathCases=0;
		this.deathRate=0F;
		this.recoveryRate=0F;
		
	}
	
	public TableDataBean(String state,Integer total,Integer recovered,Integer death,Float deathrate,Float recoveryrate) {
		
		this.stateName=state;
		this.totalCases=total;
		this.recoveredCases=recovered;
		this.deathCases=death;
		this.deathRate=deathrate;
		this.recoveryRate=recoveryrate;
		
	}
	
	public void setStateName(String state) {
		this.stateName=state;
	}
	
	public void setTotalCases(Integer inp) {
		this.totalCases=inp;
	}
	
	public void setRecoveredCases(Integer inp) {
		this.recoveredCases=inp;
	}
	
	public void setDeathCases(Integer inp) {
		this.deathCases=inp;
	}
	
	public void setDeathRate(Float inp) {
		this.deathRate=inp;
	}
	
	public void setRecoveryRate(Float inp) {
		this.recoveryRate=inp;
	}
	
	public String getStateName() {
		return this.stateName;
	}
	
	public Integer getTotalCases() {
		return this.totalCases;
	}
	
	public Integer getRecoveredCases() {
		return this.recoveredCases;
	}
	
	public Integer getDeathCases() {
		return this.deathCases;
	}
	
	public Float getDeathRate() {
		return this.deathRate;
	}
	
	public Float getRecoveryRate() {
		return this.recoveryRate;
	}
}
