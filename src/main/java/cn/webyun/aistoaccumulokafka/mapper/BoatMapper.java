package cn.webyun.aistoaccumulokafka.mapper;


import cn.webyun.aistoaccumulokafka.domain.BoatEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoatMapper {

    @Select("SELECT * FROM t_boat")
    List<BoatEntity> getAll();


    @Select("SELECT * FROM t_boat WHERE mmsi = #{mmsi}")
    BoatEntity loadById(String mmsi) throws Exception;


    @Delete("DELETE FROM t_boat WHERE mmsi =#{mmsi}")
    void deleteBoat(String mmsi) throws Exception;


    @Insert("INSERT INTO t_boat(`mmsi`, `ship_name_en`, `imo`, `destination`, `ship_type_code`, `ship_name_cn`, `ship_country_code`)" +
            " VALUES(#{mmsi}, #{shipNameEn}, #{imo},#{destination}, #{shipTypeCode},#{shipNameCn}, #{shipCountryCode})")
    void insert(BoatEntity boatEntity);
}
