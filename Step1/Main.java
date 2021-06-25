package Step1;

import Step1.IO_class;
import Step1.Tomb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * - coding: utf-8 -
 * Time    : 2021/5/24 17:32
 * Author  : Xilun Wu
 * Email   : nnuwxl@gmail.com
 * File    : Main.java
 */

public class Main {
    public static void main(String[] args) throws IOException{
        File fi = new File("src\\Step1\\DataInput\\dem_25.asc");
        String fos = "src\\Step1\\DataOutput\\DEM_SUN_25_$.asc";
        File csvfile = new File("src\\Step1\\DataInput\\方位角.csv");
        //定义了输入输出asc栅格文件,还有表格文件所在地址

        ArrayList<String> ali0 = IO_class.Read2Array(fi);
        String[] ali1 = {ali0.get(0),ali0.get(1),ali0.get(2),ali0.get(3),ali0.get(4),ali0.get(5)};
        //ali1头文件   String[]
        //ali0全文件   Arraylist<String>
        //ali2数据文件 float[][]

        int[] intl = IO_class.ReadHead(ali1);
        int rownum = intl[0];
        int colnum = intl[1];
        float[][] ali2 = new float[rownum][colnum];
        for(int i=6;i<6+rownum;i++)
        {
            String line = ali0.get(i);
            String[] lineS = line.split(" ");
            for(int colnum1=0;colnum1<colnum;colnum1++)
            {
                ali2[i-6][colnum1] = Float.parseFloat(lineS[colnum1]);
            }
        }
        Tomb tomb = new Tomb(ali2);
        tomb.setAli1(ali1);
        //至此，数据准备完成 4s

        double[] edgelist = IO_class.Readcsv(csvfile);
        //csv表格数据读取完毕 double[8]

        for(int i =0;i<8;i++){
            double edge = edgelist[i];
            int[][] outcomelistlist = new int[ali2.length][ali2[0].length];
            for(int ycount=0;ycount<rownum;ycount++){
                for(int xcount=0;xcount<colnum;xcount++){
                    int visible = tomb.Canseesun(xcount,ycount,edge);
                    if(visible==2){visible=-9999;}
                    outcomelistlist[ycount][xcount]=visible;
                }
            }

            //输出文件
            String fo = fos.replace("$",""+(i+1));
            IO_class.WritebyArray(outcomelistlist,fo,ali1);
            System.out.println(fo+"输出完成！");
        }

    }
}
