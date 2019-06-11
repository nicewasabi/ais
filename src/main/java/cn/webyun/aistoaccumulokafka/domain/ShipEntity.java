package cn.webyun.aistoaccumulokafka.domain;

import java.io.Serializable;


/**
 * @Project: xxl-job-executor-sample-spring
 * @Title: ShipEntity
 * @Description: hbase中对应t_position_new船舶实体
 * @author: zhongsb
 * @date: 2018年1月19日
 * @company: webyun
 * @UpdateTime：下午4:37:18
 * @Version :V1.0
 */
public class ShipEntity implements Serializable{

	/**
	* @Fields serialVersionUID :
	*/
	private static final long serialVersionUID = 1L;


	private String mmsi;

	private String imo;
	/**
	* @Fields callSign : 船呼号
	*/
	private String callsign;
	/**
	* @Fields shipName : 船舶英文名称
	*/
	private String shipname;
	/**
	* @Fields chineseShipname : 船舶中文名称
	*/
	private String chineseshipname;
	/**
	* @Fields longitude : 经度
	*/
	private String longitude;
	/**
	* @Fields latitude : 纬度
	*/
	private String latitude;
	/**
	* @Fields navStatus : 船舶状态
	*/
	private String navStatus;


	/**
	 * @Fields navStatus : 船舶状态
	 */
	private String navStatusCode;
	/**
	* @Fields speed : 速度
	*/
	private String speed;
	/**
	* @Fields heading : 船首向
	*/
	private String heading;

	/**
	* @Fields course : 轨迹向
	*/
	private String course;

	/**
	* @Fields shipCountry : 国籍
	*/
	private String shipcountry;
	/**
	* @Fields breadth : 船宽(米)
	*/
	private String breadth;
	/**
	* @Fields length : 船长(米)
	*/
	private String length;
	/**
	* @Fields zaizhongdun : 载重吨
	*/
	private String zaizhongdun;
	/**
	* @Fields jingzhongdun : 净重吨
	*/
	private String jingzhongdun;
	/**
	* @Fields zongdun : 总吨
	*/
	private String zongdun;
	/**
	* @Fields destination : 目的港
	*/
	private String destination;
	/**
	* @Fields eta : 预到港时间（MM-DD HH24:MI）
	*/
	private String eta;
	/**
	* @Fields draught : 吃水（米）
	*/
	private String draught;
	/**
	* @Fields shiptype : 船舶类型
	*/
	private String shiptype;
	/**
	* @Fields posttime : 位置时间（北京时间）
	*/
	private String posttime;

	/**
	* @Fields is_satellite : 卫星标志0岸基1卫星
	*/
	private String is_satellite;

