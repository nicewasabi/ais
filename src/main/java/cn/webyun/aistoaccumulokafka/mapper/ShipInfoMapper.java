package cn.webyun.aistoaccumulokafka.mapper;


import cn.webyun.aistoaccumulokafka.domain.BoatEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShipInfoMapper {

    @Select("SELECT * FROM ship_info")
    List<BoatEntity> getAll();


    @Select("SELECT * FROM ship_info WHERE mmsi = #{mmsi}")
    BoatEntity loadById(String mmsi) throws Exception;


    @Delete("DELETE FROM ship_info WHERE mmsi =#{mmsi}")
    void deleteBoat(String mmsi) throws Exception;


    @Insert("INSERT INTO ship_info(`mmsi`, `ship_name_en`, `imo`, `destination`, `ship_type_code`, `ship_name_cn`, `ship_country_code`)" +
            " VALUES(#{mmsi}, #{shipNameEn}, #{imo},#{destination}, #{shipTypeCode},#{shipNameCn}, #{shipCountryCode})")
    void insert(BoatEntity boatEntity);
}
