package data;

import java.io.Serializable;

public class ChartAreaData implements Serializable{
	
	private Integer year;
	private String month;
	private Integer recovered;
	private Integer totalCases;
	private Integer deathCases;
	
	public ChartAreaData() {
		this.month="";
		this.totalCases=0;
		this.deathCases=0;
		this.year=0;
		this.recovered=0;
	}
	
	public void ChartAreaData(String mon,Integer tot, Integer death) {
		this.month=mon;
		this.deathCases=death;
		this.totalCases=tot;
	}
	
	public void setYear(Integer inp) {
		this.year=inp;
	}
	
	public void setRecovered(Integer inp) {
		this.recovered=inp;
	}
	
	public void setMonth(String inp) {
		this.month=inp;
	}
	
	public void setTotalCases(Integer inp) {
		this.totalCases=inp;
	}
	
	public void setDeathCases(Integer inp) {
		this.deathCases=inp;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	public Integer getTotalCases() {
		return this.totalCases;
	}
	
	public Integer getDeathCases() {
		return this.deathCases;
	}
	
	public Integer getYear() {
		return this.year;
	}
	
	public Integer getRecovered() {
		return this.recovered;
	}
}
