package Step1;

/**
 * - coding: utf-8 -
 * Time    : 2021/5/24 22:48
 * Author  : Xilun Wu
 * Email   : nnuwxl@gmail.com
 * File    : Line.java
 */
//我感觉可以分四个大方向上，行交，列交、左看右看
public class Line implements Abstractline{
    private float x;
    private float y;
    private double edge;
    private double k;
    private int flag=0;
    //flag用来判断是否是tan90   0代表不是 1代表tan90 -1代表tan-90
    /**
     *构造函数
     * @param x 横坐标（地理位置）
     * @param y 纵坐标（地理位置）
     * @param edge  角度（度）
     */
    public Line(float x, float y, double edge)
    {
        this.x=x;
        this.y=y;
        this.edge=edge;
        if(edge%180!=0){
        this.k = Math.tan(Math.toRadians(90-edge));}
        else if(Math.round(edge)%360==0){flag=1;}
        else {flag=-1;}
    }

    /**
     * 按距离获得相互平行的两条相距dis的线
     * @param dis   距离
     * @return Abstractline[]接口下的两条线组成数组
     */
    @Override
    public Abstractline[] twoPLby_dis(float dis) {
        if(flag==0){
            float mid = (float) (dis/(Math.cos(Math.toRadians(edge))/Math.sin(Math.toRadians(edge))));
            return new Line[]{new Line(x + mid, y, edge), new Line(x - mid, y, edge)};
        }else {
            return new Line[]{new Line(x+dis,y,edge),new Line(x-dis,y,edge)};
        }

    }

    /**
     * 根据提供的Y值返回交线上对应X值
     * @param y1    提供的Y值
     * @return (float)横坐标值
     */
    @Override
    public float GetX(float y1) {
        if (flag==0)//要注意这里会出现除0异常，我会抛出给虚拟机
        {
            return (float) ((y1+k*x-y)/this.k);
        }else {
            return x;
        }
    }

    /**
     * 根据提供的X值返回交线上对应Y值
     * @param x1    提供的Y值
     * @return (float)横坐标值
     */
    @Override
    public float GetY(float x1) {
        if (flag==0){
            return (float) (k*(x1-this.x)+y);
        }else {throw new NotHaveKException();}
    }

}
