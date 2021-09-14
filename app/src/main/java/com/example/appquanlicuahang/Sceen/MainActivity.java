package com.example.appquanlicuahang.Sceen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appquanlicuahang.Arapter.ArapterKhachHang;
import com.example.appquanlicuahang.Model.KhachHang;
import com.example.appquanlicuahang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyKhachHang;
    ArrayList<KhachHang> khachHangArrayList = new ArrayList<>();
    ArapterKhachHang arapterKhachHang;
    EditText edtKhachHang, edtSoDienThoai;
    String ten = "";
    String sdt = "";
    ArrayList<KhachHang> arr;
    int chon = 0;
    ArrayList<KhachHang> arr1;
    TextView txtNam, txtNu;
    ImageView imgThem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleSSLHandshake();

        innit();
        anhxa();
        setup();
        setClick();
    }

    private void setClick() {
        edtKhachHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ten = edtKhachHang.getText().toString().trim();
                ten = ten.toLowerCase();
                locTen();

            }
        });


        edtSoDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sdt = edtSoDienThoai.getText().toString().trim();
                locSDT();
            }
        });

    }

    private void locSDT() {
        if (arr == null) {
            arr1 = new ArrayList<>();
            for (KhachHang kh : khachHangArrayList) {
                if (kh.getSdt().substring(7).indexOf(sdt) >= 0) {
                    arr1.add(kh);
                }
            }
            arapterKhachHang.thayDoiList(arr1);

        } else {
            ArrayList<KhachHang> arr2 = new ArrayList<>();
            for (KhachHang kh : arr) {
                if (kh.getSdt().substring(7).indexOf(sdt) >= 0) {
                    arr2.add(kh);
                }
            }
            arapterKhachHang.thayDoiList(arr2);
        }

    }


    // lọc ra tên khách hàng
    private void locTen() {

        if (ten.length() > 2) {
            arr = new ArrayList<>();
            for (KhachHang kh : khachHangArrayList) {
                if (kh.getTenKhachHang().toLowerCase().indexOf(ten) >= 0) {
                    arr.add(kh);
                }
            }
        } else {
            arr = khachHangArrayList;
        }
        arapterKhachHang.thayDoiList(arr);

    }

    private void setup() {
        arapterKhachHang = new ArapterKhachHang(MainActivity.this, khachHangArrayList);
        recyKhachHang.setAdapter(arapterKhachHang);
        recyKhachHang.setLayoutManager(new LinearLayoutManager(this));
        txtNam.setOnClickListener(this);
        txtNu.setOnClickListener(this);
        imgThem.setOnClickListener(this);

    }

    private void anhxa() {
        txtNam = findViewById(R.id.txtNam);
        txtNu = findViewById(R.id.txtNu);
        recyKhachHang = findViewById(R.id.recyKhachHang);
        edtKhachHang = findViewById(R.id.edtTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        imgThem = findViewById(R.id.imgThem);

    }

    private void innit() {

        // khoi tao volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://192.168.31.134/android/gettableKhachHang.php";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //   tra ve mang doi tuong khach hang
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        khachHangArrayList.add(new KhachHang(jsonObject));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonArray);


    }

    // fix loi khong lay data ve duoc
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtNam:
                locGioiTinh(txtNam, txtNu, 1);
                break;
            case R.id.txtNu:
                locGioiTinh(txtNu, txtNam, 0);
                break;
            case R.id.imgThem:
                imgThemClick();
                break;
        }

    }

    //
    private void imgThemClick() {
        PopupMenu popupMenu = new PopupMenu(this, this.imgThem);
        popupMenu.inflate(R.menu.menu_them);
//        Menu menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            //set sự kiện cho popup
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClick(item);

            }
        });
        popupMenu.show();

    }


    @SuppressLint("ResourceAsColor")
    private void locGioiTinh(TextView txt1, TextView txt2, int gt) {
        txt2.setBackgroundColor(Color.WHITE);
        txt2.setTextColor(Color.BLACK);
        txt1.setBackgroundColor(Color.BLACK);
        txt1.setTextColor(Color.WHITE);
        ArrayList<KhachHang> arrayListGt = new ArrayList<>();
        for (KhachHang kh : khachHangArrayList) {

            if (kh.getGioiTinh() == gt) {
                arrayListGt.add(kh);
            }

        }
        arapterKhachHang.thayDoiList(arrayListGt);

    }

    public boolean menuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.themKh:
                Intent intent = new Intent(MainActivity.this, ThemKhachHang.class);
                startActivity(intent);
                break;
            case R.id.themTho:
                showDialog();
                break;
        }
        return false;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String urlThemTho = "http://192.168.31.134/android/inserttho.php";
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themtho, null);
        builder.setView(view);
        EditText edtTho;
        TextView txtThemTho;
        edtTho = view.findViewById(R.id.edtThemTho);
        txtThemTho = view.findViewById(R.id.txtThemTho);
        // thêm thợ
        txtThemTho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest string = new StringRequest(Request.Method.POST, urlThemTho, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().trim().equals("Success")){
                            Toast.makeText(MainActivity.this,"Thêm Thợ Thành Công",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("tenTho",edtTho.getText().toString().trim());
                        return params ;
                    }
                };
                requestQueue.add(string);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        
    }

}