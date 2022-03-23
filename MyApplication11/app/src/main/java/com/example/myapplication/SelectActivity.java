package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class SelectActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    String in;
    private ListView listView;
    public ArrayList arrayList=new ArrayList();

    private ArrayAdapter<String> adapter;

  //  private static  String URL="https://skinsort.com/ingredients/hyaluronic-acid";


    private void init(){

        listView=findViewById(R.id.list_View);

        Bundle bnd = getIntent().getExtras();
       in=(String)bnd.getString("key");

        in=in.toLowerCase(Locale.ROOT);
        in = in.replace(" ", "-");

            for(String item : in.split(","))
            {
                arrayList.add(item);
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();


        adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
       listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String title=adapterView.getAdapter().getItem(i).toString();

                new VeriAl(title).execute();
            }
        });
    }

    class VeriAl extends AsyncTask<Void, Void ,Void> {
        String title;
        String URL;
        String setTitle;
        ArrayList ingredientslist=new ArrayList();

        public VeriAl(String title) {

            setTitle=title;

            if(title.equals("aqua")){
                this.title="water";}
            else{
                this.title=title;
            }

            this.URL="https://skinsort.com/ingredients/"+this.title;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(SelectActivity.this);
            progressDialog.setTitle("In");
            progressDialog.setMessage("Please Wait");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc= Jsoup.connect(URL).get();
                Elements elements = doc.select("div.flex.flex-col.my-5> p >a.font-bold.text-light-blue-800");

                for (int i = 0; i < elements.size(); i++) {

                    ingredientslist.add(elements.get(i).text());
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
            AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this);
            String dialog_message=ingredientslist.toString();
            builder.setMessage(dialog_message).setTitle(setTitle.toUpperCase());
            AlertDialog dialog = builder.create();
            progressDialog.dismiss();
            dialog.show();




        }
    }


}
