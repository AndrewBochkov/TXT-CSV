package ru.dcphoto;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        ConverterTXT ct = new ConverterTXT("sslova.txt");
        ct.writeConvert( "sslova.csv", ct.outString );
    }
}
