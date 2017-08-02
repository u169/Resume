package ru.ilya.parsers;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ilya on 02.08.17.
 */
public class ParserTSV {

    private String pathTSV;
    private List<String[]> listOut;

    public String getPathXML() {
        return pathTSV;
    }

    public List<String[]> getListOut() {
        return listOut;
    }

    public ParserTSV(String path){
        this.pathTSV = path;
        this.listOut = getAllRows(this.pathTSV);
    }

    private List<String[]> getAllRows(String tsvPath) {

        TsvParserSettings settings = new TsvParserSettings();
        TsvParser parser = new TsvParser(settings);
        return parser.parseAll(new File(tsvPath), "UTF-16");

    }
}
