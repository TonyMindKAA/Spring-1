package com.epam.cdp.anton.krynytskyi.domain.io;

import com.epam.cdp.anton.krynytskyi.domain.model.BookingJSonObj;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookingJSonIO {

    private static Logger LOG = Logger.getLogger("BookingJSonIO");
    private Gson gson = new Gson();

    public BookingJSonObj read(File file) {
        BookingJSonObj bookingJSonObj = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            bookingJSonObj = gson.fromJson(br, BookingJSonObj.class);
        } catch (FileNotFoundException e) {
            LOG.error("Can't read file.");
        } catch (JsonSyntaxException e) {
            LOG.error("File has a mistakes, syntax error.");
        } catch (JsonIOException e) {
            LOG.error("Can't read file");
        }
        return bookingJSonObj;
    }

    public void write(File file, BookingJSonObj jSonObj) {
        String json = gson.toJson(jSonObj);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            LOG.error("Can't write JSon object to file");
        }
    }
}
