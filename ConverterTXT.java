package ru.dcphoto;

import java.io.*;
import java.util.ArrayList;

public class ConverterTXT {

  public String nomerClass;
  public String tema;
  public ArrayList <String> outString = new ArrayList<>(0);

  ConverterTXT (String nameFile){
    getConvertFile(nameFile);
  }

  public String getConvertFile(String nameFile) {
    BufferedReader br = null;
    String result = null;

    try {
      br = new BufferedReader(new FileReader(nameFile));

      String line;
      while((line = br.readLine()) != null){
        if ( line.length() == 0) continue;
        switch (analizeString(line)) {
          case 1:
            setClass(line);
            break;
          case 2:
            setTema(line);
            break;
          case 3:
            parseWord(line);
            break;
        }
      }
      br.close();
    } catch (
            IOException e) {
      e.printStackTrace();
    }
    //.. System.out.println(">" + this.outString);

    return result;
  }
// Если в строке есть слова: класс и Словарные слова, то мы извлекаем класс и пишем в переменную КЛАСС
  private void setClass(String line) {
    int pos = line.indexOf("класс");
    this.nomerClass = line.substring(0,pos).trim();
    //..System.out.println("1:" + nomerClass);
  }
// Если в строке есть буква № то мы извлекаем тему
  private void setTema(String line) {
    int pos = line.indexOf(":");
    this.tema = line.substring(pos+1, line.length()).trim();
    //..System.out.println("2:" + tema);
  }
// Ну, а в этом случае мы разбираем и формируем готовую строку для файла CSV
  private void parseWord(String line) {
    //..System.out.println("3: " + line.length() + " "+ line);

    int i=0, nexti=0;
    while ( i<line.length()){
      nexti = line.indexOf(",", i);
      if ( nexti > 0 ) {
        this.outString.add (line.substring(i, nexti).trim().concat(";").concat(this.nomerClass).concat(";").concat(this.tema).concat(";"));
        i = nexti+1;
      } else if ( (nexti=line.indexOf(".", i))>0){
        this.outString.add (line.substring(i, nexti).trim().concat(";").concat(this.nomerClass).concat(";").concat(this.tema).concat(";"));
          i = line.length();
        } else {
        this.outString.add (line.substring(i, line.length()).trim().concat(";").concat(this.nomerClass).concat(";").concat(this.tema).concat(";"));
          i = line.length();
        }
      }
    }


  public int analizeString( String line) {
    boolean isContain1 = line.contains("класс");
    boolean isContain2 = line.contains("Словарные слова");
    boolean isContain3 = line.contains("№");
    // Если в строке есть слова: класс и Словарные слова, то 1
    if ( isContain1 && isContain2 ) return 1;
    // Если в строке есть буква № то 2
    if ( isContain3 ) return 2;
    // Ну, а иначе 3
    return 3;
  }

  public void writeConvert ( String nameFile, ArrayList <String> outString) {

    File file = new File(nameFile);
    try (FileWriter fw = new FileWriter(file)) {
      BufferedWriter bw = new BufferedWriter(fw);

      for( String s : outString) {
        bw.write(s + "\n");
      }
      bw.flush();
      bw.close();
      fw.close();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
