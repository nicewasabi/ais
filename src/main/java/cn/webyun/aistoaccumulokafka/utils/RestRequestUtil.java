package cn.webyun.aistoaccumulokafka.utils;

;
import cn.webyun.aistoaccumulokafka.common.ApiResultDto;
import cn.webyun.aistoaccumulokafka.common.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发起http请求并获取结果
* <p>Title:HttpRequestUtil </p>
* <p>Description: </p>
* <p>Company: webyun</p> 
* @author August
* @date 上午9:19:47
 */
@Component
public class RestRequestUtil {

	@Value("${api.getComKey}")
	public  String comKey;


	@Value("${api.getShipBasicInfo}")
	public  String allShipData;



	public static String token = "03486124c37e4bd29951b56073ea63c6";
	/////////////////////////请求的类型/////////////////////////////////////////
	private static final String HTTP_METHOD_POST = "POST";
	
	private static final String HTTP_METHOD_GET = "GET";
	
	private static final String HTTP_METHOD_DELETE = "DELETE";
	
	private static final String HTTP_METHOD_PATCH = "PATCH";
	
	private static final String HTTP_CONTENT_TYPE_JSON = "application/json";
	
//	public static String comKey = ConfigReaderUtil.get("api.getComKey");
//
//	public static String allShipData = ConfigReaderUtil.get("api.getShipBasicInfo");
	/////////////////////////请求方法/////////////////////////////////////////
	public static String get(String url, List<RestRequestParam> params) {
		return sendRequest(url, RestRequestUtil.HTTP_METHOD_GET, params, false);
	}
	
	public static String post(String url, List<RestRequestParam> params) {
		return sendRequest(url, HTTP_METHOD_POST, params, false);
	}
	
	public static String delete(String url, List<RestRequestParam> params) {
		return sendRequest(url, HTTP_METHOD_DELETE, params, false);
	}
	
	public static String patch(String url, List<RestRequestParam> params) {
		return sendRequest(url, HTTP_METHOD_PATCH, params, false);
	}
	
	public static String uploadFileByPost(String url, List<RestRequestParam> params) {
		return sendRequest(url, HTTP_METHOD_PATCH, params, true);
	}
	
	public static String uploadFileByPatch(String url, List<RestRequestParam> params) {
		return sendRequest(url, HTTP_METHOD_PATCH, params, true);
	}
	
