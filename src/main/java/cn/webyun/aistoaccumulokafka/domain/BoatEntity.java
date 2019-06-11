package cn.webyun.aistoaccumulokafka.domain;


import java.io.Serializable;

/**
 * @Project: webyun-api
 * @Title: Boat
 * @Description: 船舶基本信息实体
 * @Author: wuzd
 * @Date: 2018年1月24日 下午2:36:32
 * @Company: webyun
 * @Copyright: Copyright (c) 2018 Webyun. All Rights Reserved.
 * @Version v1.0
 */
public class BoatEntity implements Serializable {
    /**
     * mmsi
     */
    private String mmsi;
    /**
     * 英文船名
     */
    private String shipNameEn;
    /**
     * 中文船名
     */
    private String shipNameCn;
    /**
     * 呼号
     */
    private String callSign;
    /**
     * 船舶类型code
     */
    private String shipTypeCode;
    /**
     * imo
     */
    private Integer imo;
    /**
     * 船舶电话
     */
    private String boatPhone;
    /**
     * 船舶邮箱
     */
    private String boatMail;
    /**
     * 船东
     */
    private String owner;
    /**
     * 国籍code
     */
    private String shipCountryCode;
    /**
     * 船籍港
     */
    private Integer homePortId;
    /**
     * 船级社
     */
    private String classificationSocieties;
    /**
     * 船级社登记号
     */
    private String classificationSocietiesNo;
    /**
     * 建造年份
     */
    private String buildYear;
    /**
     * 建造厂家
     */
    private String buildShipyard;
    /**
     * 船东互保协会
     */
    private String smaa;
    /**
     * 船舶管理公司
     */
    private String manageBusiness;
    /**
     * 传真
     */
    private String boatFaxNo;
    /**
     * 卫星识别码
     */
    private String inmarsatCTlxNo;
    /**
     * 保险人
     */
    private String insurer;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人ID
     */
    private Integer createById;
    /**
     * 修改时间
     */
    private String modifyTime;
    /**
     * 修改人ID
     */
    private Integer modifyById;
    /**
     * 状态
     */
    private String boatStatus;

    /**
     * 功能状态
     */
    private String eta;

    /**
     * 功能描述
     */
    private String destination;

    /**
     * @Fields serviceStatus : 服务状态 1为系统服务的船舶
     */
    private Integer serviceStatus;

    /**
     * 船舶服务合同
     */
    private Integer serviceApply;

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


    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public String getShipNameEn() {
        return shipNameEn;
    }

    public void setShipNameEn(String shipNameEn) {
        this.shipNameEn = shipNameEn;
    }

    public String getShipNameCn() {
        return shipNameCn;
    }

    public void setShipNameCn(String shipNameCn) {
        this.shipNameCn = shipNameCn;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getShipTypeCode() {
        return shipTypeCode;
    }

    public void setShipTypeCode(String shipTypeCode) {
        this.shipTypeCode = shipTypeCode;
    }

    public Integer getImo() {
        return imo;
    }

    public void setImo(Integer imo) {
        this.imo = imo;
    }

    public String getBoatPhone() {
        return boatPhone;
    }

    public void setBoatPhone(String boatPhone) {
        this.boatPhone = boatPhone;
    }

    public String getBoatMail() {
        return boatMail;
    }

    public void setBoatMail(String boatMail) {
        this.boatMail = boatMail;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getShipCountryCode() {
        return shipCountryCode;
    }

    public void setShipCountryCode(String shipCountryCode) {
        this.shipCountryCode = shipCountryCode;
    }

    public Integer getHomePortId() {
        return homePortId;
    }

    public void setHomePortId(Integer homePortId) {
        this.homePortId = homePortId;
    }

    public String getClassificationSocieties() {
        return classificationSocieties;
    }

    public void setClassificationSocieties(String classificationSocieties) {
        this.classificationSocieties = classificationSocieties;
    }

    public String getClassificationSocietiesNo() {
        return classificationSocietiesNo;
    }

    public void setClassificationSocietiesNo(String classificationSocietiesNo) {
        this.classificationSocietiesNo = classificationSocietiesNo;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getBuildShipyard() {
        return buildShipyard;
    }

    public void setBuildShipyard(String buildShipyard) {
        this.buildShipyard = buildShipyard;
    }

    public String getSmaa() {
        return smaa;
    }

    public void setSmaa(String smaa) {
        this.smaa = smaa;
    }

    public String getManageBusiness() {
        return manageBusiness;
    }

    public void setManageBusiness(String manageBusiness) {
        this.manageBusiness = manageBusiness;
    }

    public String getBoatFaxNo() {
        return boatFaxNo;
    }

    public void setBoatFaxNo(String boatFaxNo) {
        this.boatFaxNo = boatFaxNo;
    }

    public String getInmarsatCTlxNo() {
        return inmarsatCTlxNo;
    }

    public void setInmarsatCTlxNo(String inmarsatCTlxNo) {
        this.inmarsatCTlxNo = inmarsatCTlxNo;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateById() {
        return createById;
    }

    public void setCreateById(Integer createById) {
        this.createById = createById;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyById() {
        return modifyById;
    }

    public void setModifyById(Integer modifyById) {
        this.modifyById = modifyById;
    }

    public String getBoatStatus() {
        return boatStatus;
    }

    public void setBoatStatus(String boatStatus) {
        this.boatStatus = boatStatus;
    }


    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Integer getServiceApply() {
        return serviceApply;
    }

    public void setServiceApply(Integer serviceApply) {
        this.serviceApply = serviceApply;
    }

    @Override
    public String toString() {
        return "BoatEntity [mmsi=" + mmsi + ", shipNameEn=" + shipNameEn
                + ", shipNameCn=" + shipNameCn + ", callSign=" + callSign
                + ", shipTypeCode=" + shipTypeCode + ", imo=" + imo
                + ", boatPhone=" + boatPhone + ", boatMail=" + boatMail
                + ", owner=" + owner + ", shipCountryCode=" + shipCountryCode
                + ", homePortId=" + homePortId + ", classificationSocieties="
                + classificationSocieties + ", classificationSocietiesNo="
                + classificationSocietiesNo + ", buildYear=" + buildYear
                + ", buildShipyard=" + buildShipyard + ", smaa=" + smaa
                + ", manageBusiness=" + manageBusiness + ", boatFaxNo="
                + boatFaxNo + ", inmarsatCTlxNo=" + inmarsatCTlxNo
                + ", insurer=" + insurer + ", createTime=" + createTime
                + ", createById=" + createById + ", modifyTime=" + modifyTime
                + ", modifyById=" + modifyById + ", boatStatus=" + boatStatus
                + ", eta=" + eta + ", destination=" + destination
                + ", serviceStatus=" + serviceStatus + "]"
                + "\n";
    }

}
