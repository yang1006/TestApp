package yll.self.testapp.normal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import yll.self.testapp.R;

/**
 * Created by yll on 2015/11/26.
 * ����HashMap �� HashTable ����ͬ
 */
public class HashTestActivity extends Activity {
    private TextView tv_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tesh);
        init();
        testMap();
        testTable();
    }

    private void init() {
        tv_hello = (TextView) findViewById(R.id.tv_hello);
    }

    private void testMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a_key", "a_value");
        map.put("b_key", "b_value");
        map.put("c_key", "c_value");
        map.put("d_key", "d_value");
        map.put("e_key", "e_value");

        tv_hello.setText("Map  Text:\n");

        Set<String> keySet = map.keySet();

        Iterator<String> keyI = keySet.iterator();
        tv_hello.append("Keys of the map :\n");
        while (keyI.hasNext()) {
            tv_hello.append(keyI.next() + "   ");
        }

        Collection<String> valueC = map.values();
        Iterator<String> valueI = valueC.iterator();
        tv_hello.append("\nValues of the map: \n");
        while (valueI.hasNext()) {
            tv_hello.append(valueI.next() + "   ");
        }

    }

    private void testTable() {
        tv_hello.append("\n\n\nTable Text:\nKeys of the table:\n");
        Hashtable<String, String> table = new Hashtable<>();
        table.put("a_key", "a_value");
        table.put("b_key", "b_value");
        table.put("c_key", "c_value");
        table.put("d_key", "d_value");
        table.put("e_key", "e_value");

//        Enumeration<String> ekeys = table.keys();
//        while (ekeys.hasMoreElements()){
//            tv_hello.append(ekeys.nextElement() + "   ");
//        }
//        tv_hello.append("\nValues of the table:\n");
//
//        Enumeration<String> eValues = table.elements();
//        while (eValues.hasMoreElements()){
//            tv_hello.append(eValues.nextElement() + "   ");
//        }

        Set<String> sKeys = table.keySet();
        Iterator<String> iKeys = sKeys.iterator();
        while (iKeys.hasNext()) {
            tv_hello.append(iKeys.next() + "   ");
        }
        tv_hello.append("\nValues of the table:\n");

        Collection<String> sValues = table.values();
        Iterator<String> iValues = sValues.iterator();
        while (iValues.hasNext()) {
            tv_hello.append(iValues.next() + "   ");
        }
        tv_hello.append("\n\nUse Entry to get key and value\n");
        Set<Map.Entry<String, String>> entries = table.entrySet();
//        for (Map.Entry<String, String> entry :entries){
//            tv_hello.append("key: "+entry.getKey() +"  value: " +entry.getValue()+"\n");
//        }
        Iterator<Map.Entry<String, String>> iEntries = entries.iterator();
        Map.Entry<String, String> entry;
        while (iEntries.hasNext()) {
            entry = iEntries.next();
            tv_hello.append("key: " + entry.getKey() + "  value: " + entry.getValue() + "\n");
        }

    }
}
