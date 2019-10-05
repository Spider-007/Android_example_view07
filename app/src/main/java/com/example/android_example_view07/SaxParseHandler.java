package com.example.android_example_view07;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//sax parse
public class SaxParseHandler extends DefaultHandler {

    private static final String TAG = "SpiderLine";
    private StringBuffer id, name, version;
    private String nodeName;


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id = new StringBuffer();
        name = new StringBuffer();
        version = new StringBuffer();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)) {
            Log.e(TAG, "endElement:id:" + id);
            Log.e(TAG, "endElement:name:" + name);
            Log.e(TAG, "endElement:version:" + version);
            //remember clean stringBuffer content!
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
