package cn.webyun.aistoaccumulokafka.utils;

import cn.webyun.aistoaccumulokafka.common.AccumuloData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.locationtech.geomesa.index.geotools.GeoMesaDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: xxl-job
 * @description: Kafka工具类
 * @author: wasabi
 * @Company: webyun
 * @create: 2018-08-27 09:15
 **/
@Component
public class KafkaUtils {

    private static Logger logger = LogManager.getLogger(KafkaUtils.class);
    private DataStore consumer = null;
    private Map<String, String> params = new HashMap<>();

    private final String KAFKA_BROKERS = "kafka.brokers";
    private final String KAFKA_ZOOKEEPERS = "kafka.zookeepers";


    @Value("${kafka.brokers}")
    private String brokers;

    @Value("${kafka.zookeepers}")
    private String zookeepers;


    public DataStore createDataStore() throws IOException {
        //System.out.println("Loading datastore");
        params.put(KAFKA_BROKERS, brokers);
        params.put(KAFKA_ZOOKEEPERS, zookeepers);
        DataStore datastore = DataStoreFinder.getDataStore(params);
        if (datastore == null) {
            throw new RuntimeException("Could not create data store with provided parameters");
        }
        //System.out.println();
        return datastore;
    }


    public DataStore createProducer() throws IOException {
        params.put("kafka.consumer.count", "0");
        DataStore producer = createDataStore();

        params.put("kafka.consumer.count", "1");
        consumer = createDataStore();

        return producer;

    }


    public void createSchema(DataStore datastore, SimpleFeatureType sft) throws IOException {
        //System.out.println("Creating schema: " + DataUtilities.encodeType(sft));
        datastore.createSchema(sft);
       // System.out.println();
    }


    public void writeFeatures(DataStore datastore, SimpleFeatureType sft, List<SimpleFeature> features) throws IOException {

        SimpleFeatureSource consumerFS = consumer.getFeatureSource(sft.getTypeName());
        SimpleFeatureStore producerFS = (SimpleFeatureStore) datastore.getFeatureSource(sft.getTypeName());


        for (SimpleFeature feature : features) {
            producerFS.addFeatures(new ListFeatureCollection(sft, Collections.singletonList(feature)));
            logger.info("write data into kafka " + feature);
            System.out.println("write data into kafka " + feature);

        }
    }


    public void writeFeaturesless(DataStore datastore, SimpleFeatureType sft, List<SimpleFeature> features) throws IOException {


        SimpleFeatureStore producerFS = (SimpleFeatureStore) datastore.getFeatureSource("ais_data_type_number_count");


        for (SimpleFeature feature : features) {
            producerFS.addFeatures(new ListFeatureCollection(sft, Collections.singletonList(feature)));
            logger.info("write data into kafka " + feature);

        }
    }


    public void cleanup(DataStore datastore, String typeName, boolean cleanup) {
        if (datastore != null) {
            try {
                if (cleanup) {
                    if (datastore instanceof GeoMesaDataStore) {
                        ((GeoMesaDataStore) datastore).delete();
                    } else {
                        ((SimpleFeatureStore) datastore.getFeatureSource(typeName)).removeFeatures(Filter.INCLUDE);
                        datastore.removeSchema(typeName);
                    }
                }
            } catch (Exception e) {
               //System.err.println("Exception cleaning up  data: " + e.toString());

            } finally {
                // make sure that we dispose of the datastore when we're done with it
                datastore.dispose();
            }
        }
    }


}
