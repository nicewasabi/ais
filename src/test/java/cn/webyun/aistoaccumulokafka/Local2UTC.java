package cn.webyun.aistoaccumulokafka;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Local2UTC {

    public static void main(String[] args) throws ParseException {
//
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date date = simpleDateFormat.parse("2019-04-19 10:00:00");
//        ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
////        System.out.println(zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Date date = new Date();

        System.out.println();
    }
}