	/////////////////////////发送请求核心方法/////////////////////////////////////////
	private static String sendRequest(String url, String httpMethodType, List<RestRequestParam> params, boolean isMultipartRequest) {  
		// 处理path参数
		url = paramHandlerForPath(url, params);
		
		// 检验url是否合法
        if(!urlChecked(url)){
        	throw new RuntimeException("url不合法");
        }
		
        // 处理query参数
        url = paramHandlerForQuery(url, params);

        // 处理body参数
        String bodyStr = paramHandlerForBody(params);
        
        // 生成body数据包
        StringEntity bodyEntity = StringUtils.isNotBlank(bodyStr) ? new StringEntity(bodyStr, Consts.UTF_8) : null;
        
        // 处理文件（或输入流）参数
        //HttpEntity httpEntity = paramHandlerForInputStream(params);
        
        
        CloseableHttpResponse response = null ;
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); ){// 3.创建默认的httpClient实例
        	
        	 switch (httpMethodType) {
				case HTTP_METHOD_GET:
					HttpRequestBase httpGet = new HttpGet(url);
					
					if(!CollectionUtils.isEmpty(params)) {//处理头信息
						for (RestRequestParam param : params) {
							if(Assert.isNotEmpty(param.getValue())) {
								if(param.getParamsType() == RestRequestParam.HEADER){
									httpGet.setHeader(param.getKey(), param.getValue().toString());
								}
							}
						}
					}
					
					response = httpClient.execute(httpGet);
					break;
				case HTTP_METHOD_POST:
					HttpPost httpPost = new HttpPost(url);
					if(! isMultipartRequest) {
						httpPost.setHeader("Content-type", HTTP_CONTENT_TYPE_JSON);
						httpPost.setHeader("Charset", Consts.UTF_8.toString());
						httpPost.setHeader("Accept-Charset", Consts.UTF_8.toString());
					}
					
					if(!CollectionUtils.isEmpty(params)) {//处理头信息
						for (RestRequestParam param : params) {
							if(Assert.isNotEmpty(param.getValue())) {
								if(param.getParamsType() == RestRequestParam.HEADER){
									httpPost.setHeader(param.getKey(), param.getValue().toString());
								}
							}
			    		}
					}
					
					if(bodyEntity != null) {
						httpPost.setEntity(bodyEntity);
					}
					/*if(httpEntity != null) {
						httpPost.setEntity(httpEntity);
					}*/
					response = httpClient.execute(httpPost);
					break;
				case HTTP_METHOD_DELETE:
					HttpDelete httpDelete = new HttpDelete(url);
					httpDelete.setHeader("Content-type", HTTP_CONTENT_TYPE_JSON);
					httpDelete.setHeader("Charset", Consts.UTF_8.toString());
					httpDelete.setHeader("Accept-Charset", Consts.UTF_8.toString());
					
					if(!CollectionUtils.isEmpty(params)) {//处理头信息,delete 参数在header
						for (RestRequestParam param : params) {
							if(Assert.isNotEmpty(param.getValue())) {
				            	if(param.getParamsType() != RestRequestParam.PATH){
				            		httpDelete.setHeader(param.getKey(), param.getValue().toString());
				            	}
							}
			    		}
					}
					
					response = httpClient.execute(httpDelete);
					break;
				case HTTP_METHOD_PATCH:
					HttpPatch httpPatch = new HttpPatch(url);
					if(! isMultipartRequest) {
						httpPatch.setHeader("Content-type", HTTP_CONTENT_TYPE_JSON);
						httpPatch.setHeader("Charset", Consts.UTF_8.toString());
						httpPatch.setHeader("Accept", "application/json");  
						httpPatch.setHeader("Accept-Charset", Consts.UTF_8.toString());
					}
			        
			        if(!CollectionUtils.isEmpty(params)) {//处理头信息
				        for (RestRequestParam param : params) {
				        	if(Assert.isNotEmpty(param.getValue())) {
					        	if(param.getParamsType() == RestRequestParam.HEADER){
				            		httpPatch.setHeader(param.getKey(), param.getValue().toString());
				            	}
				        	}
			    		}
			        }
			        
			        if(bodyEntity != null) {
			        	httpPatch.setEntity(bodyEntity);
			        }
			        /*if(httpEntity != null) {
			        	httpPatch.setEntity(httpEntity);
					}*/
					response = httpClient.execute(httpPatch);
					break;
				default:
					break;
			}
  
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
			try {
				if(response != null)
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return null;
    }
	
	
	@SuppressWarnings("unchecked")
	public static <T> ApiResultDto<T> toBean(String jsonStr, Class<T> contentClass){
		JSONObject fromObject = JSONObject.fromObject(jsonStr);
		Map<String, Class<T>> mapClass = new HashMap<>();
		mapClass.put("content", contentClass);
		ApiResultDto<T> api = (ApiResultDto<T>) JSONObject.toBean(fromObject, ApiResultDto.class, mapClass);
		return api;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String jsonStr, Class<T> contentClass){
		JSONArray fromObject = JSONArray.fromObject(jsonStr);
		List<T> bean = (List<T>)JSONArray.toCollection(fromObject, contentClass);
		return bean;
	}
	
	/**
	 * @Title: 检验url是否合法
	 * @param url
	 * @return
	 * @author: zhangx
	 * @date: 2017年8月30日 上午10:46:04
	 * @version v1.0
	 */
	private static boolean urlChecked(String url){
		boolean isHttpProtocol = StringUtils.contains(url, "http://") || StringUtils.contains(url, "https://");
		return isHttpProtocol ? ! StringUtils.containsAny(url, new char[]{'{','}'}) : isHttpProtocol;
	}

	
	/**
	 * @Title: 处理path参数
	 * @return
	 * @author: zhangx
	 * @date: 2017年8月31日 上午10:38:20
	 * @version v1.0
	 */
	private static String paramHandlerForPath(String url, List<RestRequestParam> params) {
		boolean isNeedPath = StringUtils.containsAny(url, new char[]{'{','}'});
		if(isNeedPath) {
			if(!CollectionUtils.isEmpty(params)) {
	        	for (RestRequestParam param : params) {
	        		if(Assert.isNotEmpty(param.getValue())) {
	                	if( param.getParamsType() == RestRequestParam.PATH ) {
	                		url = url.replace("{" + param.getKey() + "}", param.getValue().toString());
	                	}
	        		}
	    		}
	        }
		} 
		return url;
	}
	
	/**
	 * @Title: 处理query参数
	 * @return
	 * @author: zhangx
	 * @date: 2017年8月31日 上午10:38:20
	 * @version v1.0
	 */
	private static String paramHandlerForQuery(String url, List<RestRequestParam> params) {
		StringBuffer paramStr = new StringBuffer("?");
		if(!CollectionUtils.isEmpty(params)) {
			for (RestRequestParam param : params) {
				if(Assert.isNotEmpty(param.getValue())) {
					if( param.getParamsType() == RestRequestParam.QUERY ) {
						paramStr.append(param.getKey()).append("=").append(param.getValue()).append("&");
					}
				}
			}
		}
		return url + paramStr.substring(0, paramStr.length() -1);
	}
	
	/**
	 * @Title: 处理body参数,一次请求中只能有一个body
	 * @return
	 * @author: zhangx
	 * @date: 2017年8月31日 上午10:38:20
	 * @version v1.0
	 */
	private static String paramHandlerForBody(List<RestRequestParam> params) {
		String body = "";
        if(!CollectionUtils.isEmpty(params)) {
        	for (RestRequestParam param : params) {
        		if(Assert.isNotEmpty(param.getValue())) {
                	if( param.getParamsType() == RestRequestParam.BODY ) {
                		body = JSONObject.fromObject(param.getValue()).toString();
                		break;
                	}
        		}
    		}
        }
        return body;
	}
	
	
    /**
     * @Project: portal
     * @Title: HttpParams
     * @Description: rest请求中的参数
     * @author: zhangx
     * @date: 2017年8月29日 下午5:13:57
     * @company: webyun
     * @Copyright: Copyright (c) 2017
     * @version v1.0
     */
    public static class RestRequestParam {
    	
    	//////////////////////实体类属性///////////////////////
    	/**参数的key*/
    	private String key;
    	
    	/**参数的value*/
    	private Object value;
    	
    	/**参数类型*/
    	private int paramsType;
    	
    	/**文件（或输入流）参数的文件名*/
    	private String fileName;
    	
    	
    	//////////////////////参数类型///////////////////////
    	/**参数类型：请求头参数*/
    	public static int HEADER = 1;

    	/**参数类型：路径参数*/
    	public static int PATH = 2;
    	
    	/**参数类型：路径参数普通参数*/
    	public static int QUERY = 3;
    	
    	/**参数类型：对象参数*/
    	public static int BODY = 4;
    	
    	/**参数类型：输入流参数*/
    	public static int INPUTSTREAM = 5;
    	
    	/**参数类型：文件参数*/
    	public static int FILE = 6;

		public RestRequestParam(String key, Object value, int paramsType) {
			this.key = key;
			this.value = value;
			this.paramsType = paramsType;
		}
		
		public RestRequestParam(String key, Object value, int paramsType, String fileName) {
			this.key = key;
			this.value = value;
			this.paramsType = paramsType;
			this.fileName = fileName;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public int getParamsType() {
			return paramsType;
		}
		
		public void setParamsType(int paramsType) {
			this.paramsType = paramsType;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public String toString() {
			return "RestRequestParam [key=" + key + ", value=" + value + ", paramsType=" + paramsType + ", fileName="
					+ fileName + "]";
		}
		
    }
  //MD5数据
    public final static String MD5(String s) {  
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c', 'd','e','f'};        
         try {    
          byte[] btInput = s.getBytes(); 
             // 获得MD5摘要算法的 MessageDigest 对象
              MessageDigest mdInst = MessageDigest.getInstance("MD5"); 
             // 使用指定的字节更新摘要
              mdInst.update(btInput); 
             // 获得密文  
            byte[] md = mdInst.digest(); 
             // 把密文转换成十六进制的字符串形式  
            int j = md.length;   
           char str[] = new char[j * 2];
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];   
               str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
               str[k++] = hexDigits[byte0 & 0xf];     
         }    
          return new String(str);
        } catch (Exception e) {
          e.printStackTrace();      
          return null;
      }
    }
    /**文件上传测试
     * @param args
     * @throws FileNotFoundException
     */

    
    public static void main(String[] args) {
//    	String time = getDataByUrl(comKey,null);
//		String mmsi = null;
//		String md5token =  MD5(token+time);
//		System.out.println(md5token);
		//当前时间的long型
//		long endTime = new Date().getTime();
		//获取2天前的日期
		/*	String nextDate = DatetimeUtil.getNextDate(new Date(), Integer.valueOf(historyLineSpanStart));
		long startTime = DatetimeUtil.parseStringToLong(nextDate);
		//获取1天前的日期
		String lastDate = DatetimeUtil.getNextDate(new Date(), Integer.valueOf(historyLineSpanEnd));
		long endTime = DatetimeUtil.parseStringToLong(lastDate);
		System.out.println(md5token);
		System.out.println(startTime);
		System.out.println(endTime);*/
//    	RestRequestUtil service = new RestRequestUtil();
//    	service.putHistoryLinesToHBase();
    	/*String url = "http://shipapi.chinaports.com/dataApi/getComKey";
    	List<RestRequestParam> param = new ArrayList<>();
    	String time=getDataByUrl(url,param);
    	String md5token =  MD5(token+time);*/
    	/*String url1 = "http://shipapi.chinaports.com/dataApi/getPortShipbefore";
    	List<RestRequestParam> params = new ArrayList<>();
    	String url2 = "http://shipapi.chinaports.com/shipDataApi/getShipBasicInfo";
    	String mmsi = "102,107,111440512,111440706,1167373,123654987,123888889,149382685,149414,200005657,"
    			+ "201100095,201100124,201100125,201100129,201100149,202989952,203826638,203999329,203999339,203999383";
    	params.add(new RestRequestParam("mmsi",mmsi, RestRequestParam.QUERY));
        params.add(new RestRequestParam("key",md5token, RestRequestParam.QUERY));
        List<ShipEntity> get20ShipLatest = get20ShipLatest(url2, params);
        System.out.println("====================="+get20ShipLatest.size());*/
        
      /*  String historyTrack = "http://shipapi.chinaports.com/shipDataApi/getShipHistorTrack";
        
        String mmsi = "205214890";*/
//        String startTime = 
        /*Calendar instance = Calendar.getInstance();
        long endTime = new Date().getTime();
        instance.set(2018, 0, 5);
        long startTime = instance.getTimeInMillis();
        List<RestRequestParam> historyParams = new ArrayList<>();
        historyParams.add(new RestRequestParam("mmsi",mmsi, RestRequestParam.QUERY));
        historyParams.add(new RestRequestParam("key",md5token, RestRequestParam.QUERY));
        historyParams.add(new RestRequestParam("startTime",startTime, RestRequestParam.QUERY));
        historyParams.add(new RestRequestParam("endTime",endTime, RestRequestParam.QUERY));
        
        System.out.println("startTime="+startTime);
        System.out.println("endTime="+endTime);
        System.out.println("startDate="+new Date(startTime));
        System.out.println("endDate="+new Date(endTime));
        System.out.println("md5token="+md5token);*/
//        String response1 = RestRequestUtil.get(historyTrack, historyParams);
//        System.out.println(response1);
    	/*RestRequestUtil rest = new RestRequestUtil();
    	PortShipbefore model = new PortShipbefore();
    	rest.putEntityToRedis(model);*/
    	
    	
    	// 1.rest 资源
/*    	String url = "http://shipapi.chinaports.com/dataApi/getComKey";
    	List<RestRequestParam> param = new ArrayList<>();
    	 List<RestRequestParam> params = new ArrayList<>();*/
//    	putAllToMysql();
    	// 2.保存请求参数
    	// 3.添加参数
    		// 3.1 添加path的请求参数
    	//params.add(new RestRequestParam("id", "0101", RestRequestParam.BODY));
   		// 3.2 添加header的请求参数
//    	params.add(new RestRequestParam("token", "bd92acaa-20b5-4258-a074-13b79f6234a4", RestRequestParam.HEADER));
    		// 3.3 添加普通的请求参数
//    	params.add(new RestRequestParam("普通参数", "测试参数", RestRequestParam.QUERY));
    	//4.获得响应值
    /*	String time=getDataByUrl(url,param);
    	String url1 = "http://shipapi.chinaports.com/dataApi/getPortShipbefore";
        String shipname="HUA";//HUAYANGSUNRISE
        String md5token =  MD5(token+time);
        System.out.println(md5token);*/
//        String param = "mmsi="+mmsi+"&key="+md5token;
       /* String mmsi = "";
        List<RestRequestParam> para = new ArrayList<>();
        para.add(new RestRequestParam("shipname",shipname, RestRequestParam.QUERY));
        para.add(new RestRequestParam("key",md5token, RestRequestParam.QUERY));
        PortShipbefore bean1 = getPortShipbefore(url1, para);
        if(null !=bean1){
        	mmsi =bean1.getMmsi();
        }
       
        String url2 = "http://shipapi.chinaports.com/shipDataApi/getShipLatest";
        params.add(new RestRequestParam("mmsi",mmsi, RestRequestParam.QUERY));
        params.add(new RestRequestParam("key",md5token, RestRequestParam.QUERY));
        ShipLatest bean2 =getShipLatest(url2, params);
        System.out.println(bean2.getSpeed()+"---"+bean2.getMmsi()); */
        
/*        System.out.println("========================================");
        String url = "http://shipapi.chinaports.com/dataApi/getComKey";
        List<RestRequestParam> param = new ArrayList<>();
    	String time=getDataByUrl(url,param);
        String md5token =  MD5(token+time);*/
        //获取所有的数据
      /*  String urllist = "http://shipapi.chinaports.com/dataApi/getPortShipbefore";
        List<RestRequestParam> paramsList = new ArrayList<>();
        paramsList.add(new RestRequestParam("key",md5token, RestRequestParam.QUERY));
        List<PortShipbefore> ll =  getPortShipbeforeList(urllist, paramsList);
        System.out.println(ll.size());
        for(int i =0 ;i <ll.size() ;i++){System.out.println(ll.get(i).getMmsi()+"*************");}*/
    	
	}
    
    
    

	
	//提供的第一个接口,用来获取时间
	public static String getDataByUrl(String url,List<RestRequestParam> params) {
		try {
			String response = RestRequestUtil.get(url, params);
			JSONObject json = JSONObject.fromObject(response);
			Integer s = (Integer) json.get("status");
			if (0 == s) {
				String strs = (String) json.get("seedStr");
				return strs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	


	
}  