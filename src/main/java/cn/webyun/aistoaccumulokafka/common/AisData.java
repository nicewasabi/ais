

package cn.webyun.aistoaccumulokafka.common;

import org.locationtech.geomesa.utils.interop.SimpleFeatureTypes;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.stereotype.Component;


@Component
public class AisData implements AccumuloData {

    private SimpleFeatureType sft = null;
    private SimpleFeatureType sftTypeCount = null;


    @Override
    public String getTypeName() {
        return "ais_data_1121";
    }

    @Override
    public String getTypeNameLess() {
        return "ais_data_type_number_count";
    }

    @Override
    public SimpleFeatureType getSimpleFeatureType() {
        if (sft == null) {
            StringBuilder attributes = new StringBuilder();
            attributes.append("mmsi:String,");
            attributes.append("imo:String,");
            attributes.append("callsign:String,");
            attributes.append("shipname:String,");
            attributes.append("shipnamenospace:String,");
            attributes.append("chineseshipname:String,");
            attributes.append("speed:String,");
            attributes.append("heading:String,");
            attributes.append("course:String,");
            attributes.append("navStatus:String,");
            attributes.append("shipcountry:String,");
            attributes.append("breadth:String,");
            attributes.append("length:String,");
            attributes.append("zaizhongdun:String,");
            attributes.append("jingzhongdun:String,");
            attributes.append("zongdun:String,");
            attributes.append("destination:String,");
            attributes.append("eta:String,");
            attributes.append("draught:String,");
            attributes.append("shiptype:String,");
            attributes.append("is_satellite:String,");
            attributes.append("posttime:String,");
            attributes.append("longitude:String,");
            attributes.append("latitude:String,");
            attributes.append("*geom:Point:srid=4326");

            sft = SimpleFeatureTypes.createType(getTypeName(), attributes.toString());
            sft.getUserData().put(SimpleFeatureTypes.DEFAULT_DATE_KEY, "posttime");
        }
        return sft;
    }


    public SimpleFeatureType getSimpleFeatureTypeLess() {
        if (sftTypeCount == null) {
            StringBuilder attributes = new StringBuilder();
            attributes.append("mmsi:String,");
            attributes.append("shiptype:String,");
            attributes.append("posttime:String,");
            attributes.append("*geom:Point:srid=4326");
            sftTypeCount = SimpleFeatureTypes.createType(getTypeNameLess(), attributes.toString());
            sftTypeCount.getUserData().put(SimpleFeatureTypes.DEFAULT_DATE_KEY, "posttime");
        }
        return sftTypeCount;
    }


}
