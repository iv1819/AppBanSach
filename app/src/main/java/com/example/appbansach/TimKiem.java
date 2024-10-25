package com.example.appbansach;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbansach.Database;
import com.example.appbansach.R;

import java.util.ArrayList;
import java.util.List;

public class TimKiem extends AppCompatActivity {

    private EditText edtTimKiem;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Database database;
    private List<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        edtTimKiem = findViewById(R.id.edtTimKiem);
        listView = findViewById(R.id.listView);
        database = new Database(this);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Lấy danh sách sản phẩm theo từ khóa
                itemList.clear();
                if (s.length() > 0) {
                    itemList.addAll(database.timKiemSanPham(s.toString()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageView imageViewSearch = findViewById(R.id.imageView3);
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy danh sách sản phẩm theo từ khóa khi nhấn vào biểu tượng tìm kiếm
                itemList.clear();
                itemList.addAll(database.timKiemSanPham(edtTimKiem.getText().toString()));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
