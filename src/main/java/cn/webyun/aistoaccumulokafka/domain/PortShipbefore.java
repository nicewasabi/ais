package cn.webyun.aistoaccumulokafka.domain;


/**
 * @Project: xxl-job-executor-sample-spring
 * @Title: PortShipbefore
 * @Description: 船舶基本信息存入mysql中
 * @author: zhongsb
 * @date: 2018年1月24日
 * @company: webyun
 * @UpdateTime：下午2:23:46
 * @Version :V1.0
 */
public class PortShipbefore {

	private String mmsi;
	private String shipName;
	private String imo;
	private String eta;
	private String destination;
	private String shiptype;
	private String chineseShipname;
	private String shipCountry;
	
	public String getMmsi() {
		return mmsi;
	}
	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}
	
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getImo() {
		return imo;
	}
	public void setImo(String imo) {
		this.imo = imo;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getShiptype() {
		return shiptype;
	}
	public void setShiptype(String shiptype) {
		this.shiptype = shiptype;
	}
	public String getChineseShipname() {
		return chineseShipname;
	}
	public void setChineseShipname(String chineseShipname) {
		this.chineseShipname = chineseShipname;
	}
	public String getShipCountry() {
		return shipCountry;
	}
	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}
	
}
