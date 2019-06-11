

package cn.webyun.aistoaccumulokafka.common;

import org.opengis.feature.simple.SimpleFeatureType;

public interface AccumuloData {

    String getTypeName();
    String getTypeNameLess();
    SimpleFeatureType getSimpleFeatureType();
    SimpleFeatureType getSimpleFeatureTypeLess();

}
