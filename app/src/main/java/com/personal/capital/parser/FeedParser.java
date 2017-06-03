package com.personal.capital.parser;

import android.util.Xml;

import com.personal.capital.models.Feed;
import com.personal.capital.models.Item;
import com.personal.capital.models.Picture;
import com.personal.capital.utils.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by patel on 6/3/2017.
 */

public class FeedParser {

    public Feed parse(InputStream inputStream) throws XmlPullParserException, IOException {
        Feed feed = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
//            XmlPullParser parser = Xml.newPullParser();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
//            parser.next();
            feed = readFeed(parser);
        } finally {
            inputStream.close();
        }
        return feed;
    }

    private Feed readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {
        Feed feed = new Feed();

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();

                if(tagName.equals(Constants.TITLE)) {
                    feed.setTitle(readTitle(parser));
                } else if(tagName.equals(Constants.ITEM)) {
                    feed.addItem(readItem(parser));
                }
            }

            eventType = parser.next();
        }

//        parser.require(XmlPullParser.START_TAG, null, Constants.CHANNEL);
//        while(parser.next() != XmlPullParser.END_TAG) {
//            if(parser.getEventType() != XmlPullParser.START_TAG) continue;
//
//            String tagName = parser.getName();
//
//            if(tagName.equals(Constants.TITLE)) {
//                feed.setTitle(readTitle(parser));
//            } else if(tagName.equals(Constants.ITEM)) {
//                feed.addItem(readItem(parser));
//            } else {
//                skip(parser);
//            }
//        }

        return feed;
    }

    private Item readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, Constants.ITEM);

        Item item = new Item();

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals(Constants.TITLE)) {
                item.setTitle(readTitle(parser));
            } else if (name.equals(Constants.LINK)) {
                item.setLink(readLink(parser));
            } else if (name.equals(Constants.MEDIA_CONTENT)) {
                item.setPicture(readPicture(parser));
            } else {
                skip(parser);
            }
        }

        return item;
    }

    private Picture readPicture(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, Constants.MEDIA_CONTENT);

        Picture picture = new Picture();

        picture.setUrl(parser.getAttributeValue(null, Constants.URL));
        picture.setWidth(Integer.valueOf(parser.getAttributeValue(null, Constants.WIDTH)));
        picture.setHeight(Integer.valueOf(parser.getAttributeValue(null, Constants.HEIGHT)));

        return picture;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, Constants.TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, Constants.TITLE);
        return title;
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, Constants.LINK);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, Constants.LINK);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
