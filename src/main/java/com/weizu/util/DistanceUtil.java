package com.weizu.util;

public class DistanceUtil {

    private static final double EARTH_RADIUS = 6378137;

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;

        float s3 = 10000;
        float s2 = Math.round(s * 10000) / s3;
        return s2;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static void main(String[] args){
        double la1 = 39.96961208767361;
        double lng1 = 116.33464816623264;
        System.out.println(DistanceUtil.getDistance(lng1,la1 , 116.334885,39.969566));
    }

}
