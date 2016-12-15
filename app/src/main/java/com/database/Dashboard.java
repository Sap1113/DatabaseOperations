package com.database;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.adapter.CategoryAdapter;
import com.database.bean.CategoryBean;
import com.database.bll.CategoryBll;
import com.database.utils.Utils;

import java.util.ArrayList;

public class Dashboard extends Activity implements View.OnClickListener {

    private EditText edtTxt_categoryName;
    private Button btn_save;
    private RecyclerView recyclerView_list;

    CategoryBean beanCategory = new CategoryBean();
    CategoryBll bllCategory = new CategoryBll(Dashboard.this);

    ArrayList<CategoryBean> arrayCategoryList = new ArrayList<>();
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Utils.systemUpgrade(this);

        edtTxt_categoryName = (EditText) findViewById(R.id.edtTxt_categoryName);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        recyclerView_list = (RecyclerView) findViewById(R.id.recyclerView_list);

        bllCategory = new CategoryBll(Dashboard.this);
        arrayCategoryList = bllCategory.selectCategoryAll();
        if (arrayCategoryList != null && arrayCategoryList.size() > 0) {
            adapter = new CategoryAdapter(Dashboard.this, arrayCategoryList);
            recyclerView_list.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (!edtTxt_categoryName.getText().toString().equals("")) {
                    beanCategory = new CategoryBean();
                    beanCategory.categoryName = edtTxt_categoryName.getText().toString();
                    bllCategory = new CategoryBll(Dashboard.this);
                    bllCategory.Insert(beanCategory);
                    edtTxt_categoryName.setText("");

                    arrayCategoryList = null;
                    arrayCategoryList = new ArrayList<>();
                    bllCategory = new CategoryBll(Dashboard.this);

                    if (arrayCategoryList != null && arrayCategoryList.size() == 0) {
                        adapter = new CategoryAdapter(Dashboard.this, arrayCategoryList);
                        recyclerView_list.setAdapter(adapter);
                    }
                    adapter.update(bllCategory.selectCategoryAll());

                } else {
                    Toast.makeText(Dashboard.this, "Category Name can not be blank!", Toast.LENGTH_SHORT).show();
                    edtTxt_categoryName.setError("Category Name can not be blank!");
                }
                break;
        }
    }
}
