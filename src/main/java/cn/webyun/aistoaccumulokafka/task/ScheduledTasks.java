package cn.webyun.aistoaccumulokafka.task;

import cn.webyun.aistoaccumulokafka.common.AisData;
import cn.webyun.aistoaccumulokafka.domain.BoatEntity;
import cn.webyun.aistoaccumulokafka.domain.PortShipbefore;
import cn.webyun.aistoaccumulokafka.domain.ShipEntity;
import cn.webyun.aistoaccumulokafka.mapper.ShipInfoMapper;
import cn.webyun.aistoaccumulokafka.utils.AccumuloUtils;
import cn.webyun.aistoaccumulokafka.utils.KafkaUtils;
import cn.webyun.aistoaccumulokafka.utils.RestRequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.geotools.data.DataStore;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static cn.webyun.aistoaccumulokafka.utils.RestRequestUtil.*;

@Component
public class ScheduledTasks {


    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

//    @Autowired
//    BoatMapper boatMapper;

    @Autowired
    ShipInfoMapper shipInfoMapper;


    @Autowired
    AisData aisData;

    @Autowired
    AccumuloUtils accumuloUtils;

    @Autowired
    KafkaUtils kafkaUtils;


    @Value("${api.getComKey}")
    public String comKey;


    @Value("${api.getShipBasicInfo}")
    public String allShipData;


    @Value("${api.getPortShipbefore}")
    public String allShip;


    private DataStore accumulodataStore;
    private DataStore kafkadataStore;
    private DataStore kafkadataStoreTypeCount;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    public String[] shipList = {"客船", "货船", "执法", "油轮", "拖轮", "引航", "高速船", "搜救艇", "渔船", "其他"};
    public String[] shipList = {"客船", "货船", "执法", "油轮", "拖轮", "引航", "高速船", "搜救艇", "渔船", "其他"};

