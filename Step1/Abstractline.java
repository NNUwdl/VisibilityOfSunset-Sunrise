package Step1;

/**
 * - coding: utf-8 -
 * Time    : 2021/5/24 23:00
 * Author  : Xilun Wu
 * Email   : nnuwxl@gmail.com
 * File    : Abstractline.java
 */

public interface Abstractline {
    /**
     * 按距离获得相互平行的两条相距dis的线
     * @param dis   距离
     * @return Abstractline[]接口下的两条线组成数组
     */
    Abstractline[] twoPLby_dis(float dis);

    /**
     * 根据提供的Y值返回交线上对应X值
     * @param y    提供的Y值
     * @return (float)横坐标值
     */
    float GetX(float y);

    /**
     * 根据提供的X值返回交线上对应Y值
     * @param x    提供的Y值
     * @return (float)横坐标值
     */
    float GetY(float x);
}
