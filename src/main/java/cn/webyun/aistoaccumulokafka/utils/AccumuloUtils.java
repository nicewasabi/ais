package cn.webyun.aistoaccumulokafka.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.data.*;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.Hints;
import org.geotools.filter.identity.FeatureIdImpl;
import org.locationtech.geomesa.index.geotools.GeoMesaDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: xxl-job
 * @description: accumulo工具类
 * @author: wasabi
 * @Company: webyun
 * @create: 2018-08-16 09:05
 **/


@Component
public class AccumuloUtils {

    private static Logger logger = LogManager.getLogger(AccumuloUtils.class);

    private final String ACCUMULO_INSTANCE_ID = "accumulo.instance.id";
    private final String ACCUMULO_CATALOG = "accumulo.catalog";
    private final String ACCUMULO_USER = "accumulo.user";
    private final String ACCUMULO_PASSWORD = "accumulo.password";
    private final String ACCUMULO_ZOOKEEPERS = "accumulo.zookeepers";


    @Value("${accumulo.instance.id}")
    private String instance;

    @Value("${accumulo.catalog}")
    private String catalog;

    @Value("${accumulo.user}")
    private String user;

    @Value("${accumulo.password}")
    private String password;

    @Value("${accumulo.zookeepers}")
    private String zookeepers;


    private Map<String, String> params;


    public DataStore createDataStore() throws IOException {

       // System.out.println("Loading datastore");
        params = new HashMap<>();
        params.put(ACCUMULO_INSTANCE_ID, instance);
        params.put(ACCUMULO_CATALOG, catalog);
        params.put(ACCUMULO_USER, user);
        params.put(ACCUMULO_PASSWORD, password);
        params.put(ACCUMULO_ZOOKEEPERS, zookeepers);

        DataStore datastore = DataStoreFinder.getDataStore(params);
        if (datastore == null) {
            throw new RuntimeException("Could not create data store with provided parameters");
        }
        //System.out.println();
        return datastore;
    }


    public void createSchema(DataStore datastore, SimpleFeatureType sft) throws IOException {
        //System.out.println("Creating schema: " + DataUtilities.encodeType(sft));
        datastore.createSchema(sft);
    }


//    @Async("taskExecutor")
    public void writeFeatures(DataStore datastore, SimpleFeatureType sft, List<SimpleFeature> features) throws IOException {
        if (features.size() > 0) {
            try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer =
                         datastore.getFeatureWriterAppend(sft.getTypeName(), Transaction.AUTO_COMMIT)) {
                for (SimpleFeature feature : features) {

                    SimpleFeature toWrite = writer.next();


                    toWrite.setAttributes(feature.getAttributes());

                    ((FeatureIdImpl) toWrite.getIdentifier()).setID(feature.getID());
                    toWrite.getUserData().put(Hints.USE_PROVIDED_FID, Boolean.TRUE);
                    toWrite.getUserData().putAll(feature.getUserData());
                    writer.write();
                    logger.info("write data into accumulo "+feature);
                }
            }

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
               // System.err.println("Exception cleaning up test data: " + e.toString());
            } finally {
                // make sure that we dispose of the datastore when we're done with it
                datastore.dispose();
            }
        }
    }


}
