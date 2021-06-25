package Step2;

import Step1.IO_class;
import Step1.Tomb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * - coding: utf-8 -
 * Time    : 2021/5/26 22:23
 * Author  : Xilun Wu
 * Email   : nnuwxl@gmail.com
 * File    : Main2.java
 */

public class Main2 {
    public static void main(String[] args) throws IOException {
        for(int nowid=1;nowid<=8;nowid++){
            File fi = new File("src\\Step1\\DataInput\\dem_25.asc");
            String fis = "src\\Step1\\DataOutput\\DEM_SUN_25_"+nowid+".asc";
            File csvfile = new File("src\\Step1\\DataInput\\方位角.csv");
            String fos = "src\\Step2\\DataOutput\\DEM_AnalyseSUN_25_"+nowid+".asc";
            //定义了输入输出asc栅格文件,还有表格文件所在地址

            ArrayList<String> ali0 = IO_class.Read2Array(fi);
            String[] ali1 = {ali0.get(0),ali0.get(1),ali0.get(2),ali0.get(3),ali0.get(4),ali0.get(5)};
            //ali1头文件   String[]
            //ali0全文件   Arraylist<String>
            //ali2数据文件 float[][]
            //ali3可见性数据文件  int[][]

            ArrayList<String> sli = IO_class.Read2Array(fis);

            int[] intl = IO_class.ReadHead(ali1);
            int rownum = intl[0];
            int colnum = intl[1];
            float[][] ali2 = new float[rownum][colnum];
            int[][] ali3 = new int[rownum][colnum];
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
            for(int i=6;i<6+rownum;i++)
            {
                String line = sli.get(i);
                String[] lineS = line.split(" ");
                for(int colnum1=0;colnum1<colnum;colnum1++)
                {
                    ali3[i-6][colnum1] = Integer.parseInt(lineS[colnum1]);
                }
            }
            //至此，数据准备完成 4s

            double[] edgelist = IO_class.Readcsv(csvfile);
            double nowedge = edgelist[nowid-1];
            //csv表格数据读取完毕 edgelist double[8]

            int[][] outcomelistlist = new int[ali2.length][ali2[0].length];
            for(int ycount=0;ycount<rownum;ycount++){
                for(int xcount=0;xcount<colnum;xcount++){
                    if(ali3[ycount][xcount]==1)
                    {
                        outcomelistlist[ycount][xcount]=tomb.Canseeangle(xcount,ycount,nowedge);
                    }else {
                        outcomelistlist[ycount][xcount]=ali3[ycount][xcount];
                    }
                }
            }

            IO_class.WritebyArray(outcomelistlist,fos,ali1);
            System.out.println(fos+"输出完成！");

        }
        System.out.println("执行完成!");
    }
}
