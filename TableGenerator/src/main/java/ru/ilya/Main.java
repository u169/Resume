package ru.ilya;

import ru.ilya.parsers.ParserTSV;
import ru.ilya.parsers.ParserXML;
import java.io.*;
import java.util.*;

/**
 * Created by ilya on 30.07.17.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String[] inputted = new String[3];
        String path = "resources/";
        String xmlPath;
        String tsvPath;
        String resPath;

        try {
            inputted = args;
        } catch (Exception e) {
            System.out.println("Введено неверное/битое количество аргументов");
        }

        xmlPath = path + inputted[0];
        tsvPath = path + inputted[1];
        resPath = "NewTablesThere/" + inputted[2];

//      Парсим XML
        ParserXML pXML = new ParserXML(xmlPath);
        LinkedHashMap<String, Integer> map = pXML.getMapOut();

//        Парсим TSV
        ParserTSV pTSV = new ParserTSV(tsvPath);
        List<String[]> allTSVRows = pTSV.getListOut();

//        Собриаем параметры таблицы
        LinkedHashMap<String, Integer> pColumns = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            pColumns.put(entry.getKey(), entry.getValue());
        }
        pColumns.remove("height");
        pColumns.remove("width");
        String[][] columns = new String[pColumns.size()][2];
        int counter = 0;
        for (Map.Entry<String, Integer> entry: pColumns.entrySet()) {
            columns[counter][0] = entry.getKey();
            columns[counter++][1] = entry.getValue().toString();
        }

//        Получаем Строки таблицы
        TableStrings ts = new TableStrings(allTSVRows, columns, map);
        ArrayList<String> result = ts.getResult();

//        Записываем в файл
        result.remove(0);
        result.remove(result.size()-1);
        OutputStream output = new FileOutputStream(resPath);
        OutputStreamWriter osw = new OutputStreamWriter(output, "UTF-16");
        for (String s : result) {
            osw.write(s + "\n");
        }
        osw.write("\n");
        osw.write("КОММЕНТАРИИ К ПРИМЕРУ:\n" +
                "\n" +
                "1) Слово \"Константин\" разбито прямо посередине, потому что не удалось разбить значение \"Павлов Константин\" только по границам слов\n" +
                "\n" +
                "2) Строка данных \"Ким Чен Ир\" не уместилась на первой странице целиком (потому что \"Ир\" был бы на 13й строке), \n" +
                "поэтому она целиком перенесена на следующую страницу. \n" +
                "\n" +
                "ДАННОЕ ПОВЕДЕНИЕ НЕ ОПИСАНО В ТРЕБОВАНИЯХ И ЯВЛЯЕТСЯ РЕШЕНИЕМ РАЗРАБОТЧИКА.\n\n" +
                "3) \"Юлианна-Оксана Сухово-Кобылина\" - когда возможно, разделитель остаётся на предыдущей строке (Сухово-), когда не возможно - переносится на слудующую (-Оксана).\n" +
                "Исключение только для пробела, например пробел между Оксана и Сухово просто совмещён с пробелом, отделяющим значение от |\n" +
                "\n" +
                "ДАННОЕ ПОВЕДЕНИЕ НЕ ОПИСАНО В ТРЕБОВАНИЯХ И ЯВЛЯЕТСЯ РЕШЕНИЕМ РАЗРАБОТЧИКА.");
        osw.close();
    }

}