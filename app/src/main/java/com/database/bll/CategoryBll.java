package com.database.bll;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.database.bean.CategoryBean;
import com.database.utils.DBHelper;

import java.util.ArrayList;

public class CategoryBll {

    public Context context;

    public CategoryBll(Context context) {
        this.context = context;
    }

    public void sync(CategoryBean beanCategory) {
        DBHelper dbHelper = new DBHelper(this.context);
        Cursor cursor = null;
        int cnt = 0;
        try {
            dbHelper = new DBHelper(this.context);

            cursor = dbHelper
                    .query("SELECT COUNT(id) FROM Category WHERE id = "
                            + beanCategory.id);

            if (cursor != null) {
                cursor.moveToPosition(0);
                cnt = cursor.getInt(0);
                Log.i("sync", "cnt : " + cnt);
                if (cnt == 0) {
                    this.Insert(beanCategory);
                } else {
                    this.Update(beanCategory);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper = null;
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            cursor = null;
        }
    }

    public void Insert(CategoryBean beanCategory) {
        DBHelper objDBHelper = new DBHelper(this.context);
        StringBuilder Query = new StringBuilder();
        try {

            Query.append("INSERT INTO ")
                    .append("Category (categoryName)")
                    .append("Values ( '")
                    .append(DBHelper.getDBStr(beanCategory.categoryName))
                    .append("')");
            // Log.print(this.getClass() + " :: Query() :: ", Query.toString());
            objDBHelper.execute(Query.toString());

        } catch (Exception e) {
            // Log.print(this.getClass() + " :: Insert() ", e + "");
            e.printStackTrace();
        } finally {
            objDBHelper = null;
            Query = null;
        }

    }

    public void Update(CategoryBean beanCategory) {
        DBHelper objDBHelper = new DBHelper(this.context);
        StringBuilder Query = new StringBuilder();
        try {

            Query.append("UPDATE Category ").append("SET ")
                    .append(" categoryName = '")
                    .append(DBHelper.getDBStr(beanCategory.categoryName))
                    //.append("', date = '").append(beanCategory.Date)
                    .append("' WHERE id = ")
                    .append(beanCategory.id);

            Log.i(this.getClass() + "", " :: update() :: SQL :: "
                    + Query.toString());

            objDBHelper.execute(Query.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            objDBHelper = null;
            Query = null;
        }
    }

    public ArrayList<CategoryBean> selectCategoryAll() {
        DBHelper dbHelper = null;
        Cursor cursor = null;
        ArrayList<CategoryBean> arrayCategory = null;
        CategoryBean beanCategory;
        String sql = null;
        try {

            sql = "SELECT id, categoryName from Category ORDER BY id DESC";

            dbHelper = new DBHelper(this.context);

            cursor = dbHelper.query(sql);
            if (cursor != null && cursor.getCount() > 0) {
                arrayCategory = new ArrayList<CategoryBean>();
                while (cursor.moveToNext()) {
                    beanCategory = new CategoryBean();
                    beanCategory.id = cursor.getInt(0);
                    beanCategory.categoryName = cursor.getString(1);
                    arrayCategory.add(beanCategory);

                }
            }
        } catch (Exception e) {
            Log.d(this.getClass() + " :: select() ", e + "");
            e.printStackTrace();
        } finally {
            dbHelper = null;
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        // release
        dbHelper = null;
        cursor = null;
        beanCategory = null;
        sql = null;

        return arrayCategory;
    }

    public void DeleteCategory(int id) {
        DBHelper dbHelper = new DBHelper(this.context);
        StringBuilder Query = new StringBuilder();
        Cursor objCursor = null;
        try {

            Query.append("DELETE ").append(" FROM Category where id = " + id);

            objCursor = dbHelper.query(Query.toString());

            if (objCursor != null && objCursor.getCount() > 0) {
                objCursor.moveToPosition(-1);

            }
        } catch (Exception e) {
            Log.d(this.getClass() + " :: select() ", e + "");
            e.printStackTrace();
        } finally {

            dbHelper = null;
            Query = null;
            if (objCursor != null && !objCursor.isClosed()) {
                objCursor.close();
            }
            objCursor = null;
        }

    }
}
