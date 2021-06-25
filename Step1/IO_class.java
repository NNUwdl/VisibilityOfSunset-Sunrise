package Step1;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * - coding: utf-8 -
 * Time    : 2021/5/26 22:20
 * Author  : Xilun Wu
 * Email   : nnuwxl@gmail.com
 * File    : IO_class.java
 */

public class IO_class {
    /**
     * 用于读取ASC格式的数据，将数据读成数组输出
     * @param a:File:输入文件
     * @return Arraylist
     * @throws IOException
     */
    public static ArrayList<String> Read2Array(File a) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(a), StandardCharsets.UTF_8));
        ArrayList<String> ali= new ArrayList<>();
        String line;
        while ((line=br.readLine())!=null)
        {
            ali.add(line);
        }
        br.close();
        return ali;
    }
    public static ArrayList<String> Read2Array(String a) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(a), StandardCharsets.UTF_8));
        ArrayList<String> ali= new ArrayList<>();
        String line;
        while ((line=br.readLine())!=null)
        {
            ali.add(line);
        }
        br.close();
        return ali;
    }

    public static void WritebyArray(int[][] array,String f,String[] ali1) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));
        for(String s:ali1){bw.write(s+"\n");}
        for(int[] arraylist:array)
        {
            for (int i:arraylist){
                bw.write(""+i);
                bw.write(" ");
            }
            bw.write("\n");
        }
        bw.close();
    }

    public static void WritebyArray(double[][] array,String f,String[] ali1) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));
        for(String s:ali1){bw.write(s+"\n");}
        for(double[] arraylist:array)
        {
            for (double i:arraylist){
                if(i<-9900){bw.write(""+(-9999)+" ");}else {
                bw.write(""+i);
                bw.write(" ");}
            }
            bw.write("\n");
        }
        bw.close();
    }

    public static double[] Readcsv(File csv) throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8));
        br.readLine();
        double[] out = new double[8];
        for(int i =0;i<8;i++)
        {
            String line = br.readLine();
            double d = Double.parseDouble(line.split(",")[8]);
            out[i]=d;
        }
        br.close();
        return out;
    }

    public static int[] ReadHead(String[] ali1){
        //ali1头文件   String[]
        Pattern p = Pattern.compile("[\\d.]+");
        Matcher m = p.matcher(ali1[1]);
        m.find();
        int rownum = Integer.parseInt(m.group());
        Matcher m0 = p.matcher(ali1[0]);
        m0.find();
        int colnum = Integer.parseInt(m0.group());
        return new int[]{rownum, colnum};
    }

    public static ArrayList<int[]> ReadPoint(File csvfile, String[] ali1) throws IOException {
        ArrayList<int[]> outlist = new ArrayList<>();
        Pattern p = Pattern.compile("[\\d.]+");
        Matcher m = p.matcher(ali1[2]);
        m.find();
        float xllcorner=Float.parseFloat(m.group());
        m= p.matcher(ali1[3]);
        m.find();
        float yllcorner=Float.parseFloat(m.group());
        m= p.matcher(ali1[4]);
        m.find();
        float cellsize= Float.parseFloat(m.group());
        m= p.matcher(ali1[1]);
        m.find();
        int nrows= Integer.parseInt(m.group());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvfile), StandardCharsets.UTF_8));
        br.readLine();
        String s = "";
        while ((s= br.readLine())!=null)
        {
            float [] item = new float[]{Float.parseFloat(s.split(",")[0]),Float.parseFloat(s.split(",")[1])};
            int ycount = nrows-Math.round((item[1]-yllcorner)/cellsize);
            int xcount = Math.round((item[0]-xllcorner)/cellsize);
//            System.out.println(""+xcount+"   "+ycount);
            outlist.add(new int[]{xcount,ycount});
        }
        br.close();
        return outlist;
    }
}
