package com.summerzhou.calculate.cache;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存数据
 */
public class CacheData {
    private static Logger logger = Logger.getLogger(CacheData.class);
    //用来储存<redisKey,pv>
    private static Map<String,Integer> pvMap;
    //用来储存<redisKey,uv>
    private static Map<String,Long> uvMap;



    public static Map<String, Integer> getPvMap() {
        return pvMap;
    }

    public static void setPvMap(Map<String, Integer> pvMap) {
        CacheData.pvMap = pvMap;
    }

    public static Map<String, Long> getUvMap() {
        return uvMap;
    }

    public static void setUvMap(Map<String, Long> uvMap) {
        CacheData.uvMap = uvMap;
    }

    /**
     * 获取当前pv和缓存pv的差值
     * @param pvKey
     * @param pv
     * @return
     */
    public static Integer getDifferentPv(String pvKey, Integer pv) {
        if(pvMap == null){
            pvMap = new HashMap<>();
        }
        Integer pvCache = pvMap.get(pvKey);
        pvMap.put(pvKey,pv);
        logger.info(pvKey+"已计算完成差值，当前pv值为："+pv);
        if(pvCache == null){
            return pv;
        }else {
            return pv-pvCache;
        }
    }

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        Integer a = map.get("a");
        System.out.println(a==null);
        map.put("a",1);
        System.out.println(map.get("a"));
    }

    /**
     * 获取当前uv和缓存uv的差值
     * @param uvKey
     * @param uv
     * @return
     */
    public static Long getDifferentUv(String uvKey, Long uv) {
        if(uvMap == null){
            uvMap = new HashMap<>();
        }
        Long uvCache = uvMap.get(uvKey);
        uvMap.put(uvKey,uv);
        logger.info(uvKey+"已计算完成差值，当前uv值为："+uv);
        if(uvCache == null){
            return uv;
        }else {
            return uv-uvCache;
        }
    }

    public static void clearCache(){
        pvMap = null;
        uvMap = null;
    }
}