    //    @Scheduled(cron = "0 0 */1 * * ? ")
    @Scheduled(cron = "0 */1 * * * ? ")
    public void saveAisDatatoAccumuloandKafka() {
        try {
//            accumulodataStore = accumuloUtils.createDataStore();
            kafkadataStore = kafkaUtils.createProducer();
            kafkadataStoreTypeCount = kafkaUtils.createProducer();
//            accumuloUtils.createSchema(accumulodataStore, aisData.getSimpleFeatureType());
            kafkaUtils.createSchema(kafkadataStore, aisData.getSimpleFeatureType());
            kafkaUtils.createSchema(kafkadataStoreTypeCount, aisData.getSimpleFeatureTypeLess());
            List<BoatEntity> list = null;

//            // date parser corresponding to the CSV format
//            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String pattern = "yyyy-MM-dd HH:mm:ss";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            // use a geotools SimpleFeatureBuilder to create our features
            SimpleFeatureBuilder builder = new SimpleFeatureBuilder(aisData.getSimpleFeatureType());
            SimpleFeatureBuilder builderTypeCount = new SimpleFeatureBuilder(aisData.getSimpleFeatureTypeLess());

            try {
                list = shipInfoMapper.getAll();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            List<RestRequestUtil.RestRequestParam> param;
            String md5token;
            String time;
            List<RestRequestUtil.RestRequestParam> params;
            BoatEntity shipbefore;
            StringBuffer mmsi20 = new StringBuffer();

            List<ShipEntity> get20ShipLatest;
            ShipEntity shipAis = null;
            param = new ArrayList<>();
            time = getDataByUrl(comKey, param);
            md5token = MD5(RestRequestUtil.token + time);

            if (list != null) {
                for (int j = 0; j < list.size(); j += 20) {
                    List<SimpleFeature> features = new ArrayList<>();
                    List<SimpleFeature> featuresLess = new ArrayList<>();
                    mmsi20.setLength(0);
                    params = new ArrayList<>();
                    for (int k = j; k < j + 20 && k < list.size(); k++) {
                        shipbefore = list.get(k);
                        if (k != j + 19 && k != list.size() - 1) {
                            mmsi20.append(shipbefore.getMmsi()).append(",");
                        } else {
                            mmsi20.append(shipbefore.getMmsi());
                        }
                    }
                    params.add(new RestRequestUtil.RestRequestParam("mmsi", mmsi20.toString(), RestRequestUtil.RestRequestParam.QUERY));
                    params.add(new RestRequestUtil.RestRequestParam("key", md5token, RestRequestUtil.RestRequestParam.QUERY));
                    get20ShipLatest = get20ShipLatest(allShipData, params);

                    if (get20ShipLatest != null) {

                        for (int m = 0; m < get20ShipLatest.size(); m++) {
                            shipAis = get20ShipLatest.get(m);
//                            System.out.println(shipAis);
                            builder.set("mmsi", shipAis.getMmsi());
                            builder.set("mmsi", shipAis.getMmsi());
                            builder.set("imo", shipAis.getImo());
                            builder.set("callsign", shipAis.getCallsign());
                            builder.set("shipname", shipAis.getShipname());
                            if (shipAis.getShipname() != null) {
                                builder.set("shipnamenospace", shipAis.getShipname().replace(" ", ""));
                            }
                            builder.set("chineseshipname", shipAis.getChineseshipname());
                            builder.set("speed", shipAis.getSpeed());
                            builder.set("heading", shipAis.getHeading());
                            builder.set("course", shipAis.getCourse());
                            builder.set("navStatus", shipAis.getNavStatus());
                            builder.set("shipcountry", shipAis.getShipcountry());
                            builder.set("breadth", shipAis.getBreadth());
                            builder.set("length", shipAis.getLength());
                            builder.set("zaizhongdun", shipAis.getZaizhongdun());
                            builder.set("jingzhongdun", shipAis.getJingzhongdun());
                            builder.set("zongdun", shipAis.getZongdun());
                            builder.set("destination", shipAis.getDestination());
                            builder.set("eta", shipAis.getEta());
                            builder.set("draught", shipAis.getDraught());
                            if (shipAis.getShiptype() != null) {
                                for (int i = 0; i < shipList.length; i++) {
                                    if (new String(shipList[i].getBytes(), "utf-8").equals(new String(shipAis.getShiptype().getBytes(),"utf-8"))) {
                                        builder.set("shiptype", "" + i);
                                        break;
                                    }
                                }
                            }
//                            builderTypeCount.set("shiptype", sh   ipAis.getShiptype());
                            builder.set("is_satellite", shipAis.getIs_satellite());

                            // some dates are converted implicitly, so we can set them as strings
                            // however, the date format here isn't one that is converted, so we parse it into a java.util.Date
                            if (shipAis.getPosttime() != null) {

//                                builder.set("posttime", Date.from(LocalDateTime.parse(shipAis.getPosttime(), dateFormat).toInstant(ZoneOffset.UTC)));
//                                builderTypeCount.set("posttime", Date.from(LocalDateTime.parse(shipAis.getPosttime(), dateFormat).toInstant(ZoneOffset.UTC)));
                                Date date = simpleDateFormat.parse(shipAis.getPosttime());
                                ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
//                                System.out.println(zdt.format(DateTimeFormatter.ofPattern(pattern)))
                                builder.set("posttime", zdt.format(DateTimeFormatter.ofPattern(pattern)));
                                builderTypeCount.set("posttime", zdt.format(DateTimeFormatter.ofPattern(pattern)));

                            }

                            // we can use WKT (well-known-text) to represent geometries
                            // note that we use longitude first ordering
                            double longitude = Double.parseDouble(shipAis.getLongitude());
                            double latitude = Double.parseDouble(shipAis.getLatitude());

                            builder.set("longitude", longitude);
                            builder.set("latitude", latitude);

                            builder.set("geom", "POINT (" + longitude + " " + latitude + ")");
                            builderTypeCount.set("geom", "POINT (" + longitude + " " + latitude + ")");


                            // be sure to tell GeoTools explicitly that we want to use the ID we provided
                            builder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
                            builderTypeCount.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);

                            // build the feature - this also resets the feature builder for the next entry
                            // use the taxi ID as the feature ID
                            features.add(builder.buildFeature(shipAis.getMmsi()));
                            featuresLess.add(builderTypeCount.buildFeature(shipAis.getMmsi()));

                        }
                        features = Collections.unmodifiableList(features);
                        featuresLess = Collections.unmodifiableList(featuresLess);
//                        accumuloUtils.writeFeatures(accumulodataStore, aisData.getSimpleFeatureType(), features);
//                        kafkaUtils.writeFeatures(kafkadataStore, aisData.getSimpleFeatureType(), features);
                        List<SimpleFeature> finalFeatures = features;
                        List<SimpleFeature> finalFeaturesLess = featuresLess;
//                        int finalJ = j;
                        threadPoolTaskExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
//                                    accumuloUtils.writeFeatures(accumulodataStore, aisData.getSimpleFeatureType(), finalFeatures);
                                    kafkaUtils.writeFeatures(kafkadataStore, aisData.getSimpleFeatureType(), finalFeatures);
//                                    kafkaUtils.writeFeaturesless(kafkadataStoreTypeCount, aisData.getSimpleFeatureTypeLess(), finalFeaturesLess);
//                                    System.out.println(finalJ);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    kafkaUtils.cleanup(kafkadataStore, aisData.getTypeName(), true);
                                }
                            }
                        });
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            kafkaUtils.cleanup(kafkadataStore, aisData.getTypeName(), true);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    //    @Scheduled(cron = "0 0 */1 * * ? ")
//    @Scheduled(cron = "0 */1 * * * ? ")
    public void savetoMysql() {
        List<RestRequestParam> param = new ArrayList<>();
        String time = getDataByUrl(comKey, param);
        String md5token = MD5(token + time);
        //获取所有的数据
        List<RestRequestParam> paramsList = new ArrayList<>();
        paramsList.add(new RestRequestParam("key", md5token, RestRequestParam.QUERY));
        List<PortShipbefore> ll = getPortShipbeforeList(allShip, paramsList);
        BoatEntity loadById = null;
        BoatEntity model = null;
        List<BoatEntity> allShip = new ArrayList<BoatEntity>();
        for (int i = 0; i < ll.size(); i++) {
            model = new BoatEntity();
            model.setMmsi(ll.get(i).getMmsi());
            try {
                loadById = shipInfoMapper.loadById(ll.get(i).getMmsi());
                if (loadById != null) {//如果已经存在, 先删除再插入
                    //因为可能会修改船舶类型，所以先删除
                    shipInfoMapper.deleteBoat(ll.get(i).getMmsi());
                    model.setServiceStatus(loadById.getServiceStatus());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.setShipNameCn(ll.get(i).getChineseShipname());
            model.setDestination(ll.get(i).getDestination());
            model.setEta(ll.get(i).getEta());
            model.setImo(Integer.valueOf(ll.get(i).getImo() == null ? "0" : ll.get(i).getImo()));
            model.setShipCountryCode(ll.get(i).getShipCountry());
            model.setShipNameEn(ll.get(i).getShipName());
            model.setShipTypeCode(ll.get(i).getShiptype());
            allShip.add(model);
        }

        for (int j = 0; j < allShip.size(); j++) {
            try {
                shipInfoMapper.insert(allShip.get(j));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<ShipEntity> get20ShipLatest(String url1, List<RestRequestUtil.RestRequestParam> params) {
        try {
            String response1 = RestRequestUtil.get(url1, params);
            if (response1 != null) {
                JSONObject json = JSONObject.fromObject(response1);
                JSONArray datas = (JSONArray) json.get("data");
                if (datas != null) {
                    List<ShipEntity> list = toList(datas.toString(), ShipEntity.class);
                    return list;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static List<PortShipbefore> getPortShipbeforeList(String urllist, List<RestRequestParam> paramsList) {

        try {
            List<PortShipbefore> lists = new ArrayList<PortShipbefore>();
            String response1 = RestRequestUtil.get(urllist, paramsList);
            JSONObject json = JSONObject.fromObject(response1);
            if (null != json) {
                JSONArray datas = (JSONArray) json.get("data");
                if (null != datas && datas.size() > 0) {
                    for (int i = 0; i < datas.size(); i++) {
                        JSONObject d1 = (JSONObject) datas.get(i);
                        PortShipbefore bean2 = (PortShipbefore) JSONObject.toBean(d1, PortShipbefore.class);
                        lists.add(bean2);
                    }
                }
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
