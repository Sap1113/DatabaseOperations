package com.database.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.database.Dashboard;
import com.database.R;
import com.database.bean.CategoryBean;
import com.database.bll.CategoryBll;
import com.database.utils.Utils;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.msgItemViewHolder> {
    Context context;
    ArrayList<CategoryBean> arrayCategoryList;
    CategoryBll bllCategory;
    CategoryBean beanCategory = new CategoryBean();

    public CategoryAdapter(Activity activity, ArrayList<CategoryBean> beanCategory) {
        this.context = activity;
        this.arrayCategoryList = beanCategory;
    }

    @Override
    public msgItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new msgItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.msgItemViewHolder holder, final int position) {
        CategoryBean beanCategory = this.arrayCategoryList.get(position);

        holder.txt_categoryName.setText(beanCategory.categoryName);

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNameDialogPopup(position, arrayCategoryList.get(position).id,
                        arrayCategoryList.get(position).categoryName);

            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bllCategory = new CategoryBll(context);
                bllCategory.DeleteCategory(arrayCategoryList.get(position).id);
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayCategoryList.size();
    }

    public class msgItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_categoryName;
        public ImageView img_delete, img_edit;

        public msgItemViewHolder(View itemView) {
            super(itemView);
            txt_categoryName = (TextView) itemView.findViewById(R.id.txt_name);
            img_edit = (ImageView) itemView.findViewById(R.id.img_edit);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);

        }
    }

    public void update(ArrayList<CategoryBean> modelList) {
        arrayCategoryList.clear();
        for (CategoryBean model : modelList) {
            arrayCategoryList.add(model);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrayCategoryList.remove(position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void set(int position, CategoryBean beanCategory) {
        arrayCategoryList.set(position, beanCategory);
        notifyDataSetChanged();
    }

    Dialog dialog;

    EditText edt_dialog_categoryName;
    Button btn_dialog_save, btn_dialog_cancel;


    private void UpdateNameDialogPopup(final int position, final int id, final String categoryName) {
        dialog = new Dialog(context);
        dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        edt_dialog_categoryName = (EditText) dialog.findViewById(R.id.edtTxt_updatepopup_categoryName);
        edt_dialog_categoryName.setText(categoryName + "");

        btn_dialog_save = (Button) dialog.findViewById(R.id.btn_updatepopup_save);
        btn_dialog_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (!edt_dialog_categoryName.getText().toString().equals("")) {
                    beanCategory = new CategoryBean();
                    beanCategory.id = id;
                    beanCategory.categoryName = edt_dialog_categoryName.getText().toString();
                    bllCategory = new CategoryBll(context);
                    bllCategory.Update(beanCategory);
                    edt_dialog_categoryName.setText("");

                    set(position, beanCategory);
                    dialog.dismiss();
                }else{
                    edt_dialog_categoryName.setError("Category Name can not be blank!");
                }

            }
        });
        btn_dialog_cancel = (Button) dialog.findViewById(R.id.btn_updatepopup_cancel);
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
