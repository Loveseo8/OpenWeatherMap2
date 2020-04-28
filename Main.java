package com.aad;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    class Weather {

        String name;
        M main;
        Sys sys;

    }

    class M {

        double temp;

    }

    class Sys {

        long dt;
        long sunrise;
        long sunset;

    }

    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;

    public static void main(String[] args) {

        String cityName = in.nextLine();

        Gson gson = new Gson();

        String info = "";

            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=d6843ab8ee963f5d372296dfff62aed7");
                URLConnection yc = url.openConnection();
                BufferedReader buffIn = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                while ((inputLine = buffIn.readLine()) != null) info = (inputLine);
                buffIn.close();

            } catch (IOException | NullPointerException e) {

                e.printStackTrace();

            }

        Weather w;

        w = gson.fromJson(info, Weather.class);

        Double temp = w.main.temp;
        String name = w.name;
        long sunr = w.sys.sunrise;
        long suns = w.sys.sunset;

        Date srr = new java.util.Date(sunr * 1000L);
        Date sss = new java.util.Date(suns * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        String sr = sdf.format(srr);
        String ss = sdf.format(sss);

        String dayLength = null;

        try {

            Date date1 = sdf.parse(sr);
            Date date2 = sdf.parse(ss);
            long diff = date2.getTime() - date1.getTime();
            int timeInSeconds = (int) (diff / 1000);
            int hours, minutes, seconds;

            hours = timeInSeconds / 3600;
            int tIS = timeInSeconds - (hours * 3600);
            minutes = tIS / 60;
            seconds = timeInSeconds - (hours * 3600) - (minutes * 60);

            String h;
            String m;
            String s;

            if (hours >= 10) h = Integer.toString(hours);
            else h = "0" + Integer.toString(hours);
            if (minutes >= 10) m = Integer.toString(minutes);
            else m = "0" + Integer.toString(minutes);
            if (seconds >= 10) s = Integer.toString(seconds);
            else s = "0" + Integer.toString(seconds);


            if (h.equals("00")) {

                dayLength = "00:" + m + ":" + s;

            } else {

                dayLength = h + ":" + m + ":" + s;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.printf("%s %s℃ \nsunrise: %s \nsunset: %s \nday length: %s", name, temp, sr, ss, dayLength);

    }
}
