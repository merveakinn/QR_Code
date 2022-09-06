package com.merveakin.QRKod;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.merveakin.deneme.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class connectionmySQL extends AppCompatActivity {


    Button urunekle;
    Button uruncikar;

    EditText deger;
    int Adet;

    String tmpBarcode;


    ActivityResultLauncher<Intent> activityResultLauncher;


    private static final String url= "jdbc:mysql://192.168.10.20:3306/dburetim";


    private static final String user = "root";
    private static final String pass = "1234";

    public InfoAsyncTask  t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectionmy_sql);
        //    testDB();

        t= new InfoAsyncTask();
        t.execute();
        //new InfoAsyncTask().execute();

        deger= findViewById(R.id.deger);
        urunekle= findViewById(R.id.urunekle);
        uruncikar= findViewById(R.id.uruncikar);

  urunekle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try
        {
            UpdAsyncTask u = new UpdAsyncTask();
            String text = deger.getText().toString().trim();

            // int degerrr = Integer.parseInt(text);
            String sql = "UPDATE dburetim.tblproducts SET Adet = Adet+"+text+" WHERE Kodu ='" + tmpBarcode  + "'";
            u.sql = sql;
            Log.i("TAG", "onClick: DENEME: " + sql);
            u.execute();



        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }

    }
});
  uruncikar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        try
        {
            UpdAsyncTask u = new UpdAsyncTask();
            String text = deger.getText().toString().trim();
            // int degerrr = Integer.parseInt(text);
            String sql = "UPDATE dburetim.tblproducts SET Adet = Adet-"+text +" WHERE Kodu ='" + tmpBarcode + "'" ;

            u.sql = sql;
            Log.i("TAG", "onClick: DENEME: " + sql);
            u.execute();

        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
    }
});
    }

    @SuppressLint("StaticFieldLeak")
    public  class InfoAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            try (Connection connection = DriverManager.getConnection(url, user, pass)) {

                String sql = "SELECT * FROM dburetim.tblproducts WHERE Kodu ='" + MainActivity.Querybarcode  + "'";
                tmpBarcode = MainActivity.Querybarcode;
                MainActivity.Querybarcode = "";

                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    info.put("id", resultSet.getString("id"));
                    info.put("Kodu", resultSet.getString("Kodu"));
                    info.put("Adı", resultSet.getString("Adı"));
                    info.put("Müşteri", resultSet.getString("Müşteri"));
                    info.put("ACK", resultSet.getString("ACK"));
                    info.put("Adet", resultSet.getString("Adet"));
                    info.put("Tarih", resultSet.getString("Tarih"));
                    info.put("Foto", resultSet.getString("Foto"));


                    Log.i("MERVE","id:" + resultSet.getString("id") + " Kodu:" + resultSet.getString("Kodu") + " Adı:" +resultSet.getString("Adı"));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }
            return info;
        }



        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (!result.isEmpty()) {
                TextView textViewid = findViewById(R.id.text_view);
                TextView textViewKodu = findViewById(R.id.textView);
                TextView textViewAdı = findViewById(R.id.textView2);
                TextView textViewMusteri = findViewById(R.id.textView3);
                TextView textViewACK = findViewById(R.id.textView27);
                TextView textViewAdet = findViewById(R.id.Adet);
                TextView textViewTarih = findViewById(R.id.textVieww9);
                // TextView textViewFoto = findViewById(R.id.textViewo);

                textViewid.setText(result.get("id"));
                textViewKodu.setText(result.get("Kodu"));
                textViewAdı.setText(result.get("Adı"));
                textViewMusteri.setText(result.get("Müşteri"));
                textViewACK.setText(result.get("ACK"));
                textViewAdet.setText(result.get("Adet"));
                textViewTarih.setText(result.get("Tarih"));
                // textViewFoto.setText(result.get("Foto"));
            }
        }
    }



    @SuppressLint("StaticFieldLeak")
    public  class UpdAsyncTask extends AsyncTask<Void, Void, String> {
        String sql;

        @Override
        protected String doInBackground(Void... voids) {

            try (Connection connection = DriverManager.getConnection(url, user, pass)) {
                PreparedStatement statement = connection.prepareStatement(sql);
                Boolean sonuc = statement.execute();

                if (sonuc) {
                    Log.i("UPDATE async task", "Update Basarili.");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("UPDATE async task", "Error reading school information", e);
            }
            String ret = "Ok";
            return ret;
        }



        @Override
        public void onPostExecute(String result) {
            MainActivity.Querybarcode = tmpBarcode;
            InfoAsyncTask t = new InfoAsyncTask();
            t.execute();
        }


        public void testDB(){
            //   TextView tv=(TextView)this.findViewById(R.id.text_view);
            try{
                StrictMode.ThreadPolicy policy=
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Class.forName("com.mysql.jdbc.Driver");
                Connection con= DriverManager.getConnection(url,user,pass);

                String result = "Database bağlantısı başarılı!\n";
                Statement st= con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM dburetim.tblproducts;");
                ResultSetMetaData rsmd = rs.getMetaData();

                while(rs.next()){
                    result += rsmd.getColumnName(1) + ":"+rs.getInt("id")+"\n";
                    result += rsmd.getColumnName(2) + ":"+rs.getString("Kodu")+"\n";
                    result += rsmd.getColumnName(3) + ":"+rs.getString("Adı")+"\n";


                }



                while (rs.next()) {
                    System.out.println(
                            " "+rs.getInt("id")
                                    + ", "+rs.getString("Kodu") + ", "+rs.getString("Adı"));

                }


                //   tv.setText(result);
            }catch(Exception e){
                e.printStackTrace();
                //  tv.setText(e.toString());
            }
        }



    }


}