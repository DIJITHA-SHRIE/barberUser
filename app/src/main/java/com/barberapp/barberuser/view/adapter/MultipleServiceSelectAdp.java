package com.barberapp.barberuser.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.onMultipleServiceListener;
import com.barberapp.barberuser.pojos.CategoryData;
import java.util.ArrayList;

public class MultipleServiceSelectAdp extends BaseAdapter {
    ArrayList<CategoryData> categoryDataArrayList;
    Context context;
    ArrayList<String> getCheckedServices = new ArrayList<>();
    ArrayList<String> getMultipleServiceName = new ArrayList<>();
    onMultipleServiceListener multipleServiceListener;

    public MultipleServiceSelectAdp(Context context2, ArrayList<CategoryData> categoryDataArrayList2, onMultipleServiceListener multipleServiceListener2) {
        this.context = context2;
        this.categoryDataArrayList = categoryDataArrayList2;
        this.multipleServiceListener = multipleServiceListener2;
    }

    public int getCount() {
        return this.categoryDataArrayList.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2 = LayoutInflater.from(this.context).inflate(R.layout.multiple_service_selection, null);
        CheckBox simpleCheckedTextView = (CheckBox) view2.findViewById(R.id.simpleCheckedTextView);
        simpleCheckedTextView.setText(((CategoryData) this.categoryDataArrayList.get(i)).getSpecializeName());
        simpleCheckedTextView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MultipleServiceSelectAdp.this.getCheckedServices.add(((CategoryData) MultipleServiceSelectAdp.this.categoryDataArrayList.get(i)).getId());
                    MultipleServiceSelectAdp.this.getMultipleServiceName.add(((CategoryData) MultipleServiceSelectAdp.this.categoryDataArrayList.get(i)).getSpecializeName());
                    MultipleServiceSelectAdp.this.multipleServiceListener.onGetMultipleServiceListener(MultipleServiceSelectAdp.this.getCheckedServices, MultipleServiceSelectAdp.this.getMultipleServiceName);
                } else if (MultipleServiceSelectAdp.this.getCheckedServices.size() > 0 && MultipleServiceSelectAdp.this.getCheckedServices.contains(((CategoryData) MultipleServiceSelectAdp.this.categoryDataArrayList.get(i)).getId()) && MultipleServiceSelectAdp.this.getMultipleServiceName.contains(((CategoryData) MultipleServiceSelectAdp.this.categoryDataArrayList.get(i)).getSpecializeName())) {
                    MultipleServiceSelectAdp.this.getCheckedServices.remove(((CategoryData) MultipleServiceSelectAdp.this.categoryDataArrayList.get(i)).getId());
                    MultipleServiceSelectAdp.this.getMultipleServiceName.remove(((CategoryData) MultipleServiceSelectAdp.this.categoryDataArrayList.get(i)).getSpecializeName());
                    MultipleServiceSelectAdp.this.multipleServiceListener.onGetMultipleServiceListener(MultipleServiceSelectAdp.this.getCheckedServices, MultipleServiceSelectAdp.this.getMultipleServiceName);
                }
            }
        });
        return view2;
    }
}
