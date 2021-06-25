package Step1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * - coding: utf-8 -
 * Time    : 2021/5/24 22:25
 * Author  : Xilun Wu
 * Email   : nnuwxl@gmail.com
 * File    : Tomb.java
 */


/**
 * 自建容器
 */
public final class Tomb {
    /**
     * ali1头文件   String[]
     * ali0全文件   Arraylist<String>
     * ali2数据文件 float[][]
     */
    private String[] ali1;
    private float[][] ali2;
    private int cellsize=2;
    private float real_cellsize;
    private int colsnum;
    private int rowsnum;

    public Tomb(float[][] f)
    {
        ali2=f;
    }

    /**
     * 初始化的第二步，由文件头列表数组确定的成员在这个方法里确定
     * @param ali   文件头列表数组
     */
    public void setAli1(String[] ali)
    {
        this.ali1=ali;
        Pattern p = Pattern.compile("[\\d.]+");
        Matcher m = p.matcher(ali1[4]);
        m.find();
        this.real_cellsize=Float.parseFloat(m.group());
        Matcher m2 = p.matcher(ali1[0]);
        m2.find();
        this.colsnum = Integer.parseInt(m2.group());
        Matcher m3 = p.matcher(ali1[1]);
        m3.find();
        this.rowsnum = Integer.parseInt(m3.group());

    }

    public float getReal_cellsize()
    {
        return real_cellsize;
    }

    /**
     * 从edge,x,y获得该方向上相交栅格的Arraylist<int[]>
     *     int[]是x，y坐标
     *     具体算法在报告中有详细说明
     *     表现为平行线相交法
     * @param xcount     判断点的x坐标(不计算具体坐标)
     * @param ycount     判断点的y坐标(不计算具体坐标)
     * @param edge  方向，对正北方向顺时针的夹角
     * @return Arraylist<int[]>    计算得到的相交栅格的容器类Arraylist
     */
    protected ArrayList<int[]> XY_fromedge(int xcount, int ycount, double edge)
    {
        if(ali2[ycount][xcount]<-9900) return null;//当该点为nodata的时候就不用算了
        int x = xcount*this.cellsize+this.cellsize/2;
        int y = ycount*this.cellsize+this.cellsize/2;
        Abstractline line0 = new Line((float) x,(float) y,edge);
        Abstractline[] linelist = line0.twoPLby_dis((float) cellsize*(float)Math.sqrt(2)/2);
        Abstractline line1 = linelist[0];
        Abstractline line2 = linelist[1];
        //line0是当前的线
        //line1是其中左面的平行线
        //line2是其中右面的平行线

        ArrayList<int[]> outcomelist = new ArrayList<>();
        if((edge<=45)||(edge>315)){
            for(int i=y;i>0;i=i-2)
            {
                float x1 = line1.GetX(i);
                float x2 = line2.GetX(i);
                float xmax= Math.max(x1,x2);
                float xmin = Math.min(x1,x2);
                for(int xcount1=0;xcount1<colsnum;xcount1++)
                {
                    int x3 = xcount1*cellsize+cellsize/2;
                    if(x3<xmax&&x3>xmin)
                    {
                        outcomelist.add(new int[]{xcount1, (i - cellsize / 2) / cellsize});
                    }
                }
            }
        }else if(edge>45&&edge<=135){
            for(int i=x;i<colsnum*cellsize;i=i+2)
            {
                float y1 = line1.GetY(i);
                float y2 = line2.GetY(i);
                float ymax= Math.max(y1,y2);
                float ymin = Math.min(y1,y2);
                for(int ycount1=0;ycount1<rowsnum;ycount1++)
                {
                    int y3 =ycount1*cellsize+cellsize/2;
                    if(y3<ymax&&y3>ymin)
                    {
                        outcomelist.add(new int[]{(i - cellsize / 2) / cellsize,ycount1});
                    }
                }
            }
        }else if(edge>135&&edge<=225){
            for(int i=y;i<rowsnum*cellsize;i=i+2)
            {
                float x1 = line1.GetX(i);
                float x2 = line2.GetX(i);
                float xmax= Math.max(x1,x2);
                float xmin = Math.min(x1,x2);
                for(int xcount1=0;xcount1<colsnum;xcount1++)
                {
                    int x3 = xcount1*cellsize+cellsize/2;
                    if(x3<xmax&&x3>xmin)
                    {
                        outcomelist.add(new int[]{xcount1, (i - cellsize / 2) / cellsize});
                    }
                }
            }
        }else if(edge>225&&edge<=315){
            for(int i=x;i>0;i=i-2)
            {
                float y1 = line1.GetY(i);
                float y2 = line2.GetY(i);
                float ymax= Math.max(y1,y2);
                float ymin = Math.min(y1,y2);
                for(int ycount1=0;ycount1<rowsnum;ycount1++)
                {
                    int y3 =ycount1*cellsize+cellsize/2;
                    if(y3<ymax&&y3>ymin)
                    {
                        outcomelist.add(new int[]{(i - cellsize / 2) / cellsize,ycount1});
                    }
                }
            }
        }
        return outcomelist;
    }

    /**
     * 判断一个点能不能看到太阳
     * @param xcount     判断点的x坐标(不计算具体坐标)
     * @param ycount     判断点的y坐标(不计算具体坐标)
     * @param edge  方向，对正北方向顺时针的夹角（角度制）
     * @return 0：不能看到 1:能看到  2：Nodata
     */
    public int Canseesun(int xcount, int ycount, double edge)
    {
        ArrayList<int[]> intlistlist = this.XY_fromedge(xcount,ycount,edge);
        float thisheight=ali2[ycount][xcount];
        if(thisheight<-9900){return 2;}
        for(int[] intlist:intlistlist)
        {
            float laterheight = ali2[intlist[1]][intlist[0]];
            if(laterheight>thisheight){
                intlistlist.clear();
                return 0;
            }

        }
        intlistlist.clear();
        return 1;
    }

    /**
     * 根据输入的XY坐标及太阳角度计算可见太阳的角度
     * 梯度度数为5度
     * @param xcount    横坐标
     * @param ycount    纵坐标
     * @param edge      太阳角度：方向，对正北方向顺时针的夹角（角度制）
     * @return 度数 int
     */
    public int Canseeangle(int xcount,int ycount,double edge)
    {
        int outcomeint = 0;
        for(int i=5;i<=60;i=i+5)
        {
            if(Canseesun(xcount, ycount, edge+i)==1)
            {outcomeint+=5;}
            if(Canseesun(xcount, ycount, edge-i)==1)
            {outcomeint+=5;}
        }
        return outcomeint;
    }

}
