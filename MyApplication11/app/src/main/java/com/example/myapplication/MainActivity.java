package com.example.myapplication;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

   private ProgressDialog progressDialog;

    private ListView list;
    public ArrayList arrayList=new ArrayList();
    public  ArrayAdapter<String> adapter;
    Button button;

    private static  String URL="https://skinsort.com/ingredients/hyaluronic-acid";


    private void init(){

        list=findViewById(R.id.listView);
        button=findViewById(R.id.buttonin);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            init();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new VeriAl().execute();
                }
            });


    }

    class VeriAl extends AsyncTask<Void, Void ,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("In");
            progressDialog.setMessage("Lütfen bekleyiniz");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc=Jsoup.connect(URL).timeout(30*1000).get();

                //a[class*=flex]
                Elements Inadi=doc.select("a[class*=font-bold text-light-blue-800]");

                for (int i = 1; i <Inadi.size() ; i++) {
                    arrayList.add(Inadi.get(i).text());
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

    }