	public String getMmsi() {
		return mmsi;
	}
	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}
	public String getImo() {
		return imo;
	}
	public void setImo(String imo) {
		this.imo = imo;
	}
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public String getShipname() {
		return shipname;
	}
	public void setShipname(String shipname) {
		this.shipname = shipname;
	}
	public String getChineseshipname() {
		return chineseshipname;
	}
	public void setChineseshipname(String chineseshipname) {
		this.chineseshipname = chineseshipname;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getNavStatus() {
		return navStatus;
	}
	public void setNavStatus(String navStatus) {
		this.navStatus = navStatus;
	}
	public String getNavStatusCode() {
		return navStatusCode;
	}
	public void setNavStatusCode(String navStatusCode) {
		this.navStatusCode = navStatusCode;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getShipcountry() {
		return shipcountry;
	}
	public void setShipcountry(String shipcountry) {
		this.shipcountry = shipcountry;
	}
	public String getBreadth() {
		return breadth;
	}
	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getZaizhongdun() {
		return zaizhongdun;
	}
	public void setZaizhongdun(String zaizhongdun) {
		this.zaizhongdun = zaizhongdun;
	}
	public String getJingzhongdun() {
		return jingzhongdun;
	}
	public void setJingzhongdun(String jingzhongdun) {
		this.jingzhongdun = jingzhongdun;
	}
	public String getZongdun() {
		return zongdun;
	}
	public void setZongdun(String zongdun) {
		this.zongdun = zongdun;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getDraught() {
		return draught;
	}
	public void setDraught(String draught) {
		this.draught = draught;
	}
	public String getShiptype() {
		return shiptype;
	}
	public void setShiptype(String shiptype) {
		this.shiptype = shiptype;
	}
	public String getPosttime() {
		return posttime;
	}
	public void setPosttime(String posttime) {
		this.posttime = posttime;
	}

	public String getIs_satellite() {
		return is_satellite;
	}
	public void setIs_satellite(String is_satellite) {
		this.is_satellite = is_satellite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breadth == null) ? 0 : breadth.hashCode());
		result = prime * result
				+ ((callsign == null) ? 0 : callsign.hashCode());
		result = prime * result
				+ ((chineseshipname == null) ? 0 : chineseshipname.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((draught == null) ? 0 : draught.hashCode());
		result = prime * result + ((eta == null) ? 0 : eta.hashCode());
		result = prime * result + ((heading == null) ? 0 : heading.hashCode());
		result = prime * result + ((imo == null) ? 0 : imo.hashCode());
		result = prime * result
				+ ((is_satellite == null) ? 0 : is_satellite.hashCode());
		result = prime * result
				+ ((jingzhongdun == null) ? 0 : jingzhongdun.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((mmsi == null) ? 0 : mmsi.hashCode());
		result = prime * result
				+ ((navStatus == null) ? 0 : navStatus.hashCode());
		result = prime * result
				+ ((posttime == null) ? 0 : posttime.hashCode());
		result = prime * result
				+ ((shipcountry == null) ? 0 : shipcountry.hashCode());
		result = prime * result
				+ ((shipname == null) ? 0 : shipname.hashCode());
		result = prime * result
				+ ((shiptype == null) ? 0 : shiptype.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result
				+ ((zaizhongdun == null) ? 0 : zaizhongdun.hashCode());
		result = prime * result + ((zongdun == null) ? 0 : zongdun.hashCode());
		result = prime * result + ((navStatusCode == null) ? 0 : navStatusCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipEntity other = (ShipEntity) obj;
		if (breadth == null) {
			if (other.breadth != null)
				return false;
		} else if (!breadth.equals(other.breadth))
			return false;
		if (callsign == null) {
			if (other.callsign != null)
				return false;
		} else if (!callsign.equals(other.callsign))
			return false;
		if (chineseshipname == null) {
			if (other.chineseshipname != null)
				return false;
		} else if (!chineseshipname.equals(other.chineseshipname))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (draught == null) {
			if (other.draught != null)
				return false;
		} else if (!draught.equals(other.draught))
			return false;
		if (eta == null) {
			if (other.eta != null)
				return false;
		} else if (!eta.equals(other.eta))
			return false;
		if (heading == null) {
			if (other.heading != null)
				return false;
		} else if (!heading.equals(other.heading))
			return false;
		if (imo == null) {
			if (other.imo != null)
				return false;
		} else if (!imo.equals(other.imo))
			return false;
		if (is_satellite == null) {
			if (other.is_satellite != null)
				return false;

		} else if (!is_satellite.equals(other.is_satellite))
			return false;
		if (jingzhongdun == null) {
			if (other.jingzhongdun != null)
				return false;
		} else if (!jingzhongdun.equals(other.jingzhongdun))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (mmsi == null) {
			if (other.mmsi != null)
				return false;
		} else if (!mmsi.equals(other.mmsi))
			return false;
		if (navStatus == null) {
			if (other.navStatus != null)
				return false;
		} else if (!navStatus.equals(other.navStatus))
			return false;
		if (posttime == null) {
			if (other.posttime != null)
				return false;
		} else if (!posttime.equals(other.posttime))
			return false;
		if (shipcountry == null) {
			if (other.shipcountry != null)
				return false;
		} else if (!shipcountry.equals(other.shipcountry))
			return false;
		if (shipname == null) {
			if (other.shipname != null)
				return false;
		} else if (!shipname.equals(other.shipname))
			return false;
		if (shiptype == null) {
			if (other.shiptype != null)
				return false;
		} else if (!shiptype.equals(other.shiptype))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		if (zaizhongdun == null) {
			if (other.zaizhongdun != null)
				return false;
		} else if (!zaizhongdun.equals(other.zaizhongdun))
			return false;
		if (zongdun == null) {
			if (other.zongdun != null)
				return false;
		} else if (!zongdun.equals(other.zongdun))
			return false;
		if (navStatusCode == null) {
			if (other.navStatusCode != null)
				return false;
		} else if (!navStatusCode.equals(other.navStatusCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ShipEntity [mmsi=" + mmsi + ", imo=" + imo + ", callsign="
				+ callsign + ", shipname=" + shipname + ", chineseshipname="
				+ chineseshipname + ", longitude=" + longitude + ", latitude="
				+ latitude + ", navStatus=" + navStatus + ", speed=" + speed
				+ ", heading=" + heading + ", course=" + course
				+ ", shipcountry=" + shipcountry + ", breadth=" + breadth
				+ ", length=" + length + ", zaizhongdun=" + zaizhongdun
				+ ", jingzhongdun=" + jingzhongdun + ", zongdun=" + zongdun
				+ ", destination=" + destination + ", eta=" + eta
				+ ", draught=" + draught + ", shiptype=" + shiptype
				+ ", posttime=" + posttime + ", is_satellite=" + is_satellite
				+", navStatusCode=" + navStatusCode
				+ "]";
	}


}
