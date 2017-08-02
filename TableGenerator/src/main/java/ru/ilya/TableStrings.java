package ru.ilya;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ilya on 02.08.17.
 */
public class TableStrings {

    private ArrayList<ArrayList<String>> data;
    private ArrayList<String> result;

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public TableStrings(List<String[]> tsvList,
                        String[][] columns,
                        LinkedHashMap<String, Integer> map) {

        this.data = tsvListToArr(tsvList, columns, map);
        this.result = getFinalRows(columns, map);

    }


    private ArrayList<ArrayList<String>> tsvListToArr(List<String[]> tsvList,
                                                     String[][] columns,
                                                     LinkedHashMap<String, Integer> map) {

        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        for(String[] s: tsvList) {
            ArrayList<String> z = getTableRow(s, columns, map);
            result.add(z);
        }
        return result;
    }

    //    Получаем непосредственно строки *.txt файла одной строки данных
    private static ArrayList<String> getTableRow(String[] dataRow,
                                                 String[][] columns,
                                                 LinkedHashMap<String, Integer> map) {
        ArrayList<String> result = new ArrayList<String>();

        int max = 0;
        int counter = 0;
        ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();
        for (String ss: dataRow) {
            int width = Integer.parseInt(columns[counter++][1]);
            ArrayList<String> words = new ArrayList<String>();
            Collections.addAll(words, ss.split(" "));
            ArrayList<String> s1 = new ArrayList<String>();
            int height = getFormatArrayOfCell(s1, words, width).size();
            max = height > max ? height : max;
            row.add(s1);
        }
        for (int i = 0; i < max; i++){
            StringBuilder rowly = new StringBuilder();
            rowly.append("| ");
            for(int j = 0; j < row.size(); j++) {
                int width = Integer.parseInt(columns[j][1]);
                if (i < row.get(j).size()) {
                    StringBuilder space = new StringBuilder();
                    String word = row.get(j).get(i);
                    for (int m = 0; m < width-word.length(); m++) {
                        space.append(" ");
                    }
                    rowly.append(row.get(j).get(i)).append(space).append(" | ");
                } else {
                    for (int m = 0; m < width; m++) {
                        rowly.append(" ");
                    }
                    rowly.append(" | ");
                }
            }
            result.add(rowly.substring(0, rowly.length()-1));
        }
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < map.get("width"); i++) {
            line.append("-");
        }
        result.add(line.toString());
        return result;
    }

    private ArrayList<String> getFinalRows(String[][] columns,
                                           LinkedHashMap<String, Integer> map) {

        ArrayList<String> result = new ArrayList<String>();
        //        Создаём разделитель строк
        String divider = "~";

        //        Создаём шапку
        ArrayList<String> hat;
        String[] hatAr = new String[columns.length];
        for (int i = 0; i < hatAr.length; i++) {
            hatAr[i] = columns[i][0];
        }
        hat = getTableRow(hatAr, columns, map);

//        Получаем конечные строки таблицы
        int counter = 0;
        for (ArrayList<String> al : data) {
            int needLines = al.size();
            int willLines = counter + needLines;
            int tableHeight = map.get("height");
            if (counter == 0 || willLines >= tableHeight) {
                if (!result.isEmpty()) {
                    result.remove(result.size()-1);
                }
                result.add(divider);
                result.addAll(hat);
                counter = 0;
            }
            result.addAll(al);
            counter += al.size();
        }
        return result;
    }


    private static ArrayList<String> getFormatArrayOfCell(ArrayList<String> res,
                                                          ArrayList<String> text,
                                                          int rowLen) {
        if (text.size() != 0) {
            String s = text.get(0);
            text.remove(0);
            if (s.length() > rowLen) {
                if (s.contains("-[a-zA-Z]")) {
                    int defPos = s.indexOf("-");
                    text.add(0, s.substring(defPos+1));
                    text.add(0, s.substring(0, defPos+1));
                } else if (s.contains("/")) {
                    int slashIndex = s.lastIndexOf("/");
                    text.add(0, s.substring(slashIndex + 1));
                    text.add(0, s.substring(0, slashIndex + 1));
                } else {
                    text.add(0, s.substring(rowLen));
                    text.add(0, s.substring(0, rowLen));
                }
            } else if (s.length() < rowLen && !s.contains("/")) {
                if (text.size() != 0 && (text.get(0)+s).length() < rowLen) {
                    s = s + " " + text.get(0);
                    text.remove(0);
                    text.add(0, s);
                } else {
                    res.add(s);
                }
            } else {
                res.add(s);
            }
            return getFormatArrayOfCell(res, text, rowLen);
        }

        return res;
    }
}
