package me.dmjohnson.wgu.c196.db;

import java.util.Date;

public interface AlertInterface {

    int getId();
    Date getDate();
    String getTitle();
    String getText();

}
